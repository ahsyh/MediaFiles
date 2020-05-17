package com.bignerdranch.android.mediafiles.util.log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Interface to obfuscate information like personally identifiable information (PII) from log messages.
 */
public interface LogObfuscator {

    /**
     * Obfuscates personally identifiable information contained in a log message and returns
     * the obfuscated log string.
     *
     * @param logMessage The log string to obfuscate.
     * @return An obfuscated string.
     */
    @NonNull
    String obfuscate(@Nullable String logMessage);
}

