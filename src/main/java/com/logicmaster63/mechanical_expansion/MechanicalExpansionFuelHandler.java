package com.logicmaster63.mechanical_expansion;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;

public class MechanicalExpansionFuelHandler implements IFuelHandler{
    @Override
    public int getBurnTime(ItemStack fuel) {
        String name = fuel.getUnlocalizedName();
        if (name == "coal_coke"){
            return 2500;
        }else {
            return 0;
        }
    }
}