package twilightforest.dispenser;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.AABB;
import twilightforest.init.TFSounds;

public class FeatherFanDispenseBehavior extends DefaultDispenseItemBehavior {
   boolean fired = false;

   protected ItemStack execute(BlockSource source, ItemStack stack) {
      ServerLevel level = source.level();
      BlockPos blockpos = source.pos().relative((Direction)source.state().getValue(DispenserBlock.FACING));
      int damage = stack.getMaxDamage() - stack.getDamageValue();
      List<LivingEntity> thingsToPush = level.getEntitiesOfClass(LivingEntity.class, new AABB(blockpos).inflate(3.0), EntitySelector.NO_SPECTATORS);
      if (thingsToPush.size() < damage) {
         for (Entity entity : thingsToPush) {
            Vec3i lookVec = ((Direction)level.getBlockState(source.pos()).getValue(DispenserBlock.FACING)).getNormal();
            if (entity.isPushable() || entity instanceof ItemEntity) {
               entity.setDeltaMovement(lookVec.getX(), lookVec.getY(), lookVec.getZ());
               stack.hurtAndBreak(1, level, null, item -> {});
            }
         }

         this.fired = true;
      }

      return stack;
   }

   protected void playSound(BlockSource source) {
      if (this.fired) {
         RandomSource random = source.level().getRandom();
         source.level()
            .playSound(
               null, source.pos(), (SoundEvent)TFSounds.FAN_WHOOSH.get(), SoundSource.BLOCKS, 1.0F + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F
            );
         this.fired = false;
      } else {
         source.level().levelEvent(1001, source.pos(), 0);
      }
   }

   protected void playAnimation(BlockSource source, Direction direction) {
      BlockPos blockpos = source.pos().relative((Direction)source.state().getValue(DispenserBlock.FACING));
      Level world = source.level();
      RandomSource random = world.getRandom();
      int j1 = direction.getStepX();
      int j2 = direction.getStepY();
      int k2 = direction.getStepZ();
      double d18 = blockpos.getX() + j1 * 0.6 + 0.5;
      double d24 = blockpos.getY() + j2 * 0.6 + 0.5;
      double d28 = blockpos.getZ() + k2 * 0.6 + 0.5;

      for (int i = 0; i < 30; i++) {
         double d4 = random.nextDouble() * 0.2 + 0.01;
         double d6 = d18 + j1 * 0.01 + (random.nextDouble() - 0.5) * k2 * 0.5;
         double d8 = d24 + j2 * 0.01 + (random.nextDouble() - 0.5) * j2 * 0.5;
         double d30 = d28 + k2 * 0.01 + (random.nextDouble() - 0.5) * j1 * 0.5;
         double d9 = j1 * d4 + random.nextGaussian() * 0.01;
         double d10 = j2 * d4 + random.nextGaussian() * 0.01;
         double d11 = k2 * d4 + random.nextGaussian() * 0.01;
         world.addParticle(ParticleTypes.CLOUD, d6, d8, d30, d9, d10, d11);
      }
   }
}
