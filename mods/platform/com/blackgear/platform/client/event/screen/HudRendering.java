package com.blackgear.platform.client.event.screen;

import com.blackgear.platform.client.event.screen.api.ScreenAccess;
import com.blackgear.platform.core.util.event.CancellableResult;
import com.blackgear.platform.core.util.event.Event;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface HudRendering {
   Event<HudRendering.Rendering> RENDERING = Event.create(HudRendering.Rendering.class);
   Event<HudRendering.ScreenPreInitialization> PRE_INITIALIZE = Event.cancellable(HudRendering.ScreenPreInitialization.class);
   Event<HudRendering.ScreenPostInitialization> POST_INITIALIZE = Event.create(HudRendering.ScreenPostInitialization.class);
   Event<HudRendering.ScreenPreRendering> PRE_RENDERING = Event.cancellable(HudRendering.ScreenPreRendering.class);
   Event<HudRendering.ScreenPostRendering> POST_RENDERING = Event.create(HudRendering.ScreenPostRendering.class);
   Event<HudRendering.ContainerRenderBackground> RENDER_BACKGROUND = Event.create(HudRendering.ContainerRenderBackground.class);
   Event<HudRendering.ContainerRenderForeground> RENDER_FOREGROUND = Event.create(HudRendering.ContainerRenderForeground.class);
   Event<HudRendering.OpenContainer> OPEN_CONTAINER = Event.create(HudRendering.OpenContainer.class);
   Event<HudRendering.CloseContainer> CLOSE_CONTAINER = Event.create(HudRendering.CloseContainer.class);

   interface CloseContainer {
      void onClose(Minecraft var1, Screen var2);
   }

   interface ContainerRenderBackground {
      void onRender(Minecraft var1, AbstractContainerScreen<?> var2, GuiGraphics var3, int var4, int var5, DeltaTracker var6);
   }

   interface ContainerRenderForeground {
      void onRender(Minecraft var1, AbstractContainerScreen<?> var2, GuiGraphics var3, int var4, int var5, DeltaTracker var6);
   }

   interface OpenContainer {
      void onOpen(Minecraft var1, Screen var2);
   }

   interface Rendering {
      void onRender(Minecraft var1, GuiGraphics var2, DeltaTracker var3);
   }

   interface ScreenPostInitialization {
      void onInitialize(Minecraft var1, Screen var2, ScreenAccess var3);
   }

   interface ScreenPostRendering {
      void onRender(Minecraft var1, Screen var2, GuiGraphics var3, int var4, int var5, DeltaTracker var6);
   }

   interface ScreenPreInitialization {
      CancellableResult onInitialize(Minecraft var1, Screen var2, ScreenAccess var3);
   }

   interface ScreenPreRendering {
      CancellableResult onRender(Minecraft var1, Screen var2, GuiGraphics var3, int var4, int var5, DeltaTracker var6);
   }
}
