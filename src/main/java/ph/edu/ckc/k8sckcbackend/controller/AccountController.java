package ph.edu.ckc.k8sckcbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ph.edu.ckc.k8sckcbackend.dto.AccountDto;
import ph.edu.ckc.k8sckcbackend.dto.GenericResponse;
import ph.edu.ckc.k8sckcbackend.services.AccountService;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("auth/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<GenericResponse> createAccount(@Valid @RequestBody AccountDto accountDto) throws URISyntaxException {
        String account = accountService.createAccount(accountDto);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{username}").buildAndExpand(account).toUri())
                .body(new GenericResponse("Account was successfully created!"));
    }


    @PutMapping("{username}")
    public ResponseEntity<GenericResponse> updateAccount(@Valid @RequestBody AccountDto accountDto, @PathVariable String username) {
        accountService.updateAccount(username, accountDto);
        return ResponseEntity.ok()
                .body(new GenericResponse("Action done!"));
    }


    @DeleteMapping("{username}")
    public ResponseEntity<GenericResponse> deleteAccount(@PathVariable String username) {
        accountService.deleteAccount(username);
        return ResponseEntity.ok()
                .body(new GenericResponse("Action done!"));
    }


    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @GetMapping("{username}")
    public ResponseEntity<AccountDto> getMenuByUUID(@PathVariable String username) {
        return ResponseEntity.ok(accountService.getAccountByUsername(username));
    }



}
