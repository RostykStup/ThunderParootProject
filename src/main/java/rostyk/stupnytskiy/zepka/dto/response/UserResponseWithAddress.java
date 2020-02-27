package rostyk.stupnytskiy.zepka.dto.response;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.zepka.entity.Address;
import rostyk.stupnytskiy.zepka.entity.Order;
import rostyk.stupnytskiy.zepka.entity.Publication;
import rostyk.stupnytskiy.zepka.entity.User;

import java.util.List;

@Getter
@Setter
public class UserResponseWithAddress {
    private String username;
    private String phone;
    private String imageName;
    private AddressResponse address;


    public UserResponseWithAddress(User user) {
        this.username = user.getUsername();
        this.imageName = user.getImageName();
        this.address = new AddressResponse(user.getAddress());
        this.phone = user.getPhone_number();
    }
}
