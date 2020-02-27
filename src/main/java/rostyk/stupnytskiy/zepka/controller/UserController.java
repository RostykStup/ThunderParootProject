package rostyk.stupnytskiy.zepka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import rostyk.stupnytskiy.zepka.dto.request.*;
import rostyk.stupnytskiy.zepka.dto.response.*;
import rostyk.stupnytskiy.zepka.service.AddressService;
import rostyk.stupnytskiy.zepka.service.PurchaseService;
import rostyk.stupnytskiy.zepka.service.UserService;

import javax.validation.Valid;
import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private AddressService addressService;

    @PostMapping("/login")
    public AuthenticationResponse login(@Valid @RequestBody UserLoginRequest request) {
        return userService.login(request);
    }

    @PostMapping("/register")
    public AuthenticationResponse register(@Valid @RequestBody UserRegistrationRequest request) throws IOException {
        return userService.register(request);
    }

    @GetMapping("/my-profile")
    public UserResponseWithAddress getMyProfile(){
        return userService.getMyProfile();
    }

    @GetMapping()
    public UserResponse getUserProfile(Long id){
        return userService.getUserById(id);
    }

    @GetMapping("/checkToken")
    public void checkToken() {
    }

    @GetMapping("/publications")
    public PageResponse<PublicationResponse> getPublications( PaginationRequest request){
        return userService.getUserPublications(request);
    }

    @PostMapping("/changeAddress")
    public void updateAddress(@RequestBody AddressRequest request){
        addressService.update(request);
    }

    @GetMapping("/address")
    public AddressResponse getAddress(){
        return addressService.getAddress();
    }

////    @PreAuthorize("authentication.principal == #text && hasAnyRole('USER', 'ADMIN')")
//    @GetMapping("/test")
//    public void test(String text) {
//        System.out.println("find cart of " + text);
//
//    }
}
