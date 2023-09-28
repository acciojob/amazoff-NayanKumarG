package com.driver;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.*;

@Repository
public class OrderRepository {
    private HashMap<String , Order> orders;
    private HashMap<String , DeliveryPartner> partners;
    private HashMap<String, List<String>> partnerOrderHashMap;
    private HashMap<String, String> orderPartnerHashMap;
    private Integer orderAssignedToPartner;

    public OrderRepository() {
        orders = new HashMap<>();
        partners = new HashMap<>();
        partnerOrderHashMap = new HashMap<>();
        orderPartnerHashMap = new HashMap<>();
        orderAssignedToPartner = 0;
    }

    public void addOrder(Order order) {

        orders.put(order.getId(), order);

    }

    public void addPartner(String partnerId) {
        DeliveryPartner deliveryPartner = new DeliveryPartner(partnerId);
        partnerOrderHashMap.put(partnerId, new ArrayList<>());
        partners.put(partnerId, deliveryPartner);
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        if(orders.containsKey(orderId) && partners.containsKey(partnerId)) {
            partnerOrderHashMap.get(partnerId).add(orderId);
            partners.get(partnerId).setNumberOfOrders(partners.get(partnerId).getNumberOfOrders() + 1);

            orderPartnerHashMap.put(orderId, partnerId);
            orderAssignedToPartner++;
        }
    }

    public Order getOrderById(String orderId) {
        return orders.get(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        return partners.get(partnerId);
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        return partners.get(partnerId).getNumberOfOrders();
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        return partnerOrderHashMap.get(partnerId);
    }

    public List<String> getAllOrders() {
        return new ArrayList<>(orders.keySet());
    }

    public Integer getCountOfUnassignedOrders() {
        return orders.size() - orderAssignedToPartner;
    }


    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {

        //DeliveryPartner partner = partners.get(partnerId);
        List<String> orderIds = partnerOrderHashMap.get(partnerId);
        Integer orderLeft = 0;

        int t = Integer.parseInt(time.substring(0, 2))*60 + Integer.parseInt(time.substring(3));

        for(String orderId : orderIds) {
            if(orders.get(orderId).getDeliveryTime() > t) {
                orderLeft++;
            }
        }

        return orderLeft;
    }

    public void deletePartnerById(String partnerId) {
        int numberOfOrder = partners.get(partnerId).getNumberOfOrders();
        orderAssignedToPartner -= numberOfOrder;
        partnerOrderHashMap.remove(partnerId);
        partners.remove(partnerId);

    }

    public void deleteOrderById(String orderId) {


        if(orderPartnerHashMap.containsKey(orderId)) {
            String partnerId = orderPartnerHashMap.get(orderId);
            orderPartnerHashMap.remove(orderId);
            List<String> orderList = partnerOrderHashMap.get(partnerId);
            int idx = 0;
            for (int i = 0; i < orderList.size(); i++) {
                if (orderList.get(i).equals(orderId)) {
                    idx = i;
                    break;
                }
            }
            orderList.remove(idx);

            partners.get(partnerId).setNumberOfOrders(partners.get(partnerId).getNumberOfOrders() - 1);
            orderAssignedToPartner--;
        }


        orders.remove(orderId);
    }


    public int getLastDeliveryTimeByPartnerId(String partnerId) {
        List<String> ordersId = partnerOrderHashMap.get(partnerId);
        int time = 0;

        for(String orderId : ordersId) {
            int t = orders.get(orderId).getDeliveryTime();
            if(time < t) {
                time = t;
            }
        }

        return time;
    }
}
