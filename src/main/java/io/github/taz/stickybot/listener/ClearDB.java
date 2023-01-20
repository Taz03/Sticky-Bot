package io.github.taz.stickybot.listener;

import io.github.taz.stickybot.sticky.StickyMessageUtils;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public final class ClearDB extends ListenerAdapter {
    @Override
    public void onGuildLeave(GuildLeaveEvent event) {
        StickyMessageUtils.removeGuild(event.getGuild().getIdLong());
    }
}
