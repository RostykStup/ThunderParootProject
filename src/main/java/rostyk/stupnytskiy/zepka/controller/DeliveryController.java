package rostyk.stupnytskiy.zepka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rostyk.stupnytskiy.zepka.dto.request.CategoryRequest;
import rostyk.stupnytskiy.zepka.dto.request.DeliveryRequest;
import rostyk.stupnytskiy.zepka.dto.request.PaginationRequest;
import rostyk.stupnytskiy.zepka.dto.response.DeliveryResponse;
import rostyk.stupnytskiy.zepka.dto.response.PageResponse;
import rostyk.stupnytskiy.zepka.service.DeliveryService;


import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/delivery")
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    @PostMapping()
    public void save(@RequestBody DeliveryRequest request){
        deliveryService.save(request);
    }

    @PutMapping()
    public void update(Long id, @RequestBody DeliveryRequest request){
        deliveryService.update(request,id);
    }

    @GetMapping()
    public List<DeliveryResponse> findAll(){
        return deliveryService.findAll();
    }

    @DeleteMapping
    public void delete(Long id){
        deliveryService.delete(id);
    }

    @GetMapping("/")
    public PageResponse<DeliveryResponse> findAll(@Valid PaginationRequest request){
        return deliveryService.findAll(request);
    }
}

