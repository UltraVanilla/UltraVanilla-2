package world.ultravanilla.commands;

import java.util.Collections;
import java.util.List;

public class DisabledCommand extends UltraCommand {

    public DisabledCommand(String name, String permission) {
        super(name, permission);
    }

    @Override
    protected boolean onCommand(String[] args) {
        sendError("Command disabled.");
        return true;
    }

    @Override
    protected List<String> onTabComplete(String[] args) {
        return Collections.emptyList();
    }

    @Override
    public String getUsage(String arg) {
        return "";
    }
}
