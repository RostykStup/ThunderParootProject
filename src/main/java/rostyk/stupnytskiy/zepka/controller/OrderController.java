package rostyk.stupnytskiy.zepka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rostyk.stupnytskiy.zepka.dto.request.PaginationRequest;
import rostyk.stupnytskiy.zepka.dto.response.OrderResponse;
import rostyk.stupnytskiy.zepka.dto.response.PageResponse;
import rostyk.stupnytskiy.zepka.service.OrderService;

@CrossOrigin
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PutMapping("/deliver")
    public Boolean sentOrder(Long id){
        return orderService.setSent(id);
    }

    @PutMapping("/view")
    public void viewOrder(Long id){
         orderService.setViewed(id);
    }

    @GetMapping
    public PageResponse<OrderResponse> getOrders(PaginationRequest request) {
        return orderService.getUserOrders(request);
    }

}

