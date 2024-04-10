package ru.netology.cloudstorage.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.cloudstorage.dto.FileResponse;
import ru.netology.cloudstorage.entities.FileEntity;
import ru.netology.cloudstorage.repository.FileRepository;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service
public class FileService {
    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }


    public List<FileEntity> getFileList(int limit) {
        List<FileEntity> files = fileRepository.getFiles(limit);
        log.info("Files with limit " + limit + " were found");
        return files;
    }

    public byte[] getFile(String filename) {
        byte[] file = fileRepository.findById(filename).orElseThrow(() ->
                new RuntimeException("File " + filename + " not found")).getFileContent();
        log.info("File " + filename + " was found");
        return file;
    }

    public void addFile(String filename, MultipartFile file) throws IOException {
        fileRepository.save(new FileEntity(filename, file.getBytes()));
        log.info("File " + filename + " was saved to database");
    }

    public void renameFile(String oldfilename, String newFilename) {
        if(!fileRepository.existsById(oldfilename)){
            throw new RuntimeException("File " + oldfilename + " is not found");
        }
        fileRepository.renameFile(oldfilename, newFilename);
        log.info("File with name " + oldfilename + " was renamed to " + newFilename);
    }

    public void deleteFile(String filename) {
        if (!fileRepository.existsById(filename)) {
            throw new RuntimeException("File " + filename + " not found");
        }
        fileRepository.deleteById(filename);
        log.info("File " + filename + " was deleted");
    }

}

