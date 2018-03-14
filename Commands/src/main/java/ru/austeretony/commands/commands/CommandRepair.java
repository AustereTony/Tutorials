package ru.austeretony.commands.commands;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

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
	NAME = "repair",//Имя команды, используется при вызове
	ALIAS = "r",//Допустимая вариация команды, таких может быть несколько
	USAGE = "/repair";//Шаблон вызова, выводится при выбрасывании WrongUsageException
	
	@Override
	public String getName() {
		
		return this.NAME;
	}

	@Override
	public String getUsage(ICommandSender sender) {
		
		return this.USAGE;
	}
	
    @Override 
    public List<String> getAliases() { 
    	
        List<String> aliases = new ArrayList<String>();//Так как допустимых вариаций может быть много, передаются они массивом строк  
        
        aliases.add(this.ALIAS);
    	
        return aliases;
    } 
	
    //Перед выполнением команды происходит вызов этого метода для проверки наличия прав на использование
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
    	
        return sender instanceof EntityPlayer ? server.getPlayerList().canSendCommands(((EntityPlayer) sender).getGameProfile()) : false;//Если игрок опнут (opped), то он может использовать команду
    }

    //Выполнение команды происходит здесь, этот метод вызывается только на сервере (физичесуом или логическом)
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
					
		if (sender instanceof EntityPlayer) {
											
			if (args.length > 0) {
										
				throw new WrongUsageException(this.getUsage(sender));//Выбросить исключение если вместе с командой переданы какие либо аргументы.
			}
			
			EntityPlayer player = this.getCommandSenderAsPlayer(sender);//Получение экземрляра игрока, вызвавшего команду		
						
			ItemStack mainHandItem = player.getHeldItemMainhand();
			
			TextComponentTranslation chatMessage;//Ссылка на сообщение, которое будет выведено в чат игрока
												
			if (mainHandItem != ItemStack.EMPTY || mainHandItem.getItem() != Items.AIR) {
					
				chatMessage = new TextComponentTranslation("commands.repair.item");//Начало сообщения - "Предмет"
					
				chatMessage.getStyle().setColor(TextFormatting.RED);//Установка цвета сообщения
										
				TextComponentTranslation itemName = new TextComponentTranslation(" <" + mainHandItem.getDisplayName() + "> ");//Отдельный объект TextComponentTranslation для имени предмета
					
				itemName.getStyle().setColor(TextFormatting.WHITE);
					
				chatMessage.appendSibling(itemName);//Сцепление - "Предмет <Имя> "
					
				if (mainHandItem.isItemStackDamageable()) {
				
					if (mainHandItem.isItemDamaged()) {
							
						mainHandItem.setItemDamage(0);//Если предмет может быть повреждён и имеет повреждение - ремонт
						
						chatMessage.getStyle().setColor(TextFormatting.GREEN);
							
						chatMessage.appendSibling(new TextComponentTranslation("commands.repair.repaired"));//"Предмет <Имя> отремонтирован"
					}
						
					else {
							
						//Если предмет не повреждён
						
						chatMessage.appendSibling(new TextComponentTranslation("commands.repair.noDamage"));//"Предмет <Имя> отремонтирован"
					}
				}
					
				else {
					
					//Если предмет нельзя повредить
						
					chatMessage.appendSibling(new TextComponentTranslation("commands.repair.canNotRepair"));//"Предмет <Имя> не может быть отремонтирован"
				}
			}
				
			else {
				
				//Если предмета в руке нет
					
				chatMessage = new TextComponentTranslation("commands.repair.handEmpty");//"Нет активного предмета"
			}
				
			player.sendMessage(chatMessage);//Вывод сообщения для игрока, вызвавшего команду						
		}
	}
}
