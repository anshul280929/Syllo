package com.example.syllo_backend.Repository;

import com.example.syllo_backend.Model.SubjectResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubjectResourceRepository extends JpaRepository<SubjectResource, Long> {

    @Query(value = """
       SELECT sr.*
       FROM subject_resources sr
       JOIN subjects s ON s.id = sr.subject_id
       WHERE s.user_id = :userId
       AND COALESCE(sr.raw_text, '') ILIKE CONCAT('%', :keyword, '%')
       """, nativeQuery = true)
    List<SubjectResource> searchUserResources(@Param("userId") Long userId,
                                              @Param("keyword") String keyword);
}
