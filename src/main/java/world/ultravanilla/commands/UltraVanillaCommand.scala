package world.ultravanilla.commands

import world.ultravanilla.UltraVanilla

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
                sendMessage(f"&.${plugin.getDescription.getName}&:-&#${plugin.getDescription.getVersion}")
            } else if (arg.equals("reload")) {
                sendMessage("&+Reloaded configs!")
            }
        } else if (args.length == 2) if (args(0).matches("-?\\?|h(elp)?")) sendMessage(getUsage(args(1)))
        true
    }

    override protected def onTabComplete(args: Array[String]): Null = null
}
