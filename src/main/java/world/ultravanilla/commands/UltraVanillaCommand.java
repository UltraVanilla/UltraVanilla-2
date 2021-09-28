package world.ultravanilla.commands;

import org.bukkit.entity.Player;
import world.ultravanilla.UltraVanilla;

import javax.annotation.Nullable;
import java.util.List;

public class UltraVanillaCommand extends UltraCommand {

    public UltraVanillaCommand(UltraVanilla instance, String name, String permission) {
        super(instance, name, permission);
    }

    @Override
    protected boolean onCommand(String[] args) {
        //TODO: make subcommands more automated/generated
        if(args.length == 0) {
            sendHelp();
        } else if(args.length == 1) {
            if(args[0].matches("-?\\?|h(elp)?")) {
                sendHelp();
            }
            else if(args[0].equalsIgnoreCase("info")) {
                sendMessage(String.format("&.%s &:- &#%s", "UltraVanilla-2", "2.0.0-SNAPSHOT"));
            }
            else if(args[0].equalsIgnoreCase("reload")) {
                //TODO: reload each config
                sendMessage("Reloaded configs!");
            }
        } else if(args.length == 2) {
            if(args[0].matches("-?\\?|h(elp)?")) {
                sendMessage(getUsage(args[1]));
            }
        }
        return true;
    }

    @Override
    protected List<String> onTabComplete(String[] args) {
        return null;
    }

    @Override
    public String getUsage(@Nullable String arg) {
        //TODO: actually make this more automated/generated
        if(arg == null) {
            return String.join("\n\n", new String[]{getUsage("help"), getUsage("info"), getUsage("reload")});
        }
        else if(arg.toLowerCase().matches("-?h(elp)?")) {
            return getUsageMessage("help [subcommand]", "Shows help on subcommand.", "All subcommands will be listed if none is specified.");
        }
        else if(arg.equalsIgnoreCase("info")) {
            return getUsageMessage("info", "Get info about the plugin such as", "∙ version", "∙ weed toked", "∙ etc");
        }
        else if(arg.equalsIgnoreCase("reload")) {
            return getUsageMessage("reload [config]", "Reloads the config specified", "main config by default");
        } else {
            return getDefaultUsageMessage(arg);
        }
    }
}
