package com.deco2800.game.components.items;
import com.deco2800.game.entities.Entity;

public class InventorySystem
{
    public int counter;
    public InventorySystem(Entity target) {
        counter=0;
        this.target= target;
    }
    private final Entity target;
    TestBuffForItem sumer =  new TestBuffForItem();
    private int health;
    public void updateinventory()
    {
        if(counter<3)
        {
            counter++;
        }
    }
    public void pressbutton()
    {
        System.out.println("button test 1 succesful");
        System.out.println("items in inventory: " + counter);

        if(counter>0) {

                sumer.increaseHealth(target);
                System.out.println("button test 2 succesful");
                counter--;

        }

    }








}
