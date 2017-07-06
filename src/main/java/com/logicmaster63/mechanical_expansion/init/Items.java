package com.logicmaster63.mechanical_expansion.init;

import com.logicmaster63.mechanical_expansion.ItemModelProvider;
import com.logicmaster63.mechanical_expansion.ItemOreDict;
import com.logicmaster63.mechanical_expansion.MechanicalExpansion;
import com.logicmaster63.mechanical_expansion.items.*;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class Items {

    public static Item pulverized_coal;
    public static Item coal_coke;
    public static Item copper_ingot;
    public static Item lead_ingot;
    public static Item wrench;

    public static void init() {
        pulverized_coal = register(new PulverizedCoal().setUnlocalizedName("pulverized_coal").setCreativeTab(MechanicalExpansion.MECHANICAL_TAB));
        OreDictionary.registerOre("dustCoal", pulverized_coal);
        coal_coke = register(new CoalCoke().setUnlocalizedName("coal_coke").setCreativeTab(MechanicalExpansion.MECHANICAL_TAB));
        copper_ingot = register(new CopperIngot().setUnlocalizedName("copper_ingot").setCreativeTab(MechanicalExpansion.MECHANICAL_TAB));
        OreDictionary.registerOre("ingotCopper", copper_ingot);
        lead_ingot = register(new LeadIngot().setUnlocalizedName("lead_ingot").setCreativeTab(MechanicalExpansion.MECHANICAL_TAB));
        OreDictionary.registerOre("ingotLead", lead_ingot);
        wrench = register(new Wrench().setUnlocalizedName("wrench").setCreativeTab(MechanicalExpansion.MECHANICAL_TAB));
    }

    private static <T extends Item> T register(T item) {
        GameRegistry.register(item);

        if (item instanceof ItemModelProvider) {
            ((ItemModelProvider)item).registerItemModel(item);
        }
        if (item instanceof ItemOreDict) {
            ((ItemOreDict)item).initOreDict();
        }

        return item;
    }
}
