package ph.edu.ckc.k8sckcbackend.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ph.edu.ckc.k8sckcbackend.util.IdGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Menu implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private String url;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parent", orphanRemoval = true)
    private Set<Menu> children = new HashSet<>();

    @ManyToOne
    @JsonBackReference
    private Menu parent;

    private Boolean primaryMenu;


    public void setupParent(Menu menu) {
        this.getChildren().forEach(menu1 -> menu1.setParent(this));
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Menu)) return false;
        return id != null && id.equals(((Menu) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


    @CreationTimestamp
    private LocalDateTime createdAt;


    @UpdateTimestamp
    private LocalDateTime updatedAt;


    private String uuid;

    @PrePersist
    public void onSave() {
        if(Objects.isNull(primaryMenu)) this.setPrimaryMenu(false);
        this.setUuid(IdGenerator.generateIdForSlugV2(40));
    }
}
