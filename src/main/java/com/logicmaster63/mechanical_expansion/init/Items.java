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
    public static Item wrench;

    public static void init() {
        pulverized_coal = register(new PulverizedCoal());
        OreDictionary.registerOre("dustCoal", pulverized_coal);
        coal_coke = register(new CoalCoke());
        ingot_copper = register(new CopperIngot());
        OreDictionary.registerOre("ingotCopper", ingot_copper);
        ingot_lead = register(new LeadIngot());
        OreDictionary.registerOre("ingotLead", ingot_lead);
        wrench = register(new Wrench());
    }

    private static <T extends Item> T register(T item) {
        GameRegistry.register(item);
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(new ResourceLocation(Reference.MODID, item.getUnlocalizedName().substring(5)), "inventory"));
        if (item instanceof ItemOreDict)
            ((ItemOreDict)item).initOreDict();
        return item;
    }
}
