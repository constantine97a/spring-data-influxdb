package org.springframework.data.influxdb.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * This class is used to log the versions of Spring Data Influxdb, the Influxdb client libs used to built, the
 * Influxdb client libs currently used and of the Influxdb cluster. If differences greater than a patchlevel
 * are detected, these are logged as warnings.
 */
public class VersionInfo {

    private static final Logger LOG = LoggerFactory.getLogger(VersionInfo.class);

    public static final String VERSION_PROPERTIES = "versions.properties";

    public static final String VERSION_SPRING_DATA_INFLUXDB = "version.spring-data-influxdb";

    public static final String VERSION_INFLUXDB_CLIENT = "version.influxdb-client";

    private static Properties versionProperties;

    static {
        try {
            versionProperties = loadVersionProperties();
        } catch (IOException e) {
            LOG.error("Could not load {}", VERSION_PROPERTIES, e);
            versionProperties = new Properties();
            versionProperties.put(VERSION_SPRING_DATA_INFLUXDB, "0.0.0");
            versionProperties.put(VERSION_INFLUXDB_CLIENT, "0.0.0");
        }
    }


    public static void logVersion(String vendor, String runtimeLibraryVersion, @Nullable String clusterVersion) {
        try {

            String versionSpringDatainfluxdb = versionProperties.getProperty(VERSION_SPRING_DATA_INFLUXDB);
            Version versionBuiltLibraryES = Version.fromString(versionProperties.getProperty(VERSION_INFLUXDB_CLIENT));
            Version versionRuntimeLibrary = Version.fromString(runtimeLibraryVersion);
            Version versionCluster = clusterVersion != null ? Version.fromString(clusterVersion) : null;

            LOG.info("Version Spring Data InfluxDB: {}", versionSpringDatainfluxdb);
            LOG.info("Version InfluxDB client in build: {}", versionBuiltLibraryES.toString());
            LOG.info("Version runtime client used: {} - {}", vendor, versionRuntimeLibrary.toString());

            if (differInMajorOrMinor(versionBuiltLibraryES, versionRuntimeLibrary)) {
                LOG.warn("Version mismatch in between Influxdb Clients build/use: {} - {}", versionBuiltLibraryES,
                        versionRuntimeLibrary);
            }

            if (versionCluster != null) {
                LOG.info("Version cluster: {} - {}", vendor, versionCluster.toString());

                if (differInMajorOrMinor(versionRuntimeLibrary, versionCluster)) {
                    LOG.warn("Version mismatch in between  Client and Cluster: {} - {} - {}", vendor, versionRuntimeLibrary,
                            versionCluster);
                }
            }
        } catch (Exception e) {
            LOG.warn("Could not log version info: {} - {}", e.getClass().getSimpleName(), e.getMessage());
        }
    }

    private static boolean differInMajorOrMinor(Version version1, Version version2) {
        return version1.getMajor() != version2.getMajor() || version1.getMinor() != version2.getMinor();
    }

    /**
     * load the properties from classpath resource {@link  #VERSION_PROPERTIES}
     *
     * @return
     * @throws IOException
     */
    private static Properties loadVersionProperties() throws IOException {
        InputStream resource = VersionInfo.class.getClassLoader().getResourceAsStream(VERSION_PROPERTIES);
        if (resource != null) {
            Properties properties = new Properties();
            properties.load(resource);
            return properties;
        } else {
            throw new IllegalStateException("Resource not found");
        }
    }

    public static Properties versionProperties() {
        return versionProperties;
    }
}
