package com.blamejared.crafttweaker.natives.event.interact;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.NeoForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.LeftClickBlock.Action;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/interact/LeftClickBlockEvent")
@NativeTypeRegistration(value = LeftClickBlock.class, zenCodeName = "crafttweaker.neoforge.api.event.interact.LeftClickBlockEvent")
public class ExpandLeftClickBlockEvent {
   @ZenEvent.Bus
   public static final IEventBus<LeftClickBlock> BUS = IEventBus.cancelable(
      LeftClickBlock.class, NeoForgeEventBusWire.of(), NeoForgeEventCancellationCarrier.of()
   );

   @ZenCodeType.Setter("canceled")
   public static void setCanceled(LeftClickBlock internal, boolean canceled) {
      internal.setCanceled(canceled);
   }

   @ZenCodeType.Setter("useItem")
   public static void setUseItem(LeftClickBlock internal, TriState triggerItem) {
      internal.setUseItem(triggerItem);
   }

   @ZenCodeType.Setter("useBlock")
   public static void setUseBlock(LeftClickBlock internal, TriState triggerBlock) {
      internal.setUseBlock(triggerBlock);
   }

   @ZenCodeType.Getter("action")
   public static Action getAction(LeftClickBlock internal) {
      return internal.getAction();
   }

   @ZenCodeType.Getter("useItem")
   public static TriState getUseItem(LeftClickBlock internal) {
      return internal.getUseItem();
   }

   @ZenCodeType.Getter("useBlock")
   public static TriState getUseBlock(LeftClickBlock internal) {
      return internal.getUseBlock();
   }

   @ZenRegister
   @Document("neoforge/api/event/interact/LeftClickBlockEventAction")
   @NativeTypeRegistration(value = Action.class, zenCodeName = "crafttweaker.neoforge.api.event.interact.LeftClickBlockEventAction")
   @BracketEnum("neoforge:events/leftclickblock/action")
   public static class ExpandLeftClickBlockEventAction {
   }
}
