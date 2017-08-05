package com.logicmaster63.mechanical_expansion.init;

import com.logicmaster63.mechanical_expansion.ItemOreDict;
import com.logicmaster63.mechanical_expansion.Reference;
import com.logicmaster63.mechanical_expansion.blocks.MachineCasing;
import com.logicmaster63.mechanical_expansion.blocks.machines.CombustionGenerator;

import com.logicmaster63.mechanical_expansion.blocks.machines.ElectricFurnace;
import com.logicmaster63.mechanical_expansion.blocks.BlockOre;
import com.logicmaster63.mechanical_expansion.items.MachineCasingItem;
import jline.internal.Log;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

@SuppressWarnings("unchecked")
public class Blocks {

    public static Block combustion_generator;
    public static Block copper_ore;
    public static Block electric_furnace;
    public static Block machine_casing;

    public static void init() {
        electric_furnace = register(new ElectricFurnace(Material.IRON));
        combustion_generator = register(new CombustionGenerator(Material.IRON));
        copper_ore = register(new BlockOre(Material.IRON, "ore_copper"));
        machine_casing = register(new MachineCasingItem());
    }

    private static <T extends Block> T register(T block, ItemBlock itemBlock) {
        GameRegistry.register(block);
        Log.warn("Registered block: " + block.getUnlocalizedName().substring(5));
        if(itemBlock != null) {
            GameRegistry.register(itemBlock);
            ModelLoader.setCustomModelResourceLocation(itemBlock, 0, new ModelResourceLocation(new ResourceLocation(Reference.MODID, itemBlock.getUnlocalizedName().substring(5)), "inventory"));
            Log.warn("Registered itemblock: " + itemBlock.getUnlocalizedName().substring(5));
        }
        if (block instanceof ItemOreDict)
            ((ItemOreDict)block).initOreDict();
        if (itemBlock instanceof ItemOreDict)
            ((ItemOreDict)itemBlock).initOreDict();
        return block;
    }

    private static Block register(ItemBlock itemBlock) {
        return register(itemBlock.block, itemBlock);
    }

    private static <T extends Block> T register(T block) {
        ItemBlock itemBlock = new ItemBlock(block);
        if(block.getRegistryName() != null)
            itemBlock.setRegistryName(block.getRegistryName());
        return register(block, itemBlock);
    }
}
