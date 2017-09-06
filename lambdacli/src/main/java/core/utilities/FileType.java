package core.utilities;

public enum FileType {
    JAVA("jar",0,"jar"),NODE_JS("nodejs",1,"js"),PYTHON("python",2,"py"),XML("xml",3,"xml");
    private String fileType;
    private int code;
   private String extention;
    FileType(String fileType,int code,String extention){
        this.fileType=fileType;
        this.code=code;
        this.extention = extention;
    }

    public String getFileType(){
        return fileType;
    }
    public int getCode(){
        return code;
    }
public String getExtention(){
        return extention;
}

}
