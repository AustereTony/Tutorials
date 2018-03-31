package ru.austeretony.enchantments.events;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.event.world.BlockEvent;
import ru.austeretony.enchantments.enchantments.helper.EnchantmentsHelper;
import ru.austeretony.enchantments.main.EnchanmtmentsRegistry;

public class EnchantmentsEvents {
	
	/*
	 * Эвенты для применения эффектов чар.
	 */

	//Отмена урона при падении и повреждение ботинок для чар Безопасное Падение (EnchantmentSafeFall).
	@SubscribeEvent
	public void onPlayerFall(LivingFallEvent event) {
		
		if (event.entityLiving instanceof EntityPlayer) {
			
			EntityPlayer player = (EntityPlayer) event.entityLiving;
						
			if (EnchantmentsHelper.hasSafeFallEnchantment(player)) {//Проверка на наличие чар "Безопасного Падения".
				
				player.inventory.armorItemInSlot(0).damageItem((int) event.distance, player);//Дамаг по ботинкам в кол-ве равном расстоянию падения.
				
				event.setCanceled(true);//Отмена эвента (урона).
			}
		}
	}
	
	//Замена дропа при добыче блоков инструментом с чарами Переплавка (EnchantmentSmelting).
	@SubscribeEvent
	public void onPlayerBreakingBlock(BlockEvent.HarvestDropsEvent event) {
				
		if (event.harvester != null) {
		
			if (EnchantmentsHelper.hasSmeltingEnchantment(event.harvester)) {//Проверка наличия экипировки с чарами.
					
				int 
				fortune = event.fortuneLevel,//Если на инструменте есть чары Удачи.
				level = EnchantmentHelper.getEnchantmentLevel(EnchanmtmentsRegistry.SMELTING.effectId, event.harvester.getHeldItem());//Уровень чар Переплавки.
				
				Block block = event.block;					
				
				if (event.harvester.canHarvestBlock(block)) {
					
					if (block == Blocks.iron_ore) {//"Переплавка" руды.
					
						event.drops.clear();//Очистка стандартного дропа, но так делать не рекомендую, лучше перебирать дроп и заменять блоки на слитки.
					
						event.drops.add(new ItemStack(Items.iron_ingot, 1 + event.world.rand.nextInt(level) + (fortune > 0 ? event.world.rand.nextInt(fortune + 1) : 0)));//Добавление слитков.
					}
				
					else if (block == Blocks.gold_ore) {//"Переплавка" руды.
					
						event.drops.clear();
					
						event.drops.add(new ItemStack(Items.gold_ingot, 1 + event.world.rand.nextInt(level) + (fortune > 0 ? event.world.rand.nextInt(fortune + 1) : 0)));
					}
					
					else if (block == Blocks.sand) {//"Переплавка" стекла.
	
						event.drops.clear();
						
						event.drops.add(new ItemStack(Item.getItemFromBlock(Blocks.glass)));
					}
					
					else if (block == Blocks.log || block == Blocks.log2) {//Сжигание брёвен до угля.
	
						event.drops.clear();
						
						event.drops.add(new ItemStack(Items.coal, 1 + event.world.rand.nextInt(level) + (fortune > 0 ? event.world.rand.nextInt(fortune + 1) : 0), 1));
					}
				}
			}
		}
	}
	
	//Замена земли на подзол при вспахивании мотыгой с чарами Переплавка (EnchantmentSmelting).
	@SubscribeEvent
	public void onUseHoe(UseHoeEvent event) {
		
		if (event.world.getBlock(event.x, event.y, event.z) == Blocks.grass) {
		
			if (EnchantmentsHelper.hasSmeltingEnchantment(event.entityPlayer)) {
			
				event.world.setBlock(event.x, event.y, event.z, Blocks.dirt, 2, 2);
				
				event.setCanceled(true);
			}
		}
	}
}
