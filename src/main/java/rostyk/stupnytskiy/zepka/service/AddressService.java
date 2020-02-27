package rostyk.stupnytskiy.zepka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.zepka.dto.request.AddressRequest;
import rostyk.stupnytskiy.zepka.dto.response.AddressResponse;
import rostyk.stupnytskiy.zepka.entity.Address;
import rostyk.stupnytskiy.zepka.entity.User;
import rostyk.stupnytskiy.zepka.repository.AddressRepository;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserService userService;

    public void update(AddressRequest request){
        Address address = addressRepository.findByUserLogin((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        address.setCity(request.getCity());
        address.setStreet(request.getStreet());
        address.setCountry(request.getCountry());
        addressRepository.save(address);
    }

    public AddressResponse getAddress() {
        User user = userService.findByLogin((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return new AddressResponse(user.getAddress());
    }

}
