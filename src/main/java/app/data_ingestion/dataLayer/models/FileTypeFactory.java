package app.data_ingestion.dataLayer.models;

public class FileTypeFactory {

    private static FileTypeFactory fileTypeFactory = new FileTypeFactory();
    private FileTypeFactory(){}

    
    /** 
     * @return FileTypeFactory
     */
    public static FileTypeFactory getInstance(){
        return fileTypeFactory;
    }
    
    /** 
     * @return FileType
     */
    public FileType createFileType(){
        return new FileType();
    }
}
