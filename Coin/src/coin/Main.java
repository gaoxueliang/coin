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
public class Main {

    private static int[] coinBaseValues = {
        30,
        50,
        100,
        180,
        330,
        600,};
    private static int[] armValues = {
        100,
        800,
        2900,
        7900,
        18500
    };

    private static Coin[] coins = new Coin[coinBaseValues.length * 3];

    static {
        int starStart = 7 - coinBaseValues.length;
        for (int i = 0; i < coinBaseValues.length; i++) {
            int star = starStart + i;
            int baseValue = coinBaseValues[i];
            coins[i * 3] = new Coin(star, baseValue * 3 / 5, 40);
            coins[i * 3 + 1] = new Coin(star, baseValue * 4 / 5, 20);
            coins[i * 3 + 2] = new Coin(star, baseValue, 0);
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println(Arrays.toString(coins));
        for (int armValue : armValues) {
            compute(armValue);
        }
    }

    private static void compute(int aimValue) {
        System.out.print("aimValue = ");
        System.out.println(aimValue);

        for (int i = 0; i < coins.length; i++) {
            Coin coin = coins[i];
            if (coin.getValue() > aimValue) {
                break;
            }

        }
    }

}
