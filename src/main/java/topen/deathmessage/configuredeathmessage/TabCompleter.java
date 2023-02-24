package topen.deathmessage.configuredeathmessage;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class TabCompleter implements org.bukkit.command.TabCompleter {

    public static JsonObject keySet = null;
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> ret = new ArrayList<>();
        switch (args.length) {
            case 1 -> Arrays.asList("set", "reset", "get", "reload").forEach(s -> {
                if(args[0].isBlank() || s.startsWith(args[0])) ret.add(s);
            });
            case 2 -> {
                if(Stream.of("set", "reset", "get").anyMatch(s -> s.equalsIgnoreCase(args[0]))){
                    if(keySet == null){
                        keySet = JsonParser.parseString(Resources.getEN()).getAsJsonObject();
                    }
                    ret.addAll(keySet.keySet().stream().filter(s -> s.startsWith(args[1])).toList());
                }
            }
        }
        return ret;
    }
}
