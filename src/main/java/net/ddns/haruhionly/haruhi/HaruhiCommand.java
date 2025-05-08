package net.ddns.haruhionly.haruhi;

import net.ddns.haruhionly.haruhi.Haruhi;

import java.util.logging.Logger;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.bukkit.command.TabExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;
import org.bukkit.util.StringUtil;

public class HaruhiCommand implements TabExecutor {
	private Haruhi haruhi;

	private Logger logger;

	public HaruhiCommand (Haruhi haruhi) {
		this.haruhi = haruhi;
		this.logger = haruhi.getLogger();
	}

	public boolean onCommand (CommandSender sender, Command command,
			String label, String[] args) {
		switch (args[0]) {
			case "channel":
			case "c":
				return this.channel(sender, command, label, args);
			case "reply":
			case "r":
				return this.reply(sender, command, label, args);
			case "user":
			case "u":
				return this.user(sender, command, label, args);
			default:
				return this.messageAll(sender, command, label, args);
		}
	}

	public List<String> onTabComplete(CommandSender sender, Command command,
			String label, String[] args) {
		List<String> ret = new ArrayList<String>();
		if (args.length < 2) {
			String[] options = {"channel", "c", "reply", "r", "user", "u"};
			StringUtil.copyPartialMatches(args[0], Arrays.asList(options), ret);
			Collections.sort(ret);
			return ret;
		}
		switch (args[0]) {
			case "channel":
			case "c":
				return this.tabChannel(sender, command, label, args);
			case "reply":
			case "r":
				return null;
			case "user":
			case "u":
				return this.tabUser(sender, command, label, args);
			default:
				return null;
		}
	}

	private boolean channel (CommandSender sender, Command command,
			String label, String[] args) {
		// crudely parse guild and channel
		List<String> parsed = this.parseChannelMessage(args);
		// parse message
		String response = this.haruhi.commandChannel(parsed.get(0), parsed.get(1),
				sender.getName(), parsed.get(2));
		// pass strings to Haruhi and fetch response
		// sender.sendMessage() with status
		return true;
	}

	private List<String> tabChannel (CommandSender sender, Command command,
			String label, String[] args) {
		List<String> choices = this.haruhi.commandChannelTab();
		List<String> parsed = this.parseChannelMessage(args);
		for (String name : choices) {
			this.logger.info(String.format("Option %s", name));
		}
		// Check if "#" is not found, then only request guilds
		List<String> ret = new ArrayList<String>();
		String token = String.format("%s#%s", parsed.get(0), parsed.get(1));
		StringUtil.copyPartialMatches(token, choices, ret);
		Collections.sort(ret);
		return ret;
	}

	private List<String> parseChannelMessage (String[] args) {
		int i = 0;
		while (args[i].indexOf("#") < 0) {
			i++;
			if (i >= args.length) {
				return Arrays.asList(args);
			}
				
		}

		List<String> channelArgs = Arrays.asList(args).subList(1, i);
		String guild = String.join(" ", channelArgs);
		int j = args[i].indexOf("#");
		String channel = args[i].substring(j + 1);
		String message = String.join(" ", Arrays.copyOfRange(args, i + 1, args.length));
		String[] ret = {guild, channel, message};
		return Arrays.asList(ret);
	}

	private boolean reply (CommandSender sender, Command command,
			String label, String[] args) {
		// pass message straight to Haruhi
		return true;
	}

	private boolean user (CommandSender sender, Command command,
			String label, String[] args) {
		// Try to read user as UUID (long)
		// If fail, read as string
		// pass to Haruhi
		return true;
	}

	private List<String> tabUser (CommandSender sender, Command command,
			String label, String[] args) {
		return null;
	}

	private boolean messageAll (CommandSender sender, Command command,
			String label, String[] args) {
		// pass message straight to Haruhi
		return true;
	}
}

