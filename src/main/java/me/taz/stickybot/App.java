package me.taz.stickybot;

import me.taz.stickybot.listener.MessageContextInteractionListener;
import me.taz.stickybot.listener.MessageReceivedListener;
import me.taz.stickybot.listener.SlashCommandInteractionListener;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class App {
    public static void main(String[] args) {
        JDA bot = JDABuilder.createDefault(System.getenv("TOKEN"))
                .addEventListeners(new MessageReceivedListener())
                .build();

        bot.addEventListener(new MessageContextInteractionListener(bot));
        bot.addEventListener(new SlashCommandInteractionListener(bot));
    }
}
