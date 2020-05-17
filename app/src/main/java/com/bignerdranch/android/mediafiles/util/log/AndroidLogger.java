package com.bignerdranch.android.mediafiles.util.log;

import androidx.annotation.NonNull;
import android.util.Log;

import javax.annotation.CheckReturnValue;

/**
 * Sends logs to standard Android Log class
 * <p>
 * See {@see Log} for details about VERBOSE and DEBUG logging. As VERBOSE statement are supposed to
 * be used only for development, use release Proguard to remove VERBOSE logging.
 *
 * -assumenosideeffects class android.util.Log {
 *      public static int v(...);
 *  }
 * </p>
 * <p>
 * See {@see Log#isLoggable()} for details about how to override logging for particular tag/class.
 * Change the default level by setting a system property: 'setprop log.tag.<YOUR_LOG_TAG> <LEVEL>'
 * where level is either VERBOSE, DEBUG, INFO, WARN, ERROR, or ASSERT.
 * </p>
 */
public class AndroidLogger implements Logger {

    private static final int MAX_IS_LOGGABLE_LENGTH = 23;
    static final String CUSTOMER_EVENT = "CustomerEvent";

    private final @NonNull String prefix;

    //TODO NAP-594 Remove LogObfuscator if not needed by client like Gallery package anymore
    private final @NonNull LogObfuscator logObfuscator;

    public AndroidLogger(@NonNull final String logPrefix, @NonNull final LogObfuscator logObfuscator) {
        this.prefix = logPrefix + "_";
        this.logObfuscator = logObfuscator;
    }

    @Override
    public void d(@NonNull String tag, @NonNull String message) {
        final String prefixedTag = getPrefixedTag(tag);
        if (isLoggable(prefixedTag, Log.DEBUG)) {
            Log.d(prefixedTag, getLogMessage(message));
        }
    }

    @Override
    public void v(@NonNull String tag, @NonNull String message) {
        final String prefixedTag = getPrefixedTag(tag);
        if (isLoggable(prefixedTag, Log.VERBOSE)) {
            Log.v(prefixedTag, getLogMessage(message));
        }
    }

    @Override
    public void i(@NonNull String tag, @NonNull String message) {
        final String prefixedTag = getPrefixedTag(tag);
        if (isLoggable(prefixedTag, Log.INFO)) {
            Log.i(prefixedTag, getLogMessage(message));
        }
    }

    @Override
    public void w(@NonNull String tag, @NonNull String message) {
        final String prefixedTag = getPrefixedTag(tag);
        if (isLoggable(prefixedTag, Log.WARN)) {
            Log.w(prefixedTag, getLogMessage(message));
        }
    }

    @Override
    public void e(@NonNull String tag, @NonNull String message) {
        final String prefixedTag = getPrefixedTag(tag);
        if (isLoggable(prefixedTag, Log.ERROR)) {
            Log.e(prefixedTag, getLogMessage(message));
        }
    }

    @Override
    public void wtf(@NonNull String tag, @NonNull String message) {
        final String prefixedTag = getPrefixedTag(tag);
        Log.wtf(prefixedTag, getLogMessage(message));
    }

    @Override
    public void d(@NonNull String tag, @NonNull String message, @NonNull Throwable throwable) {
        final String prefixedTag = getPrefixedTag(tag);
        if (isLoggable(prefixedTag, Log.DEBUG)) {
            Log.d(prefixedTag, getLogMessage(message), throwable);
        }
    }

    @Override
    public void v(@NonNull String tag, @NonNull String message, @NonNull Throwable throwable) {
        final String prefixedTag = getPrefixedTag(tag);
        if (isLoggable(prefixedTag, Log.VERBOSE)) {
            Log.v(prefixedTag, getLogMessage(message), throwable);
        }
    }

    @Override
    public void i(@NonNull String tag, @NonNull String message, @NonNull Throwable throwable) {
        final String prefixedTag = getPrefixedTag(tag);
        if (isLoggable(prefixedTag, Log.INFO)) {
            Log.i(prefixedTag, getLogMessage(message), throwable);
        }
    }

    @Override
    public void w(@NonNull String tag, @NonNull String message, @NonNull Throwable throwable) {
        final String prefixedTag = getPrefixedTag(tag);
        if (isLoggable(prefixedTag, Log.WARN)) {
            Log.w(prefixedTag, getLogMessage(message), throwable);
        }
    }

    @Override
    public void e(@NonNull String tag, @NonNull String message, @NonNull Throwable throwable) {
        final String prefixedTag = getPrefixedTag(tag);
        if (isLoggable(prefixedTag, Log.ERROR)) {
            Log.e(prefixedTag, getLogMessage(message), throwable);
        }
    }

    @Override
    public void wtf(@NonNull String tag, @NonNull String message, @NonNull Throwable throwable) {
        final String prefixedTag = getPrefixedTag(tag);
        Log.wtf(prefixedTag, getLogMessage(message), throwable);
    }

    @Override
    public void logCustomerEvent(@NonNull String message) {
        this.i(CUSTOMER_EVENT, message);
    }

    @CheckReturnValue
    @NonNull
    String getPrefixedTag(@NonNull String tag) {
        return this.prefix + ((tag.lastIndexOf('.') > 0) ? tag.substring(tag.lastIndexOf('.') + 1, tag.length()) : tag);
    }

    @NonNull
    String getLogMessage(@NonNull  String message) {
        return logObfuscator.obfuscate(message);
    }

    @NonNull
    boolean isLoggable(@NonNull String tag, int level) {
        if (tag.length() > MAX_IS_LOGGABLE_LENGTH) {
            // Log.isLoggable is the only android Log method with a length limit
            return Log.isLoggable(tag.substring(0, MAX_IS_LOGGABLE_LENGTH), level);
        }
        return Log.isLoggable(tag, level);
    }
}

