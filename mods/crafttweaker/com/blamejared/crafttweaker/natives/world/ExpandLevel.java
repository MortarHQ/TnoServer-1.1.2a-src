package com.blamejared.crafttweaker.natives.world;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.util.sequence.SequenceBuilder;
import com.blamejared.crafttweaker.api.util.sequence.SequenceType;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.google.common.base.Suppliers;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/world/Level")
@NativeTypeRegistration(value = Level.class, zenCodeName = "crafttweaker.api.world.Level")
public class ExpandLevel {
   @ZenCodeType.Method
   public static SequenceBuilder<Level, IData> sequence(Level internal, @ZenCodeType.Optional("new crafttweaker.api.data.MapData()") IData data) {
      return sequence(internal, IData.class, data);
   }

   @ZenCodeType.Method
   public static <T> SequenceBuilder<Level, T> sequence(Level internal, Class<T> dataClass, T data) {
      return new SequenceBuilder<>(
         internal.isClientSide ? SequenceType.CLIENT_THREAD_LEVEL : SequenceType.SERVER_THREAD_LEVEL, Suppliers.memoize(() -> internal), data
      );
   }

   @ZenCodeType.Method
   public static void destroyBlockProgress(Level internal, int breakerId, BlockPos pos, int progress) {
      internal.destroyBlockProgress(breakerId, pos, progress);
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("isDay")
   public static boolean isDay(Level internal) {
      return internal.isDay();
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("isNight")
   public static boolean isNight(Level internal) {
      return internal.isNight();
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("gameTime")
   public static long getGametime(Level internal) {
      return internal.getGameTime();
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("daytime")
   public static long getDayTime(Level internal) {
      return internal.getDayTime();
   }

   @ZenCodeType.Getter("raining")
   public static boolean isRaining(Level internal) {
      return internal.isRaining();
   }

   @ZenCodeType.Method
   @ZenCodeType.Setter("rainLevel")
   public static void setRainingLevel(Level internal, float level) {
      internal.setRainLevel(level);
   }

   @ZenCodeType.Getter("thundering")
   public static boolean isThundering(Level internal) {
      return internal.isThundering();
   }

   @ZenCodeType.Getter("dimension")
   public static ResourceLocation getDimension(Level internal) {
      return internal.dimension().location();
   }

   @ZenCodeType.Method
   public static boolean isRainingAt(Level internal, BlockPos pos) {
      return internal.isRainingAt(pos);
   }

   @ZenCodeType.Method
   public static int getDirectSignalTo(Level internal, BlockPos pos) {
      return internal.getDirectSignalTo(pos);
   }

   @ZenCodeType.Method
   public static int getSignal(Level internal, BlockPos pos, Direction direction) {
      return internal.getSignal(pos, direction);
   }

   @ZenCodeType.Method
   public static int getBestNeighborSignal(Level internal, BlockPos pos) {
      return internal.getBestNeighborSignal(pos);
   }

   @ZenCodeType.Method
   public static boolean setBlockAndUpdate(Level internal, BlockPos pos, BlockState state) {
      return internal.setBlockAndUpdate(pos, state);
   }

   @ZenCodeType.Method
   public static boolean hasNeighborSignal(Level internal, BlockPos pos) {
      return internal.hasNeighborSignal(pos);
   }

   @ZenCodeType.Method
   public static void globalLevelEvent(Level internal, int eventId, BlockPos pos, int data) {
      internal.globalLevelEvent(eventId, pos, data);
   }

   @ZenCodeType.Method
   public static boolean isLoaded(Level internal, BlockPos pos) {
      return internal.isLoaded(pos);
   }

   @ZenCodeType.Method
   public static <T extends Entity> List<T> getEntitiesOfClass(
      Level internal, Class<T> typeOfT, double x1, double y1, double z1, double x2, double y2, double z2
   ) {
      return internal.getEntitiesOfClass(typeOfT, new AABB(x1, y1, z1, x2, y2, z2));
   }

   @ZenCodeType.Method
   public static <T extends Entity> List<T> getEntitiesInArea(Level internal, Class<T> typeOfT, BlockPos pos1, @ZenCodeType.Optional BlockPos pos2) {
      AABB aabb = new AABB(pos1);
      if (pos2 != null) {
         aabb = AABB.encapsulatingFullBlocks(pos1, pos2);
      }

      return internal.getEntitiesOfClass(typeOfT, aabb);
   }

   @ZenCodeType.Method
   public static List<Entity> getEntities(
      Level internal,
      @ZenCodeType.Nullable Entity excludingEntity,
      double x1,
      double y1,
      double z1,
      double x2,
      double y2,
      double z2,
      Predicate<Entity> predicate
   ) {
      return internal.getEntities(excludingEntity, new AABB(x1, y1, z1, x2, y2, z2), predicate);
   }

   @ZenCodeType.Method
   public static List<Entity> getEntitiesInAreaExcluding(
      Level internal, @ZenCodeType.Nullable Entity excludingEntity, Predicate<Entity> predicate, BlockPos pos1, @ZenCodeType.Optional BlockPos pos2
   ) {
      AABB aabb = new AABB(pos1);
      if (pos2 != null) {
         aabb = AABB.encapsulatingFullBlocks(pos1, pos2);
      }

      return internal.getEntities(excludingEntity, aabb, predicate);
   }

   @ZenCodeType.Method
   public static @ZenCodeType.Nullable BlockEntity getBlockEntity(Level internal, BlockPos pos) {
      return internal.getBlockEntity(pos);
   }

   @ZenCodeType.Method
   public static BlockHitResult rayTraceBlocks(
      Level internal, Vec3 startVec, Vec3 endVec, Block blockMode, Fluid fluidMode, @ZenCodeType.Optional Entity entity
   ) {
      return internal.clip(new ClipContext(startVec, endVec, blockMode, fluidMode, entity));
   }
}
