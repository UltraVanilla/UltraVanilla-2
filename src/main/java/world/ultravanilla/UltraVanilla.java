package world.ultravanilla;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class UltraVanilla extends JavaPlugin {

    public static Logger logger;

    public void onEnable() {
        logger = getLogger();
        logger.info("epic win?");
    }

    public void onDisable() {
        logger.info("bye!");
    }
}
