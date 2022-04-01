package ph.edu.ckc.k8sckcbackend.dto;

import com.fasterxml.jackson.annotation.*;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties
public class MenuDto  {

    private String uuid;

    @NotEmpty(message = "{description.NotEmpty}")
    @Size(min = 5, max = 255, message = "{description.Size}")
    private String description;

    @NotEmpty(message = "{url.NotEmpty}")
    @Size(min = 5, max = 255, message = "{url.Size}")
    private String url;

    private Boolean primaryMenu;

    @Valid
    private Set<MenuDto> children = new HashSet<>();

    @JsonFormat(pattern = "MM-dd-yyyy")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "MM-dd-yyyy")
    private LocalDateTime updatedAt;

}
