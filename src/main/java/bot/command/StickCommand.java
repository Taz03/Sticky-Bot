package bot.command;

import bot.sticky.StickyMessage;
import bot.sticky.StickyMessageUtils;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;

public class StickCommand implements MessageCommand {
    @Override
    public void run(MessageContextInteractionEvent event) {
        String msg = event.getTarget().getContentRaw();
        MessageChannel channel = event.getTarget().getChannel();

        event.reply("""
                Sticking Message...
                Done :thumbsup:""").setEphemeral(true).queue();

        channel.sendMessage(msg).queue(message -> {
            StickyMessage stickyMessage = new StickyMessage(channel.getIdLong(), msg, message.getIdLong());

            StickyMessageUtils.addSticky(event.getGuild().getIdLong(), stickyMessage);
        });
    }

    @Override
    public String name() {
        return "Stick Message";
    }
}
