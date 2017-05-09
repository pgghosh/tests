package com.suisse.credit.shopping;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import static com.suisse.credit.shopping.OfferUtils.noOfferCost;
import static com.suisse.credit.shopping.ShoppingItem.offers;
import static com.suisse.credit.shopping.ShoppingItem.priceList;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

/**
 * This class uses Java 8 features to provide an elegant solution.
 */
public class ShoppingBasket {

    public Double totalCost(final List<String> shoppingList) {

        if (shoppingList == null || shoppingList.isEmpty()) {
            return 0.0;
        }

        final Map<String, Long> itemCountMap = shoppingList.stream().collect(groupingBy(identity(), counting()));

        return itemCountMap.entrySet()
                .stream()
                .mapToDouble(this::calculateCost)
                .sum();
    }

    private double calculateCost(final Map.Entry<String, Long> item) {
        final String itemName = item.getKey();
        final long itemCount = item.getValue();
        final BiFunction<Long, Double, Double> offer = offers.getOrDefault(itemName, noOfferCost);
        return offer.apply(itemCount, priceList.getOrDefault(itemName, 0.0));
    }
}
