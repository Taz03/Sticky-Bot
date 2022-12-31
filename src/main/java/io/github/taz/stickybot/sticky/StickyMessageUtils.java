package io.github.taz.stickybot.sticky;

import java.sql.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StickyMessageUtils {
    private static final String URL = "jdbc:sqlite:src/main/resources/db/sticky.db";
    private static final Map<Long, List<StickyMessage>> guildMap = new HashMap<>();

    static {
        try (Connection connection = DriverManager.getConnection(URL)) {
            try (Statement statement = connection.createStatement()) {
                statement.execute("""
                    CREATE TABLE DATA IF NOT EXISTS (
                        serverId  BIGINT NOT NULL,
                        channelId BIGINT NOT NULL,
                        text      TEXT NOT NULL,
                        messageId BIGINT NOT NULL
                    )
                """);
                try (ResultSet stickies = statement.executeQuery("SELECT * FROM DATA")) {
                    while (stickies.next()) {
                        addStickyToMap(stickies.getLong("serverId"), new StickyMessage(
                                stickies.getLong("channelId"),
                                stickies.getString("text"),
                                stickies.getLong("messageId")
                        ));
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
                        "INSERT INTO DATA VALUES (" + guildId + ", " + stickyMessage.getChannelId() + ", '" + stickyMessage.getText() + "', " + stickyMessage.getMessageId() + ")"
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
                statement.executeUpdate("DELETE FROM DATA WHERE messageId = " + stickyMessage.getMessageId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        guildMap.get(guildId).remove(stickyMessage);
    }

    public static void updateSticky(StickyMessage stickyMessage, long newMessageId) {
        try (Connection connection = DriverManager.getConnection(URL)) {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("UPDATE DATA SET messageId = " + newMessageId + " WHERE messageId = " + stickyMessage.getMessageId());
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
