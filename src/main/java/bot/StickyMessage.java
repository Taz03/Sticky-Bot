package bot;

import net.dv8tion.jda.api.entities.MessageEmbed;

import java.util.List;

public class StickyMessage {
    private final long messageChannelId;
    private final String text;
    private final List<MessageEmbed> embeds;
    private long messageId;

    public StickyMessage(long messageChannelId, String text, List<MessageEmbed> embeds) {
        this.messageChannelId = messageChannelId;
        this.text = text;
        this.embeds = embeds;
    }

    public long getMessageChannelId() {
        return messageChannelId;
    }

    public String getText() {
        return text;
    }

    public List<MessageEmbed> getEmbeds() {
        return embeds;
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }
}
