package net.ddns.haruhionly.haruhi;

import net.ddns.haruhionly.haruhi.Bot;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.ConfigurationSection;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class Haruhi extends JavaPlugin {
	private JDA api;

	@Override
	public void onEnable() {
		this.configure();
	}

	private void configure() {
		FileConfiguration config = this.getConfig();
		if (!config.contains("discord")) {
			config.createSection("discord");
		}
		ConfigurationSection discord =
			config.getConfigurationSection("discord");
		if (!discord.isSet("token")) {
			discord.set("token", "YOUR-TOKEN-HERE");
			this.getLogger().warning("Need Discord bot access token. Update config.yml");
		}
		if (!discord.isSet("client-id")) {
			discord.set("client-id", 0);
			this.getLogger().warning("Need Discord bot client ID. Update config.yml");
		}
		this.saveConfig();
		this.apiInit();
	}

	private void apiInit() {
		final String token = this.getConfig().getString("discord.token");
		this.getLogger().info(String.format("Got Discord token <%s>", token));
		this.api = JDABuilder.createDefault(token).build();
		Bot bot = new Bot();
		bot.startupMessage = "Haruhi Only is online at `haruhionly.ddns.net:25565`!";
		bot.clientID = this.getConfig().getLong("discord.client-id");
		bot.logger = this.getLogger();
		this.api.addEventListener(bot);
	}

	@Override
	public void onDisable() {
	}
}

