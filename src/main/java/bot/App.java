package bot;

import bot.listener.MessageContextInteractionListener;
import bot.listener.MessageReceivedListener;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class App {
    public static JDA bot;

    public static void main(String[] args) throws LoginException {
        bot = JDABuilder.createDefault(System.getenv("TOKEN"))
                .addEventListeners(new MessageReceivedListener())
                .build();

        bot.addEventListener(new MessageContextInteractionListener(bot));
    }
}
