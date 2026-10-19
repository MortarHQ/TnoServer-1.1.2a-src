package net.witherspawnanimation.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.witherspawnanimation.init.WitherSpawnAnimationModBlocks;
import net.witherspawnanimation.network.WitherSpawnAnimationModVariables;

public class WitherStormModFixProcedure {
   public static boolean execute(LevelAccessor world, double x, double y, double z, BlockState blockstate) {
      boolean nope = false;
      if (blockstate.getBlock() == Blocks.WITHER_SKELETON_SKULL || blockstate.getBlock() == Blocks.WITHER_SKELETON_WALL_SKULL) {
         nope = true;
         if (world.getBlockState(BlockPos.containing(x, y - 1.0, z)).getBlock() == Blocks.COMMAND_BLOCK
            && world.getBlockState(BlockPos.containing(x, y - 2.0, z)).getBlock() == Blocks.SOUL_SAND
            && world.getBlockState(BlockPos.containing(x + 1.0, y - 1.0, z)).getBlock() == Blocks.SOUL_SAND
            && world.getBlockState(BlockPos.containing(x - 1.0, y - 1.0, z)).getBlock() == Blocks.SOUL_SAND
            && (
               world.getBlockState(BlockPos.containing(x - 1.0, y, z)).getBlock() == Blocks.WITHER_SKELETON_SKULL
                  || world.getBlockState(BlockPos.containing(x - 1.0, y, z)).getBlock() == Blocks.WITHER_SKELETON_WALL_SKULL
            )
            && (
               world.getBlockState(BlockPos.containing(x + 1.0, y, z)).getBlock() == Blocks.WITHER_SKELETON_SKULL
                  || world.getBlockState(BlockPos.containing(x + 1.0, y, z)).getBlock() == Blocks.WITHER_SKELETON_WALL_SKULL
            )
            && (
               world.getBlockState(BlockPos.containing(x + 1.0, y - 2.0, z)).getBlock() == Blocks.AIR
                  || world.getBlockState(BlockPos.containing(x + 1.0, y - 2.0, z)).getBlock() == WitherSpawnAnimationModBlocks.NO_W.get()
                  || world.getBlockState(BlockPos.containing(x + 1.0, y - 2.0, z))
                        .is(BlockTags.create(ResourceLocation.parse("minecraft:replaceable_spawn_fix")))
                     && WitherSpawnAnimationModVariables.MapVariables.get(world).spawn_fix
            )
            && (
               world.getBlockState(BlockPos.containing(x - 1.0, y - 2.0, z)).getBlock() == Blocks.AIR
                  || world.getBlockState(BlockPos.containing(x - 1.0, y - 2.0, z)).getBlock() == WitherSpawnAnimationModBlocks.NO_W.get()
                  || world.getBlockState(BlockPos.containing(x - 1.0, y - 2.0, z))
                        .is(BlockTags.create(ResourceLocation.parse("minecraft:replaceable_spawn_fix")))
                     && WitherSpawnAnimationModVariables.MapVariables.get(world).spawn_fix
            )) {
            world.setBlock(BlockPos.containing(x, y, z), Blocks.AIR.defaultBlockState(), 3);
            world.setBlock(BlockPos.containing(x, y - 1.0, z), Blocks.AIR.defaultBlockState(), 3);
            world.setBlock(BlockPos.containing(x, y - 2.0, z), Blocks.AIR.defaultBlockState(), 3);
            world.setBlock(BlockPos.containing(x + 1.0, y - 1.0, z), Blocks.AIR.defaultBlockState(), 3);
            world.setBlock(BlockPos.containing(x - 1.0, y - 1.0, z), Blocks.AIR.defaultBlockState(), 3);
            world.setBlock(BlockPos.containing(x - 1.0, y, z), Blocks.AIR.defaultBlockState(), 3);
            world.setBlock(BlockPos.containing(x + 1.0, y, z), Blocks.AIR.defaultBlockState(), 3);
            nope = false;
            return true;
         }
      }

      if (nope
         && world.getBlockState(BlockPos.containing(x, y - 1.0, z)).getBlock() == Blocks.COMMAND_BLOCK
         && world.getBlockState(BlockPos.containing(x, y - 2.0, z)).getBlock() == Blocks.SOUL_SAND
         && world.getBlockState(BlockPos.containing(x, y - 1.0, z + 1.0)).getBlock() == Blocks.SOUL_SAND
         && world.getBlockState(BlockPos.containing(x, y - 1.0, z - 1.0)).getBlock() == Blocks.SOUL_SAND
         && (
            world.getBlockState(BlockPos.containing(x, y, z - 1.0)).getBlock() == Blocks.WITHER_SKELETON_SKULL
               || world.getBlockState(BlockPos.containing(x, y, z - 1.0)).getBlock() == Blocks.WITHER_SKELETON_WALL_SKULL
         )
         && (
            world.getBlockState(BlockPos.containing(x, y, z + 1.0)).getBlock() == Blocks.WITHER_SKELETON_SKULL
               || world.getBlockState(BlockPos.containing(x, y, z + 1.0)).getBlock() == Blocks.WITHER_SKELETON_WALL_SKULL
         )
         && (
            world.getBlockState(BlockPos.containing(x, y - 2.0, z + 1.0)).getBlock() == Blocks.AIR
               || world.getBlockState(BlockPos.containing(x, y - 2.0, z + 1.0)).getBlock() == WitherSpawnAnimationModBlocks.NO_W.get()
               || world.getBlockState(BlockPos.containing(x, y - 2.0, z + 1.0)).is(BlockTags.create(ResourceLocation.parse("minecraft:replaceable_spawn_fix")))
                  && WitherSpawnAnimationModVariables.MapVariables.get(world).spawn_fix
         )
         && (
            world.getBlockState(BlockPos.containing(x, y - 2.0, z - 1.0)).getBlock() == Blocks.AIR
               || world.getBlockState(BlockPos.containing(x, y - 2.0, z - 1.0)).getBlock() == WitherSpawnAnimationModBlocks.NO_W.get()
               || world.getBlockState(BlockPos.containing(x, y - 2.0, z - 1.0)).is(BlockTags.create(ResourceLocation.parse("minecraft:replaceable_spawn_fix")))
                  && WitherSpawnAnimationModVariables.MapVariables.get(world).spawn_fix
         )) {
         world.setBlock(BlockPos.containing(x, y, z), Blocks.AIR.defaultBlockState(), 3);
         world.setBlock(BlockPos.containing(x, y - 1.0, z), Blocks.AIR.defaultBlockState(), 3);
         world.setBlock(BlockPos.containing(x, y - 2.0, z), Blocks.AIR.defaultBlockState(), 3);
         world.setBlock(BlockPos.containing(x, y - 1.0, z + 1.0), Blocks.AIR.defaultBlockState(), 3);
         world.setBlock(BlockPos.containing(x, y - 1.0, z - 1.0), Blocks.AIR.defaultBlockState(), 3);
         world.setBlock(BlockPos.containing(x, y, z + 1.0), Blocks.AIR.defaultBlockState(), 3);
         world.setBlock(BlockPos.containing(x, y, z - 1.0), Blocks.AIR.defaultBlockState(), 3);
         nope = false;
         return true;
      }

      if (nope
         && world.getBlockState(BlockPos.containing(x, y - 1.0, z)).getBlock() == Blocks.SOUL_SAND
         && (
            world.getBlockState(BlockPos.containing(x, y - 2.0, z)).getBlock() == Blocks.AIR
               || world.getBlockState(BlockPos.containing(x, y - 2.0, z)).getBlock() == WitherSpawnAnimationModBlocks.NO_W.get()
               || world.getBlockState(BlockPos.containing(x, y - 2.0, z)).is(BlockTags.create(ResourceLocation.parse("minecraft:replaceable_spawn_fix")))
                  && WitherSpawnAnimationModVariables.MapVariables.get(world).spawn_fix
         )
         && (
            world.getBlockState(BlockPos.containing(x + 1.0, y - 1.0, z)).getBlock() == Blocks.AIR
               || world.getBlockState(BlockPos.containing(x + 1.0, y - 1.0, z)).getBlock() == WitherSpawnAnimationModBlocks.NO_W.get()
               || world.getBlockState(BlockPos.containing(x + 1.0, y - 1.0, z)).is(BlockTags.create(ResourceLocation.parse("minecraft:replaceable_spawn_fix")))
                  && WitherSpawnAnimationModVariables.MapVariables.get(world).spawn_fix
         )
         && world.getBlockState(BlockPos.containing(x - 1.0, y - 1.0, z)).getBlock() == Blocks.COMMAND_BLOCK
         && (
            world.getBlockState(BlockPos.containing(x - 1.0, y, z)).getBlock() == Blocks.WITHER_SKELETON_SKULL
               || world.getBlockState(BlockPos.containing(x - 1.0, y, z)).getBlock() == Blocks.WITHER_SKELETON_WALL_SKULL
         )
         && (
            world.getBlockState(BlockPos.containing(x + 1.0, y, z)).getBlock() == Blocks.AIR
               || world.getBlockState(BlockPos.containing(x + 1.0, y, z)).getBlock() == WitherSpawnAnimationModBlocks.NO_W.get()
               || world.getBlockState(BlockPos.containing(x + 1.0, y, z)).is(BlockTags.create(ResourceLocation.parse("minecraft:replaceable_spawn_fix")))
                  && WitherSpawnAnimationModVariables.MapVariables.get(world).spawn_fix
         )
         && (
            world.getBlockState(BlockPos.containing(x + 1.0, y - 2.0, z)).getBlock() == Blocks.AIR
               || world.getBlockState(BlockPos.containing(x + 1.0, y - 2.0, z)).getBlock() == WitherSpawnAnimationModBlocks.NO_W.get()
               || world.getBlockState(BlockPos.containing(x + 1.0, y - 2.0, z)).is(BlockTags.create(ResourceLocation.parse("minecraft:replaceable_spawn_fix")))
                  && WitherSpawnAnimationModVariables.MapVariables.get(world).spawn_fix
         )
         && world.getBlockState(BlockPos.containing(x - 1.0, y - 2.0, z)).getBlock() == Blocks.SOUL_SAND
         && (
            world.getBlockState(BlockPos.containing(x - 2.0, y, z)).getBlock() == Blocks.WITHER_SKELETON_SKULL
               || world.getBlockState(BlockPos.containing(x - 2.0, y, z)).getBlock() == Blocks.WITHER_SKELETON_WALL_SKULL
         )
         && world.getBlockState(BlockPos.containing(x - 2.0, y - 1.0, z)).getBlock() == Blocks.SOUL_SAND
         && (
            world.getBlockState(BlockPos.containing(x - 2.0, y - 2.0, z)).getBlock() == Blocks.AIR
               || world.getBlockState(BlockPos.containing(x - 2.0, y - 2.0, z)).getBlock() == WitherSpawnAnimationModBlocks.NO_W.get()
               || world.getBlockState(BlockPos.containing(x - 2.0, y - 2.0, z)).is(BlockTags.create(ResourceLocation.parse("minecraft:replaceable_spawn_fix")))
                  && WitherSpawnAnimationModVariables.MapVariables.get(world).spawn_fix
         )
         && (
            world.getBlockState(BlockPos.containing(x - 3.0, y - 2.0, z)).getBlock() == Blocks.AIR
               || world.getBlockState(BlockPos.containing(x - 3.0, y - 2.0, z)).getBlock() == WitherSpawnAnimationModBlocks.NO_W.get()
               || world.getBlockState(BlockPos.containing(x - 3.0, y - 2.0, z)).is(BlockTags.create(ResourceLocation.parse("minecraft:replaceable_spawn_fix")))
                  && WitherSpawnAnimationModVariables.MapVariables.get(world).spawn_fix
         )
         && (
            world.getBlockState(BlockPos.containing(x - 3.0, y - 1.0, z)).getBlock() == Blocks.AIR
               || world.getBlockState(BlockPos.containing(x - 3.0, y - 1.0, z)).getBlock() == WitherSpawnAnimationModBlocks.NO_W.get()
               || world.getBlockState(BlockPos.containing(x - 3.0, y - 1.0, z)).is(BlockTags.create(ResourceLocation.parse("minecraft:replaceable_spawn_fix")))
                  && WitherSpawnAnimationModVariables.MapVariables.get(world).spawn_fix
         )
         && (
            world.getBlockState(BlockPos.containing(x - 3.0, y, z)).getBlock() == Blocks.AIR
               || world.getBlockState(BlockPos.containing(x - 3.0, y, z)).getBlock() == WitherSpawnAnimationModBlocks.NO_W.get()
               || world.getBlockState(BlockPos.containing(x - 3.0, y, z)).is(BlockTags.create(ResourceLocation.parse("minecraft:replaceable_spawn_fix")))
                  && WitherSpawnAnimationModVariables.MapVariables.get(world).spawn_fix
         )) {
         nope = false;
         world.setBlock(BlockPos.containing(x, y, z), Blocks.AIR.defaultBlockState(), 3);
         world.setBlock(BlockPos.containing(x, y - 1.0, z), Blocks.AIR.defaultBlockState(), 3);
         world.setBlock(BlockPos.containing(x - 1.0, y, z), Blocks.AIR.defaultBlockState(), 3);
         world.setBlock(BlockPos.containing(x - 2.0, y, z), Blocks.AIR.defaultBlockState(), 3);
         world.setBlock(BlockPos.containing(x - 1.0, y - 1.0, z), Blocks.AIR.defaultBlockState(), 3);
         world.setBlock(BlockPos.containing(x - 1.0, y - 2.0, z), Blocks.AIR.defaultBlockState(), 3);
         world.setBlock(BlockPos.containing(x - 2.0, y - 1.0, z), Blocks.AIR.defaultBlockState(), 3);
         return true;
      }

      if (nope
         && world.getBlockState(BlockPos.containing(x, y - 1.0, z)).getBlock() == Blocks.SOUL_SAND
         && (
            world.getBlockState(BlockPos.containing(x, y - 2.0, z)).getBlock() == Blocks.AIR
               || world.getBlockState(BlockPos.containing(x, y - 2.0, z)).getBlock() == WitherSpawnAnimationModBlocks.NO_W.get()
               || world.getBlockState(BlockPos.containing(x, y - 2.0, z)).is(BlockTags.create(ResourceLocation.parse("minecraft:replaceable_spawn_fix")))
                  && WitherSpawnAnimationModVariables.MapVariables.get(world).spawn_fix
         )
         && (
            world.getBlockState(BlockPos.containing(x, y - 1.0, z + 1.0)).getBlock() == Blocks.AIR
               || world.getBlockState(BlockPos.containing(x, y - 1.0, z + 1.0)).getBlock() == WitherSpawnAnimationModBlocks.NO_W.get()
               || world.getBlockState(BlockPos.containing(x, y - 1.0, z + 1.0)).is(BlockTags.create(ResourceLocation.parse("minecraft:replaceable_spawn_fix")))
                  && WitherSpawnAnimationModVariables.MapVariables.get(world).spawn_fix
         )
         && world.getBlockState(BlockPos.containing(x, y - 1.0, z - 1.0)).getBlock() == Blocks.COMMAND_BLOCK
         && (
            world.getBlockState(BlockPos.containing(x, y, z - 1.0)).getBlock() == Blocks.WITHER_SKELETON_SKULL
               || world.getBlockState(BlockPos.containing(x, y, z - 1.0)).getBlock() == Blocks.WITHER_SKELETON_WALL_SKULL
         )
         && (
            world.getBlockState(BlockPos.containing(x, y, z + 1.0)).getBlock() == Blocks.AIR
               || world.getBlockState(BlockPos.containing(x, y, z + 1.0)).getBlock() == WitherSpawnAnimationModBlocks.NO_W.get()
               || world.getBlockState(BlockPos.containing(x, y, z + 1.0)).is(BlockTags.create(ResourceLocation.parse("minecraft:replaceable_spawn_fix")))
                  && WitherSpawnAnimationModVariables.MapVariables.get(world).spawn_fix
         )
         && (
            world.getBlockState(BlockPos.containing(x, y - 2.0, z + 1.0)).getBlock() == Blocks.AIR
               || world.getBlockState(BlockPos.containing(x, y - 2.0, z + 1.0)).getBlock() == WitherSpawnAnimationModBlocks.NO_W.get()
               || world.getBlockState(BlockPos.containing(x, y - 2.0, z + 1.0)).is(BlockTags.create(ResourceLocation.parse("minecraft:replaceable_spawn_fix")))
                  && WitherSpawnAnimationModVariables.MapVariables.get(world).spawn_fix
         )
         && world.getBlockState(BlockPos.containing(x, y - 2.0, z - 1.0)).getBlock() == Blocks.SOUL_SAND
         && (
            world.getBlockState(BlockPos.containing(x, y, z - 2.0)).getBlock() == Blocks.WITHER_SKELETON_SKULL
               || world.getBlockState(BlockPos.containing(x, y, z - 2.0)).getBlock() == Blocks.WITHER_SKELETON_WALL_SKULL
         )
         && world.getBlockState(BlockPos.containing(x, y - 1.0, z - 2.0)).getBlock() == Blocks.SOUL_SAND
         && (
            world.getBlockState(BlockPos.containing(x, y - 2.0, z - 2.0)).getBlock() == Blocks.AIR
               || world.getBlockState(BlockPos.containing(x, y - 2.0, z - 2.0)).getBlock() == WitherSpawnAnimationModBlocks.NO_W.get()
               || world.getBlockState(BlockPos.containing(x, y - 2.0, z - 2.0)).is(BlockTags.create(ResourceLocation.parse("minecraft:replaceable_spawn_fix")))
                  && WitherSpawnAnimationModVariables.MapVariables.get(world).spawn_fix
         )
         && (
            world.getBlockState(BlockPos.containing(x, y - 2.0, z - 3.0)).getBlock() == Blocks.AIR
               || world.getBlockState(BlockPos.containing(x, y - 2.0, z - 3.0)).getBlock() == WitherSpawnAnimationModBlocks.NO_W.get()
               || world.getBlockState(BlockPos.containing(x, y - 2.0, z - 3.0)).is(BlockTags.create(ResourceLocation.parse("minecraft:replaceable_spawn_fix")))
                  && WitherSpawnAnimationModVariables.MapVariables.get(world).spawn_fix
         )
         && (
            world.getBlockState(BlockPos.containing(x, y - 1.0, z - 3.0)).getBlock() == Blocks.AIR
               || world.getBlockState(BlockPos.containing(x, y - 1.0, z - 3.0)).getBlock() == WitherSpawnAnimationModBlocks.NO_W.get()
               || world.getBlockState(BlockPos.containing(x, y - 1.0, z - 3.0)).is(BlockTags.create(ResourceLocation.parse("minecraft:replaceable_spawn_fix")))
                  && WitherSpawnAnimationModVariables.MapVariables.get(world).spawn_fix
         )
         && (
            world.getBlockState(BlockPos.containing(x, y, z - 3.0)).getBlock() == Blocks.AIR
               || world.getBlockState(BlockPos.containing(x, y, z - 3.0)).getBlock() == WitherSpawnAnimationModBlocks.NO_W.get()
               || world.getBlockState(BlockPos.containing(x, y, z - 3.0)).is(BlockTags.create(ResourceLocation.parse("minecraft:replaceable_spawn_fix")))
                  && WitherSpawnAnimationModVariables.MapVariables.get(world).spawn_fix
         )) {
         nope = false;
         world.setBlock(BlockPos.containing(x, y, z), Blocks.AIR.defaultBlockState(), 3);
         world.setBlock(BlockPos.containing(x, y - 1.0, z), Blocks.AIR.defaultBlockState(), 3);
         world.setBlock(BlockPos.containing(x, y, z - 1.0), Blocks.AIR.defaultBlockState(), 3);
         world.setBlock(BlockPos.containing(x, y, z - 2.0), Blocks.AIR.defaultBlockState(), 3);
         world.setBlock(BlockPos.containing(x, y - 1.0, z - 1.0), Blocks.AIR.defaultBlockState(), 3);
         world.setBlock(BlockPos.containing(x, y - 2.0, z - 1.0), Blocks.AIR.defaultBlockState(), 3);
         world.setBlock(BlockPos.containing(x, y - 1.0, z - 2.0), Blocks.AIR.defaultBlockState(), 3);
         return true;
      }

      if (nope
         && world.getBlockState(BlockPos.containing(x, y - 1.0, z)).getBlock() == Blocks.SOUL_SAND
         && (
            world.getBlockState(BlockPos.containing(x, y - 2.0, z)).getBlock() == Blocks.AIR
               || world.getBlockState(BlockPos.containing(x, y - 2.0, z)).getBlock() == WitherSpawnAnimationModBlocks.NO_W.get()
               || world.getBlockState(BlockPos.containing(x, y - 2.0, z)).is(BlockTags.create(ResourceLocation.parse("minecraft:replaceable_spawn_fix")))
                  && WitherSpawnAnimationModVariables.MapVariables.get(world).spawn_fix
         )
         && world.getBlockState(BlockPos.containing(x + 1.0, y - 1.0, z)).getBlock() == Blocks.COMMAND_BLOCK
         && (
            world.getBlockState(BlockPos.containing(x - 1.0, y - 1.0, z)).getBlock() == Blocks.AIR
               || world.getBlockState(BlockPos.containing(x - 1.0, y - 1.0, z)).getBlock() == WitherSpawnAnimationModBlocks.NO_W.get()
               || world.getBlockState(BlockPos.containing(x - 1.0, y - 1.0, z)).is(BlockTags.create(ResourceLocation.parse("minecraft:replaceable_spawn_fix")))
                  && WitherSpawnAnimationModVariables.MapVariables.get(world).spawn_fix
         )
         && (
            world.getBlockState(BlockPos.containing(x - 1.0, y, z)).getBlock() == Blocks.AIR
               || world.getBlockState(BlockPos.containing(x - 1.0, y, z)).getBlock() == WitherSpawnAnimationModBlocks.NO_W.get()
               || world.getBlockState(BlockPos.containing(x - 1.0, y, z)).is(BlockTags.create(ResourceLocation.parse("minecraft:replaceable_spawn_fix")))
                  && WitherSpawnAnimationModVariables.MapVariables.get(world).spawn_fix
         )
         && (
            world.getBlockState(BlockPos.containing(x + 1.0, y, z)).getBlock() == Blocks.WITHER_SKELETON_SKULL
               || world.getBlockState(BlockPos.containing(x + 1.0, y, z)).getBlock() == Blocks.WITHER_SKELETON_WALL_SKULL
         )
         && world.getBlockState(BlockPos.containing(x + 1.0, y - 2.0, z)).getBlock() == Blocks.SOUL_SAND
         && (
            world.getBlockState(BlockPos.containing(x - 1.0, y - 2.0, z)).getBlock() == Blocks.AIR
               || world.getBlockState(BlockPos.containing(x - 1.0, y - 2.0, z)).getBlock() == WitherSpawnAnimationModBlocks.NO_W.get()
               || world.getBlockState(BlockPos.containing(x - 1.0, y - 2.0, z)).is(BlockTags.create(ResourceLocation.parse("minecraft:replaceable_spawn_fix")))
                  && WitherSpawnAnimationModVariables.MapVariables.get(world).spawn_fix
         )
         && (
            world.getBlockState(BlockPos.containing(x + 2.0, y, z)).getBlock() == Blocks.WITHER_SKELETON_SKULL
               || world.getBlockState(BlockPos.containing(x + 2.0, y, z)).getBlock() == Blocks.WITHER_SKELETON_WALL_SKULL
         )
         && world.getBlockState(BlockPos.containing(x + 2.0, y - 1.0, z)).getBlock() == Blocks.SOUL_SAND
         && (
            world.getBlockState(BlockPos.containing(x + 2.0, y - 2.0, z)).getBlock() == Blocks.AIR
               || world.getBlockState(BlockPos.containing(x + 2.0, y - 2.0, z)).getBlock() == WitherSpawnAnimationModBlocks.NO_W.get()
               || world.getBlockState(BlockPos.containing(x + 2.0, y - 2.0, z)).is(BlockTags.create(ResourceLocation.parse("minecraft:replaceable_spawn_fix")))
                  && WitherSpawnAnimationModVariables.MapVariables.get(world).spawn_fix
         )
         && (
            world.getBlockState(BlockPos.containing(x + 3.0, y - 2.0, z)).getBlock() == Blocks.AIR
               || world.getBlockState(BlockPos.containing(x + 3.0, y - 2.0, z)).getBlock() == WitherSpawnAnimationModBlocks.NO_W.get()
               || world.getBlockState(BlockPos.containing(x + 3.0, y - 2.0, z)).is(BlockTags.create(ResourceLocation.parse("minecraft:replaceable_spawn_fix")))
                  && WitherSpawnAnimationModVariables.MapVariables.get(world).spawn_fix
         )
         && (
            world.getBlockState(BlockPos.containing(x + 3.0, y - 1.0, z)).getBlock() == Blocks.AIR
               || world.getBlockState(BlockPos.containing(x + 3.0, y - 1.0, z)).getBlock() == WitherSpawnAnimationModBlocks.NO_W.get()
               || world.getBlockState(BlockPos.containing(x + 3.0, y - 1.0, z)).is(BlockTags.create(ResourceLocation.parse("minecraft:replaceable_spawn_fix")))
                  && WitherSpawnAnimationModVariables.MapVariables.get(world).spawn_fix
         )
         && (
            world.getBlockState(BlockPos.containing(x + 3.0, y, z)).getBlock() == Blocks.AIR
               || world.getBlockState(BlockPos.containing(x + 3.0, y, z)).getBlock() == WitherSpawnAnimationModBlocks.NO_W.get()
               || world.getBlockState(BlockPos.containing(x + 3.0, y, z)).is(BlockTags.create(ResourceLocation.parse("minecraft:replaceable_spawn_fix")))
                  && WitherSpawnAnimationModVariables.MapVariables.get(world).spawn_fix
         )) {
         nope = false;
         world.setBlock(BlockPos.containing(x, y, z), Blocks.AIR.defaultBlockState(), 3);
         world.setBlock(BlockPos.containing(x + 1.0, y, z), Blocks.AIR.defaultBlockState(), 3);
         world.setBlock(BlockPos.containing(x + 2.0, y, z), Blocks.AIR.defaultBlockState(), 3);
         world.setBlock(BlockPos.containing(x, y - 1.0, z), Blocks.AIR.defaultBlockState(), 3);
         world.setBlock(BlockPos.containing(x + 1.0, y - 1.0, z), Blocks.AIR.defaultBlockState(), 3);
         world.setBlock(BlockPos.containing(x + 2.0, y - 1.0, z), Blocks.AIR.defaultBlockState(), 3);
         world.setBlock(BlockPos.containing(x + 1.0, y - 2.0, z), Blocks.AIR.defaultBlockState(), 3);
         return true;
      }

      if (!nope
         || world.getBlockState(BlockPos.containing(x, y - 1.0, z)).getBlock() != Blocks.SOUL_SAND
         || world.getBlockState(BlockPos.containing(x, y - 2.0, z)).getBlock() != Blocks.AIR
            && world.getBlockState(BlockPos.containing(x, y - 2.0, z)).getBlock() != WitherSpawnAnimationModBlocks.NO_W.get()
            && (
               !world.getBlockState(BlockPos.containing(x, y - 2.0, z)).is(BlockTags.create(ResourceLocation.parse("minecraft:replaceable_spawn_fix")))
                  || !WitherSpawnAnimationModVariables.MapVariables.get(world).spawn_fix
            )
         || world.getBlockState(BlockPos.containing(x, y - 1.0, z + 1.0)).getBlock() != Blocks.COMMAND_BLOCK
         || world.getBlockState(BlockPos.containing(x, y - 1.0, z - 1.0)).getBlock() != Blocks.AIR
            && world.getBlockState(BlockPos.containing(x, y - 1.0, z - 1.0)).getBlock() != WitherSpawnAnimationModBlocks.NO_W.get()
            && (
               !world.getBlockState(BlockPos.containing(x, y - 1.0, z - 1.0)).is(BlockTags.create(ResourceLocation.parse("minecraft:replaceable_spawn_fix")))
                  || !WitherSpawnAnimationModVariables.MapVariables.get(world).spawn_fix
            )
         || world.getBlockState(BlockPos.containing(x, y, z - 1.0)).getBlock() != Blocks.AIR
            && world.getBlockState(BlockPos.containing(x, y, z - 1.0)).getBlock() != WitherSpawnAnimationModBlocks.NO_W.get()
            && (
               !world.getBlockState(BlockPos.containing(x, y, z - 1.0)).is(BlockTags.create(ResourceLocation.parse("minecraft:replaceable_spawn_fix")))
                  || !WitherSpawnAnimationModVariables.MapVariables.get(world).spawn_fix
            )
         || world.getBlockState(BlockPos.containing(x, y, z + 1.0)).getBlock() != Blocks.WITHER_SKELETON_SKULL
            && world.getBlockState(BlockPos.containing(x, y, z + 1.0)).getBlock() != Blocks.WITHER_SKELETON_WALL_SKULL
         || world.getBlockState(BlockPos.containing(x, y - 2.0, z + 1.0)).getBlock() != Blocks.SOUL_SAND
         || world.getBlockState(BlockPos.containing(x, y - 2.0, z - 1.0)).getBlock() != Blocks.AIR
            && world.getBlockState(BlockPos.containing(x, y - 2.0, z - 1.0)).getBlock() != WitherSpawnAnimationModBlocks.NO_W.get()
            && (
               !world.getBlockState(BlockPos.containing(x, y - 2.0, z - 1.0)).is(BlockTags.create(ResourceLocation.parse("minecraft:replaceable_spawn_fix")))
                  || !WitherSpawnAnimationModVariables.MapVariables.get(world).spawn_fix
            )
         || world.getBlockState(BlockPos.containing(x, y, z + 2.0)).getBlock() != Blocks.WITHER_SKELETON_SKULL
            && world.getBlockState(BlockPos.containing(x, y, z + 2.0)).getBlock() != Blocks.WITHER_SKELETON_WALL_SKULL
         || world.getBlockState(BlockPos.containing(x, y - 1.0, z + 2.0)).getBlock() != Blocks.SOUL_SAND
         || world.getBlockState(BlockPos.containing(x, y - 2.0, z + 2.0)).getBlock() != Blocks.AIR
            && world.getBlockState(BlockPos.containing(x, y - 2.0, z + 2.0)).getBlock() != WitherSpawnAnimationModBlocks.NO_W.get()
            && (
               !world.getBlockState(BlockPos.containing(x, y - 2.0, z + 2.0)).is(BlockTags.create(ResourceLocation.parse("minecraft:replaceable_spawn_fix")))
                  || !WitherSpawnAnimationModVariables.MapVariables.get(world).spawn_fix
            )
         || world.getBlockState(BlockPos.containing(x, y - 2.0, z + 3.0)).getBlock() != Blocks.AIR
            && world.getBlockState(BlockPos.containing(x, y - 2.0, z + 3.0)).getBlock() != WitherSpawnAnimationModBlocks.NO_W.get()
            && (
               !world.getBlockState(BlockPos.containing(x, y - 2.0, z + 3.0)).is(BlockTags.create(ResourceLocation.parse("minecraft:replaceable_spawn_fix")))
                  || !WitherSpawnAnimationModVariables.MapVariables.get(world).spawn_fix
            )
         || world.getBlockState(BlockPos.containing(x, y - 1.0, z + 3.0)).getBlock() != Blocks.AIR
            && world.getBlockState(BlockPos.containing(x, y - 1.0, z + 3.0)).getBlock() != WitherSpawnAnimationModBlocks.NO_W.get()
            && (
               !world.getBlockState(BlockPos.containing(x, y - 1.0, z + 3.0)).is(BlockTags.create(ResourceLocation.parse("minecraft:replaceable_spawn_fix")))
                  || !WitherSpawnAnimationModVariables.MapVariables.get(world).spawn_fix
            )
         || world.getBlockState(BlockPos.containing(x, y, z + 3.0)).getBlock() != Blocks.AIR
            && world.getBlockState(BlockPos.containing(x, y, z + 3.0)).getBlock() != WitherSpawnAnimationModBlocks.NO_W.get()
            && (
               !world.getBlockState(BlockPos.containing(x, y, z + 3.0)).is(BlockTags.create(ResourceLocation.parse("minecraft:replaceable_spawn_fix")))
                  || !WitherSpawnAnimationModVariables.MapVariables.get(world).spawn_fix
            )) {
         return false;
      }

      nope = false;
      world.setBlock(BlockPos.containing(x, y, z), Blocks.AIR.defaultBlockState(), 3);
      world.setBlock(BlockPos.containing(x, y, z + 1.0), Blocks.AIR.defaultBlockState(), 3);
      world.setBlock(BlockPos.containing(x, y, z + 2.0), Blocks.AIR.defaultBlockState(), 3);
      world.setBlock(BlockPos.containing(x, y - 1.0, z), Blocks.AIR.defaultBlockState(), 3);
      world.setBlock(BlockPos.containing(x, y - 1.0, z + 1.0), Blocks.AIR.defaultBlockState(), 3);
      world.setBlock(BlockPos.containing(x, y - 1.0, z + 2.0), Blocks.AIR.defaultBlockState(), 3);
      world.setBlock(BlockPos.containing(x, y - 2.0, z + 1.0), Blocks.AIR.defaultBlockState(), 3);
      return true;
   }
}
