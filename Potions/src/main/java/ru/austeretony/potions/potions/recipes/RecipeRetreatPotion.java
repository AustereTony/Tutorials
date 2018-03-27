package ru.austeretony.potions.potions.recipes;

import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.common.brewing.IBrewingRecipe;

public class RecipeRetreatPotion implements IBrewingRecipe {

	private final ItemStack inputStack, ingredientStack, outputStack;
	
	public RecipeRetreatPotion(ItemStack inputStack, ItemStack ingredientStack, ItemStack outputStack) {
		
		this.inputStack = inputStack; 
		this.ingredientStack = ingredientStack; 
		this.outputStack = outputStack;
	}
	
	@Override
	public boolean isInput(ItemStack inputStack) {

		boolean isValid = false;
		
		if (inputStack.getItem() == this.inputStack.getItem() && inputStack.hasTagCompound()) {
			
			isValid = PotionUtils.getEffectsFromStack(inputStack).equals(PotionUtils.getEffectsFromStack(this.inputStack));
		}
		
		return isValid;
	}

	@Override
	public boolean isIngredient(ItemStack ingredientStack) {
		
		boolean isValid = false;
		
		if (ingredientStack.getItem() == this.ingredientStack.getItem() && ingredientStack.hasTagCompound()) {
			
			isValid = PotionUtils.getEffectsFromStack(ingredientStack).equals(PotionUtils.getEffectsFromStack(this.ingredientStack));
		}
		
		return isValid;
	}

	@Override
	public ItemStack getOutput(ItemStack inputStack, ItemStack ingredientStack) {
		
		ItemStack output = ItemStack.EMPTY;
		
		if (this.isInput(inputStack) && this.isIngredient(ingredientStack)) {
			
			output = this.outputStack.copy();
		}
		
		return output;
	}
}
