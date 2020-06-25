package com.dodonov.university.repository;

import com.dodonov.university.domain.Course;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<Course, UUID> {
    @EntityGraph(attributePaths = {"professor", "faculty", "students"})
    Optional<Course> findFullById(UUID id);
}
