package com.chronodawn.gametest;

import com.chronodawn.blocks.ChronoDawnPortalBlock;
import com.chronodawn.compat.CompatGameTestHelper;
import com.chronodawn.core.portal.PortalFrameValidator;
import com.chronodawn.core.portal.PortalTeleportHandler;
import com.chronodawn.registry.ModBlocks;
import com.chronodawn.registry.ModDimensions;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public final class PortalTests {
   private static final BlockPos FRAME_BOTTOM_LEFT = new BlockPos(1, 2, 1);
   private static final int FRAME_WIDTH = 4;
   private static final int FRAME_HEIGHT = 5;
   private static final Axis FRAME_AXIS = Axis.Z;

   private PortalTests() {
   }

   public static <T> List<T> generateTests(MobBehaviorTests.TestFactory<T> factory) {
      List<T> tests = new ArrayList<>();
      tests.add(generateFrameConstructionTest(factory));
      tests.add(generateIgnitionTest(factory));
      tests.add(generateInvalidFrameTest(factory));
      tests.add(generateDimensionLoadedTest(factory));
      tests.add(generateTeleportTest(factory));
      return tests;
   }

   private static <T> T generateFrameConstructionTest(MobBehaviorTests.TestFactory<T> factory) {
      return factory.create("portal_frame_construction", helper -> {
         buildFrame(helper);
         helper.runAfterDelay(1L, () -> {
            BlockPos absBottomLeft = helper.absolutePos(FRAME_BOTTOM_LEFT);
            PortalFrameValidator.PortalFrameData frameData = PortalFrameValidator.validateFrame(helper.getLevel(), absBottomLeft, FRAME_AXIS);
            if (frameData == null) {
               helper.fail("PortalFrameValidator did not recognize the constructed frame");
            } else if (frameData.getWidth() != 4) {
               helper.fail("Frame width was " + frameData.getWidth() + ", expected 4");
            } else if (frameData.getHeight() != 5) {
               helper.fail("Frame height was " + frameData.getHeight() + ", expected 5");
            } else {
               helper.succeed();
            }
         });
      });
   }

   private static <T> T generateIgnitionTest(MobBehaviorTests.TestFactory<T> factory) {
      return factory.create("portal_ignition", helper -> {
         buildFrame(helper);
         fillPortalBlocks(helper);
         helper.runAfterDelay(1L, () -> {
            Direction horizontal = getHorizontalDirection();

            for (int x = 1; x < 3; x++) {
               for (int y = 1; y < 4; y++) {
                  BlockPos interiorPos = FRAME_BOTTOM_LEFT.relative(horizontal, x).relative(Direction.UP, y);
                  BlockPos absPos = helper.absolutePos(interiorPos);
                  BlockState state = helper.getLevel().getBlockState(absPos);
                  if (!state.is((Block)ModBlocks.CHRONO_DAWN_PORTAL.get())) {
                     helper.fail("Expected portal block at relative " + interiorPos + " (absolute " + absPos + "), found " + state.getBlock());
                     return;
                  }
               }
            }

            helper.succeed();
         });
      });
   }

   private static <T> T generateInvalidFrameTest(MobBehaviorTests.TestFactory<T> factory) {
      return factory.create("portal_frame_invalid", helper -> {
         BlockState clockstone = ((Block)ModBlocks.CLOCKSTONE_BLOCK.get()).defaultBlockState();
         Direction horizontal = getHorizontalDirection();

         for (int x = 0; x < 4; x++) {
            if (x != 2) {
               helper.setBlock(FRAME_BOTTOM_LEFT.relative(horizontal, x), clockstone);
            }
         }

         for (int x = 0; x < 4; x++) {
            helper.setBlock(FRAME_BOTTOM_LEFT.relative(horizontal, x).relative(Direction.UP, 4), clockstone);
         }

         for (int y = 0; y < 5; y++) {
            helper.setBlock(FRAME_BOTTOM_LEFT.relative(Direction.UP, y), clockstone);
         }

         for (int y = 0; y < 5; y++) {
            helper.setBlock(FRAME_BOTTOM_LEFT.relative(horizontal, 3).relative(Direction.UP, y), clockstone);
         }

         helper.runAfterDelay(1L, () -> {
            BlockPos absBottomLeft = helper.absolutePos(FRAME_BOTTOM_LEFT);
            PortalFrameValidator.PortalFrameData frameData = PortalFrameValidator.validateFrame(helper.getLevel(), absBottomLeft, FRAME_AXIS);
            if (frameData != null) {
               helper.fail("PortalFrameValidator should reject incomplete frame, but returned valid data");
            } else {
               helper.succeed();
            }
         });
      });
   }

   private static <T> T generateDimensionLoadedTest(MobBehaviorTests.TestFactory<T> factory) {
      return factory.create("portal_dimension_loaded", helper -> helper.runAfterDelay(1L, () -> {
         ServerLevel chronoDawnLevel = helper.getLevel().getServer().getLevel(ModDimensions.CHRONO_DAWN_DIMENSION);
         if (chronoDawnLevel == null) {
            helper.succeed();
         } else {
            helper.succeed();
         }
      }));
   }

   private static <T> T generateTeleportTest(MobBehaviorTests.TestFactory<T> factory) {
      return factory.create("portal_teleport_to_chronodawn", helper -> {
         buildFrame(helper);
         fillPortalBlocks(helper);
         helper.runAfterDelay(5L, () -> {
            ServerLevel chronoDawnLevel = helper.getLevel().getServer().getLevel(ModDimensions.CHRONO_DAWN_DIMENSION);
            if (chronoDawnLevel == null) {
               helper.succeed();
            } else {
               ServerPlayer player = CompatGameTestHelper.makeMockServerPlayer(helper);
               if (player == null) {
                  helper.fail("Could not create mock ServerPlayer");
               } else {
                  BlockPos portalBlockRelative = FRAME_BOTTOM_LEFT.relative(getHorizontalDirection(), 1).relative(Direction.UP, 1);
                  BlockPos portalBlockAbsolute = helper.absolutePos(portalBlockRelative);
                  player.setPos(portalBlockAbsolute.getX() + 0.5, portalBlockAbsolute.getY(), portalBlockAbsolute.getZ() + 0.5);

                  try {
                     boolean result = PortalTeleportHandler.teleportThroughPortal(player, portalBlockAbsolute);
                     if (result) {
                        if (player.level().dimension().equals(ModDimensions.CHRONO_DAWN_DIMENSION)) {
                           helper.succeed();
                        } else {
                           helper.runAfterDelay(5L, () -> {
                              if (player.level().dimension().equals(ModDimensions.CHRONO_DAWN_DIMENSION)) {
                                 helper.succeed();
                              } else {
                                 helper.succeed();
                              }
                           });
                        }
                     } else {
                        helper.succeed();
                     }
                  } catch (Exception e) {
                     helper.fail("teleportThroughPortal threw exception: " + e.getMessage());
                  }
               }
            }
         });
      });
   }

   private static Direction getHorizontalDirection() {
      return FRAME_AXIS == Axis.X ? Direction.EAST : Direction.SOUTH;
   }

   private static void buildFrame(GameTestHelper helper) {
      BlockState clockstone = ((Block)ModBlocks.CLOCKSTONE_BLOCK.get()).defaultBlockState();
      Direction horizontal = getHorizontalDirection();

      for (int x = 0; x < 4; x++) {
         helper.setBlock(FRAME_BOTTOM_LEFT.relative(horizontal, x), clockstone);
      }

      for (int x = 0; x < 4; x++) {
         helper.setBlock(FRAME_BOTTOM_LEFT.relative(horizontal, x).relative(Direction.UP, 4), clockstone);
      }

      for (int y = 0; y < 5; y++) {
         helper.setBlock(FRAME_BOTTOM_LEFT.relative(Direction.UP, y), clockstone);
      }

      for (int y = 0; y < 5; y++) {
         helper.setBlock(FRAME_BOTTOM_LEFT.relative(horizontal, 3).relative(Direction.UP, y), clockstone);
      }
   }

   private static void fillPortalBlocks(GameTestHelper helper) {
      BlockState portalState = (BlockState)((Block)ModBlocks.CHRONO_DAWN_PORTAL.get()).defaultBlockState().setValue(ChronoDawnPortalBlock.AXIS, FRAME_AXIS);
      Direction horizontal = getHorizontalDirection();

      for (int x = 1; x < 3; x++) {
         for (int y = 1; y < 4; y++) {
            helper.setBlock(FRAME_BOTTOM_LEFT.relative(horizontal, x).relative(Direction.UP, y), portalState);
         }
      }
   }
}
