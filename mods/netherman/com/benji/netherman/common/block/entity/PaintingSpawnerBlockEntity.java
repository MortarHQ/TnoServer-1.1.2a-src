package com.benji.netherman.common.block.entity;

import com.benji.netherman.common.block.PaintingSpawnerBlock;
import com.benji.netherman.init.ModBlockEntities;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class PaintingSpawnerBlockEntity extends BlockEntity {
   private int waitTicks = 0;

   public PaintingSpawnerBlockEntity(BlockPos pos, BlockState state) {
      super((BlockEntityType)ModBlockEntities.PAINTING_SPAWNER.get(), pos, state);
   }

   public static void tick(Level level, BlockPos pos, BlockState state, PaintingSpawnerBlockEntity entity) {
      if (!level.isClientSide()) {
         entity.waitTicks++;
         if (entity.waitTicks >= 10) {
            Player player = level.getNearestPlayer(pos.getX(), pos.getY(), pos.getZ(), 200.0, false);
            if (player != null && player.isCreative()) {
               entity.waitTicks = 0;
            } else {
               if (state.getBlock() instanceof PaintingSpawnerBlock spawnerBlock) {
                  Direction facing = (Direction)state.getValue(PaintingSpawnerBlock.FACING);
                  Registry<PaintingVariant> registry = level.registryAccess().registryOrThrow(Registries.PAINTING_VARIANT);
                  Optional<Reference<PaintingVariant>> holderOptional = registry.getHolder(spawnerBlock.getPaintingKey());
                  if (holderOptional.isPresent()) {
                     level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                     Painting painting = new Painting(level, pos, facing, (Holder)holderOptional.get());
                     if (painting.survives()) {
                        level.addFreshEntity(painting);
                     } else {
                        level.setBlock(pos, state, 3);
                        entity.waitTicks = 0;
                     }
                  } else {
                     level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                  }
               }
            }
         }
      }
   }
}
