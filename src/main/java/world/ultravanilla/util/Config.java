package world.ultravanilla.util;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import world.ultravanilla.UltraVanilla;

import java.io.File;

public abstract class Config {

    protected String path;
    protected String id;
    protected File file;
    protected Class<?> root;
    protected UltraVanilla uv;

    public Config(UltraVanilla uv, String path, String id) {

        // Set the plugin
        this.uv = uv;

        // Set the path
        this.path = path;

        // Set the root
        this.root = UltraVanilla.class;

        // Create a strings File reference
        file = new File(uv.getServerRoot(), path);

        // Set the id
        this.id = id;

        System.out.printf("Registered a new config \"%s\": %s\n", id, file.getAbsolutePath());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void copyDefaults() {
        IOUtil.copyDefaults(uv.getServerRoot(), path, true);
    }

    public void reload() {
        if (!file.exists()) {
            IOUtil.copyDefaults(uv.getServerRoot(), path);
        }
        uv.getLogger().info("Reloaded config: " + id);
    }
    public abstract void saveConfig();
    public abstract boolean hasKey(String key);
}
