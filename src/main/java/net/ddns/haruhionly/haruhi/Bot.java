package net.ddns.haruhionly.haruhi;

import java.util.logging.Logger;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class Bot extends ListenerAdapter {
	public String startupMessage;
	public Logger logger;

	private JDA bot;
	private TextChannel sys;
	@Override
	public void onReady(ReadyEvent event) {
		this.bot = event.getJDA();
		this.logger.info(String.format("Startup message is %s\n", this.startupMessage));
		for (Guild gld : this.bot.getGuilds()) {
			this.logger.info(String.format("Sending startup message to %s\n", gld.getName()));
			this.sys = gld.getSystemChannel();
			this.sys.sendMessage(this.startupMessage);
		}
	}
}

