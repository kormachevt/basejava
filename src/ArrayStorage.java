/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int firstEmptySpotIndex = 0;

    void clear() {
        for (int i = 0; i < firstEmptySpotIndex; i++) {
            this.storage[i] = null;
        }
        firstEmptySpotIndex = 0;
    }

    void save(Resume resume) {
        if (resume.uuid != null) {
            this.storage[firstEmptySpotIndex] = resume;
            firstEmptySpotIndex += 1;
        } else {
            System.out.println("Empty resumes now allowed!");
        }
    }

    Resume get(String uuid) {
        int indexOfResume = indexByUuid(uuid);
        if (indexOfResume > 0) {
            return this.storage[indexOfResume];
        }
        return null;
    }

    void delete(String uuid) {
        int indexOfResumeToBeDeleted = indexByUuid(uuid);
        this.storage[indexOfResumeToBeDeleted] = null;
        if (firstEmptySpotIndex + 1 - indexOfResumeToBeDeleted >= 0) {
            System.arraycopy(this.storage, indexOfResumeToBeDeleted + 1, this.storage, indexOfResumeToBeDeleted,
                    firstEmptySpotIndex + 1 - indexOfResumeToBeDeleted);
        }
        firstEmptySpotIndex -= 1;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] resumes = new Resume[firstEmptySpotIndex];
        System.arraycopy(this.storage, 0, resumes, 0, firstEmptySpotIndex);
        return resumes;
    }

    int size() {
        return firstEmptySpotIndex + 1;
    }

    private int indexByUuid(String uuid) {
        for (int i = 0; i < firstEmptySpotIndex; i++) {
            if (this.storage[i].uuid.equals(uuid)) {
                return i;
            }
        }
        System.out.println(String.format("No Resume with [uuid: %s] was found", uuid));
        return -1;
    }
}
