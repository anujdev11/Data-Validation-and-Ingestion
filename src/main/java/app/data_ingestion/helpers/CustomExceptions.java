package app.data_ingestion.helpers;

public class CustomExceptions {

    public static class EmptyFileException extends Exception {

        public EmptyFileException() {
            super();
        }

        public EmptyFileException(String message) {
            super(message);
        }
    }

}
