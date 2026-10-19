package io.github.Memoires.mysticism.registry.entity;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.Memoires.mysticism.entity.magic.field.BlueFlare;
import io.github.Memoires.mysticism.entity.magic.misc.BlackHoleEntity;
import io.github.Memoires.mysticism.entity.skill.BubbleShieldBarrierEntity;
import io.github.Memoires.mysticism.entity.skill.DispenserEntity;
import io.github.Memoires.mysticism.entity.skill.EnkiduEntity;
import io.github.Memoires.mysticism.entity.skill.EternalDomainEntity;
import io.github.Memoires.mysticism.entity.skill.GateEntity;
import io.github.Memoires.mysticism.entity.skill.KamuiPortalEntity;
import io.github.Memoires.mysticism.entity.skill.ProviderHaloEntity;
import io.github.Memoires.mysticism.entity.skill.RelapseCloneEntity;
import io.github.Memoires.mysticism.entity.skill.SentryEntity;
import io.github.Memoires.mysticism.entity.skill.SolidificationShieldEntity;
import io.github.Memoires.mysticism.entity.skill.UncontrolledJinchurikiEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType.Builder;

public class MysticismSkillEntityTypes {
   private static final DeferredRegister<EntityType<?>> SKILL_ENTITIES = DeferredRegister.create("mysticism", Registries.ENTITY_TYPE);
   public static final RegistrySupplier<EntityType<ProviderHaloEntity>> PROVIDER_HALO = SKILL_ENTITIES.register(
      "provider_halo",
      () -> Builder.of(ProviderHaloEntity::new, MobCategory.MISC).sized(0.5F, 7.1F).clientTrackingRange(64).updateInterval(1).build("provider_halo")
   );
   public static final RegistrySupplier<EntityType<SolidificationShieldEntity>> SOLIDIFICATION_SHIELD = SKILL_ENTITIES.register(
      "solidification_shield",
      () -> Builder.of(SolidificationShieldEntity::new, MobCategory.MISC)
         .sized(0.01F, 0.01F)
         .clientTrackingRange(64)
         .updateInterval(1)
         .build("solidification_shield")
   );
   public static final RegistrySupplier<EntityType<EternalDomainEntity>> ETERNAL_DOMAIN = SKILL_ENTITIES.register(
      "eternal_domain",
      () -> Builder.of(EternalDomainEntity::new, MobCategory.MISC).sized(0.01F, 0.01F).clientTrackingRange(64).updateInterval(1).build("eternal_domain")
   );
   public static final RegistrySupplier<EntityType<BubbleShieldBarrierEntity>> BUBBLE_SHIELD_BARRIER = SKILL_ENTITIES.register(
      "bubble_shield",
      () -> Builder.of(BubbleShieldBarrierEntity::new, MobCategory.MISC).sized(0.01F, 0.01F).clientTrackingRange(64).updateInterval(1).build("bubble_shield")
   );
   public static final RegistrySupplier<EntityType<SentryEntity>> SENTRY = SKILL_ENTITIES.register(
      "sentry", () -> Builder.of(SentryEntity::new, MobCategory.MISC).sized(0.7F, 1.1F).clientTrackingRange(64).updateInterval(1).build("sentry")
   );
   public static final RegistrySupplier<EntityType<DispenserEntity>> DISPENSER = SKILL_ENTITIES.register(
      "dispenser", () -> Builder.of(DispenserEntity::new, MobCategory.MISC).sized(0.7F, 1.1F).clientTrackingRange(64).updateInterval(1).build("dispenser")
   );
   public static final RegistrySupplier<EntityType<BlackHoleEntity>> BLACK_HOLE = SKILL_ENTITIES.register(
      "black_hole", () -> Builder.of(BlackHoleEntity::new, MobCategory.MISC).sized(1.0F, 1.0F).clientTrackingRange(64).updateInterval(1).build("black_hole")
   );
   public static final RegistrySupplier<EntityType<RelapseCloneEntity>> RELAPSE_CLONE = SKILL_ENTITIES.register(
      "relapse_clone",
      () -> Builder.of(RelapseCloneEntity::new, MobCategory.MONSTER)
         .sized(0.6F, 1.8F)
         .eyeHeight(1.62F)
         .build(ResourceLocation.fromNamespaceAndPath("mysticism", "relapse_clone").toString())
   );
   public static final RegistrySupplier<EntityType<UncontrolledJinchurikiEntity>> UNCONTROLLED_JINCHURIKI = SKILL_ENTITIES.register(
      "uncontrolled_jinchuriki",
      () -> Builder.of(UncontrolledJinchurikiEntity::new, MobCategory.MONSTER)
         .sized(0.6F, 1.8F)
         .eyeHeight(1.62F)
         .build(ResourceLocation.fromNamespaceAndPath("mysticism", "uncontrolled_jinchuriki").toString())
   );
   public static final RegistrySupplier<EntityType<EnkiduEntity>> ENKIDU = SKILL_ENTITIES.register(
      "enkidu", () -> Builder.of(EnkiduEntity::new, MobCategory.MISC).sized(1.0F, 1.0F).clientTrackingRange(64).updateInterval(1).build("enkidu")
   );
   public static final RegistrySupplier<EntityType<GateEntity>> GATE = SKILL_ENTITIES.register(
      "gate", () -> Builder.of(GateEntity::new, MobCategory.MISC).sized(1.0F, 1.0F).clientTrackingRange(64).updateInterval(1).build("gate")
   );
   public static final RegistrySupplier<EntityType<KamuiPortalEntity>> KAMUI_PORTAL = SKILL_ENTITIES.register(
      "kamui_portal",
      () -> Builder.of(KamuiPortalEntity::new, MobCategory.MISC)
         .sized(0.1F, 0.1F)
         .clientTrackingRange(64)
         .build(ResourceLocation.fromNamespaceAndPath("mysticism", "kamui_portal").toString())
   );
   public static final RegistrySupplier<EntityType<BlueFlare>> BLUE_FLARE = SKILL_ENTITIES.register(
      "blueflare", () -> Builder.of(BlueFlare::new, MobCategory.MISC).sized(0.1F, 0.1F).clientTrackingRange(64).build("blueflare")
   );

   public static void init() {
      SKILL_ENTITIES.register();
   }
}
