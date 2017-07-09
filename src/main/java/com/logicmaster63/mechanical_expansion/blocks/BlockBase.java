package com.logicmaster63.mechanical_expansion.blocks;

import com.logicmaster63.mechanical_expansion.MechanicalExpansion;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public abstract class BlockBase extends Block {

    public BlockBase(Material material, String name) {
        super(material);
        setCreativeTab(MechanicalExpansion.MECHANICAL_TAB);
        setUnlocalizedName(name);
        setRegistryName(name);
    }
}
