package com.deco2800.game.components.player;

/**
 * 物品迭代器接口
 */
public interface ComponentIterator<T> {

    public T next();

    public boolean hasNext();

}