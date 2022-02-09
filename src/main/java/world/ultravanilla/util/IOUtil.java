package world.ultravanilla.util;

import world.ultravanilla.UltraVanilla;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class IOUtil {
    /**
     * If the file doesn't exist, copy the original file in the .jar into the new file.
     *
     * @param dataFolder The plugin's data folder
     * @param path       The file to check
     */
    public static void copyDefaults(File dataFolder, String path) {
        copyDefaults(dataFolder, path, false);
    }

    /**
     * If the file doesn't exist, copy the original file in the .jar into the new file.
     *
     * @param dataFolder The plugin's data folder
     * @param path       The file to check
     * @param overwrite  Whether to overwrite
     */
    public static void copyDefaults(File dataFolder, String path, boolean overwrite) {
        File file = new File(dataFolder, path);
        boolean exists = file.exists();
        if (overwrite || !exists) {
            if (!exists) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            InputStream fis = UltraVanilla.class.getResourceAsStream("/" + path);
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                byte[] buf = new byte[1024];
                int i;
                while ((i = fis.read(buf)) != -1) {
                    fos.write(buf, 0, i);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fis != null) {
                        fis.close();
                    }
                    if (fos != null) {
                        fos.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
