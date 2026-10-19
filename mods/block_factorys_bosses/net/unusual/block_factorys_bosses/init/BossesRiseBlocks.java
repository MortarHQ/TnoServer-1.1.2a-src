package net.unusual.block_factorys_bosses.init;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredRegister.Blocks;
import net.unusual.block_factorys_bosses.block.BigChainBlock;
import net.unusual.block_factorys_bosses.block.BigOakPlanksBlock;
import net.unusual.block_factorys_bosses.block.BoneRemainsBlock;
import net.unusual.block_factorys_bosses.block.BossSpawnerBlock;
import net.unusual.block_factorys_bosses.block.CandlesBlock;
import net.unusual.block_factorys_bosses.block.CannonballBlock;
import net.unusual.block_factorys_bosses.block.CoinPileBlock;
import net.unusual.block_factorys_bosses.block.CorpseBlock;
import net.unusual.block_factorys_bosses.block.DragonBannerBlock;
import net.unusual.block_factorys_bosses.block.DragonSkullBlock;
import net.unusual.block_factorys_bosses.block.FancyWallTorchBlock;
import net.unusual.block_factorys_bosses.block.GuardrailBlock;
import net.unusual.block_factorys_bosses.block.GuardrailDiagonalBlock;
import net.unusual.block_factorys_bosses.block.HugeDoorBlock;
import net.unusual.block_factorys_bosses.block.IceBlockParticulesBlock;
import net.unusual.block_factorys_bosses.block.KrakenSpawnerBlock;
import net.unusual.block_factorys_bosses.block.NetBlock;
import net.unusual.block_factorys_bosses.block.PlankBlock;
import net.unusual.block_factorys_bosses.block.PrisonDoorBlock;
import net.unusual.block_factorys_bosses.block.RopeRollBlock;
import net.unusual.block_factorys_bosses.block.ShipLanternBlock;
import net.unusual.block_factorys_bosses.block.ShipSteeringWheelBlock;
import net.unusual.block_factorys_bosses.block.TallCandlesBlock;
import net.unusual.block_factorys_bosses.block.TallVaseBlock;
import net.unusual.block_factorys_bosses.block.VaseBlock;
import net.unusual.block_factorys_bosses.block.entity.UnderworldArenaDoorBlockEntity;

public class BossesRiseBlocks {
   public static final Blocks REGISTRY = DeferredRegister.createBlocks("block_factorys_bosses");
   public static final DeferredBlock<Block> BIG_OAK_PLANKS = registerBigOakPlanks("big_oak_planks", false);
   public static final DeferredBlock<Block> BIG_OAK_PLANKS_BROKEN = registerBigOakPlanks("big_oak_planks_broken", false);
   public static final DeferredBlock<Block> BIG_OAK_PLANKS_CRACKED = registerBigOakPlanks("big_oak_planks_cracked", false);
   public static final DeferredBlock<Block> BIG_OAK_PLANKS_STRAIGHT = registerBigOakPlanks("big_oak_planks_straight", false);
   public static final DeferredBlock<Block> BIG_OAK_PLANKS_VARIATION = registerBigOakPlanks("big_oak_planks_variation", false);
   public static final DeferredBlock<Block> BIG_OAK_PLANKS_WET = registerBigOakPlanks("big_oak_planks_wet", true);
   public static final DeferredBlock<Block> BIG_OAK_PLANKS_WET_BROKEN = registerBigOakPlanks("big_oak_planks_wet_broken", true);
   public static final DeferredBlock<Block> BIG_OAK_PLANKS_WET_CRACKED = registerBigOakPlanks("big_oak_planks_wet_cracked", true);
   public static final DeferredBlock<Block> BIG_OAK_PLANKS_WET_STRAIGHT = registerBigOakPlanks("big_oak_planks_wet_straight", true);
   public static final DeferredBlock<Block> BIG_OAK_PLANKS_WET_VARIATION = registerBigOakPlanks("big_oak_planks_wet_variation", true);
   public static final DeferredBlock<Block> GUARDRAIL = REGISTRY.register("guardrail", GuardrailBlock::new);
   public static final DeferredBlock<Block> GUARDRAIL_DIAGONAL = REGISTRY.register("guardrail_diagonal", GuardrailDiagonalBlock::new);
   public static final DeferredBlock<CoinPileBlock> COIN_PILE = REGISTRY.register("coin_pile", CoinPileBlock::new);
   public static final DeferredBlock<Block> ROPE_ROLL = REGISTRY.register("rope_roll", RopeRollBlock::new);
   public static final DeferredBlock<Block> SHIP_STEERING_WHEEL = REGISTRY.register("ship_steering_wheel", ShipSteeringWheelBlock::new);
   public static final DeferredBlock<Block> CANNONBALL = REGISTRY.register("cannonball", CannonballBlock::new);
   public static final DeferredBlock<Block> NET = REGISTRY.register("net", NetBlock::new);
   public static final DeferredBlock<Block> VASE = REGISTRY.register("vase", VaseBlock::new);
   public static final DeferredBlock<Block> WALL_TORCH = REGISTRY.register("wall_torch", () -> new FancyWallTorchBlock(() -> ParticleTypes.FLAME));
   public static final DeferredBlock<Block> UNDERWOLD_WALL_TORCH = REGISTRY.register(
      "underwold_wall_torch", () -> new FancyWallTorchBlock(() -> ParticleTypes.SOUL_FIRE_FLAME)
   );
   public static final DeferredBlock<Block> TALL_VASE = REGISTRY.register("tall_vase", TallVaseBlock::new);
   public static final DeferredBlock<Block> CANDLES = REGISTRY.register("candles", () -> new CandlesBlock(() -> ParticleTypes.FLAME));
   public static final DeferredBlock<Block> UNDERWOLD_CANDLES = REGISTRY.register(
      "underwold_candles", () -> new CandlesBlock(() -> ParticleTypes.SOUL_FIRE_FLAME)
   );
   public static final DeferredBlock<Block> TALL_CANDLES = REGISTRY.register(
      "tall_candles", () -> new TallCandlesBlock(() -> ParticleTypes.FLAME, TallCandlesBlock.FLAME_POINTS_TWISTED)
   );
   public static final DeferredBlock<Block> UNDERWORLD_TALL_CANDLES = REGISTRY.register(
      "underworld_tall_candles", () -> new TallCandlesBlock(() -> ParticleTypes.SOUL_FIRE_FLAME, TallCandlesBlock.FLAME_POINTS_TWISTED)
   );
   public static final DeferredBlock<Block> BONE_REMAINS = REGISTRY.register("bone_remains", BoneRemainsBlock::new);
   public static final DeferredBlock<Block> CORPSE = REGISTRY.register("corpse", CorpseBlock::new);
   public static final DeferredBlock<Block> BONE_REMAINS_LEGS = REGISTRY.register("bone_remains_legs", BoneRemainsBlock::new);
   public static final DeferredBlock<Block> BONE_REMAINS_RIB_CAGE = REGISTRY.register("bone_remains_rib_cage", BoneRemainsBlock::new);
   public static final DeferredBlock<Block> TALL_CANDLES_CROSS = REGISTRY.register(
      "tall_candles_cross", () -> new TallCandlesBlock(() -> ParticleTypes.FLAME, TallCandlesBlock.FLAME_POINTS_CROSS)
   );
   public static final DeferredBlock<Block> UNDER_WORLD_TALL_CANDLE_CROSS = REGISTRY.register(
      "under_world_tall_candle_cross", () -> new TallCandlesBlock(() -> ParticleTypes.SOUL_FIRE_FLAME, TallCandlesBlock.FLAME_POINTS_CROSS)
   );
   public static final DeferredBlock<Block> BIG_CHAIN = REGISTRY.register("big_chain", BigChainBlock::new);
   public static final DeferredBlock<Block> PRISON_DOOR = REGISTRY.register("prison_door", PrisonDoorBlock::new);
   public static final DeferredBlock<Block> RUSTY_PRISON_DOOR = REGISTRY.register("rusty_prison_door", PrisonDoorBlock::new);
   public static final DeferredBlock<Block> ICE_BLOCK_PARTICULES = REGISTRY.register("ice_block_particules", IceBlockParticulesBlock::new);
   public static final DeferredBlock<Block> DRAGON_BANNER = REGISTRY.register("dragon_banner", DragonBannerBlock::new);
   public static final DeferredBlock<Block> DRAGON_SKULL = REGISTRY.register("dragon_skull", DragonSkullBlock::new);
   public static final DeferredBlock<Block> BOSS_SPAWNER = REGISTRY.register("boss_spawner", BossSpawnerBlock::new);
   public static final DeferredBlock<Block> KRAKEN_SPAWNER = REGISTRY.register("kraken_spawner", KrakenSpawnerBlock::new);
   public static final DeferredBlock<Block> UNDERWORLD_ARENA_DOOR = REGISTRY.register(
      "underworld_arena_door",
      () -> new HugeDoorBlock(
         Properties.of().destroyTime(40.0F).explosionResistance(400.0F).sound(SoundType.STONE).dynamicShape().noOcclusion().pushReaction(PushReaction.BLOCK),
         stack -> stack.is(BossesRiseItems.UNDERWORLD_ARENA_KEY),
         UnderworldArenaDoorBlockEntity::new
      )
   );
   public static final DeferredBlock<Block> SHIP_LANTERN = REGISTRY.register("ship_lantern", ShipLanternBlock::new);
   public static final DeferredBlock<PlankBlock> PLANK = REGISTRY.register("plank", PlankBlock::new);

   private static DeferredBlock<Block> registerBigOakPlanks(String name, boolean isWet) {
      return REGISTRY.register(
         name,
         () -> new BigOakPlanksBlock(
            Properties.of().destroyTime(2.5F).ignitedByLava().mapColor(MapColor.WOOD).sound(isWet ? SoundType.WET_SPONGE : SoundType.WOOD)
         )
      );
   }
}
