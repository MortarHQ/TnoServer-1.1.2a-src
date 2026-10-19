package com.blamejared.crafttweaker.natives.event.entity.player;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.HarvestCheck;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/entity/player/PlayerHarvestCheckEvent")
@NativeTypeRegistration(value = HarvestCheck.class, zenCodeName = "crafttweaker.neoforge.api.event.entity.player.PlayerHarvestCheckEvent")
public class ExpandPlayerHarvestCheckEvent {
   @ZenEvent.Bus
   public static final IEventBus<HarvestCheck> BUS = IEventBus.direct(HarvestCheck.class, NeoForgeEventBusWire.of());

   @ZenCodeType.Getter("targetBlock")
   public static BlockState getTargetBlock(HarvestCheck internal) {
      return internal.getTargetBlock();
   }

   @ZenCodeType.Getter("canHarvest")
   public static boolean canHarvest(HarvestCheck internal) {
      return internal.canHarvest();
   }

   @ZenCodeType.Setter("canHarvest")
   public static void setCanHarvest(HarvestCheck internal, boolean success) {
      internal.setCanHarvest(success);
   }
}
