package ru.javawebinar.basejava;

import ru.javawebinar.basejava.storage.SqlStorage;
import ru.javawebinar.basejava.storage.Storage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final Config INSTANCE = new Config();
    private static final String PROPS = "/resumes.properties";
    private String storageDir;
    private Storage storage;


    public static Config get() {
        return INSTANCE;
    }

    private Config() {
        try (InputStream is = Config.class.getResourceAsStream(PROPS)) {
            Properties props = new Properties();
            props.load(is);
            storageDir = props.getProperty("storage.dir");
            storage = new SqlStorage(props.getProperty("db.url"), props.getProperty("db.user"), props.getProperty("db.password"));
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Config file not found " + PROPS);
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPS);
        }
    }

    public String getStorageDir() {
        return storageDir;
    }

    public Storage getStorage() {
        return storage;
    }
}
