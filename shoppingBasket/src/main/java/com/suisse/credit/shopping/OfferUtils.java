package com.suisse.credit.shopping;

import java.math.BigDecimal;
import java.util.function.BiFunction;

public class OfferUtils {

    public static BiFunction<Long,Double,Double> noOfferCost=(itemCount,itemPrice) -> itemCount * itemPrice;

    public static Double buyOneGetOneFree(long itemCount,double itemPrice){
        long bogofCount=itemCount/2;
        long nonBogofCount=itemCount%2;
        return round((bogofCount+nonBogofCount) * itemPrice,2);
    }

    public static Double buyThreeForPriceOfOne(long itemCount,double itemPrice){
        long countOfItemsOnOffer=itemCount/3;
        long countOfItemNotOnOffer=itemCount%3;
        return round((countOfItemsOnOffer+countOfItemNotOnOffer) * itemPrice,2);
    }

    private static double round(double value,int precision){
        return new BigDecimal(value).setScale(precision,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

}
