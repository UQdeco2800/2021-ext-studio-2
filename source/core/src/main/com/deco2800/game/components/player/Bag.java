package com.deco2800.game.components.player;

import com.deco2800.game.components.score.ScoringSystemV1;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.services.ServiceLocator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Bag implements BagInterface {

    /*
      所有物品
      物品
     */
    private List<Entity> entities;
    private ScoringSystemV1 scoringSystem = new ScoringSystemV1();

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
        //when pick up one item, score will increase by 10 points.
        scoringSystem.addToScore(10);
        //using ScoreService
        ServiceLocator.getScoreService().addToScore(10);
    }
    public void remove(Entity e) {
        entities.remove(e);
    }

    @Override
    public void move(Entity e1, Entity e2) {
        entities.set(entities.indexOf(e1),e2);
        entities.set(entities.indexOf(e2),e1);
    }
}
