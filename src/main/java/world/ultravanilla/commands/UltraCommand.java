package world.ultravanilla.commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import world.ultravanilla.Colors;

import java.util.List;

public abstract class UltraCommand implements TabExecutor {

    // Colors
    protected ChatColor accentColor = Colors.ACCENT;
    protected ChatColor playerColor = Colors.PLAYER;
    protected ChatColor numberColor = Colors.NUMBER;
    protected ChatColor baseColor = Colors.BASE;
    protected ChatColor errorColor = Colors.ERROR;

    public ChatColor getAccentColor() {
        return accentColor;
    }

    public ChatColor getPlayerColor() {
        return playerColor;
    }

    public ChatColor getNumberColor() {
        return numberColor;
    }

    public ChatColor getBaseColor() {
        return baseColor;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPermission() {
        return permission;
    }

    // Command info
    protected String name, description, permission;

    // Sender
    protected CommandSender sender;

    public UltraCommand(String name, String permission) {
        this.name = name;
        this.permission = permission;
    }

    private UltraCommand() { }

    // Abstract methods

    /**
     * onCommand wrapper
     * @param args The arguments passed from the CommandSender
     * @return True if the command was a major success, False otherwise
     */
    protected abstract boolean onCommand(String[] args);

    /**
     * onTabComplete wrapper for effortless command
     * @param args The arguments the CommandSender has typed so far
     * @return A list of suggestions to give for tab completion
     */
    protected abstract List<String> onTabComplete(String[] args);

    /**
     * Get usage for an argument
     * @param arg The argument to get usage for
     * @return The usage for the argument
     */
    public abstract String getUsage(String arg);

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        this.sender = sender;
        if(sender instanceof Player && !sender.hasPermission(permission)) {
            sendPermissionMessage("execute this command");
        }
        return onCommand(args);
    }

    protected void sendError(String errorMessage) {
        sendMessage(errorColor + errorMessage);
    }

    protected void sendPermissionMessage(String attemptedAction) {
        sendMessage(baseColor + "You do not have permission to " + errorColor + attemptedAction + baseColor + ".");
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        this.sender = sender;
        return onTabComplete(args);
    }

    protected void sendMessage(String message) {
        message = ChatColor.translateAlternateColorCodes('&', message);
        sender.sendMessage(baseColor + message
                .replace("&@", playerColor + "")
                .replace("&:", baseColor + "")
                .replace("&.", accentColor + "")
                .replace("&#", numberColor + "")
                .replace("&!", errorColor + "")
        );
    }

    private void getArgs(String[] args) {
    }

    private void getSuggestions(String[] arg) {
    }
}
