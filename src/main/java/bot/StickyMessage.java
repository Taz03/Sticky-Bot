package bot;

import net.dv8tion.jda.api.entities.MessageEmbed;

import java.util.List;

public class StickyMessage {
    private final long messageChannelId;
    private final String text;
    private long messageId;
    private List<MessageEmbed> embeds;

    public StickyMessage(long messageChannelId, String text, List<MessageEmbed> embeds) {
        this.messageChannelId = messageChannelId;
        this.text = text;
        setEmbeds(embeds);
    }

    public long getMessageChannelId() {
        return messageChannelId;
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

    public List<MessageEmbed> getEmbeds() {
        return embeds;
    }

    public void setEmbeds(List<MessageEmbed> embeds) {
        this.embeds = embeds;
    }
}
