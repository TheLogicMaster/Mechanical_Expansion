package com.logicmaster63.mechanical_expansion.init;

import com.logicmaster63.mechanical_expansion.ItemModelProvider;
import com.logicmaster63.mechanical_expansion.ItemOreDict;
import com.logicmaster63.mechanical_expansion.MechanicalExpansion;
import com.logicmaster63.mechanical_expansion.blocks.machines.CombustionGenerator;

import com.logicmaster63.mechanical_expansion.blocks.machines.ElectricFurnace;
import com.logicmaster63.mechanical_expansion.blocks.BlockOre;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

@SuppressWarnings("unchecked")
public class Blocks {

    public static Block combustion_generator;
    public static Block copper_ore;
    public static Block electric_furnace;

    public static void init() {
        electric_furnace = register(new ElectricFurnace(Material.IRON));
        combustion_generator = register(new CombustionGenerator(Material.IRON));
        copper_ore = register(new BlockOre(Material.IRON, "copper_ore"));
        //storage_controller = register(new StorageController(Material.IRON).setUnlocalizedName("storage_controller").setCreativeTab(MechanicalExpansion.MECHANICAL_TAB));
    }

    private static <T extends Block> T register(T block, ItemBlock itemBlock) {
        GameRegistry.register(block);
        if(itemBlock != null)
            GameRegistry.register(itemBlock);

        if (block instanceof ItemModelProvider) {
            ((ItemModelProvider)block).registerItemModel(itemBlock);
        }
        if (block instanceof ItemOreDict) {
            ((ItemOreDict)block).initOreDict();
        }
        if (itemBlock instanceof ItemOreDict) {
            ((ItemOreDict)itemBlock).initOreDict();
        }

        return block;
    }

    private static <T extends Block> T register(T block) {
        ItemBlock itemBlock = new ItemBlock(block);
        if(block.getRegistryName() != null)
            itemBlock.setRegistryName(block.getRegistryName());
        return register(block, itemBlock);
    }
}
