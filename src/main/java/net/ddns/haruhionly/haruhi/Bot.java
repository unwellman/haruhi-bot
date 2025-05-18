package net.ddns.haruhionly.haruhi;

import net.ddns.haruhionly.haruhi.Haruhi;

import java.util.logging.Logger;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
import net.dv8tion.jda.api.entities.channel.attribute.IPermissionContainer;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import net.dv8tion.jda.api.requests.restaction.CacheRestAction;
import net.dv8tion.jda.api.Permission;

public class Bot extends ListenerAdapter {
	public long clientID;
	public Logger logger;

	private JDA api;
	private Haruhi haruhi;
	private List<TextChannel> forwardChannels;
	private List<TextChannel> allChannels;
	private User user;
	private List<PrivateChannel> dms;

	public Bot (Haruhi haruhi) {
		this.haruhi = haruhi;
		this.forwardChannels = new ArrayList();
		this.allChannels = new ArrayList();
		this.dms = new ArrayList();
	}

	@Override
	public void onReady (ReadyEvent event) {
		this.api = event.getJDA();
		this.haruhi.ready();
		this.user = this.api.getUserById(this.clientID);
		this.updateAllChannels();
		this.updateAllDMs();
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

	Consumer<PrivateChannel> append = dm -> this.dms.add(dm);

	private void updateAllDMs () {
		for (User user : this.api.getUsers()) {
			if (user.hasPrivateChannel()) {
				CacheRestAction<PrivateChannel> action = user.openPrivateChannel();
				action.queue(this.append);
			}
		}
	}
	
	public String enumerateChannels () {
		String ret = new String();
		int i = 0;
		for (TextChannel channel : this.allChannels) {
			ret += String.format("(%d) %s#%s\n", i, channel.getGuild().getName(),
				channel.getName());
			i++;
		}
		return ret;
	}

	public String messageChannel (int channelPos, String message) {
		TextChannel channel;
		try {
			channel = this.allChannels.get(channelPos);
		} catch (IndexOutOfBoundsException e) {
			return "Invalid channel index";
		}
		try {
			channel.sendMessage(message).queue();
		} catch (InsufficientPermissionException e) {
			return "The Haruhi bot does not have permissions in that channel!";
		}
		return String.format("%s: %s", channel.getName(), message);
	}

	public String messageChannel (Long channelId, String message) {
		return "Not implemented!";
	}

	public String enumerateUsers () {
		String ret = new String();
		int i = 0;
		for (PrivateChannel dm : this.dms) {
			ret += String.format("(%d) %s\n", i, dm.getName());
			i++;
		}
		if (i == 0) {
			return "No DM's found!";
		}
		return ret;
	}

	public String messageUser (int userPos, String message) {
		PrivateChannel channel;
		try {
			channel = this.dms.get(userPos);
		} catch (IndexOutOfBoundsException e) {
			return "Invalid user index";
		}
		try {
			channel.sendMessage(message).queue();
		} catch (InsufficientPermissionException e) {
			return "The Haruhi bot cannot DM this user!";
		}
		return String.format("%s: %s", channel.getName(), message);
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

