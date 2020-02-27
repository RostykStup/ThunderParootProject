package rostyk.stupnytskiy.zepka.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AddressRequest {
    @NotNull
    private String country;
    @NotNull
    private String city;
    @NotNull
    private String street;
}
