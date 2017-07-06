package com.logicmaster63.mechanical_expansion.blocks.machines;

import com.logicmaster63.mechanical_expansion.blocks.BlockBase;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

import java.util.List;
import java.util.Random;

public abstract class MachineBase extends BlockBase implements ITileEntityProvider{

    protected List<String> tooltip;

    public MachineBase(Material material, String name, List<String> tooltip) {
        super(material, name);
        this.tooltip = tooltip;
        setHardness(10.0f);
        setResistance(15.0f);
        setHarvestLevel("pickaxe", 2);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        Item item = super.getItemDropped(state, rand, fortune);
        item.addInformation(null, null, tooltip, false);
        return item;
    }
}
