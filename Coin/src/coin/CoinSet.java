/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coin;

import java.util.Arrays;

/**
 *
 * @author git
 */
public class CoinSet implements Cloneable {

    private int aimValue;
    private int value;
    private int criticalRate;
    private int coinCount = 0;
    private Coin[] coins = new Coin[6];

    @Override
    public String toString() {
        return "CoinSet{" + "coinCount=" + coinCount + ", coins=" + Arrays.toString(coins) + '}';
    }

    @Override
    public CoinSet clone() {
        try {
            CoinSet coinSet = (CoinSet) super.clone();
            coinSet.coins = coins.clone();
            return coinSet;
        } catch (CloneNotSupportedException e) {
            // this shouldn't happen, since we are Cloneable
            throw new InternalError(e);
        }
    }
}
