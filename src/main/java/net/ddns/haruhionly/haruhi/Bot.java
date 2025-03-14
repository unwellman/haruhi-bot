package net.ddns.haruhionly.haruhi;

import net.ddns.haruhionly.haruhi.Haruhi;

import java.util.logging.Logger;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

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
	private List<TextChannel> forwardChannels;

	public Bot (Haruhi haruhi) {
		this.haruhi = haruhi;
		this.forwardChannels = new ArrayList();
	}

	@Override
	public void onReady (ReadyEvent event) {
		this.api = event.getJDA();
		this.haruhi.ready();
	}

	public int messageAll (String message) {
		int ret = 0;
		if (this.forwardChannels == null)
			return 0;
		for (TextChannel channel : this.forwardChannels) {
			Guild gld = channel.getGuild();
			this.logger.info(String.format("Sending message to #%s in %s",
				channel.getName(), gld.getName()));
			try {
				channel.sendMessage(message).queue();
				ret ++;
			}
			catch(InsufficientPermissionException e) {
				this.logger.info("Insufficient permissions");
			}
		}
		return ret;
	}

	public void addForwardChannel (Long channelId) {
		this.forwardChannels.add(this.api.getTextChannelById(channelId));
	}

}

