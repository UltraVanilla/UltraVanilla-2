package world.ultravanilla.commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import world.ultravanilla.Colors;
import world.ultravanilla.UltraVanilla;

import java.util.List;

public abstract class UltraCommand implements TabExecutor {

    // Colors
    protected ChatColor accentColor = Colors.ACCENT;
    protected ChatColor playerColor = Colors.PLAYER;
    protected ChatColor numberColor = Colors.NUMBER;
    protected ChatColor baseColor = Colors.BASE;
    protected ChatColor errorColor = Colors.ERROR;
    protected ChatColor successColor = Colors.SUCCESS;

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

    public String getPermission() {
        return permission;
    }

    // Plugin instance
    protected UltraVanilla plugin;

    // Command info
    protected String name, permission;

    // Sender
    protected CommandSender sender;

    public UltraCommand(UltraVanilla instance, String commandName, String permissionNode) {
        this.plugin = instance;
        this.name = commandName;
        this.permission = permissionNode;
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
                .replace("&+", successColor + "")
        );
    }

    private void getArgs(String[] args) {
    }

    private void getSuggestions(String[] arg) {
    }

    protected String getUsageMessage(String arg, String... description) {
        return successColor + arg + baseColor + "\n  " + baseColor + String.join("\n  ", description);
    }

    protected String getDefaultUsageMessage(String arg) {
        return String.format("Could not find help for subcommand &!%s&:!", arg);
    }

    protected void sendHelp() {
        sendMessage(getUsage(null));
    }
}
