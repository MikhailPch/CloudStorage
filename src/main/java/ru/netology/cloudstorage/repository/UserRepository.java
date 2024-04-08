package ru.netology.cloudstorage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.netology.cloudstorage.entities.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

}
