package bot.command;

import bot.StickyMessage;
import bot.StickyMessageUtils;

import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;

import java.util.List;

public class UnStickCommand implements MessageCommand {
    @Override
    public void run(MessageContextInteractionEvent event) {
        List<StickyMessage> stickyMessages = StickyMessageUtils.getStickyMessageList(event.getGuild().getIdLong());

        for (StickyMessage stickyMessage : stickyMessages) {
            if (stickyMessage.getMessageId() == event.getTarget().getIdLong()) {
                stickyMessages.remove(stickyMessage);

                event.getChannel().deleteMessageById(stickyMessage.getMessageId()).queue();

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

    @Override
    public boolean isGuildOnly() {
        return true;
    }
}
