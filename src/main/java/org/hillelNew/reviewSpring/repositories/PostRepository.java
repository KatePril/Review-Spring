package org.hillelNew.reviewSpring.repositories;

import org.hillelNew.reviewSpring.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
