package bot;

import bot.listener.MessageContextInteractionListener;
import bot.listener.MessageReceivedListener;

import bot.listener.SlashCommandInteractionListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class App {
    public static JDA bot;

    public static void main(String[] args) {
        bot = JDABuilder.createDefault(System.getenv("TOKEN"))
                .addEventListeners(new MessageReceivedListener())
                .build();

        bot.addEventListener(new MessageContextInteractionListener(bot));
        bot.addEventListener(new SlashCommandInteractionListener(bot));
    }
}
