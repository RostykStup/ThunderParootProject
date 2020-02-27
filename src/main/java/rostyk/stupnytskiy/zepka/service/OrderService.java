package rostyk.stupnytskiy.zepka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.zepka.dto.request.PaginationRequest;
import rostyk.stupnytskiy.zepka.dto.response.OrderResponse;
import rostyk.stupnytskiy.zepka.dto.response.PageResponse;
import rostyk.stupnytskiy.zepka.entity.*;
import rostyk.stupnytskiy.zepka.repository.OrderRepository;
import rostyk.stupnytskiy.zepka.repository.PublicationRepository;

import java.time.LocalDateTime;
import java.util.stream.Collectors;


@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Boolean setSent(Long id){
        Order order = orderRepository.findByIdAndUserLogin(id,(String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if(!order.getPurchase().getCancelled()){
            order.getPurchase().setSent(true);
            orderRepository.save(order);
            return true;
        } else return false;
    }

    public void setViewed(Long id){
        Order order = orderRepository.findByIdAndUserLogin(id,(String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        order.setIsView(true);
        orderRepository.save(order);
    }

    public Page<Order> findByLogin(String login, PaginationRequest request){
        return orderRepository.findAllByUserLogin(login,request.mapToPageable());
    }

    public PageResponse<OrderResponse> getUserOrders(PaginationRequest request){
        final String login = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final Page<Order> page = findByLogin(login,request);
        return new PageResponse<>(page.get()
                .map(OrderResponse::new)
                .collect(Collectors.toList()),
                page.getTotalElements(),
                page.getTotalPages());
    }
}