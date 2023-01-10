package com.project.application.repository;

import com.project.application.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findByName(String tagName);

    @Query("select t from Question q join q.tags t where q.id = :questionId")
    public List<Tag> findAll(long questionId);

    @Query(nativeQuery = true,value="SELECT * from tag where name like %:theSearchName%")
    List<Tag> searchTags(@Param("theSearchName") String theSearchName);

}
