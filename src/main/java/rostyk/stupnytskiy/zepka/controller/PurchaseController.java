package rostyk.stupnytskiy.zepka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rostyk.stupnytskiy.zepka.dto.request.PaginationRequest;
import rostyk.stupnytskiy.zepka.dto.request.PurchaseRequest;
import rostyk.stupnytskiy.zepka.dto.request.UserLoginRequest;
import rostyk.stupnytskiy.zepka.dto.request.UserRegistrationRequest;
import rostyk.stupnytskiy.zepka.dto.response.*;
import rostyk.stupnytskiy.zepka.service.PurchaseService;
import rostyk.stupnytskiy.zepka.service.UserService;

import javax.validation.Valid;
import java.io.IOException;

//import rostyk.stupnytskiy.zepka.dto.request.AddToCartRequest;

@CrossOrigin
@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;


    @PostMapping()
    public void makePurchase(@RequestBody PurchaseRequest request){
        purchaseService.createPurchase(request);
    }

    @PutMapping("/cancel")
    public Boolean updatePurchase(Long id){
        return purchaseService.cancelPurchase(id);
    }

    @PutMapping("/confirm")
    public Boolean confirmPurchase(Long id){
        return purchaseService.confirmPurchase(id);
    }

    @GetMapping
    public PageResponse<PurchaseResponse> getPurchases(PaginationRequest request){
        return purchaseService.getUserPurchases(request);
    }
}
