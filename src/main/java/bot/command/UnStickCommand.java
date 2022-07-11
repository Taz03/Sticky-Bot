package bot.command;

import bot.sticky.StickyMessage;
import bot.sticky.StickyMessageUtils;

import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;

import java.util.List;

public class UnStickCommand implements MessageCommand {
    @Override
    public void run(MessageContextInteractionEvent event) {
        Long guildId = event.getGuild().getIdLong();
        List<StickyMessage> stickyMessages = StickyMessageUtils.getStickyMessageList(guildId);

        for (StickyMessage stickyMessage : stickyMessages) {
            if (stickyMessage.getMessageId() == event.getTarget().getIdLong()) {
                event.getChannel().deleteMessageById(stickyMessage.getMessageId()).queue();

                event.reply("Removed :thumbsup:").setEphemeral(true).queue();

                StickyMessageUtils.removeSticky(guildId, stickyMessage);

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
