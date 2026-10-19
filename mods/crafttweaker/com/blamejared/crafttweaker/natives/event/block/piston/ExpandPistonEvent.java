package com.blamejared.crafttweaker.natives.event.block.piston;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.NeoForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.piston.PistonStructureResolver;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.level.PistonEvent;
import net.neoforged.neoforge.event.level.PistonEvent.PistonMoveType;
import net.neoforged.neoforge.event.level.PistonEvent.Post;
import net.neoforged.neoforge.event.level.PistonEvent.Pre;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("neoforge/api/event/block/piston/PistonEvent")
@NativeTypeRegistration(value = PistonEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.block.piston.PistonEvent")
public class ExpandPistonEvent {
   @ZenCodeType.Getter("level")
   public static Level getLevel(BlockEvent internal) {
      return (Level)internal.getLevel();
   }

   @ZenCodeType.Getter("direction")
   public static Direction getDirection(PistonEvent internal) {
      return internal.getDirection();
   }

   @ZenCodeType.Getter("faceOffsetPos")
   public static BlockPos getFaceOffsetPos(PistonEvent internal) {
      return internal.getFaceOffsetPos();
   }

   @ZenCodeType.Getter("pistonMoveType")
   public static PistonMoveType getPistonMoveType(PistonEvent internal) {
      return internal.getPistonMoveType();
   }

   @ZenCodeType.Getter("structureHelper")
   public static @ZenCodeType.Nullable PistonStructureResolver getStructureHelper(PistonEvent internal) {
      return internal.getStructureHelper();
   }

   @ZenRegister
   @ZenEvent
   @Document("neoforge/api/event/block/piston/PostPistonEvent")
   @NativeTypeRegistration(value = Post.class, zenCodeName = "crafttweaker.neoforge.api.event.block.piston.PostPistonEvent")
   public static class ExpandPistonEventPostEvent {
      @ZenEvent.Bus
      public static final IEventBus<Post> BUS = IEventBus.direct(Post.class, NeoForgeEventBusWire.of());
   }

   @ZenRegister
   @ZenEvent
   @Document("neoforge/api/event/block/piston/PrePistonEvent")
   @NativeTypeRegistration(value = Pre.class, zenCodeName = "crafttweaker.neoforge.api.event.block.piston.PrePistonEvent")
   public static class ExpandPistonEventPreEvent {
      @ZenEvent.Bus
      public static final IEventBus<Pre> BUS = IEventBus.cancelable(Pre.class, NeoForgeEventBusWire.of(), NeoForgeEventCancellationCarrier.of());
   }
}
