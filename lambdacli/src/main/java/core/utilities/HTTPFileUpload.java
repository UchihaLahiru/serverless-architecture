package core.utilities;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.*;

import static core.utilities.FileType.*;

public class HTTPFileUpload extends HTTPUtilities {

    private static final MediaType MEDIA_TYPE_JAR
            = MediaType.parse("application/java-archive");
    private static final String FILE_UPLOAD_URL = "http://localhost:8080/rest";
    public static final String FUNCTION_IS_UPLOADED = "Function is uploaded";
    public static final String FUNCTION_UPLOADING_FAILED = "Function uploading failed";
    public static final String FILE_PARAMETER_NAME = "file";
    public static final String UNKNOWN_FILE_TYPE = "Unknown file type !";
    public static final String FILE_TYPE_IS_NOT_COMPATIBLE = "File type is not compatible";
    public static final String DOT_REGEX = "\\.";


    HTTPFileUpload() {
        super(FILE_UPLOAD_URL);
    }

    private String uploadFile(FileType fileType, File file) {
        if (!file.isFile()) {
            return "Cannot find the file !";
        }
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        RequestBody requestBody = null;
        switch (fileType) {
            case JAVA: {
                if (!isFileTypeCorrect(file.getName(), JAVA)) {
                    return FILE_TYPE_IS_NOT_COMPATIBLE;
                }
                requestBody = builder.addFormDataPart(FILE_PARAMETER_NAME, file.getName(),
                        RequestBody.create(MEDIA_TYPE_JAR, file))
                        .build();
                break;
            }
            case PYTHON: {
                if (!isFileTypeCorrect(file.getName(), PYTHON)) {
                    return FILE_TYPE_IS_NOT_COMPATIBLE;
                }
                requestBody = builder.addFormDataPart(FILE_PARAMETER_NAME, file.getName(),
                        RequestBody.create(MEDIA_TYPE_JAR, file))
                        .build();
                break;
            }
            case NODE_JS: {
                if (!isFileTypeCorrect(file.getName(), NODE_JS)) {
                    return FILE_TYPE_IS_NOT_COMPATIBLE;
                }
                requestBody = builder.addFormDataPart(FILE_PARAMETER_NAME, file.getName(),
                        RequestBody.create(MEDIA_TYPE_JAR, file))
                        .build();
                break;
            }
            case XML: {
                if (!isFileTypeCorrect(file.getName(), XML)) {
                    return FILE_TYPE_IS_NOT_COMPATIBLE;
                }
                requestBody = builder.addFormDataPart(FILE_PARAMETER_NAME, file.getName(),
                        RequestBody.create(MEDIA_TYPE_JAR, file))
                        .build();
                break;
            }
            default: {
                return UNKNOWN_FILE_TYPE;
            }
        }


        Response response = sendPost(requestBody);

        return response.isSuccessful() ? FUNCTION_IS_UPLOADED : FUNCTION_UPLOADING_FAILED;
    }

    private boolean isFileTypeCorrect(String fileName, FileType fileType) {
        String[] array = fileName.split(DOT_REGEX);
        return array[array.length - 1].equals(fileType.getExtention());
    }

    public static void main(String[] args) {
        File file = new File("pom.xml");
        System.out.println(new HTTPFileUpload().uploadFile(XML,file));
        //uploadFile(FileType.JAVA);
    }
}
