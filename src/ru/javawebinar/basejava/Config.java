package ru.javawebinar.basejava;

import java.io.*;
import java.util.Properties;

public class Config {
    private static final Config INSTANCE = new Config();
    protected static final File PROPS = new File(".//storage//resumes.properties");
    private Properties props = new Properties();
    private String storageDir;

    public static Config get() {
        return INSTANCE;
    }

    private Config() {
        try (InputStream is = new FileInputStream("./config/resumes.properties")) {
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
}
