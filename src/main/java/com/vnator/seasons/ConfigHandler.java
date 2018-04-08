package com.vnator.seasons;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = Seasons.MODID, name = Seasons.MODID+"/"+Seasons.MODID, category = "")
@Mod.EventBusSubscriber
public class ConfigHandler {

	public static General general_category = new General();
	public static Visual visual_category = new Visual();
	public static SeasonFertility seasonal_fertility = new SeasonFertility();

	@SubscribeEvent
	public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event){
		if(event.getModID().equals(Seasons.MODID)){
			ConfigManager.sync(event.getModID(), Config.Type.INSTANCE); //resync config
		}
	}

	public static class General{
		@Config.Comment({"The number of in-game days per season"})
		public int days_per_season = 1;
	}

	public static class Visual{
		@Config.Comment({"Whether to display the seasonal background in the top-right corner"})
		public boolean display_season_background = true;
		@Config.Comment({"Whether to display the season name in the top-right corner"})
		public boolean display_season_name = true;
	}

	public static class SeasonFertility{
		@Config.Comment({"Crops growable in the spring"})
		public String [] spring_crops = new String[]{"minecraft:potatoes"};
		@Config.Comment({"Crops growable in the summer"})
		public String [] summer_crops = new String[]{"minecraft:potatoes", "minecraft:carrot"};
		@Config.Comment({"Crops growable in the fall"})
		public String [] fall_crops = new String[]{"minecraft:potatoes", "minecraft:seeds"};
		@Config.Comment({"Crops growable in the winter"})
		public String [] winter_crops = new String[]{"minecraft:potato"};
	}
}
