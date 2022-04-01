package ph.edu.ckc.k8sckcbackend.dto;


import org.mapstruct.*;
import ph.edu.ckc.k8sckcbackend.entities.Menu;
import ph.edu.ckc.k8sckcbackend.entities.Post;
import ph.edu.ckc.k8sckcbackend.entities.Users;


@Mapper(
    componentModel = "spring"
)
public interface TransferObjectMapper {

    @Mappings({
            @Mapping(target = "uuid", source =              "post.uuid"),
            @Mapping(target = "title", source =             "post.title"),
            @Mapping(target = "excerpt", source =           "post.excerpt"),
            @Mapping(target = "content", source =           "post.content"),
            @Mapping(target = "slug", source =              "post.slug"),
            @Mapping(target = "keywords", source =          "post.keywords"),
            @Mapping(target = "overrideDefaults", source =  "post.overrideDefaults"),
            @Mapping(target = "staticPage", source =        "post.staticPage"),
            @Mapping(target = "createdAt", source =         "post.postedAt"),
            @Mapping(target = "updatedAt", source =         "post.updatedAt"),
    })
    PostDto postEntityToPostDto(Post post);

    @InheritInverseConfiguration(
            name = "postEntityToPostDto"
    )
    Post postDtoToPostEntity(PostDto postDto);



    @Mappings({
            @Mapping(target = "description", source = "menu.description"),
            @Mapping(target = "url", source = "menu.url"),
            @Mapping(target = "uuid", source = "menu.uuid"),
            @Mapping(target = "primaryMenu", source = "menu.primaryMenu"),
            @Mapping(target = "children", source = "menu.children"),
            @Mapping(target = "createdAt", source = "menu.createdAt"),
            @Mapping(target = "updatedAt", source = "menu.updatedAt")
    })
    MenuDto menuEntityToMenuDto(Menu menu);



    @InheritInverseConfiguration(
            name = "menuEntityToMenuDto"
    )
    Menu menuDtoToMenuEntity(MenuDto menuDto);




}
