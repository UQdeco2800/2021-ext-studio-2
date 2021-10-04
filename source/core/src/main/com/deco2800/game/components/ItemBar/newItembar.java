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
    private static int capacity = 10;
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
    private static ArrayList<String> kits;
//    private ArrayList<ArrayList<String>> items;

    public newItembar() {
       waters = new ArrayList<>();
       foods = new ArrayList<>();
       kits = new ArrayList<>();
//       items = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            waters.add("water");
            foods.add("food");
            kits.add("kit");
        }
//        items.add(kits);
//        items.add(foods);
//        items.add(waters);
    }

    /**
     * water number plus one
     */
    public void addwater(){
        if(waters.size()<capacity){
            waters.add("water");
        }
    }


    /**
     * food number plus one
     */
    public void addfood(){
        if(foods.size()<capacity){
            foods.add("food");
        }
    }

    /**
     * kit number plus one
     */
    public static void addkit(){
        if(kits.size()<capacity){
            kits.add("kit");
        }
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
        if (kits.size()>0)
        {
            kits.remove(kits.size()-1);
        }

    }

    /**
     * @return food number
     */
    public int getfood(){
        return foods.size();
    }

    /**
     * @return water number
     */
    public int getwater(){
        return waters.size();
    }

    /**
     * @return kit number
     */
    public int getkit(){
        return kits.size();
    }

    /**
     * @return a string containing kit, water and food numbers
     */
    public String getcounts(){return getkit() +"         " + getwater() + "         " + getfood();}

}
