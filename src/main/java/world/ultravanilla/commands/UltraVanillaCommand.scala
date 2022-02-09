package world.ultravanilla.commands

import world.ultravanilla.UltraVanilla
import world.ultravanilla.util.Config

import javax.annotation.Nullable

class UltraVanillaCommand(val instance: UltraVanilla, val commandName: String, val permissionNode: String)
    extends UltraCommand(instance, commandName, permissionNode) {
    override def getUsage(@Nullable arg: String): String = {
        if (arg == null) List(getUsage("help"), getUsage("info"), getUsage("reload")).mkString("\n\n")
        else if (arg.toLowerCase.matches("-?h(elp)?")) {
            getUsageMessage(
                "help [subcommand]",
                "Shows help on subcommand.",
                "All subcommands will be listed if none is specified."
            )
        } else if (arg.equalsIgnoreCase("info")) {
            getUsageMessage("info", "Get info about the plugin such as", "∙ version", "∙ weed toked", "∙ etc")
        } else if (arg.equalsIgnoreCase("reload")) {
            getUsageMessage("reload [config]", "Reloads the config specified", "main config by default")
        } else getDefaultUsageMessage(arg)
    }

    override protected def onCommand(args: Array[String]): Boolean = {
        if (args.length == 0) sendHelp()
        else if (args.length == 1) {
            val arg = args(0).toLowerCase
            if (arg.matches("-?\\?|h(elp)?")) {
                sendHelp();
            } else if (arg.equals("info")) {
                sendMessage(f"&.${uv.getDescription.getName}&:-&#${uv.getDescription.getVersion}")
            } else if (arg.equals("reload")) {
                uv.getConfigs.forEach((_, v) => v.reload())
                sendMessage("&+Reloaded configs!")
            }
        } else if (args.length == 2){
            if (args(0).matches("-?\\?|h(elp)?")) sendMessage(getUsage(args(1)))
            else {
                val id = args(1)
                val config = uv.getConfig(id)
                if(config == null) {
                    sendError("Config not found: " + id)
                    return true
                }
                if(args(0).equals("reload")) {
                    config.reload()
                    sendMessage("Reloaded config: " + id)
                }
                else if(args(0).equals("default")) {
                    config.copyDefaults()
                    config.reload()
                    sendMessage("Copied default config: " + id)
                }
            }
        }
        true
    }

    override protected def onTabComplete(args: Array[String]): Null = null
}
