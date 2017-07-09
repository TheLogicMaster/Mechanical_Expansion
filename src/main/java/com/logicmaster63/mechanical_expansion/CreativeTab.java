package com.logicmaster63.mechanical_expansion;

import com.logicmaster63.mechanical_expansion.init.Blocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;


public class CreativeTab extends CreativeTabs{

    public CreativeTab(String label) {
        super(label);
        this.setBackgroundImageName("mechanical_expansion_tab.png");
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(Item.getItemFromBlock(Blocks.combustion_generator));
    }
}
