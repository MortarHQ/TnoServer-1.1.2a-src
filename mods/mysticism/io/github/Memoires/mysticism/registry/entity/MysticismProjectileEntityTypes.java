package io.github.Memoires.mysticism.registry.entity;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.Memoires.mysticism.entity.magic.spike.DarknessSpikeEntity;
import io.github.Memoires.mysticism.entity.projectile.BlueFlareProjectile;
import io.github.Memoires.mysticism.entity.projectile.DraconicBreathProjectile;
import io.github.Memoires.mysticism.entity.projectile.DraconicMeteorProjectile;
import io.github.Memoires.mysticism.entity.projectile.DraconicPulseProjectile;
import io.github.Memoires.mysticism.entity.projectile.SentryBulletProjectile;
import io.github.Memoires.mysticism.entity.projectile.SentryMissileProjectile;
import io.github.Memoires.mysticism.entity.projectile.ShadowBallProjectile;
import io.github.Memoires.mysticism.entity.projectile.StasisShotProjectile;
import io.github.Memoires.mysticism.entity.projectile.TailedBeastBombProjectile;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType.Builder;

public class MysticismProjectileEntityTypes {
   private static final DeferredRegister<EntityType<?>> PROJECTILES = DeferredRegister.create("mysticism", Registries.ENTITY_TYPE);
   public static final RegistrySupplier<EntityType<StasisShotProjectile>> STASIS_SHOT = PROJECTILES.register(
      "stasis_shot",
      () -> Builder.of(StasisShotProjectile::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(64).updateInterval(1).build("stasis_shot")
   );
   public static final RegistrySupplier<EntityType<BlueFlareProjectile>> BLUE_FLARE_PROJECTILE = PROJECTILES.register(
      "blue_flare_projectile",
      () -> Builder.of(BlueFlareProjectile::new, MobCategory.MISC).sized(1.0F, 1.0F).clientTrackingRange(32).build("blue_flare_projectile")
   );
   public static final RegistrySupplier<EntityType<DraconicPulseProjectile>> DRACONIC_PULSE_PROJECTILE = PROJECTILES.register(
      "draconic_pulse", () -> Builder.of(DraconicPulseProjectile::new, MobCategory.MISC).sized(1.0F, 1.0F).clientTrackingRange(64).build("draconic_pulse")
   );
   public static final RegistrySupplier<EntityType<DraconicBreathProjectile>> DRACONIC_BREATH_PROJECTILE = PROJECTILES.register(
      "draconic_breath", () -> Builder.of(DraconicBreathProjectile::new, MobCategory.MISC).sized(1.0F, 1.0F).clientTrackingRange(64).build("draconic_breath")
   );
   public static final RegistrySupplier<EntityType<DraconicMeteorProjectile>> DRACONIC_METEOR_PROJECTILE = PROJECTILES.register(
      "draconic_meteor", () -> Builder.of(DraconicMeteorProjectile::new, MobCategory.MISC).sized(1.0F, 1.0F).clientTrackingRange(64).build("draconic_meteor")
   );
   public static final RegistrySupplier<EntityType<SentryBulletProjectile>> SENTRY_BULLET = PROJECTILES.register(
      "sentry_bullet", () -> Builder.of(SentryBulletProjectile::new, MobCategory.MISC).sized(1.0F, 1.0F).clientTrackingRange(64).build("sentry_bullet")
   );
   public static final RegistrySupplier<EntityType<SentryMissileProjectile>> SENTRY_MISSILE = PROJECTILES.register(
      "sentry_missile", () -> Builder.of(SentryMissileProjectile::new, MobCategory.MISC).sized(1.0F, 1.0F).clientTrackingRange(64).build("sentry_missile")
   );
   public static final RegistrySupplier<EntityType<ShadowBallProjectile>> SHADOW_BALL = PROJECTILES.register(
      "shadow_ball", () -> Builder.of(ShadowBallProjectile::new, MobCategory.MISC).sized(1.0F, 1.0F).clientTrackingRange(64).build("shadow_ball")
   );
   public static final RegistrySupplier<EntityType<TailedBeastBombProjectile>> TAILED_BEAST_BOMB = PROJECTILES.register(
      "tailed_beast_bomb",
      () -> Builder.of(TailedBeastBombProjectile::new, MobCategory.MISC).sized(1.0F, 1.0F).clientTrackingRange(64).build("tailed_beast_bomb")
   );
   public static final RegistrySupplier<EntityType<DarknessSpikeEntity>> DARKNESS_SPIKE = PROJECTILES.register(
      "darkness_spike", () -> Builder.of(DarknessSpikeEntity::new, MobCategory.MISC).sized(1.0F, 1.0F).clientTrackingRange(64).build("darkness_spike")
   );

   public static void init() {
      PROJECTILES.register();
   }
}
