package com.logicmaster63.mechanical_expansion;

import com.logicmaster63.mechanical_expansion.init.Blocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;


public class MechanicalExpansionTab extends CreativeTabs{

    public MechanicalExpansionTab(String label) {
        super(label);
        this.setBackgroundImageName("MechanicalExpansionTab.png");
    }

    @Override
    public Item getTabIconItem() {
        return Item.getItemFromBlock(Blocks.combustion_generator);
    }
}
