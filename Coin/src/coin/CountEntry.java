/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coin;

import java.util.ArrayList;

/**
 *
 * @author git
 */
public class CountEntry implements Cloneable, Comparable<CountEntry> {

    private int aimValue;
    private int value;
    private int criticalRate;
    private ArrayList<Coin> coins = new ArrayList<>();

    @Override
    public int compareTo(CountEntry o) {
        int key = Integer.compare(value, value);
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        return "CountEntry{" + "aimValue=" + aimValue + ", value=" + value + ", criticalRate=" + criticalRate + ", coins=" + coins + '}';
    }

    @Override
    public CountEntry clone() {
        try {
            CountEntry countEntry = (CountEntry) super.clone();
            countEntry.coins = (ArrayList<Coin>) coins.clone();
            return countEntry;
        } catch (CloneNotSupportedException e) {
            // this shouldn't happen, since we are Cloneable
            throw new InternalError(e);
        }
    }

}
