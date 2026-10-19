package com.blamejared.crafttweaker.natives.loot.param;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/loot/param/LootContextParams")
@NativeTypeRegistration(value = LootContextParams.class, zenCodeName = "crafttweaker.api.loot.param.LootContextParams")
public final class ExpandLootContextParams {
   private static final Map<ResourceLocation, LootContextParam<?>> PRE_REGISTERED_PARAMS = (Map<ResourceLocation, LootContextParam<?>>)Util.make(
      new HashMap(), map -> {
         map.put(ResourceLocation.parse("this_entity"), LootContextParams.THIS_ENTITY);
         map.put(ResourceLocation.parse("last_damage_player"), LootContextParams.LAST_DAMAGE_PLAYER);
         map.put(ResourceLocation.parse("damage_source"), LootContextParams.DAMAGE_SOURCE);
         map.put(ResourceLocation.parse("attacking_entity"), LootContextParams.ATTACKING_ENTITY);
         map.put(ResourceLocation.parse("direct_attacking_entity"), LootContextParams.DIRECT_ATTACKING_ENTITY);
         map.put(ResourceLocation.parse("origin"), LootContextParams.ORIGIN);
         map.put(ResourceLocation.parse("block_state"), LootContextParams.BLOCK_STATE);
         map.put(ResourceLocation.parse("block_entity"), LootContextParams.BLOCK_ENTITY);
         map.put(ResourceLocation.parse("tool"), LootContextParams.TOOL);
         map.put(ResourceLocation.parse("explosion_radius"), LootContextParams.EXPLOSION_RADIUS);
      }
   );

   @ZenCodeType.StaticExpansionMethod
   public static LootContextParam<Entity> thisEntity() {
      return LootContextParams.THIS_ENTITY;
   }

   @ZenCodeType.StaticExpansionMethod
   public static LootContextParam<Player> lastDamagePlayer() {
      return LootContextParams.LAST_DAMAGE_PLAYER;
   }

   @ZenCodeType.StaticExpansionMethod
   public static LootContextParam<DamageSource> damageSource() {
      return LootContextParams.DAMAGE_SOURCE;
   }

   @ZenCodeType.StaticExpansionMethod
   public static LootContextParam<Entity> attackingEntity() {
      return LootContextParams.ATTACKING_ENTITY;
   }

   @ZenCodeType.StaticExpansionMethod
   public static LootContextParam<Entity> directAttackingEntity() {
      return LootContextParams.DIRECT_ATTACKING_ENTITY;
   }

   @ZenCodeType.StaticExpansionMethod
   public static LootContextParam<Vec3> origin() {
      return LootContextParams.ORIGIN;
   }

   @ZenCodeType.StaticExpansionMethod
   public static LootContextParam<BlockState> blockState() {
      return LootContextParams.BLOCK_STATE;
   }

   @ZenCodeType.StaticExpansionMethod
   public static LootContextParam<BlockEntity> blockEntity() {
      return LootContextParams.BLOCK_ENTITY;
   }

   @ZenCodeType.StaticExpansionMethod
   public static LootContextParam<ItemStack> tool() {
      return LootContextParams.TOOL;
   }

   @ZenCodeType.StaticExpansionMethod
   public static LootContextParam<Float> explosionRadius() {
      return LootContextParams.EXPLOSION_RADIUS;
   }

   @ZenCodeType.StaticExpansionMethod
   public static <T> LootContextParam<T> getOrCreate(ResourceLocation name, Class<T> tClass) {
      return GenericUtil.uncheck(PRE_REGISTERED_PARAMS.computeIfAbsent(name, LootContextParam::new));
   }
}
