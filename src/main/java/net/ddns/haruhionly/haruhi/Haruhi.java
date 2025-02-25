package net.ddns.haruhionly.haruhi;

import java.io.File;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;

import net.dv8tion.jda.api.JDA;

public class Haruhi extends JavaPlugin {
	@Override
	public void onEnable() {
		final File data = this.getDataFolder();
		FileConfiguration config = this.getConfig();
		if (!config.contains("token")) {
			config.addDefault("token", "NONE");
			this.saveConfig();
		}
	}

	@Override
	public void onDisable() {
	}
}
