package topen.deathmessage.configuredeathmessage;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Listener implements org.bukkit.event.Listener {

    private static JsonObject en;
    private static JsonObject en_o;
    private static JsonObject ko_kr;


    public static void setup() {
        en = JsonParser.parseString(Resources.getEN()).getAsJsonObject();
        en_o = JsonParser.parseString(Resources.getEN_O()).getAsJsonObject();
        ko_kr = JsonParser.parseString(Resources.getKO_KR()).getAsJsonObject();
    }
    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        if(e.getDeathMessage() == null){
            return;
        }
        en.asMap().forEach((key, value) -> {
           Pattern pattern = Pattern.compile(value.getAsString());
           Matcher matcher = pattern.matcher(e.getDeathMessage());
           if(matcher.find()) {
               String var = en_o.get(key).getAsString();
               String[] split = var.split(" ");
               String[] args = new String[3];
               for (int i = 0; i < split.length && i < matcher.groupCount(); i++) {
                   args[i] = matcher.group(i+1);
               }
               String deathMessage = Configure.getDeathMessage(key);
               for (int i = 0; i < args.length; i++) {
                   if(args[i] == null) args[i] = "";
                   String regex = ("\\{" + (i + 1) + "}").substring(1);
                   deathMessage = deathMessage.replace(regex, args[i]);
               }
               e.setDeathMessage(deathMessage);
           }
        });
    }
}
