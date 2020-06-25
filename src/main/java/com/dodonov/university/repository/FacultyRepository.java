package com.dodonov.university.repository;

import com.dodonov.university.domain.Faculty;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, UUID> {
    @EntityGraph(attributePaths = {"courses"})
    Optional<Faculty> findFullById(UUID id);
}
