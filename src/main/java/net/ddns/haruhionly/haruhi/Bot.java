package net.ddns.haruhionly.haruhi;

import java.util.logging.Logger;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.attribute.IPermissionContainer;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;

public class Bot extends ListenerAdapter {
	public long clientID;
	public String startupMessage;
	public Logger logger;

	private JDA bot;
	private TextChannel sys;
	@Override
	public void onReady(ReadyEvent event) {
		this.bot = event.getJDA();
		this.logger.info(String.format("Startup message is %s", this.startupMessage));
		for (Guild gld : this.bot.getGuilds()) {
			this.logger.info(String.format("Sending startup message to %s", gld.getName()));
			this.sys = gld.getSystemChannel();
			try {
				this.sys.sendMessage(this.startupMessage).queue();
			}
			catch(InsufficientPermissionException e) {
				this.logger.info(String.format("Insufficient permissions in %s", gld.getName()));
			}
		}
	}
}

