package bot.listener;

import bot.StickyMessageUtils;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import org.jetbrains.annotations.NotNull;

import static bot.App.bot;

public class MessageReceivedListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().equals(event.getJDA().getSelfUser())) return;

        StickyMessageUtils.getStickyMessageList(event.isFromGuild() ? event.getGuild().getIdLong() : event.getPrivateChannel().getIdLong()).forEach(stickyMessage -> {
            if (stickyMessage.getMessageChannelId() == event.getChannel().getIdLong()) {
                MessageChannel channel = bot.getChannelById(MessageChannel.class, stickyMessage.getMessageChannelId());

                assert channel != null;
                channel.deleteMessageById(stickyMessage.getMessageId()).queue();

                channel.sendMessage(stickyMessage.getText()).setEmbeds(stickyMessage.getEmbeds()).queue(message -> stickyMessage.setMessageId(message.getIdLong()));
            }
        });
    }
}
