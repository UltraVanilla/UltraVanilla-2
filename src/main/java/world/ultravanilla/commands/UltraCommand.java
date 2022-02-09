package world.ultravanilla.commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.tomlj.Toml;
import org.tomlj.TomlParseResult;
import world.ultravanilla.UltraVanilla;
import world.ultravanilla.util.IOUtil;
import world.ultravanilla.util.TomlConfig;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Supplier;

public abstract class UltraCommand implements TabExecutor {

    public String getName() {
        return name;
    }

    public String getPermission() {
        return permission;
    }

    // UltraVanilla instance
    protected UltraVanilla uv;

    // Command info
    protected String name, permission;

    // Sender
    protected CommandSender sender;

    // Config
    protected TomlConfig config;

    public UltraCommand(UltraVanilla instance, String commandName, String permissionNode) {
        this.uv = instance;
        this.name = commandName;
        this.permission = permissionNode;

        config = (TomlConfig) uv.registerConfig("command:" + commandName, "commands/" + commandName + ".toml");
    }

    public String getDefaultColor(String id) {
        return uv.getPalette().getUxDefault(id);
    }

    public ChatColor getColor(String id) {
        return ChatColor.of(config.getTomlParseResult().getString("color." + id, () -> getDefaultColor(id)));
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
    public String getUsage(String arg) {
        // todo: load from toml
        return null;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        this.sender = sender;
        if(sender instanceof Player && !sender.hasPermission(permission)) {
            sendPermissionMessage("execute this command");
        }
        return onCommand(args);
    }

    protected void sendError(String errorMessage) {
        sendMessage("&!" + errorMessage);
    }

    protected void sendPermissionMessage(String attemptedAction) {
        sendMessage("You do not have permission to &!" + attemptedAction + "&:.");
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        this.sender = sender;
        return onTabComplete(args);
    }

    protected String getString(String key) {
        return config.getTomlParseResult().getString(key, () -> key);
    }

    protected void sendMessage(String message) {
        message = ChatColor.translateAlternateColorCodes('&', message);
        sender.sendMessage(getColor("base") + message
                .replace("&@", getColor("player") + "")
                .replace("&:", getColor("base") + "")
                .replace("&.", getColor("accent") + "")
                .replace("&#", getColor("number") + "")
                .replace("&!", getColor("error") + "")
                .replace("&+", getColor("success") + "")
        );
    }

    private void getArgs(String[] args) {
    }

    private void getSuggestions(String[] arg) {
    }

    protected String getUsageMessage(String arg, String... description) {
        return "&+" + arg + "&:\n  &:" + String.join("\n  ", description);
    }

    protected String getDefaultUsageMessage(String arg) {
        return String.format("Could not find help for subcommand &!%s&:!", arg);
    }

    protected void sendHelp() {
        sendMessage(getUsage(null));
    }
}
