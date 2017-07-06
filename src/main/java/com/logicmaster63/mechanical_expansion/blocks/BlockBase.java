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

    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    public int facing;
    private String name;

    public BlockBase(Material material, String name) {
        super(material);
        this.name = name;
        setCreativeTab(MechanicalExpansion.MECHANICAL_TAB);
        setUnlocalizedName(name);
        setRegistryName(name);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }

    @Override
    public void registerItemModel(Item item) {
        MechanicalExpansion.proxy.registerItemRenderer(item, 0, item.getUnlocalizedName().substring(4));
    }
    //@Override
    //public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
    //    this.facing = facing.getHorizontalIndex();
    //    return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    //}

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState blockstate) {
        MachineBaseTile te = (MachineBaseTile) world.getTileEntity(pos);
        InventoryHelper.dropInventoryItems(world, pos, te);
        super.breakBlock(world, pos, blockstate);
    }

    /*@Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing facing = EnumFacing.getHorizontal(meta);
        return this.getDefaultState().withProperty(FACING, facing);
    }*/

    /*@Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getHorizontalIndex();
    }*/
}
