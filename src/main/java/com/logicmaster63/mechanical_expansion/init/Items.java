package com.logicmaster63.mechanical_expansion.init;

import com.logicmaster63.mechanical_expansion.MechanicalExpansion;
import com.logicmaster63.mechanical_expansion.Reference;
import com.logicmaster63.mechanical_expansion.items.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
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
        pulverized_coal = new PulverizedCoal().setUnlocalizedName("pulverized_coal").setCreativeTab(MechanicalExpansion.MECHANICAL_TAB);
        coal_coke = new CoalCoke().setUnlocalizedName("coal_coke").setCreativeTab(MechanicalExpansion.MECHANICAL_TAB);
        copper_ingot = new CopperIngot().setUnlocalizedName("copper_ingot").setCreativeTab(MechanicalExpansion.MECHANICAL_TAB);
        lead_ingot = new LeadIngot().setUnlocalizedName("lead_ingot").setCreativeTab(MechanicalExpansion.MECHANICAL_TAB);
        wrench = new Wrench().setUnlocalizedName("wrench").setCreativeTab(MechanicalExpansion.MECHANICAL_TAB);
    }

    public static void register() {
        GameRegistry.registerItem(wrench, wrench.getUnlocalizedName().substring(5));
        GameRegistry.registerItem(coal_coke, coal_coke.getUnlocalizedName().substring(5));
        GameRegistry.registerItem(pulverized_coal, pulverized_coal.getUnlocalizedName().substring(5));
        OreDictionary.registerOre("dustCoal", pulverized_coal);
        GameRegistry.registerItem(copper_ingot, copper_ingot.getUnlocalizedName().substring(5));
        OreDictionary.registerOre("ingotCopper", copper_ingot);
        GameRegistry.registerItem(lead_ingot, lead_ingot.getUnlocalizedName().substring(5));
        OreDictionary.registerOre("ingotLead", lead_ingot);
    }

    public static void registerRenders() {
        registerRender(wrench);
        registerRender(coal_coke);
        registerRender(pulverized_coal);
        registerRender(copper_ingot);
        registerRender(lead_ingot);
    }

    public static void registerRender(Item item) {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(Reference.MODID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
    }
}
