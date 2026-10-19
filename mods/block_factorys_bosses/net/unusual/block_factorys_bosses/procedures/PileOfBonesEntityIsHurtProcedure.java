package net.unusual.block_factorys_bosses.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.entity.decoration.PileOfBonesEntity;

public class PileOfBonesEntityIsHurtProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         double distance = 0.0;
         if (0 == (entity instanceof PileOfBonesEntity _datEntI ? (Integer)_datEntI.getEntityData().get(PileOfBonesEntity.DATA_hit_animation_time) : 0)) {
            if (0 < (entity instanceof PileOfBonesEntity _datEntI ? (Integer)_datEntI.getEntityData().get(PileOfBonesEntity.DATA_remaning_hit) : 0)) {
               if (entity instanceof PileOfBonesEntity _datEntSetI) {
                  _datEntSetI.getEntityData()
                     .set(
                        PileOfBonesEntity.DATA_remaning_hit,
                        (entity instanceof PileOfBonesEntity _datEntI ? (Integer)_datEntI.getEntityData().get(PileOfBonesEntity.DATA_remaning_hit) : 0) - 1
                     );
               }

               if (!world.isClientSide() && world instanceof Level _level) {
                  if (!_level.isClientSide()) {
                     _level.playSound(null, BlockPos.containing(x, y, z), SoundEvents.WITHER_SKELETON_HURT, SoundSource.NEUTRAL, 1.0F, -2.0F);
                  } else {
                     _level.playLocalSound(x, y, z, SoundEvents.WITHER_SKELETON_HURT, SoundSource.NEUTRAL, 1.0F, -2.0F, false);
                  }
               }

               if (entity instanceof PileOfBonesEntity _datEntSetI) {
                  _datEntSetI.getEntityData().set(PileOfBonesEntity.DATA_hit_animation_time, 4);
               }
            } else {
               if (!entity.level().isClientSide()) {
                  entity.discard();
               }

               if (world instanceof Level _level) {
                  if (!_level.isClientSide()) {
                     _level.playSound(null, BlockPos.containing(x, y, z), SoundEvents.WITHER_SKELETON_DEATH, SoundSource.NEUTRAL, 1.0F, -2.0F);
                  } else {
                     _level.playLocalSound(x, y, z, SoundEvents.WITHER_SKELETON_DEATH, SoundSource.NEUTRAL, 1.0F, -2.0F, false);
                  }
               }

               for (int index0 = 0; index0 < Mth.nextInt(RandomSource.create(), 8, 12); index0++) {
                  if (world instanceof ServerLevel _level) {
                     FallingBlockEntity.fall(
                        _level,
                        BlockPos.containing(
                           x + Mth.nextInt(RandomSource.create(), -4, 4),
                           y + Mth.nextInt(RandomSource.create(), 0, 3),
                           z + Mth.nextInt(RandomSource.create(), -4, 4)
                        ),
                        (new Object() {
                              public BlockState with(BlockState _bs, Direction newValue) {
                                 if (_bs.getBlock().getStateDefinition().getProperty("facing") instanceof DirectionProperty _dp
                                    && _dp.getPossibleValues().contains(newValue)) {
                                    return (BlockState)_bs.setValue(_dp, newValue);
                                 } else {
                                    return _bs.getBlock().getStateDefinition().getProperty("axis") instanceof EnumProperty _ep
                                          && _ep.getPossibleValues().contains(newValue.getAxis())
                                       ? (BlockState)_bs.setValue(_ep, newValue.getAxis())
                                       : _bs;
                                 }
                              }
                           })
                           .with(
                              ((Block)BuiltInRegistries.BLOCK
                                    .getOrCreateTag(BlockTags.create(BossesRise.prefix("bone_remains")))
                                    .getRandomElement(RandomSource.create())
                                    .orElseGet(() -> BuiltInRegistries.BLOCK.wrapAsHolder(Blocks.AIR))
                                    .value())
                                 .defaultBlockState(),
                              Direction.getRandom(RandomSource.create())
                           )
                     );
                  }
               }
            }
         }
      }
   }
}
