package com.deco2800.game.components.ItemBar;

import com.deco2800.game.components.Component;
import java.util.ArrayList;

/**
 * the finalized item bar system
 */
public class newItembar extends Component{

    /**
     * item bar capacity, each item has a upper limit of 10
     */
    private static final int capacity = 10;
    /**
     * waters stored in item bar
     */
    private static ArrayList<String> waters;
    /**
     * foods stored in item bar
     */
    private static ArrayList<String> foods;
    /**
     * kits stored in item bar
     */
    private static double kits;

    private static int pao=9;

    public newItembar() {
        waters = new ArrayList<>();
        foods = new ArrayList<>();
        kits = 3;
        for (int i = 0; i < 3; i++) {
            waters.add("water");
            foods.add("food");
        }
    }

    /**
     * water number plus one
     */
    public void addwater(){
        if(waters.size()<capacity){
            waters.add("water");
        }
    }

    public static int getpao(){
        return pao;
    }

    public static void usepao(){
        if (pao > 0) pao -= 1;
    }
    /**
     * food number plus one
     */
    public  void addfood(){
        if(foods.size()<capacity){
            foods.add("food");
        }
    }

    /**
     * kit number plus one
     */
    public static void addkit(){
        if (kits < capacity) kits += 0.5;
    }

    /**
     * if the water number is greater than one, water number minus one
     */
    public static void usewater(){
        if (waters.size()>0) waters.remove(waters.size()-1);

    }

    /**
     * if the food number is greater than one, food number minus one
     */
    public static void usefood(){
        if (foods.size()>0) foods.remove(foods.size()-1);
    }

    /**
     * if the kit number is greater than one, kit number minus one
     */
    public static void usekit(){
        if (kits > 0) {kits -= 1;}
    }

    /**
     * @return food number
     */
    public static int getfood(){
        return foods.size();
    }

    /**
     * @return water number
     */
    public static int getwater(){
        return waters.size();
    }

    /**
     * @return kit number
     */
    public int getkit(){
        return (int)kits;
    }

    /**
     * @return a string containing kit, water and food numbers
     */
    public String getcounts(){return getkit() +"          " + getwater() + "         " + getfood()+ "          " + getpao();}

}
