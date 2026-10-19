package twilightforest.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.BlockFamily.Builder;
import net.minecraft.data.BlockFamily.Variant;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import twilightforest.TwilightForestMod;
import twilightforest.init.TFBlocks;

public class TFBlockFamilies {
   public static final BlockFamily TWILIGHT_OAK = familyBuilder((Block)TFBlocks.TWILIGHT_OAK_PLANKS.value())
      .stairs((Block)TFBlocks.TWILIGHT_OAK_STAIRS.value())
      .slab((Block)TFBlocks.TWILIGHT_OAK_SLAB.value())
      .button((Block)TFBlocks.TWILIGHT_OAK_BUTTON.value())
      .fence((Block)TFBlocks.TWILIGHT_OAK_FENCE.value())
      .fenceGate((Block)TFBlocks.TWILIGHT_OAK_GATE.value())
      .pressurePlate((Block)TFBlocks.TWILIGHT_OAK_PLATE.value())
      .door((Block)TFBlocks.TWILIGHT_OAK_DOOR.value())
      .trapdoor((Block)TFBlocks.TWILIGHT_OAK_TRAPDOOR.value())
      .sign((Block)TFBlocks.TWILIGHT_OAK_SIGN.value(), (Block)TFBlocks.TWILIGHT_WALL_SIGN.value())
      .recipeGroupPrefix("wooden")
      .recipeUnlockedBy("has_planks")
      .getFamily();
   public static final BlockFamily CANOPY = familyBuilder((Block)TFBlocks.CANOPY_PLANKS.value())
      .stairs((Block)TFBlocks.CANOPY_STAIRS.value())
      .slab((Block)TFBlocks.CANOPY_SLAB.value())
      .button((Block)TFBlocks.CANOPY_BUTTON.value())
      .fence((Block)TFBlocks.CANOPY_FENCE.value())
      .fenceGate((Block)TFBlocks.CANOPY_GATE.value())
      .pressurePlate((Block)TFBlocks.CANOPY_PLATE.value())
      .door((Block)TFBlocks.CANOPY_DOOR.value())
      .trapdoor((Block)TFBlocks.CANOPY_TRAPDOOR.value())
      .sign((Block)TFBlocks.CANOPY_SIGN.value(), (Block)TFBlocks.CANOPY_WALL_SIGN.value())
      .recipeGroupPrefix("wooden")
      .recipeUnlockedBy("has_planks")
      .getFamily();
   public static final BlockFamily MANGROVE = familyBuilder((Block)TFBlocks.MANGROVE_PLANKS.value())
      .stairs((Block)TFBlocks.MANGROVE_STAIRS.value())
      .slab((Block)TFBlocks.MANGROVE_SLAB.value())
      .button((Block)TFBlocks.MANGROVE_BUTTON.value())
      .fence((Block)TFBlocks.MANGROVE_FENCE.value())
      .fenceGate((Block)TFBlocks.MANGROVE_GATE.value())
      .pressurePlate((Block)TFBlocks.MANGROVE_PLATE.value())
      .door((Block)TFBlocks.MANGROVE_DOOR.value())
      .trapdoor((Block)TFBlocks.MANGROVE_TRAPDOOR.value())
      .sign((Block)TFBlocks.MANGROVE_SIGN.value(), (Block)TFBlocks.MANGROVE_WALL_SIGN.value())
      .recipeGroupPrefix("wooden")
      .recipeUnlockedBy("has_planks")
      .getFamily();
   public static final BlockFamily DARKWOOD = familyBuilder((Block)TFBlocks.DARK_PLANKS.value())
      .stairs((Block)TFBlocks.DARK_STAIRS.value())
      .slab((Block)TFBlocks.DARK_SLAB.value())
      .button((Block)TFBlocks.DARK_BUTTON.value())
      .fence((Block)TFBlocks.DARK_FENCE.value())
      .fenceGate((Block)TFBlocks.DARK_GATE.value())
      .pressurePlate((Block)TFBlocks.DARK_PLATE.value())
      .door((Block)TFBlocks.DARK_DOOR.value())
      .trapdoor((Block)TFBlocks.DARK_TRAPDOOR.value())
      .sign((Block)TFBlocks.DARK_SIGN.value(), (Block)TFBlocks.DARK_WALL_SIGN.value())
      .recipeGroupPrefix("wooden")
      .recipeUnlockedBy("has_planks")
      .getFamily();
   public static final BlockFamily TIMEWOOD = familyBuilder((Block)TFBlocks.TIME_PLANKS.value())
      .stairs((Block)TFBlocks.TIME_STAIRS.value())
      .slab((Block)TFBlocks.TIME_SLAB.value())
      .button((Block)TFBlocks.TIME_BUTTON.value())
      .fence((Block)TFBlocks.TIME_FENCE.value())
      .fenceGate((Block)TFBlocks.TIME_GATE.value())
      .pressurePlate((Block)TFBlocks.TIME_PLATE.value())
      .door((Block)TFBlocks.TIME_DOOR.value())
      .trapdoor((Block)TFBlocks.TIME_TRAPDOOR.value())
      .sign((Block)TFBlocks.TIME_SIGN.value(), (Block)TFBlocks.TIME_WALL_SIGN.value())
      .recipeGroupPrefix("wooden")
      .recipeUnlockedBy("has_planks")
      .getFamily();
   public static final BlockFamily TRANSWOOD = familyBuilder((Block)TFBlocks.TRANSFORMATION_PLANKS.value())
      .stairs((Block)TFBlocks.TRANSFORMATION_STAIRS.value())
      .slab((Block)TFBlocks.TRANSFORMATION_SLAB.value())
      .button((Block)TFBlocks.TRANSFORMATION_BUTTON.value())
      .fence((Block)TFBlocks.TRANSFORMATION_FENCE.value())
      .fenceGate((Block)TFBlocks.TRANSFORMATION_GATE.value())
      .pressurePlate((Block)TFBlocks.TRANSFORMATION_PLATE.value())
      .door((Block)TFBlocks.TRANSFORMATION_DOOR.value())
      .trapdoor((Block)TFBlocks.TRANSFORMATION_TRAPDOOR.value())
      .sign((Block)TFBlocks.TRANSFORMATION_SIGN.value(), (Block)TFBlocks.TRANSFORMATION_WALL_SIGN.value())
      .recipeGroupPrefix("wooden")
      .recipeUnlockedBy("has_planks")
      .getFamily();
   public static final BlockFamily MINEWOOD = familyBuilder((Block)TFBlocks.MINING_PLANKS.value())
      .stairs((Block)TFBlocks.MINING_STAIRS.value())
      .slab((Block)TFBlocks.MINING_SLAB.value())
      .button((Block)TFBlocks.MINING_BUTTON.value())
      .fence((Block)TFBlocks.MINING_FENCE.value())
      .fenceGate((Block)TFBlocks.MINING_GATE.value())
      .pressurePlate((Block)TFBlocks.MINING_PLATE.value())
      .door((Block)TFBlocks.MINING_DOOR.value())
      .trapdoor((Block)TFBlocks.MINING_TRAPDOOR.value())
      .sign((Block)TFBlocks.MINING_SIGN.value(), (Block)TFBlocks.MINING_WALL_SIGN.value())
      .recipeGroupPrefix("wooden")
      .recipeUnlockedBy("has_planks")
      .getFamily();
   public static final BlockFamily SORTWOOD = familyBuilder((Block)TFBlocks.SORTING_PLANKS.value())
      .stairs((Block)TFBlocks.SORTING_STAIRS.value())
      .slab((Block)TFBlocks.SORTING_SLAB.value())
      .button((Block)TFBlocks.SORTING_BUTTON.value())
      .fence((Block)TFBlocks.SORTING_FENCE.value())
      .fenceGate((Block)TFBlocks.SORTING_GATE.value())
      .pressurePlate((Block)TFBlocks.SORTING_PLATE.value())
      .door((Block)TFBlocks.SORTING_DOOR.value())
      .trapdoor((Block)TFBlocks.SORTING_TRAPDOOR.value())
      .sign((Block)TFBlocks.SORTING_SIGN.value(), (Block)TFBlocks.SORTING_WALL_SIGN.value())
      .recipeGroupPrefix("wooden")
      .recipeUnlockedBy("has_planks")
      .getFamily();

   public static void verifyFamilyShapes(BlockFamily family, Variant... required) {
      List<Variant> missing = findMissingFamilyShapes(family, required);
      if (!missing.isEmpty()) {
         TwilightForestMod.LOGGER.warn("BlockFamily " + family + " for " + family.getBaseBlock() + " is missing variants for " + missing);
      }
   }

   public static List<Variant> findMissingFamilyShapes(BlockFamily family, Variant... required) {
      ArrayList<Variant> available = new ArrayList<>(Arrays.asList(required));
      available.removeAll(family.getVariants().keySet());
      return available;
   }

   @NotNull
   private static Builder familyBuilder(Block baseBlock) {
      return new Builder(baseBlock);
   }
}
