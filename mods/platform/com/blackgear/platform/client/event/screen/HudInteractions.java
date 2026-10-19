package com.blackgear.platform.client.event.screen;

import com.blackgear.platform.core.util.event.CancellableResult;
import com.blackgear.platform.core.util.event.Event;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface HudInteractions {
   Event<HudInteractions.MouseScroll> SCROLLING_PRE = Event.cancellable(HudInteractions.MouseScroll.class);
   Event<HudInteractions.MouseScroll> SCROLLING_POST = Event.cancellable(HudInteractions.MouseScroll.class);
   Event<HudInteractions.MouseClick> CLICKING_PRE = Event.cancellable(HudInteractions.MouseClick.class);
   Event<HudInteractions.MouseClick> CLICKING_POST = Event.cancellable(HudInteractions.MouseClick.class);
   Event<HudInteractions.MouseRelease> RELEASING_PRE = Event.cancellable(HudInteractions.MouseRelease.class);
   Event<HudInteractions.MouseRelease> RELEASING_POST = Event.cancellable(HudInteractions.MouseRelease.class);
   Event<HudInteractions.SlotClicked> SLOT_CLICK = Event.create(HudInteractions.SlotClicked.class);
   Event<HudInteractions.StopHovering> STOP_HOVERING = Event.create(HudInteractions.StopHovering.class);

   interface MouseClick {
      CancellableResult onClicking(Minecraft var1, Screen var2, double var3, double var5, int var7);
   }

   interface MouseRelease {
      CancellableResult onReleasing(Minecraft var1, Screen var2, double var3, double var5, int var7);
   }

   interface MouseScroll {
      CancellableResult onScrolling(Minecraft var1, Screen var2, double var3, double var5, double var7, double var9);
   }

   interface SlotClicked {
      void onMouseClick(Minecraft var1, AbstractContainerScreen<?> var2, Slot var3, ClickType var4);
   }

   interface StopHovering {
      void onStopHovering(Minecraft var1, AbstractContainerScreen<?> var2, Slot var3);
   }
}
