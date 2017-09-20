package object_storage;

import io.minio.Result;
import io.minio.messages.Upload;

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

    /**
     *
     * @param bucket Name should be at least 3 characters long
     * @param objName
     * @return InputStream obj which holds the obj
     */
    InputStream getObj(String bucket,String objName);

    /**
     * Returns List of incomplete uploads
     * @param bucket
     * @param objName
     * @return
     */
    boolean isIncompleteUpload(String bucket,String objName);
}
