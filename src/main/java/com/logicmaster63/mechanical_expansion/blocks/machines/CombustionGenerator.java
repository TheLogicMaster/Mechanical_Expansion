package com.logicmaster63.mechanical_expansion.blocks.machines;

import com.logicmaster63.mechanical_expansion.MechanicalExpansion;
import com.logicmaster63.mechanical_expansion.tileEntity.CombustionGeneratorTile;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;

import static com.logicmaster63.mechanical_expansion.GUIHandler.GUI_COMBUSTION_GENERATOR;

public class CombustionGenerator extends MachineBase {

    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    private static final String NAME = "combustion_generator";

    public CombustionGenerator(Material materialIn) {
        super(materialIn, NAME, new ArrayList<>(Arrays.asList("TEST: VGAVHKSBFHKSBFJKSBFKJSBFKJSB")));
        setSoundType(SoundType.METAL);
        //this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = playerIn.getActiveItemStack();
        if(stack != null) {
            if (!playerIn.isSneaking()) {
                if (stack.getUnlocalizedName().substring(5).equals("wrench")) {
                    worldIn.setBlockState(pos, getDefaultState().withProperty(FACING, state.getValue(FACING).rotateAround(EnumFacing.Axis.Y)));
                } else {
                    playerIn.openGui(MechanicalExpansion.instance, GUI_COMBUSTION_GENERATOR, worldIn, pos.getX(), pos.getY(), pos.getZ());
                }
            } else {
                NBTTagCompound nbt = new NBTTagCompound();
                CombustionGeneratorTile te = (CombustionGeneratorTile)worldIn.getTileEntity(pos);
                te.writeToNBT(nbt);
                ItemStack itemStack = new ItemStack(state.getBlock().getItem(worldIn, pos, state).getItem(), 1, 0);
                itemStack.setTagCompound(nbt);
                if (!worldIn.isRemote) {
                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), itemStack));
                    te.clear();
                    worldIn.destroyBlock(pos, false);
                }
            }
        } else {
            playerIn.openGui(MechanicalExpansion.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        CombustionGeneratorTile te = (CombustionGeneratorTile)worldIn.getTileEntity(pos);
        NBTTagCompound nbt = stack.getTagCompound();
        if (nbt != null) {
            te.getStorage().readFromNBT(nbt);

            NBTTagCompound stackTag = nbt.getCompoundTag("Inventory");
            //te.setInventorySlotContents(0, ItemStack.loadItemStackFromNBT(stackTag));
            if (nbt.hasKey("CustomName", 8)) {
                te.customName = nbt.getString("CustomName");
            }
        }
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new CombustionGeneratorTile();
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState blockstate) {
        CombustionGeneratorTile te = (CombustionGeneratorTile) world.getTileEntity(pos);
        InventoryHelper.dropInventoryItems(world, pos, te);
        super.breakBlock(world, pos, blockstate);
    }
}
