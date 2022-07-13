package io.github.randomkiddo.worldborder;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BorderRemoveCommand implements CommandExecutor {
    @Override public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) { return false; }
        if (sender instanceof Player) {
            Player player = (Player)sender;
            player.getWorld().getWorldBorder().reset();
        } else {
            Bukkit.getWorld(args[0]).getWorldBorder().reset();
        }
        return true;
    }
}
