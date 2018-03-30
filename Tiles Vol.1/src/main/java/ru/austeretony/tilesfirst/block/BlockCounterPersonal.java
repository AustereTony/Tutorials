package ru.austeretony.tilesfirst.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import ru.austeretony.tilesfirst.block.tile.BlockTileEntity;
import ru.austeretony.tilesfirst.tiles.TileEntityCounterPersonal;

public class BlockCounterPersonal extends BlockTileEntity<TileEntityCounterPersonal> {

	public BlockCounterPersonal(String name, Material material, float hardness, float resistanse, SoundType soundType) {
		
		super(name, material, hardness, resistanse, soundType);
		
		this.setHarvestLevel("pickaxe", 3);				
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos position, IBlockState blockState, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		
		TileEntityCounterPersonal tileEntity = this.getTileEntity(world, position);
		
		if (!world.isRemote) {//На сервере работа с тайлом.
						
			if (player.getPersistentID().equals(tileEntity.getOwnerUUID())) {
				
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
				
				player.sendMessage(new TextComponentTranslation("tile.counter.current", tileEntity.getCount()));//Значение отправляется с сервера.
			}
		}
		
		else {//На клиенте вывод информации.
						
			String ownerName = tileEntity.getOwnerName();

			if (player.getPersistentID().equals(tileEntity.getOwnerUUID())) {
				
				player.sendMessage(new TextComponentTranslation("tile.counter.owner", ownerName));//Ник владельца синхронизируется тайлом и выводится на клиенте.
			}
			
			else {
				
				player.sendMessage(new TextComponentTranslation("tile.counter.imposter", ownerName));
			}			
		}
		
		return true;
	}

	@Override
	public Class<TileEntityCounterPersonal> getTileEntityClass() {
		
		return TileEntityCounterPersonal.class;
	}

	@Override
	public TileEntityCounterPersonal createTileEntity(World world, IBlockState blockState) {
		
		return new TileEntityCounterPersonal();
	}
	
	@Override
    public void onBlockPlacedBy(World world, BlockPos position, IBlockState blockState, EntityLivingBase placer, ItemStack itemStack) {
    	
		if (!world.isRemote) {//На сервере установка владельца тайла.
		
	    	if (this.getTileEntity(world, position) != null) {
	    		
	    		if (placer instanceof EntityPlayer) {
	    			
	    			this.getTileEntity(world, position).setOwner((EntityPlayer) placer);
	    		}
	    	}
		}
    }
}
