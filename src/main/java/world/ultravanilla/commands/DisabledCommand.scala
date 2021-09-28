package world.ultravanilla.commands

import net.md_5.bungee.api.ChatColor
import world.ultravanilla.UltraVanilla

import java.util.Collections
import java.util

class DisabledCommand(val instance: UltraVanilla, val commandName: String, val permissionNode: String) extends UltraCommand(instance, commandName, permissionNode) {
    errorColor = ChatColor.of("#ff0000")
    override protected def onCommand(args: Array[String]): Boolean = {
        sendError("Command disabled.")
        true
    }
    override protected def onTabComplete(args: Array[String]): util.List[String] = Collections.emptyList
    override def getUsage(arg: String) = ""
}
