package io.github.taz.stickybot.command.slash;

import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

import io.github.taz.stickybot.command.SlashCommand;
import io.github.taz.stickybot.sticky.StickyMessage;
import io.github.taz.stickybot.sticky.StickyMessageUtils;

public class StickCommand implements SlashCommand {
    @Override
    public void run(SlashCommandInteractionEvent event) {
        TextInput messageInput = TextInput.create("message", "Message", TextInputStyle.PARAGRAPH)
                .setPlaceholder("The message to stick...")
                .setMinLength(1)
                .setMaxLength(2000)
                .build();

        Modal modal = Modal.create("stick", "Stick Message")
                .addActionRows(ActionRow.of(messageInput))
                .build();

        event.replyModal(modal).queue();
    }

    @Override
    public void onModalInteraction(ModalInteractionEvent event) {
        if (event.getModalId().equals("stick")) {
            String msg = event.getValue("message").getAsString();
            MessageChannel channel = event.getChannel();

            event.reply("Sticked :thumbsup:").setEphemeral(true).queue();

            channel.sendMessage(msg).queue(message -> {
                StickyMessage stickyMessage = new StickyMessage(channel.getIdLong(), msg, message.getIdLong());

                StickyMessageUtils.addSticky(event.getGuild().getIdLong(), stickyMessage);
            });
        }
    }

    @Override
    public String getName() {
        return "stick";
    }

    @Override
    public String getDescription() {
        return "sticks a text";
    }
}
