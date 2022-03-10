package org.springframework.data.influxdb.support;

import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Version implements Serializable {

    private static final Pattern PATTERN = Pattern.compile("^(\\d+)(\\.(\\d+))?(\\.(\\d+))?.*$");

    private final int major;

    private final int minor;

    private final int revision;


    public Version(int major, int minor, int revision) {
        this.major = major;
        this.minor = minor;
        this.revision = revision;
    }

    @Override
    public String toString() {
        return "" + major + "." + minor + "." + revision;
    }

    public static Version fromString(String s) {
        Assert.notNull(s, "s must not be null");
        Matcher matcher = PATTERN.matcher(s);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("invalid input pattern");
        }

        int major = Integer.parseInt(matcher.group(1));
        int minor = matcher.group(3) != null ? Integer.parseInt(matcher.group(3)) : 0;
        int revision = matcher.group(5) != null ? Integer.parseInt(matcher.group(5)) : 0;

        return new Version(major, minor, revision);
    }

    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }

    public int getRevision() {
        return revision;
    }
}
