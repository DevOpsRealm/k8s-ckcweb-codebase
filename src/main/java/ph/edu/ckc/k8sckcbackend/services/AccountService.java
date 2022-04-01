package ph.edu.ckc.k8sckcbackend.services;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ph.edu.ckc.k8sckcbackend.dto.AccountDto;
import ph.edu.ckc.k8sckcbackend.entities.Users;
import ph.edu.ckc.k8sckcbackend.exception.ResourceDuplicateException;
import ph.edu.ckc.k8sckcbackend.exception.ResourceNotFoundException;
import ph.edu.ckc.k8sckcbackend.repository.UsersRepository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountService(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String createAccount(@NotNull AccountDto accountDto) {
        if (usersRepository.existsByUsername(accountDto.getUsername()))
            throw new ResourceDuplicateException("Username already exist");

        Users users = new Users();
        users.setUsername(accountDto.getUsername());
        users.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        users.setRoles(accountDto.getRoles());
        users.setFullName(accountDto.getFullName());
        users.setDesignation(accountDto.getDesignation());
        usersRepository.save(users);

        return accountDto.getUsername();

    }


    public Boolean updateAccount(@NotNull String username, AccountDto accountDto) {
        return usersRepository.findByUsername(username)
                .map(users -> {
                    users.setPassword(passwordEncoder.encode(accountDto.getPassword()));
                    users.setRoles(accountDto.getRoles());
                    users.setFullName(accountDto.getFullName());
                    users.setDesignation(accountDto.getDesignation());
                    usersRepository.save(users);
                    return true;
                }).orElseThrow(() -> new ResourceNotFoundException("Username not found"));
    }

    public Boolean deleteAccount(@NotNull String username) {
        return usersRepository.findByUsername(username)
                .map(users -> {
                    usersRepository.delete(users);
                    return true;
                }).orElseThrow(() -> new ResourceNotFoundException("Username not found"));
    }

    public AccountDto getAccountByUsername(@NotNull String username) {
        return usersRepository.findByUsername(username)
                .map(this::toaccountDtoMapperObject).orElseThrow(() -> new ResourceNotFoundException("Username not found"));
    }

    private AccountDto toaccountDtoMapperObject(Users users) {
        AccountDto accountDto = new AccountDto();
        accountDto.setUsername(users.getUsername());
        accountDto.setRoles(users.getRoles());
        accountDto.setDesignation(users.getDesignation());
        accountDto.setFullName(users.getFullName());
        return accountDto;
    }

    public List<AccountDto> getAllAccounts() {
        return usersRepository.findAll().stream().map(this::toaccountDtoMapperObject).collect(Collectors.toList());
    }

}
