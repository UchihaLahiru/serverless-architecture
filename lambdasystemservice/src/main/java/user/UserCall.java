package user;

/**
 * Created by deshan on 8/24/17.
 */
public interface UserCall {
    void createFunction(String functionName, String file, String language, String user);

    void deleteFunction(String functionName, String user);

    void listFunction(String user);

    void invokeFunction(String functionName, boolean blocking, String[] args);

    void updateFunction(String functionName, String file);

//    refer openwhisk ibm

}
