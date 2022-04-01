package ph.edu.ckc.k8sckcbackend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ph.edu.ckc.k8sckcbackend.dto.GenericResponse;
import ph.edu.ckc.k8sckcbackend.dto.PostDto;
import ph.edu.ckc.k8sckcbackend.services.PostService;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;


@RestController
@RequiredArgsConstructor
@RequestMapping("auth/post")
@Slf4j
public class PostController {


    private final PostService postService;


    @GetMapping
    public Page<PostDto> getAllPost(@RequestParam(value = "page", defaultValue = "0") String page,
                                    @RequestParam(value = "size", defaultValue = "2") String size,
                                    @RequestParam(value = "overrideDefaults", defaultValue = "false") String overrideDefaults,
                                    @RequestParam(value = "doFilter", defaultValue = "false") String doFilter,
                                    @RequestParam(value = "staticPage", defaultValue = "false") String staticPage) {
        return postService.getAllPostByOverrideDefaults(page, size, overrideDefaults, doFilter, staticPage);
    }


    @GetMapping("{slug}")
    public ResponseEntity<PostDto> post(@PathVariable String slug) {
        PostDto post = postService.getPostBySlug(slug);
        return ResponseEntity.ok(post);

    }

    @PostMapping
    public ResponseEntity<GenericResponse> post(@Valid @RequestBody PostDto postDto ) throws URISyntaxException {
        PostDto post = postService.post(postDto);
        return ResponseEntity.created(new URI("auth/post/"+post.getSlug()))
                .body(new GenericResponse("Great! Your post was successfully posted"));

    }

    @PutMapping("{slug}")
    public ResponseEntity<GenericResponse> post(@Valid @RequestBody PostDto postDto, @PathVariable String slug) {
        postService.updatePost(slug, postDto);
        return ResponseEntity.ok(new GenericResponse("Action done!"));

    }

    @DeleteMapping("{slug}")
    public ResponseEntity<GenericResponse> deletePost(@PathVariable String slug) {
        postService.deletePost(slug);
        return ResponseEntity.ok(new GenericResponse("Action done!"));
    }


}

