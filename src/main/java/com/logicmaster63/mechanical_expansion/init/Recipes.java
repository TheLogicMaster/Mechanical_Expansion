package com.logicmaster63.mechanical_expansion.init;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class Recipes {

    public static void init() {

        GameRegistry.addShapelessRecipe(new ItemStack(Items.wire_copper, 2), Items.sheet_copper, new ItemStack(Items.wire_cutters,1, OreDictionary.WILDCARD_VALUE));
    }
}