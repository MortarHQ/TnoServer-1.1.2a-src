package com.benji.netherman.init;

import com.benji.netherman.NetherExp;
import com.benji.netherman.common.entity.AzazelEarthquakeEntity;
import com.benji.netherman.common.entity.AzazelEntity;
import com.benji.netherman.common.entity.AzazelGuideBookEntity;
import com.benji.netherman.common.entity.AzazelHumanEntity;
import com.benji.netherman.common.entity.AzazelSpikeEntity;
import com.benji.netherman.common.entity.AzazelSpikesProjectileEntity;
import com.benji.netherman.common.entity.AzazelSplashEntity;
import com.benji.netherman.common.entity.BelieverEntity;
import com.benji.netherman.common.entity.BelieverVillagerEntity;
import com.benji.netherman.common.entity.BellGuardianEntity;
import com.benji.netherman.common.entity.BlacksmithEntity;
import com.benji.netherman.common.entity.CrimsonArrowEntity;
import com.benji.netherman.common.entity.DoctorEntity;
import com.benji.netherman.common.entity.GhastlyEntity;
import com.benji.netherman.common.entity.GildedGolemEntity;
import com.benji.netherman.common.entity.GuardianEntity;
import com.benji.netherman.common.entity.LaserEntity;
import com.benji.netherman.common.entity.ManipulatorEntity;
import com.benji.netherman.common.entity.PiglinPrisonerEntity;
import com.benji.netherman.common.entity.StatueBossunitEntity;
import com.benji.netherman.common.entity.StatueEntity;
import com.benji.netherman.common.entity.TotemusPuzzleEntity;
import com.benji.netherman.common.entity.TraderEntity;
import com.benji.netherman.common.entity.VillagerPrisonerEntity;
import com.benji.netherman.common.entity.WelcomerEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType.Builder;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEntities {
   public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, "netherman");
   public static final DeferredHolder<EntityType<?>, EntityType<CrimsonArrowEntity>> CRIMSON_ARROW = ENTITIES.register(
      "crimson_arrow",
      () -> Builder.of(CrimsonArrowEntity::new, MobCategory.MISC)
         .sized(0.5F, 0.5F)
         .clientTrackingRange(4)
         .updateInterval(20)
         .build(NetherExp.location("crimson_arrow").toString())
   );
   public static final DeferredHolder<EntityType<?>, EntityType<AzazelEntity>> AZAZEL = ENTITIES.register(
      "azazel", () -> Builder.of(AzazelEntity::new, MobCategory.MONSTER).sized(3.0F, 4.5F).fireImmune().build(NetherExp.location("azazel").toString())
   );
   public static final DeferredHolder<EntityType<?>, EntityType<AzazelHumanEntity>> AZAZEL_HUMAN = ENTITIES.register(
      "azazel_human",
      () -> Builder.of(AzazelHumanEntity::new, MobCategory.MONSTER).sized(2.5F, 12.75F).fireImmune().build(NetherExp.location("azazel_human").toString())
   );
   public static final DeferredHolder<EntityType<?>, EntityType<AzazelSplashEntity>> SPLASH_ENTITY = ENTITIES.register(
      "azazel_splash", () -> Builder.of(AzazelSplashEntity::new, MobCategory.MISC).sized(3.5F, 6.5F).clientTrackingRange(64).build("azazel_splash")
   );
   public static final DeferredHolder<EntityType<?>, EntityType<AzazelSpikesProjectileEntity>> SPIKE_PROJECTILE_ENTITY = ENTITIES.register(
      "azazel_spike_projectile",
      () -> Builder.of(AzazelSpikesProjectileEntity::new, MobCategory.MISC).sized(1.0F, 1.5F).clientTrackingRange(64).build("azazel_spike_projectile")
   );
   public static final DeferredHolder<EntityType<?>, EntityType<AzazelSpikeEntity>> SPIKE_ENTITY = ENTITIES.register(
      "azazel_spike", () -> Builder.of(AzazelSpikeEntity::new, MobCategory.MISC).sized(0.75F, 3.125F).clientTrackingRange(64).build("azazel_spike")
   );
   public static final DeferredHolder<EntityType<?>, EntityType<GildedGolemEntity>> GILDED_GOLEM = ENTITIES.register(
      "gilded_golem",
      () -> Builder.of(GildedGolemEntity::new, MobCategory.MISC).sized(1.4F, 2.7F).fireImmune().build(NetherExp.location("gilded_golem").toString())
   );
   public static final DeferredHolder<EntityType<?>, EntityType<AzazelEarthquakeEntity>> EARTHQUAKE_ENTITY = ENTITIES.register(
      "azazel_earthquake", () -> Builder.of(AzazelEarthquakeEntity::new, MobCategory.MISC).sized(1.0F, 1.0F).clientTrackingRange(64).build("azazel_earthquake")
   );
   public static final DeferredHolder<EntityType<?>, EntityType<StatueBossunitEntity>> STATUE_BOSSUNIT = ENTITIES.register(
      "statue_bossunit",
      () -> Builder.of(StatueBossunitEntity::new, MobCategory.MONSTER)
         .sized(0.625F, 2.125F)
         .fireImmune()
         .build(NetherExp.location("statue_bossunit").toString())
   );
   public static final DeferredHolder<EntityType<?>, EntityType<LaserEntity>> LASER = ENTITIES.register(
      "laser", () -> Builder.of(LaserEntity::new, MobCategory.MISC).sized(3.0F, 18.75F).fireImmune().build(NetherExp.location("laser").toString())
   );
   public static final DeferredHolder<EntityType<?>, EntityType<StatueEntity>> STATUE = ENTITIES.register(
      "statue_entity",
      () -> Builder.of(StatueEntity::new, MobCategory.MONSTER).sized(0.625F, 2.125F).fireImmune().build(NetherExp.location("statue_entity").toString())
   );
   public static final DeferredHolder<EntityType<?>, EntityType<TraderEntity>> TRADER = ENTITIES.register(
      "trader", () -> Builder.of(TraderEntity::new, MobCategory.CREATURE).sized(1.125F, 1.5F).build(NetherExp.location("trader").toString())
   );
   public static final DeferredHolder<EntityType<?>, EntityType<DoctorEntity>> DOCTOR = ENTITIES.register(
      "doctor", () -> Builder.of(DoctorEntity::new, MobCategory.CREATURE).sized(0.6F, 1.95F).build(NetherExp.location("doctor").toString())
   );
   public static final DeferredHolder<EntityType<?>, EntityType<BlacksmithEntity>> BLACKSMITH = ENTITIES.register(
      "blacksmith", () -> Builder.of(BlacksmithEntity::new, MobCategory.CREATURE).sized(0.6F, 1.95F).build(NetherExp.location("blacksmith").toString())
   );
   public static final DeferredHolder<EntityType<?>, EntityType<BelieverEntity>> BELIEVER = ENTITIES.register(
      "believer", () -> Builder.of(BelieverEntity::new, MobCategory.CREATURE).sized(0.6F, 1.95F).build(NetherExp.location("believer").toString())
   );
   public static final DeferredHolder<EntityType<?>, EntityType<BelieverVillagerEntity>> BELIEVER_VILLAGER = ENTITIES.register(
      "believer_villager",
      () -> Builder.of(BelieverVillagerEntity::new, MobCategory.CREATURE).sized(0.6F, 1.95F).build(NetherExp.location("believer_villager").toString())
   );
   public static final DeferredHolder<EntityType<?>, EntityType<VillagerPrisonerEntity>> VILLAGER_PRISONER = ENTITIES.register(
      "villager_prisoner", () -> Builder.of(VillagerPrisonerEntity::new, MobCategory.CREATURE).sized(0.6F, 1.95F).build("villager_prisoner")
   );
   public static final DeferredHolder<EntityType<?>, EntityType<PiglinPrisonerEntity>> PIGLIN_PRISONER = ENTITIES.register(
      "piglin_prisoner", () -> Builder.of(PiglinPrisonerEntity::new, MobCategory.CREATURE).sized(0.6F, 1.95F).build("piglin_prisoner")
   );
   public static final DeferredHolder<EntityType<?>, EntityType<ManipulatorEntity>> MANIPULATOR = ENTITIES.register(
      "manipulator", () -> Builder.of(ManipulatorEntity::new, MobCategory.MONSTER).fireImmune().sized(0.9375F, 2.125F).build("manipulator")
   );
   public static final DeferredHolder<EntityType<?>, EntityType<WelcomerEntity>> WELCOMER = ENTITIES.register(
      "welcomer", () -> Builder.of(WelcomerEntity::new, MobCategory.MONSTER).sized(0.625F, 2.25F).build("welcomer")
   );
   public static final DeferredHolder<EntityType<?>, EntityType<AzazelGuideBookEntity>> AZAZEL_GUIDE_BOOK = ENTITIES.register(
      "azazel_guide_book", () -> Builder.of(AzazelGuideBookEntity::new, MobCategory.MISC).sized(1.0F, 1.0F).fireImmune().build("azazel_guide_book")
   );
   public static final DeferredHolder<EntityType<?>, EntityType<GuardianEntity>> GUARDIAN = ENTITIES.register(
      "guardian", () -> Builder.of(GuardianEntity::new, MobCategory.MONSTER).sized(1.0F, 5.125F).fireImmune().build("guardian")
   );
   public static final DeferredHolder<EntityType<?>, EntityType<GhastlyEntity>> GHASTLY = ENTITIES.register(
      "ghastly", () -> Builder.of(GhastlyEntity::new, MobCategory.CREATURE).sized(0.625F, 0.8125F).fireImmune().build("ghastly")
   );
   public static final DeferredHolder<EntityType<?>, EntityType<TotemusPuzzleEntity>> TOTEMUS_PUZZLE = ENTITIES.register(
      "totemus_puzzle", () -> Builder.of(TotemusPuzzleEntity::new, MobCategory.MONSTER).sized(0.5F, 2.25F).fireImmune().build("totemus_puzzle")
   );
   public static final DeferredHolder<EntityType<?>, EntityType<BellGuardianEntity>> BELL_GUARDIAN = ENTITIES.register(
      "bell_guardian", () -> Builder.of(BellGuardianEntity::new, MobCategory.MONSTER).sized(0.6F, 1.8F).build(NetherExp.location("bell_guardian").toString())
   );

   public static void init(IEventBus bus) {
      ENTITIES.register(bus);
   }
}
