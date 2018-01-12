/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coin;

/**
 *
 * @author git
 */
public final class Coin {

    private final int star;
    private final int value;
    private final int criticalRate;

    public Coin(int star, int value, int successRate) {
        this.star = star;
        this.value = value;
        this.criticalRate = successRate;
    }

    public int getStar() {
        return star;
    }

    public int getValue() {
        return value;
    }

    public int getCriticalRate() {
        return criticalRate;
    }

    @Override
    public String toString() {
        return "\nCoin{" + "star=" + star + ", value=" + value + ", successRate=" + criticalRate + '}';
    }

}
