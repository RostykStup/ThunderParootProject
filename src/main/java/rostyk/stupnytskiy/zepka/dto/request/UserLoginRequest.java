package rostyk.stupnytskiy.zepka.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class UserLoginRequest {
    @NotBlank
    private String login;

    @Size(min = 3, max = 30)
    private String password;

//    @NotEmpty//@Size(min = 1)
//    private List<Long> favoritesIds;
}
