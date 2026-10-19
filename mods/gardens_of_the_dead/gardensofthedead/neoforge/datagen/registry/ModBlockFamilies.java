package gardensofthedead.neoforge.datagen.registry;

import com.google.common.collect.Maps;
import gardensofthedead.registry.ModBlocks;
import java.util.Map;
import java.util.stream.Stream;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.BlockFamily.Builder;
import net.minecraft.world.level.block.Block;

public class ModBlockFamilies {
   private static final Map<Block, BlockFamily> MAP = Maps.newHashMap();
   private static final String RECIPE_GROUP_PREFIX_WOODEN = "wooden";
   private static final String RECIPE_UNLOCKED_BY_HAS_PLANKS = "has_planks";
   public static final BlockFamily SOULBLIGHT_PLANKS = familyBuilder((Block)ModBlocks.SOULBLIGHT_PLANKS.get())
      .button((Block)ModBlocks.SOULBLIGHT_BUTTON.get())
      .fence((Block)ModBlocks.SOULBLIGHT_FENCE.get())
      .fenceGate((Block)ModBlocks.SOULBLIGHT_FENCE_GATE.get())
      .pressurePlate((Block)ModBlocks.SOULBLIGHT_PRESSURE_PLATE.get())
      .sign((Block)ModBlocks.SOULBLIGHT_SIGN.get(), (Block)ModBlocks.SOULBLIGHT_WALL_SIGN.get())
      .slab((Block)ModBlocks.SOULBLIGHT_SLAB.get())
      .stairs((Block)ModBlocks.SOULBLIGHT_STAIRS.get())
      .door((Block)ModBlocks.SOULBLIGHT_DOOR.get())
      .trapdoor((Block)ModBlocks.SOULBLIGHT_TRAPDOOR.get())
      .recipeGroupPrefix("wooden")
      .recipeUnlockedBy("has_planks")
      .getFamily();
   public static final BlockFamily WHISTLECANE_PLANKS = familyBuilder((Block)ModBlocks.WHISTLECANE_PLANKS.get())
      .button((Block)ModBlocks.WHISTLECANE_BUTTON.get())
      .slab((Block)ModBlocks.WHISTLECANE_SLAB.get())
      .stairs((Block)ModBlocks.WHISTLECANE_STAIRS.get())
      .customFence((Block)ModBlocks.WHISTLECANE_FENCE.get())
      .customFenceGate((Block)ModBlocks.WHISTLECANE_FENCE_GATE.get())
      .pressurePlate((Block)ModBlocks.WHISTLECANE_PRESSURE_PLATE.get())
      .sign((Block)ModBlocks.WHISTLECANE_SIGN.get(), (Block)ModBlocks.WHISTLECANE_WALL_SIGN.get())
      .door((Block)ModBlocks.WHISTLECANE_DOOR.get())
      .trapdoor((Block)ModBlocks.WHISTLECANE_TRAPDOOR.get())
      .mosaic((Block)ModBlocks.WHISTLECANE_MOSAIC.get())
      .recipeGroupPrefix("wooden")
      .recipeUnlockedBy("has_planks")
      .getFamily();
   public static final BlockFamily WHISTLECANE_MOSAIC = familyBuilder((Block)ModBlocks.WHISTLECANE_MOSAIC.get())
      .slab((Block)ModBlocks.WHISTLECANE_MOSAIC_SLAB.get())
      .stairs((Block)ModBlocks.WHISTLECANE_MOSAIC_STAIRS.get())
      .getFamily();

   private static Builder familyBuilder(Block block) {
      Builder builder = new Builder(block);
      BlockFamily blockfamily = MAP.put(block, builder.getFamily());
      if (blockfamily != null) {
         throw new IllegalStateException("Duplicate family definition for " + BuiltInRegistries.BLOCK.getKey(block));
      } else {
         return builder;
      }
   }

   public static Stream<BlockFamily> getAllFamilies() {
      return MAP.values().stream();
   }
}
