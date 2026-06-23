package com.ecommerce.order.mapper;

import java.util.List;

import com.ecommerce.order.dto.OrderItemRequest;
import com.ecommerce.order.dto.OrderItemResponse;
import com.ecommerce.order.dto.OrderRequest;
import com.ecommerce.order.dto.OrderResponse;
import com.ecommerce.order.entity.CustomerOrder;
import com.ecommerce.order.entity.OrderItem;
import com.ecommerce.order.entity.OrderStatus;

import java.math.BigDecimal;

//Mapper som konverterar mellan DTOs och entitiess
public class OrderMapper {

    //Konverterar OrderRequest till CustomerOrder entity
    public static CustomerOrder toEntity(OrderRequest request){
        List<OrderItem> items = request.getItems()
                .stream()
                .map(OrderMapper::toOrderItem)
                .toList();
        BigDecimal totalAmount = items.stream()
                .map(OrderItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return CustomerOrder.builder()
                .userId(request.getUserId())
                .status(OrderStatus.PENDING)
                .totalAmount(totalAmount)
                .items(items)
                .build();
    }

    //Konverterar OrderItemRequest till OrderItem entity
    private static OrderItem toOrderItem(OrderItemRequest request){
        BigDecimal subtotal = request.getUnitPrice()
                .multiply(BigDecimal.valueOf(request.getQuantity()));
        
        return OrderItem.builder()
                .productId(request.getProductId())
                .productName(request.getProductName())
                .quantity(request.getQuantity())
                .unitPrice(request.getUnitPrice())
                .subtotal(subtotal)
                .build();
    }

    //Konverterar CustomerOrder entity till OrderResponse DTO
    public static OrderResponse toResponse(CustomerOrder order){
        List<OrderItemResponse> itemResponses = order.getItems()
                .stream()
                .map(OrderMapper::toOrderItemResponse)
                .toList();
        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .status(order.getStatus().name())
                .totalAmount(order.getTotalAmount())
                .items(itemResponses)
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }

    //Konverterar OrderItem entity till OrderItemResponse DTO
    private static OrderItemResponse toOrderItemResponse(OrderItem item){
        return OrderItemResponse.builder()
                .id(item.getId())
                .productId(item.getProductId())
                .productName(item.getProductName())
                .quantity(item.getQuantity())
                .unitPrice(item.getUnitPrice())
                .subtotal(item.getSubtotal())
                .build();
    }
    
}
