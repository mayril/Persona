package com.ssafy.project.common.db.repository;

import com.ssafy.project.common.db.entity.common.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    public Optional<Comment> findById(Long id);
    public List<Comment> findByBoardId(Long boardId);
    //등록
    public Comment save(Comment comment);
    //삭제
    public void deleteById(Long id);

}