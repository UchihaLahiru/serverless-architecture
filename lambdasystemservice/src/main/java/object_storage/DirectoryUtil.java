package object_storage;

import launch.Launcher;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DirectoryUtil {
    private static final Logger logger = Logger.getLogger(DirectoryUtil.class);

    public final static String BASE_DIR= Launcher.TMP_FILE_LOCATION;

    public static Path createDir(String name){
        Path path = Paths.get(BASE_DIR+name);
        if (!Files.exists(path)) {
            try {
               path= Files.createDirectories(path);
               logger.info(name+" Dir is created !");
            } catch (IOException e) {
               logger.error("fail to create directory",e);
            }
        }
        return path;
    }

    public static boolean deleteDir(String name){
        Path path = Paths.get(BASE_DIR+name);
        boolean result=false;

        try {
            result= Files.deleteIfExists(path);
            logger.info(name+" Dir is deleted !");
        } catch (IOException e) {
            logger.error("fail to delete directory",e);
        }
        return result;
    }

}
