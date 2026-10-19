package twilightforest.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType.Builder;
import net.minecraft.world.entity.EntityType.EntityFactory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import twilightforest.entity.CharmEffect;
import twilightforest.entity.MagicPainting;
import twilightforest.entity.ProtectionBox;
import twilightforest.entity.RovingCube;
import twilightforest.entity.SlideBlock;
import twilightforest.entity.TFEntityNames;
import twilightforest.entity.boss.AlphaYeti;
import twilightforest.entity.boss.Hydra;
import twilightforest.entity.boss.HydraMortar;
import twilightforest.entity.boss.KnightPhantom;
import twilightforest.entity.boss.Lich;
import twilightforest.entity.boss.Minoshroom;
import twilightforest.entity.boss.Naga;
import twilightforest.entity.boss.PlateauBoss;
import twilightforest.entity.boss.SnowQueen;
import twilightforest.entity.boss.UrGhast;
import twilightforest.entity.monster.Adherent;
import twilightforest.entity.monster.ArmoredGiant;
import twilightforest.entity.monster.BlockChainGoblin;
import twilightforest.entity.monster.CarminiteGhastguard;
import twilightforest.entity.monster.CarminiteGhastling;
import twilightforest.entity.monster.CarminiteGolem;
import twilightforest.entity.monster.DeathTome;
import twilightforest.entity.monster.FireBeetle;
import twilightforest.entity.monster.GiantMiner;
import twilightforest.entity.monster.HarbingerCube;
import twilightforest.entity.monster.HedgeSpider;
import twilightforest.entity.monster.HelmetCrab;
import twilightforest.entity.monster.HostileWolf;
import twilightforest.entity.monster.IceCrystal;
import twilightforest.entity.monster.KingSpider;
import twilightforest.entity.monster.Kobold;
import twilightforest.entity.monster.LichMinion;
import twilightforest.entity.monster.LowerGoblinKnight;
import twilightforest.entity.monster.LoyalZombie;
import twilightforest.entity.monster.MazeSlime;
import twilightforest.entity.monster.Minotaur;
import twilightforest.entity.monster.MistWolf;
import twilightforest.entity.monster.MosquitoSwarm;
import twilightforest.entity.monster.PinchBeetle;
import twilightforest.entity.monster.Redcap;
import twilightforest.entity.monster.RedcapSapper;
import twilightforest.entity.monster.RisingZombie;
import twilightforest.entity.monster.SkeletonDruid;
import twilightforest.entity.monster.SlimeBeetle;
import twilightforest.entity.monster.SnowGuardian;
import twilightforest.entity.monster.StableIceCore;
import twilightforest.entity.monster.SwarmSpider;
import twilightforest.entity.monster.TowerBroodling;
import twilightforest.entity.monster.TowerwoodBorer;
import twilightforest.entity.monster.Troll;
import twilightforest.entity.monster.UnstableIceCore;
import twilightforest.entity.monster.UpperGoblinKnight;
import twilightforest.entity.monster.WinterWolf;
import twilightforest.entity.monster.Wraith;
import twilightforest.entity.monster.Yeti;
import twilightforest.entity.passive.Bighorn;
import twilightforest.entity.passive.Boar;
import twilightforest.entity.passive.Deer;
import twilightforest.entity.passive.DwarfRabbit;
import twilightforest.entity.passive.Penguin;
import twilightforest.entity.passive.QuestRam;
import twilightforest.entity.passive.Raven;
import twilightforest.entity.passive.Squirrel;
import twilightforest.entity.passive.TinyBird;
import twilightforest.entity.projectile.ChainBlock;
import twilightforest.entity.projectile.CubeOfAnnihilation;
import twilightforest.entity.projectile.FallingIce;
import twilightforest.entity.projectile.IceArrow;
import twilightforest.entity.projectile.IceBomb;
import twilightforest.entity.projectile.IceSnowball;
import twilightforest.entity.projectile.LichBolt;
import twilightforest.entity.projectile.LichBomb;
import twilightforest.entity.projectile.MoonwormShot;
import twilightforest.entity.projectile.NatureBolt;
import twilightforest.entity.projectile.SeekerArrow;
import twilightforest.entity.projectile.SlimeProjectile;
import twilightforest.entity.projectile.ThrownBlock;
import twilightforest.entity.projectile.ThrownWep;
import twilightforest.entity.projectile.TomeBolt;
import twilightforest.entity.projectile.TwilightWandBolt;

public class TFEntities {
   public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, "twilightforest");
   public static final DeferredRegister<Item> SPAWN_EGGS = DeferredRegister.create(Registries.ITEM, "twilightforest");
   public static final DeferredHolder<EntityType<?>, EntityType<Adherent>> ADHERENT = make(
      TFEntityNames.ADHERENT, Adherent::new, MobCategory.MONSTER, 0.8F, 2.2F, 0, 0
   );
   public static final DeferredHolder<EntityType<?>, EntityType<AlphaYeti>> ALPHA_YETI = make(
      TFEntityNames.ALPHA_YETI, AlphaYeti::new, MobCategory.MONSTER, 3.8F, 5.0F, 13487565, 2705518
   );
   public static final DeferredHolder<EntityType<?>, EntityType<ArmoredGiant>> ARMORED_GIANT = make(
      TFEntityNames.ARMORED_GIANT, ArmoredGiant::new, MobCategory.MONSTER, 2.4F, 7.2F, 2331537, 10132122
   );
   public static final DeferredHolder<EntityType<?>, EntityType<Bighorn>> BIGHORN_SHEEP = make(
      TFEntityNames.BIGHORN_SHEEP, Bighorn::new, MobCategory.CREATURE, 0.9F, 1.3F, 14405295, 14141297
   );
   public static final DeferredHolder<EntityType<?>, EntityType<BlockChainGoblin>> BLOCKCHAIN_GOBLIN = make(
      TFEntityNames.BLOCKCHAIN_GOBLIN, BlockChainGoblin::new, MobCategory.MONSTER, 0.9F, 1.4F, 13887420, 2047999
   );
   public static final DeferredHolder<EntityType<?>, EntityType<Boar>> BOAR = make(
      TFEntityNames.BOAR, Boar::new, MobCategory.CREATURE, 0.9F, 0.9F, 8611131, 16773066
   );
   public static final DeferredHolder<EntityType<?>, EntityType<TowerBroodling>> CARMINITE_BROODLING = make(
      TFEntityNames.CARMINITE_BROODLING, TowerBroodling::new, MobCategory.MONSTER, 0.7F, 0.5F, 3423252, 12250626
   );
   public static final DeferredHolder<EntityType<?>, EntityType<CarminiteGhastguard>> CARMINITE_GHASTGUARD = make(
      TFEntityNames.CARMINITE_GHASTGUARD, CarminiteGhastguard::new, MobCategory.MONSTER, 4.0F, 6.0F, true, 12369084, 12023928
   );
   public static final DeferredHolder<EntityType<?>, EntityType<CarminiteGhastling>> CARMINITE_GHASTLING = make(
      TFEntityNames.CARMINITE_GHASTLING, CarminiteGhastling::new, MobCategory.MONSTER, 1.1F, 1.5F, 0.5F, true, 12369084, 10961731
   );
   public static final DeferredHolder<EntityType<?>, EntityType<CarminiteGolem>> CARMINITE_GOLEM = make(
      TFEntityNames.CARMINITE_GOLEM, CarminiteGolem::new, MobCategory.MONSTER, 1.4F, 2.9F, 7028000, 14867930
   );
   public static final DeferredHolder<EntityType<?>, EntityType<ChainBlock>> CHAIN_BLOCK = buildNoEgg(
      TFEntityNames.CHAIN_BLOCK, makeCastedBuilder(ChainBlock.class, ChainBlock::new, 0.6F, 0.6F, 80, 1), true
   );
   public static final DeferredHolder<EntityType<?>, EntityType<CharmEffect>> CHARM_EFFECT = buildNoEgg(
      TFEntityNames.CHARM_EFFECT, makeCastedBuilder(CharmEffect.class, CharmEffect::new, 0.0F, 0.0F, 16, Integer.MAX_VALUE).noSave().noSummon(), true
   );
   public static final DeferredHolder<EntityType<?>, EntityType<CubeOfAnnihilation>> CUBE_OF_ANNIHILATION = buildNoEgg(
      TFEntityNames.CUBE_OF_ANNIHILATION, makeCastedBuilder(CubeOfAnnihilation.class, CubeOfAnnihilation::new, 1.0F, 1.0F, 80, 1), true
   );
   public static final DeferredHolder<EntityType<?>, EntityType<DeathTome>> DEATH_TOME = make(
      TFEntityNames.DEATH_TOME, DeathTome::new, MobCategory.MONSTER, 0.75F, 1.5F, 7818786, 14405054
   );
   public static final DeferredHolder<EntityType<?>, EntityType<Deer>> DEER = make(
      TFEntityNames.DEER, Deer::new, MobCategory.CREATURE, 0.7F, 1.8F, 8080686, 4924445
   );
   public static final DeferredHolder<EntityType<?>, EntityType<DwarfRabbit>> DWARF_RABBIT = make(
      TFEntityNames.DWARF_RABBIT, DwarfRabbit::new, MobCategory.CREATURE, 0.4F, 0.4F, 16711406, 13413017
   );
   public static final DeferredHolder<EntityType<?>, EntityType<FallingIce>> FALLING_ICE = make(
      TFEntityNames.FALLING_ICE, FallingIce::new, MobCategory.MISC, 1.0F, 1.0F, true, 0, 0
   );
   public static final DeferredHolder<EntityType<?>, EntityType<FireBeetle>> FIRE_BEETLE = make(
      TFEntityNames.FIRE_BEETLE, FireBeetle::new, MobCategory.MONSTER, 1.1F, 0.5F, true, 1903360, 13332261
   );
   public static final DeferredHolder<EntityType<?>, EntityType<GiantMiner>> GIANT_MINER = make(
      TFEntityNames.GIANT_MINER, GiantMiner::new, MobCategory.MONSTER, 2.4F, 7.2F, 2169682, 10132122
   );
   public static final DeferredHolder<EntityType<?>, EntityType<HarbingerCube>> HARBINGER_CUBE = make(
      TFEntityNames.HARBINGER_CUBE, HarbingerCube::new, MobCategory.MONSTER, 1.9F, 2.4F, true, 0, 0
   );
   public static final DeferredHolder<EntityType<?>, EntityType<HedgeSpider>> HEDGE_SPIDER = make(
      TFEntityNames.HEDGE_SPIDER, HedgeSpider::new, MobCategory.MONSTER, 1.4F, 0.9F, 2318099, 5645907
   );
   public static final DeferredHolder<EntityType<?>, EntityType<HelmetCrab>> HELMET_CRAB = make(
      TFEntityNames.HELMET_CRAB, HelmetCrab::new, MobCategory.MONSTER, 0.8F, 1.1F, 0.45F, false, 16486475, 13887420
   );
   public static final DeferredHolder<EntityType<?>, EntityType<HostileWolf>> HOSTILE_WOLF = make(
      TFEntityNames.HOSTILE_WOLF, HostileWolf::new, MobCategory.MONSTER, 0.6F, 0.85F, 14144467, 11214356
   );
   public static final DeferredHolder<EntityType<?>, EntityType<Hydra>> HYDRA = make(
      TFEntityNames.HYDRA, Hydra::new, MobCategory.MONSTER, 16.0F, 12.0F, true, 1321280, 2719851
   );
   public static final DeferredHolder<EntityType<?>, EntityType<HydraMortar>> HYDRA_MORTAR = buildNoEgg(
      TFEntityNames.HYDRA_MORTAR, makeCastedBuilder(HydraMortar.class, HydraMortar::new, 0.75F, 0.75F, 150, 3), true
   );
   public static final DeferredHolder<EntityType<?>, EntityType<IceArrow>> ICE_ARROW = buildNoEgg(
      TFEntityNames.ICE_ARROW, makeCastedBuilder(IceArrow.class, IceArrow::new, 0.5F, 0.5F, 150, 1), false
   );
   public static final DeferredHolder<EntityType<?>, EntityType<IceBomb>> THROWN_ICE = buildNoEgg(
      TFEntityNames.THROWN_ICE, makeCastedBuilder(IceBomb.class, IceBomb::new, 1.0F, 1.0F, 80, 2), true
   );
   public static final DeferredHolder<EntityType<?>, EntityType<IceCrystal>> ICE_CRYSTAL = make(
      TFEntityNames.ICE_CRYSTAL, IceCrystal::new, MobCategory.MONSTER, 0.6F, 1.8F, 14477822, 11389691
   );
   public static final DeferredHolder<EntityType<?>, EntityType<IceSnowball>> ICE_SNOWBALL = buildNoEgg(
      TFEntityNames.ICE_SNOWBALL, makeCastedBuilder(IceSnowball.class, IceSnowball::new, 0.25F, 0.25F, 150, 3), false
   );
   public static final DeferredHolder<EntityType<?>, EntityType<KingSpider>> KING_SPIDER = make(
      TFEntityNames.KING_SPIDER, KingSpider::new, MobCategory.MONSTER, 1.6F, 1.6F, 2890254, 16760855
   );
   public static final DeferredHolder<EntityType<?>, EntityType<KnightPhantom>> KNIGHT_PHANTOM = make(
      TFEntityNames.KNIGHT_PHANTOM, KnightPhantom::new, MobCategory.MONSTER, 1.25F, 2.5F, 10905403, 13887420
   );
   public static final DeferredHolder<EntityType<?>, EntityType<Kobold>> KOBOLD = make(
      TFEntityNames.KOBOLD, Kobold::new, MobCategory.MONSTER, 0.8F, 1.1F, 3612822, 9002267
   );
   public static final DeferredHolder<EntityType<?>, EntityType<Lich>> LICH = make(
      TFEntityNames.LICH, Lich::new, MobCategory.MONSTER, 0.8F, 2.5F, 1.95F, false, 11314313, 3540082
   );
   public static final DeferredHolder<EntityType<?>, EntityType<LichBolt>> LICH_BOLT = buildNoEgg(
      TFEntityNames.LICH_BOLT, makeCastedBuilder(LichBolt.class, LichBolt::new, 0.25F, 0.25F, 150, 2), false
   );
   public static final DeferredHolder<EntityType<?>, EntityType<LichBomb>> LICH_BOMB = buildNoEgg(
      TFEntityNames.LICH_BOMB, makeCastedBuilder(LichBomb.class, LichBomb::new, 0.25F, 0.25F, 150, 3), false
   );
   public static final DeferredHolder<EntityType<?>, EntityType<LichMinion>> LICH_MINION = make(
      TFEntityNames.LICH_MINION, LichMinion::new, MobCategory.MONSTER, 0.6F, 1.95F, 0, 0
   );
   public static final DeferredHolder<EntityType<?>, EntityType<LowerGoblinKnight>> LOWER_GOBLIN_KNIGHT = make(
      TFEntityNames.LOWER_GOBLIN_KNIGHT, LowerGoblinKnight::new, MobCategory.MONSTER, 0.7F, 1.1F, 5660757, 13887420
   );
   public static final DeferredHolder<EntityType<?>, EntityType<LoyalZombie>> LOYAL_ZOMBIE = make(
      TFEntityNames.LOYAL_ZOMBIE, LoyalZombie::new, MobCategory.MONSTER, 0.6F, 1.95F, 0, 0, -0.7F
   );
   public static final DeferredHolder<EntityType<?>, EntityType<MazeSlime>> MAZE_SLIME = make(
      TFEntityNames.MAZE_SLIME, MazeSlime::new, MobCategory.MONSTER, 0.52F, 0.52F, 0.325F, false, 10724259, 2767639
   );
   public static final DeferredHolder<EntityType<?>, EntityType<Minoshroom>> MINOSHROOM = make(
      TFEntityNames.MINOSHROOM, Minoshroom::new, MobCategory.MONSTER, 1.49F, 2.5F, 11014162, 11173222
   );
   public static final DeferredHolder<EntityType<?>, EntityType<Minotaur>> MINOTAUR = make(
      TFEntityNames.MINOTAUR, Minotaur::new, MobCategory.MONSTER, 0.6F, 2.1F, 4141092, 11173222
   );
   public static final DeferredHolder<EntityType<?>, EntityType<MistWolf>> MIST_WOLF = make(
      TFEntityNames.MIST_WOLF, MistWolf::new, MobCategory.MONSTER, 1.4F, 1.9F, 1.45F, false, 3806225, 14862474
   );
   public static final DeferredHolder<EntityType<?>, EntityType<MoonwormShot>> MOONWORM_SHOT = buildNoEgg(
      TFEntityNames.MOONWORM_SHOT, makeCastedBuilder(MoonwormShot.class, MoonwormShot::new, 0.25F, 0.25F, 150, 3), false
   );
   public static final DeferredHolder<EntityType<?>, EntityType<MosquitoSwarm>> MOSQUITO_SWARM = make(
      TFEntityNames.MOSQUITO_SWARM, MosquitoSwarm::new, MobCategory.MONSTER, 0.7F, 1.9F, 526596, 2961185
   );
   public static final DeferredHolder<EntityType<?>, EntityType<Naga>> NAGA = make(
      TFEntityNames.NAGA, Naga::new, MobCategory.MONSTER, 2.0F, 3.0F, 2.0F, true, 10801942, 1783819
   );
   public static final DeferredHolder<EntityType<?>, EntityType<NatureBolt>> NATURE_BOLT = buildNoEgg(
      TFEntityNames.NATURE_BOLT, makeCastedBuilder(NatureBolt.class, NatureBolt::new, 0.25F, 0.25F, 150, 5), false
   );
   public static final DeferredHolder<EntityType<?>, EntityType<MagicPainting>> MAGIC_PAINTING = buildNoEgg(
      TFEntityNames.MAGIC_PAINTING, makeCastedBuilder(MagicPainting.class, MagicPainting::new, 0.5F, 0.5F, 10, Integer.MAX_VALUE), false
   );
   public static final DeferredHolder<EntityType<?>, EntityType<Penguin>> PENGUIN = make(
      TFEntityNames.PENGUIN, Penguin::new, MobCategory.CREATURE, 0.5F, 0.9F, 1185051, 16379346
   );
   public static final DeferredHolder<EntityType<?>, EntityType<PinchBeetle>> PINCH_BEETLE = make(
      TFEntityNames.PINCH_BEETLE, PinchBeetle::new, MobCategory.MONSTER, 1.2F, 0.5F, 12358439, 2364937
   );
   public static final DeferredHolder<EntityType<?>, EntityType<PlateauBoss>> PLATEAU_BOSS = make(
      TFEntityNames.PLATEAU_BOSS, PlateauBoss::new, MobCategory.MONSTER, 1.0F, 1.0F, true, 0, 0
   );
   public static final DeferredHolder<EntityType<?>, EntityType<ProtectionBox>> PROTECTION_BOX = buildNoEgg(
      TFEntityNames.PROTECTION_BOX, makeCastedBuilder(ProtectionBox.class, ProtectionBox::new, 0.0F, 0.0F, 80, 3).noSave().noSummon(), true
   );
   public static final DeferredHolder<EntityType<?>, EntityType<QuestRam>> QUEST_RAM = make(
      TFEntityNames.QUEST_RAM, QuestRam::new, MobCategory.CREATURE, 1.25F, 2.9F, 16711406, 3386077
   );
   public static final DeferredHolder<EntityType<?>, EntityType<Raven>> RAVEN = make(
      TFEntityNames.RAVEN, Raven::new, MobCategory.CREATURE, 0.3F, 0.5F, 17, 2236979
   );
   public static final DeferredHolder<EntityType<?>, EntityType<Redcap>> REDCAP = make(
      TFEntityNames.REDCAP, Redcap::new, MobCategory.MONSTER, 0.9F, 1.4F, 3881580, 11214356, -0.4F
   );
   public static final DeferredHolder<EntityType<?>, EntityType<RedcapSapper>> REDCAP_SAPPER = make(
      TFEntityNames.REDCAP_SAPPER, RedcapSapper::new, MobCategory.MONSTER, 0.9F, 1.4F, 5725473, 11214356, -0.4F
   );
   public static final DeferredHolder<EntityType<?>, EntityType<RisingZombie>> RISING_ZOMBIE = buildNoEgg(
      TFEntityNames.RISING_ZOMBIE,
      Builder.of(RisingZombie::new, MobCategory.MONSTER)
         .sized(0.6F, 1.95F)
         .eyeHeight(1.74F)
         .passengerAttachments(new float[]{2.0125F})
         .ridingOffset(-0.7F)
         .clientTrackingRange(16),
      false
   );
   public static final DeferredHolder<EntityType<?>, EntityType<RovingCube>> ROVING_CUBE = make(
      TFEntityNames.ROVING_CUBE, RovingCube::new, MobCategory.MONSTER, 1.2F, 2.1F, 0, 0
   );
   public static final DeferredHolder<EntityType<?>, EntityType<SeekerArrow>> SEEKER_ARROW = buildNoEgg(
      TFEntityNames.SEEKER_ARROW, makeCastedBuilder(SeekerArrow.class, SeekerArrow::new, 0.5F, 0.5F, 150, 1), false
   );
   public static final DeferredHolder<EntityType<?>, EntityType<SkeletonDruid>> SKELETON_DRUID = make(
      TFEntityNames.SKELETON_DRUID, SkeletonDruid::new, MobCategory.MONSTER, 0.6F, 1.99F, 10724259, 2767639, -0.7F
   );
   public static final DeferredHolder<EntityType<?>, EntityType<SlideBlock>> SLIDER = buildNoEgg(
      TFEntityNames.SLIDER, makeCastedBuilder(SlideBlock.class, SlideBlock::new, 0.98F, 0.98F, 80, 1), false
   );
   public static final DeferredHolder<EntityType<?>, EntityType<SlimeBeetle>> SLIME_BEETLE = make(
      TFEntityNames.SLIME_BEETLE, SlimeBeetle::new, MobCategory.MONSTER, 0.9F, 0.5F, 792070, 6334284
   );
   public static final DeferredHolder<EntityType<?>, EntityType<SlimeProjectile>> SLIME_BLOB = buildNoEgg(
      TFEntityNames.SLIME_BLOB, makeCastedBuilder(SlimeProjectile.class, SlimeProjectile::new, 0.25F, 0.25F, 150, 3), false
   );
   public static final DeferredHolder<EntityType<?>, EntityType<SnowGuardian>> SNOW_GUARDIAN = make(
      TFEntityNames.SNOW_GUARDIAN, SnowGuardian::new, MobCategory.MONSTER, 0.6F, 1.8F, 13887420, 16711422
   );
   public static final DeferredHolder<EntityType<?>, EntityType<SnowQueen>> SNOW_QUEEN = make(
      TFEntityNames.SNOW_QUEEN, SnowQueen::new, MobCategory.MONSTER, 0.7F, 2.5F, 11645652, 8847470
   );
   public static final DeferredHolder<EntityType<?>, EntityType<Squirrel>> SQUIRREL = make(
      TFEntityNames.SQUIRREL, Squirrel::new, MobCategory.CREATURE, 0.3F, 0.5F, 9457426, 15658734
   );
   public static final DeferredHolder<EntityType<?>, EntityType<StableIceCore>> STABLE_ICE_CORE = make(
      TFEntityNames.STABLE_ICE_CORE, StableIceCore::new, MobCategory.MONSTER, 0.8F, 1.8F, 1.35F, false, 10600435, 7340280
   );
   public static final DeferredHolder<EntityType<?>, EntityType<SwarmSpider>> SWARM_SPIDER = make(
      TFEntityNames.SWARM_SPIDER, SwarmSpider::new, MobCategory.MONSTER, 0.8F, 0.4F, 3277358, 1516830
   );
   public static final DeferredHolder<EntityType<?>, EntityType<ThrownBlock>> THROWN_BLOCK = buildNoEgg(
      TFEntityNames.THROWN_BLOCK, makeCastedBuilder(ThrownBlock.class, ThrownBlock::new, 1.0F, 1.0F, 80, 2), true
   );
   public static final DeferredHolder<EntityType<?>, EntityType<ThrownWep>> THROWN_WEP = make(
      TFEntityNames.THROWN_WEP, ThrownWep::new, MobCategory.MISC, 0.5F, 0.5F, 0, 0
   );
   public static final DeferredHolder<EntityType<?>, EntityType<TinyBird>> TINY_BIRD = make(
      TFEntityNames.TINY_BIRD, TinyBird::new, MobCategory.CREATURE, 0.3F, 0.3F, 3386077, 1149166
   );
   public static final DeferredHolder<EntityType<?>, EntityType<TomeBolt>> TOME_BOLT = buildNoEgg(
      TFEntityNames.TOME_BOLT, makeCastedBuilder(TomeBolt.class, TomeBolt::new, 0.25F, 0.25F, 150, 5), false
   );
   public static final DeferredHolder<EntityType<?>, EntityType<TowerwoodBorer>> TOWERWOOD_BORER = make(
      TFEntityNames.TOWERWOOD_BORER, TowerwoodBorer::new, MobCategory.MONSTER, 0.4F, 0.3F, 6105889, 11313210
   );
   public static final DeferredHolder<EntityType<?>, EntityType<Troll>> TROLL = make(
      TFEntityNames.TROLL, Troll::new, MobCategory.MONSTER, 1.4F, 2.4F, 10398095, 11572366
   );
   public static final DeferredHolder<EntityType<?>, EntityType<TwilightWandBolt>> WAND_BOLT = buildNoEgg(
      TFEntityNames.WAND_BOLT, makeCastedBuilder(TwilightWandBolt.class, TwilightWandBolt::new, 0.25F, 0.25F, 150, 5), false
   );
   public static final DeferredHolder<EntityType<?>, EntityType<UnstableIceCore>> UNSTABLE_ICE_CORE = make(
      TFEntityNames.UNSTABLE_ICE_CORE, UnstableIceCore::new, MobCategory.MONSTER, 0.8F, 1.8F, 1.35F, false, 10136821, 10162085
   );
   public static final DeferredHolder<EntityType<?>, EntityType<UpperGoblinKnight>> UPPER_GOBLIN_KNIGHT = make(
      TFEntityNames.UPPER_GOBLIN_KNIGHT, UpperGoblinKnight::new, MobCategory.MONSTER, 1.1F, 1.3F, 0, 0
   );
   public static final DeferredHolder<EntityType<?>, EntityType<UrGhast>> UR_GHAST = make(
      TFEntityNames.UR_GHAST, UrGhast::new, MobCategory.MONSTER, 14.0F, 18.0F, true, 12369084, 12023928
   );
   public static final DeferredHolder<EntityType<?>, EntityType<WinterWolf>> WINTER_WOLF = make(
      TFEntityNames.WINTER_WOLF, WinterWolf::new, MobCategory.MONSTER, 1.4F, 1.9F, 1.45F, false, 14672869, 11713738
   );
   public static final DeferredHolder<EntityType<?>, EntityType<Wraith>> WRAITH = make(
      TFEntityNames.WRAITH, Wraith::new, MobCategory.MONSTER, 0.6F, 2.1F, true, 5263440, 8618883
   );
   public static final DeferredHolder<EntityType<?>, EntityType<Yeti>> YETI = make(
      TFEntityNames.YETI, Yeti::new, MobCategory.MONSTER, 1.4F, 2.4F, 14606046, 4617659
   );

   private static <E extends Entity> DeferredHolder<EntityType<?>, EntityType<E>> make(
      ResourceLocation id, EntityFactory<E> factory, MobCategory classification, float width, float height, int primary, int secondary
   ) {
      return make(id, factory, classification, width, height, primary, secondary, 0.0F);
   }

   private static <E extends Entity> DeferredHolder<EntityType<?>, EntityType<E>> make(
      ResourceLocation id, EntityFactory<E> factory, MobCategory classification, float width, float height, int primary, int secondary, float ridingOffset
   ) {
      return make(id, factory, classification, width, height, false, primary, secondary, ridingOffset);
   }

   private static <E extends Entity> DeferredHolder<EntityType<?>, EntityType<E>> make(
      ResourceLocation id, EntityFactory<E> factory, MobCategory classification, float width, float height, boolean fireproof, int primary, int secondary
   ) {
      return make(id, factory, classification, width, height, fireproof, primary, secondary, 0.0F);
   }

   private static <E extends Entity> DeferredHolder<EntityType<?>, EntityType<E>> make(
      ResourceLocation id,
      EntityFactory<E> factory,
      MobCategory classification,
      float width,
      float height,
      boolean fireproof,
      int primary,
      int secondary,
      float ridingOffset
   ) {
      return build(id, makeBuilder(factory, classification, width, height, 80, 3, ridingOffset), fireproof, primary, secondary);
   }

   private static <E extends Entity> DeferredHolder<EntityType<?>, EntityType<E>> make(
      ResourceLocation id,
      EntityFactory<E> factory,
      MobCategory classification,
      float width,
      float height,
      float eyeHeight,
      boolean fireproof,
      int primary,
      int secondary
   ) {
      return make(id, factory, classification, width, height, eyeHeight, fireproof, primary, secondary, 0.0F);
   }

   private static <E extends Entity> DeferredHolder<EntityType<?>, EntityType<E>> make(
      ResourceLocation id,
      EntityFactory<E> factory,
      MobCategory classification,
      float width,
      float height,
      float eyeHeight,
      boolean fireproof,
      int primary,
      int secondary,
      float ridingOffset
   ) {
      return build(id, makeBuilder(factory, classification, width, height, 80, 3, ridingOffset).eyeHeight(eyeHeight), fireproof, primary, secondary);
   }

   private static <E extends Entity> DeferredHolder<EntityType<?>, EntityType<E>> buildNoEgg(ResourceLocation id, Builder<E> builder, boolean fireproof) {
      if (fireproof) {
         builder.fireImmune();
      }

      return ENTITIES.register(id.getPath(), () -> builder.build(id.toString()));
   }

   private static <E extends Entity> DeferredHolder<EntityType<?>, EntityType<E>> build(
      ResourceLocation id, Builder<E> builder, boolean fireproof, int primary, int secondary
   ) {
      if (fireproof) {
         builder.fireImmune();
      }

      DeferredHolder<EntityType<?>, EntityType<E>> ret = ENTITIES.register(id.getPath(), () -> builder.build(id.toString()));
      if (primary != 0 && secondary != 0) {
         SPAWN_EGGS.register(id.getPath() + "_spawn_egg", () -> new DeferredSpawnEggItem(() -> (EntityType)ret.get(), primary, secondary, new Properties()));
      }

      return ret;
   }

   private static <E extends Entity> Builder<E> makeCastedBuilder(Class<E> cast, EntityFactory<E> factory, float width, float height, int range, int interval) {
      return makeBuilder(factory, MobCategory.MISC, width, height, range, interval, 0.0F);
   }

   private static <E extends Entity> Builder<E> makeBuilder(
      EntityFactory<E> factory, MobCategory classification, float width, float height, int range, int interval, float ridingOffset
   ) {
      return Builder.of(factory, classification)
         .sized(width, height)
         .setTrackingRange(range)
         .setUpdateInterval(interval)
         .setShouldReceiveVelocityUpdates(true)
         .ridingOffset(ridingOffset);
   }
}
