package com.github.ysbbbbbb.kaleidoscopecookery.init;

import com.github.ysbbbbbb.kaleidoscopecookery.block.crop.BaseCropBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.block.crop.ChiliCropBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.block.crop.LettuceCropBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.block.crop.RiceCropBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.block.decoration.ChairBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.block.decoration.CookStoolBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.block.decoration.FruitBasketBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.block.decoration.StackableFoodBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.block.decoration.TableBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.block.drink.EmptyCupBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.block.food.FoodBiteThreeByThreeBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.block.kitchen.ChoppingBoardBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.block.kitchen.EnamelBasinBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.block.kitchen.KitchenwareRacksBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.block.kitchen.MillstoneBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.block.kitchen.OilPotBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.block.kitchen.PotBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.block.kitchen.ShawarmaSpitBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.block.kitchen.SteamerBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.block.kitchen.StockpotBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.block.kitchen.StoveBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.block.kitchen.TeapotBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.block.misc.ChiliRistraBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.block.misc.OilBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.block.misc.RecipeBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.block.misc.StrawBlocks;
import com.github.ysbbbbbb.kaleidoscopecookery.block.misc.StrungMushroomsBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.block.misc.TrashCanBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.decoration.ChairBlockEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.decoration.FruitBasketBlockEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.decoration.OilPotBlockEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.decoration.RecipeBlockEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.decoration.TableBlockEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.food.FoodBiteThreeByThreeBlockEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.kitchen.ChoppingBoardBlockEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.kitchen.KitchenwareRacksBlockEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.kitchen.MillstoneBlockEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.kitchen.PotBlockEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.kitchen.ShawarmaSpitBlockEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.kitchen.SteamerBlockEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.kitchen.StockpotBlockEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.kitchen.TeapotBlockEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.misc.TrashCanBlockEntity;
import java.util.function.Supplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;
import net.minecraft.world.phys.shapes.Shapes;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredRegister.Blocks;

public class ModBlocks {
   public static final Blocks BLOCKS = DeferredRegister.createBlocks("kaleidoscope_cookery");
   public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(
      BuiltInRegistries.BLOCK_ENTITY_TYPE, "kaleidoscope_cookery"
   );
   public static DeferredBlock<Block> STOVE = BLOCKS.register("stove", StoveBlock::new);
   public static DeferredBlock<Block> POT = BLOCKS.register("pot", PotBlock::new);
   public static DeferredBlock<Block> STOCKPOT = BLOCKS.register("stockpot", StockpotBlock::new);
   public static DeferredBlock<Block> FRUIT_BASKET = BLOCKS.register("fruit_basket", FruitBasketBlock::new);
   public static DeferredBlock<Block> CHOPPING_BOARD = BLOCKS.register("chopping_board", ChoppingBoardBlock::new);
   public static DeferredBlock<Block> OIL_BLOCK = BLOCKS.register("oil_block", OilBlock::new);
   public static DeferredBlock<Block> ENAMEL_BASIN = BLOCKS.register("enamel_basin", EnamelBasinBlock::new);
   public static DeferredBlock<Block> KITCHENWARE_RACKS = BLOCKS.register("kitchenware_racks", KitchenwareRacksBlock::new);
   public static DeferredBlock<Block> CHILI_RISTRA = BLOCKS.register("chili_ristra", ChiliRistraBlock::new);
   public static DeferredBlock<Block> STRUNG_MUSHROOMS = BLOCKS.register("strung_mushrooms", StrungMushroomsBlock::new);
   public static DeferredBlock<Block> STRAW_BLOCK = BLOCKS.register("straw_block", StrawBlocks::new);
   public static DeferredBlock<Block> SHAWARMA_SPIT = BLOCKS.register("shawarma_spit", ShawarmaSpitBlock::new);
   public static DeferredBlock<Block> MILLSTONE = BLOCKS.register("millstone", MillstoneBlock::new);
   public static DeferredBlock<Block> STEAMER = BLOCKS.register("steamer", SteamerBlock::new);
   public static DeferredBlock<Block> TEAPOT = BLOCKS.register("teapot", TeapotBlock::new);
   public static DeferredBlock<Block> EMPTY_CUP = BLOCKS.register("empty_cup", EmptyCupBlock::new);
   public static DeferredBlock<Block> TRASH_CAN = BLOCKS.register("trash_can", TrashCanBlock::new);
   public static DeferredBlock<Block> OIL_POT = BLOCKS.register("oil_pot", OilPotBlock::new);
   public static DeferredBlock<Block> RECIPE_BLOCK = BLOCKS.register("recipe_block", RecipeBlock::new);
   public static DeferredBlock<Block> TOMATO_CROP = BLOCKS.register("tomato_crop", () -> new BaseCropBlock(ModItems.TOMATO, ModItems.TOMATO_SEED));
   public static DeferredBlock<Block> CHILI_CROP = BLOCKS.register("chili_crop", ChiliCropBlock::new);
   public static DeferredBlock<Block> LETTUCE_CROP = BLOCKS.register("lettuce_crop", LettuceCropBlock::new);
   public static DeferredBlock<Block> RICE_CROP = BLOCKS.register("rice_crop", RiceCropBlock::new);
   public static DeferredBlock<Block> COOK_STOOL_OAK = BLOCKS.register("cook_stool_oak", CookStoolBlock::new);
   public static DeferredBlock<Block> COOK_STOOL_SPRUCE = BLOCKS.register("cook_stool_spruce", CookStoolBlock::new);
   public static DeferredBlock<Block> COOK_STOOL_ACACIA = BLOCKS.register("cook_stool_acacia", CookStoolBlock::new);
   public static DeferredBlock<Block> COOK_STOOL_BAMBOO = BLOCKS.register("cook_stool_bamboo", CookStoolBlock::new);
   public static DeferredBlock<Block> COOK_STOOL_BIRCH = BLOCKS.register("cook_stool_birch", CookStoolBlock::new);
   public static DeferredBlock<Block> COOK_STOOL_CHERRY = BLOCKS.register("cook_stool_cherry", CookStoolBlock::new);
   public static DeferredBlock<Block> COOK_STOOL_CRIMSON = BLOCKS.register("cook_stool_crimson", CookStoolBlock::new);
   public static DeferredBlock<Block> COOK_STOOL_DARK_OAK = BLOCKS.register("cook_stool_dark_oak", CookStoolBlock::new);
   public static DeferredBlock<Block> COOK_STOOL_JUNGLE = BLOCKS.register("cook_stool_jungle", CookStoolBlock::new);
   public static DeferredBlock<Block> COOK_STOOL_MANGROVE = BLOCKS.register("cook_stool_mangrove", CookStoolBlock::new);
   public static DeferredBlock<Block> COOK_STOOL_WARPED = BLOCKS.register("cook_stool_warped", CookStoolBlock::new);
   public static DeferredBlock<Block> CHAIR_OAK = BLOCKS.register("chair_oak", ChairBlock::new);
   public static DeferredBlock<Block> CHAIR_SPRUCE = BLOCKS.register("chair_spruce", ChairBlock::new);
   public static DeferredBlock<Block> CHAIR_ACACIA = BLOCKS.register("chair_acacia", ChairBlock::new);
   public static DeferredBlock<Block> CHAIR_BAMBOO = BLOCKS.register("chair_bamboo", ChairBlock::new);
   public static DeferredBlock<Block> CHAIR_BIRCH = BLOCKS.register("chair_birch", ChairBlock::new);
   public static DeferredBlock<Block> CHAIR_CHERRY = BLOCKS.register("chair_cherry", ChairBlock::new);
   public static DeferredBlock<Block> CHAIR_CRIMSON = BLOCKS.register("chair_crimson", ChairBlock::new);
   public static DeferredBlock<Block> CHAIR_DARK_OAK = BLOCKS.register("chair_dark_oak", ChairBlock::new);
   public static DeferredBlock<Block> CHAIR_JUNGLE = BLOCKS.register("chair_jungle", ChairBlock::new);
   public static DeferredBlock<Block> CHAIR_MANGROVE = BLOCKS.register("chair_mangrove", ChairBlock::new);
   public static DeferredBlock<Block> CHAIR_WARPED = BLOCKS.register("chair_warped", ChairBlock::new);
   public static DeferredBlock<Block> TABLE_OAK = BLOCKS.register("table_oak", TableBlock::new);
   public static DeferredBlock<Block> TABLE_SPRUCE = BLOCKS.register("table_spruce", TableBlock::new);
   public static DeferredBlock<Block> TABLE_ACACIA = BLOCKS.register("table_acacia", TableBlock::new);
   public static DeferredBlock<Block> TABLE_BAMBOO = BLOCKS.register("table_bamboo", TableBlock::new);
   public static DeferredBlock<Block> TABLE_BIRCH = BLOCKS.register("table_birch", TableBlock::new);
   public static DeferredBlock<Block> TABLE_CHERRY = BLOCKS.register("table_cherry", TableBlock::new);
   public static DeferredBlock<Block> TABLE_CRIMSON = BLOCKS.register("table_crimson", TableBlock::new);
   public static DeferredBlock<Block> TABLE_DARK_OAK = BLOCKS.register("table_dark_oak", TableBlock::new);
   public static DeferredBlock<Block> TABLE_JUNGLE = BLOCKS.register("table_jungle", TableBlock::new);
   public static DeferredBlock<Block> TABLE_MANGROVE = BLOCKS.register("table_mangrove", TableBlock::new);
   public static DeferredBlock<Block> TABLE_WARPED = BLOCKS.register("table_warped", TableBlock::new);
   public static DeferredBlock<Block> COLD_CUT_HAM_SLICES = BLOCKS.register(
      "cold_cut_ham_slices", () -> new FoodBiteThreeByThreeBlock(ModFoods.COLD_CUT_HAM_SLICES_BLOCK, 8, null)
   );
   public static DeferredBlock<Block> BAMBOO_TUBE_RICE = BLOCKS.register(
      "bamboo_tube_rice",
      StackableFoodBlock.create()
         .maxCount(4)
         .item(() -> (Item)ModItems.BAMBOO_TUBE_RICE.get())
         .shapes(
            Block.box(4.0, 0.0, 4.0, 12.0, 10.0, 12.0),
            Shapes.or(Block.box(7.0, 0.0, 1.0, 15.0, 10.0, 9.0), Block.box(1.0, 0.0, 7.0, 9.0, 10.0, 15.0)),
            Shapes.or(Block.box(0.0, 0.0, 6.0, 16.0, 10.0, 15.0), Block.box(4.0, 0.0, 0.0, 12.0, 10.0, 15.0)),
            Block.box(0.0, 0.0, 0.0, 16.0, 10.0, 16.0)
         )
         .build()
   );
   public static Supplier<BlockEntityType<PotBlockEntity>> POT_BE = BLOCK_ENTITIES.register(
      "pot", () -> Builder.of(PotBlockEntity::new, new Block[]{(Block)POT.get()}).build(null)
   );
   public static Supplier<BlockEntityType<StockpotBlockEntity>> STOCKPOT_BE = BLOCK_ENTITIES.register(
      "stockpot", () -> Builder.of(StockpotBlockEntity::new, new Block[]{(Block)STOCKPOT.get()}).build(null)
   );
   public static Supplier<BlockEntityType<FruitBasketBlockEntity>> FRUIT_BASKET_BE = BLOCK_ENTITIES.register(
      "fruit_basket", () -> Builder.of(FruitBasketBlockEntity::new, new Block[]{(Block)FRUIT_BASKET.get()}).build(null)
   );
   public static Supplier<BlockEntityType<ChoppingBoardBlockEntity>> CHOPPING_BOARD_BE = BLOCK_ENTITIES.register(
      "chopping_board", () -> Builder.of(ChoppingBoardBlockEntity::new, new Block[]{(Block)CHOPPING_BOARD.get()}).build(null)
   );
   public static Supplier<BlockEntityType<KitchenwareRacksBlockEntity>> KITCHENWARE_RACKS_BE = BLOCK_ENTITIES.register(
      "kitchenware_racks", () -> Builder.of(KitchenwareRacksBlockEntity::new, new Block[]{(Block)KITCHENWARE_RACKS.get()}).build(null)
   );
   public static Supplier<BlockEntityType<ShawarmaSpitBlockEntity>> SHAWARMA_SPIT_BE = BLOCK_ENTITIES.register(
      "shawarma_spit", () -> Builder.of(ShawarmaSpitBlockEntity::new, new Block[]{(Block)SHAWARMA_SPIT.get()}).build(null)
   );
   public static Supplier<BlockEntityType<MillstoneBlockEntity>> MILLSTONE_BE = BLOCK_ENTITIES.register(
      "millstone", () -> Builder.of(MillstoneBlockEntity::new, new Block[]{(Block)MILLSTONE.get()}).build(null)
   );
   public static Supplier<BlockEntityType<RecipeBlockEntity>> RECIPE_BLOCK_BE = BLOCK_ENTITIES.register(
      "recipe_block", () -> Builder.of(RecipeBlockEntity::new, new Block[]{(Block)RECIPE_BLOCK.get()}).build(null)
   );
   public static Supplier<BlockEntityType<SteamerBlockEntity>> STEAMER_BE = BLOCK_ENTITIES.register(
      "steamer", () -> Builder.of(SteamerBlockEntity::new, new Block[]{(Block)STEAMER.get()}).build(null)
   );
   public static Supplier<BlockEntityType<TeapotBlockEntity>> TEAPOT_BE = BLOCK_ENTITIES.register(
      "teapot", () -> Builder.of(TeapotBlockEntity::new, new Block[]{(Block)TEAPOT.get()}).build(null)
   );
   public static Supplier<BlockEntityType<OilPotBlockEntity>> OIL_POT_BE = BLOCK_ENTITIES.register(
      "oil_pot", () -> Builder.of(OilPotBlockEntity::new, new Block[]{(Block)OIL_POT.get()}).build(null)
   );
   public static Supplier<BlockEntityType<TrashCanBlockEntity>> TRASH_CAN_BE = BLOCK_ENTITIES.register(
      "trash_can", () -> Builder.of(TrashCanBlockEntity::new, new Block[]{(Block)TRASH_CAN.get()}).build(null)
   );
   public static Supplier<BlockEntityType<FoodBiteThreeByThreeBlockEntity>> FOOD_BITE_THREE_BY_THREE_BE = BLOCK_ENTITIES.register(
      "food_bite_three_by_three", () -> Builder.of(FoodBiteThreeByThreeBlockEntity::new, new Block[]{(Block)COLD_CUT_HAM_SLICES.get()}).build(null)
   );
   public static Supplier<BlockEntityType<ChairBlockEntity>> CHAIR_BE = BLOCK_ENTITIES.register(
      "chair",
      () -> Builder.of(
            ChairBlockEntity::new,
            new Block[]{
               (Block)CHAIR_OAK.get(),
               (Block)CHAIR_SPRUCE.get(),
               (Block)CHAIR_ACACIA.get(),
               (Block)CHAIR_BAMBOO.get(),
               (Block)CHAIR_BIRCH.get(),
               (Block)CHAIR_CHERRY.get(),
               (Block)CHAIR_CRIMSON.get(),
               (Block)CHAIR_DARK_OAK.get(),
               (Block)CHAIR_JUNGLE.get(),
               (Block)CHAIR_MANGROVE.get(),
               (Block)CHAIR_WARPED.get()
            }
         )
         .build(null)
   );
   public static Supplier<BlockEntityType<TableBlockEntity>> TABLE_BE = BLOCK_ENTITIES.register(
      "table",
      () -> Builder.of(
            TableBlockEntity::new,
            new Block[]{
               (Block)TABLE_OAK.get(),
               (Block)TABLE_SPRUCE.get(),
               (Block)TABLE_ACACIA.get(),
               (Block)TABLE_BAMBOO.get(),
               (Block)TABLE_BIRCH.get(),
               (Block)TABLE_CHERRY.get(),
               (Block)TABLE_CRIMSON.get(),
               (Block)TABLE_DARK_OAK.get(),
               (Block)TABLE_JUNGLE.get(),
               (Block)TABLE_MANGROVE.get(),
               (Block)TABLE_WARPED.get()
            }
         )
         .build(null)
   );
}
