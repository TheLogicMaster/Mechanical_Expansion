package com.logicmaster63.mechanical_expansion.init;

import com.logicmaster63.mechanical_expansion.ItemOreDict;
import com.logicmaster63.mechanical_expansion.MechanicalExpansion;
import com.logicmaster63.mechanical_expansion.Reference;
import com.logicmaster63.mechanical_expansion.items.*;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
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
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(new ResourceLocation(Reference.MODID, item.getUnlocalizedName().substring(5)), "inventory"));
        if (item instanceof ItemOreDict)
            ((ItemOreDict)item).initOreDict();
        return item;
    }
}
