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
				return false;
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
		return null;
	}
	
	private boolean listChannels (CommandSender sender, Command command,
			String label, String[] args) {
		String enumerated = this.haruhi.enumerateChannels();
		sender.sendMessage(enumerated);
		return true;
	}

	private boolean channel (CommandSender sender, Command command,
			String label, String[] args) {
		switch (args[1]) {
			case "list":
			case "l":
				return this.listChannels(sender, command, label, args);
			default:
				break;
		}

		int channelPos;
		try {
			channelPos = Integer.valueOf(args[1]);
		} catch (NumberFormatException e) {
			sender.sendMessage("Invalid channel number format!");
			return true;
		}
		String message = String.join(" ", Arrays.copyOfRange(args, 2, args.length));
		String response = this.haruhi.commandChannel(channelPos, sender.getName(),
			message);
		sender.sendMessage(response);
		return true;
	}

	private boolean reply (CommandSender sender, Command command,
			String label, String[] args) {
		// pass message straight to Haruhi
		sender.sendMessage("Not implemented!");
		return true;
	}

	private boolean listUsers (CommandSender sender, Command command,
			String label, String[] args) {
		String enumerated = this.haruhi.enumerateUsers();
		sender.sendMessage(enumerated);
		return true;
	}

	private boolean user (CommandSender sender, Command command,
			String label, String[] args) {
		switch (args[1]) {
			case "list":
			case "l":
				return this.listUsers(sender, command, label, args);
			default:
				break;
		}

		int userPos;
		try {
			userPos = Integer.valueOf(args[1]);
		} catch (NumberFormatException e) {
			sender.sendMessage("Invalid channel number format!");
			return true;
		}
		String message = String.join(" ", Arrays.copyOfRange(args, 2, args.length));
		String response = this.haruhi.commandUser(userPos, sender.getName(), message);
		sender.sendMessage(response);
		return true;
	}

	private List<String> tabUser (CommandSender sender, Command command,
			String label, String[] args) {
		return null;
	}

	private boolean messageAll (CommandSender sender, Command command,
			String label, String[] args) {
		// pass message straight to Haruhi
		sender.sendMessage("Not implemented!");
		return true;
	}
}

