package com.Wato133.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Events extends JavaPlugin {
	
	public void onEnable() {
		getConfig().options().copyDefaults();
		saveConfig();
		Bukkit.getServer().getLogger().info("[Events] Events enabled!");
	}
	
	public void onDisable() {
		Bukkit.getServer().getLogger().info("[Events] Events disabled!");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage("Only players can use the events plugin!");
		} else {
			Player player = (Player) sender;
			if(cmd.getName().equalsIgnoreCase("events") && args.length == 0) {
				if(player.hasPermission("events.info")) {
					String Title = getConfig().getString("EventHeader");
					player.sendMessage(Title.replace("&", "\u007A"));
					player.sendMessage("***************************************************************");
					player.sendMessage(getConfig().getString("EventTitle").replace("&", "\u007A"));
					player.sendMessage(getConfig().getString("EventInfo").replace("&", "\u007A"));
					player.sendMessage("***************************************************************");
				} else {
					player.sendMessage(ChatColor.RED + "[Events] You do not have permission to use that command!");
				}
			} else if(cmd.getName().equalsIgnoreCase("events") && args.length > 0) {
				if(args[0] == "reload") {
					if(player.hasPermission("events.reload")) {
						reloadConfig();
						player.sendMessage(ChatColor.BLUE + "[Events] Config reloaded...");
					} else {
						player.sendMessage(ChatColor.RED + "[Events] You do not have permission to use that command!");
					}
				} else {
					player.sendMessage(ChatColor.RED + "[Events] That is not a valid subcommand!");
				}
			}
		}
		return true;
	}
}
