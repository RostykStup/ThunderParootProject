package rostyk.stupnytskiy.zepka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.zepka.dto.request.PaginationRequest;
import rostyk.stupnytskiy.zepka.dto.request.UserLoginRequest;
import rostyk.stupnytskiy.zepka.dto.request.UserRegistrationRequest;
import rostyk.stupnytskiy.zepka.dto.response.*;
import rostyk.stupnytskiy.zepka.entity.*;
import rostyk.stupnytskiy.zepka.repository.UserRepository;
import rostyk.stupnytskiy.zepka.security.JwtTokenTool;
import rostyk.stupnytskiy.zepka.security.JwtUser;
import rostyk.stupnytskiy.zepka.tools.FileTool;

import java.io.IOException;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileTool fileTool;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenTool jwtTokenTool;

    @Autowired
    private PublicationService publicationService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private PurchaseService purchaseService;



    public AuthenticationResponse register(UserRegistrationRequest request) throws IOException {
        if (userRepository.existsByLogin(request.getLogin())) {
            throw new BadCredentialsException("User with username " + request.getLogin() + " already exists");
        }
        User user = new User();
        user.setLogin(request.getLogin());
        user.setUserRole(UserRole.ROLE_USER);
        user.setPassword(encoder.encode(request.getPassword()));
        user.setUsername(request.getUsername());
        user.setImageName(request.getImage());
        user.setPhone_number(request.getPhoneNumber());
        if (request.getImage() != null) {
            user.setImageName(fileTool.saveFile(request.getImage()));
        }
        user.setCart(new Cart());
        user.setAddress(new Address());
        userRepository.save(user);
        return login(registrationToLogin(request));
    }

    public AuthenticationResponse login(UserLoginRequest request) {
        String login = request.getLogin();
        User user = findByLogin(login);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, request.getPassword()));
        String token = jwtTokenTool.createToken(login, user.getUserRole());
        String name = user.getUsername();
        Long id = user.getId();
        return new AuthenticationResponse(name,token,id);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = findByLogin(login);
        return new JwtUser(user.getLogin(), user.getUserRole(), user.getPassword());
    }

    public User findByLogin(String username)  {
        return userRepository.findByLogin(username).orElseThrow(() -> new UsernameNotFoundException("User with login " + username + " not exists"));
    }

    public User findById(Long id)  {
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User with id " + id + " not exists"));
    }

    public UserResponseWithAddress getMyProfile() {
        User user = findByLogin((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return new UserResponseWithAddress(user);
    }

    private UserLoginRequest registrationToLogin(UserRegistrationRequest registrationRequest){
        UserLoginRequest loginRequest = new UserLoginRequest();
        loginRequest.setLogin(registrationRequest.getLogin());
        loginRequest.setPassword(registrationRequest.getPassword());
        return loginRequest;
    }

    public PageResponse<PublicationResponse> getUserPublications(PaginationRequest request){
        final String login = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final Page<Publication> page = publicationService.findByLogin(login,request);
        return new PageResponse<>(page.get()
        .map(PublicationResponse::new)
        .collect(Collectors.toList()),
        page.getTotalElements(),
        page.getTotalPages());
    }

    public UserResponse getUserById(Long id) {
        return new UserResponse(findById(id));
    }
}
