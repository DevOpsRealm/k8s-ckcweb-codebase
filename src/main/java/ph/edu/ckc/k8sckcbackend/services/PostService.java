package ph.edu.ckc.k8sckcbackend.services;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ph.edu.ckc.k8sckcbackend.dto.PostDto;
import ph.edu.ckc.k8sckcbackend.dto.TransferObjectMapper;
import ph.edu.ckc.k8sckcbackend.entities.Post;
import ph.edu.ckc.k8sckcbackend.exception.ResourceNotFoundException;
import ph.edu.ckc.k8sckcbackend.repository.PostRepository;

import java.util.Optional;

import static ph.edu.ckc.k8sckcbackend.util.IdGenerator.*;


@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final TransferObjectMapper mapper;

    private static final Logger LOG = LoggerFactory.getLogger(PostService.class);

    @Transactional
    public PostDto post(PostDto postDto) {
        postDto.setSlug(toSlug(postDto.getTitle()));
        postRepository.findBySlug(toSlug(postDto.getTitle()))
                .ifPresent(post -> postDto.setSlug( String.format("%s-%s", postDto.getSlug(), generateIdForSlugV2(20))  ));

        Post post = postRepository.save(mapper.postDtoToPostEntity(postDto));
        return mapper.postEntityToPostDto(post);
    }

    @Transactional
    public PostDto updatePost(String slug, PostDto postDto) {
        return findPostBySlug(slug)
                .map(post -> {
                    post.setTitle(postDto.getTitle());
                    post.setExcerpt(postDto.getExcerpt());
                    post.setContent(postDto.getContent());
                    post.setKeywords(postDto.getKeywords());
                    post.setOverrideDefaults(postDto.getOverrideDefaults());
                    post.setStaticPage(postDto.getStaticPage());
                    Post save = postRepository.save(post);
                    return mapper.postEntityToPostDto(save);
                }).orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
    }

    @Transactional
    public Boolean deletePost(String slug) {
        return findPostBySlug(slug)
            .map(post -> {
                postRepository.delete(post);
                return true;
            }).orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
    }

    @Transactional
    public PostDto getPostBySlug(String slug) {
        return findPostBySlug(slug)
                .map(mapper::postEntityToPostDto).orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
    }


    @Transactional
    public Page<PostDto> getAllPostByOverrideDefaults(String pageString, String sizeString, String stringOverrideDefaults, String stringDoFilter, String stringStaticPage) {
        int page, size;
        boolean overrideDefaults, doFilter, staticPage;
        try {
            page = Integer.parseInt(pageString);
            size = Integer.parseInt(sizeString);
            overrideDefaults = Boolean.parseBoolean(stringOverrideDefaults);
            doFilter = Boolean.parseBoolean(stringDoFilter);
            staticPage = Boolean.parseBoolean(stringStaticPage);
        }catch (IllegalArgumentException ex) {
            page = 0;
            size = 2;
            overrideDefaults = false;
            doFilter = false;
            staticPage = false;
        }
        PageRequest pageRequest = PageRequest.of( Math.abs(  page  ), Math.abs(  size  ), Sort.Direction.DESC, "postedAt");

        if(!doFilter) return postRepository.findAll(pageRequest).map(mapper::postEntityToPostDto);
        else {
            if(staticPage) return postRepository.findAllByStaticPageEquals(true, pageRequest).map(mapper::postEntityToPostDto);
            return postRepository.findAllByOverrideDefaultsEqualsAndStaticPageIsFalse(overrideDefaults, pageRequest).map(mapper::postEntityToPostDto);
        }
    }
    @Transactional
    public Optional<Post> findPostBySlug(String slug) {
        return postRepository.findBySlug(slug);
    }

}
