/**
 * Created by David Tennander, 10 juni 2016, all rights reserved.
 * Every one is allowed to use this source material for private use.
 * 
 */
package com.tennander.NeuralNetworking;

import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author David Tennander, 10 juni 2016
 */
public class GuessDigitTest {

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void test() {
        MainClass.guessADigitt(815);
    }

}
