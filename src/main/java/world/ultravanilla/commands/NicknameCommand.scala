package world.ultravanilla.commands

import world.ultravanilla.UltraVanilla

import java.util

class NicknameCommand(val instance: UltraVanilla, val commandName: String, val permissionNode: String) extends UltraCommand(instance, commandName, permissionNode) {

    override protected def onCommand(args: Array[String]): Boolean = {
        sendError(getString("help.desc"))
        true
    }

    override protected def onTabComplete(args: Array[String]): util.List[String] = {
        null
    }
}
