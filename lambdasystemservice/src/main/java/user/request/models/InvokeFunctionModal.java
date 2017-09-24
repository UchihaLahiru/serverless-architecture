package user.request.models;

import java.util.ArrayList;

/**
 * Created by deshan on 9/24/17.
 */
public class InvokeFunctionModal {
    String domainName;
    boolean blocking;
    ArrayList<String > args;

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public boolean isBlocking() {
        return blocking;
    }

    public void setBlocking(boolean blocking) {
        this.blocking = blocking;
    }

    public ArrayList<String> getArgs() {
        return args;
    }

    public void addArgs(String data){
        this.args.add(data);
    }



}
