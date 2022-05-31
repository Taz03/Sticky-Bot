package bot.command;

import bot.StickyMessage;
import bot.StickyMessageUtils;

import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;

import java.util.List;

public class UnStickCommand implements MessageCommand {
    @Override
    public void run(MessageContextInteractionEvent event) {
        long channelId = event.isFromGuild() ? event.getGuild().getIdLong() : event.getPrivateChannel().getIdLong();

        List<StickyMessage> stickyMessages = StickyMessageUtils.getStickyMessageList(channelId);

        for (StickyMessage stickyMessage : stickyMessages) {
            if (stickyMessage.getMessageId() == event.getTarget().getIdLong()) {
                stickyMessages.remove(stickyMessage);

                (event.isFromGuild() ? event.getChannel() : event.getPrivateChannel()).deleteMessageById(stickyMessage.getMessageId()).queue();

                event.reply("Removed :thumbsup:").setEphemeral(true).queue();
                return;
            }
        }

        event.reply("This is not a stickied message!").setEphemeral(true).queue();
    }

    @Override
    public String name() {
        return "Unstick Message";
    }
}
