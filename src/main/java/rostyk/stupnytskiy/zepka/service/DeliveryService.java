package rostyk.stupnytskiy.zepka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.zepka.dto.request.DeliveryRequest;
import rostyk.stupnytskiy.zepka.dto.request.PaginationRequest;
import rostyk.stupnytskiy.zepka.dto.response.DeliveryResponse;
import rostyk.stupnytskiy.zepka.dto.response.PageResponse;
import rostyk.stupnytskiy.zepka.entity.Delivery;
import rostyk.stupnytskiy.zepka.repository.DeliveryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    public void save(DeliveryRequest request){
        deliveryRepository.save(deliveryRequestToDelivery(request,null));
    }

    public void update(DeliveryRequest request, Long id){
        deliveryRepository.save(deliveryRequestToDelivery(request,findById(id)));
    }

    public void delete(Long id){
        deliveryRepository.delete(findById(id));
    }

    public List<DeliveryResponse> findAll(){
        return deliveryRepository.findAll()
                .stream()
                .map(DeliveryResponse::new)
                .collect(Collectors.toList());
    }

    public Delivery findById(Long id){
        return deliveryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Delivery with id " + id + " not exist"));
    }

    public PageResponse<DeliveryResponse> findAll(PaginationRequest paginationRequest){
        final Page<Delivery> page = deliveryRepository.findAll(paginationRequest.mapToPageable());
        return new PageResponse<>(page.getContent().stream().map(DeliveryResponse::new).collect(Collectors.toList()),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    private Delivery deliveryRequestToDelivery(DeliveryRequest request, Delivery delivery) {
        if (delivery == null) {
            delivery = new Delivery();
        }
        delivery.setName(request.getName());
        return delivery;
    }
}
