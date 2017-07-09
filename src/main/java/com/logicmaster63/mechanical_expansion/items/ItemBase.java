package com.logicmaster63.mechanical_expansion.items;

import com.logicmaster63.mechanical_expansion.MechanicalExpansion;
import net.minecraft.item.Item;

public class ItemBase extends Item {

    protected String name;

    public ItemBase(String name) {
        this.name = name;
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(MechanicalExpansion.MECHANICAL_TAB);
    }
}