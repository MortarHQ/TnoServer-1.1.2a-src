package com.blackgear.platform.neoforge.client;

import com.blackgear.platform.client.event.FogRendering;
import com.blackgear.platform.client.event.HudRenderEvent;
import com.blackgear.platform.client.event.LocalPlayerEvents;
import com.blackgear.platform.client.event.screen.HudRendering;
import com.blackgear.platform.client.event.screen.TooltipEvents;
import com.blackgear.platform.client.event.screen.api.ScreenAccessImpl;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent.Clone;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent.LoggingIn;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent.LoggingOut;
import net.neoforged.neoforge.client.event.ContainerScreenEvent.Render.Background;
import net.neoforged.neoforge.client.event.ContainerScreenEvent.Render.Foreground;
import net.neoforged.neoforge.client.event.RenderGuiEvent.Post;
import net.neoforged.neoforge.client.event.RenderTooltipEvent.Pre;
import net.neoforged.neoforge.client.event.ScreenEvent.Closing;
import net.neoforged.neoforge.client.event.ScreenEvent.Opening;
import net.neoforged.neoforge.client.event.ViewportEvent.ComputeFogColor;
import net.neoforged.neoforge.client.event.ViewportEvent.RenderFog;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

@EventBusSubscriber(modid = "platform", bus = Bus.GAME, value = Dist.CLIENT)
public class ForgeClientEvents {
   @SubscribeEvent(priority = EventPriority.HIGH)
   public static void onItemTooltip(ItemTooltipEvent event) {
      TooltipEvents.ITEM_SETUP.invoker().registerTooltip(event.getItemStack(), event.getContext(), event.getFlags(), event.getToolTip());
   }

   @SubscribeEvent(priority = EventPriority.HIGH)
   public static void onItemTooltipRender(Pre event) {
      if (TooltipEvents.RENDER_TOOLTIP.invoker().onRendering(event.getGraphics(), event.getComponents(), event.getX(), event.getY()).isCancelled()) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent(priority = EventPriority.HIGH)
   public static void onRenderGui(Post event) {
      HudRendering.RENDERING.invoker().onRender(Minecraft.getInstance(), event.getGuiGraphics(), event.getPartialTick());
   }

   @SubscribeEvent(priority = EventPriority.HIGH)
   public static void onScreenInitialization(net.neoforged.neoforge.client.event.ScreenEvent.Init.Pre event) {
      if (HudRendering.PRE_INITIALIZE.invoker().onInitialize(Minecraft.getInstance(), event.getScreen(), new ScreenAccessImpl(event.getScreen())).isCancelled()
         )
       {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent(priority = EventPriority.HIGH)
   public static void onScreenInitialization(net.neoforged.neoforge.client.event.ScreenEvent.Init.Post event) {
      HudRendering.POST_INITIALIZE.invoker().onInitialize(Minecraft.getInstance(), event.getScreen(), new ScreenAccessImpl(event.getScreen()));
   }

   @SubscribeEvent(priority = EventPriority.HIGH)
   public static void onScreenRendering(net.neoforged.neoforge.client.event.ScreenEvent.Render.Pre event) {
      if (HudRendering.PRE_RENDERING
         .invoker()
         .onRender(Minecraft.getInstance(), event.getScreen(), event.getGuiGraphics(), event.getMouseX(), event.getMouseY(), Minecraft.getInstance().getTimer())
         .isCancelled()) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent(priority = EventPriority.HIGH)
   public static void onScreenRendering(net.neoforged.neoforge.client.event.ScreenEvent.Render.Post event) {
      HudRendering.POST_RENDERING
         .invoker()
         .onRender(Minecraft.getInstance(), event.getScreen(), event.getGuiGraphics(), event.getMouseX(), event.getMouseY(), Minecraft.getInstance().getTimer());
   }

   @SubscribeEvent(priority = EventPriority.HIGH)
   public static void onBackgroundRender(Background event) {
      HudRendering.RENDER_BACKGROUND
         .invoker()
         .onRender(
            Minecraft.getInstance(),
            event.getContainerScreen(),
            event.getGuiGraphics(),
            event.getMouseX(),
            event.getMouseY(),
            Minecraft.getInstance().getTimer()
         );
   }

   @SubscribeEvent(priority = EventPriority.HIGH)
   public static void onForegroundRender(Foreground event) {
      HudRendering.RENDER_FOREGROUND
         .invoker()
         .onRender(
            Minecraft.getInstance(),
            event.getContainerScreen(),
            event.getGuiGraphics(),
            event.getMouseX(),
            event.getMouseY(),
            Minecraft.getInstance().getTimer()
         );
   }

   @SubscribeEvent(priority = EventPriority.HIGH)
   public static void onContainerOpen(Opening event) {
      HudRendering.OPEN_CONTAINER.invoker().onOpen(Minecraft.getInstance(), event.getNewScreen());
   }

   @SubscribeEvent(priority = EventPriority.HIGH)
   public static void onContainerClose(Closing event) {
      HudRendering.CLOSE_CONTAINER.invoker().onClose(Minecraft.getInstance(), event.getScreen());
   }

   @SubscribeEvent(priority = EventPriority.HIGH)
   public static void onPlayerJoin(LoggingIn event) {
      LocalPlayerEvents.ON_LOGIN.invoker().onLogin(event.getPlayer());
   }

   @SubscribeEvent(priority = EventPriority.HIGH)
   public static void onPlayerLeave(LoggingOut event) {
      LocalPlayerEvents.ON_LOGOUT.invoker().onLogout(event.getPlayer());
   }

   @SubscribeEvent(priority = EventPriority.HIGH)
   public static void onPlayerRespawn(Clone event) {
      LocalPlayerEvents.ON_RESPAWN.invoker().onRespawn(event.getOldPlayer(), event.getNewPlayer());
   }

   @SubscribeEvent(priority = EventPriority.HIGH)
   public static void renderHudEvent(net.neoforged.neoforge.client.event.RenderGuiLayerEvent.Post event) {
      GuiGraphics matrices = event.getGuiGraphics();
      DeltaTracker tracker = event.getPartialTick();
      Minecraft minecraft = Minecraft.getInstance();
      HudRenderEvent.RenderContext context = new HudRenderEvent.RenderContext() {};
      if (Minecraft.useFancyGraphics()) {
         HudRenderEvent.RENDER_HUD.invoker().render(matrices, tracker.getGameTimeDeltaTicks(), HudRenderEvent.ElementType.VIGNETTE, context);
      }

      if (minecraft.gameMode.canHurtPlayer()) {
         HudRenderEvent.RENDER_HUD.invoker().render(matrices, tracker.getGameTimeDeltaTicks(), HudRenderEvent.ElementType.HEALTH, context);
      }

      if (minecraft.gameMode.hasExperience()) {
         HudRenderEvent.RENDER_HUD.invoker().render(matrices, tracker.getGameTimeDeltaTicks(), HudRenderEvent.ElementType.EXPERIENCE, context);
      }

      if (minecraft.options.getCameraType().isFirstPerson()) {
         HudRenderEvent.RENDER_HUD.invoker().render(matrices, tracker.getGameTimeDeltaTicks(), HudRenderEvent.ElementType.FIRST_PERSON, context);
      }

      HudRenderEvent.RENDER_HUD.invoker().render(matrices, tracker.getGameTimeDeltaTicks(), HudRenderEvent.ElementType.DEFAULT, context);
   }

   @SubscribeEvent(priority = EventPriority.HIGH)
   public static void renderFogColor(ComputeFogColor event) {
      FogRendering.ColorData data = new FogRendering.ColorData(event.getCamera(), event.getRed(), event.getGreen(), event.getBlue());
      FogRendering.FOG_COLOR.invoker().setColor(data, (float)event.getPartialTick());
      event.setRed(data.getRed());
      event.setGreen(data.getGreen());
      event.setBlue(data.getBlue());
   }

   @SubscribeEvent(priority = EventPriority.HIGH)
   public static void setupFogDensity(RenderFog event) {
      float density = FogRendering.FOG_DENSITY.invoker().setDensity(event.getCamera(), 0.1F);
      if (density != 0.1F) {
         event.setNearPlaneDistance(-8.0F);
         event.setFarPlaneDistance(density * 0.5F);
         event.setCanceled(true);
      }
   }

   @SubscribeEvent(priority = EventPriority.HIGH)
   public static void renderFog(RenderFog event) {
      FogRendering.FogData data = new FogRendering.FogData(event.getNearPlaneDistance(), event.getFarPlaneDistance(), event.getFogShape());
      if (FogRendering.FOG_RENDER
         .invoker()
         .onFogRender(
            event.getMode(),
            event.getType(),
            event.getCamera(),
            (float)event.getPartialTick(),
            event.getRenderer().getRenderDistance(),
            event.getFarPlaneDistance(),
            event.getNearPlaneDistance(),
            event.getFogShape(),
            data
         )
         .isCancelled()) {
         event.setNearPlaneDistance(data.getNearPlaneDistance());
         event.setFarPlaneDistance(data.getFarPlaneDistance());
         event.setFogShape(data.getShape());
      }
   }
}
