package com.logicmaster63.mechanical_expansion.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.EnumWorldBlockLayer;

public class CopperOre extends Block{

    public CopperOre(Material materialIn) {
        super(materialIn);
        this.setHardness(10.0f);
        this.setResistance(15.0f);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean isFullCube() {
        return false;
    }

    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }

}
