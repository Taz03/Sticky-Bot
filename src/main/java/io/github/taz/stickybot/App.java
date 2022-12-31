package io.github.taz.stickybot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import io.github.taz.stickybot.listener.MessageContextInteractionListener;
import io.github.taz.stickybot.listener.MessageReceivedListener;
import io.github.taz.stickybot.listener.SlashCommandInteractionListener;

public class App {
    public static void main(String[] args) {
        JDA bot = JDABuilder.createDefault(System.getenv("TOKEN"))
                .addEventListeners(new MessageReceivedListener())
                .build();

        bot.addEventListener(new MessageContextInteractionListener(bot));
        bot.addEventListener(new SlashCommandInteractionListener(bot));
    }
}
