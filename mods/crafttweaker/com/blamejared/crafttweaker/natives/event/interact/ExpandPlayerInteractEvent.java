package com.blamejared.crafttweaker.natives.event.interact;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.item.MCItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("neoforge/api/event/interact/PlayerInteractEvent")
@NativeTypeRegistration(value = PlayerInteractEvent.class, zenCodeName = "crafttweaker.neoforge.api.interact.PlayerInteractEvent")
public class ExpandPlayerInteractEvent {
   @ZenCodeType.Getter("blockPos")
   public static BlockPos getBlockPos(PlayerInteractEvent internal) {
      return internal.getPos();
   }

   @ZenCodeType.Getter("itemStack")
   public static IItemStack getItemStack(PlayerInteractEvent internal) {
      return new MCItemStack(internal.getItemStack());
   }

   @ZenCodeType.Getter("face")
   public static @ZenCodeType.Nullable Direction getFace(PlayerInteractEvent internal) {
      return internal.getFace();
   }

   @ZenCodeType.Getter("hand")
   public static InteractionHand getHand(PlayerInteractEvent internal) {
      return internal.getHand();
   }
}
