package rostyk.stupnytskiy.zepka.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import rostyk.stupnytskiy.zepka.entity.Publication;
import rostyk.stupnytskiy.zepka.entity.User;

import java.util.List;

@Getter
@Setter
public class UserResponse {
    private String username;
    private String phone;
    private String imageName;


    public UserResponse(User user) {
        this.username = user.getUsername();
        this.imageName = user.getImageName();
        this.phone = user.getPhone_number();
    }
}
