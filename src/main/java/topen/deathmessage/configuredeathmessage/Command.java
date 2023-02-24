package topen.deathmessage.configuredeathmessage;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.entity.EntityDamageEvent;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Command implements CommandExecutor {

    private static void sendMessage(String message){
        sender.sendMessage(prefix + message);
    }
    private static CommandSender sender;
    private static final String prefix = "[Topen] ";
    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        Command.sender = sender;
        switch (args[0]){
            case "set" -> {
                Configure.setDeathMessage(args[1], String.join(" ", Arrays.copyOfRange(args, 2, args.length)));
                sendMessage("DeathMessage("+args[1]+") set to " + String.join(" ", Arrays.copyOfRange(args, 2, args.length)));
            }
            case "get" -> sendMessage("DeathMessage("+args[1]+") is " +Configure.getDeathMessage(args[1]));
            case "reset" -> {
                Configure.setDeathMessage(args[1], "");
                sendMessage("DeathMessage("+args[1]+") reset");
            }
            case "reload" ->{
                try {
                    Configure.setup();
                    sendMessage("reload success");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return true;
    }
}
