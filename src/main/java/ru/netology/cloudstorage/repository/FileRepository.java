package ru.netology.cloudstorage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.netology.cloudstorage.entities.FileEntity;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, String> {
    @Query(value = "SELECT * FROM files LIMIT :limit", nativeQuery = true)
    List<FileEntity> getFiles(int limit);

    @Modifying
    @Query(value = "UPDATE files f SET f.filename = :newFilename where f.filename = :oldFilename",
            nativeQuery = true)
    void renameFile(@Param("newFilename") String newFilename,
                    @Param("oldFilename") String oldFilename);
}