package artifacts.mixin.ability.fluidcollision;

import artifacts.component.SwimData;
import artifacts.equipment.EquipmentHelper;
import artifacts.platform.PlatformServices;
import artifacts.registry.ModDataComponents;
import artifacts.registry.ModSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {
   @Shadow
   private Level level;
   @Shadow
   @Final
   protected RandomSource random;
   @Shadow
   private EntityDimensions dimensions;

   @Shadow
   public abstract double getX();

   @Shadow
   public abstract double getY();

   @Shadow
   public abstract double getZ();

   @Shadow
   public abstract Vec3 getDeltaMovement();

   @Inject(method = "playStepSound(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V", at = @At("HEAD"))
   private void playWaterStepSound(BlockPos pos, BlockState blockState, CallbackInfo callbackInfo) {
      if (blockState.liquid() && this.artifacts$isRunningWithAquaDashers()) {
         ((LivingEntity)this).playSound((SoundEvent)ModSoundEvents.WATER_STEP.value(), 0.15F, 1.0F);
      }
   }

   @Inject(method = "spawnSprintParticle()V", at = @At("HEAD"))
   private void spawnWaterSprintParticle(CallbackInfo callbackInfo) {
      if (this.artifacts$isRunningWithAquaDashers()) {
         BlockPos pos = new BlockPos(Mth.floor(this.getX()), Mth.floor(this.getY() - 0.2), Mth.floor(this.getZ()));
         BlockState blockstate = this.level.getBlockState(pos);
         if (blockstate.getRenderShape() == RenderShape.INVISIBLE) {
            Vec3 motion = this.getDeltaMovement().multiply(-4.0, 0.0, -4.0);
            ParticleOptions particle;
            if (blockstate.getFluidState().is(FluidTags.LAVA)) {
               motion = motion.add(0.0, 1.0, 0.0);
               if (this.random.nextInt(3) == 0) {
                  particle = ParticleTypes.LAVA;
               } else {
                  particle = ParticleTypes.FLAME;
                  motion = motion.multiply(0.2, 0.03, 0.2);
               }
            } else {
               if (blockstate.getFluidState().isEmpty()) {
                  return;
               }

               if (this.random.nextInt(3) == 0) {
                  particle = ParticleTypes.BUBBLE;
               } else {
                  particle = ParticleTypes.SPLASH;
                  motion = motion.add(0.0, 1.5, 0.0);
               }
            }

            this.level
               .addParticle(
                  particle,
                  this.getX() + (this.random.nextDouble() - 0.5) * this.dimensions.width(),
                  this.getY(),
                  this.getZ() + (this.random.nextDouble() - 0.5) * this.dimensions.width(),
                  motion.x,
                  motion.y,
                  motion.z
               );
         }
      }
   }

   @Unique
   private boolean artifacts$isRunningWithAquaDashers() {
      if (!(this instanceof LivingEntity entity)) {
         return false;
      } else {
         SwimData swimData = PlatformServices.platformHelper.getSwimData(entity);
         return swimData != null
            && EquipmentHelper.hasAbilityActive(ModDataComponents.FLUID_COLLISION.get(), entity, true)
            && entity.isSprinting()
            && !swimData.isWet();
      }
   }
}
