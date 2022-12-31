package io.github.taz.stickybot.command;

import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;

public interface MessageCommand {
    void run(MessageContextInteractionEvent event);
    String getName();
    
    default void onModalInteraction(ModalInteractionEvent event) { }
}
