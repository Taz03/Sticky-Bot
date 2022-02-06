package bot.command;

import bot.StickyMessage;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

public class UnstickCommand implements Command {
    @Override
    public void run(SlashCommandEvent event) {
        StickyMessage stickyMessage = StickyMessage.getStickyMessageByChannel(event.getChannel());

        if (stickyMessage == null) {
            event.reply("There is no message sticked to this channel").setEphemeral(true).queue();

            return;
        }

        stickyMessage.getChannel().deleteMessageById(stickyMessage.getMessageId()).queue();

        StickyMessage.removeStickyMessage(stickyMessage);

        event.reply("The message is now un-sticked from this channel").setEphemeral(true).queue();
    }

    @Override
    public String getName() {
        return "unstick";
    }

    @Override
    public String getDescription() {
        return "unstick the sticked message from this channel(if any)";
    }
}
