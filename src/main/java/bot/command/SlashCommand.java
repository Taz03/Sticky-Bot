package bot.command;

import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;
import org.jetbrains.annotations.NotNull;

public interface SlashCommand {
    void run(SlashCommandInteractionEvent event);
    String getName();
    String getDescription();

    default void onCommandAutoCompleteInteraction(@NotNull CommandAutoCompleteInteractionEvent event) { }
    default void onButtonInteraction(@NotNull ButtonInteractionEvent event) { }
    default void onSelectMenuInteraction(@NotNull SelectMenuInteractionEvent event) { }
    default void onModalInteraction(@NotNull ModalInteractionEvent event) { }
}
