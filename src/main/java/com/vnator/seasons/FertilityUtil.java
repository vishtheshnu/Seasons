package com.vnator.seasons;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.Level;

import java.util.*;

/**
 * Singleton that handles the fertility of seeds and crops in different seasons
 */
@Mod.EventBusSubscriber
public class FertilityUtil {

	private HashSet<String> springPlants = new HashSet<String>();
	private HashSet<String> summerPlants = new HashSet<String>();
	private HashSet<String> fallPlants = new HashSet<String>();
	private HashSet<String> winterPlants = new HashSet<String>();
	HashMap<String, List<String>> ttAdd = new HashMap<String, List<String>>();

	private static FertilityUtil instance;
	public static FertilityUtil getInstance(){
		if(instance == null)
			instance = new FertilityUtil();
		return instance;
	}

	private FertilityUtil(){

	}

	public boolean isPlantFirtile(String plantName, int season){
		switch (season){
			case 0: return springPlants.contains(plantName);
			case 1: return summerPlants.contains(plantName);
			case 2: return fallPlants.contains(plantName);
			case 3: return winterPlants.contains(plantName);
		}
		Seasons.logger.log(Level.ERROR, "Season passed into isPlantFirtile isn't valid! Something wrong!");
		return false;
	}

	public void setSeeds(String [] spring, String [] summer, String [] fall, String [] winter){

		ArrayList<String> allcrops = new ArrayList<String>();
		allcrops.addAll(Arrays.asList(spring));
		allcrops.addAll(Arrays.asList(spring));
		allcrops.addAll(Arrays.asList(fall));
		allcrops.addAll(Arrays.asList(winter));
		//String [] allcrops = (String[])ArrayUtils.addAll(spring, summer, fall, winter);

		for(String s: allcrops) {
			if (!ttAdd.containsKey(s)) {
				LinkedList<String> list = new LinkedList<>();
				list.push("Seasons:");
				ttAdd.put(s, list);
			}
		}
		for(String s : spring) {
			if (ttAdd.containsKey(s))
				ttAdd.get(s).add(TextFormatting.GREEN + "Spring");
			Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(s));
			if(item instanceof IPlantable){
				springPlants.add(((IPlantable) item).getPlant(null, null).getBlock().getRegistryName().toString());
			}
		}
		for(String s : summer) {
			if (ttAdd.containsKey(s))
				ttAdd.get(s).add(TextFormatting.YELLOW + "Summer");
			Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(s));
			if(item instanceof IPlantable){
				summerPlants.add(((IPlantable) item).getPlant(null, null).getBlock().getRegistryName().toString());
			}
		}
		for(String s : fall) {
			if (ttAdd.containsKey(s))
				ttAdd.get(s).add(TextFormatting.GOLD + "Fall");
			Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(s));
			if(item instanceof IPlantable){
				fallPlants.add(((IPlantable) item).getPlant(null, null).getBlock().getRegistryName().toString());
			}
		}
		for(String s : winter) {
			if (ttAdd.containsKey(s))
				ttAdd.get(s).add(TextFormatting.BLUE + "Winter");
			Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(s));
			if(item instanceof IPlantable){
				winterPlants.add(((IPlantable) item).getPlant(null, null).getBlock().getRegistryName().toString());
			}
		}

		//Temp print plant names
		System.out.println("Plants: ");
		for(String s : springPlants)
			System.out.println(s);
		for(String s : summerPlants)
			System.out.println(s);
		for(String s : fallPlants)
			System.out.println(s);
		for(String s : winterPlants)
			System.out.println(s);
	}

	@SideOnly(Side.CLIENT)
	public void addSeedTooltip(){
		for(String s : ttAdd.keySet()){
			System.out.println("Item to add Tooltip to = "+s);
			Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(s));
			item.addInformation(null, null, ttAdd.get(s), null);
		}
	}

	@SubscribeEvent
	public static void onItemTooltip(ItemTooltipEvent event){
		String itemName = event.getItemStack().getItem().getRegistryName().toString();
		if(getInstance().ttAdd.containsKey(itemName)) {
			event.getToolTip().addAll(getInstance().ttAdd.get(itemName));
		}
	}
}
