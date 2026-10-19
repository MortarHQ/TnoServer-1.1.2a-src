package twilightforest.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import twilightforest.block.FireJetBlock;
import twilightforest.enums.FireJetVariant;
import twilightforest.init.TFBlockEntities;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFDamageTypes;
import twilightforest.init.TFParticleType;
import twilightforest.init.TFSounds;

public class FireJetBlockEntity extends BlockEntity {
   private int counter = 0;

   public FireJetBlockEntity(BlockPos pos, BlockState state) {
      super((BlockEntityType)TFBlockEntities.FLAME_JET.get(), pos, state);
   }

   public static void tick(Level level, BlockPos pos, BlockState state, FireJetBlockEntity te) {
      if (state.getBlock() == TFBlocks.FIRE_JET.get() || state.getBlock() == TFBlocks.ENCASED_FIRE_JET.get()) {
         switch ((FireJetVariant)state.getValue(FireJetBlock.STATE)) {
            case POPPING:
               te.tickPopping(level, pos, state, te);
               break;
            case FLAME:
               te.tickFlame(level, pos, state, te);
         }
      }
   }

   private void tickPopping(Level level, BlockPos pos, BlockState state, FireJetBlockEntity te) {
      if (++te.counter >= 80) {
         te.counter = 0;
         if (!level.isClientSide()) {
            if (state.getBlock() != TFBlocks.FIRE_JET.get() && state.getBlock() != TFBlocks.ENCASED_FIRE_JET.get()) {
               level.removeBlock(pos, false);
            } else {
               level.setBlockAndUpdate(pos, (BlockState)state.setValue(FireJetBlock.STATE, FireJetVariant.FLAME));
            }
         }
      } else if (te.counter % 20 == 0) {
         for (int i = 0; i < 8; i++) {
            level.addParticle(ParticleTypes.LAVA, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, 0.0, 0.0, 0.0);
         }

         level.playSound(
            null, pos, (SoundEvent)TFSounds.JET_POP.get(), SoundSource.BLOCKS, 0.2F + level.random.nextFloat() * 0.2F, 0.9F + level.random.nextFloat() * 0.15F
         );
      }
   }

   private void tickFlame(Level level, BlockPos pos, BlockState state, FireJetBlockEntity te) {
      double x = pos.getX();
      double y = pos.above().getY();
      double z = pos.getZ();
      if (++te.counter > 60) {
         te.counter = 0;
         if (!level.isClientSide()) {
            if (state.getBlock() != TFBlocks.FIRE_JET.get() && state.getBlock() != TFBlocks.ENCASED_FIRE_JET.get()) {
               level.removeBlock(pos, false);
            } else {
               level.setBlockAndUpdate(
                  pos,
                  (BlockState)state.setValue(FireJetBlock.STATE, state.getBlock() == TFBlocks.FIRE_JET.get() ? FireJetVariant.IDLE : FireJetVariant.TIMEOUT)
               );
            }
         }
      }

      if (level.isClientSide()) {
         if (te.counter % 2 == 0) {
            level.addParticle(ParticleTypes.LARGE_SMOKE, x + 0.5, y + 1.0, z + 0.5, 0.0, 0.0, 0.0);
            level.addParticle((ParticleOptions)TFParticleType.LARGE_FLAME.get(), x + 0.5, y + 1.0, z + 0.5, 0.0, 0.5, 0.0);
            level.addParticle((ParticleOptions)TFParticleType.LARGE_FLAME.get(), x - 0.5, y + 1.0, z + 0.5, 0.05, 0.5, 0.0);
            level.addParticle((ParticleOptions)TFParticleType.LARGE_FLAME.get(), x + 0.5, y + 1.0, z - 0.5, 0.0, 0.5, 0.05);
            level.addParticle((ParticleOptions)TFParticleType.LARGE_FLAME.get(), x + 1.5, y + 1.0, z + 0.5, -0.05, 0.5, 0.0);
            level.addParticle((ParticleOptions)TFParticleType.LARGE_FLAME.get(), x + 0.5, y + 1.0, z + 1.5, 0.0, 0.5, -0.05);
         }

         if (te.counter % 4 == 0) {
            level.playLocalSound(
               x + 0.5,
               y + 0.5,
               z + 0.5,
               (SoundEvent)TFSounds.JET_ACTIVE.get(),
               SoundSource.BLOCKS,
               1.0F + level.random.nextFloat(),
               level.random.nextFloat() * 0.7F + 0.3F,
               false
            );
         } else if (te.counter == 1) {
            level.playLocalSound(
               x + 0.5,
               y + 0.5,
               z + 0.5,
               (SoundEvent)TFSounds.JET_START.get(),
               SoundSource.BLOCKS,
               1.0F + level.random.nextFloat(),
               level.random.nextFloat() * 0.7F + 0.3F,
               false
            );
         }
      }

      if (!level.isClientSide() && te.counter % 5 == 0) {
         for (Entity entity : level.getEntitiesOfClass(Entity.class, new AABB(pos.offset(-2, 0, -2).getCenter(), pos.offset(2, 4, 2).getCenter()))) {
            if (!entity.fireImmune()) {
               entity.hurt(TFDamageTypes.getDamageSource(level, TFDamageTypes.FIRE_JET), 2.0F);
               entity.setRemainingFireTicks(300);
            }
         }
      }
   }
}
