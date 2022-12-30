package me.taz.stickybot.command;

import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;

public interface MessageCommand {
    void run(MessageContextInteractionEvent event);
    String getName();

    default void onButtonInteraction(ButtonInteractionEvent event) { }
    default void onSelectMenuInteraction(SelectMenuInteractionEvent event) { }
    default void onModalInteraction(ModalInteractionEvent event) { }
}
