package net.ddns.haruhionly.haruhi;

import net.ddns.haruhionly.haruhi.Bot;
import net.ddns.haruhionly.haruhi.ServerListener;

import java.util.logging.Logger;
import java.util.Map;
import java.util.Set;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class Haruhi extends JavaPlugin {
	private JDA api;
	private Bot bot;

	@Override
	public void onEnable () {
		this.configure();
		this.apiInit();
		ServerListener listener = new ServerListener(this);
		this.getServer().getPluginManager().registerEvents(listener, this);
	}

	public void ready () {
		if (this.getConfig().getBoolean("system.send-startup-message")) {
			this.bot.messageAll(this.getConfig().getString("discord.startup-message"));
		}
	}

	@Override
	public void onDisable () {
		if (this.getConfig().getBoolean("system.send-restart-message")) {
			this.bot.messageAll(this.getConfig().getString("discord.restart-message"));
		}
	}

	public void onPlayerJoin (String name) {
		if (this.getConfig().getBoolean("system.send-join-message")) {
			String message = this.getConfig().getString("discord.player-join-message");
			this.bot.messageAll(String.format(message, name));
		}
	}

	public void onPlayerQuit (String name) {
		if (this.getConfig().getBoolean("system.send-quit-message")) {
			String message = this.getConfig().getString("discord.player-quit-message");
			this.bot.messageAll(String.format(message, name));
		}
	}

	public void onPlayerChat (String name, String message) {
		if (this.getConfig().getBoolean("system.forward-chat-messages")) {
			String format = this.getConfig().getString("discord.player-chat-format");
			this.bot.messageAll(String.format(format, name, message));
		}
	}

	private void configure () {
		this.saveDefaultConfig(); // Fails if old config.yml already exists
		YamlConfiguration config = new YamlConfiguration();
		try {
			config.load(this.getTextResource("defaults.yml")); // Path in the jar
		}
		catch (IOException e) {
			this.getLogger().severe("IO error: " + e.getMessage());
		}
		catch (InvalidConfigurationException e) {
			this.getLogger().severe("Default configuration YAML is invalid: " + e.getMessage());
		}
		Set<String> keys = config.getKeys(true);
		this.getLogger().info(String.format("Got defaults\n%s", config.saveToString()));
		// TODO: Make sure to throw a fatal exception if the user-defined config.yml is invalid
		this.deepCopyConfig(config, keys);
		this.saveConfig();
	}

	private void deepCopyConfig (YamlConfiguration defaults, Set<String> keys) {
		// I don't trust this code, but seems to do what I expect it to, so I'm not touching it.
		this.reloadConfig();
		for (String key : keys) {
			if (!this.getConfig().contains(key)) {
				this.getLogger().info(String.format("Adding %s: %s", key, defaults.get(key)));
				this.getConfig().createSection(key);
				this.getConfig().set(key, defaults.get(key));
			}
		}
	}

	private void apiInit () {
		final String token = this.getConfig().getString("discord.token");
		this.getLogger().info(String.format("Got Discord token <%s>", token));
		this.api = JDABuilder.createDefault(token).build();

		this.botInit ();
		this.api.addEventListener(this.bot);
	}

	private void botInit () {
		this.bot = new Bot(this);
		this.bot.clientID = this.getConfig().getLong("discord.client-id");
		this.bot.logger = this.getLogger();
	}

}

