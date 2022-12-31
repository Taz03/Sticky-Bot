package io.github.taz.stickybot.listener;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

import io.github.taz.stickybot.command.MessageCommand;
import io.github.taz.stickybot.command.message.StickCommand;
import io.github.taz.stickybot.command.message.UnStickCommand;

import java.util.HashMap;
import java.util.Map;

public class MessageContextInteractionListener extends ListenerAdapter {
    public static final Map<String, MessageCommand> commandMap = new HashMap<>();

    static {
        addCommand(new StickCommand());
        addCommand(new UnStickCommand());
    }

    public MessageContextInteractionListener(JDA bot) {
        CommandListUpdateAction update = bot.updateCommands();

        commandMap.values().forEach(command -> update.addCommands(
                Commands.message(command.getName()).setGuildOnly(true)
        ));

        update.queue();
    }

    private static void addCommand(MessageCommand command) {
        commandMap.put(command.getName(), command);
    }

    @Override
    public void onMessageContextInteraction(MessageContextInteractionEvent event) {
        commandMap.get(event.getName()).run(event);
    }
}
