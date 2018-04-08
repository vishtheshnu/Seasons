package com.vnator.seasons;

import net.minecraft.block.*;
import net.minecraft.world.World;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.logging.log4j.Level;

@Mod.EventBusSubscriber
public class SeasonUtil {
	public static final int SPRING = 0;
	public static final int SUMMER = 1;
	public static final int FALL = 2;
	public static final int WINTER = 3;

	public static String getSeasonName(World world){
		int season = getSeason(world);
		switch (season){
			case 0: return "Spring";
			case 1: return "Summer";
			case 2: return "Fall";
			case 3: return "Winter";
			default: return "Something is wrong! Season index = "+season;
		}
	}

	public static int getSeason(World world){
		int slen = ConfigHandler.general_category.days_per_season;
		return (int) (world.getWorldTime()/24000 / slen)%4;
	}

	public static boolean isSeasonApplicable(Block block){
		if(block instanceof BlockGrass ||
				block instanceof BlockDoublePlant ||
				block instanceof BlockTallGrass ||
				block instanceof BlockSapling)
			return false;

		if(block instanceof IGrowable)
			return true;

		return false;
	}

	@SubscribeEvent
	public static void onCropGrowth(BlockEvent.CropGrowEvent evt){
		Block block = evt.getState().getBlock();
		if(isSeasonApplicable(block) && !canCropGrow(block, evt.getWorld())) {
			//evt.setCanceled(true);
			evt.getWorld().destroyBlock(evt.getPos(), true);
		}
	}

	@SubscribeEvent
	public static void onBonemeal(BonemealEvent evt){
		Block block = evt.getBlock().getBlock();
		if(isSeasonApplicable(block) && !canCropGrow(block, evt.getWorld())) {
			evt.setCanceled(true);
			evt.getWorld().destroyBlock(evt.getPos(), true);
		}
	}

	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent evt){
		//If the season has changed, update the player with a toast
		//evt.player.world.getTotalWorldTime()
	}

	private static boolean canCropGrow(Block crop, World world){
		String name = crop.getRegistryName().toString();
		return FertilityUtil.getInstance().isPlantFirtile(name, getSeason(world));
	}
}
