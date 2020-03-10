package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private File directory;
    private SerializationStrategy serializationStrategy;

    public FileStorage(String dir, SerializationStrategy serializationStrategy) {
        File directory = new File(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not readable/writable");
        }
        this.directory = directory;
        this.serializationStrategy = serializationStrategy;
    }

    public void setSerializationStrategy(SerializationStrategy serializationStrategy){
        this.serializationStrategy = serializationStrategy;
    }

    @Override
    protected void doSave(Resume resume, File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("IO error - file creation error", file.getName(), e);
        }
        doUpdate(resume, file);
    }

    @Override
    protected void doUpdate(Resume resume, File file) {
        try {
            this.serializationStrategy.write(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("IO error - file overwrite error", file.getName(), e);
        }
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return this.serializationStrategy.read(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException | ClassNotFoundException e) {
            throw new StorageException("IO error - file reading error", file.getName(), e);
        }
    }

    @Override
    protected void doDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("IO error - file deletion error", file.getName());
        }
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected List<Resume> asList() {
        File[] files = directory.listFiles();
        validateFileList(files);
        List<Resume> resumeList = new ArrayList<>();
        for (File file : files) {
            resumeList.add(doGet(file));
        }
        return resumeList;
    }

    @Override
    public void clear() {
        File[] files = directory.listFiles();
        validateFileList(files);
        for (File file : files) {
            doDelete(file);
        }
    }

    @Override
    public int size() {
        String[] fileNames = directory.list();
        validateFileList(fileNames);
        return fileNames.length;
    }

    private <T> void validateFileList(T[] list) {
        if (list == null) {
            throw new StorageException("List of files is invalid", directory.getName());
        }
    }
}