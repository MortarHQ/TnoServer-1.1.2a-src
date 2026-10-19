package com.almostreliable.lootjs.loot.extension;

import com.almostreliable.lootjs.LootJS;
import com.almostreliable.lootjs.core.LootType;
import com.almostreliable.lootjs.util.LootContextUtils;
import dev.latvian.mods.rhino.util.RemapPrefixForJS;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

@RemapPrefixForJS("lootjs$")
public interface LootContextExtension {
   static LootContextExtension cast(LootContext context) {
      return (LootContextExtension)context;
   }

   LootContext lootjs$self();

   default ResourceLocation lootjs$getId() {
      return this.lootjs$self().getQueriedLootTableId();
   }

   default boolean lootjs$isType(LootType type) {
      return type == this.lootjs$getType();
   }

   LootType lootjs$getType();

   default Vec3 lootjs$getPosition() {
      Vec3 pos = (Vec3)this.lootjs$self().getParamOrNull(LootContextParams.ORIGIN);
      if (pos != null) {
         return pos;
      }

      Entity entity = this.lootjs$getEntity();
      if (entity != null) {
         return entity.position();
      }

      LootJS.LOG.warn("Loot table {} has no position. This should not happen", this.lootjs$self().getQueriedLootTableId());
      return Vec3.ZERO;
   }

   @Nullable
   default Entity lootjs$getEntity() {
      return (Entity)this.lootjs$self().getParamOrNull(LootContextParams.THIS_ENTITY);
   }

   @Nullable
   default Entity lootjs$getAttackingEntity() {
      return (Entity)this.lootjs$self().getParamOrNull(LootContextParams.ATTACKING_ENTITY);
   }

   @Nullable
   default ServerPlayer lootjs$getKillerPlayer() {
      return LootContextUtils.getPlayerOrNull(this.lootjs$self());
   }

   @Nullable
   default DamageSource lootjs$getDamageSource() {
      return (DamageSource)this.lootjs$self().getParamOrNull(LootContextParams.DAMAGE_SOURCE);
   }

   default ItemStack lootjs$getTool() {
      ItemStack tool = (ItemStack)this.lootjs$self().getParamOrNull(LootContextParams.TOOL);
      return tool != null ? tool : ItemStack.EMPTY;
   }

   default boolean lootjs$isExploded() {
      return this.lootjs$self().hasParam(LootContextParams.EXPLOSION_RADIUS);
   }

   default float lootjs$getExplosionRadius() {
      Float f = (Float)this.lootjs$self().getParamOrNull(LootContextParams.EXPLOSION_RADIUS);
      return f != null ? f : 0.0F;
   }

   @Nullable
   default MinecraftServer lootjs$getServer() {
      return this.lootjs$self().getLevel().getServer();
   }

   Map<String, Object> lootjs$getData();
}
