package com.qifeng.will.web.view.response;


/**
 * 文件处理响应
 * @author huyang
 * @date 2016年2月1日 
 *
 */
public class FileResponse extends BaseResponse{

    /** 
     * @Fields serialVersionUID : 
     */ 
    private static final long serialVersionUID = -5565988730605716918L;
    
    public FileResponse(boolean success){
        super(success);
    }

    public FileResponse(boolean success, String msg) {
        super(success, msg);
    }
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 文件路径
     */
    private String filePath;
    
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public String getFilePath() {
        return filePath;
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

}
