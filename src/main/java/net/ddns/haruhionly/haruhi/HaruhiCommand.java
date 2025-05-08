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
			case "list":
				return this.list(sender, command, label, args);
			case "channel":
				return this.channel(sender, command, label, args);
			case "reply":
				return this.reply(sender, command, label, args);
			case "user":
				return this.user(sender, command, label, args);
			default:
				return false;
		}
	}

	public List<String> onTabComplete(CommandSender sender, Command command,
			String label, String[] args) {
		List<String> ret = new ArrayList<String>();
		if (args.length < 2) {
			String[] options = {"list", "channel", "reply", "user"};
			StringUtil.copyPartialMatches(args[0], Arrays.asList(options), ret);
			Collections.sort(ret);
			return ret;
		}
		switch (args[0]) {
			case "user":
				return this.tabUser(sender, command, label, args);
			case "list":
			case "channel":
			case "reply":
			default:
				return null;
		}
	}
	
	private boolean list (CommandSender sender, Command command,
			String label, String[] args) {
		String enumerated = this.haruhi.enumerateChannels();
		sender.sendMessage(enumerated);
		return true;
	}

	private boolean channel (CommandSender sender, Command command,
			String label, String[] args) {
		// crudely parse guild and channel
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

	private boolean user (CommandSender sender, Command command,
			String label, String[] args) {
		// Try to read user as UUID (long)
		// If fail, read as string
		// pass to Haruhi
		sender.sendMessage("Not implemented!");
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

