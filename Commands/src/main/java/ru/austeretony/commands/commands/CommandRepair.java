package ru.austeretony.commands.commands;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;

public class CommandRepair extends CommandBase {
	
	/*
	 * При вызове команды предмет в руке полностью ремонтируется. Доступна опнутым игрокам.
	 * 
	 * Использование: 
	 * 
	 * /repair, 
	 * /r
	 */

	public static final String 
	NAME = "repair",//Имя команды, используется при вызове.
	ALIAS = "r",//Допустимая вариация команды, таких может быть несколько.
	USAGE = "/repair";//Шаблон вызова, выводится при выбрасывании WrongUsageException.
	
	@Override
	public String getCommandName() {
		
		return this.NAME;
	}

	@Override
	public String getCommandUsage(ICommandSender commandSender) {
		
		return this.USAGE;
	}
	
    @Override 
    public List<String> getCommandAliases() { 
    	
        List<String> aliases = new ArrayList<String>();//Так как допустимых вариаций может быть много, передаются они массивом строк.  
        
        aliases.add(this.ALIAS);
    	
        return aliases;
    } 
    
    @Override
    public boolean canCommandSenderUseCommand(ICommandSender commandSender) {
    	
    	//Только опам или если в мире активны читы.
		return commandSender instanceof EntityPlayer ? MinecraftServer.getServer().getConfigurationManager().func_152596_g(((EntityPlayer) commandSender).getGameProfile()) : false;    	
    }
    
	@Override
	public void processCommand(ICommandSender commandSender, String[] args) {
		
		if (commandSender instanceof EntityPlayer) {
			
			if (args.length > 0) {
										
				throw new WrongUsageException(this.getCommandUsage(commandSender));//Выбросить исключение если вместе с командой переданы какие либо аргументы.
			}
			
			EntityPlayer player = this.getCommandSenderAsPlayer(commandSender);//Получение экземрляра игрока, вызвавшего команду.		
						
			ItemStack mainHandItem = player.getHeldItem();
			
			ChatComponentTranslation chatMessage;//Ссылка на сообщение, которое будет выведено в чат игрока.
												
			if (mainHandItem != null) {
					
				chatMessage = new ChatComponentTranslation("commands.repair.item");//Начало сообщения - "Предмет".
					
				chatMessage.getChatStyle().setColor(EnumChatFormatting.RED);//Установка цвета сообщения.
										
				ChatComponentTranslation itemName = new ChatComponentTranslation(" <" + mainHandItem.getDisplayName() + "> ");//Отдельный объект TextComponentTranslation для имени предмета.
					
				itemName.getChatStyle().setColor(EnumChatFormatting.WHITE);
					
				chatMessage.appendSibling(itemName);//Сцепление - "Предмет <Имя> ".
					
				if (mainHandItem.isItemStackDamageable()) {
				
					if (mainHandItem.isItemDamaged()) {
							
						mainHandItem.setItemDamage(0);//Если предмет может быть повреждён и имеет повреждение - ремонт.
						
						chatMessage.getChatStyle().setColor(EnumChatFormatting.GREEN);
							
						chatMessage.appendSibling(new ChatComponentTranslation("commands.repair.repaired"));//"Предмет <Имя> отремонтирован".
					}
						
					else {
							
						//Если предмет не повреждён.
						
						chatMessage.appendSibling(new ChatComponentTranslation("commands.repair.noDamage"));//"Предмет <Имя> отремонтирован".
					}
				}
					
				else {
					
					//Если предмет нельзя повредить.
						
					chatMessage.appendSibling(new ChatComponentTranslation("commands.repair.canNotRepair"));//"Предмет <Имя> не может быть отремонтирован".
				}
			}
				
			else {
				
				//Если предмета в руке нет.
					
				chatMessage = new ChatComponentTranslation("commands.repair.handEmpty");//"Нет активного предмета".
			}
				
			player.addChatMessage(chatMessage);//Вывод сообщения для игрока, вызвавшего команду.					
		}
	}
}
