package com.page.server.repository;

import com.page.server.entity.ProjectTypeRef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProjectTypeRefRepository extends JpaRepository<ProjectTypeRef, Long> {
    @Query(
            nativeQuery = true,
            value = "SELECT * FROM _prject_type_ref WHERE project_no = ?1 AND type_no = ?2"
    )
    Optional<ProjectTypeRef> findByProjectNoAndTypeNo(Long projectNo, Long typeNo);

    List<ProjectTypeRef> findAllByProjectNoAndDeletedIsNullOrDeletedFalse(Long projectNo);
}
