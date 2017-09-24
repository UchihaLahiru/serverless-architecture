package user.request.models;

/**
 * Created by deshan on 9/24/17.
 */
public class CreateFunctionModal {
    String domainName;
    String filePath;
    String lang;
//    set user as headers
//    String user

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }




}
