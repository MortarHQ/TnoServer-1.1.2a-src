package com.blamejared.crafttweaker.natives.event.block;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.NeoForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.event.level.BlockEvent.BlockToolModificationEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/block/BlockToolModificationEvent")
@NativeTypeRegistration(value = BlockToolModificationEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.block.BlockToolModificationEvent")
public class ExpandBlockToolModificationEvent {
   @ZenEvent.Bus
   public static final IEventBus<BlockToolModificationEvent> BUS = IEventBus.cancelable(
      BlockToolModificationEvent.class, NeoForgeEventBusWire.of(), NeoForgeEventCancellationCarrier.of()
   );

   @ZenCodeType.Getter("player")
   public static Player getPlayer(BlockToolModificationEvent internal) {
      return internal.getPlayer();
   }

   @ZenCodeType.Getter("heldItemStack")
   public static IItemStack getHeldItemStack(BlockToolModificationEvent internal) {
      return IItemStack.of(internal.getHeldItemStack());
   }

   @ZenCodeType.Getter("itemAbility")
   public static ItemAbility getItemAbility(BlockToolModificationEvent internal) {
      return internal.getItemAbility();
   }

   @ZenCodeType.Getter("isSimulated")
   public static boolean isSimulated(BlockToolModificationEvent internal) {
      return internal.isSimulated();
   }

   @ZenCodeType.Getter("context")
   public static UseOnContext getContext(BlockToolModificationEvent internal) {
      return internal.getContext();
   }

   @ZenCodeType.Setter("finalState")
   public static void setFinalState(BlockToolModificationEvent internal, BlockState state) {
      internal.setFinalState(state);
   }

   @ZenCodeType.Getter("finalState")
   public static @ZenCodeType.Nullable BlockState getFinalState(BlockToolModificationEvent internal) {
      return internal.getFinalState();
   }
}
