package io.github.taz.stickybot.sticky;

import dev.mccue.json.JsonDecoder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import io.github.taz.stickybot.App;
import io.github.taz.stickybot.listener.ResendStickyListener;

import java.lang.Thread.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StickyMessageUtils {
    private static final Logger logger = LoggerFactory.getLogger(StickyMessageUtils.class);
    private static final String DB_PATH = JsonDecoder.field(App.configJson, "db_storage", JsonDecoder::string) + "sticky.db";
    private static final String URL = "jdbc:sqlite:" + DB_PATH;
    private static final Map<Long, List<StickyMessage>> guildMap = new HashMap<>();

    public static void init(JDA jda) {
        try (Connection connection = DriverManager.getConnection(URL)) {
            try (Statement statement = connection.createStatement()) {
                statement.execute("""
                    CREATE TABLE IF NOT EXISTS sticky_data (
                        serverId  BIGINT NOT NULL,
                        channelId BIGINT NOT NULL,
                        text      TEXT   NOT NULL,
                        messageId BIGINT NOT NULL
                    )
                """);
                try (ResultSet stickies = statement.executeQuery("SELECT * FROM sticky_data")) {
                    while (stickies.next()) {
                        long channelId = stickies.getLong("channelId");
                        MessageChannel channel = jda.getTextChannelById(channelId);

                        if (channel == null) {
                            statement.executeUpdate("DELETE FROM sticky_data WHERE channelId = " + channelId);
                            continue;
                        }

                        long serverId = stickies.getLong("serverId");
                        long messageId = stickies.getLong("messageId");

                        StickyMessage stickyMessage = new StickyMessage(channelId, stickies.getString("text"), messageId);
                        addStickyToMap(serverId, stickyMessage);

                        channel.getHistory().retrievePast(1).map(messages -> messages.get(0)).queue(message -> {
                            if (message.getIdLong() != messageId)
                                ResendStickyListener.resendSticky(jda, stickyMessage);
                        });
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addSticky(long guildId, StickyMessage stickyMessage) {
        try (Connection connection = DriverManager.getConnection(URL)) {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(
                        "INSERT INTO sticky_data VALUES (" + guildId + ", " + stickyMessage.getChannelId() + ", '" + stickyMessage.getText() + "', " + stickyMessage.getMessageId() + ")"
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        addStickyToMap(guildId, stickyMessage);
    }

    private static void addStickyToMap(long guildId, StickyMessage stickyMessage) {
        List<StickyMessage> stickyMessageList = guildMap.getOrDefault(guildId, new ArrayList<>());
        stickyMessageList.add(stickyMessage);
        guildMap.put(guildId, stickyMessageList);
    }

    public static void removeSticky(long guildId, StickyMessage stickyMessage) {
        try (Connection connection = DriverManager.getConnection(URL)) {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("DELETE FROM sticky_data WHERE messageId = " + stickyMessage.getMessageId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        guildMap.get(guildId).remove(stickyMessage);
    }

    public static void removeGuild(long guildId) {
        try (Connection connection = DriverManager.getConnection(URL)) {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("DELETE FROM sticky_data WHERE guildId = " + guildId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        guildMap.remove(guildId);
    }

    public static void updateSticky(StickyMessage stickyMessage, long newMessageId) {
        try (Connection connection = DriverManager.getConnection(URL)) {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("UPDATE sticky_data SET messageId = " + newMessageId + " WHERE messageId = " + stickyMessage.getMessageId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        stickyMessage.setMessageId(newMessageId);
    }

    public static List<StickyMessage> getStickyMessageList(long guildId) {
        return guildMap.getOrDefault(guildId, new ArrayList<>());
    }
}
