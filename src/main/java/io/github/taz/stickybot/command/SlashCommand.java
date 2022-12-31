package io.github.taz.stickybot.command;

import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public interface SlashCommand {
    void run(SlashCommandInteractionEvent event);
    String getName();
    String getDescription();

    default void onModalInteraction(ModalInteractionEvent event) { }
}
