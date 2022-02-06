package bot.command;

import bot.StickyMessage;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;

public class StickCommand implements Command {
    @Override
    public void run(SlashCommandEvent event) {
        MessageChannel channel = event.getChannel();
        String text;

        try {
            text = channel.retrieveMessageById(event.getOption("id").getAsString()).complete().getContentRaw();
        } catch (Exception e) {
            e.printStackTrace();
            event.reply("No message found with this id").setEphemeral(true).queue();
            return;
        }

        StickyMessage stickyMessage = new StickyMessage(channel, text);

        channel.sendMessage(text).queue(message -> {
            stickyMessage.setMessageId(message.getIdLong());
        });

        StickyMessage.addStickyMessage(stickyMessage);

        event.reply("Sticked a message to this channel").setEphemeral(true).queue();
    }

    @Override
    public String getName() {
        return "stick";
    }

    @Override
    public String getDescription() {
        return "Sticks the specified message to this channel";
    }

    @Override
    public ArrayList<OptionData> getOptions() {
        ArrayList<OptionData> options = new ArrayList<>();

        options.add(new OptionData(OptionType.STRING, "id", "id of the message to stick (must be in this channel)", true));

        return options;
    }
}
