package com.deco2800.game.components.player;

import com.deco2800.game.entities.*;

/**
 * 背包接口
 */
public interface BagInterface {

    static final int CAPACITY = 10;

    /**
     * 背包迭代器
     *
     * @return 背包迭代器，用于访问背包中每个项
     */
    public ComponentIterator<Entity> iterator();

    /**
     * 添加物品
     * @param  e 物品
     */
    public void add(Entity e);

    /**
     * 移除装备
     * @param e 物品
     */
    public void remove(Entity e);

    /**
     * 物品交换位置
     * @param e1 物品1
     * @param e2 物品2
     */
    public void move(Entity e1, Entity e2);
}
