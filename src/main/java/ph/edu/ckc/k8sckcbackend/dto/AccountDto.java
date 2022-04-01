package ph.edu.ckc.k8sckcbackend.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {

    @NotEmpty(message = "{username.NotEmpty}")
    @Size(min = 2, max = 255, message = "{username.Size}")
    private String username;

    @NotEmpty(message = "{password.NotEmpty}")
    @Size(min = 5, max = 255, message = "{password.Size}")
    private String password;


    @NotEmpty(message = "{fullName.NotEmpty}")
    @Size(min = 2, max = 255, message = "{fullName.Size}")
    private String fullName;


    @NotEmpty(message = "{designation.NotEmpty}")
    @Size(min = 2, max = 255, message = "{designation.Size}")
    private String designation;


    @NotEmpty(message = "{roles.NotEmpty}")
    @Size(min = 2, max = 255, message = "{roles.Size}")
    private String roles;

}
