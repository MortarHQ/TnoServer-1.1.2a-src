package com.blamejared.crafttweaker.natives.event.item;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/item/ItemTooltipEvent")
@NativeTypeRegistration(value = ItemTooltipEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.item.ItemTooltipEvent")
public class ExpandItemTooltipEvent {
   @ZenEvent.Bus
   public static final IEventBus<ItemTooltipEvent> BUS = IEventBus.direct(ItemTooltipEvent.class, NeoForgeEventBusWire.of());

   @ZenCodeType.Method
   @ZenCodeType.Getter("flags")
   public static TooltipFlag getFlags(ItemTooltipEvent internal) {
      return internal.getFlags();
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("itemStack")
   public static IItemStack getItemStack(ItemTooltipEvent internal) {
      return IItemStack.of(internal.getItemStack());
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("tooltip")
   public static List<Component> getToolTip(ItemTooltipEvent internal) {
      return internal.getToolTip();
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("player")
   public static @ZenCodeType.Nullable Player getPlayer(ItemTooltipEvent internal) {
      return internal.getEntity();
   }
}
