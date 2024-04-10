package ru.netology.cloudstorage.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.cloudstorage.entities.FileEntity;
import ru.netology.cloudstorage.repository.FileRepository;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

class FileServiceTest {
    public static final String EXISTING_FILE = "existingFile";
    public static final String NOT_EXISTING_FILE = "notExistingFile";
    private final FileEntity existingFileEntity = new FileEntity(EXISTING_FILE, new byte[]{1, 2, 3, 4, 5});
    private final FileRepository fileRepository = fileRepositoryMock();
    private final FileService fileService = new FileService(fileRepository);

    private FileRepository fileRepositoryMock() {
        final FileRepository fileRepository = Mockito.mock(FileRepository.class);

        when(fileRepository.findById(EXISTING_FILE)).thenReturn(Optional.of(existingFileEntity));
        when(fileRepository.findById(NOT_EXISTING_FILE)).thenReturn(Optional.empty());

        when(fileRepository.existsById(EXISTING_FILE)).thenReturn(true);
        when(fileRepository.existsById(NOT_EXISTING_FILE)).thenReturn(false);

        when(fileRepository.getFiles(1)).thenReturn(List.of(existingFileEntity));

        return fileRepository;
    }

    @Test
    void test_getFileList() {
        final List<FileEntity> expectedFileList = List.of(existingFileEntity);
        final List<FileEntity> actualfileList = fileService.getFileList(1);
        Assertions.assertEquals(expectedFileList, actualfileList);
    }

    @Test
    void test_getFile() {
        final byte[] expectedFile = new byte[]{1, 2, 3, 4, 5};
        final byte[] actualFile = fileService.getFile(EXISTING_FILE);
        Assertions.assertArrayEquals(expectedFile, actualFile);
    }

    @Test
    void test_getFile_failed() {
        Assertions.assertThrows(RuntimeException.class, () -> fileService.getFile(NOT_EXISTING_FILE));
    }

    @Test
    void test_renameFile() {
        Assertions.assertDoesNotThrow(() -> fileService.renameFile(EXISTING_FILE, NOT_EXISTING_FILE));
    }

    @Test
    void test_renameFile_failed() {
        Assertions.assertThrows(RuntimeException.class, () -> fileService.renameFile(NOT_EXISTING_FILE, EXISTING_FILE));
    }

    @Test
    void test_deleteFile() {
        Assertions.assertDoesNotThrow(() -> fileService.deleteFile(EXISTING_FILE));
    }

    @Test
    void test_deleteFile_failed() {
        Assertions.assertThrows(RuntimeException.class, () -> fileService.deleteFile(NOT_EXISTING_FILE));
    }

}
