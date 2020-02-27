package rostyk.stupnytskiy.zepka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rostyk.stupnytskiy.zepka.dto.request.CartRequest;
import rostyk.stupnytskiy.zepka.dto.response.CartResponse;
import rostyk.stupnytskiy.zepka.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping()
    public CartResponse getCart(){
         return cartService.getCart();
    }

    @PostMapping()
    public Boolean addToCart(@RequestBody CartRequest cartRequest){
        return cartService.addToCart(cartRequest);
    }

    @PutMapping()
    public void updateCart(Long id, @RequestBody CartRequest cartRequest){
        cartService.updateCart(id, cartRequest);
    }

    @DeleteMapping()
    public void deleteFromCart(Long id){
        cartService.deleteFromCart(id);
    }
}
