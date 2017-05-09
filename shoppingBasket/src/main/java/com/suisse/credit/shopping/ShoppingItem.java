package com.suisse.credit.shopping;

import java.util.Arrays;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public enum ShoppingItem {
    Apple(0.35),
    Banana(0.20),
    Melon(0.50,OfferUtils::buyOneGetOneFree),
    Lime(0.15,OfferUtils::buyThreeForPriceOfOne);

    private double unitPrice;
    private BiFunction<Long, Double, Double> offer;

    ShoppingItem(final double unitPrice,BiFunction<Long, Double, Double> offer){
        this.unitPrice = unitPrice;
        this.offer = offer;
    }
    ShoppingItem(double unitPrice){
        this(unitPrice,null);
    }

    public static final Map<String, Double> priceList =
        Arrays.stream(ShoppingItem.values()).collect(Collectors.toMap(si -> si.name(),si -> si.unitPrice));

    /**
     * Since Java 8 allows the usage of functions as first class objects, we can have an offerMap of item to
     * offer calculation function. This helps in providing some really elegant solutions to problems as the method
     * {@link ShoppingBasket#totalCost ShoppingBasket.totalCost} illustrates.
     */
    public static final Map<String,BiFunction<Long, Double, Double>> offers =
            Arrays.stream(ShoppingItem.values())
                    .filter(si -> si.offer != null)
                    .collect(Collectors.toMap(si -> si.name(),si -> si.offer));

}
