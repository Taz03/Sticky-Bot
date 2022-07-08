package bot.listener;

import bot.command.MessageCommand;
import bot.command.StickCommand;
import bot.command.UnStickCommand;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

import org.jetbrains.annotations.NotNull;

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
                Commands.message(command.name()).setGuildOnly(command.isGuildOnly())
        ));

        update.queue();
    }

    private static void addCommand(MessageCommand command) {
        commandMap.put(command.name(), command);
    }

    @Override
    public void onMessageContextInteraction(@NotNull MessageContextInteractionEvent event) {
        commandMap.get(event.getName()).run(event);
    }
}
