package com.blamejared.crafttweaker.natives.loot;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.natives.item.ExpandItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/loot/LootContext")
@NativeTypeRegistration(value = LootContext.class, zenCodeName = "crafttweaker.api.loot.LootContext")
public final class ExpandLootContext {
   @ZenCodeType.Getter("thisEntity")
   public static @ZenCodeType.Nullable Entity getThisEntity(LootContext internal) {
      return (Entity)internal.getParamOrNull(LootContextParams.THIS_ENTITY);
   }

   @ZenCodeType.Getter("lastDamagePlayer")
   public static @ZenCodeType.Nullable Player getLastDamagePlayer(LootContext internal) {
      return (Player)internal.getParamOrNull(LootContextParams.LAST_DAMAGE_PLAYER);
   }

   @ZenCodeType.Getter("damageSource")
   public static @ZenCodeType.Nullable DamageSource getDamageSource(LootContext internal) {
      return (DamageSource)internal.getParamOrNull(LootContextParams.DAMAGE_SOURCE);
   }

   @ZenCodeType.Getter("attackingEntity")
   public static @ZenCodeType.Nullable Entity getAttackingEntity(LootContext internal) {
      return (Entity)internal.getParamOrNull(LootContextParams.ATTACKING_ENTITY);
   }

   @ZenCodeType.Getter("directAttackingEntity")
   public static @ZenCodeType.Nullable Entity getDirectAttackingEntity(LootContext internal) {
      return (Entity)internal.getParamOrNull(LootContextParams.DIRECT_ATTACKING_ENTITY);
   }

   @ZenCodeType.Getter("origin")
   public static @ZenCodeType.Nullable Vec3 getOrigin(LootContext internal) {
      return (Vec3)internal.getParamOrNull(LootContextParams.ORIGIN);
   }

   @ZenCodeType.Getter("blockState")
   public static @ZenCodeType.Nullable BlockState getBlockState(LootContext internal) {
      return (BlockState)internal.getParamOrNull(LootContextParams.BLOCK_STATE);
   }

   @ZenCodeType.Getter("blockEntity")
   public static @ZenCodeType.Nullable BlockEntity getTileEntity(LootContext internal) {
      return (BlockEntity)internal.getParamOrNull(LootContextParams.BLOCK_ENTITY);
   }

   @ZenCodeType.Getter("tool")
   public static IItemStack getTool(LootContext internal) {
      ItemStack nativeTool = (ItemStack)internal.getParamOrNull(LootContextParams.TOOL);
      return ExpandItemStack.asIItemStack(nativeTool == null ? ItemStack.EMPTY : nativeTool);
   }

   @ZenCodeType.Getter("explosionRadius")
   public static float getExplosionRadius(LootContext internal) {
      Float nativeRadius = (Float)internal.getParamOrNull(LootContextParams.EXPLOSION_RADIUS);
      return nativeRadius == null ? 0.0F : nativeRadius;
   }

   @ZenCodeType.Getter("level")
   public static ServerLevel getWorld(LootContext internal) {
      return internal.getLevel();
   }

   @ZenCodeType.Getter("luck")
   public static float getLuck(LootContext internal) {
      return internal.getLuck();
   }

   @ZenCodeType.Getter("random")
   public static RandomSource getRandom(LootContext internal) {
      return internal.getRandom();
   }
}
