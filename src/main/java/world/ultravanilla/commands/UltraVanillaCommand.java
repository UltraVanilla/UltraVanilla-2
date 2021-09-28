package world.ultravanilla.commands;

import org.bukkit.entity.Player;

import java.util.List;

public class UltraVanillaCommand extends UltraCommand {

    public UltraVanillaCommand(String name, String permission) {
        super(name, permission);
    }

    @Override
    protected boolean onCommand(String[] args) {
        //TODO: make subcommands more automated/generated
        if(args.length == 0) {
            sendMessage(getUsage(""));
        } else if(args.length == 1) {
            if(args[0].matches("-?\\?|h(elp)?")) {
                sendMessage(getUsage(""));
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
    public String getUsage(String arg) {
        //TODO: actually make this more automated/generated
        if(arg.equalsIgnoreCase("")) {
            return String.join("\n", new String[]{"--- Subcommands ---", getUsage("help"), getUsage("info"), getUsage("reload")});
        }
        else if(arg.toLowerCase().matches("-?h(elp)?")) {
            return "&.help [subcommand] \n  &:&oshow help on subcommand, this output by default";
        }
        else if(arg.equalsIgnoreCase("info")) {
            return "&.info \n  &:&oget info about the plugin";
        }
        else if(arg.equalsIgnoreCase("reload")) {
            return "&.reload [config] \n  &:&oreloads the config specified, main config by default";
        } else {
            return String.format("Could not find help for subcommand &!%s&:!", arg);
        }
    }
}
