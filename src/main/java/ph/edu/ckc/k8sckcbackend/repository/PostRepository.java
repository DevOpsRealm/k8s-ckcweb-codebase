package ph.edu.ckc.k8sckcbackend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ph.edu.ckc.k8sckcbackend.entities.Post;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, PagingAndSortingRepository<Post, Long> {
    Page<Post> findAll(Pageable pageable);
    Page<Post> findAllByOverrideDefaultsEqualsAndStaticPageIsFalse(Boolean overrideDefaults, Pageable pageable);
    Page<Post> findAllByStaticPageEquals(Boolean staticPage, Pageable pageable);
    Optional<Post> findBySlug(String slug);
}
