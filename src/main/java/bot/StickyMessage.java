package bot;

public class StickyMessage {
    private final long channelId;
    private final String text;
    private long messageId;

    public StickyMessage(long channelId, String text) {
        this.channelId = channelId;
        this.text = text;
    }

    public long getChannelId() {
        return channelId;
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
}
