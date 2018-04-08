package com.vnator.seasons.gui;

import com.vnator.seasons.ConfigHandler;
import com.vnator.seasons.SeasonUtil;
import com.vnator.seasons.Seasons;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.toasts.SystemToast;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;

/**
 * Displays the current season to the player
 */
public class GuiSeasonDisplay extends Gui {

	private static final ResourceLocation SPRING_BACK = new ResourceLocation(Seasons.MODID, "textures/gui/spring.png");
	private static final ResourceLocation SUMMER_BACK = new ResourceLocation(Seasons.MODID, "textures/gui/summer.png");
	private static final ResourceLocation FALL_BACK = new ResourceLocation(Seasons.MODID, "textures/gui/autumn.png");
	private static final ResourceLocation WINTER_BACK = new ResourceLocation(Seasons.MODID, "textures/gui/winter.png");
	private static final int [] SEASON_COLORS = new int[]{0x32CD32, 0xFFDF00, 0xCD853F, 0x6495ED};
	private static final String [] SEASON_DESC = new String[]{"Smell the flowers!", "Time to get tropical!", "Prepare for the cold!", "Brr!"};

	private int currentSeason = 0;

	public GuiSeasonDisplay(){
		ScaledResolution scaled = new ScaledResolution(Minecraft.getMinecraft());
		int width = scaled.getScaledWidth();
		int height = scaled.getScaledHeight();
	}

	public void draw(){
		Minecraft mc = Minecraft.getMinecraft();
		String seasonName = SeasonUtil.getSeasonName(Minecraft.getMinecraft().world);
		int season = SeasonUtil.getSeason(Minecraft.getMinecraft().world);

		GlStateManager.color(1, 1, 1, 1);
		switch (season){
			case 0: mc.getTextureManager().bindTexture(SPRING_BACK); break;
			case 1: mc.getTextureManager().bindTexture(SUMMER_BACK); break;
			case 2: mc.getTextureManager().bindTexture(FALL_BACK); break;
			case 3: mc.getTextureManager().bindTexture(WINTER_BACK); break;
		}

		if(ConfigHandler.visual_category.display_season_background)
			drawTexturedModalRect(0, 0, 40, 30, 200, 200);

		GlStateManager.scale(2, 2, 1);
		if(ConfigHandler.visual_category.display_season_name)
			drawString(Minecraft.getMinecraft().fontRenderer, seasonName, 25, 10, SEASON_COLORS[season]);
		GlStateManager.scale(.5f, .5f, 1);
		if(ConfigHandler.visual_category.display_season_name){
			int seasonLen = ConfigHandler.general_category.days_per_season;
			int days = 1+(int)((Minecraft.getMinecraft().world.getWorldTime() / 24000) % seasonLen);
			System.out.println(Minecraft.getMinecraft().world.getWorldTime());
			drawString(Minecraft.getMinecraft().fontRenderer, "Day "+days+"/"+seasonLen, 50, 40, SEASON_COLORS[season]);
		}
		if(season != currentSeason){
			currentSeason = season;
			Minecraft.getMinecraft().getToastGui().add(new SystemToast(SystemToast.Type.TUTORIAL_HINT, new TextComponentString(seasonName), new TextComponentString(SEASON_DESC[season])));
		}
	}
}
