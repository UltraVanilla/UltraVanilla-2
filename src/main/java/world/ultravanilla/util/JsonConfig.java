package world.ultravanilla.util;

import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import world.ultravanilla.UltraVanilla;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class JsonConfig extends Config {

    private JSONObject jsonObject;
    private final JSONParser parser;

    public JsonConfig(UltraVanilla uv, String path, String id) {
        super(uv, path, id);
        parser = new JSONParser();
        reload();
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    @Override
    public void reload() {
        super.reload();
        try {
            jsonObject = (JSONObject) parser.parse(new String(Files.readAllBytes(file.toPath())));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveConfig() {
        try {
            Files.writeString(file.toPath(), jsonObject.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean hasKey(String key) {
        return jsonObject.containsKey(key);
    }
}
