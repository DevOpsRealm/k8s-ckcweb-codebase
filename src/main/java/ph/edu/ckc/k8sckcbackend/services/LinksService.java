package ph.edu.ckc.k8sckcbackend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ph.edu.ckc.k8sckcbackend.dto.MenuDto;
import ph.edu.ckc.k8sckcbackend.dto.TransferObjectMapper;
import ph.edu.ckc.k8sckcbackend.entities.Menu;
import ph.edu.ckc.k8sckcbackend.exception.ResourceNotFoundException;
import ph.edu.ckc.k8sckcbackend.repository.MenuRepository;
import ph.edu.ckc.k8sckcbackend.util.IdGenerator;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LinksService {

    private final MenuRepository menuRepository;
    private final TransferObjectMapper mapper;


    @Transactional
    public Menu saveLinks(@NotNull MenuDto menuDto) {
        Menu buildLinks = mapper.menuDtoToMenuEntity(menuDto);
        Menu menu = recursiveUrlChecker(buildLinks);
        return menuRepository.save(menu);

    }

    private Menu recursiveUrlChecker(@NotNull Menu links) {
        menuRepository.findAllByUrl(links.getUrl()).stream().findFirst()
                .ifPresent(menu -> links.setUrl(  String.format("%s-%s", links.getUrl(), IdGenerator.generateIdForSlugV2(20))  ));

        links.getChildren().forEach(menu -> {
            menu.setParent(links);
            recursiveUrlChecker(menu);
        });
        return links;
    }


    @Transactional
    public Menu recursiveUpdateLinks(@NotNull String uuid, @NotNull Menu links) {

        menuRepository.findAllByUrl(links.getUrl()).stream().findFirst()
                .ifPresent(menu -> links.setUrl(  String.format("%s-%s", links.getUrl(), IdGenerator.generateIdForSlugV2(20)) ));

        return menuRepository.findByUuid(uuid)
                .map(menu -> {
                    menu.setDescription(links.getDescription());
                    menu.setUrl(links.getUrl());
                    menu.setPrimaryMenu(links.getPrimaryMenu());
                    menu.setupParent(links);
                    menuRepository.save(menu);
                    links.getChildren().forEach( eachChildren -> recursiveUpdateLinks(eachChildren.getUuid(), eachChildren));
                    return menu;
                }).orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
    }

    public void deleteMenu(@NotNull String uuid) {
        Menu menuToDelete = menuRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
        menuRepository.delete(menuToDelete);
    }


    public List<MenuDto> getAllMenu() {
        return menuRepository
                .findAll().stream()
                .filter(menu -> Objects.isNull(menu.getParent()))
                .map(this::menuMapperBuilder).collect(Collectors.toList());
    }

    public MenuDto getMenuByUUID(@NotNull String uuid) {
        return menuRepository.findByUuid(uuid)
                .map(this::menuMapperBuilder)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
    }

    private MenuDto menuMapperBuilder(@NotNull Menu menu) {
        MenuDto menuDto = new MenuDto();
        menuDto.setDescription(menu.getDescription());
        menuDto.setUrl(menu.getUrl());
        menuDto.setPrimaryMenu(menu.getPrimaryMenu());
        menuDto.setCreatedAt(menu.getCreatedAt());
        menuDto.setUpdatedAt(menu.getUpdatedAt());
        menuDto.setUuid(menu.getUuid());
        menuDto.getChildren().addAll(menu.getChildren().stream().map(this::menuMapperBuilder).collect(Collectors.toList()));
        return menuDto;

    }


}
