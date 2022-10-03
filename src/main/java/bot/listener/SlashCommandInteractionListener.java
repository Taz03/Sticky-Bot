package bot.listener;

import bot.command.SlashCommand;
import bot.command.slash.StickCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class SlashCommandInteractionListener extends ListenerAdapter {
    public static final Map<String, SlashCommand> commandMap = new HashMap<>();

    static {
        addCommand(new StickCommand());
    }

    public SlashCommandInteractionListener(JDA bot) {
        CommandListUpdateAction update = bot.updateCommands();

        commandMap.values().forEach(command -> update.addCommands(
                Commands.slash(command.getName(), command.getDescription()).setGuildOnly(true)
        ));

        update.queue();
    }

    private static void addCommand(SlashCommand command) {
        commandMap.put(command.getName(), command);
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        commandMap.get(event.getName()).run(event);
    }

    @Override
    public void onCommandAutoCompleteInteraction(@NotNull CommandAutoCompleteInteractionEvent event) {
        commandMap.get(event.getName()).onCommandAutoCompleteInteraction(event);
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        commandMap.values().forEach(command -> command.onButtonInteraction(event));
    }

    @Override
    public void onSelectMenuInteraction(@NotNull SelectMenuInteractionEvent event) {
        commandMap.values().forEach(command -> command.onSelectMenuInteraction(event));
    }

    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent event) {
        commandMap.values().forEach(command -> command.onModalInteraction(event));
    }
}
