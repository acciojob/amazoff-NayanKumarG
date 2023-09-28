package com.driver;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    OrderRespository orderRepository = new OrderRespository();
    public void addOrder(Order order) {
        orderRepository.addOrder(order);
    }

    public void addPartner(String partnerId) {
        orderRepository.addPartner(partnerId);
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {

        orderRepository.addOrderPartnerPair(orderId, partnerId);
    }


    public Order getOrderById(String orderId) {

        return orderRepository.getOrderById(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId) {

        return orderRepository.getPartnerById(partnerId);
    }

    public Integer getOrderCountByPartnerId(String partnerId) {

        Integer orderCount = orderRepository.getOrderCountByPartnerId(partnerId);

        if(orderCount == null) return 0;
        else return orderCount;
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        return orderRepository.getOrdersByPartnerId(partnerId);
    }

    public List<String> getAllOrders() {
        return orderRepository.getAllOrders();
    }

    public Integer getCountOfUnassignedOrders() {

        return orderRepository.getCountOfUnassignedOrders();
    }


    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {

        return orderRepository.getOrdersLeftAfterGivenTimeByPartnerId(time, partnerId);
    }

    public void deletePartnerById(String partnerId) {
        orderRepository.deletePartnerById(partnerId);
    }

    public void deleteOrderById(String orderId) {
        orderRepository.deleteOrderById(orderId);
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {

        int time = orderRepository.getLastDeliveryTimeByPartnerId(partnerId);
        System.out.println(time);
        String string = "";

        int h = time / 60;

        int m = time % 60;

        if (h < 10) {
            string += "0" + h;
        } else {
            string += h;
        }
        string += ':';

        if (m < 10) {
            string += "0" + m;
        } else {
            string += m;
        }

        return string;
    }
}