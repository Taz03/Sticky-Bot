package bot.listener;

import bot.App;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class SlashCommandListener extends ListenerAdapter {
    @Override
    public void onSlashCommand(@NotNull SlashCommandEvent event) {
        try {
            if (Objects.requireNonNull(event.getMember()).hasPermission(Permission.MANAGE_CHANNEL)) {
                App.getCommands().get(event.getName()).run(event);
            } else {
                event.reply("You need permission(s) `MANAGE_CHANNEL` to use the command").setEphemeral(true).queue();
            }
        } catch (Exception e) {
            event.reply("An unknown error occurred while executing the command.").setEphemeral(true).queue();

            e.printStackTrace();
        }
    }
}
