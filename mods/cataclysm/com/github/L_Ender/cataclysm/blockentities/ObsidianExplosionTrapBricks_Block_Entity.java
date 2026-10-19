package com.github.L_Ender.cataclysm.blockentities;

import com.github.L_Ender.cataclysm.blocks.ObsidianExplosionTrapBricks;
import com.github.L_Ender.cataclysm.init.ModTag;
import com.github.L_Ender.cataclysm.init.ModTileentites;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ObsidianExplosionTrapBricks_Block_Entity extends BlockEntity {
   public int tickCount;

   public ObsidianExplosionTrapBricks_Block_Entity(BlockPos pos, BlockState state) {
      super((BlockEntityType)ModTileentites.OBSIDIAN_EXPLOSION_TRAP_BRICKS.get(), pos, state);
   }

   public static void commonTick(Level level, BlockPos pos, BlockState state, ObsidianExplosionTrapBricks_Block_Entity entity) {
      entity.tick();
   }

   public void tick() {
      boolean LIT = false;
      if (this.getBlockState().getBlock() instanceof ObsidianExplosionTrapBricks) {
         LIT = (Boolean)this.getBlockState().getValue(ObsidianExplosionTrapBricks.LIT);
      }

      if (LIT) {
         this.tickCount++;
         float x = this.getBlockPos().getX() + 0.5F;
         float y = this.getBlockPos().getY();
         float z = this.getBlockPos().getZ() + 0.5F;
         float f = 5.0F;
         if (this.tickCount < 80) {
            for (LivingEntity inRange : this.level
               .getEntitiesOfClass(LivingEntity.class, new AABB((double)x - f, (double)y - f, (double)z - f, (double)x + f, (double)y + f, (double)z + f))) {
               if ((!(inRange instanceof Player) || !((Player)inRange).getAbilities().invulnerable) && !inRange.getType().is(ModTag.TRAP_BLOCK_NOT_DETECTED)) {
                  Vec3 diff = inRange.position().subtract(Vec3.atCenterOf(this.getBlockPos().offset(0, 0, 0)));
                  diff = diff.normalize().scale(0.06);
                  inRange.setDeltaMovement(inRange.getDeltaMovement().subtract(diff));
               }
            }

            if (this.level.isClientSide) {
               for (int i = 0; i < 3; i++) {
                  int j = this.level.random.nextInt(2) * 2 - 1;
                  int k = this.level.random.nextInt(2) * 2 - 1;
                  double d0 = this.worldPosition.getX() + 0.5 + 0.25 * j;
                  double d1 = this.worldPosition.getY() + this.level.random.nextFloat();
                  double d2 = this.worldPosition.getZ() + 0.5 + 0.25 * k;
                  double d3 = this.level.random.nextFloat() * j;
                  double d4 = (this.level.random.nextFloat() - 0.5) * 0.125;
                  double d5 = this.level.random.nextFloat() * k;
                  this.level.addParticle(ParticleTypes.PORTAL, d0, d1, d2, d3, d4, d5);
               }
            }
         }

         if (this.tickCount == 80 && !this.level.isClientSide) {
            this.level.explode(null, x, y + 1.0F, z, 3.0F, ExplosionInteraction.NONE);
         }
      } else {
         this.tickCount = 0;
      }
   }
}
