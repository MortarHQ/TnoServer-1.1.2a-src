package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.entity.attack.Candlash;
import cn.leolezury.eternalstarlight.common.entity.attack.Coldsnap;
import cn.leolezury.eternalstarlight.common.entity.attack.CrystalCluster;
import cn.leolezury.eternalstarlight.common.entity.attack.EnergizedFlame;
import cn.leolezury.eternalstarlight.common.entity.attack.LunarThorn;
import cn.leolezury.eternalstarlight.common.entity.attack.PermafrostCloud;
import cn.leolezury.eternalstarlight.common.entity.attack.PoisonousCloud;
import cn.leolezury.eternalstarlight.common.entity.attack.TangledHusk;
import cn.leolezury.eternalstarlight.common.entity.attack.TentacleSpike;
import cn.leolezury.eternalstarlight.common.entity.attack.ray.GolemLaserBeam;
import cn.leolezury.eternalstarlight.common.entity.attack.ray.LunarMonstrosityBreath;
import cn.leolezury.eternalstarlight.common.entity.living.AethersentGolem;
import cn.leolezury.eternalstarlight.common.entity.living.GrimstoneGolem;
import cn.leolezury.eternalstarlight.common.entity.living.animal.AuroraDeer;
import cn.leolezury.eternalstarlight.common.entity.living.animal.CrystallizedMoth;
import cn.leolezury.eternalstarlight.common.entity.living.animal.Ent;
import cn.leolezury.eternalstarlight.common.entity.living.animal.Luminaris;
import cn.leolezury.eternalstarlight.common.entity.living.animal.Luminofish;
import cn.leolezury.eternalstarlight.common.entity.living.animal.Ratlin;
import cn.leolezury.eternalstarlight.common.entity.living.animal.Rookfish;
import cn.leolezury.eternalstarlight.common.entity.living.animal.ShadowSnail;
import cn.leolezury.eternalstarlight.common.entity.living.animal.ShimmerLacewing;
import cn.leolezury.eternalstarlight.common.entity.living.animal.StarfireBird;
import cn.leolezury.eternalstarlight.common.entity.living.animal.TwilightGaze;
import cn.leolezury.eternalstarlight.common.entity.living.animal.Yeti;
import cn.leolezury.eternalstarlight.common.entity.living.boss.creeper.SolarCreeper;
import cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper.TheGatekeeper;
import cn.leolezury.eternalstarlight.common.entity.living.boss.golem.Permafrost;
import cn.leolezury.eternalstarlight.common.entity.living.boss.golem.StarlightGolem;
import cn.leolezury.eternalstarlight.common.entity.living.boss.monstrosity.LunarMonstrosity;
import cn.leolezury.eternalstarlight.common.entity.living.monster.Creteor;
import cn.leolezury.eternalstarlight.common.entity.living.monster.Freeze;
import cn.leolezury.eternalstarlight.common.entity.living.monster.Gleech;
import cn.leolezury.eternalstarlight.common.entity.living.monster.LonestarSkeleton;
import cn.leolezury.eternalstarlight.common.entity.living.monster.NightfallSpider;
import cn.leolezury.eternalstarlight.common.entity.living.monster.Seeker;
import cn.leolezury.eternalstarlight.common.entity.living.monster.Stranghoul;
import cn.leolezury.eternalstarlight.common.entity.living.monster.Tangled;
import cn.leolezury.eternalstarlight.common.entity.living.monster.TangledSkull;
import cn.leolezury.eternalstarlight.common.entity.living.monster.ThirstWalker;
import cn.leolezury.eternalstarlight.common.entity.living.monster.TinyCreteor;
import cn.leolezury.eternalstarlight.common.entity.living.monster.ZombifiedRatlin;
import cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.Boarwarf;
import cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.golem.AstralGolem;
import cn.leolezury.eternalstarlight.common.entity.misc.CrestEntity;
import cn.leolezury.eternalstarlight.common.entity.misc.ESBoat;
import cn.leolezury.eternalstarlight.common.entity.misc.ESChestBoat;
import cn.leolezury.eternalstarlight.common.entity.misc.ESFallingBlock;
import cn.leolezury.eternalstarlight.common.entity.misc.ESPainting;
import cn.leolezury.eternalstarlight.common.entity.misc.EyeOfSeeking;
import cn.leolezury.eternalstarlight.common.entity.misc.TearBomb;
import cn.leolezury.eternalstarlight.common.entity.misc.TearBombMinecart;
import cn.leolezury.eternalstarlight.common.entity.projectile.AethersentArrow;
import cn.leolezury.eternalstarlight.common.entity.projectile.AethersentMeteor;
import cn.leolezury.eternalstarlight.common.entity.projectile.AetherstrikeRocketEntity;
import cn.leolezury.eternalstarlight.common.entity.projectile.AirSacArrow;
import cn.leolezury.eternalstarlight.common.entity.projectile.AmaramberArrow;
import cn.leolezury.eternalstarlight.common.entity.projectile.AshenSnowball;
import cn.leolezury.eternalstarlight.common.entity.projectile.BallLightning;
import cn.leolezury.eternalstarlight.common.entity.projectile.ChainOfSouls;
import cn.leolezury.eternalstarlight.common.entity.projectile.EnergySpark;
import cn.leolezury.eternalstarlight.common.entity.projectile.FrozenBomb;
import cn.leolezury.eternalstarlight.common.entity.projectile.FrozenTube;
import cn.leolezury.eternalstarlight.common.entity.projectile.GatekeeperFireball;
import cn.leolezury.eternalstarlight.common.entity.projectile.GlaciteArrow;
import cn.leolezury.eternalstarlight.common.entity.projectile.GleechEgg;
import cn.leolezury.eternalstarlight.common.entity.projectile.LunarSpore;
import cn.leolezury.eternalstarlight.common.entity.projectile.MalariteArrow;
import cn.leolezury.eternalstarlight.common.entity.projectile.PermafrostSpit;
import cn.leolezury.eternalstarlight.common.entity.projectile.ShotSeeds;
import cn.leolezury.eternalstarlight.common.entity.projectile.SonarBomb;
import cn.leolezury.eternalstarlight.common.entity.projectile.SoulitSpectator;
import cn.leolezury.eternalstarlight.common.entity.projectile.ThioquartzArrow;
import cn.leolezury.eternalstarlight.common.entity.projectile.ThioquartzShard;
import cn.leolezury.eternalstarlight.common.entity.projectile.ThrownEnergyBoomerang;
import cn.leolezury.eternalstarlight.common.entity.projectile.ThrownMalariteSpear;
import cn.leolezury.eternalstarlight.common.entity.projectile.ThrownPungencyFruitSpear;
import cn.leolezury.eternalstarlight.common.entity.projectile.ThrownShatteredBlade;
import cn.leolezury.eternalstarlight.common.entity.projectile.ThrownStarfire;
import cn.leolezury.eternalstarlight.common.entity.projectile.VoraciousArrow;
import cn.leolezury.eternalstarlight.common.entity.projectile.WiltedPetal;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType.Builder;
import net.minecraft.world.entity.player.Player;

public class ESEntities {
   public static final RegistrationProvider<EntityType<?>> ENTITIES = RegistrationProvider.get(Registries.ENTITY_TYPE, "eternal_starlight");
   public static final RegistryObject<EntityType<?>, EntityType<ESFallingBlock>> FALLING_BLOCK = ENTITIES.register(
      "falling_block",
      () -> Builder.of(ESFallingBlock::new, MobCategory.MISC).sized(0.98F, 0.98F).clientTrackingRange(10).updateInterval(1).build("falling_block")
   );
   public static final RegistryObject<EntityType<?>, EntityType<ESPainting>> PAINTING = ENTITIES.register(
      "painting",
      () -> Builder.of(ESPainting::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(10).updateInterval(Integer.MAX_VALUE).build("painting")
   );
   public static final RegistryObject<EntityType<?>, EntityType<AethersentMeteor>> AETHERSENT_METEOR = ENTITIES.register(
      "aethersent_meteor",
      () -> Builder.of(AethersentMeteor::new, MobCategory.MISC)
         .sized(0.98F, 0.98F)
         .clientTrackingRange(10)
         .updateInterval(1)
         .build(EternalStarlight.id("aethersent_meteor").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<AetherstrikeRocketEntity>> AETHERSTRIKE_ROCKET = ENTITIES.register(
      "aetherstrike_rocket",
      () -> Builder.of(AetherstrikeRocketEntity::new, MobCategory.MISC)
         .sized(0.25F, 0.25F)
         .clientTrackingRange(4)
         .updateInterval(10)
         .build(EternalStarlight.id("aetherstrike_rocket").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<ESBoat>> BOAT = ENTITIES.register(
      "boat", () -> Builder.of(ESBoat::new, MobCategory.MISC).sized(1.375F, 0.5625F).eyeHeight(0.5625F).clientTrackingRange(10).build("boat")
   );
   public static final RegistryObject<EntityType<?>, EntityType<ESChestBoat>> CHEST_BOAT = ENTITIES.register(
      "chest_boat", () -> Builder.of(ESChestBoat::new, MobCategory.MISC).sized(1.375F, 0.5625F).eyeHeight(0.5625F).clientTrackingRange(10).build("chest_boat")
   );
   public static final RegistryObject<EntityType<?>, EntityType<Boarwarf>> BOARWARF = ENTITIES.register(
      "boarwarf",
      () -> Builder.of(Boarwarf::new, MobCategory.CREATURE).sized(0.6F, 1.7F).clientTrackingRange(8).build(EternalStarlight.id("boarwarf").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<AstralGolem>> ASTRAL_GOLEM = ENTITIES.register(
      "astral_golem",
      () -> Builder.of(AstralGolem::new, MobCategory.CREATURE).sized(0.5F, 1.25F).ridingOffset(-0.625F).build(EternalStarlight.id("astral_golem").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<Gleech>> GLEECH = ENTITIES.register(
      "gleech",
      () -> Builder.of(Gleech::new, MobCategory.MONSTER)
         .sized(0.5F, 0.4F)
         .eyeHeight(0.13F)
         .passengerAttachments(new float[]{0.2375F})
         .ridingOffset(-0.0625F)
         .clientTrackingRange(8)
         .build(EternalStarlight.id("gleech").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<GleechEgg>> GLEECH_EGG = ENTITIES.register(
      "gleech_egg",
      () -> Builder.of(GleechEgg::new, MobCategory.MISC)
         .sized(0.3F, 0.3F)
         .clientTrackingRange(10)
         .updateInterval(1)
         .build(EternalStarlight.id("gleech_egg").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<LonestarSkeleton>> LONESTAR_SKELETON = ENTITIES.register(
      "lonestar_skeleton",
      () -> Builder.of(LonestarSkeleton::new, MobCategory.MONSTER)
         .sized(0.6F, 1.99F)
         .eyeHeight(1.74F)
         .ridingOffset(-0.7F)
         .clientTrackingRange(8)
         .build(EternalStarlight.id("lonestar_skeleton").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<NightfallSpider>> NIGHTFALL_SPIDER = ENTITIES.register(
      "nightfall_spider",
      () -> Builder.of(NightfallSpider::new, MobCategory.MONSTER)
         .sized(0.75F, 0.75F)
         .clientTrackingRange(8)
         .build(EternalStarlight.id("nightfall_spider").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<Seeker>> SEEKER = ENTITIES.register(
      "seeker",
      () -> Builder.of(Seeker::new, MobCategory.MONSTER).sized(1.0F, 1.0F).clientTrackingRange(8).fireImmune().build(EternalStarlight.id("seeker").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<ThirstWalker>> THIRST_WALKER = ENTITIES.register(
      "thirst_walker",
      () -> Builder.of(ThirstWalker::new, MobCategory.MONSTER)
         .sized(0.6F, 2.5F)
         .ridingOffset(-0.125F)
         .clientTrackingRange(8)
         .build(EternalStarlight.id("thirst_walker").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<Creteor>> CRETEOR = ENTITIES.register(
      "creteor", () -> Builder.of(Creteor::new, MobCategory.MONSTER).sized(0.6F, 1.5F).clientTrackingRange(8).build(EternalStarlight.id("creteor").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<TinyCreteor>> TINY_CRETEOR = ENTITIES.register(
      "tiny_creteor",
      () -> Builder.of(TinyCreteor::new, MobCategory.MONSTER).sized(0.5F, 0.5F).clientTrackingRange(8).build(EternalStarlight.id("tiny_creteor").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<Stranghoul>> STRANGHOUL = ENTITIES.register(
      "stranghoul",
      () -> Builder.of(Stranghoul::new, MobCategory.MONSTER)
         .sized(0.6F, 1.99F)
         .eyeHeight(1.74F)
         .ridingOffset(-0.7F)
         .clientTrackingRange(8)
         .build(EternalStarlight.id("stranghoul").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<Ent>> ENT = ENTITIES.register(
      "ent",
      () -> Builder.of(Ent::new, MobCategory.CREATURE)
         .sized(0.5F, 0.6875F)
         .passengerAttachments(new float[]{0.35F})
         .clientTrackingRange(8)
         .build(EternalStarlight.id("ent").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<Ratlin>> RATLIN = ENTITIES.register(
      "ratlin", () -> Builder.of(Ratlin::new, MobCategory.CREATURE).sized(0.9F, 0.775F).clientTrackingRange(8).build(EternalStarlight.id("ratlin").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<ZombifiedRatlin>> ZOMBIFIED_RATLIN = ENTITIES.register(
      "zombified_ratlin",
      () -> Builder.of(ZombifiedRatlin::new, MobCategory.CREATURE)
         .sized(0.9F, 0.775F)
         .clientTrackingRange(8)
         .build(EternalStarlight.id("zombified_ratlin").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<ShadowSnail>> SHADOW_SNAIL = ENTITIES.register(
      "shadow_snail",
      () -> Builder.of(ShadowSnail::new, MobCategory.CREATURE)
         .sized(0.5F, 0.4F)
         .eyeHeight(0.13F)
         .passengerAttachments(new float[]{0.2375F})
         .ridingOffset(-0.0625F)
         .clientTrackingRange(8)
         .build(EternalStarlight.id("shadow_snail").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<Yeti>> YETI = ENTITIES.register(
      "yeti", () -> Builder.of(Yeti::new, MobCategory.CREATURE).sized(0.9F, 0.9F).clientTrackingRange(8).build(EternalStarlight.id("yeti").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<AuroraDeer>> AURORA_DEER = ENTITIES.register(
      "aurora_deer",
      () -> Builder.of(AuroraDeer::new, MobCategory.CREATURE)
         .sized(0.9F, 1.75F)
         .passengerAttachments(new float[]{1.0F})
         .clientTrackingRange(8)
         .build(EternalStarlight.id("aurora_deer").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<CrystallizedMoth>> CRYSTALLIZED_MOTH = ENTITIES.register(
      "crystallized_moth",
      () -> Builder.of(CrystallizedMoth::new, MobCategory.MONSTER)
         .sized(0.5F, 0.4375F)
         .clientTrackingRange(8)
         .build(EternalStarlight.id("crystallized_moth").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<ShimmerLacewing>> SHIMMER_LACEWING = ENTITIES.register(
      "shimmer_lacewing",
      () -> Builder.of(ShimmerLacewing::new, MobCategory.AMBIENT)
         .sized(0.5F, 0.5F)
         .clientTrackingRange(8)
         .build(EternalStarlight.id("shimmer_lacewing").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<StarfireBird>> STARFIRE_BIRD = ENTITIES.register(
      "starfire_bird",
      () -> Builder.of(StarfireBird::new, MobCategory.AMBIENT)
         .sized(0.5F, 0.625F)
         .clientTrackingRange(8)
         .build(EternalStarlight.id("starfire_bird").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<GrimstoneGolem>> GRIMSTONE_GOLEM = ENTITIES.register(
      "grimstone_golem",
      () -> Builder.of(GrimstoneGolem::new, MobCategory.CREATURE)
         .sized(0.5F, 1.0F)
         .clientTrackingRange(8)
         .build(EternalStarlight.id("grimstone_golem").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<AethersentGolem>> AETHERSENT_GOLEM = ENTITIES.register(
      "aethersent_golem",
      () -> Builder.of(AethersentGolem::new, MobCategory.CREATURE)
         .sized(0.5F, 1.75F)
         .clientTrackingRange(8)
         .build(EternalStarlight.id("aethersent_golem").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<Rookfish>> ROOKFISH = ENTITIES.register(
      "rookfish",
      () -> Builder.of(Rookfish::new, MobCategory.WATER_CREATURE)
         .sized(0.6F, 0.6F)
         .eyeHeight(0.4F)
         .clientTrackingRange(8)
         .build(EternalStarlight.id("rookfish").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<Luminofish>> LUMINOFISH = ENTITIES.register(
      "luminofish",
      () -> Builder.of(Luminofish::new, MobCategory.WATER_AMBIENT)
         .sized(0.5F, 0.3F)
         .clientTrackingRange(10)
         .build(EternalStarlight.id("luminofish").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<Luminaris>> LUMINARIS = ENTITIES.register(
      "luminaris",
      () -> Builder.of(Luminaris::new, MobCategory.WATER_AMBIENT).sized(0.5F, 0.3F).clientTrackingRange(10).build(EternalStarlight.id("luminaris").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<TwilightGaze>> TWILIGHT_GAZE = ENTITIES.register(
      "twilight_gaze",
      () -> Builder.of(TwilightGaze::new, MobCategory.WATER_CREATURE)
         .sized(0.9F, 0.6F)
         .clientTrackingRange(10)
         .build(EternalStarlight.id("twilight_gaze").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<EyeOfSeeking>> EYE_OF_SEEKING = ENTITIES.register(
      "eye_of_seeking",
      () -> Builder.of(EyeOfSeeking::new, MobCategory.MISC)
         .sized(0.25F, 0.25F)
         .clientTrackingRange(4)
         .updateInterval(4)
         .build(EternalStarlight.id("eye_of_seeking").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<CrestEntity>> CREST = ENTITIES.register(
      "crest",
      () -> Builder.of(CrestEntity::new, MobCategory.MISC)
         .sized(0.25F, 0.25F)
         .clientTrackingRange(4)
         .updateInterval(4)
         .build(EternalStarlight.id("crest").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<TheGatekeeper>> THE_GATEKEEPER = ENTITIES.register(
      "the_gatekeeper",
      () -> Builder.of(TheGatekeeper::new, MobCategory.MONSTER)
         .sized(0.75F, 1.99F)
         .clientTrackingRange(32)
         .eyeHeight(1.74F)
         .fireImmune()
         .build(EternalStarlight.id("the_gatekeeper").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<GatekeeperFireball>> GATEKEEPER_FIREBALL = ENTITIES.register(
      "gatekeeper_fireball",
      () -> Builder.of(GatekeeperFireball::new, MobCategory.MISC)
         .sized(1.0F, 1.0F)
         .clientTrackingRange(4)
         .updateInterval(1)
         .build(EternalStarlight.id("gatekeeper_fireball").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<StarlightGolem>> STARLIGHT_GOLEM = ENTITIES.register(
      "starlight_golem",
      () -> Builder.of(StarlightGolem::new, MobCategory.MONSTER)
         .sized(1.25F, 2.5F)
         .clientTrackingRange(32)
         .fireImmune()
         .build(EternalStarlight.id("starlight_golem").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<GolemLaserBeam>> GOLEM_LASER_BEAM = ENTITIES.register(
      "golem_laser_beam",
      () -> Builder.of(GolemLaserBeam::new, MobCategory.MISC)
         .sized(0.0F, 0.0F)
         .clientTrackingRange(32)
         .build(EternalStarlight.id("golem_laser_beam").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<EnergizedFlame>> ENERGIZED_FLAME = ENTITIES.register(
      "energized_flame", () -> Builder.of(EnergizedFlame::new, MobCategory.MISC).sized(0.0F, 0.0F).build(EternalStarlight.id("energized_flame").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<Freeze>> FREEZE = ENTITIES.register(
      "freeze", () -> Builder.of(Freeze::new, MobCategory.MONSTER).sized(0.5F, 1.25F).fireImmune().build(EternalStarlight.id("freeze").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<FrozenTube>> FROZEN_TUBE = ENTITIES.register(
      "frozen_tube",
      () -> Builder.of(FrozenTube::new, MobCategory.MISC)
         .sized(0.3F, 0.3F)
         .clientTrackingRange(10)
         .updateInterval(1)
         .build(EternalStarlight.id("frozen_tube").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<Permafrost>> PERMAFROST = ENTITIES.register(
      "permafrost",
      () -> Builder.of(Permafrost::new, MobCategory.MONSTER)
         .sized(0.75F, 2.25F)
         .clientTrackingRange(32)
         .fireImmune()
         .build(EternalStarlight.id("permafrost").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<PermafrostSpit>> PERMAFROST_SPIT = ENTITIES.register(
      "permafrost_spit",
      () -> Builder.of(PermafrostSpit::new, MobCategory.MISC)
         .sized(0.3F, 0.3F)
         .clientTrackingRange(10)
         .updateInterval(1)
         .build(EternalStarlight.id("permafrost_spit").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<PermafrostCloud>> PERMAFROST_CLOUD = ENTITIES.register(
      "permafrost_cloud",
      () -> Builder.of(PermafrostCloud::new, MobCategory.MISC)
         .sized(3.5F, 0.3F)
         .clientTrackingRange(10)
         .build(EternalStarlight.id("permafrost_cloud").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<Coldsnap>> COLDSNAP = ENTITIES.register(
      "coldsnap",
      () -> Builder.of(Coldsnap::new, MobCategory.MISC)
         .sized(0.0F, 0.0F)
         .clientTrackingRange(10)
         .updateInterval(1)
         .build(EternalStarlight.id("coldsnap").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<LunarMonstrosity>> LUNAR_MONSTROSITY = ENTITIES.register(
      "lunar_monstrosity",
      () -> Builder.of(LunarMonstrosity::new, MobCategory.MONSTER)
         .sized(0.9F, 3.5F)
         .clientTrackingRange(32)
         .build(EternalStarlight.id("lunar_monstrosity").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<LunarMonstrosityBreath>> LUNAR_MONSTROSITY_BREATH = ENTITIES.register(
      "lunar_monstrosity_breath",
      () -> Builder.of(LunarMonstrosityBreath::new, MobCategory.MISC)
         .sized(0.0F, 0.0F)
         .clientTrackingRange(32)
         .build(EternalStarlight.id("lunar_monstrosity_breath").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<LunarSpore>> LUNAR_SPORE = ENTITIES.register(
      "lunar_spore",
      () -> Builder.of(LunarSpore::new, MobCategory.MISC)
         .sized(0.3F, 0.3F)
         .clientTrackingRange(6)
         .updateInterval(1)
         .build(EternalStarlight.id("lunar_spore").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<LunarThorn>> LUNAR_THORN = ENTITIES.register(
      "lunar_thorn", () -> Builder.of(LunarThorn::new, MobCategory.MISC).sized(0.3F, 1.0F).build(EternalStarlight.id("lunar_thorn").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<PoisonousCloud>> POISONOUS_CLOUD = ENTITIES.register(
      "poisonous_cloud",
      () -> Builder.of(PoisonousCloud::new, MobCategory.MISC)
         .sized(5.0F, 1.0F)
         .clientTrackingRange(10)
         .build(EternalStarlight.id("poisonous_cloud").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<Tangled>> TANGLED = ENTITIES.register(
      "tangled",
      () -> Builder.of(Tangled::new, MobCategory.MONSTER)
         .sized(0.6F, 1.99F)
         .eyeHeight(1.74F)
         .ridingOffset(-0.7F)
         .clientTrackingRange(8)
         .build(EternalStarlight.id("tangled").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<TangledSkull>> TANGLED_SKULL = ENTITIES.register(
      "tangled_skull",
      () -> Builder.of(TangledSkull::new, MobCategory.MONSTER).sized(0.5F, 0.5F).clientTrackingRange(8).build(EternalStarlight.id("tangled_skull").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<SolarCreeper>> SOLAR_CREEPER = ENTITIES.register(
      "solar_creeper",
      () -> Builder.of(SolarCreeper::new, MobCategory.MONSTER)
         .sized(1.5F, 2.75F)
         .clientTrackingRange(32)
         .fireImmune()
         .build(EternalStarlight.id("solar_creeper").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<TangledHusk>> TANGLED_HUSK = ENTITIES.register(
      "tangled_husk",
      () -> Builder.of(TangledHusk::new, MobCategory.MISC)
         .sized(0.6F, 1.8F)
         .eyeHeight(1.62F)
         .vehicleAttachment(Player.DEFAULT_VEHICLE_ATTACHMENT)
         .clientTrackingRange(32)
         .updateInterval(2)
         .build(EternalStarlight.id("tangled_husk").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<ThrownShatteredBlade>> SHATTERED_BLADE = ENTITIES.register(
      "shattered_blade",
      () -> Builder.of(ThrownShatteredBlade::new, MobCategory.MISC)
         .sized(0.3F, 0.3F)
         .clientTrackingRange(6)
         .updateInterval(1)
         .build(EternalStarlight.id("shattered_blade").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<ThrownMalariteSpear>> MALARITE_SPEAR = ENTITIES.register(
      "malarite_spear",
      () -> Builder.of(ThrownMalariteSpear::new, MobCategory.MISC)
         .sized(0.3F, 0.3F)
         .clientTrackingRange(6)
         .updateInterval(1)
         .build(EternalStarlight.id("malarite_spear").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<TearBomb>> TEAR_BOMB = ENTITIES.register(
      "tear_bomb",
      () -> Builder.of(TearBomb::new, MobCategory.MISC)
         .fireImmune()
         .sized(0.98F, 0.98F)
         .eyeHeight(0.15F)
         .clientTrackingRange(10)
         .updateInterval(10)
         .build(EternalStarlight.id("tear_bomb").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<TearBombMinecart>> TEAR_BOMB_MINECART = ENTITIES.register(
      "tear_bomb_minecart",
      () -> Builder.of(TearBombMinecart::new, MobCategory.MISC)
         .sized(0.98F, 0.7F)
         .passengerAttachments(new float[]{0.1875F})
         .clientTrackingRange(8)
         .build(EternalStarlight.id("tear_bomb_minecart").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<ThrownPungencyFruitSpear>> PUNGENCY_FRUIT_SPEAR = ENTITIES.register(
      "pungency_fruit_spear",
      () -> Builder.of(ThrownPungencyFruitSpear::new, MobCategory.MISC)
         .sized(0.3F, 0.3F)
         .clientTrackingRange(6)
         .updateInterval(1)
         .build(EternalStarlight.id("pungency_fruit_spear").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<ThioquartzArrow>> THIOQUARTZ_ARROW = ENTITIES.register(
      "thioquartz_arrow",
      () -> Builder.of(ThioquartzArrow::new, MobCategory.MISC)
         .sized(0.5F, 0.5F)
         .clientTrackingRange(4)
         .updateInterval(20)
         .build(EternalStarlight.id("thioquartz_arrow").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<ThioquartzShard>> THIOQUARTZ_SHARD = ENTITIES.register(
      "thioquartz_shard",
      () -> Builder.of(ThioquartzShard::new, MobCategory.MISC)
         .sized(0.2F, 0.2F)
         .clientTrackingRange(10)
         .updateInterval(1)
         .build(EternalStarlight.id("thioquartz_shard").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<AethersentArrow>> AETHERSENT_ARROW = ENTITIES.register(
      "aethersent_arrow",
      () -> Builder.of(AethersentArrow::new, MobCategory.MISC)
         .sized(0.5F, 0.5F)
         .clientTrackingRange(4)
         .updateInterval(20)
         .build(EternalStarlight.id("aethersent_arrow").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<GlaciteArrow>> GLACITE_ARROW = ENTITIES.register(
      "glacite_arrow",
      () -> Builder.of(GlaciteArrow::new, MobCategory.MISC)
         .sized(0.5F, 0.5F)
         .clientTrackingRange(4)
         .updateInterval(20)
         .build(EternalStarlight.id("glacite_arrow").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<MalariteArrow>> MALARITE_ARROW = ENTITIES.register(
      "malarite_arrow",
      () -> Builder.of(MalariteArrow::new, MobCategory.MISC)
         .sized(0.5F, 0.5F)
         .clientTrackingRange(4)
         .updateInterval(20)
         .build(EternalStarlight.id("malarite_arrow").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<AmaramberArrow>> AMARAMBER_ARROW = ENTITIES.register(
      "amaramber_arrow",
      () -> Builder.of(AmaramberArrow::new, MobCategory.MISC)
         .sized(0.5F, 0.5F)
         .clientTrackingRange(4)
         .updateInterval(20)
         .build(EternalStarlight.id("amaramber_arrow").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<VoraciousArrow>> VORACIOUS_ARROW = ENTITIES.register(
      "voracious_arrow",
      () -> Builder.of(VoraciousArrow::new, MobCategory.MISC)
         .sized(0.5F, 0.5F)
         .clientTrackingRange(4)
         .updateInterval(20)
         .build(EternalStarlight.id("voracious_arrow").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<AirSacArrow>> AIR_SAC_ARROW = ENTITIES.register(
      "air_sac_arrow",
      () -> Builder.of(AirSacArrow::new, MobCategory.MISC)
         .sized(0.5F, 0.5F)
         .clientTrackingRange(4)
         .updateInterval(20)
         .build(EternalStarlight.id("air_sac_arrow").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<SonarBomb>> SONAR_BOMB = ENTITIES.register(
      "sonar_bomb",
      () -> Builder.of(SonarBomb::new, MobCategory.MISC)
         .sized(0.3F, 0.3F)
         .clientTrackingRange(10)
         .updateInterval(1)
         .build(EternalStarlight.id("sonar_bomb").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<AshenSnowball>> ASHEN_SNOWBALL = ENTITIES.register(
      "ashen_snowball",
      () -> Builder.of(AshenSnowball::new, MobCategory.MISC)
         .sized(0.3F, 0.3F)
         .clientTrackingRange(10)
         .updateInterval(1)
         .build(EternalStarlight.id("ashen_snowball").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<FrozenBomb>> FROZEN_BOMB = ENTITIES.register(
      "frozen_bomb",
      () -> Builder.of(FrozenBomb::new, MobCategory.MISC)
         .sized(0.3F, 0.3F)
         .clientTrackingRange(10)
         .updateInterval(1)
         .build(EternalStarlight.id("frozen_bomb").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<WiltedPetal>> WILTED_PETAL = ENTITIES.register(
      "wilted_petal",
      () -> Builder.of(WiltedPetal::new, MobCategory.MISC)
         .sized(0.2F, 0.2F)
         .clientTrackingRange(10)
         .updateInterval(1)
         .build(EternalStarlight.id("wilted_petal").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<ShotSeeds>> SHOT_SEEDS = ENTITIES.register(
      "shot_seeds",
      () -> Builder.of(ShotSeeds::new, MobCategory.MISC)
         .sized(0.2F, 0.2F)
         .clientTrackingRange(10)
         .updateInterval(1)
         .build(EternalStarlight.id("shot_seeds").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<ThrownStarfire>> STARFIRE = ENTITIES.register(
      "starfire",
      () -> Builder.of(ThrownStarfire::new, MobCategory.MISC)
         .sized(0.3F, 0.3F)
         .clientTrackingRange(10)
         .updateInterval(1)
         .build(EternalStarlight.id("starfire").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<Candlash>> CANDLASH = ENTITIES.register(
      "candlash",
      () -> Builder.of(Candlash::new, MobCategory.MISC)
         .sized(0.0F, 0.0F)
         .clientTrackingRange(10)
         .updateInterval(1)
         .build(EternalStarlight.id("candlash").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<TentacleSpike>> TENTACLE_SPIKE = ENTITIES.register(
      "tentacle_spike",
      () -> Builder.of(TentacleSpike::new, MobCategory.MISC)
         .sized(0.0F, 0.0F)
         .clientTrackingRange(10)
         .updateInterval(1)
         .build(EternalStarlight.id("tentacle_spike").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<EnergySpark>> ENERGY_SPARK = ENTITIES.register(
      "energy_spark",
      () -> Builder.of(EnergySpark::new, MobCategory.MISC)
         .sized(0.5F, 0.5F)
         .clientTrackingRange(10)
         .updateInterval(1)
         .build(EternalStarlight.id("energy_spark").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<BallLightning>> BALL_LIGHTNING = ENTITIES.register(
      "ball_lightning",
      () -> Builder.of(BallLightning::new, MobCategory.MISC)
         .sized(0.5F, 0.5F)
         .clientTrackingRange(10)
         .updateInterval(1)
         .build(EternalStarlight.id("ball_lightning").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<CrystalCluster>> CRYSTAL_CLUSTER = ENTITIES.register(
      "crystal_cluster",
      () -> Builder.of(CrystalCluster::new, MobCategory.MISC)
         .sized(0.75F, 0.75F)
         .clientTrackingRange(10)
         .updateInterval(1)
         .build(EternalStarlight.id("crystal_cluster").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<ThrownEnergyBoomerang>> ENERGY_BOOMERANG = ENTITIES.register(
      "energy_boomerang",
      () -> Builder.of(ThrownEnergyBoomerang::new, MobCategory.MISC)
         .sized(0.3F, 0.3F)
         .clientTrackingRange(6)
         .updateInterval(1)
         .build(EternalStarlight.id("energy_boomerang").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<SoulitSpectator>> SOULIT_SPECTATOR = ENTITIES.register(
      "soulit_spectator",
      () -> Builder.of(SoulitSpectator::new, MobCategory.MISC)
         .sized(0.3F, 0.3F)
         .clientTrackingRange(10)
         .updateInterval(1)
         .build(EternalStarlight.id("soulit_spectator").toString())
   );
   public static final RegistryObject<EntityType<?>, EntityType<ChainOfSouls>> CHAIN_OF_SOULS = ENTITIES.register(
      "chain_of_souls",
      () -> Builder.of(ChainOfSouls::new, MobCategory.MISC)
         .sized(0.3F, 0.3F)
         .clientTrackingRange(10)
         .updateInterval(1)
         .build(EternalStarlight.id("chain_of_souls").toString())
   );

   public static void loadClass() {
   }
}
