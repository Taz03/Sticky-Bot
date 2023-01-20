package io.github.taz.stickybot.listener;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import io.github.taz.stickybot.sticky.StickyMessage;
import io.github.taz.stickybot.sticky.StickyMessageUtils;

public class ResendStickyListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().equals(event.getJDA().getSelfUser()))
            return;

        StickyMessageUtils.getStickyMessageList(event.getGuild().getIdLong()).forEach(stickyMessage -> {
            if (stickyMessage.getChannelId() == event.getChannel().getIdLong()) 
                resendSticky(event.getJDA(), stickyMessage);
        });
    }

    public static void resendSticky(JDA jda, StickyMessage stickyMessage) {
        MessageChannel channel = jda.getTextChannelById(stickyMessage.getChannelId());

        if (channel != null) {
            channel.deleteMessageById(stickyMessage.getMessageId()).queue();
            channel.sendMessage(stickyMessage.getText()).queue(message -> StickyMessageUtils.updateSticky(stickyMessage, message.getIdLong()));
        }
    }
}
