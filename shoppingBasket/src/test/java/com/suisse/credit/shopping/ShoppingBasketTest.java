package com.suisse.credit.shopping;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ShoppingBasketTest {

    private ShoppingBasket shoppingBasket;


    @Before
    public void setUp() throws Exception {
        shoppingBasket = new ShoppingBasket();
    }

    @Test
    public void totalCostForNullShoppingBasketList_givesZero() throws Exception {

        assertThat(shoppingBasket.totalCost(null), is(0.0));

    }

    @Test
    public void totalCostForEmptyShoppingBasket_givesZero() throws Exception {
        assertThat(shoppingBasket.totalCost(Collections.emptyList()), is(0.0));
    }

    @Test
    public void totalCostForShoppingBasketOfRandomlyOrderedItemListWithEvenNumberOfMelonsAndLimesInMultiplesOfThree() throws Exception {

        List<String> shoppingList= Arrays.asList("Apple", "Apple", "Banana","Lime","Banana","Apple","Melon","Banana","Lime","Melon","Lime");
        Collections.shuffle(shoppingList);
        double totalCost = shoppingBasket.totalCost(shoppingList);
        assertThat(totalCost,is(2.30));
    }

    @Test
    public void totalCostForShoppingBasketOfRandomlyOrderedItemListWithOddNumberOfMelonsAndLimesNotInMultiplesOfThree() throws Exception {

        List<String> shoppingList= Arrays.asList("Apple", "Apple", "Banana","Lime","Banana","Apple","Melon","Banana","Lime","Melon","Lime","Melon","Lime","Banana","Lime");
        Collections.shuffle(shoppingList);
        double totalCost = shoppingBasket.totalCost(shoppingList);
        assertThat(totalCost,is(3.30));

    }

}