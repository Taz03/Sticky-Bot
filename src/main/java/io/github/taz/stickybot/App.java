package io.github.taz.stickybot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import dev.mccue.json.Json;
import dev.mccue.json.JsonDecoder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import io.github.taz.stickybot.listener.MessageContextInteractionListener;
import io.github.taz.stickybot.listener.MessageReceivedListener;
import io.github.taz.stickybot.listener.SlashCommandInteractionListener;

public class App {
    public static void main(String[] args) throws IOException {
        String config = Files.readString(Path.of("src/main/resources/config.json"));
        Json json = Json.readString(config);

        JDA bot = JDABuilder.createDefault(JsonDecoder.field(json, "token", JsonDecoder::string))
                .addEventListeners(new MessageReceivedListener())
                .build();

        CommandListUpdateAction updateAction = bot.updateCommands();

        bot.addEventListener(new MessageContextInteractionListener(updateAction));
        bot.addEventListener(new SlashCommandInteractionListener(updateAction));

        updateAction.queue();
    }
}
