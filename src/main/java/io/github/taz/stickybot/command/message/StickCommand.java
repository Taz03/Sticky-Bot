package io.github.taz.stickybot.command.message;

import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;

import io.github.taz.stickybot.command.MessageCommand;
import io.github.taz.stickybot.sticky.StickyMessage;
import io.github.taz.stickybot.sticky.StickyMessageUtils;

public class StickCommand implements MessageCommand {
    @Override
    public void run(MessageContextInteractionEvent event) {
        String msg = event.getTarget().getContentRaw();
        MessageChannel channel = event.getTarget().getChannel();

        event.reply("Sticked :thumbsup:").setEphemeral(true).queue();

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
