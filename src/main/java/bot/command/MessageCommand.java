package bot.command;

import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;

public interface MessageCommand {
    void run(MessageContextInteractionEvent event);
    String name();

    default boolean isGuildOnly() {
        return false;
    }
}
