package com.vnator.seasons;

import com.vnator.seasons.proxy.CommonProxy;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = Seasons.MODID, name = Seasons.NAME, version = Seasons.VERSION)
public class Seasons
{
    public static final String MODID = "seasons";
    public static final String NAME = "Seasons";
    public static final String VERSION = "1.0";

    public static Logger logger;

    @SidedProxy(clientSide = "com.vnator.seasons.proxy.ClientProxy", serverSide = "com.vnator.seasons.proxy.ServerProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static Seasons seasons;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        // some example code
        logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }

    @EventHandler
	public void postInit(FMLPostInitializationEvent event){
    	FertilityUtil.getInstance().setSeeds(
    			ConfigHandler.seasonal_fertility.spring_crops,
    			ConfigHandler.seasonal_fertility.summer_crops,
    			ConfigHandler.seasonal_fertility.fall_crops,
    			ConfigHandler.seasonal_fertility.winter_crops
		);
	}
}
