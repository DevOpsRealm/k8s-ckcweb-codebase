package ph.edu.ckc.k8sckcbackend;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ph.edu.ckc.k8sckcbackend.dto.GenericResponse;
import ph.edu.ckc.k8sckcbackend.dto.PostDto;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;

/*@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")*/
public class K8sCkcBackendApplicationTest {

    /*private static final String API_AUTH_ENDPOINT = "/auth/post";


    @Autowired
    TestRestTemplate testRestTemplate;

    @Qualifier("announcementRepository")
    @Autowired
    AnnouncementRepository announcementRepository;

    @Qualifier("latestUpdatesRepository")
    @Autowired
    LatestUpdatesRepository latestUpdatesRepository;


    @Before
    public void setup() {
        announcementRepository.deleteAll();
        latestUpdatesRepository.deleteAll();
    }


     @Test
    public void post_whenPostIsValid_receiveOk() {

        PostDto postDto = buildPost("ANNOUNCEMENT");

        ResponseEntity<?>
                response = testRestTemplate.postForEntity(API_AUTH_ENDPOINT, postDto, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void post_whenPostIsValid_savePostInDatabase() {
        PostDto postDto = buildPost( "LATEST_UPDATES");

        testRestTemplate.postForEntity(API_AUTH_ENDPOINT, postDto, Object.class);
        assertThat(announcementRepository.count()).isEqualTo(1L);
    }

    @Test
    public void post_whenPostIsValid_saveAnnouncementInDatabase() {
        PostDto postDto = buildPost("ANNOUNCEMENT");

        testRestTemplate.postForEntity(API_AUTH_ENDPOINT, postDto, Object.class);
        assertThat(announcementRepository.count()).isEqualTo(1);
        assertThat(announcementRepository.findAll().get(0).getTitle()).isEqualTo(postDto.getTitle());
    }




    @Test
    public void post_whenPostIsValid_saveLatestInDatabase() {
        PostDto postDto = buildPost("LATEST_UPDATES");

        testRestTemplate.postForEntity(API_AUTH_ENDPOINT, postDto, Object.class);
        assertThat(latestUpdatesRepository.count()).isEqualTo(1L);
        assertThat(latestUpdatesRepository.findAll().get(0).getTitle()).isEqualTo(postDto.getTitle());
    }

    @Test
    public void post_whenPostTitleIsNullOrEmpty_receiveBadRequest() {
        PostDto postDto = buildPost("LATEST_UPDATES");
        postDto.setTitle(null);

        ResponseEntity<Object> actual =  testRestTemplate.postForEntity(API_AUTH_ENDPOINT, postDto, Object.class);

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void post_whenPostTitleIsEmpty_receiveBadRequest() {
        PostDto postDto = buildPost("LATEST_UPDATES");
        postDto.setTitle("");

        ResponseEntity<Object> actual =  testRestTemplate.postForEntity(API_AUTH_ENDPOINT, postDto, Object.class);

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }


    @Test
    public void post_whenPostTitlelessThan5Characters_receiveBadRequest() {
        PostDto postDto = buildPost("LATEST_UPDATES");
        postDto.setTitle("asd");

        ResponseEntity<Object> actual =  testRestTemplate.postForEntity(API_AUTH_ENDPOINT, postDto, Object.class);

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void post_whenPostTitleGreaterThan255Characters_receiveBadRequest() {
        PostDto postDto = buildPost("LATEST_UPDATES");
        String val = IntStream.rangeClosed(1, 256).mapToObj(x -> "a").collect(Collectors.joining());
        postDto.setTitle(val);

        ResponseEntity<Object> actual =  testRestTemplate.postForEntity(API_AUTH_ENDPOINT, postDto, Object.class);

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }



    @Test
    public void post_whenPostExceptlessThan5Characters_receiveBadRequest() {
        PostDto postDto = buildPost("LATEST_UPDATES");
        postDto.setExcerpt("asd");

        ResponseEntity<Object> actual =  testRestTemplate.postForEntity(API_AUTH_ENDPOINT, postDto, Object.class);

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void post_whenPostExcerptGreaterThan255Characters_receiveBadRequest() {
        PostDto postDto = buildPost("LATEST_UPDATES");
        String val = IntStream.rangeClosed(1, 256).mapToObj(x -> "a").collect(Collectors.joining());
        postDto.setExcerpt(val);

        ResponseEntity<Object> actual =  testRestTemplate.postForEntity(API_AUTH_ENDPOINT, postDto, Object.class);

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }


    @Test
    public void post_whenPostContentIsNullOrEmpty_receiveBadRequest() {
        PostDto postDto = buildPost("LATEST_UPDATES");
        postDto.setContent(null);

        ResponseEntity<Object> actual =  testRestTemplate.postForEntity(API_AUTH_ENDPOINT, postDto, Object.class);

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void post_whenUsersIsvalid_thenReturnGenericResponse() {

        PostDto postDto = buildPost("LATEST_UPDATES");

        ResponseEntity<GenericResponse> actual =  testRestTemplate.postForEntity(API_AUTH_ENDPOINT, postDto, GenericResponse.class);

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(actual.getBody().getMessage()).isEqualTo("Great! your post was successfully posted");
    }


    @Test
    public void post_whenUsersProvideEmptyPostType_thenReturnBadrequest() {

        PostDto postDto = buildPost(null);

        ResponseEntity<GenericResponse> actual =  testRestTemplate.postForEntity(API_AUTH_ENDPOINT, postDto, GenericResponse.class);

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }


    @Test
    public void post_whenUsersProvideMustOnePostType_thenReturnBadrequest() {

        PostDto postDto = buildPost("ANNOUNCEMENT");

        ResponseEntity<GenericResponse> actual =  testRestTemplate.postForEntity(API_AUTH_ENDPOINT, postDto, GenericResponse.class);

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void post_whenUserProvideAnInvalidPostType_returnBadrequest() {
        PostDto postDto = buildPost("sdjashdj");
        ResponseEntity<GenericResponse> actual =  testRestTemplate.postForEntity(API_AUTH_ENDPOINT, postDto, GenericResponse.class);
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

    }

    @Test
    public void post_whenUserProvideAnOneValidAndOtherIsNotValid_thenReturnBadRequest() {
        PostDto postDto = buildPost( "announcement");
        ResponseEntity<GenericResponse> actual = testRestTemplate.postForEntity(API_AUTH_ENDPOINT, postDto, GenericResponse.class);
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void post_whenUserProvideValidTwoPostType_thenPass() {
        PostDto postDto = buildPost( "LATEST_UPDATES");
        ResponseEntity<GenericResponse> actual = testRestTemplate.postForEntity(API_AUTH_ENDPOINT, postDto, GenericResponse.class);
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }




    private PostDto buildPost(String postType) {
        return  new PostDto("title",
                "exceprot",
                "content",
                "slugs",
                postType);
    }*/

/*    private PostDto buildPost(ArrayList<PostType> postTypeList) {
        return  new PostDto("title",
                "exceprot",
                "content",
                "slugs", postTypeList);
    }*/
}
