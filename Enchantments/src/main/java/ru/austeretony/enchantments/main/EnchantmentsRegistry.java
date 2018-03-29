package ru.austeretony.enchantments.main;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantment.Rarity;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.austeretony.enchantments.enchantments.EnchantmentKeenBlade;
import ru.austeretony.enchantments.enchantments.EnchantmentSafeFall;
import ru.austeretony.enchantments.enchantments.EnchantmentSmelting;

@Mod.EventBusSubscriber(modid = EnchantmentsMain.MODID)
public class EnchantmentsRegistry {

	public static final Enchantment 
	SAFE_FALL = new EnchantmentSafeFall("safe_fall", Rarity.RARE, EnumEnchantmentType.ARMOR_FEET, new EntityEquipmentSlot[] {EntityEquipmentSlot.FEET}),//Безопасное Падение
	SMELTING = new EnchantmentSmelting("smelting", Rarity.VERY_RARE, EnumEnchantmentType.DIGGER, new EntityEquipmentSlot[] {EntityEquipmentSlot.MAINHAND}),//Переплавка	
	KEEN_BLADE = new EnchantmentKeenBlade("keen_blade", Rarity.COMMON, EnumEnchantmentType.WEAPON, new EntityEquipmentSlot[] {EntityEquipmentSlot.MAINHAND});//Острое Лезвие

	@SubscribeEvent
	public static void registerEnchantments(RegistryEvent.Register<Enchantment> event) {
		
		event.getRegistry().registerAll(
				
				SAFE_FALL,
				SMELTING,
				KEEN_BLADE
		);
	}
}

