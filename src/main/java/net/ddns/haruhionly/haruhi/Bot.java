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
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.attribute.IPermissionContainer;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import net.dv8tion.jda.api.Permission;

public class Bot extends ListenerAdapter {
	public long clientID;
	public Logger logger;

	private JDA api;
	private Haruhi haruhi;
	private List<TextChannel> forwardChannels;
	private List<TextChannel> allChannels;
	private User user;

	public Bot (Haruhi haruhi) {
		this.haruhi = haruhi;
		this.forwardChannels = new ArrayList();
		this.allChannels = new ArrayList();
	}

	@Override
	public void onReady (ReadyEvent event) {
		this.api = event.getJDA();
		this.haruhi.ready();
		this.user = this.api.getUserById(this.clientID);
		this.updateAllChannels();
	}

	private void updateAllChannels () {
		for (Guild guild : this.api.getGuilds()) {
			Member member = guild.getMemberById(this.clientID);
			for (GuildChannel channel : guild.getChannels()) {
				if (channel instanceof TextChannel) {
					this.logger.info(String.format("Checking permissions for %s#%s",
						guild.getName(), channel.getName()));
					if (member.hasPermission(channel,
					Permission.VIEW_CHANNEL, Permission.MESSAGE_SEND)) {
						this.allChannels.add((TextChannel) channel);
						this.logger.info("Good");
					}
				}
			}
		}
	}

	public boolean messageChannel (Long channelId, String message) {
		TextChannel channel = this.api.getTextChannelById(channelId);
		try {
			channel.sendMessage(message).queue();
		} catch (InsufficientPermissionException e) {
			return false;
		}
		return true;
	}

	public List<String> tabCompleteChannelIds () {
		List<String> completeIds = new ArrayList();
		for (TextChannel channel : this.allChannels) {
			completeIds.add(channel.getId());
		}
		return completeIds;
	}

	public List<String> tabCompleteChannelNames () {
		List<String> completeNames = new ArrayList<String>();
		for (TextChannel channel : this.allChannels) {
			String name = String.format("%s#%s", channel.getGuild().getName(),
					channel.getName());
			this.logger.info(String.format("Found channel %s", name));
			completeNames.add(name);
		}
		return completeNames;
	}

	public int messageAll (String message) {
		int ret = 0;
		if (this.forwardChannels == null)
			return 0;
		for (TextChannel channel : this.forwardChannels) {
			Guild guild = channel.getGuild();
			this.logger.info(String.format("Sending message to #%s in %s",
				channel.getName(), guild.getName()));
			try {
				channel.sendMessage(message).queue();
				ret ++;
			} catch (InsufficientPermissionException e) {
				this.logger.info("Insufficient permissions");
			}
		}
		return ret;
	}

	public void addForwardChannel (Long channelId) {
		this.forwardChannels.add(this.api.getTextChannelById(channelId));
	}

}

