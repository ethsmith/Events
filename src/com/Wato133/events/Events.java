package com.Wato133.events;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Events extends JavaPlugin {
	
	public void onEnable() {
		saveConfig();
		try {
			setupConfig(getConfig());
		} catch (IOException e) {
			e.printStackTrace();
		}
		saveConfig();
		Bukkit.getServer().getLogger().info("[Events] Events enabled!");
	}
	
	public void onDisable() {
		saveConfig();
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
					player.sendMessage(Title.replace("&", "\u007a"));
					player.sendMessage("***************************************************************");
					player.sendMessage(getConfig().getString("EventTitle").replace("&", "\u007a"));
					player.sendMessage(getConfig().getString("EventInfo").replace("&", "\u007a"));
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
	
	private void setupConfig(FileConfiguration config) throws IOException {
		if(!new File(getDataFolder(), "RESET.FILE").exists()) {
			new File(getDataFolder(), "RESET.FILE").createNewFile();
			
			config.set("EventHeader", "&1Events");
			config.set("EventTitle", "&3Example Title");
			config.set("EventInfo", "&3This is an example event.");
			
			new File(getDataFolder(), "RESET.FILE").createNewFile();
		}
	}
}
