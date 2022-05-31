package bot.command;

import bot.StickyMessage;
import bot.StickyMessageUtils;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;

import java.util.List;

public class StickCommand implements MessageCommand {
    @Override
    public void run(MessageContextInteractionEvent event) {
        String msg = event.getTarget().getContentRaw();
        List<MessageEmbed> embeds = event.getTarget().getEmbeds();
        MessageChannel channel = event.getTarget().getChannel();

        StickyMessage stickyMessage = new StickyMessage(channel.getIdLong(), msg, embeds);

        channel.sendMessage(msg).setEmbeds(embeds).queue(message -> stickyMessage.setMessageId(message.getIdLong()));

        if (event.isFromGuild()) StickyMessageUtils.addSticky(event.getGuild().getIdLong(), stickyMessage);
        else StickyMessageUtils.addSticky(event.getPrivateChannel().getIdLong(), stickyMessage);

        event.reply("""
                Sticking Message...
                Done :thumbsup:""").setEphemeral(true).queue();
    }

    @Override
    public String name() {
        return "Stick Message";
    }
}
