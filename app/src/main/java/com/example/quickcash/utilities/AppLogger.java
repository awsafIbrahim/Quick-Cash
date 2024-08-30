package com.example.quickcash.utilities;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Custom logging class implemented as a singleton.
 */
public class AppLogger {
    public static final int INFO = 1;
    public static final int DEBUG = 2;
    public static final int ERROR = 3;
    public static final int USER_ALERT_ERROR = 4;

    private static AppLogger instance;

    /**
     * Returns an instance of the AppLogger.
     * @return  An instance of the AppLogger.
     */
    public static AppLogger getInstance() {
        if (instance == null) {
            instance = new AppLogger();
        }
        return instance;
    }

    private LoggerChain chain;

    private AppLogger() {
        chain = new InfoLogger(new DebugLogger(new ErrorLogger(new UserAlertErrorLogger(null))));
    }

    /**
     * Creates a log message for logCat.
     * Loglevel specifies the severity of the log. If USER_ALERT_ERROR is the level,
     * then setContext must be called first to set the context so a toast message can alert the user.
     * @param logLevel  The desired severity of the log.
     * @param tag       A tag to identify the location of the error.
     * @param message   A message to print to logcat.
     */
    public void logMessage(int logLevel, String tag, String message) {
        chain.logMessage(logLevel, tag, message);
    }

    /**
     * Creates a log message for logCat for assessing the content of an object.
     * Loglevel specifies the severity of the log. If USER_ALERT_ERROR is the level,
     * then setContext must be called first to set the context so a toast message can alert the user.
     * @param logLevel  The desired severity of the log.
     * @param tag       A tag to identify the location of the error.
     * @param object    The object to inspect in the log.
     */
    public void logMessage(int logLevel, String tag, Object object) {
        String message = (object != null) ? object.toString() : "null";
        chain.logMessage(logLevel, tag, message);
    }

    /**
     * Sets the context for UserAlertErrorLogger which must be done before sending
     * a log message to the log severity of USER_ALERT_ERROR.
     * @param context   The context of the activity where you want to display the toast error.
     */
    public void setContext(Context context) {
        chain.setContext(context);
    }
}

/**
 * Abstract custom logger class for AppLogger.java
 */
abstract class LoggerChain {
    protected LoggerChain nextLogger;

    /**
     * Creates a Logger.
     * @param nextLogger    The next severity of Logger.
     */
    public LoggerChain(LoggerChain nextLogger) {
        this.nextLogger = nextLogger;
    }

    /**
     * Creates a log message.
     * @param logLevel  The severity of the log.
     * @param tag       A tag used to locate the origin of the message.
     * @param message   The message to log.
     */
    public abstract void logMessage(int logLevel, String tag, String message);

    /**
     * Sets the context for the USER_ALERT_ERROR level log.
     * @param context
     */
    public abstract void setContext(Context context);
}

/**
 * An info logger object for logging to Log.i.
 */
class InfoLogger extends LoggerChain {
    /**
     * Creates an info Logger.
     * @param nextLogger    The next severity of Logger.
     */
    public InfoLogger(LoggerChain nextLogger) {
        super(nextLogger);
    }

    /**
     * Creates a log message.
     * @param logLevel  The severity of the log.
     * @param tag       A tag used to locate the origin of the message.
     * @param message   The message to log.
     */
    @Override
    public void logMessage(int logLevel, String tag, String message) {
        if (logLevel <= AppLogger.INFO) {
            Log.i(tag, message);
        } else {
            nextLogger.logMessage(logLevel, tag, message);
        }
    }

    /**
     * Sets the context for the USER_ALERT_ERROR level log.
     * @param context
     */
    @Override
    public void setContext(Context context) {
        nextLogger.setContext(context);
    }
}

/**
 * A debug logger object for logging to Log.d.
 */
class DebugLogger extends LoggerChain {
    /**
     * Creates a debug Logger.
     * @param nextLogger    The next severity of Logger.
     */
    public DebugLogger(LoggerChain nextLogger) {
        super(nextLogger);
    }

    /**
     * Creates a log message.
     * @param logLevel  The severity of the log.
     * @param tag       A tag used to locate the origin of the message.
     * @param message   The message to log.
     */
    @Override
    public void logMessage(int logLevel, String tag, String message) {
        if (logLevel <= AppLogger.DEBUG) {
            Log.d(tag, message);
        } else {
            nextLogger.logMessage(logLevel, tag, message);
        }
    }

    /**
     * Sets the context for the USER_ALERT_ERROR level log.
     * @param context
     */
    @Override
    public void setContext(Context context) {
        nextLogger.setContext(context);
    }
}

/**
 * An error logger object for logging to Log.e.
 */
class ErrorLogger extends LoggerChain {
    /**
     * Creates an error Logger.
     * @param nextLogger    The next severity of Logger.
     */
    public ErrorLogger(LoggerChain nextLogger) {
        super(nextLogger);
    }

    /**
     * Creates a log message.
     * @param logLevel  The severity of the log.
     * @param tag       A tag used to locate the origin of the message.
     * @param message   The message to log.
     */
    @Override
    public void logMessage(int logLevel, String tag, String message) {
        if (logLevel <= AppLogger.ERROR) {
            Log.e(tag, message);
        } else {
            nextLogger.logMessage(logLevel, tag, message);
        }
    }

    /**
     * Sets the context for the USER_ALERT_ERROR level log.
     * @param context
     */
    public void setContext(Context context) {
        nextLogger.setContext(context);
    }
}

/**
 * A user alert error logger object for logging to Log.e and displaying the error message
 * as a Toast message to the user.
 */
class UserAlertErrorLogger extends LoggerChain {
    Context context;

    /**
     * Creates a user alert error Logger.
     * @param nextLogger    The next severity of Logger.
     */
    public UserAlertErrorLogger(LoggerChain nextLogger) {
        super(nextLogger);
    }

    /**
     * Creates a log message.
     * @param logLevel  The severity of the log.
     * @param tag       A tag used to locate the origin of the message.
     * @param message   The message to log.
     */
    @Override
    public void logMessage(int logLevel, String tag, String message) {
        if (logLevel <= AppLogger.USER_ALERT_ERROR) {
            Log.e(tag, message);
            if (context != null) {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            }
        } else {
            Log.e("UserAlertErrorLogger", "invalid log level: " + logLevel);
        }
    }

    /**
     * Sets the context for the USER_ALERT_ERROR level log.
     * @param context
     */
    public void setContext(Context context) {
        this.context = context;
    }
}