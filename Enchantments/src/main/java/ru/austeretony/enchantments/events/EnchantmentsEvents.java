package ru.austeretony.enchantments.events;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockDirt.DirtType;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.austeretony.enchantments.enchantments.helper.EnchantmentsHelper;
import ru.austeretony.enchantments.main.EnchantmentsMain;
import ru.austeretony.enchantments.main.EnchantmentsRegistry;

@Mod.EventBusSubscriber(modid = EnchantmentsMain.MODID)
public class EnchantmentsEvents {
	
	/*
	 * Эвенты для применения эффектов чар.
	 */

	//Отмена урона при падении и повреждение ботинок для чар Безопасное Падение (EnchantmentSafeFall).
	@SubscribeEvent
	public static void onPlayerFall(LivingFallEvent event) {
		
		if (event.getEntityLiving() instanceof EntityPlayer) {
			
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
						
			if (EnchantmentsHelper.hasSafeFallEnchantment(player)) {//Проверка на наличие чар "Безопасного Падения".
				
				player.inventory.armorItemInSlot(0).damageItem((int) event.getDistance(), player);//Дамаг по ботинкам в кол-ве равном расстоянию падения.
				
				event.setCanceled(true);//Отмена эвента (урона).
			}
		}
	}
	
	//Замена дропа при добыче блоков инструментом с чарами Переплавка (EnchantmentSmelting).
	@SubscribeEvent
	public static void onPlayerBreakingBlock(BlockEvent.HarvestDropsEvent event) {
				
		if (event.getHarvester() != null) {
		
			if (EnchantmentsHelper.hasSmeltingEnchantment(event.getHarvester())) {//Проверка наличия экипировки с чарами.
					
				int 
				fortune = event.getFortuneLevel(),//Если на инструменте есть чары Удачи.
				level = EnchantmentHelper.getEnchantmentLevel(EnchantmentsRegistry.SMELTING, event.getHarvester().getHeldItemMainhand());//Уровень чар Переплавки.
				
				Block block = event.getState().getBlock();					
				
				if (event.getHarvester().canHarvestBlock(event.getState())) {
					
					if (block == Blocks.IRON_ORE) {//"Переплавка" руды.
					
						event.getDrops().clear();//Очистка стандартного дропа, но так делать не рекомендую, лучше перебирать дроп и заменять блоки на слитки.
					
						event.getDrops().add(new ItemStack(Items.IRON_INGOT, event.getWorld().rand.nextInt(level + 1) + (fortune > 0 ? event.getWorld().rand.nextInt(fortune + 1) : 0)));//Добавление слитков.
					}
				
					else if (block == Blocks.GOLD_ORE) {//"Переплавка" руды.
					
						event.getDrops().clear();
					
						event.getDrops().add(new ItemStack(Items.GOLD_INGOT, event.getWorld().rand.nextInt(level + 1) + (fortune > 0 ? event.getWorld().rand.nextInt(fortune + 1) : 0)));
					}
					
					else if (block == Blocks.SAND) {//"Переплавка" стекла.
	
						event.getDrops().clear();
						
						event.getDrops().add(new ItemStack(Item.getItemFromBlock(Blocks.GLASS)));
					}
					
					else if (block == Blocks.LOG || block == Blocks.LOG2) {//Сжигание брёвен до угля.
	
						event.getDrops().clear();
						
						event.getDrops().add(new ItemStack(Items.COAL, event.getWorld().rand.nextInt(level + 1) + (fortune > 0 ? event.getWorld().rand.nextInt(fortune + 1) : 0), 1));
					}
				}
			}
		}
	}
	
	//Замена земли на подзол при вспахивании мотыгой с чарами Переплавка (EnchantmentSmelting).
	@SubscribeEvent
	public static void onUseHoe(UseHoeEvent event) {
		
		if (event.getWorld().getBlockState(event.getPos()).getBlock() == Blocks.GRASS) {
		
			if (EnchantmentsHelper.hasSmeltingEnchantment(event.getEntityPlayer())) {
			
				event.getWorld().setBlockState(event.getPos(), Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, DirtType.PODZOL));
			}
		}
	}
}
