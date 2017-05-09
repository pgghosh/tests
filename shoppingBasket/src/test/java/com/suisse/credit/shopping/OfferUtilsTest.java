package com.suisse.credit.shopping;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class OfferUtilsTest {
    @Test
    public void buyOneGetOneFreeForEvenItemCount() throws Exception {
        assertThat(OfferUtils.buyOneGetOneFree(4,0.20), is(0.40));
    }

    @Test
    public void buyOneGetOneFreeForOddItemCount() throws Exception {
        assertThat(OfferUtils.buyOneGetOneFree(5,0.20), is(0.60));
    }

    @Test
    public void buyThreeForPriceOfOneForItemCountAsMultipleOfThree() throws Exception {
        assertThat(OfferUtils.buyThreeForPriceOfOne(6,0.20), is(0.40));
    }

    @Test
    public void buyThreeForPriceOfOneForItemCountNotMultipleOfThree() throws Exception {
        assertThat(OfferUtils.buyThreeForPriceOfOne(8,0.20), is(0.80));
    }
}