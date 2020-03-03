package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {
    private Path directory;

    public AbstractPathStorage(String dir) {
        Path directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory) ) {
            throw new IllegalArgumentException(dir + "is not directory or is not readable/writable");
        }
        this.directory = directory;
    }


    protected abstract void doWrite(Resume r, OutputStream os) throws IOException;

    protected abstract Resume doRead(InputStream is) throws IOException;

    @Override
    protected void doSave(Resume resume, Path Path) {
        try {
//            Path.createNewPath();
        } catch (IOException e) {
            throw new StorageException("IO error - Path creation has failed", Path.getName(), e);
        }
        doUpdate(resume, Path);
    }

    @Override
    protected void doUpdate(Resume resume, Path Path) {
        try {
            doWrite(resume, new BufferedOutputStream(new FileOutputStream()));
        } catch (IOException e) {
            throw new StorageException("IO error - Path overwrite has failed", Path.getName(), e);
        }
    }

    @Override
    protected Resume doGet(Path Path) {
        try {
            return doRead(new BufferedInputStream(new PathInputStream(Path)));
        } catch (IOException e) {
            throw new StorageException("IO error - Path reading has failed", Path.getName(), e);
        }
    }

    @Override
    protected void doDelete(Path Path) {
        if (!Path.delete()) {
            throw new StorageException("IO error - Path deletion has failed", Path.getName());
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return new Path(directory, uuid);
    }

    @Override
    protected boolean isExist(Path Path) {
        return Path.exists();
    }

    @Override
    protected List<Resume> asList() {
        Path[] Paths = directory.listPaths();
        validatePathList(Paths);
        List<Resume> resumeList = new ArrayList<>();
        for (Path Path : Paths) {
            resumeList.add(doGet(Path));
        }
        return resumeList;
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null);
        }
    }

    @Override
    public int size() {
        String[] PathNames = directory.list();
        validatePathList(PathNames);
        return PathNames.length;
    }

    private <T> void validatePathList(T[] list) {
        if (list == null) {
            throw new StorageException("List of Paths is invalid", directory.getName());
        }
    }
}