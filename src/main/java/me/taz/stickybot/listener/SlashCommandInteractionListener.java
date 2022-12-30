package me.taz.stickybot.listener;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

import java.util.HashMap;
import java.util.Map;

import me.taz.stickybot.command.SlashCommand;
import me.taz.stickybot.command.slash.StickCommand;

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
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        commandMap.get(event.getName()).run(event);
    }

    @Override
    public void onCommandAutoCompleteInteraction(CommandAutoCompleteInteractionEvent event) {
        commandMap.get(event.getName()).onCommandAutoCompleteInteraction(event);
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        commandMap.values().forEach(command -> command.onButtonInteraction(event));
    }

    @Override
    public void onSelectMenuInteraction(SelectMenuInteractionEvent event) {
        commandMap.values().forEach(command -> command.onSelectMenuInteraction(event));
    }

    @Override
    public void onModalInteraction(ModalInteractionEvent event) {
        commandMap.values().forEach(command -> command.onModalInteraction(event));
    }
}
