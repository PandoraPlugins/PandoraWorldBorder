package io.github.randomkiddo.worldborder;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BorderCommand implements CommandExecutor {
    @Override public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) { return false; }
        /*
        /border x z size
        /border size
        /border x z

        /border world x z size
        /border world size
        /border world x z
         */
        boolean completed = false;
        if (sender instanceof Player) {
            Player player = (Player)sender;
            WorldBorder wb = player.getWorld().getWorldBorder();
            //TODO handle ~ in player-registered commands
            args = this.parseForTilde(args, player);
            switch (args.length) {
                case 1:
                    wb.setCenter(player.getLocation());
                    wb.setSize(Double.parseDouble(args[0]));
                    completed = true;
                    break;
                case 2:
                    wb.setCenter(Double.parseDouble(args[0]), Double.parseDouble(args[1]));
                    completed = true;
                    break;
                case 3:
                    wb.setCenter(Double.parseDouble(args[0]), Double.parseDouble(args[1]));
                    wb.setSize(Double.parseDouble(args[2]));
                    completed = true;
                    break;
            }
            wb.setDamageAmount(1_000_000_000);
            wb.setDamageBuffer(0);
        } else {
            try {
                World world = Bukkit.getWorld(args[0]);
                WorldBorder wb = world.getWorldBorder();
                switch (args.length) {
                    case 2:
                        wb.setCenter(0, 0);
                        wb.setSize(Double.parseDouble(args[1]));
                        completed = true;
                        break;
                    case 3:
                        wb.setCenter(Double.parseDouble(args[1]), Double.parseDouble(args[2]));
                        completed = true;
                        break;
                    case 4:
                        wb.setCenter(Double.parseDouble(args[1]), Double.parseDouble(args[2]));
                        wb.setSize(Double.parseDouble(args[3]));
                        completed = true;
                        break;
                }
                wb.setDamageAmount(1_000_000_000);
                wb.setDamageBuffer(0);
            } catch (NullPointerException npe) { npe.printStackTrace(); return false; }
        }
        return completed;
    }
    private String[] parseForTilde(String[] args, Player player) {
        if (args.length == 1) { return args; }
        else if (args.length == 2) {
            String[] copy = new String[args.length];
            copy[0] = this.parseTilde(args[0], player, 'x');
            copy[1] = this.parseTilde(args[1], player, 'z');
            return copy;
        } else {
            String[] copy = new String[args.length];
            copy[2] = args[2];
            copy[0] = this.parseTilde(args[0], player, 'x');
            copy[1] = this.parseTilde(args[1], player, 'z');
            return copy;
        }
    }
    private String parseTilde(String s, Player player, Character arg) {
        if (s.length() == 1 && arg.toString().equalsIgnoreCase("x")) {
             return player.getLocation().getX() + "";
        } else if (s.length() == 1 && arg.toString().equalsIgnoreCase("z")) {
            return player.getLocation().getZ() + "";
        } else {
            double delta = Double.parseDouble(s.substring(1));
            if (arg.toString().equalsIgnoreCase("x")) {
                return (player.getLocation().getX() + delta) + "";
            } else {
                return (player.getLocation().getZ() + delta) + "";
            }
        }
    }
}
