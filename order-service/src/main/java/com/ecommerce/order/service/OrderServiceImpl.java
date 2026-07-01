package com.ecommerce.order.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.order.client.ProductClient;
import com.ecommerce.order.dto.OrderRequest;
import com.ecommerce.order.dto.OrderResponse;
import com.ecommerce.order.dto.ProductInfoResponse;
import com.ecommerce.order.entity.CustomerOrder;
import com.ecommerce.order.entity.OrderItem;
import com.ecommerce.order.entity.OrderStatus;
import com.ecommerce.order.exception.InvalidOrderStatusException;
import com.ecommerce.order.exception.OrderNotFoundException;
import com.ecommerce.order.mapper.OrderMapper;
import com.ecommerce.order.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final ProductClient productClient;

    //Skapar en nu order
    @Override
    public OrderResponse createOrder(Long userId,OrderRequest request) {
        List<OrderItem> items = request.getItems()
                .stream()
                .map(itemRequest -> {
                    ProductInfoResponse product =
                        productClient.getProductById(itemRequest.getProductId());
                    return OrderMapper.toOrderItem(itemRequest, product);
                })
                .toList();
                
        CustomerOrder order = OrderMapper.toEntity(userId, items);
        CustomerOrder savedOrder = orderRepository.save(order);
        return OrderMapper.toResponse(savedOrder);
    }

    //Hämtar alla orders
    @Override
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(OrderMapper::toResponse)
                .toList();
    }

    //Hämta order för en specifik id
    @Override
    public OrderResponse getOrderById(Long id, Long userId) {
        CustomerOrder order = orderRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new OrderNotFoundException(id));
        return OrderMapper.toResponse(order);
    }

    //Hämta alla orders för en användare
    @Override
    public List<OrderResponse> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId)
                .stream()
                .map(OrderMapper::toResponse)
                .toList();
    }
    
    public OrderResponse cancelOrder(Long id, Long userId){
        CustomerOrder order = orderRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new OrderNotFoundException(id));
        
        if (order.getStatus() != OrderStatus.PENDING) {
            throw new InvalidOrderStatusException(
                "Only orders with status PENDING can be cancelled"
            );
        }

        order.setStatus(OrderStatus.CANCELLED);
        CustomerOrder updatedOrder = orderRepository.save(order);
        return OrderMapper.toResponse(updatedOrder);

    }

}
