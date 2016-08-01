package com.logicmaster63.mechanical_expansion.machines;

import com.logicmaster63.mechanical_expansion.MechanicalExpansion;
import com.logicmaster63.mechanical_expansion.init.Items;
import com.logicmaster63.mechanical_expansion.tileEntity.CombustionGeneratorTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;
import org.lwjgl.Sys;

public class CombustionGenerator extends Block implements ITileEntityProvider {

    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    public CombustionGenerator(Material materialIn) {
        super(materialIn);
        this.setStepSound(soundTypeMetal);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        this.setHardness(10.0f);
        this.setResistance(15.0f);
        this.setHarvestLevel("pickaxe", 2);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
        ItemStack stack = playerIn.getCurrentEquippedItem();
        if(stack != null) {
            if (!playerIn.isSneaking()) {
                if (stack.getUnlocalizedName().substring(5).equals("wrench")) {
                    worldIn.setBlockState(pos, getDefaultState().withProperty(FACING, state.getValue(FACING).rotateAround(EnumFacing.Axis.Y)));
                } else {
                    playerIn.openGui(MechanicalExpansion.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
                }
            } else {
                NBTTagCompound nbt = new NBTTagCompound();
                CombustionGeneratorTileEntity te = (CombustionGeneratorTileEntity)worldIn.getTileEntity(pos);
                te.writeToNBT(nbt);
                ItemStack itemStack = new ItemStack(state.getBlock().getItem(worldIn, pos), 1, 0);
                itemStack.setTagCompound(nbt);
                if (!worldIn.isRemote) {
                    worldIn.spawnEntityInWorld(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), itemStack));
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
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean isFullCube() {
        return false;
    }

    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        CombustionGeneratorTileEntity te = (CombustionGeneratorTileEntity)worldIn.getTileEntity(pos);
        NBTTagCompound nbt = stack.getTagCompound();
        if (nbt != null) {
            te.getStorage().readFromNBT(nbt);

            NBTTagCompound stackTag = nbt.getCompoundTag("Inventory");
            te.setInventorySlotContents(0, ItemStack.loadItemStackFromNBT(stackTag));
            if (nbt.hasKey("CustomName", 8)) {
                te.customName = nbt.getString("CustomName");
            }
        }
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new CombustionGeneratorTileEntity();
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState blockstate) {
        CombustionGeneratorTileEntity te = (CombustionGeneratorTileEntity) world.getTileEntity(pos);
        InventoryHelper.dropInventoryItems(world, pos, te);
        super.breakBlock(world, pos, blockstate);
    }

    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, FACING);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing facing = EnumFacing.getHorizontal(meta);
        return this.getDefaultState().withProperty(FACING, facing);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getHorizontalIndex();
    }
}
