package DataAccess;

/**
 * Created by brandonderbidge on 5/22/17.
 */

public class DatabaseException extends Exception {

        public DatabaseException(String s) {
            super(s);
        }

        public DatabaseException(String s, Throwable throwable) {
            super(s, throwable);
        }
}

