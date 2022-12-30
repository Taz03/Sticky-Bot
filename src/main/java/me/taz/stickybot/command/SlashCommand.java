package me.taz.stickybot.command;

import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;

public interface SlashCommand {
    void run(SlashCommandInteractionEvent event);
    String getName();
    String getDescription();

    default void onCommandAutoCompleteInteraction(CommandAutoCompleteInteractionEvent event) { }
    default void onButtonInteraction(ButtonInteractionEvent event) { }
    default void onSelectMenuInteraction(SelectMenuInteractionEvent event) { }
    default void onModalInteraction(ModalInteractionEvent event) { }
}
