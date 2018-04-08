package com.vnator.seasons.gui;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber
public class RenderGuiHandler {

	private static GuiSeasonDisplay instance;

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void onRenderGui(RenderGameOverlayEvent.Post evt){
		if(evt.getType() != RenderGameOverlayEvent.ElementType.EXPERIENCE)
			return;
		if(instance == null)
			instance = new GuiSeasonDisplay();
		instance.draw();
	}
}
