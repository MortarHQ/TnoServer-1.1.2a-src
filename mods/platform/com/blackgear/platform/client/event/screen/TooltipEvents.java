package com.blackgear.platform.client.event.screen;

import com.blackgear.platform.core.util.event.CancellableResult;
import com.blackgear.platform.core.util.event.Event;
import java.util.List;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.TooltipContext;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface TooltipEvents {
   Event<TooltipEvents.Item> ITEM_SETUP = Event.create(TooltipEvents.Item.class);
   Event<TooltipEvents.RenderTooltip> RENDER_TOOLTIP = Event.cancellable(TooltipEvents.RenderTooltip.class);

   interface Item {
      void registerTooltip(ItemStack var1, TooltipContext var2, TooltipFlag var3, List<Component> var4);
   }

   interface RenderTooltip {
      CancellableResult onRendering(GuiGraphics var1, List<? extends ClientTooltipComponent> var2, int var3, int var4);
   }
}
