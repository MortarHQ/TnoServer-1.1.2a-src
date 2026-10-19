package com.blamejared.crafttweaker.natives.entity.type.misc;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.mixin.common.access.entity.AccessFallingBlockEntity;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/entity/type/misc/FallingBlockEntity")
@NativeTypeRegistration(value = FallingBlockEntity.class, zenCodeName = "crafttweaker.api.entity.type.misc.FallingBlockEntity")
public class ExpandFallingBlockEntity {
   @ZenCodeType.StaticExpansionMethod
   public static FallingBlockEntity fall(Level level, BlockPos pos, BlockState state) {
      return FallingBlockEntity.fall(level, pos, state);
   }

   @ZenCodeType.Method
   public static void setStartPos(FallingBlockEntity internal, BlockPos pos) {
      internal.setStartPos(pos);
   }

   @ZenCodeType.Method
   public static BlockPos getStartPos(FallingBlockEntity internal) {
      return internal.getStartPos();
   }

   @ZenCodeType.Method
   public static void callOnBrokenAfterFall(FallingBlockEntity internal, Block fallableBlock, BlockPos position) {
      internal.callOnBrokenAfterFall(fallableBlock, position);
   }

   @ZenCodeType.Method
   public static void setHurtsEntities(FallingBlockEntity internal, float damagePerDistance, int maxDamage) {
      internal.setHurtsEntities(damagePerDistance, maxDamage);
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("blockstate")
   public static BlockState getBlockState(FallingBlockEntity internal) {
      return internal.getBlockState();
   }

   @ZenCodeType.Method
   @ZenCodeType.Setter("blockstate")
   public static void setBlockState(FallingBlockEntity internal, BlockState state) {
      ((AccessFallingBlockEntity)internal).crafttweaker$setBlockState(state);
   }
}
