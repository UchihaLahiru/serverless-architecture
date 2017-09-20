package object_storage;

import java.io.InputStream;

public interface ObjectStorage {
    /**
     *
     * This method will store given file in the tmp file location in the obj server
     * @param bucket
     * @param objName
     * @param file file name
     */
    public  void storeOBJ(String bucket,String objName,String file);


    InputStream getObj(String bucket,String objName);
}
