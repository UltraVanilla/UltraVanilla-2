package world.ultravanilla.util;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import world.ultravanilla.UltraVanilla;

import java.io.IOException;

public class YamlConfig extends Config {

    private final YamlConfiguration yamlConfiguration;

    public YamlConfig(UltraVanilla uv, String path, String id) {
        super(uv, path, id);
        yamlConfiguration = new YamlConfiguration();
        reload();
    }

    /**
     * Get the YamlConfiguration instance
     *
     * @return The YamlConfiguration instance
     */
    public YamlConfiguration getYamlConfiguration() {
        return yamlConfiguration;
    }

    /**
     * Reload the strings object with the info written on file. Copy defaults from the jar if needed
     */
    public void reload() {
        super.reload();
        try {
            yamlConfiguration.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Save the YamlConfiguration to the file
     */
    public void saveConfig() {
        try {
            yamlConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if the configuration contains the specified key.
     *
     * @param key The key to search for
     * @return Whether the configuration has the specified key
     */
    public boolean hasKey(String key) {
        return yamlConfiguration.contains(key);
    }
}