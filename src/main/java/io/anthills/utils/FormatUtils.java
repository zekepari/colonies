package io.anthills.utils;

public class FormatUtils {
    /**
     * Dynamically formats a cooldown given in seconds.
     * It will only show nonzero units (days, hours, minutes, seconds),
     * unless the total time is less than one minute.
     *
     * @param seconds the cooldown in seconds
     * @return a dynamically formatted time string.
     */
    public static String formatCooldown(int seconds) {
        int days = seconds / 86400;
        int remainder = seconds % 86400;
        int hours = remainder / 3600;
        remainder %= 3600;
        int minutes = remainder / 60;
        int secs = remainder % 60;

        StringBuilder sb = new StringBuilder();
        if (days > 0) {
            sb.append(days).append("d ");
        }
        if (hours > 0) {
            sb.append(hours).append("h ");
        }
        if (minutes > 0) {
            sb.append(minutes).append("m ");
        }
        if (secs > 0 || sb.length() == 0) {
            sb.append(secs).append("s");
        }
        return sb.toString().trim();
    }
}
