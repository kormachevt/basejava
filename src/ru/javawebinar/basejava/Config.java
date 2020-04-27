package ru.javawebinar.basejava;

import java.io.*;
import java.util.Properties;

public class Config {
    private static final String PROPS_PATH = "./config/resumes.properties";
    private static final Config INSTANCE = new Config();
    protected static final File PROPS = new File(PROPS_PATH);
    private Properties props = new Properties();
    private String storageDir;

    public static Config get() {
        return INSTANCE;
    }

    private Config() {
        try (InputStream is = new FileInputStream(PROPS_PATH)) {
            props.load(is);
            storageDir = props.getProperty("storage.dir");
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Config file not found " + PROPS.getAbsolutePath());
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPS.getAbsolutePath());
        }
    }

    public String getStorageDir() {
        return storageDir;
    }

    public String getDbUrl() {
        return props.getProperty("db.url");
    }

    public String getDbUser() {
        return props.getProperty("db.user");
    }

    public String getDbPassword() {
        return props.getProperty("db.password");
    }
}
