package com.logicmaster63.mechanical_expansion.blocks;

import com.logicmaster63.mechanical_expansion.init.Blocks;
import com.logicmaster63.mechanical_expansion.machines.Machine;
import com.logicmaster63.mechanical_expansion.tileEntity.CombustionGeneratorTile;
import com.logicmaster63.mechanical_expansion.tileEntity.MachineBaseTile;
import com.logicmaster63.mechanical_expansion.tileEntity.MachineTile;
import jline.internal.Log;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MachineCasing extends BlockBase implements ITileEntityProvider {

    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    public MachineCasing() {
        super(Material.IRON, "machine_casing");
        setHardness(10.0f);
        setResistance(15.0f);
        setHarvestLevel("pickaxe", 2);
        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean hasTileEntity(IBlockState p_hasTileEntity_1_) {
        return true;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos blockPos, IBlockState state, EntityLivingBase placer, ItemStack itemStack) {
        ItemStack stack = new ItemStack(Blocks.machine_casing);
        stack.setStackDisplayName("Electric Furnace");
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString("Machine", "com.logicmaster63.mechanical_expansion.machines.ElectricFurnace");
        stack.setTagCompound(nbt);
        placer.setHeldItem(EnumHand.OFF_HAND, stack);
        //if(itemStack.hasTagCompound())
            //itemStack.getTagCompound().setString("Machine", "com.logicmaster63.mechanical_expansion.machines.Machine");
        if(world.getTileEntity(blockPos) != null && itemStack.hasTagCompound()) {
            NBTTagCompound tag = new NBTTagCompound();
            world.getTileEntity(blockPos).writeToNBT(tag);
            tag.merge(itemStack.getTagCompound());
            world.getTileEntity(blockPos).readFromNBT(tag);
            //Log.error(tag);
        }
        Log.warn(itemStack);
        Log.warn(world.getTileEntity(blockPos).serializeNBT());
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new MachineTile();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float p_onBlockActivated_7_, float p_onBlockActivated_8_, float p_onBlockActivated_9_) {
        TileEntity te = world.getTileEntity(pos);
        return te != null && ((MachineTile)te).onBlockActivated(world, pos, state, player, hand, facing, p_onBlockActivated_7_, p_onBlockActivated_8_, p_onBlockActivated_9_);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{FACING});
    }

    public IBlockState getStateFromMeta(int meta) {
        EnumFacing enumfacing = EnumFacing.getFront(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
            enumfacing = EnumFacing.NORTH;
        }

        return getDefaultState().withProperty(FACING, enumfacing);
    }

    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex();
    }


    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState blockstate) {
        MachineTile te = (MachineTile) world.getTileEntity(pos);
        if(te != null)
            InventoryHelper.dropInventoryItems(world, pos, te);
        super.breakBlock(world, pos, blockstate);
    }
}