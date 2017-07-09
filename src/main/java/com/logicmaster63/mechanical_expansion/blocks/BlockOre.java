package com.logicmaster63.mechanical_expansion.blocks;

import net.minecraft.block.material.Material;

public class BlockOre extends BlockBase {

    public BlockOre(Material materialIn, String name) {
        super(materialIn, name);
        this.setHardness(10.0f);
        this.setResistance(15.0f);
        this.setHarvestLevel("pickaxe", 2);
    }
}
