package topen.deathmessage.configuredeathmessage;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.bukkit.event.entity.EntityDamageEvent;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Configure {
    private static final String path = Main.getDataFolder.getPath() + File.separator + "deathMessage.yml";

    private static final Map<String, String> causeStringMap = new HashMap<>();
    public static void setup() throws IOException {
        Main.getDataFolder.mkdir();
        File file = new File(path);
        if (file.createNewFile()) {
            Map<String, JsonElement> stringJsonElementMap = JsonParser.parseString(Resources.getEN_OR()).getAsJsonObject().asMap();
            for (String s : stringJsonElementMap.keySet()) {
                causeStringMap.put(s, stringJsonElementMap.get(s).getAsString());
            }
            save();
        }else{
            causeStringMap.clear();
            causeStringMap.putAll(yamlFileToMap(file.getPath()));
        }
    }

    public static void save() throws IOException{
        mapToYamlFile(causeStringMap, path);
    }
    public static String getDeathMessage(String cause){
        return causeStringMap.get(cause);
    }

    public static void setDeathMessage(String cause, String message){
        causeStringMap.put(cause, message);
    }
    public static void mapToYamlFile(Map<String, String> map, String filePath) throws IOException {
        Yaml yaml = new Yaml();
        String yamlData = yaml.dump(map);
        FileWriter writer = new FileWriter(filePath);
        writer.write(yamlData);
        writer.close();
    }

    public static Map<String, String> yamlFileToMap(String filePath) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(filePath));
        String yamlData = new String(encoded);
        Yaml yaml = new Yaml();
        Map<String, String> map = yaml.load(yamlData);
        return map;
    }

}
