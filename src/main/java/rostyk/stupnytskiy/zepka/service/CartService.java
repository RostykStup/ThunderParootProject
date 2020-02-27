package rostyk.stupnytskiy.zepka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.zepka.dto.request.CartRequest;
import rostyk.stupnytskiy.zepka.dto.response.CartResponse;
import rostyk.stupnytskiy.zepka.entity.Cart;
import rostyk.stupnytskiy.zepka.entity.PublicationForPurchase;
import rostyk.stupnytskiy.zepka.repository.CartRepository;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    PublicationService publicationService;

    @Autowired
    PublicationForPurchaseService pubForPurService;

    public CartResponse getCart() {
        Cart cart = cartRepository.findByUserLogin((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return new CartResponse(cart);
    }

    public Boolean addToCart(CartRequest request) {
        Cart cart = cartRepository.findByUserLogin((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if(publicationService.findById(request.getPublicationId()).getUser() == cart.getUser()) return false;
        return pubForPurService.save(PublicationForPurchase
                .builder()
                .publication(publicationService.findById(request.getPublicationId()))
                .cart(cart)
                .count(request.getCount())
                .build());
    }

    public void updateCart(Long id, CartRequest request) {
        Cart cart = cartRepository.findByUserLogin((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        PublicationForPurchase p = pubForPurService.findById(id);
        p.setCount(request.getCount());
        p.setCart(cart);
        pubForPurService.update(p);
    }

    public void deleteFromCart(Long id) {
        Cart cart = cartRepository.findByUserLogin((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        pubForPurService.delete(id,cart);
    }
}
