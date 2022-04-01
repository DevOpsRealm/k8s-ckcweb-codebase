package ph.edu.ckc.k8sckcbackend.dto;



import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostDto {

    private String uuid;

    @NotEmpty(message = "{Title.NotEmpty}")
    @Size(min = 5, max = 255, message = "{Title.Size}")
    private String title;

    @NotEmpty(message = "{Excerpt.NotEmpty}")
    @Size(min = 5, max = 255, message = "{Excerpt.Size}")
    private String excerpt;

    @NotEmpty(message = "{Content.NotEmpty}")
    @Size(min = 5, message = "{Content.Size}")
    private String content;

    private String slug;

    @NotEmpty(message = "{Keywords.NotEmpty}")
    @Size(min = 5, max = 255, message = "{Keywords.Size}")
    private String keywords;

    private Boolean overrideDefaults;
    private Boolean staticPage;


    @JsonFormat(pattern = "MM-dd-yyyy")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "MM-dd-yyyy")
    private LocalDateTime updatedAt;

}

