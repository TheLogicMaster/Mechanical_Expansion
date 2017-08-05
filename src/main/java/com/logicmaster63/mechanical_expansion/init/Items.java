package com.logicmaster63.mechanical_expansion.init;

import com.logicmaster63.mechanical_expansion.ItemOreDict;
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

    public static Item ingot_copper;
    public static Item ingot_lead;

    public static Item wire_copper;
    public static Item sheet_copper;

    public static Item wrench;
    public static Item wire_cutters;

    public static void init() {

        pulverized_coal = register(new PulverizedCoal());
        coal_coke = register(new CoalCoke());

        ingot_copper = register(new CopperIngot());
        ingot_lead = register(new LeadIngot());

        wire_copper = register(new ItemBase("wire_copper"));
        sheet_copper = register(new ItemBase("sheet_copper"));

        wrench = register(new Wrench());
        wire_cutters = register(new WireCutters());

        OreDictionary.registerOre("ingotCopper", ingot_copper);
        OreDictionary.registerOre("ingotLead", ingot_lead);

        OreDictionary.registerOre("dustCoal", pulverized_coal);
    }

    private static <T extends Item> T register(T item) {
        GameRegistry.register(item);
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(new ResourceLocation(Reference.MODID, item.getUnlocalizedName().substring(5)), "inventory"));
        if (item instanceof ItemOreDict)
            ((ItemOreDict)item).initOreDict();
        return item;
    }
}
