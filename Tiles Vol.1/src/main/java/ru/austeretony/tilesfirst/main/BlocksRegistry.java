package ru.austeretony.tilesfirst.main;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.austeretony.tilesfirst.block.BlockCounter;
import ru.austeretony.tilesfirst.block.BlockCounterPersonal;

@Mod.EventBusSubscriber(modid = TilesMain.MODID)
public class BlocksRegistry {

	public static final Block
	COUNTER = new BlockCounter("counter", Material.ROCK, 5.0F, 5.0F, SoundType.STONE).setCreativeTab(TilesMain.TILES),
	COUNTER_PERSONAL = new BlockCounterPersonal("counter_personal", Material.ROCK, 10.0F, 10.0F, SoundType.STONE).setCreativeTab(TilesMain.TILES);
	
	public static final Block[] BLOCKS = new Block[] {
			
			COUNTER,
			COUNTER_PERSONAL
	};
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		
		event.getRegistry().registerAll(BLOCKS);
	}
	
	@SubscribeEvent
	public static void registerItemBlocks(RegistryEvent.Register<Item> event) {
		
		event.getRegistry().registerAll(getItemBlocks(BLOCKS));
	}
	
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void registerModels(ModelRegistryEvent event) {
    	
    	setRenderForAll(BLOCKS);
    }
    
	private static Item[] getItemBlocks(Block... blocks) {
		
		Item[] items = new Item[blocks.length];
		
		for (int i = 0; i < blocks.length; i++) {
			
			items[i] = new ItemBlock(blocks[i]).setRegistryName(blocks[i].getRegistryName());
		}
		
		return items;
	}
    
    @SideOnly(Side.CLIENT)
    private static void setRenderForAll(Block... blocks) {
    	
    	for (int i = 0; i < blocks.length; i++) {
    		
    		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(blocks[i]), 0, new ModelResourceLocation(blocks[i].getRegistryName(), "inventory"));
    	}
    }
}
