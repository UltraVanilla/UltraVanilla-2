package world.ultravanilla;

import net.md_5.bungee.api.ChatColor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import world.ultravanilla.util.JsonConfig;

public class Palette {

    private JsonConfig config;
    private JSONObject colors;

    public Palette(UltraVanilla uv) {
        config = (JsonConfig) uv.getConfig("palette");
        colors = config.getJsonObject();
    }

    public String getUxDefault(String id) {
        return getHex("ux:default." + id);
    }

    public String getHex(String id) {
        Object result = colors.get(id);
        if(result == null) {
            return "#ffffff";
        }
        return "#" + result;
    }

    public ChatColor getColor(String id) {
        return ChatColor.of(getHex(id));
    }
}
