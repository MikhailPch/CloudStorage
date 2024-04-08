package ru.netology.cloudstorage.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.cloudstorage.dto.FileResponse;
import ru.netology.cloudstorage.entities.FileEntity;
import ru.netology.cloudstorage.repository.FileRepository;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileService {
    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }


    public List<FileResponse> getFileList(int limit) {
        final List<FileEntity> files = fileRepository.getFiles(limit);
        return files.stream()
                .map(file -> new FileResponse(file.getFileName(), file.getFileContent().length))
                .collect(Collectors.toList());
    }

    public byte[] getFile(String filename) {
        return fileRepository.findById(filename).orElseThrow(() ->
                new RuntimeException("File " + filename + " not found")).getFileContent();
    }

    public synchronized void addFile(String filename, MultipartFile file) throws IOException {
        fileRepository.save(new FileEntity(filename, file.getBytes()));
    }

    public synchronized void renameFile(String newFilename, String oldFilename) {
        fileRepository.renameFile(newFilename, oldFilename);
    }

    public synchronized void deleteFile(String filename) {
        if (!fileRepository.existsById(filename)) {
            throw new RuntimeException("File " + filename + " not found");
        }
        fileRepository.deleteById(filename);
    }

}

