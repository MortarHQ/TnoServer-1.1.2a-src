package twilightforest.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import twilightforest.block.entity.AntibuilderBlockEntity;
import twilightforest.block.entity.BrazierBlockEntity;
import twilightforest.block.entity.CandelabraBlockEntity;
import twilightforest.block.entity.CarminiteBuilderBlockEntity;
import twilightforest.block.entity.CarminiteReactorBlockEntity;
import twilightforest.block.entity.CicadaBlockEntity;
import twilightforest.block.entity.CinderFurnaceBlockEntity;
import twilightforest.block.entity.DryingRackBlockEntity;
import twilightforest.block.entity.FireJetBlockEntity;
import twilightforest.block.entity.FireflyBlockEntity;
import twilightforest.block.entity.GhastTrapBlockEntity;
import twilightforest.block.entity.GrowingBeanstalkBlockEntity;
import twilightforest.block.entity.JarBlockEntity;
import twilightforest.block.entity.KeepsakeCasketBlockEntity;
import twilightforest.block.entity.MasonJarBlockEntity;
import twilightforest.block.entity.MoonwormBlockEntity;
import twilightforest.block.entity.OminousCandleBlockEntity;
import twilightforest.block.entity.ReactorDebrisBlockEntity;
import twilightforest.block.entity.RedThreadBlockEntity;
import twilightforest.block.entity.SkullCandleBlockEntity;
import twilightforest.block.entity.SkullChestBlockEntity;
import twilightforest.block.entity.TFSmokerBlockEntity;
import twilightforest.block.entity.TrophyBlockEntity;
import twilightforest.block.entity.bookshelf.ChiseledCanopyShelfBlockEntity;
import twilightforest.block.entity.spawner.AlphaYetiSpawnerBlockEntity;
import twilightforest.block.entity.spawner.FinalBossSpawnerBlockEntity;
import twilightforest.block.entity.spawner.HydraSpawnerBlockEntity;
import twilightforest.block.entity.spawner.KnightPhantomSpawnerBlockEntity;
import twilightforest.block.entity.spawner.LichSpawnerBlockEntity;
import twilightforest.block.entity.spawner.MinoshroomSpawnerBlockEntity;
import twilightforest.block.entity.spawner.NagaSpawnerBlockEntity;
import twilightforest.block.entity.spawner.SinisterSpawnerBlockEntity;
import twilightforest.block.entity.spawner.SnowQueenSpawnerBlockEntity;
import twilightforest.block.entity.spawner.UrGhastSpawnerBlockEntity;

public class TFBlockEntities {
   public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, "twilightforest");
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AntibuilderBlockEntity>> ANTIBUILDER = BLOCK_ENTITIES.register(
      "antibuilder", () -> Builder.of(AntibuilderBlockEntity::new, new Block[]{(Block)TFBlocks.ANTIBUILDER.get()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CinderFurnaceBlockEntity>> CINDER_FURNACE = BLOCK_ENTITIES.register(
      "cinder_furnace", () -> Builder.of(CinderFurnaceBlockEntity::new, new Block[]{(Block)TFBlocks.CINDER_FURNACE.get()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CarminiteReactorBlockEntity>> CARMINITE_REACTOR = BLOCK_ENTITIES.register(
      "carminite_reactor", () -> Builder.of(CarminiteReactorBlockEntity::new, new Block[]{(Block)TFBlocks.CARMINITE_REACTOR.get()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ReactorDebrisBlockEntity>> REACTOR_DEBRIS = BLOCK_ENTITIES.register(
      "reactor_debris", () -> Builder.of(ReactorDebrisBlockEntity::new, new Block[]{(Block)TFBlocks.REACTOR_DEBRIS.get()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<FireJetBlockEntity>> FLAME_JET = BLOCK_ENTITIES.register(
      "flame_jet", () -> Builder.of(FireJetBlockEntity::new, new Block[]{(Block)TFBlocks.FIRE_JET.get(), (Block)TFBlocks.ENCASED_FIRE_JET.get()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GhastTrapBlockEntity>> GHAST_TRAP = BLOCK_ENTITIES.register(
      "ghast_trap", () -> Builder.of(GhastTrapBlockEntity::new, new Block[]{(Block)TFBlocks.GHAST_TRAP.get()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TFSmokerBlockEntity>> SMOKER = BLOCK_ENTITIES.register(
      "smoker", () -> Builder.of(TFSmokerBlockEntity::new, new Block[]{(Block)TFBlocks.SMOKER.get(), (Block)TFBlocks.ENCASED_SMOKER.get()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CarminiteBuilderBlockEntity>> TOWER_BUILDER = BLOCK_ENTITIES.register(
      "tower_builder", () -> Builder.of(CarminiteBuilderBlockEntity::new, new Block[]{(Block)TFBlocks.CARMINITE_BUILDER.get()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TrophyBlockEntity>> TROPHY = BLOCK_ENTITIES.register(
      "trophy",
      () -> Builder.of(
            TrophyBlockEntity::new,
            new Block[]{
               (Block)TFBlocks.NAGA_TROPHY.get(),
               (Block)TFBlocks.LICH_TROPHY.get(),
               (Block)TFBlocks.MINOSHROOM_TROPHY.get(),
               (Block)TFBlocks.HYDRA_TROPHY.get(),
               (Block)TFBlocks.KNIGHT_PHANTOM_TROPHY.get(),
               (Block)TFBlocks.UR_GHAST_TROPHY.get(),
               (Block)TFBlocks.ALPHA_YETI_TROPHY.get(),
               (Block)TFBlocks.SNOW_QUEEN_TROPHY.get(),
               (Block)TFBlocks.QUEST_RAM_TROPHY.get(),
               (Block)TFBlocks.NAGA_WALL_TROPHY.get(),
               (Block)TFBlocks.LICH_WALL_TROPHY.get(),
               (Block)TFBlocks.MINOSHROOM_WALL_TROPHY.get(),
               (Block)TFBlocks.HYDRA_WALL_TROPHY.get(),
               (Block)TFBlocks.KNIGHT_PHANTOM_WALL_TROPHY.get(),
               (Block)TFBlocks.UR_GHAST_WALL_TROPHY.get(),
               (Block)TFBlocks.ALPHA_YETI_WALL_TROPHY.get(),
               (Block)TFBlocks.SNOW_QUEEN_WALL_TROPHY.get(),
               (Block)TFBlocks.QUEST_RAM_WALL_TROPHY.get()
            }
         )
         .build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AlphaYetiSpawnerBlockEntity>> ALPHA_YETI_SPAWNER = BLOCK_ENTITIES.register(
      "alpha_yeti_spawner", () -> Builder.of(AlphaYetiSpawnerBlockEntity::new, new Block[]{(Block)TFBlocks.ALPHA_YETI_BOSS_SPAWNER.get()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<FinalBossSpawnerBlockEntity>> FINAL_BOSS_SPAWNER = BLOCK_ENTITIES.register(
      "final_boss_spawner", () -> Builder.of(FinalBossSpawnerBlockEntity::new, new Block[]{(Block)TFBlocks.FINAL_BOSS_BOSS_SPAWNER.get()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<HydraSpawnerBlockEntity>> HYDRA_SPAWNER = BLOCK_ENTITIES.register(
      "hydra_boss_spawner", () -> Builder.of(HydraSpawnerBlockEntity::new, new Block[]{(Block)TFBlocks.HYDRA_BOSS_SPAWNER.get()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<KnightPhantomSpawnerBlockEntity>> KNIGHT_PHANTOM_SPAWNER = BLOCK_ENTITIES.register(
      "knight_phantom_spawner",
      () -> Builder.of(KnightPhantomSpawnerBlockEntity::new, new Block[]{(Block)TFBlocks.KNIGHT_PHANTOM_BOSS_SPAWNER.get()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<LichSpawnerBlockEntity>> LICH_SPAWNER = BLOCK_ENTITIES.register(
      "lich_spawner", () -> Builder.of(LichSpawnerBlockEntity::new, new Block[]{(Block)TFBlocks.LICH_BOSS_SPAWNER.get()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MinoshroomSpawnerBlockEntity>> MINOSHROOM_SPAWNER = BLOCK_ENTITIES.register(
      "minoshroom_spawner", () -> Builder.of(MinoshroomSpawnerBlockEntity::new, new Block[]{(Block)TFBlocks.MINOSHROOM_BOSS_SPAWNER.get()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<NagaSpawnerBlockEntity>> NAGA_SPAWNER = BLOCK_ENTITIES.register(
      "naga_spawner", () -> Builder.of(NagaSpawnerBlockEntity::new, new Block[]{(Block)TFBlocks.NAGA_BOSS_SPAWNER.get()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SnowQueenSpawnerBlockEntity>> SNOW_QUEEN_SPAWNER = BLOCK_ENTITIES.register(
      "snow_queen_spawner", () -> Builder.of(SnowQueenSpawnerBlockEntity::new, new Block[]{(Block)TFBlocks.SNOW_QUEEN_BOSS_SPAWNER.get()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<UrGhastSpawnerBlockEntity>> UR_GHAST_SPAWNER = BLOCK_ENTITIES.register(
      "tower_boss_spawner", () -> Builder.of(UrGhastSpawnerBlockEntity::new, new Block[]{(Block)TFBlocks.UR_GHAST_BOSS_SPAWNER.get()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CicadaBlockEntity>> CICADA = BLOCK_ENTITIES.register(
      "cicada", () -> Builder.of(CicadaBlockEntity::new, new Block[]{(Block)TFBlocks.CICADA.get()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<FireflyBlockEntity>> FIREFLY = BLOCK_ENTITIES.register(
      "firefly", () -> Builder.of(FireflyBlockEntity::new, new Block[]{(Block)TFBlocks.FIREFLY.get()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MoonwormBlockEntity>> MOONWORM = BLOCK_ENTITIES.register(
      "moonworm", () -> Builder.of(MoonwormBlockEntity::new, new Block[]{(Block)TFBlocks.MOONWORM.get()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SkullChestBlockEntity>> SKULL_CHEST = BLOCK_ENTITIES.register(
      "skull_chest", () -> Builder.of(SkullChestBlockEntity::new, new Block[]{(Block)TFBlocks.SKULL_CHEST.get()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<KeepsakeCasketBlockEntity>> KEEPSAKE_CASKET = BLOCK_ENTITIES.register(
      "keepsake_casket", () -> Builder.of(KeepsakeCasketBlockEntity::new, new Block[]{(Block)TFBlocks.KEEPSAKE_CASKET.get()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BrazierBlockEntity>> BRAZIER = BLOCK_ENTITIES.register(
      "brazier", () -> Builder.of(BrazierBlockEntity::new, new Block[]{(Block)TFBlocks.BRAZIER.get()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SkullCandleBlockEntity>> SKULL_CANDLE = BLOCK_ENTITIES.register(
      "skull_candle",
      () -> Builder.of(
            SkullCandleBlockEntity::new,
            new Block[]{
               (Block)TFBlocks.ZOMBIE_SKULL_CANDLE.get(),
               (Block)TFBlocks.ZOMBIE_WALL_SKULL_CANDLE.get(),
               (Block)TFBlocks.SKELETON_SKULL_CANDLE.get(),
               (Block)TFBlocks.SKELETON_WALL_SKULL_CANDLE.get(),
               (Block)TFBlocks.WITHER_SKELE_SKULL_CANDLE.get(),
               (Block)TFBlocks.WITHER_SKELE_WALL_SKULL_CANDLE.get(),
               (Block)TFBlocks.CREEPER_SKULL_CANDLE.get(),
               (Block)TFBlocks.CREEPER_WALL_SKULL_CANDLE.get(),
               (Block)TFBlocks.PLAYER_SKULL_CANDLE.get(),
               (Block)TFBlocks.PLAYER_WALL_SKULL_CANDLE.get(),
               (Block)TFBlocks.PIGLIN_SKULL_CANDLE.get(),
               (Block)TFBlocks.PIGLIN_WALL_SKULL_CANDLE.get()
            }
         )
         .build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ChiseledCanopyShelfBlockEntity>> CHISELED_CANOPY_BOOKSHELF = BLOCK_ENTITIES.register(
      "chiseled_canopy_bookshelf",
      () -> Builder.of(ChiseledCanopyShelfBlockEntity::new, new Block[]{(Block)TFBlocks.CHISELED_CANOPY_BOOKSHELF.get()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GrowingBeanstalkBlockEntity>> BEANSTALK_GROWER = BLOCK_ENTITIES.register(
      "beanstalk_grower", () -> Builder.of(GrowingBeanstalkBlockEntity::new, new Block[]{(Block)TFBlocks.BEANSTALK_GROWER.get()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<RedThreadBlockEntity>> RED_THREAD = BLOCK_ENTITIES.register(
      "red_thread", () -> Builder.of(RedThreadBlockEntity::new, new Block[]{(Block)TFBlocks.RED_THREAD.get()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CandelabraBlockEntity>> CANDELABRA = BLOCK_ENTITIES.register(
      "candelabra", () -> Builder.of(CandelabraBlockEntity::new, new Block[]{(Block)TFBlocks.CANDELABRA.get()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<JarBlockEntity>> JAR = BLOCK_ENTITIES.register(
      "jar", () -> Builder.of(JarBlockEntity::new, new Block[]{(Block)TFBlocks.FIREFLY_JAR.get(), (Block)TFBlocks.CICADA_JAR.get()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MasonJarBlockEntity>> MASON_JAR = BLOCK_ENTITIES.register(
      "mason_jar", () -> Builder.of(MasonJarBlockEntity::new, new Block[]{(Block)TFBlocks.MASON_JAR.get()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SinisterSpawnerBlockEntity>> SINISTER_SPAWNER = BLOCK_ENTITIES.register(
      "sinister_spawner", () -> Builder.of(SinisterSpawnerBlockEntity::new, new Block[]{(Block)TFBlocks.SINISTER_SPAWNER.get()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<DryingRackBlockEntity>> DRYING_RACK = BLOCK_ENTITIES.register(
      "drying_rack",
      () -> Builder.of(
            DryingRackBlockEntity::new,
            new Block[]{
               (Block)TFBlocks.OAK_DRYING_RACK.get(),
               (Block)TFBlocks.SPRUCE_DRYING_RACK.get(),
               (Block)TFBlocks.BIRCH_DRYING_RACK.get(),
               (Block)TFBlocks.JUNGLE_DRYING_RACK.get(),
               (Block)TFBlocks.ACACIA_DRYING_RACK.get(),
               (Block)TFBlocks.DARK_OAK_DRYING_RACK.get(),
               (Block)TFBlocks.CRIMSON_DRYING_RACK.get(),
               (Block)TFBlocks.WARPED_DRYING_RACK.get(),
               (Block)TFBlocks.VANGROVE_DRYING_RACK.get(),
               (Block)TFBlocks.BAMBOO_DRYING_RACK.get(),
               (Block)TFBlocks.CHERRY_DRYING_RACK.get(),
               (Block)TFBlocks.TWILIGHT_OAK_DRYING_RACK.get(),
               (Block)TFBlocks.CANOPY_DRYING_RACK.get(),
               (Block)TFBlocks.MANGROVE_DRYING_RACK.get(),
               (Block)TFBlocks.DARK_DRYING_RACK.get(),
               (Block)TFBlocks.TIME_DRYING_RACK.get(),
               (Block)TFBlocks.TRANSFORMATION_DRYING_RACK.get(),
               (Block)TFBlocks.MINING_DRYING_RACK.get(),
               (Block)TFBlocks.SORTING_DRYING_RACK.get()
            }
         )
         .build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<OminousCandleBlockEntity>> OMINOUS_CANDLE = BLOCK_ENTITIES.register(
      "ominous_candle",
      () -> Builder.of(
            OminousCandleBlockEntity::new,
            new Block[]{
               (Block)TFBlocks.OMINOUS_CANDLE.get(),
               (Block)TFBlocks.OMINOUS_WHITE_CANDLE.get(),
               (Block)TFBlocks.OMINOUS_ORANGE_CANDLE.get(),
               (Block)TFBlocks.OMINOUS_MAGENTA_CANDLE.get(),
               (Block)TFBlocks.OMINOUS_LIGHT_BLUE_CANDLE.get(),
               (Block)TFBlocks.OMINOUS_YELLOW_CANDLE.get(),
               (Block)TFBlocks.OMINOUS_LIME_CANDLE.get(),
               (Block)TFBlocks.OMINOUS_PINK_CANDLE.get(),
               (Block)TFBlocks.OMINOUS_GRAY_CANDLE.get(),
               (Block)TFBlocks.OMINOUS_LIGHT_GRAY_CANDLE.get(),
               (Block)TFBlocks.OMINOUS_CYAN_CANDLE.get(),
               (Block)TFBlocks.OMINOUS_PURPLE_CANDLE.get(),
               (Block)TFBlocks.OMINOUS_BLUE_CANDLE.get(),
               (Block)TFBlocks.OMINOUS_BROWN_CANDLE.get(),
               (Block)TFBlocks.OMINOUS_GREEN_CANDLE.get(),
               (Block)TFBlocks.OMINOUS_RED_CANDLE.get(),
               (Block)TFBlocks.OMINOUS_BLACK_CANDLE.get()
            }
         )
         .build(null)
   );
}
