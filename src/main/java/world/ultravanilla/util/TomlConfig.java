package world.ultravanilla.util;

import org.tomlj.Toml;
import org.tomlj.TomlParseResult;
import world.ultravanilla.UltraVanilla;

import java.io.File;
import java.io.IOException;

public class TomlConfig extends Config {

    protected TomlParseResult tomlParseResult;

    public TomlConfig(UltraVanilla uv, String path, String id) {
        super(uv, path, id);
        reload();
    }

    public TomlParseResult getTomlParseResult() {
        return tomlParseResult;
    }

    @Override
    public void reload() {
        super.reload();
        try {
            tomlParseResult = Toml.parse(new File(path).toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveConfig() {
        // TODO: Unimplemented
        uv.getLogger().warning("Could not save TOML config: " + id + "\nThis feature is not yet implemented!");
    }

    @Override
    public boolean hasKey(String key) {
        return tomlParseResult.contains(key);
    }
}
