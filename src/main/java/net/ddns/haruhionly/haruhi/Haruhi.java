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
	private JDA bot;

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
		this.saveConfig();
		this.botInit();
	}

	private void botInit() {
		final String token = this.getConfig().getString("discord.token");
		this.bot = JDABuilder.createDefault(token).build();
		Bot listener = new Bot();
		listener.startupMessage = "Haruhi Only is online at haruhionly.ddns.net:25565";
		listener.logger = this.getLogger();
		this.bot.addEventListener(listener);
	}

	@Override
	public void onDisable() {
	}
}

