/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int size = 0;

    void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    void save(Resume resume) {
        if (resume.uuid != null) {
            storage[size] = resume;
            size++;
        } else {
            System.out.println("Empty resumes are not allowed!");
        }
    }

    Resume get(String uuid) {
        int index = indexByUuid(uuid);
        if (index > 0) {
            return storage[index];
        }
        return null;
    }

    void delete(String uuid) {
        int index = indexByUuid(uuid);
        if (size + 1 - index >= 0) {
            System.arraycopy(storage, index + 1, storage, index, size - index - 1);
        }
        size--;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] resumes = new Resume[size];
        System.arraycopy(storage, 0, resumes, 0, size);
        return resumes;
    }

    int size() {
        return size;
    }

    private int indexByUuid(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return i;
            }
        }
        System.out.println(String.format("No Resume with [uuid: %s] was found", uuid));
        return -1;
    }
}
