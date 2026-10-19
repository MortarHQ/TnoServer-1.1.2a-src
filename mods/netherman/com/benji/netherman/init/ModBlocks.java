package com.benji.netherman.init;

import com.benji.netherman.NetherExp;
import com.benji.netherman.common.block.AltarBlock;
import com.benji.netherman.common.block.AzazelTrophyBlock;
import com.benji.netherman.common.block.BlackstoneAxonBlock;
import com.benji.netherman.common.block.BlackstoneColumnBlock;
import com.benji.netherman.common.block.BlackstonePlantBlock;
import com.benji.netherman.common.block.CrimsonHoneyBlock;
import com.benji.netherman.common.block.CrimsonWebBlock;
import com.benji.netherman.common.block.EntranceBlock;
import com.benji.netherman.common.block.EyeBlock;
import com.benji.netherman.common.block.FacePuzzleBlock;
import com.benji.netherman.common.block.GhastlyNestBlock;
import com.benji.netherman.common.block.GrandDoorBlock;
import com.benji.netherman.common.block.GrandDoorPartBlock;
import com.benji.netherman.common.block.LabyrinthTeleportBlock;
import com.benji.netherman.common.block.LockerNetherBlock;
import com.benji.netherman.common.block.MazeDoorBlock;
import com.benji.netherman.common.block.MosaicChurchBlock;
import com.benji.netherman.common.block.NetherSpawnerBlock;
import com.benji.netherman.common.block.PaintingSpawnerBlock;
import com.benji.netherman.common.block.PointedBlackstoneBlock;
import com.benji.netherman.common.block.PotentMagmaBlock;
import com.benji.netherman.common.block.SamsonitEyeBlock;
import com.benji.netherman.common.block.SamsoniteBellBlock;
import com.benji.netherman.common.block.StatueStandBlock;
import com.benji.netherman.common.block.TotemusBlock;
import com.benji.netherman.common.block.TotemusHoleBlock;
import com.benji.netherman.common.block.TraphiveBlock;
import com.benji.netherman.common.block.VoidCaveCornerBlock;
import com.benji.netherman.common.block.VoidCaveMidBlock;
import com.benji.netherman.common.block.VoidCaveMidCornerBlock;
import com.benji.netherman.common.block.VoidCornerBlock;
import com.benji.netherman.common.block.VoidMidBlock;
import com.benji.netherman.common.block.VoidMidCornerBlock;
import com.benji.netherman.common.block.VoidNetherCornerBlock;
import com.benji.netherman.common.block.VoidNetherMidBlock;
import com.benji.netherman.common.block.VoidNetherMidCornerBlock;
import com.benji.netherman.common.item.AzazelTrophyItem;
import com.benji.netherman.common.item.GeoBlockItem;
import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.util.DeferredSoundType;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredRegister.Blocks;

public class ModBlocks {
   public static final Blocks BLOCKS = DeferredRegister.createBlocks("netherman");
   public static final DeferredSoundType SAMSONIT_SOUNDS = new DeferredSoundType(
      1.0F, 1.0F, ModSounds.SAMSONIT_BREAK, ModSounds.SAMSONIT_STEP, ModSounds.SAMSONIT_PLACE, ModSounds.SAMSONIT_HIT, ModSounds.SAMSONIT_STEP
   );
   public static final DeferredSoundType SAMSONIT_BRICKS_SOUNDS = new DeferredSoundType(
      1.0F,
      1.0F,
      ModSounds.SAMSONIT_BREAK,
      ModSounds.SAMSONIT_BRICKS_STEP,
      ModSounds.SAMSONIT_BRICKS_PLACE,
      ModSounds.SAMSONIT_HIT,
      ModSounds.SAMSONIT_BRICKS_STEP
   );
   public static final DeferredBlock<Block> ANGEL_SPAWNER = BLOCKS.register(
      "angel_spawner",
      () -> new PaintingSpawnerBlock(
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.STRUCTURE_VOID).noCollission().noLootTable(), ModPaintings.ANGEL_PAINTING
      )
   );
   public static final DeferredBlock<Block> FACE_SPAWNER = BLOCKS.register(
      "face_spawner",
      () -> new PaintingSpawnerBlock(
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.STRUCTURE_VOID).noCollission().noLootTable(), ModPaintings.FACE_PAINTING
      )
   );
   public static final DeferredBlock<Block> VILLAGE_SPAWNER = BLOCKS.register(
      "village_spawner",
      () -> new PaintingSpawnerBlock(
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.STRUCTURE_VOID).noCollission().noLootTable(), ModPaintings.VILLAGE_PAINTING
      )
   );
   public static final DeferredBlock<Block> KING_SPAWNER = BLOCKS.register(
      "king_spawner",
      () -> new PaintingSpawnerBlock(
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.STRUCTURE_VOID).noCollission().noLootTable(), ModPaintings.KING_PAINTING
      )
   );
   public static final DeferredBlock<Block> THRONE_SPAWNER = BLOCKS.register(
      "throne_spawner",
      () -> new PaintingSpawnerBlock(
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.STRUCTURE_VOID).noCollission().noLootTable(), ModPaintings.THRONE_PAINTING
      )
   );
   public static final DeferredBlock<Block> A_PUZZLE = register(
      "a_puzzle",
      () -> new Block(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.TUFF).strength(6.0F).requiresCorrectToolForDrops().sound(SAMSONIT_SOUNDS)),
      new net.minecraft.world.item.Item.Properties()
   );
   public static final DeferredBlock<Block> Z_PUZZLE = register(
      "z_puzzle",
      () -> new Block(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.TUFF).strength(6.0F).requiresCorrectToolForDrops().sound(SAMSONIT_SOUNDS)),
      new net.minecraft.world.item.Item.Properties()
   );
   public static final DeferredBlock<Block> E_PUZZLE = register(
      "e_puzzle",
      () -> new Block(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.TUFF).strength(6.0F).requiresCorrectToolForDrops().sound(SAMSONIT_SOUNDS)),
      new net.minecraft.world.item.Item.Properties()
   );
   public static final DeferredBlock<Block> L_PUZZLE = register(
      "l_puzzle",
      () -> new Block(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.TUFF).strength(6.0F).requiresCorrectToolForDrops().sound(SAMSONIT_SOUNDS)),
      new net.minecraft.world.item.Item.Properties()
   );
   public static final DeferredBlock<Block> SAMSONIT = register(
      "samsonit",
      () -> new Block(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.TUFF).strength(6.0F).requiresCorrectToolForDrops().sound(SAMSONIT_SOUNDS)),
      new net.minecraft.world.item.Item.Properties()
   );
   public static final DeferredBlock<Block> COBBLED_SAMSONIT = register(
      "cobbled_samsonit",
      () -> new Block(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.TUFF).strength(6.0F).requiresCorrectToolForDrops().sound(SAMSONIT_SOUNDS)),
      new net.minecraft.world.item.Item.Properties()
   );
   public static final DeferredBlock<Block> SAMSONIT_BRICKS = register(
      "samsonit_bricks",
      () -> new Block(
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.TUFF).strength(6.0F).requiresCorrectToolForDrops().sound(SAMSONIT_BRICKS_SOUNDS)
      ),
      new net.minecraft.world.item.Item.Properties()
   );
   public static final DeferredBlock<Block> POLISHED_SAMSONIT = register(
      "polished_samsonit",
      () -> new Block(
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.TUFF).strength(6.0F).requiresCorrectToolForDrops().sound(SAMSONIT_BRICKS_SOUNDS)
      ),
      new net.minecraft.world.item.Item.Properties()
   );
   public static final DeferredBlock<Block> SAMSONIT_TILES = register(
      "samsonit_tiles",
      () -> new Block(
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.TUFF).strength(6.0F).requiresCorrectToolForDrops().sound(SAMSONIT_BRICKS_SOUNDS)
      ),
      new net.minecraft.world.item.Item.Properties()
   );
   public static final DeferredBlock<Block> CHISELED_SAMSONIT = register(
      "chiseled_samsonit",
      () -> new Block(
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.TUFF).strength(6.0F).requiresCorrectToolForDrops().sound(SAMSONIT_BRICKS_SOUNDS)
      ),
      new net.minecraft.world.item.Item.Properties()
   );
   public static final DeferredBlock<Block> LABYRINTH_TELEPORT = register(
      "labyrinth_teleport",
      () -> new LabyrinthTeleportBlock(
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.NETHERITE_BLOCK)
            .lightLevel(state -> 15)
            .requiresCorrectToolForDrops()
            .strength(20.0F)
            .noOcclusion()
      ),
      new net.minecraft.world.item.Item.Properties()
   );
   public static final DeferredBlock<Block> COBBLED_SAMSONIT_SLAB = register(
      "cobbled_samsonit_slab",
      () -> new SlabBlock(
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.TUFF).strength(6.0F).requiresCorrectToolForDrops().sound(SAMSONIT_SOUNDS)
      ),
      new net.minecraft.world.item.Item.Properties()
   );
   public static final DeferredBlock<Block> POLISHED_SAMSONIT_SLAB = register(
      "polished_samsonit_slab",
      () -> new SlabBlock(
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.TUFF).strength(6.0F).requiresCorrectToolForDrops().sound(SAMSONIT_BRICKS_SOUNDS)
      ),
      new net.minecraft.world.item.Item.Properties()
   );
   public static final DeferredBlock<Block> SAMSONIT_BRICKS_SLAB = register(
      "samsonit_bricks_slab",
      () -> new SlabBlock(
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.TUFF).strength(6.0F).requiresCorrectToolForDrops().sound(SAMSONIT_BRICKS_SOUNDS)
      ),
      new net.minecraft.world.item.Item.Properties()
   );
   public static final DeferredBlock<Block> COBBLED_SAMSONIT_STAIRS = register(
      "cobbled_samsonit_stairs",
      () -> new StairBlock(
         ((Block)COBBLED_SAMSONIT.get()).defaultBlockState(),
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.TUFF).strength(6.0F).requiresCorrectToolForDrops().sound(SAMSONIT_SOUNDS)
      ),
      new net.minecraft.world.item.Item.Properties()
   );
   public static final DeferredBlock<Block> POLISHED_SAMSONIT_STAIRS = register(
      "polished_samsonit_stairs",
      () -> new StairBlock(
         ((Block)POLISHED_SAMSONIT.get()).defaultBlockState(),
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.TUFF).strength(6.0F).requiresCorrectToolForDrops().sound(SAMSONIT_BRICKS_SOUNDS)
      ),
      new net.minecraft.world.item.Item.Properties()
   );
   public static final DeferredBlock<Block> SAMSONIT_BRICKS_STAIRS = register(
      "samsonit_bricks_stairs",
      () -> new StairBlock(
         ((Block)SAMSONIT_BRICKS.get()).defaultBlockState(),
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.TUFF).strength(6.0F).requiresCorrectToolForDrops().sound(SAMSONIT_BRICKS_SOUNDS)
      ),
      new net.minecraft.world.item.Item.Properties()
   );
   public static final DeferredBlock<Block> COBBLED_SAMSONIT_WALL = register(
      "cobbled_samsonit_wall",
      () -> new WallBlock(
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.TUFF).strength(6.0F).requiresCorrectToolForDrops().sound(SAMSONIT_SOUNDS)
      ),
      new net.minecraft.world.item.Item.Properties()
   );
   public static final DeferredBlock<Block> POLISHED_SAMSONIT_WALL = register(
      "polished_samsonit_wall",
      () -> new WallBlock(
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.TUFF).strength(6.0F).requiresCorrectToolForDrops().sound(SAMSONIT_BRICKS_SOUNDS)
      ),
      new net.minecraft.world.item.Item.Properties()
   );
   public static final DeferredBlock<Block> SAMSONIT_BRICKS_WALL = register(
      "samsonit_bricks_wall",
      () -> new WallBlock(
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.TUFF).strength(6.0F).requiresCorrectToolForDrops().sound(SAMSONIT_BRICKS_SOUNDS)
      ),
      new net.minecraft.world.item.Item.Properties()
   );
   public static final DeferredBlock<Block> NETHER_SPAWNER = register(
      "nether_spawner",
      () -> new NetherSpawnerBlock(
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.NETHERITE_BLOCK)
            .lightLevel(state -> 15)
            .requiresCorrectToolForDrops()
            .strength(2.0F)
            .noOcclusion()
      ),
      new net.minecraft.world.item.Item.Properties()
   );
   public static final DeferredBlock<Block> BLACKSTONE_COLUMN = register(
      "blackstone_column",
      () -> new BlackstoneColumnBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.STONE).strength(2.0F).requiresCorrectToolForDrops()),
      new net.minecraft.world.item.Item.Properties()
   );
   public static final DeferredBlock<Block> POINTED_BLACKSTONE = register(
      "pointed_blackstone",
      () -> new PointedBlackstoneBlock(
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.POINTED_DRIPSTONE).requiresCorrectToolForDrops().strength(1.0F).noOcclusion()
      ),
      new net.minecraft.world.item.Item.Properties()
   );
   public static final DeferredBlock<Block> LOCKER_NETHER = register(
      "locker_nether",
      () -> new LockerNetherBlock(
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.NETHERITE_BLOCK).strength(2.0F).requiresCorrectToolForDrops().noOcclusion()
      ),
      new net.minecraft.world.item.Item.Properties()
   );
   public static final DeferredBlock<Block> SAMSONIT_EYE = register(
      "samsonit_eye",
      () -> new SamsonitEyeBlock(
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.TUFF).strength(6.0F).requiresCorrectToolForDrops().sound(SAMSONIT_SOUNDS)
      ),
      new net.minecraft.world.item.Item.Properties()
   );
   public static final DeferredBlock<Block> SAMSONIT_BELL = register(
      "samsonit_bell",
      () -> new Block(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.TUFF).strength(6.0F).requiresCorrectToolForDrops().sound(SAMSONIT_SOUNDS)),
      new net.minecraft.world.item.Item.Properties()
   );
   public static final DeferredBlock<Block> SAMSONIT_KEY = register(
      "samsonit_key",
      () -> new Block(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.TUFF).strength(6.0F).requiresCorrectToolForDrops().sound(SAMSONIT_SOUNDS)),
      new net.minecraft.world.item.Item.Properties()
   );
   public static final DeferredBlock<Block> BLACKSTONE_PLANT = register(
      "blackstone_plant",
      () -> new BlackstonePlantBlock(
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.GRASS_BLOCK).lightLevel(state -> 10).instabreak().noCollission().noOcclusion()
      ),
      new net.minecraft.world.item.Item.Properties()
   );
   public static final DeferredBlock<Block> BLACKSTONE_AXON = register(
      "blackstone_axon",
      () -> new BlackstoneAxonBlock(
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.GRASS_BLOCK).lightLevel(state -> 10).instabreak().noCollission().noOcclusion()
      ),
      new net.minecraft.world.item.Item.Properties()
   );
   public static final DeferredBlock<Block> POTENT_MAGMA = register(
      "potent_magma",
      () -> new PotentMagmaBlock(
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.STONE).strength(2.0F).lightLevel(state -> 10).requiresCorrectToolForDrops()
      ),
      new net.minecraft.world.item.Item.Properties()
   );
   public static final DeferredBlock<Block> VOIDMID = register(
      "void_mid",
      () -> new VoidMidBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.BEDROCK).strength(20.0F).requiresCorrectToolForDrops().noOcclusion()),
      new net.minecraft.world.item.Item.Properties()
   );
   public static final DeferredBlock<Block> VOIDCORNER = register(
      "void_corner",
      () -> new VoidCornerBlock(
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.BEDROCK).strength(20.0F).requiresCorrectToolForDrops().noOcclusion()
      ),
      new net.minecraft.world.item.Item.Properties()
   );
   public static final DeferredBlock<Block> VOIDMIDCORNER = register(
      "void_midcorner",
      () -> new VoidMidCornerBlock(
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.BEDROCK).strength(20.0F).requiresCorrectToolForDrops().noOcclusion()
      ),
      new net.minecraft.world.item.Item.Properties()
   );
   public static final DeferredBlock<Block> VOIDMID_CAVE = register(
      "void_cave_mid",
      () -> new VoidCaveMidBlock(
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.BEDROCK)
            .strength(20.0F)
            .requiresCorrectToolForDrops()
            .lightLevel(state -> 10)
            .noOcclusion()
      ),
      new net.minecraft.world.item.Item.Properties()
   );
   public static final DeferredBlock<Block> VOIDCORNER_CAVE = register(
      "void_cave_corner",
      () -> new VoidCaveCornerBlock(
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.BEDROCK)
            .strength(20.0F)
            .requiresCorrectToolForDrops()
            .lightLevel(state -> 10)
            .noOcclusion()
      ),
      new net.minecraft.world.item.Item.Properties()
   );
   public static final DeferredBlock<Block> VOIDMIDCORNER_CAVE = register(
      "void_cave_midcorner",
      () -> new VoidCaveMidCornerBlock(
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.BEDROCK)
            .strength(20.0F)
            .requiresCorrectToolForDrops()
            .lightLevel(state -> 10)
            .noOcclusion()
      ),
      new net.minecraft.world.item.Item.Properties()
   );
   public static final DeferredBlock<Block> VOIDMIDNETHER = register(
      "voidnether_mid",
      () -> new VoidNetherMidBlock(
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.BEDROCK).strength(20.0F).requiresCorrectToolForDrops().noOcclusion()
      ),
      new net.minecraft.world.item.Item.Properties()
   );
   public static final DeferredBlock<Block> VOIDCORNERNETHER = register(
      "voidnether_corner",
      () -> new VoidNetherCornerBlock(
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.BEDROCK).strength(20.0F).requiresCorrectToolForDrops().noOcclusion()
      ),
      new net.minecraft.world.item.Item.Properties()
   );
   public static final DeferredBlock<Block> VOIDMIDCORNERNETHER = register(
      "voidnether_midcorner",
      () -> new VoidNetherMidCornerBlock(
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.BEDROCK).strength(20.0F).requiresCorrectToolForDrops().noOcclusion()
      ),
      new net.minecraft.world.item.Item.Properties()
   );
   public static final DeferredBlock<Block> ENTRANCE = register(
      "entrance",
      () -> new EntranceBlock(
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.NETHER_WART_BLOCK).strength(20.0F).requiresCorrectToolForDrops().noOcclusion()
      ),
      new net.minecraft.world.item.Item.Properties()
   );
   public static final DeferredBlock<Block> CRIMSON_WEB = registerSpecialItem(
      "crimson_web",
      () -> new CrimsonWebBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.NETHER_WART_BLOCK).instabreak().noOcclusion()),
      web -> () -> new GeoBlockItem(
         (Block)web.get(),
         new net.minecraft.world.item.Item.Properties(),
         NetherExp.location("geo/crimson_web.geo.json"),
         NetherExp.location("textures/block/crimson_web.png"),
         NetherExp.location("animations/crimson_web.animation.json"),
         NetherExp.location("textures/block/blackstone_column_emissive.png")
      )
   );
   public static final DeferredBlock<Block> TRAPHIVE = registerSpecialItem(
      "traphive",
      () -> new TraphiveBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.NETHER_WART_BLOCK).instabreak().noOcclusion()),
      hive -> () -> new GeoBlockItem(
         (Block)hive.get(),
         new net.minecraft.world.item.Item.Properties(),
         NetherExp.location("geo/traphive.geo.json"),
         NetherExp.location("textures/block/traphive.png"),
         NetherExp.location("animations/traphive.animation.json"),
         NetherExp.location("textures/block/blackstone_column_emissive.png")
      )
   );
   public static final DeferredBlock<Block> STATUE_STAND = registerSpecialItem(
      "statue_stand",
      () -> new StatueStandBlock(
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.NETHER_BRICKS).strength(5.0F).requiresCorrectToolForDrops().noOcclusion()
      ),
      stand -> () -> new DoubleHighBlockItem((Block)stand.get(), new net.minecraft.world.item.Item.Properties())
   );
   public static final DeferredBlock<Block> TOTEMUS = register(
      "totemus",
      () -> new TotemusBlock(
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.NETHER_BRICKS).strength(5.0F).requiresCorrectToolForDrops().noOcclusion()
      ),
      new net.minecraft.world.item.Item.Properties()
   );
   public static final DeferredBlock<Block> EYE = registerSpecialItem(
      "eye_block",
      () -> new EyeBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.HONEY_BLOCK).strength(5.0F).requiresCorrectToolForDrops().noOcclusion()),
      eye -> () -> new GeoBlockItem(
         (Block)eye.get(),
         new net.minecraft.world.item.Item.Properties(),
         NetherExp.location("geo/eye_block.geo.json"),
         NetherExp.location("textures/block/eye_block.png"),
         NetherExp.location("animations/eye_block.animation.json"),
         NetherExp.location("textures/block/eye_block_emissive.png")
      )
   );
   public static final DeferredBlock<Block> ALTAR = registerSpecialItem(
      "altar",
      () -> new AltarBlock(
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.DEEPSLATE)
            .lightLevel(state -> state.getValue(AltarBlock.LIT) ? 15 : 0)
            .strength(10.0F)
            .requiresCorrectToolForDrops()
            .noOcclusion()
      ),
      altar -> () -> new GeoBlockItem(
         (Block)altar.get(),
         new net.minecraft.world.item.Item.Properties(),
         NetherExp.location("geo/altar.geo.json"),
         NetherExp.location("textures/block/altar.png"),
         NetherExp.location("animations/altar.animation.json"),
         NetherExp.location("textures/block/altar_emissive.png")
      )
   );
   public static final DeferredBlock<Block> MOSAIC_CHURCH = register(
      "mosaic_church",
      () -> new MosaicChurchBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.GLASS).lightLevel(state -> 10).instabreak().noOcclusion()),
      new net.minecraft.world.item.Item.Properties()
   );
   public static final DeferredBlock<Block> GRAND_DOOR = registerSpecialItem(
      "grand_door",
      () -> new GrandDoorBlock(
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.POLISHED_BLACKSTONE_BRICKS).strength(20.0F).requiresCorrectToolForDrops().noOcclusion()
      ),
      door -> () -> new GeoBlockItem(
         (Block)door.get(),
         new net.minecraft.world.item.Item.Properties(),
         NetherExp.location("geo/grand_door.geo.json"),
         NetherExp.location("textures/block/grand_door.png"),
         NetherExp.location("animations/grand_door.animation.json"),
         NetherExp.location("textures/block/grand_door_emissive.png")
      )
   );
   public static final DeferredBlock<Block> GRAND_DOOR_PART = registerNoItem(
      "grand_door_part",
      () -> new GrandDoorPartBlock(
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.POLISHED_BLACKSTONE_BRICKS)
            .strength(20.0F)
            .requiresCorrectToolForDrops()
            .noOcclusion()
            .noLootTable()
      )
   );
   public static final DeferredBlock<Block> CRIMSON_HONEY_BLOCK = register(
      "crimson_honey_block",
      () -> new CrimsonHoneyBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.HONEY_BLOCK).instabreak().lightLevel(state -> 5).noOcclusion()),
      new net.minecraft.world.item.Item.Properties()
   );
   public static final DeferredBlock<Block> AZAZEL_TROPHY = registerSpecialItem(
      "azazel_trophy",
      () -> new AzazelTrophyBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.GOLD_BLOCK).noOcclusion()),
      trophy -> () -> new AzazelTrophyItem((Block)trophy.get(), new net.minecraft.world.item.Item.Properties())
   );
   public static final DeferredBlock<Block> AZAZEL_TROPHY_STAGE2 = registerSpecialItem(
      "azazel_trophy_stage2",
      () -> new AzazelTrophyBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.GOLD_BLOCK).noOcclusion()),
      trophy -> () -> new AzazelTrophyItem((Block)trophy.get(), new net.minecraft.world.item.Item.Properties())
   );
   public static final DeferredBlock<Block> AZAZEL_TROPHY_STAGE3 = registerSpecialItem(
      "azazel_trophy_stage3",
      () -> new AzazelTrophyBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.GOLD_BLOCK).noOcclusion()),
      trophy -> () -> new AzazelTrophyItem((Block)trophy.get(), new net.minecraft.world.item.Item.Properties())
   );
   public static final DeferredBlock<Block> GHASTLY_NEST = register(
      "ghastly_nest",
      () -> new GhastlyNestBlock(
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.BEEHIVE).strength(2.0F).requiresCorrectToolForDrops().noOcclusion()
      ),
      new net.minecraft.world.item.Item.Properties()
   );
   public static final DeferredBlock<Block> FACE_PUZZLE_RIGHT_DOWN = register(
      "face_puzzle_right_down",
      () -> new FacePuzzleBlock(
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.STONE).strength(2.0F).requiresCorrectToolForDrops().noOcclusion(),
         2,
         ModBlockEntities.FACE_PUZZLE_RIGHT_DOWN
      ),
      new net.minecraft.world.item.Item.Properties()
   );
   public static final DeferredBlock<Block> FACE_PUZZLE_LEFT_UP = register(
      "face_puzzle_left_up",
      () -> new FacePuzzleBlock(
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.STONE).strength(2.0F).requiresCorrectToolForDrops().noOcclusion(),
         2,
         ModBlockEntities.FACE_PUZZLE_LEFT_UP
      ),
      new net.minecraft.world.item.Item.Properties()
   );
   public static final DeferredBlock<Block> FACE_PUZZLE_RIGHT_UP = register(
      "face_puzzle_right_up",
      () -> new FacePuzzleBlock(
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.STONE).strength(2.0F).requiresCorrectToolForDrops().noOcclusion(),
         3,
         ModBlockEntities.FACE_PUZZLE_RIGHT_UP
      ),
      new net.minecraft.world.item.Item.Properties()
   );
   public static final DeferredBlock<Block> FACE_PUZZLE_LEFT_DOWN = register(
      "face_puzzle_left_down",
      () -> new FacePuzzleBlock(
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.STONE).strength(2.0F).requiresCorrectToolForDrops().noOcclusion(),
         3,
         ModBlockEntities.FACE_PUZZLE_LEFT_DOWN
      ),
      new net.minecraft.world.item.Item.Properties()
   );
   public static final DeferredBlock<Block> SAMSONITE_BELL = register(
      "samsonite_bell",
      () -> new SamsoniteBellBlock(
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.BELL).strength(5.0F).requiresCorrectToolForDrops().noOcclusion()
      ),
      new net.minecraft.world.item.Item.Properties()
   );
   public static final DeferredBlock<Block> LABYRINTH_BELLSPAWN = register(
      "labyrinth_bellspawn",
      () -> new Block(
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.NETHERITE_BLOCK)
            .lightLevel(state -> 15)
            .requiresCorrectToolForDrops()
            .strength(20.0F)
            .noOcclusion()
      ),
      new net.minecraft.world.item.Item.Properties()
   );
   public static final DeferredBlock<Block> MAZE_DOOR = registerSpecialItem(
      "maze_door",
      () -> new MazeDoorBlock(
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.POLISHED_BLACKSTONE_BRICKS).strength(20.0F).requiresCorrectToolForDrops().noOcclusion()
      ),
      door -> () -> new GeoBlockItem(
         (Block)door.get(),
         new net.minecraft.world.item.Item.Properties(),
         NetherExp.location("geo/maze_door.geo.json"),
         NetherExp.location("textures/block/maze_door.png"),
         NetherExp.location("animations/maze_door.animation.json"),
         NetherExp.location("textures/block/blackstone_column_emissive.png")
      )
   );
   public static final DeferredBlock<Block> TOTEMUS_HOLE = register(
      "totemus_hole",
      () -> new TotemusHoleBlock(
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.DEEPSLATE_BRICKS).requiresCorrectToolForDrops().strength(2.0F).noOcclusion()
      ),
      new net.minecraft.world.item.Item.Properties()
   );

   private static <T extends Block> DeferredBlock<T> register(String id, Supplier<T> block, net.minecraft.world.item.Item.Properties pIProp) {
      DeferredBlock<T> toReturn = BLOCKS.register(id.toLowerCase(), block);
      makeBlockItem(toReturn, pIProp);
      return toReturn;
   }

   private static <T extends Block> DeferredBlock<T> registerSpecialItem(
      String id, Supplier<T> block, Function<DeferredBlock<T>, Supplier<? extends BlockItem>> sup
   ) {
      DeferredBlock<T> toReturn = BLOCKS.register(id.toLowerCase(), block);
      makeSpecialBlockItem(toReturn, sup.apply(toReturn));
      return toReturn;
   }

   private static <T extends Block> DeferredBlock<T> registerNoItem(String id, Supplier<T> block) {
      return BLOCKS.register(id.toLowerCase(), block);
   }

   private static <T extends Block> void makeBlockItem(DeferredBlock<T> block, net.minecraft.world.item.Item.Properties pIProp) {
      ModItems.registerBlockItem(block, pIProp);
   }

   private static <T extends Block> void makeSpecialBlockItem(DeferredBlock<T> block, Supplier<? extends BlockItem> sup) {
      ModItems.registerSpecialBlockItem(block, sup);
   }

   public static void init(IEventBus bus) {
      BLOCKS.register(bus);
   }

   public static Collection<DeferredHolder<Block, ? extends Block>> getBlocks() {
      return BLOCKS.getEntries();
   }
}
