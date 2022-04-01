package ph.edu.ckc.k8sckcbackend.entities;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import ph.edu.ckc.k8sckcbackend.util.IdGenerator;


import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Entity
public class Post implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String excerpt;

    private String slug;
    private String keywords;


    @Lob
    private String content;

    private Boolean overrideDefaults;
    private Boolean staticPage;

    @CreationTimestamp
    private LocalDateTime postedAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;


    @DateTimeFormat(pattern = "MM/dd/yyyy")
    @CreationTimestamp
    private LocalDate datePath;

    private String uuid;

    @PrePersist
    public void prePersist() {
        if(Objects.isNull(overrideDefaults))  this.setOverrideDefaults(false);
        if(Objects.isNull(staticPage))   this.setStaticPage(false);
        this.setUuid(IdGenerator.generateIdForSlugV2(40));
    }

    @PreUpdate
    public void preUpdate() {
        if(Objects.isNull(overrideDefaults))  this.setOverrideDefaults(false);
        if(Objects.isNull(staticPage))   this.setStaticPage(false);
    }

}
