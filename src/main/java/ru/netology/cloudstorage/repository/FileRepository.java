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
    @Query(value = "UPDATE files SET file_name = :newFilename where file_name = :oldFilename",
            nativeQuery = true)
    void renameFile(@Param("oldFilename") String oldFilename,
                    @Param("newFilename") String newFilename);
}





