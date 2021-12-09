package app.data_ingestion.dataLayer.models;

public class FileTypeFactory {

    private static FileTypeFactory fileTypeFactory = new FileTypeFactory();

    private FileTypeFactory() {
    }

    /**
     * return instance of this class
     * @return FileTypeFactory
     */
    public static FileTypeFactory getInstance() {
        return fileTypeFactory;
    }

    /**
     * create a new instance of FileType and return it
     * @return FileType
     */
    public FileType createFileType() {
        return new FileType();
    }
}
