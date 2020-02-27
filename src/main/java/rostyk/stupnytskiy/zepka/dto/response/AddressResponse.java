package rostyk.stupnytskiy.zepka.dto.response;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.zepka.entity.Address;

@Getter
@Setter
public class AddressResponse {
    private String country;
    private String city;
    private String street;

    public AddressResponse(Address address){
        country = address.getCountry();
        city = address.getCity();
        street = address.getStreet();
    }
}
