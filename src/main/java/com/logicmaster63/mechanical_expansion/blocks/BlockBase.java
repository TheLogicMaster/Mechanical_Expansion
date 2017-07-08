package com.logicmaster63.mechanical_expansion.blocks;

import com.logicmaster63.mechanical_expansion.ItemModelProvider;
import com.logicmaster63.mechanical_expansion.MechanicalExpansion;
import com.logicmaster63.mechanical_expansion.tileEntity.MachineBaseTile;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BlockBase extends Block implements ItemModelProvider {

    private String name;

    public BlockBase(Material material, String name) {
        super(material);
        this.name = name;
        setCreativeTab(MechanicalExpansion.MECHANICAL_TAB);
        setUnlocalizedName(name);
        setRegistryName(name);
    }

    @Override
    public void registerItemModel(Item item) {
        MechanicalExpansion.proxy.registerItemRenderer(item, 0, item.getUnlocalizedName().substring(4));
    }
}
