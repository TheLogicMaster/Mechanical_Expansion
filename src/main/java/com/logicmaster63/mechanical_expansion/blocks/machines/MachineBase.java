package com.logicmaster63.mechanical_expansion.blocks.machines;

import com.logicmaster63.mechanical_expansion.blocks.BlockBase;
import com.logicmaster63.mechanical_expansion.tileEntity.MachineBaseTile;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public abstract class MachineBase extends BlockBase implements ITileEntityProvider {

    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    protected List<String> tooltip;

    public MachineBase(Material material, String name, List<String> tooltip) {
        super(material, name);
        this.tooltip = tooltip;
        setHardness(10.0f);
        setResistance(15.0f);
        setHarvestLevel("pickaxe", 2);
        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
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
        MachineBaseTile te = (MachineBaseTile) world.getTileEntity(pos);
        InventoryHelper.dropInventoryItems(world, pos, te);
        super.breakBlock(world, pos, blockstate);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        Item item = super.getItemDropped(state, rand, fortune);
        item.addInformation(new ItemStack(item), null, tooltip, false);
        return item;
    }
}
