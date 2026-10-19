package com.blamejared.crafttweaker.natives.world;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.level.CraftTweakerSavedData;
import com.blamejared.crafttweaker.api.level.CraftTweakerSavedDataHolder;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.natives.entity.ExpandEntityTypeTest;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/world/ServerLevel")
@NativeTypeRegistration(value = ServerLevel.class, zenCodeName = "crafttweaker.api.world.ServerLevel")
public class ExpandServerLevel {
   @ZenCodeType.Method
   @ZenCodeType.Getter("customData")
   public static CraftTweakerSavedData getCustomData(ServerLevel internal) {
      return ((CraftTweakerSavedDataHolder)internal).crafttweaker$getSavedData();
   }

   @ZenCodeType.Setter("dayTime")
   public static void setDayTime(ServerLevel internal, long time) {
      internal.setDayTime(time);
   }

   @ZenCodeType.Method
   public static void setTimeToDay(ServerLevel internal) {
      internal.setDayTime(1000L);
   }

   @ZenCodeType.Method
   public static void setTimeToNoon(ServerLevel internal) {
      internal.setDayTime(6000L);
   }

   @ZenCodeType.Method
   public static void setTimeToNight(ServerLevel internal) {
      internal.setDayTime(13000L);
   }

   @ZenCodeType.Method
   public static void setTimeToMidnight(ServerLevel internal) {
      internal.setDayTime(18000L);
   }

   @ZenCodeType.Getter("seed")
   public static long getSeed(ServerLevel internal) {
      return internal.getSeed();
   }

   @ZenCodeType.Method
   public static boolean isVillage(ServerLevel internal, BlockPos pos) {
      return internal.isVillage(pos);
   }

   @ZenCodeType.Method
   public static boolean isRaided(ServerLevel internal, BlockPos pos) {
      return internal.isRaided(pos);
   }

   @ZenCodeType.Method
   public static boolean isSlimeChunk(ServerLevel internal, BlockPos pos) {
      return WorldgenRandom.seedSlimeChunk(pos.getX() >> 4, pos.getZ() >> 4, internal.getSeed(), 987234911L).nextInt(10) == 0;
   }

   @ZenCodeType.Getter("server")
   public static MinecraftServer getServer(ServerLevel internal) {
      return internal.getServer();
   }

   @ZenCodeType.Method
   public static List<Entity> getEntities(ServerLevel internal, Predicate<Entity> predicate, @ZenCodeType.Optional EntityType<Entity> type) {
      return GenericUtil.uncheck(internal.getEntities((EntityTypeTest)(type == null ? ExpandEntityTypeTest.ANY : type), predicate));
   }

   @ZenCodeType.Getter("damageSources")
   public static DamageSources damageSources(ServerLevel level) {
      return level.damageSources();
   }
}
