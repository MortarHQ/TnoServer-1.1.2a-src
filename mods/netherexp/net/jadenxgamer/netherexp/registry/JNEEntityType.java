package net.jadenxgamer.netherexp.registry;

import java.util.function.Supplier;
import net.jadenxgamer.netherexp.core.entity.AntidoteEffectCloud;
import net.jadenxgamer.netherexp.core.entity.Apparition;
import net.jadenxgamer.netherexp.core.entity.Banshee;
import net.jadenxgamer.netherexp.core.entity.BlackIcicle;
import net.jadenxgamer.netherexp.core.entity.Carcass;
import net.jadenxgamer.netherexp.core.entity.EctoSlab;
import net.jadenxgamer.netherexp.core.entity.ExorcismMob;
import net.jadenxgamer.netherexp.core.entity.PhasmoArrow;
import net.jadenxgamer.netherexp.core.entity.PhasmoPellet;
import net.jadenxgamer.netherexp.core.entity.PortalGlow;
import net.jadenxgamer.netherexp.core.entity.ShotgunPellet;
import net.jadenxgamer.netherexp.core.entity.SlugPellet;
import net.jadenxgamer.netherexp.core.entity.Stampede;
import net.jadenxgamer.netherexp.core.entity.ThrownAntidote;
import net.jadenxgamer.netherexp.core.entity.Vessel;
import net.jadenxgamer.netherexp.core.entity.WillOWisp;
import net.jadenxgamer.netherexp.core.entity.Wisp;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.EntityType.Builder;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent.Operation;
import net.neoforged.neoforge.registries.DeferredRegister;

public class JNEEntityType {
   public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, "netherexp");
   public static final Supplier<EntityType<Wisp>> WISP = ENTITY_TYPES.register(
      "wisp", () -> Builder.of(Wisp::new, MobCategory.AMBIENT).sized(0.5F, 0.6F).fireImmune().build("wisp")
   );
   public static final Supplier<EntityType<Apparition>> APPARITION = ENTITY_TYPES.register(
      "apparition", () -> Builder.of(Apparition::new, MobCategory.MONSTER).sized(1.0F, 2.2F).fireImmune().build("apparition")
   );
   public static final Supplier<EntityType<Vessel>> VESSEL = ENTITY_TYPES.register(
      "vessel", () -> Builder.of(Vessel::new, MobCategory.MONSTER).sized(0.8F, 2.6F).fireImmune().build("vessel")
   );
   public static final Supplier<EntityType<EctoSlab>> ECTO_SLAB = ENTITY_TYPES.register(
      "ecto_slab", () -> Builder.of(EctoSlab::new, MobCategory.MONSTER).sized(1.375F, 0.75F).fireImmune().build("ecto_slab")
   );
   public static final Supplier<EntityType<Banshee>> BANSHEE = ENTITY_TYPES.register(
      "banshee", () -> Builder.of(Banshee::new, MobCategory.MONSTER).sized(1.25F, 2.375F).fireImmune().build("banshee")
   );
   public static final Supplier<EntityType<Stampede>> STAMPEDE = ENTITY_TYPES.register(
      "stampede", () -> Builder.of(Stampede::new, MobCategory.MONSTER).sized(1.25F, 4.0F).fireImmune().build("stampede")
   );
   public static final Supplier<EntityType<Carcass>> CARCASS = ENTITY_TYPES.register(
      "carcass", () -> Builder.of(Carcass::new, MobCategory.MISC).sized(1.4F, 1.3F).fireImmune().build("carcass")
   );
   public static final Supplier<EntityType<ShotgunPellet>> SHOTGUN_PELLET = ENTITY_TYPES.register(
      "shotgun_pellet", () -> Builder.of(ShotgunPellet::new, MobCategory.MISC).sized(0.5F, 0.5F).build("shotgun_pellet")
   );
   public static final Supplier<EntityType<SlugPellet>> SLUG_PELLET = ENTITY_TYPES.register(
      "slug_pellet", () -> Builder.of(SlugPellet::new, MobCategory.MISC).sized(0.5F, 0.5F).build("slug_pellet")
   );
   public static final Supplier<EntityType<PhasmoPellet>> PHASMO_PELLET = ENTITY_TYPES.register(
      "phasmo_pellet", () -> Builder.of(PhasmoPellet::new, MobCategory.MISC).sized(0.5F, 0.5F).build("phasmo_pellet")
   );
   public static final Supplier<EntityType<PhasmoArrow>> PHASMO_ARROW = ENTITY_TYPES.register(
      "phasmo_arrow", () -> Builder.of(PhasmoArrow::new, MobCategory.MISC).sized(0.5F, 0.5F).build("phasmo_arrow")
   );
   public static final Supplier<EntityType<BlackIcicle>> BLACK_ICICLE = ENTITY_TYPES.register(
      "black_icicle", () -> Builder.of(BlackIcicle::new, MobCategory.MISC).sized(0.5F, 0.5F).build("black_icicle")
   );
   public static final Supplier<EntityType<WillOWisp>> WILL_O_WISP = ENTITY_TYPES.register(
      "will_o_wisp", () -> Builder.of(WillOWisp::new, MobCategory.MISC).sized(0.5F, 0.5F).build("will_o_wisp")
   );
   public static final Supplier<EntityType<PortalGlow>> PORTAL_GLOW = ENTITY_TYPES.register(
      "portal_glow", () -> Builder.of(PortalGlow::new, MobCategory.MISC).sized(1.0F, 1.0F).noSummon().build("portal_glow")
   );
   public static final Supplier<EntityType<ThrownAntidote>> ANTIDOTE = ENTITY_TYPES.register(
      "antidote", () -> Builder.of(ThrownAntidote::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).build("antidote")
   );
   public static final Supplier<EntityType<AntidoteEffectCloud>> ANTIDOTE_EFFECT_CLOUD = ENTITY_TYPES.register(
      "antidote_effect_cloud",
      () -> Builder.of(AntidoteEffectCloud::new, MobCategory.MISC)
         .fireImmune()
         .sized(6.0F, 0.5F)
         .clientTrackingRange(10)
         .updateInterval(Integer.MAX_VALUE)
         .build("antidote_effect_cloud")
   );

   public static void init(IEventBus eventBus) {
      ENTITY_TYPES.register(eventBus);
   }

   public static void registerAttributes(EntityAttributeCreationEvent event) {
      event.put(WISP.get(), Wisp.createAttributes().build());
      event.put(APPARITION.get(), Apparition.createAttributes().build());
      event.put(VESSEL.get(), Vessel.createAttributes().build());
      event.put(ECTO_SLAB.get(), EctoSlab.createAttributes().build());
      event.put(STAMPEDE.get(), Stampede.createAttributes().build());
      event.put(BANSHEE.get(), Banshee.createAttributes().build());
      event.put(CARCASS.get(), Carcass.createAttributes().build());
   }

   public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
      event.register(APPARITION.get(), SpawnPlacementTypes.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, ExorcismMob::checkSpawnRules, Operation.REPLACE);
      event.register(VESSEL.get(), SpawnPlacementTypes.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, ExorcismMob::checkSpawnRules, Operation.REPLACE);
      event.register(ECTO_SLAB.get(), SpawnPlacementTypes.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, EctoSlab::checkSpawnRules, Operation.REPLACE);
      event.register(BANSHEE.get(), SpawnPlacementTypes.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, ExorcismMob::checkSpawnRules, Operation.REPLACE);
      event.register(STAMPEDE.get(), SpawnPlacementTypes.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, ExorcismMob::checkSpawnRules, Operation.REPLACE);
   }
}
