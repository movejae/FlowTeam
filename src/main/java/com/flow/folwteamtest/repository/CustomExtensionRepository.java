package com.flow.folwteamtest.repository;

import com.flow.folwteamtest.entity.CustomExtension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomExtensionRepository extends JpaRepository<CustomExtension, Long> {
    boolean existsByExtension(String extension);
    Optional<CustomExtension> findByExtension(String extension);
    long count();
}
