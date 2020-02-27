package rostyk.stupnytskiy.zepka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.zepka.entity.Cart;
import rostyk.stupnytskiy.zepka.entity.PublicationForPurchase;
import rostyk.stupnytskiy.zepka.repository.PublicationForPurchaseRepository;

import java.util.Set;

@Service
public class PublicationForPurchaseService {

    @Autowired
    private PublicationForPurchaseRepository pubForRurRepository;


    public Boolean save(PublicationForPurchase forPurchase) {
        Boolean audit = pubForRurRepository.existsByPublicationIdAndCart(forPurchase.getPublication().getId(), forPurchase.getCart());
        if(forPurchase.getPublication().getCount() != 0
                && forPurchase.getPublication().getCount() >= forPurchase.getCount()
                && !audit){
            pubForRurRepository.save(forPurchase);
        }
        return !audit;
    }

    public void update(PublicationForPurchase forPurchase) {
        if(forPurchase.getPublication().getCount() != 0
                && forPurchase.getPublication().getCount() >= forPurchase.getCount()){
            pubForRurRepository.save(forPurchase);
        }
    }

    public void delete(Long id , Cart cart){
        pubForRurRepository.delete(findByIdAndCart(id,cart));
    }

    public PublicationForPurchase findById(Long id){
        return pubForRurRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Publ with id " + id + " not exist"));
    }

    public PublicationForPurchase findByIdAndCart(Long id, Cart cart){
        return pubForRurRepository.findByIdAndCart(id,cart);
    }

}
