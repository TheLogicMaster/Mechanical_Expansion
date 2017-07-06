package com.logicmaster63.mechanical_expansion.blocks;

import com.logicmaster63.mechanical_expansion.ItemModelProvider;
import com.logicmaster63.mechanical_expansion.MechanicalExpansion;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public class BlockOre extends BlockBase implements ItemModelProvider {

    public BlockOre(Material materialIn, String name) {
        super(materialIn, name);
        this.setHardness(10.0f);
        this.setResistance(15.0f);
        this.setHarvestLevel("pickaxe", 2);
    }

    @Override
    public void registerItemModel(Item item) {
        MechanicalExpansion.proxy.registerItemRenderer(item, 0, "copperore");
    }
}
