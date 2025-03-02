package net.ddns.haruhionly.haruhi;

import net.ddns.haruhionly.haruhi.Haruhi;

import java.util.logging.Logger;
import java.util.List;

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
	public Logger logger;

	private JDA api;
	private Haruhi haruhi;
	public Bot(Haruhi haruhi) {
		this.haruhi = haruhi;
	}

	@Override
	public void onReady(ReadyEvent event) {
		this.api = event.getJDA();
		this.haruhi.ready();
	}

	public int messageAll(String message) {
		int ret = 0;
		for (Guild gld : this.api.getGuilds()) {
			TextChannel sys = gld.getSystemChannel();
			this.logger.info(String.format("Sending message to %s", gld.getName()));
			try {
				sys.sendMessage(message).queue();
				ret ++;
			}
			catch(InsufficientPermissionException e) {
				this.logger.info(String.format("Insufficient permissions in %s", gld.getName()));
			}
		}
		return ret;
	}

}

