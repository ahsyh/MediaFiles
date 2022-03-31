package com.bignerdranch.android.mediafiles.util.log;

import androidx.annotation.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class looks for specific patterns and obfuscates personally identifiable information (PII),
 * which is currently not allowed to be recorded in Spectator.
 * @see <a href="https://issues.labcollab.net/browse/AP-10444">AP-10444</a>
 */
public class DefaultLogObfuscator implements LogObfuscator {

    static final String TAG = "DefaultLogObfuscator";
    static final String TAG_OBFUSCATED = "{Data removed by LogObfuscator}";

    /**
     * Pattern for directed IDs.
     * @see <a href="https://w.amazon.com/index.php/IdentityServices/DirectedIDs">DirectedIDs</a>
     */
    private static final Pattern PATTERN_DIRECTED_ID = Pattern.compile("amzn1.account.\\w*");
    private static final String EMPTY_STRING = "";

    @Override
    public String obfuscate(final @Nullable String logMessage) {
        if (logMessage == null || EMPTY_STRING.equals(logMessage)) {
            return EMPTY_STRING;
        }

        final Matcher matcher = PATTERN_DIRECTED_ID.matcher(logMessage);
        final StringBuffer sb = new StringBuffer();
        int count = 0;

        while (matcher.find()) {
            count++;
            matcher.appendReplacement(sb, TAG_OBFUSCATED);
        }
        matcher.appendTail(sb);

        return sb.toString();
    }
}

