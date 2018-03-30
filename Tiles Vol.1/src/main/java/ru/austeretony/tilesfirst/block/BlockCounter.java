package ru.austeretony.tilesfirst.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import ru.austeretony.tilesfirst.block.tile.BlockTileEntity;
import ru.austeretony.tilesfirst.tiles.TileEntityCounter;

public class BlockCounter extends BlockTileEntity<TileEntityCounter> {

	public BlockCounter(String name, Material material, float hardness, float resistanse, SoundType soundType) {
		
		super(name, material, hardness, resistanse, soundType);
		
		this.setHarvestLevel("pickaxe", 3);				
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos position, IBlockState blockState, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		
		if (!world.isRemote) {
			
			TileEntityCounter tileEntity = this.getTileEntity(world, position);
			
			switch (side) {
			
				case DOWN: 
					tileEntity.decrementCount();
					break;
					
				case UP: 
					tileEntity.incrementCount();
					break;
					
				default:
					break;
			}
			
			player.sendMessage(new TextComponentTranslation("tile.counter.current", tileEntity.getCount()));
		}
		
		return true;
	}

	@Override
	public Class<TileEntityCounter> getTileEntityClass() {
		
		return TileEntityCounter.class;
	}

	@Override
	public TileEntityCounter createTileEntity(World world, IBlockState blockState) {
		
		return new TileEntityCounter();
	}
}
