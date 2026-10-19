package com.mongoose.royalvariations.common.entities;

import com.mongoose.royalvariations.RoyalVariations;
import com.mongoose.royalvariations.common.entities.hostile.RoyalCreeper;
import com.mongoose.royalvariations.common.entities.hostile.RoyalSkeleton;
import com.mongoose.royalvariations.common.entities.hostile.RoyalZombie;
import com.mongoose.royalvariations.common.entities.neutral.RoyalEnderman;
import com.mongoose.royalvariations.common.entities.projectile.NetEntity;
import com.mongoose.royalvariations.common.entities.projectile.RoyalArrow;
import com.mongoose.royalvariations.common.entities.projectile.RoyalBomb;
import com.mongoose.royalvariations.common.entities.projectile.RoyalBombProjectile;
import com.mongoose.royalvariations.common.entities.projectile.SpectralAnvil;
import com.mongoose.royalvariations.common.entities.projectile.ThrownRoyalEnderPearl;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType.Builder;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RVEntityType {
   public static final DeferredRegister<EntityType<?>> ENTITY_TYPE = DeferredRegister.create(Registries.ENTITY_TYPE, "royalvariations");
   public static final DeferredHolder<EntityType<?>, EntityType<RoyalArrow>> ROYAL_ARROW = register(
      "royal_arrow", Builder.of(RoyalArrow::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<RoyalBombProjectile>> ROYAL_BOMB_PROJECTILE = register(
      "royal_bomb_projectile", Builder.of(RoyalBombProjectile::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(8).updateInterval(1)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<RoyalBomb>> ROYAL_BOMB = register(
      "royal_bomb", Builder.of(RoyalBomb::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<ThrownRoyalEnderPearl>> ROYAL_ENDER_PEARL = register(
      "royal_ender_pearl", Builder.of(ThrownRoyalEnderPearl::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<NetEntity>> NET = register(
      "net", Builder.of(NetEntity::new, MobCategory.MISC).sized(1.0F, 0.5F).clientTrackingRange(8).updateInterval(1)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<SpectralAnvil>> SPECTRAL_ANVIL = register(
      "spectral_anvil", Builder.of(SpectralAnvil::new, MobCategory.MISC).sized(0.98F, 0.98F).clientTrackingRange(8).updateInterval(1)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<RoyalZombie>> ROYAL_ZOMBIE = register(
      "royal_zombie", Builder.of(RoyalZombie::new, MobCategory.MONSTER).sized(0.6F, 1.95F).clientTrackingRange(8)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<RoyalSkeleton>> ROYAL_SKELETON = register(
      "royal_skeleton", Builder.of(RoyalSkeleton::new, MobCategory.MONSTER).sized(0.6F, 1.99F).clientTrackingRange(8)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<RoyalCreeper>> ROYAL_CREEPER = register(
      "royal_creeper", Builder.of(RoyalCreeper::new, MobCategory.MONSTER).sized(0.6F, 1.7F).clientTrackingRange(8)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<RoyalEnderman>> ROYAL_ENDERMAN = register(
      "royal_enderman", Builder.of(RoyalEnderman::new, MobCategory.MONSTER).sized(0.6F, 2.9F).clientTrackingRange(8)
   );

   private static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> register(String p_20635_, Builder<T> p_20636_) {
      return ENTITY_TYPE.register(p_20635_, () -> p_20636_.build(RoyalVariations.location(p_20635_).toString()));
   }
}
