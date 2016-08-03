package com.logicmaster63.mechanical_expansion.init;

import com.logicmaster63.mechanical_expansion.MechanicalExpansion;
import com.logicmaster63.mechanical_expansion.Reference;
import com.logicmaster63.mechanical_expansion.machines.CombustionGenerator;

import com.logicmaster63.mechanical_expansion.machines.ElectricFurnace;
import com.logicmaster63.mechanical_expansion.items.CombustionGeneratorItem;
import com.logicmaster63.mechanical_expansion.blocks.CopperOre;
import com.logicmaster63.mechanical_expansion.items.ElectricFurnaceItem;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Blocks {

    public static Block combustion_generator;
    public static Block copper_ore;
    public static Block electric_furnace;

    public static void init() {
        electric_furnace = new ElectricFurnace(Material.iron).setUnlocalizedName("electric_furnace").setCreativeTab(MechanicalExpansion.MECHANICAL_TAB);
        combustion_generator = new CombustionGenerator(Material.iron).setUnlocalizedName("combustion_generator").setCreativeTab(MechanicalExpansion.MECHANICAL_TAB);
        copper_ore = new CopperOre(Material.iron).setUnlocalizedName("copper_ore").setCreativeTab(MechanicalExpansion.MECHANICAL_TAB);
    }

    public static void register() {
        GameRegistry.registerBlock(combustion_generator, CombustionGeneratorItem.class,combustion_generator.getUnlocalizedName().substring(5));
        GameRegistry.registerBlock(electric_furnace, ElectricFurnaceItem.class,electric_furnace.getUnlocalizedName().substring(5));
        GameRegistry.registerBlock(copper_ore, copper_ore.getUnlocalizedName().substring(5));
    }

    public static void registerRenders() {
        registerRender(electric_furnace);
        registerRender(combustion_generator);
        registerRender(copper_ore);
    }

    public static void registerRender(Block block) {
        Item item = Item.getItemFromBlock(block);
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(Reference.MODID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
    }
}
