package me.taz.stickybot.command.message;

import me.taz.stickybot.command.MessageCommand;
import me.taz.stickybot.sticky.StickyMessage;
import me.taz.stickybot.sticky.StickyMessageUtils;

import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
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
    public String getName() {
        return "Stick Message";
    }
}
