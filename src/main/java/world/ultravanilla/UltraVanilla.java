package world.ultravanilla;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import world.ultravanilla.commands.DisabledCommand;
import world.ultravanilla.commands.NicknameCommand;
import world.ultravanilla.commands.UltraCommand;
import world.ultravanilla.commands.UltraVanillaCommand;
import world.ultravanilla.util.Config;
import world.ultravanilla.util.JsonConfig;
import world.ultravanilla.util.TomlConfig;
import world.ultravanilla.util.YamlConfig;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.logging.Logger;

public class UltraVanilla extends JavaPlugin {

    private static Logger logger;
    private HashSet<UltraCommand> commands;

    public HashMap<String, Config> getConfigs() {
        return configs;
    }

    private HashMap<String, Config> configs;
    private DisabledCommand disabledCommand;
    private Palette palette;

    public HashSet<UltraCommand> getCommands() {
        return commands;
    }

    public File getServerRoot() {
        return serverRoot;
    }

    private File serverRoot;

    public void onEnable() {
        logger = getLogger();

        serverRoot = getDataFolder().getParentFile().getParentFile();

        initStructure();

        configs = new HashMap<>();
        registerConfig("config", "config/config.toml");
        registerConfig("palette", "config/palette.json");

        palette = new Palette(this);

        disabledCommand = new DisabledCommand(this, "disabled", "ultravanilla.command.disabled");

        commands = new HashSet<>();
        registerCommand(new UltraVanillaCommand(this, "ultravanilla", "ultravanilla.command.ultravanilla"));
        registerCommand(new NicknameCommand(this, "nick", "ultravanilla.command.nick"));
    }

    private void initStructure() {
        new File(serverRoot, "commands").mkdirs();
        new File(serverRoot, "config").mkdirs();
    }

    public void onDisable() {
        logger.info("bye!");
    }

    public UltraCommand getUltraCommand(String name) {
        for(UltraCommand command : commands) {
            if(command.getName().equals(name)) {
                return command;
            }
        }
        return null;
    }

    public void registerCommand(UltraCommand command) {
        commands.add(command);
        PluginCommand cmd = getCommand(command.getName());
        Objects.requireNonNull(cmd).setExecutor(command);
    }

    public void disableCommand(UltraCommand command) {
        Objects.requireNonNull(getCommand(command.getName())).setExecutor(disabledCommand);
    }

    public Palette getPalette() {
        return palette;
    }

    public Config registerConfig(String id, String path) {
        Config config;
        if(path.endsWith(".json")) {
            config = new JsonConfig(this, path, id);
        }
        else if(path.endsWith(".yml")) {
            config = new YamlConfig(this, path, id);
        }
        else if(path.endsWith(".toml")) {
            config = new TomlConfig(this, path, id);
        } else {
            getLogger().warning("Could not detect config type for: " + path);
            return null;
        }
        configs.put(id, config);
        return config;
    }

    public Config getConfig(String id) {
        return configs.get(id);
    }
}
