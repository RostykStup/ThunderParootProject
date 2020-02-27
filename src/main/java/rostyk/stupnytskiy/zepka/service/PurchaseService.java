package rostyk.stupnytskiy.zepka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.zepka.dto.request.PaginationRequest;
import rostyk.stupnytskiy.zepka.dto.request.PurchaseRequest;
import rostyk.stupnytskiy.zepka.dto.response.PageResponse;
import rostyk.stupnytskiy.zepka.dto.response.PurchaseResponse;
import rostyk.stupnytskiy.zepka.entity.*;
import rostyk.stupnytskiy.zepka.repository.PurchaseRepository;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private PublicationForPurchaseService publicationForPurchaseService;

    @Autowired
    private UserService userService;

    @Autowired
    private PublicationService publicationService;


    public void createPurchase(PurchaseRequest request) {
        User shopper = userService.findByLogin((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Set<PublicationForPurchase> fullPubForPurSet = requestToSetPubForPur(request);
        Set<User> sellers = getSellersFromRequest(request);
        for (User seller : sellers) {
            Purchase purchase = buildFullPurchase(seller,shopper,fullPubForPurSet);
            purchase.setOrder(Order.builder().isView(false).user(seller).build());
            purchaseRepository.save(purchase);
        }
    }

    private Purchase buildFullPurchase(User seller, User shopper, Set<PublicationForPurchase> fullSet){
        Purchase purchase = new Purchase();
        purchase.setCreationDate(LocalDateTime.now());
        purchase.setAddress(shopper.getAddress());
        purchase.setSent(false);
        purchase.setFinished(false);
        purchase.setCancelled(false);
        purchase.setPublicationsForPurchase(getPubForPurByUser(seller, fullSet));
        purchase.setUser(shopper);
        purchase.setPrice(getPurchasePrice(purchase.getPublicationsForPurchase()));
        System.out.println(reduceCountOfPublication(purchase.getPublicationsForPurchase()));
        if (reduceCountOfPublication(purchase.getPublicationsForPurchase())
        && !seller.getLogin().equals(shopper.getLogin())) return purchase;
        return null;
    }

    public Boolean cancelPurchase(Long id){
        Purchase purchase = findById(id);
        if(!purchase.getSent()) {
            purchase.setCancelled(false);
            purchaseRepository.save(purchase);
            return true;
        } else return false;
    }

    public Boolean confirmPurchase(Long id){
        Purchase purchase = findById(id);
        if(purchase.getSent() && !purchase.getCancelled()) {
            purchase.setFinished(true);
            purchaseRepository.save(purchase);
            return true;
        } return false;
    }
 
    public Page<Purchase> findByLogin(String login, PaginationRequest request) {
        return purchaseRepository.findAllByUserLogin(login, request.mapToPageable());
    }

    public PageResponse<PurchaseResponse> getUserPurchases(PaginationRequest request){
        final String login = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final Page<Purchase> page = findByLogin(login,request);
        return new PageResponse<>(page.get()
                .map(PurchaseResponse::new)
                .collect(Collectors.toList()),
                page.getTotalElements(),
                page.getTotalPages());
    }

    private Set<PublicationForPurchase> requestToSetPubForPur(PurchaseRequest request){
        return request.getPublicationsForPurchaseId()
                .stream()
                .map(id -> publicationForPurchaseService.findById(id))
                .collect(Collectors.toSet());
    }

    private Set<User> getSellersFromRequest(PurchaseRequest request){
        return  requestToSetPubForPur(request).stream().map(p -> p.getPublication().getUser()).collect(Collectors.toSet());
    }

    private Double getPurchasePrice(Set<PublicationForPurchase> purchaseSet){
        double price = 0.0;
        for (PublicationForPurchase forPurchase : purchaseSet) {
            price+=(forPurchase.getPublication().getPrice() * forPurchase.getCount());
        }
        return price;
    }

    private Boolean reduceCountOfPublication(Set<PublicationForPurchase> fullSet) {
        boolean audit = true;
        for (PublicationForPurchase p : fullSet) {
            if (publicationService.reducePublicationCount(p.getCount(),p.getPublication().getId())) {
                p.setCart(null);
                publicationForPurchaseService.save(p);
            } else audit = false;
        }
        return audit;
    }

    private Set<PublicationForPurchase> getPubForPurByUser(User user, Set<PublicationForPurchase> fullSet){
        return fullSet.stream().filter( p -> p.getPublication().getUser().getLogin().equals(user.getLogin())).collect(Collectors.toSet());
    }

    private Purchase findById(Long id){
        return purchaseRepository.findById(id).orElseThrow( () -> new IllegalArgumentException("not exist"));
    }
}
