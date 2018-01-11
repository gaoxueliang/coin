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
public class Coin {

    private int star;
    private int value;
    private int criticalRate;

    public Coin(int star, int value, int successRate) {
        this.star = star;
        this.value = value;
        this.criticalRate = successRate;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getCriticalRate() {
        return criticalRate;
    }

    public void setCriticalRate(int criticalRate) {
        this.criticalRate = criticalRate;
    }

    @Override
    public String toString() {
        return "\nCoin{" + "star=" + star + ", value=" + value + ", successRate=" + criticalRate + '}';
    }

}
