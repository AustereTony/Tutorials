package ru.austeretony.tilesfirst.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;
import ru.austeretony.tilesfirst.block.tile.BlockTileEntity;
import ru.austeretony.tilesfirst.main.TilesMain;
import ru.austeretony.tilesfirst.tiles.TileEntityCounterPersonal;

public class BlockCounterPersonal extends BlockTileEntity<TileEntityCounterPersonal> {

	public BlockCounterPersonal(String name, Material material, float hardness, float resistanse, SoundType soundType) {
		
		super(name, material, hardness, resistanse, soundType);
		
		this.setHarvestLevel("pickaxe", 3);		
		this.setBlockTextureName(TilesMain.MODID + ":" + "counter/counter");
	}
	
	@Override
	public boolean onBlockActivated(World world, int xPos, int yPos, int zPos, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		
		TileEntityCounterPersonal tileEntity = this.getTileEntity(world, xPos, yPos, zPos);
		
		if (!world.isRemote) {//На сервере работа с тайлом.
						
			if (player.getPersistentID().equals(tileEntity.getOwnerUUID())) {
				
				switch (side) {
				
					case 0://Top 
						tileEntity.decrementCount();
						break;
						
					case 1://Bottom 
						tileEntity.incrementCount();
						break;
						
					default:
						break;
				}
				
				player.addChatMessage(new ChatComponentTranslation("tile.counter.current", tileEntity.getCount()));//Значение отправляется с сервера.
			}
		}
		
		else {//На клиенте вывод информации.
						
			String ownerName = tileEntity.getOwnerName();

			if (player.getPersistentID().equals(tileEntity.getOwnerUUID())) {
				
				player.addChatMessage(new ChatComponentTranslation("tile.counter.owner", ownerName));//Ник владельца синхронизируется тайлом и выводится на клиенте.
			}
			
			else {
				
				player.addChatMessage(new ChatComponentTranslation("tile.counter.imposter", ownerName));
			}			
		}
		
		return true;
	}

	@Override
	public Class<TileEntityCounterPersonal> getTileEntityClass() {
		
		return TileEntityCounterPersonal.class;
	}

	@Override
	public TileEntityCounterPersonal createTileEntity(World world, int meta) {
		
		return new TileEntityCounterPersonal();
	}
	
	@Override
    public void onBlockPlacedBy(World world, int xPos, int yPos, int zPos, EntityLivingBase placer, ItemStack itemStack) {
    	
		if (!world.isRemote) {//На сервере установка владельца тайла.
		
	    	if (this.getTileEntity(world, xPos, yPos, zPos) != null) {
	    		
	    		if (placer instanceof EntityPlayer) {
	    			
	    			this.getTileEntity(world, xPos, yPos, zPos).setOwner((EntityPlayer) placer);
	    		}
	    	}
		}
    }
}
