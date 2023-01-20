package io.github.taz.stickybot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import dev.mccue.json.Json;
import dev.mccue.json.JsonDecoder;
import io.github.taz.stickybot.listener.ClearDB;
import io.github.taz.stickybot.listener.MessageContextInteractionListener;
import io.github.taz.stickybot.listener.ResendStickyListener;
import io.github.taz.stickybot.listener.SlashCommandInteractionListener;
import io.github.taz.stickybot.sticky.StickyMessageUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class App {
    public static Json configJson = null;
    
    public static void main(String[] args) throws IOException, InterruptedException {
        String configString = Files.readString(Path.of(args.length > 0 ? args[0] : "src/main/resources/config.json"));
        configJson = Json.readString(configString);

        JDA jda = JDABuilder.createDefault(JsonDecoder.field(configJson, "token", JsonDecoder::string)).build();

        jda.awaitReady();
        StickyMessageUtils.init(jda);

        CommandListUpdateAction updateAction = jda.updateCommands();

        jda.addEventListener(new MessageContextInteractionListener(updateAction));
        jda.addEventListener(new SlashCommandInteractionListener(updateAction));

        updateAction.queue();

        jda.addEventListener(new ResendStickyListener());
        jda.addEventListener(new ClearDB());
    }
}
