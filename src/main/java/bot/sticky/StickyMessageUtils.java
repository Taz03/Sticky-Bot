package bot.sticky;

import java.sql.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StickyMessageUtils {
    private static final String URL = "jdbc:postgresql://" + System.getenv("PGHOST") + ":" + System.getenv("PGPORT") + "/" + System.getenv("PGDATABASE");
    private static final String USER = System.getenv("PGUSER");
    private static final String PASS = System.getenv("PGPASSWORD");
    private static final Map<Long, List<StickyMessage>> GUILD_MAP = new HashMap<>();

    static {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASS)) {
            try (Statement statement = connection.createStatement()) {
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

    public static void addSticky(Long guildId, StickyMessage stickyMessage) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASS)) {
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

    private static void addStickyToMap(Long guildId, StickyMessage stickyMessage) {
        if (GUILD_MAP.containsKey(stickyMessage.getChannelId())) {
            GUILD_MAP.get(guildId).add(stickyMessage);
        } else {
            List<StickyMessage> stickyMessageList = new ArrayList<>();
            stickyMessageList.add(stickyMessage);
            GUILD_MAP.put(guildId, stickyMessageList);
        }
    }

    public static void removeSticky(Long guildId, StickyMessage stickyMessage) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASS)) {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("DELETE FROM DATA WHERE messageId = " + stickyMessage.getMessageId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        GUILD_MAP.get(guildId).remove(stickyMessage);
    }

    public static void updateSticky(StickyMessage stickyMessage, Long newMessageId) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASS)) {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("UPDATE DATA SET messageId = " + newMessageId + " WHERE messageId = " + stickyMessage.getMessageId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        stickyMessage.setMessageId(newMessageId);
    }

    public static List<StickyMessage> getStickyMessageList(Long guildId) {
        return GUILD_MAP.getOrDefault(guildId, new ArrayList<>());
    }
}
