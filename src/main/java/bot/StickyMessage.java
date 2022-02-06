package bot;

import net.dv8tion.jda.api.entities.MessageChannel;

import java.util.ArrayList;

public class StickyMessage {
    private final static ArrayList<StickyMessage> stickyMessages = new ArrayList<>();

    private final MessageChannel channel;
    private final String text;
    private long messageId;

    public StickyMessage(MessageChannel channel, String text) {
        this.channel = channel;
        this.text = text;
    }

    public MessageChannel getChannel() {
        return channel;
    }

    public String getText() {
        return text;
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public static void addStickyMessage(StickyMessage stickyMessage) {
        stickyMessages.add(stickyMessage);
    }

    public static void removeStickyMessage(StickyMessage stickyMessage) {
        stickyMessages.remove(stickyMessage);
    }

    public static StickyMessage getStickyMessageByChannel(MessageChannel channel) {
        for (StickyMessage stickyMessage : stickyMessages) {
            if (stickyMessage.getChannel().getId().equals(channel.getId())) {
                return stickyMessage;
            }
        }

        return null;
    }
}
