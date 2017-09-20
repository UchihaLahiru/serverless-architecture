package object_storage;

import io.minio.MinioClient;
import io.minio.errors.*;
import launch.ConfigConstantKeys;
import launch.Launcher;
import org.apache.log4j.Logger;
import org.xmlpull.v1.XmlPullParserException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class ObjectStorageImpl implements ObjectStorage {
    private static final Logger logger = Logger.getLogger(ObjectStorageImpl.class);

    public final static String OBJ_CONNECTION_URL=Launcher.getStringValue(ConfigConstantKeys.CONFIG_OBJ_SERVER_CONNECTION_URL);
    public final static String OBJ_CONNECTION_ACCESSKEY=Launcher.getStringValue(ConfigConstantKeys.CONFIG_OBJ_SERVER_CONNECTION_ACCESSKEY);
    public final static String OBJ_CONNECTION_SECRETKEY=Launcher.getStringValue(ConfigConstantKeys.CONFIG_OBJ_SERVER_CONNECTION_SECRETKEY);

    private   MinioClient minioClient ;
    private static ObjectStorageImpl instance=null;


    private ObjectStorageImpl(){
        try {
            minioClient=new MinioClient(OBJ_CONNECTION_URL, OBJ_CONNECTION_ACCESSKEY, OBJ_CONNECTION_SECRETKEY);
        } catch (InvalidEndpointException e) {
            e.printStackTrace();
        } catch (InvalidPortException e) {
            e.printStackTrace();
        }
    }

    public static ObjectStorageImpl getInstance(){
        if (instance==null){
            instance = new ObjectStorageImpl();
        }
        return instance;
    }


    @Override
    public  void storeOBJ(String bucket,String objName,String file){

        boolean isExist = false;
        try {
            isExist = minioClient.bucketExists(bucket);
            if(isExist) {
                logger.info("Bucket already exists.");
            } else {
                // Make a new bucket called asiatrip to hold a zip file of photos.
                minioClient.makeBucket(bucket);
                logger.info("Bucket is created !");
            }
            minioClient.putObject(bucket,objName, Launcher.TMP_FILE_LOCATION+file);
        } catch (InvalidBucketNameException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InsufficientDataException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoResponseException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (InternalException e) {
            e.printStackTrace();
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        } catch (RegionConflictException e) {
            e.printStackTrace();
        }

    }

    @Override
    public InputStream getObj(String bucket, String objName) {
        InputStream inputStream = null;
        try {
           inputStream= minioClient.getObject(bucket,objName);
        } catch (InvalidBucketNameException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InsufficientDataException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoResponseException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (InternalException e) {
            e.printStackTrace();
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    public static void main(String[] args) throws Exception {

        ObjectStorage objectStorage = ObjectStorageImpl.getInstance();
        InputStream stream = objectStorage.getObj("asiatrip","lambda.jar");
        byte[] buf = new byte[1000];
        int bytesRead;
        FileOutputStream fileOutputStream=new FileOutputStream(Launcher.TMP_FILE_LOCATION+"lambda.jar");
        while ((bytesRead = stream.read(buf, 0, buf.length)) >= 0) {
          fileOutputStream.write(buf,0,bytesRead);
        }
    }


}
