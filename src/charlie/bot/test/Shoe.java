/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package charlie.bot.test;

//import charlie.plugin.IShoe;
import java.util.Random;

/**
 *
 * @author Rakesh
 */
public class Shoe extends charlie.card.Shoe {

    @Override
    public void init() {

        ran = new Random(1);
        load();
        shuffle();
    }
}