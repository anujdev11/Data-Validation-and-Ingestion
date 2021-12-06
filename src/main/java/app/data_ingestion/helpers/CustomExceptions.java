package app.data_ingestion.helpers;

public class CustomExceptions {

    private CustomExceptions() {
        throw new IllegalStateException("Helper Class");
    }

    public static class EmptyFileException extends Exception {

        public EmptyFileException() {
            super();
        }

        public EmptyFileException(String message) {
            super(message);
        }
    }

}
