package com.blamejared.crafttweaker.natives.world;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.storage.LevelData;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/world/LevelAccessor")
@NativeTypeRegistration(value = LevelAccessor.class, zenCodeName = "crafttweaker.api.world.LevelAccessor")
public class ExpandLevelAccessor {
   @ZenCodeType.Getter("levelData")
   public static LevelData getLevelData(LevelAccessor internal) {
      return internal.getLevelData();
   }

   @ZenCodeType.Method
   public static DifficultyInstance getCurrentDifficultyAt(LevelAccessor internal, BlockPos position) {
      return internal.getCurrentDifficultyAt(position);
   }

   @ZenCodeType.Getter("difficulty")
   public static Difficulty getDifficulty(LevelAccessor internal) {
      return internal.getDifficulty();
   }

   @ZenCodeType.Getter("random")
   public static RandomSource getRandom(LevelAccessor internal) {
      return internal.getRandom();
   }

   @ZenCodeType.Method
   public static void playSound(LevelAccessor internal, @ZenCodeType.Nullable Player player, BlockPos position, SoundEvent event, SoundSource source) {
      internal.playSound(player, position, event, source);
   }

   @ZenCodeType.Method
   public static void playSound(
      LevelAccessor internal, @ZenCodeType.Nullable Player player, BlockPos position, SoundEvent event, SoundSource source, float volume, float pitch
   ) {
      internal.playSound(player, position, event, source, volume, pitch);
   }

   @ZenCodeType.Method
   public static void levelEvent(LevelAccessor internal, @ZenCodeType.Nullable Player excluded, int event, BlockPos position, int extra) {
      internal.levelEvent(excluded, event, position, extra);
   }

   @ZenCodeType.Method
   public static void levelEvent(LevelAccessor internal, int event, BlockPos position, int extra) {
      internal.levelEvent(event, position, extra);
   }
}
