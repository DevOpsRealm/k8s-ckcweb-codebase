package ph.edu.ckc.k8sckcbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ph.edu.ckc.k8sckcbackend.dto.GenericResponse;
import ph.edu.ckc.k8sckcbackend.dto.MenuDto;
import ph.edu.ckc.k8sckcbackend.dto.TransferObjectMapper;
import ph.edu.ckc.k8sckcbackend.entities.Menu;
import ph.edu.ckc.k8sckcbackend.services.LinksService;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth/menu")
public class MenuController {

    private final LinksService linksService;
    private final TransferObjectMapper mapper;

    @PostMapping
    public ResponseEntity<GenericResponse> saveLinks(@Valid @RequestBody MenuDto menuDto) throws URISyntaxException  {
        Menu menuServiceResponse = linksService.saveLinks(menuDto);
        return ResponseEntity
                .created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{uuid}").buildAndExpand(menuServiceResponse.getUuid()).toUri())
                .body(new GenericResponse("Great! This is just a first step to create pages."));
    }

    @PutMapping("{uuid}")
    public ResponseEntity<GenericResponse> updateMenu(@PathVariable String uuid, @Valid @RequestBody MenuDto menuDto) {
        Menu menu = mapper.menuDtoToMenuEntity(menuDto);
        linksService.recursiveUpdateLinks(uuid, menu);
        return ResponseEntity.ok()
                .body(new GenericResponse("Action done!"));
    }

    @DeleteMapping("{uuid}")
    public ResponseEntity<GenericResponse> deleteMenu(@PathVariable String uuid) {
        linksService.deleteMenu(uuid);
        return ResponseEntity.ok()
                .body(new GenericResponse("Action done!"));
    }


    @GetMapping
    public ResponseEntity<List<MenuDto>> getAllMenus() {
        return ResponseEntity.ok(linksService.getAllMenu());
    }

    @GetMapping("{uuid}")
    public ResponseEntity<MenuDto> getMenuByUUID(@PathVariable String uuid) {
        return ResponseEntity.ok(linksService.getMenuByUUID(uuid));
    }
}
