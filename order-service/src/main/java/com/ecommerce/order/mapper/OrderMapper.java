package com.ecommerce.order.mapper;

import com.ecommerce.order.dto.OrderItemRequest;
import com.ecommerce.order.dto.OrderItemResponse;
import com.ecommerce.order.dto.OrderResponse;
import com.ecommerce.order.dto.ProductInfoResponse;
import com.ecommerce.order.entity.CustomerOrder;
import com.ecommerce.order.entity.OrderItem;
import com.ecommerce.order.entity.OrderStatus;

import java.math.BigDecimal;
import java.util.List;

/**
 * Mapper som konverterar mellan DTOs och entities.
 */
public class OrderMapper {

    /**
     * Skapar CustomerOrder entity från userId och färdiga orderrader.
     */
    public static CustomerOrder toEntity(Long userId, List<OrderItem> items) {

        BigDecimal totalAmount = items.stream()
                .map(OrderItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return CustomerOrder.builder()
                .userId(userId)
                .status(OrderStatus.PENDING)
                .totalAmount(totalAmount)
                .items(items)
                .build();
    }

    /**
     * Skapar en OrderItem utifrån request-data och produktdata från product-service.
     */
    public static OrderItem toOrderItem(
            OrderItemRequest request,
            ProductInfoResponse product
    ) {
        BigDecimal subtotal = product.getPrice()
                .multiply(BigDecimal.valueOf(request.getQuantity()));

        return OrderItem.builder()
                .productId(product.getId())
                .productName(product.getName())
                .quantity(request.getQuantity())
                .unitPrice(product.getPrice())
                .subtotal(subtotal)
                .build();
    }

    /**
     * Konverterar CustomerOrder entity till OrderResponse DTO.
     */
    public static OrderResponse toResponse(CustomerOrder order) {

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

    /**
     * Konverterar OrderItem entity till OrderItemResponse DTO.
     */
    private static OrderItemResponse toOrderItemResponse(OrderItem item) {

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