package com.deco2800.game.components.player;

import com.deco2800.game.components.player.BagInterface;
import com.deco2800.game.entities.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Bag implements BagInterface {

    /**
     * 所有物品
     */
    private List<Entity> entities;

    protected class BagIterator implements ComponentIterator<Entity> {

        private Iterator<Entity> _it;

        public synchronized Entity next() {
            return _it.next();
        }

        public boolean hasNext() {
            return _it.hasNext();
        }

        public BagIterator() {
            super();
            if (null == _it) {
                _it = entities.iterator();
            }
        }
    }

    public Bag() {
        entities = new ArrayList<Entity>(CAPACITY);
    }


    public ComponentIterator<Entity> iterator() {
        return new BagIterator();
    }


    public void add(Entity e) {
        if (CAPACITY <= entities.size()) {
            return;
        }
        entities.add(e);
    }

    public void remove(Entity e) {
        entities.remove(e);
    }

    public void move(Entity e1, Entity e2) {
        entities.set(entities.indexOf(e1),e2);
        entities.set(entities.indexOf(e2),e1);
    }
}