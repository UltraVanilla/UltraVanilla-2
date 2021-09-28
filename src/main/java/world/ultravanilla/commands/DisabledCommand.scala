package world.ultravanilla.commands

import java.util.Collections
import java.util

class DisabledCommand(val name: String, val permission: String) extends UltraCommand(name, permission) {
    override protected def onCommand(args: Array[String]): Boolean = {
        sendError("Command disabled.")
        true
    }
    override protected def onTabComplete(args: Array[String]): util.List[String] = Collections.emptyList
    override def getUsage(arg: String) = ""
}
