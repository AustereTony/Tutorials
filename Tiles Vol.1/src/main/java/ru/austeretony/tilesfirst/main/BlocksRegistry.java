package ru.austeretony.tilesfirst.main;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import ru.austeretony.tilesfirst.block.BlockCounter;
import ru.austeretony.tilesfirst.block.BlockCounterPersonal;

public class BlocksRegistry {

	public static final Block
	COUNTER = new BlockCounter("counter", Material.rock, 5.0F, 5.0F, Block.soundTypeStone).setCreativeTab(TilesMain.TILES),
	COUNTER_PERSONAL = new BlockCounterPersonal("counter_personal", Material.rock, 10.0F, 10.0F, Block.soundTypeStone).setCreativeTab(TilesMain.TILES);
	
	public static final Block[] BLOCKS = new Block[] {
			
			COUNTER,
			COUNTER_PERSONAL
	};
	
	public static void register() {
		
    	registerBlocks(BLOCKS);
	}
	
    private static void registerBlocks(Block... blocks) {
    	
		for (int i = 0; i < blocks.length; i++) {

			GameRegistry.registerBlock(blocks[i], blocks[i].getUnlocalizedName());
		}
    }
}
