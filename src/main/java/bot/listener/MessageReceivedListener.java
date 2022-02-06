package bot.listener;

import bot.StickyMessage;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import org.jetbrains.annotations.NotNull;

public class MessageReceivedListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor() == event.getJDA().getSelfUser()) return;

        StickyMessage stickyMessage = StickyMessage.getStickyMessageByChannel(event.getChannel());

        if (stickyMessage != null) {
            stickyMessage.getChannel().deleteMessageById(stickyMessage.getMessageId()).queue();

            stickyMessage.getChannel().sendMessage(stickyMessage.getText()).queue(message -> {
                stickyMessage.setMessageId(message.getIdLong());
            });
        }
    }
}
