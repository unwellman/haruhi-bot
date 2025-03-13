package net.ddns.haruhionly.haruhi;

import net.ddns.haruhionly.haruhi.Haruhi;

import java.util.Set;
import java.util.Collection;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.entity.Player;

public class ServerListener implements Listener {
	private Haruhi haruhi;

	public ServerListener (Haruhi haruhi) {
		this.haruhi = haruhi;
	}

	@EventHandler
	public void onPlayerJoin (PlayerJoinEvent event) {
		String name = event.getPlayer().getName();
		this.haruhi.onPlayerJoin(name);
	}

	@EventHandler
	public void onPlayerQuit (PlayerQuitEvent event) {
		String name = event.getPlayer().getName();
		this.haruhi.onPlayerQuit(name);
	}

	@EventHandler
	public void onPlayerChat (AsyncPlayerChatEvent event) {
		// Only forward if audience is every player
		Collection<? extends Player> online = this.haruhi.getServer().getOnlinePlayers();
		Set<Player> recipients = event.getRecipients();
		if (!recipients.containsAll(online)) {
			return;
		}

		String name = event.getPlayer().getName();
		String message = event.getMessage();
		this.haruhi.onPlayerChat(name, message);
	}

}

