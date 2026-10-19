package net.unusual.block_factorys_bosses.init;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType.Builder;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.unusual.block_factorys_bosses.entity.FireAreaEntity;
import net.unusual.block_factorys_bosses.entity.PoisonAreaEntity;
import net.unusual.block_factorys_bosses.entity.RiftEntity;
import net.unusual.block_factorys_bosses.entity.SandColumnEntity;
import net.unusual.block_factorys_bosses.entity.boss.dragon.boss.InfernalDragonEntity;
import net.unusual.block_factorys_bosses.entity.boss.dragon.guardians.DragonGuardSwordEntity;
import net.unusual.block_factorys_bosses.entity.boss.dragon.guardians.FlamingSkeletonGuardFireballEntity;
import net.unusual.block_factorys_bosses.entity.boss.dragon.guardians.FlamingSkeletonGuardSwordEntity;
import net.unusual.block_factorys_bosses.entity.boss.knight.KnightMarkEntity;
import net.unusual.block_factorys_bosses.entity.boss.knight.UnderworldKnightEntity;
import net.unusual.block_factorys_bosses.entity.boss.kraken.KrakenCinematicEntity;
import net.unusual.block_factorys_bosses.entity.boss.kraken.KrakenEntity;
import net.unusual.block_factorys_bosses.entity.boss.kraken.KrakenTentacleEntity;
import net.unusual.block_factorys_bosses.entity.boss.kraken.summons.CrossbowPirateEntity;
import net.unusual.block_factorys_bosses.entity.boss.kraken.summons.GhostTentacleEntity;
import net.unusual.block_factorys_bosses.entity.boss.kraken.summons.PirateCaptainEntity;
import net.unusual.block_factorys_bosses.entity.boss.kraken.summons.PirateRookEntity;
import net.unusual.block_factorys_bosses.entity.boss.sandworm.SandwormEntity;
import net.unusual.block_factorys_bosses.entity.boss.yeti.FrozenSkeletonEntity;
import net.unusual.block_factorys_bosses.entity.boss.yeti.GlacialShoveEntity;
import net.unusual.block_factorys_bosses.entity.boss.yeti.IceSpikeClusterEntity;
import net.unusual.block_factorys_bosses.entity.boss.yeti.IceSpikeEntity;
import net.unusual.block_factorys_bosses.entity.boss.yeti.IceSpikeProjectileEntity;
import net.unusual.block_factorys_bosses.entity.boss.yeti.YetiEntity;
import net.unusual.block_factorys_bosses.entity.decoration.AnchorEntity;
import net.unusual.block_factorys_bosses.entity.decoration.CageEntity;
import net.unusual.block_factorys_bosses.entity.decoration.CannonEntity;
import net.unusual.block_factorys_bosses.entity.decoration.CratePileEntity;
import net.unusual.block_factorys_bosses.entity.decoration.PileOfBonesEntity;
import net.unusual.block_factorys_bosses.entity.monster.SoulKnightWitherSkeletonEntity;
import net.unusual.block_factorys_bosses.entity.monster.SoulSkeletonEntity;
import net.unusual.block_factorys_bosses.entity.projectile.BigRiftProjectileEntity;
import net.unusual.block_factorys_bosses.entity.projectile.BlazingFireBallEntity;
import net.unusual.block_factorys_bosses.entity.projectile.CannonballEntity;
import net.unusual.block_factorys_bosses.entity.projectile.PoisonSpitPrEntity;
import net.unusual.block_factorys_bosses.entity.projectile.RiftProjectileEntity;
import net.unusual.block_factorys_bosses.entity.projectile.SoulShockwaveEntity;
import net.unusual.block_factorys_bosses.entity.projectile.SwordWaveEntity;
import net.unusual.block_factorys_bosses.entity.projectile.ThrownCrateEntity;
import net.unusual.block_factorys_bosses.entity.projectile.ThrownKrakenTridentEntity;

@EventBusSubscriber
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BossesRiseEntities {
   public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(Registries.ENTITY_TYPE, "block_factorys_bosses");
   public static final DeferredHolder<EntityType<?>, EntityType<PileOfBonesEntity>> PILE_OF_BONES = register(
      "pile_of_bones",
      Builder.of(PileOfBonesEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .fireImmune()
         .sized(5.0F, 3.0F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<YetiEntity>> YETI = register(
      "yeti",
      Builder.of(YetiEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).sized(3.2F, 4.95F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<GlacialShoveEntity>> GLACIAL_SHOVE = register(
      "glacial_shove",
      Builder.of(GlacialShoveEntity::new, MobCategory.MISC)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .fireImmune()
         .sized(3.0F, 2.5F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<IceSpikeEntity>> ICE_SPIKE = register(
      "ice_spike",
      Builder.of(IceSpikeEntity::new, MobCategory.MISC)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .fireImmune()
         .sized(1.0F, 1.0F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<IceSpikeClusterEntity>> ICE_SPIKE_CLUSTER = register(
      "ice_spike_cluster",
      Builder.of(IceSpikeClusterEntity::new, MobCategory.MISC)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .fireImmune()
         .sized(2.0F, 2.0F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<FrozenSkeletonEntity>> FROZEN_SKELETON = register(
      "frozen_skeleton",
      Builder.of(FrozenSkeletonEntity::new, MobCategory.MISC)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .fireImmune()
         .sized(0.6F, 1.99F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<CageEntity>> CAGE = register(
      "cage",
      Builder.of((type, world) -> new CageEntity(type, world, BossesRiseItems.CAGE_ITEM), MobCategory.MISC)
         .sized(1.7F, 3.2F)
         .eyeHeight(0.0F)
         .clientTrackingRange(10)
         .fireImmune()
   );
   public static final DeferredHolder<EntityType<?>, EntityType<CageEntity>> CAGE_SKELLY = register(
      "cage_skelly",
      Builder.of((type, world) -> new CageEntity(type, world, BossesRiseItems.CAGE_SKELLY_ITEM), MobCategory.MISC)
         .sized(1.7F, 3.2F)
         .eyeHeight(0.0F)
         .clientTrackingRange(10)
         .fireImmune()
   );
   public static final DeferredHolder<EntityType<?>, EntityType<CageEntity>> BIG_CAGE = register(
      "big_cage",
      Builder.of((type, world) -> new CageEntity(type, world, BossesRiseItems.BIG_CAGE_ITEM), MobCategory.MISC)
         .sized(3.0F, 6.5F)
         .eyeHeight(0.0F)
         .clientTrackingRange(10)
         .fireImmune()
   );
   public static final DeferredHolder<EntityType<?>, EntityType<CageEntity>> BIG_CAGE_SKELLY = register(
      "big_cage_skelly",
      Builder.of((type, world) -> new CageEntity(type, world, BossesRiseItems.BIG_CAGE_SKELLY_ITEM), MobCategory.MISC)
         .sized(3.0F, 6.5F)
         .eyeHeight(0.0F)
         .clientTrackingRange(10)
         .fireImmune()
   );
   public static final DeferredHolder<EntityType<?>, EntityType<CannonEntity>> KRAKEN_CANNON = register(
      "kraken_cannon",
      Builder.of((type, world) -> new CannonEntity(type, world, BossesRiseItems.KRAKEN_CANNON_ITEM), MobCategory.MISC)
         .sized(3.0F, 4.0F)
         .eyeHeight(2.25F)
         .clientTrackingRange(10)
         .fireImmune()
   );
   public static final DeferredHolder<EntityType<?>, EntityType<CannonballEntity>> CANNONBALL = register(
      "cannonball",
      Builder.of(CannonballEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.2F, 0.2F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<ThrownCrateEntity>> THROWN_CRATE = register(
      "thrown_crate",
      Builder.of(ThrownCrateEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.2F, 0.2F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<IceSpikeProjectileEntity>> ICE_SPIKE_PR = register(
      "ice_spike_pr",
      Builder.of(IceSpikeProjectileEntity::new, MobCategory.MISC)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(1)
         .sized(0.5F, 0.5F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<InfernalDragonEntity>> INFERNAL_DRAGON = register(
      "infernal_dragon",
      Builder.of(InfernalDragonEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(200)
         .setUpdateInterval(3)
         .fireImmune()
         .sized(5.0F, 4.0F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<BlazingFireBallEntity>> BLAZING_FIRE_BALL = register(
      "blazing_fire_ball",
      Builder.of(BlazingFireBallEntity::new, MobCategory.MISC)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(1)
         .sized(0.5F, 0.5F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<FireAreaEntity>> FIRE_AREA = register(
      "fire_area",
      Builder.of(FireAreaEntity::new, MobCategory.MISC)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .fireImmune()
         .sized(3.5F, 0.3F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<UnderworldKnightEntity>> UNDERWORLD_KNIGHT = register(
      "underworld_knight",
      Builder.of(UnderworldKnightEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .fireImmune()
         .sized(3.0F, 5.3F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<DragonGuardSwordEntity>> DRAGON_GUARD_SWORD = register(
      "dragon_guard_sword",
      Builder.of(DragonGuardSwordEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .fireImmune()
         .sized(0.6F, 2.5F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<FlamingSkeletonGuardSwordEntity>> FLAMING_SKELETON_GUARD_SWORD = register(
      "flaming_skeleton_guard_sword",
      Builder.of(FlamingSkeletonGuardSwordEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .fireImmune()
         .sized(0.6F, 2.3F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<FlamingSkeletonGuardFireballEntity>> FLAMING_SKELETON_GUARD_FIREBALL = register(
      "flaming_skeleton_guard_fireball",
      Builder.of(FlamingSkeletonGuardFireballEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .fireImmune()
         .sized(0.6F, 2.3F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<SoulSkeletonEntity>> SOUL_SKELETON = register(
      "soul_skeleton",
      Builder.of(SoulSkeletonEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .fireImmune()
         .sized(0.6F, 2.0F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<SoulKnightWitherSkeletonEntity>> SOUL_KNIGHT_WITHER_SKELETON = register(
      "soul_knight_wither_skeleton",
      Builder.of(SoulKnightWitherSkeletonEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .fireImmune()
         .sized(0.6F, 2.5F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<SoulShockwaveEntity>> SOUL_SHOCKWAVE = register(
      "soul_shockwave",
      Builder.of(SoulShockwaveEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(1.0F, 1.0F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<SandColumnEntity>> SAND_COLUMN = register(
      "sand_column",
      Builder.of(SandColumnEntity::new, MobCategory.MISC)
         .setShouldReceiveVelocityUpdates(false)
         .setTrackingRange(96)
         .setUpdateInterval(3)
         .fireImmune()
         .sized(1.8F, 3.0F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<SandwormEntity>> SANDWORM = register(
      "sandworm",
      Builder.of(SandwormEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(96).setUpdateInterval(1).sized(0.8F, 0.9F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<PoisonAreaEntity>> POISON_AREA = register(
      "poison_area",
      Builder.of(PoisonAreaEntity::new, MobCategory.MISC)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .fireImmune()
         .sized(2.7F, 0.3F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<PoisonSpitPrEntity>> POISON_SPIT_PR = register(
      "poison_spit_pr",
      Builder.of(PoisonSpitPrEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5F, 0.5F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<SwordWaveEntity>> SWORD_WAVE = register(
      "sword_wave",
      Builder.of(SwordWaveEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(4.0F, 0.2F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<RiftEntity>> RIFT = register(
      "rift",
      Builder.of(RiftEntity::new, MobCategory.MISC)
         .setShouldReceiveVelocityUpdates(false)
         .setTrackingRange(64)
         .setUpdateInterval(1)
         .fireImmune()
         .sized(1.0F, 1.0F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<RiftProjectileEntity>> RIFT_PROJECTILE = register(
      "rift_projectile",
      Builder.of(RiftProjectileEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.2F, 0.2F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<BigRiftProjectileEntity>> BIG_RIFT_PROJECTILE = register(
      "big_rift_projectile",
      Builder.of(BigRiftProjectileEntity::new, MobCategory.MISC)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(1)
         .sized(0.2F, 0.2F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<KnightMarkEntity>> KNIGHT_MARK = register(
      "knight_mark",
      Builder.of(KnightMarkEntity::new, MobCategory.MISC)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(1)
         .fireImmune()
         .sized(1.0F, 1.0F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<KrakenEntity>> KRAKEN = register(
      "kraken",
      Builder.of(KrakenEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(1)
         .fireImmune()
         .sized(10.0F, 11.0F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<KrakenCinematicEntity>> CINEMATIC_KRAKEN = register(
      "cinematic_kraken",
      Builder.of(KrakenCinematicEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(1)
         .fireImmune()
         .sized(10.0F, 11.0F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<KrakenTentacleEntity>> KRAKEN_TENTACLE = register(
      "kraken_tentacle",
      Builder.of(KrakenTentacleEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(1)
         .fireImmune()
         .sized(1.0F, 1.0F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<ThrownKrakenTridentEntity>> KRAKEN_TRIDENT = register(
      "kraken_trident",
      Builder.of(ThrownKrakenTridentEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).eyeHeight(0.13F).clientTrackingRange(4).updateInterval(20)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<PirateCaptainEntity>> PIRATE_CAPTAIN = register(
      "pirate_captain",
      Builder.of(PirateCaptainEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .sized(0.6F, 2.3F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<PirateRookEntity>> PIRATE_ROOK = register(
      "pirate_rook",
      Builder.of(PirateRookEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).sized(0.6F, 2.3F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<CrossbowPirateEntity>> CROSSBOW_PIRATE = register(
      "crossbow_pirate",
      Builder.of(CrossbowPirateEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .sized(0.6F, 2.3F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<GhostTentacleEntity>> GHOST_TENTACLE = register(
      "ghost_tentacle",
      Builder.of(GhostTentacleEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .sized(0.6F, 2.3F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<AnchorEntity>> ANCHOR = register(
      "anchor",
      Builder.of((type, world) -> new AnchorEntity(type, world, BossesRiseItems.ANCHOR_ITEM), MobCategory.MISC)
         .sized(1.0F, 3.5F)
         .eyeHeight(0.0F)
         .clientTrackingRange(10)
         .fireImmune()
   );
   public static final DeferredHolder<EntityType<?>, EntityType<CratePileEntity>> CRATE_PILE = register(
      "crate_pile",
      Builder.of((type, world) -> new CratePileEntity(type, world, BossesRiseItems.CRATE), MobCategory.MISC)
         .sized(1.0F, 1.0F)
         .eyeHeight(0.0F)
         .clientTrackingRange(10)
   );

   private static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> register(String registryName, Builder<T> entityTypeBuilder) {
      return REGISTRY.register(registryName, () -> entityTypeBuilder.build(registryName));
   }

   @SubscribeEvent
   public static void init(RegisterSpawnPlacementsEvent event) {
      PileOfBonesEntity.init(event);
      YetiEntity.init(event);
      InfernalDragonEntity.init(event);
      UnderworldKnightEntity.init(event);
      DragonGuardSwordEntity.init(event);
      FlamingSkeletonGuardSwordEntity.init(event);
      FlamingSkeletonGuardFireballEntity.init(event);
      SoulSkeletonEntity.init(event);
      SoulKnightWitherSkeletonEntity.init(event);
      SandwormEntity.init(event);
      PirateCaptainEntity.init(event);
   }

   @SubscribeEvent
   public static void registerAttributes(EntityAttributeCreationEvent event) {
      event.put((EntityType)PILE_OF_BONES.get(), PileOfBonesEntity.createAttributes().build());
      event.put((EntityType)YETI.get(), YetiEntity.createAttributes().build());
      event.put((EntityType)INFERNAL_DRAGON.get(), InfernalDragonEntity.createAttributes().build());
      event.put((EntityType)UNDERWORLD_KNIGHT.get(), UnderworldKnightEntity.createAttributes().build());
      event.put((EntityType)DRAGON_GUARD_SWORD.get(), DragonGuardSwordEntity.createAttributes().build());
      event.put((EntityType)FLAMING_SKELETON_GUARD_SWORD.get(), FlamingSkeletonGuardSwordEntity.createAttributes().build());
      event.put((EntityType)FLAMING_SKELETON_GUARD_FIREBALL.get(), FlamingSkeletonGuardFireballEntity.createAttributes().build());
      event.put((EntityType)SOUL_SKELETON.get(), SoulSkeletonEntity.createAttributes().build());
      event.put((EntityType)SOUL_KNIGHT_WITHER_SKELETON.get(), SoulKnightWitherSkeletonEntity.createAttributes().build());
      event.put((EntityType)SANDWORM.get(), SandwormEntity.createAttributes().build());
      event.put((EntityType)KRAKEN.get(), KrakenEntity.createAttributes().build());
      event.put((EntityType)CINEMATIC_KRAKEN.get(), KrakenCinematicEntity.createMonsterAttributes().build());
      event.put((EntityType)KRAKEN_TENTACLE.get(), KrakenTentacleEntity.createAttributes().build());
      event.put((EntityType)PIRATE_CAPTAIN.get(), PirateCaptainEntity.createAttributes().build());
      event.put((EntityType)PIRATE_ROOK.get(), PirateRookEntity.createAttributes().build());
      event.put((EntityType)CROSSBOW_PIRATE.get(), CrossbowPirateEntity.createAttributes().build());
      event.put((EntityType)GHOST_TENTACLE.get(), GhostTentacleEntity.createAttributes().build());
   }
}
