package net.mcreator.rpgstylemorebosses.init;

import net.mcreator.rpgstylemorebosses.entity.BeaconActiveEntity;
import net.mcreator.rpgstylemorebosses.entity.BeaconEntity;
import net.mcreator.rpgstylemorebosses.entity.BlockBarrierEntity;
import net.mcreator.rpgstylemorebosses.entity.BossCounterEntity;
import net.mcreator.rpgstylemorebosses.entity.ExoframeBeamEntity;
import net.mcreator.rpgstylemorebosses.entity.ExoframeTickEntity;
import net.mcreator.rpgstylemorebosses.entity.KriStrarzEntity;
import net.mcreator.rpgstylemorebosses.entity.RunicSpiderEntity;
import net.mcreator.rpgstylemorebosses.entity.TheExoframeEntity;
import net.mcreator.rpgstylemorebosses.entity.TheExoframeSpawnEntity;
import net.mcreator.rpgstylemorebosses.entity.TheExoframeVFX2Entity;
import net.mcreator.rpgstylemorebosses.entity.TheExoframeVFXEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType.Builder;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@EventBusSubscriber(bus = Bus.MOD)
public class RpgStyleMoreBossesModEntities {
   public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(Registries.ENTITY_TYPE, "rpg_style_more_bosses");
   public static final DeferredHolder<EntityType<?>, EntityType<RunicSpiderEntity>> RUNIC_SPIDER = register(
      "runic_spider",
      Builder.of(RunicSpiderEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .fireImmune()
         .sized(0.6F, 1.8F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<KriStrarzEntity>> KRI_STRARZ = register(
      "kri_strarz",
      Builder.of(KriStrarzEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .fireImmune()
         .sized(0.6F, 1.8F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<TheExoframeEntity>> THE_EXOFRAME = register(
      "the_exoframe",
      Builder.of(TheExoframeEntity::new, MobCategory.CREATURE)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(280)
         .setUpdateInterval(3)
         .sized(0.8F, 2.0F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<TheExoframeVFXEntity>> THE_EXOFRAME_VFX = register(
      "the_exoframe_vfx",
      Builder.of(TheExoframeVFXEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(250)
         .setUpdateInterval(3)
         .fireImmune()
         .sized(1.0F, 0.3F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<TheExoframeVFX2Entity>> THE_EXOFRAME_VFX_2 = register(
      "the_exoframe_vfx_2",
      Builder.of(TheExoframeVFX2Entity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(250)
         .setUpdateInterval(3)
         .fireImmune()
         .sized(1.0F, 0.3F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<BeaconEntity>> BEACON = register(
      "beacon",
      Builder.of(BeaconEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(250)
         .setUpdateInterval(3)
         .fireImmune()
         .sized(1.0F, 2.0F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<BeaconActiveEntity>> BEACON_ACTIVE = register(
      "beacon_active",
      Builder.of(BeaconActiveEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(250)
         .setUpdateInterval(3)
         .fireImmune()
         .sized(1.0F, 10.0F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<BossCounterEntity>> BOSS_COUNTER = register(
      "boss_counter",
      Builder.of(BossCounterEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(250)
         .setUpdateInterval(3)
         .fireImmune()
         .sized(1.0F, 0.2F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<BlockBarrierEntity>> BLOCK_BARRIER = register(
      "block_barrier",
      Builder.of(BlockBarrierEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(250)
         .setUpdateInterval(3)
         .fireImmune()
         .sized(4.0F, 3.0F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<TheExoframeSpawnEntity>> THE_EXOFRAME_SPAWN = register(
      "the_exoframe_spawn",
      Builder.of(TheExoframeSpawnEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(280)
         .setUpdateInterval(3)
         .fireImmune()
         .sized(0.8F, 2.0F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<ExoframeBeamEntity>> EXOFRAME_BEAM = register(
      "exoframe_beam",
      Builder.of(ExoframeBeamEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(250)
         .setUpdateInterval(3)
         .fireImmune()
         .sized(1.0F, 0.3F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<ExoframeTickEntity>> EXOFRAME_TICK = register(
      "exoframe_tick",
      Builder.of(ExoframeTickEntity::new, MobCategory.CREATURE)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(280)
         .setUpdateInterval(3)
         .sized(0.5F, 1.0F)
   );

   private static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> register(String registryname, Builder<T> entityTypeBuilder) {
      return REGISTRY.register(registryname, () -> entityTypeBuilder.build(registryname));
   }

   @SubscribeEvent
   public static void init(RegisterSpawnPlacementsEvent event) {
      RunicSpiderEntity.init(event);
      KriStrarzEntity.init(event);
      TheExoframeEntity.init(event);
      TheExoframeVFXEntity.init(event);
      TheExoframeVFX2Entity.init(event);
      BeaconEntity.init(event);
      BeaconActiveEntity.init(event);
      BossCounterEntity.init(event);
      BlockBarrierEntity.init(event);
      TheExoframeSpawnEntity.init(event);
      ExoframeBeamEntity.init(event);
      ExoframeTickEntity.init(event);
   }

   @SubscribeEvent
   public static void registerAttributes(EntityAttributeCreationEvent event) {
      event.put((EntityType)RUNIC_SPIDER.get(), RunicSpiderEntity.createAttributes().build());
      event.put((EntityType)KRI_STRARZ.get(), KriStrarzEntity.createAttributes().build());
      event.put((EntityType)THE_EXOFRAME.get(), TheExoframeEntity.createAttributes().build());
      event.put((EntityType)THE_EXOFRAME_VFX.get(), TheExoframeVFXEntity.createAttributes().build());
      event.put((EntityType)THE_EXOFRAME_VFX_2.get(), TheExoframeVFX2Entity.createAttributes().build());
      event.put((EntityType)BEACON.get(), BeaconEntity.createAttributes().build());
      event.put((EntityType)BEACON_ACTIVE.get(), BeaconActiveEntity.createAttributes().build());
      event.put((EntityType)BOSS_COUNTER.get(), BossCounterEntity.createAttributes().build());
      event.put((EntityType)BLOCK_BARRIER.get(), BlockBarrierEntity.createAttributes().build());
      event.put((EntityType)THE_EXOFRAME_SPAWN.get(), TheExoframeSpawnEntity.createAttributes().build());
      event.put((EntityType)EXOFRAME_BEAM.get(), ExoframeBeamEntity.createAttributes().build());
      event.put((EntityType)EXOFRAME_TICK.get(), ExoframeTickEntity.createAttributes().build());
   }
}
