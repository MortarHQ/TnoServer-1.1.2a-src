package com.blamejared.crafttweaker.natives.event.entity.player;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.TabListNameFormat;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/entity/player/TabListNameFormat")
@NativeTypeRegistration(value = TabListNameFormat.class, zenCodeName = "crafttweaker.neoforge.api.event.entity.player.TabListNameFormat")
public class ExpandPlayerTabListNameFormatEvent {
   @ZenEvent.Bus
   public static final IEventBus<TabListNameFormat> BUS = IEventBus.direct(TabListNameFormat.class, NeoForgeEventBusWire.of());

   @ZenCodeType.Getter("displayName")
   public static @ZenCodeType.Nullable Component getDisplayName(TabListNameFormat internal) {
      return internal.getDisplayName();
   }

   @ZenCodeType.Setter("displayName")
   public static void setDisplayName(TabListNameFormat internal, @ZenCodeType.Nullable Component displayName) {
      internal.setDisplayName(displayName);
   }
}
