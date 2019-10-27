package android.util;

/**
 * "Mock" implementation of the Android Log class to avoid static mocking for tests.
 * This class is only used within the test scope, so it does no harm in production.
 */
public class Log {

    // TODO: 27.10.2019 If another Log method is needed, it should be implemented here

    public static int d(String logTag, String message){
        return 0;
    }
}
