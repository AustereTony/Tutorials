package ru.austeretony.attributes.main;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import net.minecraft.potion.Potion;
import ru.austeretony.attributes.player.potions.PotionLooting;
import ru.austeretony.attributes.player.potions.PotionWetting;

public class PotionsRegistry {
	
	//Увеличение размера массива в классе Potion.
	static {
		
		extendPotionsArray();
	}
	
	//Инициализация и регистрация новых эффектов.
	/** Эффекты */
	public static final Potion 
	WETTING = new PotionWetting("wetting", 100, false, 0x00C2FF),
	LOOTING = new PotionLooting("looting", 101, false, 0x00C2FF);
	
	//Для инициализации класса.
	public static void register() {}
	
	private static void extendPotionsArray() {
		
	    Potion[] potionTypes = null;

	    for (Field field : Potion.class.getDeclaredFields()) {
	    	
	        field.setAccessible(true);
	         
	        try {
	        	 
	            if (field.getName().equals("potionTypes") || field.getName().equals("field_76425_a")) {
	            	 
	                 Field modifierField = Field.class.getDeclaredField("modifiers");
	                 
	                 final Potion[] newPotionTypes = new Potion[256];
	                 
	                 modifierField.setAccessible(true);
	                 modifierField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
	             
	                 potionTypes = (Potion[]) field.get(null);
	                 
	                 System.arraycopy(potionTypes, 0, newPotionTypes, 0, potionTypes.length);
	                 
	                 field.set(null, newPotionTypes);
	            }
	        }
	         
	        catch (Exception e) {
	        	 
	        	e.printStackTrace();
	    	} 
	    } 
	}
}

