package com.bobmowzie.mowziesmobs.server.entity.effects.geomancy;

import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;

public class EntityRockSling extends EntityBoulderProjectile implements GeoEntity {
   private Vec3 launchVec;
   private static final RawAnimation ROLL_ANIM = RawAnimation.begin().thenLoop("roll");

   public EntityRockSling(EntityType<? extends EntityRockSling> type, Level worldIn) {
      super(type, worldIn);
      this.setDamage(3);
   }

   public EntityRockSling(
      EntityType<? extends EntityBoulderProjectile> type,
      Level world,
      LivingEntity caster,
      BlockState blockState,
      BlockPos pos,
      EntityGeomancyBase.GeomancyTier tier
   ) {
      super(type, world, caster, blockState, pos, tier);
      this.setDamage(3);
   }

   @NotNull
   @Override
   protected AABB makeBoundingBox() {
      return this.dimensions.makeBoundingBox(this.position());
   }

   public boolean canBeHitByProjectile() {
      return false;
   }

   @Override
   public void tick() {
      super.tick();
      if (this.tickCount > 15 + this.random.nextInt(10) && this.launchVec != null) {
         this.setDeltaMovement(this.launchVec.normalize().multiply(2.0F + this.random.nextFloat() / 5.0F, 2.0, 2.0F + this.random.nextFloat() / 5.0F));
         this.setTravelling(true);
      }

      if (this.tickCount > 45) {
         this.discard();
      }
   }

   @Override
   protected double getDamageMult() {
      return (Double)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.SCULPTOR_STAFF.attackMultiplier.get();
   }

   public void setLaunchVec(Vec3 vec) {
      this.launchVec = vec;
   }

   @Override
   public boolean canBeCollidedWith() {
      return false;
   }

   @Override
   protected void findRidingEntities() {
   }

   @Override
   protected void doPopupEntities() {
   }

   @Override
   public void registerControllers(ControllerRegistrar controllers) {
      AnimationController<EntityRockSling> controller = new AnimationController(this, "controller", 0, event -> {
         event.getController().setAnimation(ROLL_ANIM);
         return PlayState.CONTINUE;
      });
      controllers.add(controller);
   }
}
