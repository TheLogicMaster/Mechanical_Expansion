package com.logicmaster63.mechanical_expansion.blocks.machines;

import com.logicmaster63.mechanical_expansion.MechanicalExpansion;
import com.logicmaster63.mechanical_expansion.tileEntity.ElectricFurnaceTile;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;

public class ElectricFurnace extends MachineBase {

    private static final String NAME = "electric_furnace";

    public ElectricFurnace(Material material) {
        super(material, NAME, new ArrayList<>(Arrays.asList("Test2 fJBFSJNFfnjsenfjsknJNFJN")));
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote)
            playerIn.openGui(MechanicalExpansion.instance, 1, worldIn, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new ElectricFurnaceTile();
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState blockstate) {
        ElectricFurnaceTile te = (ElectricFurnaceTile) world.getTileEntity(pos);
        InventoryHelper.dropInventoryItems(world, pos, te);
        super.breakBlock(world, pos, blockstate);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        ElectricFurnaceTile te = (ElectricFurnaceTile)worldIn.getTileEntity(pos);
        NBTTagCompound nbt = stack.getTagCompound();
        if (nbt != null) {
            te.getStorage().readFromNBT(nbt);
            NBTTagList list = nbt.getTagList("Items", 10);
            for (int i = 0; i < list.tagCount(); ++i) {
                NBTTagCompound stackTag = list.getCompoundTagAt(i);
                int slot = stackTag.getByte("Slot") & 255;
                //te.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(stackTag));
            }

            if (nbt.hasKey("CustomName", 8)) {
                te.customName = nbt.getString("CustomName");
            }
        }
    }
}
