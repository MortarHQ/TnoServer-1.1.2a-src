package com.chronodawn.gametest;

import com.chronodawn.registry.ModBlocks;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams.Builder;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

public final class FadedPlainsTests {
   private static final BlockPos BASE_POS = new BlockPos(1, 2, 1);
   private static final BlockPos TOP_POS = new BlockPos(1, 3, 1);

   private FadedPlainsTests() {
   }

   public static <T> List<T> generateTests(MobBehaviorTests.TestFactory<T> factory) {
      List<T> tests = new ArrayList<>();
      tests.add(factory.create("dead_bush_survives_on_parched_dirt", FadedPlainsTests::testDeadBushSurvivesOnParchedDirt));
      tests.add(factory.create("dead_bush_breaks_on_grass", FadedPlainsTests::testDeadBushBreaksOnGrass));
      tests.add(factory.create("faded_grass_shears_drops_self", FadedPlainsTests::testFadedGrassShearsDropsSelf));
      return tests;
   }

   private static void testDeadBushSurvivesOnParchedDirt(GameTestHelper helper) {
      helper.setBlock(BASE_POS, (Block)ModBlocks.PARCHED_TEMPORAL_DIRT.get());
      helper.runAfterDelay(1L, () -> {
         BlockState deadBushState = ((Block)ModBlocks.TEMPORAL_DEAD_BUSH.get()).defaultBlockState();
         BlockPos absTop = helper.absolutePos(TOP_POS);
         boolean survives = deadBushState.canSurvive(helper.getLevel(), absTop);
         if (survives) {
            helper.succeed();
         } else {
            helper.fail("TEMPORAL_DEAD_BUSH should survive on PARCHED_TEMPORAL_DIRT but canSurvive() returned false");
         }
      });
   }

   private static void testDeadBushBreaksOnGrass(GameTestHelper helper) {
      helper.setBlock(BASE_POS, (Block)ModBlocks.TEMPORAL_GRASS_BLOCK.get());
      helper.runAfterDelay(1L, () -> {
         BlockState deadBushState = ((Block)ModBlocks.TEMPORAL_DEAD_BUSH.get()).defaultBlockState();
         BlockPos absTop = helper.absolutePos(TOP_POS);
         boolean survives = deadBushState.canSurvive(helper.getLevel(), absTop);
         if (!survives) {
            helper.succeed();
         } else {
            helper.fail("TEMPORAL_DEAD_BUSH should NOT survive on TEMPORAL_GRASS_BLOCK but canSurvive() returned true");
         }
      });
   }

   private static void testFadedGrassShearsDropsSelf(GameTestHelper helper) {
      helper.setBlock(BASE_POS, (Block)ModBlocks.FADED_TEMPORAL_GRASS.get());
      helper.runAfterDelay(
         1L,
         () -> {
            BlockState grassState = ((Block)ModBlocks.FADED_TEMPORAL_GRASS.get()).defaultBlockState();
            BlockPos absPos = helper.absolutePos(BASE_POS);
            ServerLevel serverLevel = helper.getLevel();
            ItemStack shears = new ItemStack(Items.SHEARS);
            Builder lootParamsBuilder = new Builder(serverLevel)
               .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(absPos))
               .withParameter(LootContextParams.TOOL, shears);
            List<ItemStack> drops = grassState.getDrops(lootParamsBuilder);
            if (drops.size() == 1 && drops.get(0).is(((Block)ModBlocks.FADED_TEMPORAL_GRASS.get()).asItem())) {
               helper.succeed();
            } else {
               helper.fail("FADED_TEMPORAL_GRASS with shears should drop exactly 1 faded_temporal_grass item, got: " + drops);
            }
         }
      );
   }
}
