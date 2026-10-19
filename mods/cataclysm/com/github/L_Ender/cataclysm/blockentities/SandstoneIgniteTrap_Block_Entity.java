package com.github.L_Ender.cataclysm.blockentities;

import com.github.L_Ender.cataclysm.blocks.Sandstone_Ignite_Trap;
import com.github.L_Ender.cataclysm.init.ModParticle;
import com.github.L_Ender.cataclysm.init.ModTag;
import com.github.L_Ender.cataclysm.init.ModTileentites;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class SandstoneIgniteTrap_Block_Entity extends BlockEntity {
   public int tickCount;
   private final RandomSource random = RandomSource.create();

   public SandstoneIgniteTrap_Block_Entity(BlockPos pos, BlockState state) {
      super((BlockEntityType)ModTileentites.SANDSTONE_IGNITE_TRAP.get(), pos, state);
   }

   public static void commonTick(Level level, BlockPos pos, BlockState state, SandstoneIgniteTrap_Block_Entity entity) {
      entity.tick(level, pos);
   }

   public void tick(Level level, BlockPos pos) {
      boolean LIT = false;
      if (this.getBlockState().getBlock() instanceof Sandstone_Ignite_Trap) {
         LIT = (Boolean)this.getBlockState().getValue(Sandstone_Ignite_Trap.LIT);
      }

      if (LIT) {
         this.tickCount++;
         double x = pos.getX();
         double y = pos.above().getY();
         double z = pos.getZ();
         if (level.isClientSide()) {
            level.addParticle((ParticleOptions)ModParticle.TRAP_FLAME.get(), x + 0.5, y, z + 0.5, 0.0, 0.5, 0.0);
         } else if (this.tickCount % 5 == 0) {
            for (LivingEntity entity : level.getEntitiesOfClass(
               LivingEntity.class, new AABB(pos.offset(-1, 0, -1).getCenter(), pos.offset(1, 6, 1).getCenter())
            )) {
               if (!entity.getType().is(ModTag.TEAM_ANCIENT_REMNANT) && !entity.fireImmune()) {
                  entity.hurt(entity.level().damageSources().inFire(), 5.0F);
                  entity.igniteForSeconds(5.0F);
               }
            }
         }
      } else {
         this.tickCount = 0;
      }
   }
}
