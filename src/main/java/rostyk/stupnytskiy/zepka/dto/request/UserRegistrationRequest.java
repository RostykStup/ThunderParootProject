package rostyk.stupnytskiy.zepka.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserRegistrationRequest {
    @NotBlank
    @NotNull
    private String login;

    @Size(min = 3, max = 30)
    private String password;

    @NotBlank
    @NotNull
    private String username;

    private String image;

    private String phoneNumber;
}
