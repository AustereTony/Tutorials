package ru.austeretony.tilesfirst.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;
import ru.austeretony.tilesfirst.block.tile.BlockTileEntity;
import ru.austeretony.tilesfirst.main.TilesMain;
import ru.austeretony.tilesfirst.tiles.TileEntityCounter;

public class BlockCounter extends BlockTileEntity<TileEntityCounter> {

	public BlockCounter(String name, Material material, float hardness, float resistanse, SoundType soundType) {
		
		super(name, material, hardness, resistanse, soundType);
		
		this.setHarvestLevel("pickaxe", 3);	
		this.setBlockTextureName(TilesMain.MODID + ":" + "counter/counter");
	}
	
	@Override
	public boolean onBlockActivated(World world, int xPos, int yPos, int zPos, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		
		if (!world.isRemote) {
			
			TileEntityCounter tileEntity = this.getTileEntity(world, xPos, yPos, zPos);
			
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
			
			player.addChatMessage(new ChatComponentTranslation("tile.counter.current", tileEntity.getCount()));
		}
		
		return true;
	}

	@Override
	public Class<TileEntityCounter> getTileEntityClass() {
		
		return TileEntityCounter.class;
	}

	@Override
	public TileEntityCounter createTileEntity(World world, int meta) {
		
		return new TileEntityCounter();
	}
}
