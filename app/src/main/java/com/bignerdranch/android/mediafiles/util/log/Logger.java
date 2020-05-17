package com.bignerdranch.android.mediafiles.util.log;

import androidx.annotation.NonNull;

/**
 * API to send log output.
 */
public interface Logger {

    /**
     * Send a DEBUG log message.
     *
     * @param tag     Used to identify the source of a log message.  It usually identifies
     *                the class or activity where the log call occurs.
     * @param message The message you would like logged.
     */
    void d(@NonNull String tag, @NonNull String message);

    /**
     * Send a VERBOSE log message.
     *
     * @param tag     Used to identify the source of a log message.  It usually identifies
     *                the class or activity where the log call occurs.
     * @param message The message you would like logged.
     */
    void v(@NonNull String tag, @NonNull String message);

    /**
     * Send a INFO log message.
     *
     * @param tag     Used to identify the source of a log message.  It usually identifies
     *                the class or activity where the log call occurs.
     * @param message The message you would like logged.
     */
    void i(@NonNull String tag, @NonNull String message);

    /**
     * Send a WARN log message.
     *
     * @param tag     Used to identify the source of a log message.  It usually identifies
     *                the class or activity where the log call occurs.
     * @param message The message you would like logged.
     */
    void w(@NonNull String tag, @NonNull String message);

    /**
     * Send a ERROR log message.
     *
     * @param tag     Used to identify the source of a log message.  It usually identifies
     *                the class or activity where the log call occurs.
     * @param message The message you would like logged.
     */
    void e(@NonNull String tag, @NonNull String message);

    /**
     * What a Terrible Failure: Report a condition that should never happen.
     *
     * @param tag     Used to identify the source of a log message.  It usually identifies
     *                the class or activity where the log call occurs.
     * @param message The message you would like logged.
     */
    void wtf(@NonNull String tag, @NonNull String message);

    /**
     * Send a DEBUG log message and log the exception.
     *
     * @param tag       Used to identify the source of a log message.  It usually identifies
     *                  the class or activity where the log call occurs.
     * @param message   The message you would like logged.
     * @param throwable An exception to log
     */
    void d(@NonNull String tag, @NonNull String message, @NonNull Throwable throwable);

    /**
     * Send a VERBOSE log message and log the exception.
     *
     * @param tag       Used to identify the source of a log message.  It usually identifies
     *                  the class or activity where the log call occurs.
     * @param message   The message you would like logged.
     * @param throwable An exception to log
     */
    void v(@NonNull String tag, @NonNull String message, @NonNull Throwable throwable);

    /**
     * Send a INFO log message and log the exception.
     *
     * @param tag       Used to identify the source of a log message.  It usually identifies
     *                  the class or activity where the log call occurs.
     * @param message   The message you would like logged.
     * @param throwable An exception to log
     */
    void i(@NonNull String tag, @NonNull String message, @NonNull Throwable throwable);

    /**
     * Send a WARN log message and log the exception.
     *
     * @param tag       Used to identify the source of a log message.  It usually identifies
     *                  the class or activity where the log call occurs.
     * @param message   The message you would like logged.
     * @param throwable An exception to log
     */
    void w(@NonNull String tag, @NonNull String message, @NonNull Throwable throwable);

    /**
     * Send a ERROR log message and log the exception.
     *
     * @param tag       Used to identify the source of a log message.  It usually identifies
     *                  the class or activity where the log call occurs.
     * @param message   The message you would like logged.
     * @param throwable An exception to log
     */
    void e(@NonNull String tag, @NonNull String message, @NonNull Throwable throwable);

    /**
     * What a Terrible Failure: Report an exception that should never happen.
     *
     * @param tag       Used to identify the source of a log message.  It usually identifies
     *                  the class or activity where the log call occurs.
     * @param message   The message you would like logged.
     * @param throwable An exception to log
     */
    void wtf(@NonNull String tag, @NonNull String message, @NonNull Throwable throwable);

    /**
     * Log customer event using tag string CustomerEvent
     *
     * @param message The message you would like logged.
     */
    void logCustomerEvent(@NonNull String message);
}
