package ru.austeretony.potions.main;

import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.austeretony.potions.potions.PotionCure;
import ru.austeretony.potions.potions.PotionEquilibrium;
import ru.austeretony.potions.potions.PotionIronSkin;
import ru.austeretony.potions.potions.PotionVanish;
import ru.austeretony.potions.potions.recipes.RecipeRetreatPotion;

@Mod.EventBusSubscriber(modid = PotionsMain.MODID)
public class PotionsRegistry {

	/** Эффекты */
	public static final Potion 
	VANISH = new PotionVanish("vanish", false, 0x00FFFF),//Эффект	применяется вне класса Potion (отмена рендера игрока в VanishEffectHandler).	
	CURE = new PotionCure("cure", false, 0xFFBAFB),//Моментальный эффект (удаление эффекта отравления).		
	EQUILIBRIUM = new PotionEquilibrium("equilibrium", false, 0x77FFA9),//Периодический эффект (обмен опыта на здоровье).	
	IRON_SKIN = new PotionIronSkin("iron_skin", false, 0x7363FF);//Эффект временно модифицирует аттрибут (добавление очков брони).
		
	/** "Типы", представляющие зелья как предметы */
	public static final PotionType 
	VANISH_TYPE_STANDARD = createPotionType(null, new PotionEffect(VANISH, 1200)),//Обычное зелье.
	VANISH_TYPE_LONG = createPotionType("long", new PotionEffect(VANISH, 2400)),//Продолжительное зелье.
	VANISH_TYPE_STRONG = createPotionType("strong", new PotionEffect(VANISH, 1200, 1, false, false)),//Сильное зелье.		
	
	CURE_TYPE = createPotionType(null, new PotionEffect(CURE)),	
	
	EQUILIBRIUM_TYPE_STANDARD = createPotionType(null, new PotionEffect(EQUILIBRIUM, 600)),
	EQUILIBRIUM_TYPE_LONG = createPotionType("long", new PotionEffect(EQUILIBRIUM, 900)),
	EQUILIBRIUM_TYPE_STRONG = createPotionType("strong", new PotionEffect(EQUILIBRIUM, 600, 1)),
	
	IRON_SKIN_TYPE = createPotionType(null, new PotionEffect(IRON_SKIN, 600)),
	
	RETREAT_TYPE = createCompositePotionType("retreat", null, new PotionEffect(VANISH, 400), new PotionEffect(IRON_SKIN, 400));//Несколько эффектов.
		
	public static void registerSimpleRecipes() {
				
		addStandardRecipes(VANISH_TYPE_STANDARD, VANISH_TYPE_LONG, VANISH_TYPE_STRONG, Items.DIAMOND);//Добавление линейки стандартных рецептов.			
		addRecipe(PotionTypes.AWKWARD, Items.MILK_BUCKET, CURE_TYPE);//Добавление одного рецепта.		
		addStandardRecipes(EQUILIBRIUM_TYPE_STANDARD, EQUILIBRIUM_TYPE_LONG, EQUILIBRIUM_TYPE_STRONG, Items.EXPERIENCE_BOTTLE);	
		addRecipe(PotionTypes.AWKWARD, Items.IRON_INGOT, IRON_SKIN_TYPE);		
	}
	
	public static void registerAdvancedRecipes() {
		
		//Продвинутый рецепт, определённый в собственном классе RecipeRetreatPotion. Используется для создания сложных рецептов, требующих проверки NBT.
		BrewingRecipeRegistry.addRecipe(new RecipeRetreatPotion(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM, 1), VANISH_TYPE_STANDARD), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM, 1), IRON_SKIN_TYPE), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM, 1), RETREAT_TYPE)));		
	}

	/**
	 * Возвращает новый экземпляр PotionType с именем эффекта.
	 * 
	 * @param namePrefix
	 * @param potionEffect
	 * 
	 * @return PotionType
	 */
	private static PotionType createPotionType(String namePrefix, PotionEffect potionEffect) {		
			
		ResourceLocation potionName = potionEffect.getPotion().getRegistryName();

		ResourceLocation potionTypeName;
		
		if (namePrefix != null)		
			potionTypeName = new ResourceLocation(potionName.getResourceDomain(), namePrefix + "_" + potionName.getResourcePath());					
		else		
			potionTypeName = potionName;

		return new PotionType(potionName.toString(), potionEffect).setRegistryName(potionTypeName);
	}
	
	/**
	 * Возвращает новый экземпляр PotionType с указанным именем. Используется для зелий с несколькими эффектами.
	 * 
	 * @param typeName
	 * @param namePrefix
	 * @param potionEffects
	 * 
	 * @return PotionType
	 */
	private static PotionType createCompositePotionType(String typeName, String namePrefix, PotionEffect... potionEffects) {		
		
		ResourceLocation potionTypeName;
		
		if (namePrefix != null)	
			potionTypeName = new ResourceLocation(PotionsMain.MODID, namePrefix + "_" + typeName);					
		else		
			potionTypeName = new ResourceLocation(PotionsMain.MODID, typeName);

		return new PotionType(typeName, potionEffects).setRegistryName(potionTypeName);
	}
	
	/**
	 * Создание стандартных рецептов для зелий.
	 * 
	 * @param standardPotionType
	 * @param longPotionType
	 * @param strongPotionType
	 * @param ingredient
	 */
	private static void addStandardRecipes(PotionType standardPotionType, PotionType longPotionType, PotionType strongPotionType, Item ingredient) {
		
		PotionHelper.addMix(PotionTypes.AWKWARD, ingredient, standardPotionType);//Аргументы: основа, ингредиент, результат.
		if (longPotionType != null)
		PotionHelper.addMix(standardPotionType, Items.REDSTONE, longPotionType);		
		if (strongPotionType != null)
		PotionHelper.addMix(standardPotionType, Items.GLOWSTONE_DUST, strongPotionType);
	}
	
	/**
	 * Добавлние своего рецепта.
	 * 
	 * @param basePotionType
	 * @param ingredient
	 * @param outputPotiontype
	 */
	private static void addRecipe(PotionType inputPotionType, Item ingredient, PotionType outputPotiontype) {
		
		PotionHelper.addMix(inputPotionType, ingredient, outputPotiontype);
	}
	
	@SubscribeEvent
    public static void registerPotions(RegistryEvent.Register<Potion> event) {
    	    	
		event.getRegistry().registerAll(

				VANISH,							
				CURE,				
				EQUILIBRIUM,			
				IRON_SKIN
		);
    }
	
	@SubscribeEvent
	public static void registerPotionTypes(RegistryEvent.Register<PotionType> event) {
		
		event.getRegistry().registerAll(
				
				VANISH_TYPE_STANDARD,
				VANISH_TYPE_LONG,
				VANISH_TYPE_STRONG,				
				CURE_TYPE,				
				EQUILIBRIUM_TYPE_STANDARD,
				EQUILIBRIUM_TYPE_LONG,
				EQUILIBRIUM_TYPE_STRONG,
				IRON_SKIN_TYPE,
				RETREAT_TYPE
		);
	}
}
