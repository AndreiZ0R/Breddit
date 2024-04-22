package com.andreiz0r.breddit.utils;

import static com.andreiz0r.breddit.utils.AppUtils.KILOBYTE;
import static com.andreiz0r.breddit.utils.AppUtils.MEGABYTE;

public class BytesFormatter {

    public static double toKilobytes(final long bytes) {
        return bytes / (double) KILOBYTE;
    }

    public static double toMegabytes(final long bytes) {
        return bytes / (double) MEGABYTE;
    }

    public static String toKilobytesString(final long bytes) {
        return toKilobytes(bytes) + " KB";
    }

    public static String toMegabytesString(final long bytes) {
        return toMegabytes(bytes) + " MB";
    }
}
