package bot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StickyMessageUtils {
    private final static Map<Long, List<StickyMessage>> channelsMap = new HashMap<>();

    public static void addSticky(Long channelId, StickyMessage stickyMessage) {
        if (channelsMap.containsKey(channelId)) {
            channelsMap.get(channelId).add(stickyMessage);
        } else {
            List<StickyMessage> stickyMessageList = new ArrayList<>();
            stickyMessageList.add(stickyMessage);
            channelsMap.put(channelId, stickyMessageList);
        }
    }

    public static List<StickyMessage> getStickyMessageList(Long channelId) {
        return channelsMap.getOrDefault(channelId, new ArrayList<>());
    }
}
