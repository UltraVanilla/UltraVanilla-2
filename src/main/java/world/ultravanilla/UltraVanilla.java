package world.ultravanilla;

import org.bukkit.command.Command;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import world.ultravanilla.commands.DisabledCommand;
import world.ultravanilla.commands.UltraCommand;
import world.ultravanilla.commands.UltraVanillaCommand;

import java.util.HashSet;
import java.util.logging.Logger;

public class UltraVanilla extends JavaPlugin {

    public static Logger logger;
    public HashSet<UltraCommand> commands;
    private UltraCommand disabledCommand;

    public void onEnable() {
        logger = getLogger();

        disabledCommand = new DisabledCommand("disabled", "ultravanilla.command.disabled");

        commands = new HashSet<>();
        registerCommand(new UltraVanillaCommand("ultravanilla", "ultravanilla.command.ultravanilla"));
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
        cmd.setExecutor(command);
    }

    public void disableCommand(UltraCommand command) {
        getCommand(command.getName()).setExecutor(disabledCommand);
    }
}
