package com.bobmowzie.mowziesmobs.server.entity.lantern;

import com.bobmowzie.mowziesmobs.client.particle.ParticleCloud;
import com.bobmowzie.mowziesmobs.client.particle.ParticleHandler;
import com.bobmowzie.mowziesmobs.client.particle.ParticleOrb;
import com.bobmowzie.mowziesmobs.client.particle.ParticleVanillaCloudExtended;
import com.bobmowzie.mowziesmobs.client.particle.util.AdvancedParticleBase;
import com.bobmowzie.mowziesmobs.client.particle.util.ParticleComponent;
import com.bobmowzie.mowziesmobs.server.ai.animation.AnimationDieAI;
import com.bobmowzie.mowziesmobs.server.ai.animation.AnimationTakeDamage;
import com.bobmowzie.mowziesmobs.server.ai.animation.SimpleAnimationAI;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.MowzieEntity;
import com.bobmowzie.mowziesmobs.server.entity.MowzieLLibraryEntity;
import com.bobmowzie.mowziesmobs.server.loot.LootTableHandler;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import com.ilexiconn.llibrary.server.animation.Animation;
import com.ilexiconn.llibrary.server.animation.AnimationHandler;
import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.MoveControl.Operation;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class EntityLantern extends MowzieLLibraryEntity {
   public static final Animation DIE_ANIMATION = Animation.create(25);
   public static final Animation HURT_ANIMATION = Animation.create(10);
   public static final Animation PUFF_ANIMATION = Animation.create(28);
   private static final Animation[] ANIMATIONS = new Animation[]{DIE_ANIMATION, HURT_ANIMATION, PUFF_ANIMATION};
   public Vec3 dir;
   private int groundDist = 1;
   private Vec3[] pos;

   public EntityLantern(EntityType<? extends EntityLantern> type, Level world) {
      super(type, world);
      this.dir = null;
      if (world.isClientSide) {
         this.pos = new Vec3[1];
      }

      this.moveControl = new EntityLantern.MoveHelperController(this);
   }

   protected void registerGoals() {
      super.registerGoals();
      this.goalSelector.addGoal(2, new SimpleAnimationAI<>(this, PUFF_ANIMATION, false));
      this.goalSelector.addGoal(3, new AnimationTakeDamage<>(this));
      this.goalSelector.addGoal(1, new AnimationDieAI<>(this));
      this.goalSelector.addGoal(5, new EntityLantern.RandomFlyGoal(this));
   }

   public static Builder createAttributes() {
      return MowzieEntity.createAttributes().add(Attributes.MAX_HEALTH, 4.0).add(Attributes.FLYING_SPEED, 0.3).add(Attributes.MOVEMENT_SPEED, 0.2);
   }

   public float getLightLevelDependentMagicValue() {
      return 1.572888E7F;
   }

   @Override
   public void tick() {
      super.tick();
      if (this.getAnimation() == PUFF_ANIMATION && this.getAnimationTick() == 7) {
         if (this.groundDist == 0) {
            this.groundDist = 1;
         }

         this.setDeltaMovement(this.getDeltaMovement().add(0.0, 0.2 + 0.2 / this.groundDist, 0.0));
         if (!this.level().isClientSide) {
            if (this.getMoveHelperController() != null && this.getMoveHelperController().isMovingTo()) {
               Vec3 lvt_1_1_ = new Vec3(
                  this.getMoveControl().getWantedX() - this.getX(),
                  this.getMoveControl().getWantedY() - this.getY(),
                  this.getMoveControl().getWantedZ() - this.getZ()
               );
               double lvt_2_1_ = lvt_1_1_.length();
               lvt_1_1_ = lvt_1_1_.normalize();
               if (this.getMoveHelperController().canReach(lvt_1_1_, Mth.ceil(lvt_2_1_))) {
                  this.setDeltaMovement(this.getDeltaMovement().add(lvt_1_1_.scale(0.2)));
               }
            }
         } else {
            for (int i = 0; i < 5; i++) {
               ParticleVanillaCloudExtended.spawnVanillaCloud(
                  this.level(),
                  this.getX(),
                  this.getY() + 0.3,
                  this.getZ(),
                  -this.getDeltaMovement().x() * 0.2 + 0.1 * (this.random.nextFloat() - 0.5),
                  -this.getDeltaMovement().y() * 0.2 + 0.1 * (this.random.nextFloat() - 0.5),
                  -this.getDeltaMovement().z() * 0.2 + 0.1 * (this.random.nextFloat() - 0.5),
                  0.8 + this.random.nextDouble() * 1.0,
                  0.63671875,
                  0.96484375,
                  0.2890625,
                  0.95,
                  30.0
               );
            }

            for (int i = 0; i < 8; i++) {
               AdvancedParticleBase.spawnParticle(
                  this.level(),
                  ParticleHandler.PIXEL,
                  this.getX(),
                  this.getY() + 0.3,
                  this.getZ(),
                  -this.getDeltaMovement().x() * 0.2 + 0.2 * (this.random.nextFloat() - 0.5),
                  -this.getDeltaMovement().y() * 0.2 + 0.1 * (this.random.nextFloat() - 0.5),
                  -this.getDeltaMovement().z() * 0.2 + 0.2 * (this.random.nextFloat() - 0.5),
                  true,
                  0.0,
                  0.0,
                  0.0,
                  0.0,
                  4.0,
                  0.63671875,
                  0.96484375,
                  0.2890625,
                  1.0,
                  0.9,
                  17.0F + this.random.nextFloat() * 10.0F,
                  true,
                  true,
                  new ParticleComponent[]{
                     new ParticleComponent.PropertyControl(
                        ParticleComponent.PropertyControl.EnumParticleProperty.SCALE,
                        new ParticleComponent.KeyTrack(new float[]{4.0F, 0.0F}, new float[]{0.8F, 1.0F}),
                        false
                     )
                  }
               );
            }
         }

         this.playSound((SoundEvent)MMSounds.ENTITY_LANTERN_PUFF.get(), 0.6F, 1.0F + this.random.nextFloat() * 0.2F);
      }

      if (!this.level().isClientSide && this.getAnimation() == NO_ANIMATION && (this.groundDist < 5 || this.random.nextInt(13) == 0 && this.groundDist < 16)) {
         AnimationHandler.INSTANCE.sendAnimationMessage(this, PUFF_ANIMATION);
      }

      if (this.groundDist >= 2) {
         this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.0055, 0.0));
      }

      if (this.tickCount % 5 == 0) {
         BlockPos checkPos = this.blockPosition();

         int i;
         for (i = 0; i < 16 && this.level().getBlockState(checkPos).getBlock() == Blocks.AIR; i++) {
            checkPos = checkPos.below();
         }

         this.groundDist = i;
      }

      if (this.level().isClientSide && (Boolean)ConfigHandler.CLIENT.glowEffect.get()) {
         this.pos[0] = this.position().add(0.0, this.getBbHeight() * 0.8, 0.0);
         if (this.tickCount % 70 == 0) {
            AdvancedParticleBase.spawnParticle(
               this.level(),
               ParticleHandler.GLOW,
               this.pos[0].x,
               this.pos[0].y,
               this.pos[0].z,
               0.0,
               0.0,
               0.0,
               true,
               0.0,
               0.0,
               0.0,
               0.0,
               20.0,
               0.8,
               0.95,
               0.35,
               1.0,
               1.0,
               70.0,
               true,
               true,
               new ParticleComponent[]{
                  new ParticleComponent.PropertyControl(
                     ParticleComponent.PropertyControl.EnumParticleProperty.ALPHA,
                     new ParticleComponent.KeyTrack(new float[]{0.0F, 0.8F, 0.0F}, new float[]{0.0F, 0.5F, 1.0F}),
                     false
                  ),
                  new ParticleComponent.PinLocation(this.pos)
               }
            );
         }
      }
   }

   @Override
   protected void tickDeath() {
      super.tickDeath();
      if (this.getAnimationTick() == 1 && this.level().isClientSide) {
         for (int i = 0; i < 8; i++) {
            this.level()
               .addParticle(
                  ParticleTypes.ITEM_SLIME,
                  this.getX(),
                  this.getY(),
                  this.getZ(),
                  0.2 * (this.random.nextFloat() - 0.5),
                  0.2 * (this.random.nextFloat() - 0.5),
                  0.2 * (this.random.nextFloat() - 0.5)
               );
            this.level()
               .addParticle(
                  new ParticleCloud.Data(
                     0.63671875F, 0.96484375F, 0.2890625F, 10.0F + this.random.nextFloat() * 20.0F, 30, ParticleCloud.EnumCloudBehavior.GROW, 0.9F
                  ),
                  this.getX(),
                  this.getY() + 0.3,
                  this.getZ(),
                  0.25 * (this.random.nextFloat() - 0.5),
                  0.25 * (this.random.nextFloat() - 0.5),
                  0.25 * (this.random.nextFloat() - 0.5)
               );
            this.level()
               .addParticle(
                  ParticleOrb.Data.create(0.63671875F, 0.96484375F, 0.2890625F, 1.5F, 25),
                  this.getX(),
                  this.getY() + 0.3,
                  this.getZ(),
                  0.2F * (this.random.nextFloat() - 0.5F),
                  0.2F * (this.random.nextFloat() - 0.5F),
                  0.2F * (this.random.nextFloat() - 0.5F)
               );
         }
      }

      if (this.getAnimationTick() == 2) {
         this.playSound((SoundEvent)MMSounds.ENTITY_LANTERN_POP.get(), 1.0F, 0.8F + this.random.nextFloat() * 0.4F);
      }
   }

   protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
   }

   public void travel(Vec3 movement) {
      if (this.isInWater()) {
         this.moveRelative(0.02F, movement);
         this.move(MoverType.SELF, this.getDeltaMovement());
         this.setDeltaMovement(this.getDeltaMovement().scale(0.8F));
      } else if (this.isInLava()) {
         this.moveRelative(0.02F, movement);
         this.move(MoverType.SELF, this.getDeltaMovement());
         this.setDeltaMovement(this.getDeltaMovement().scale(0.5));
      } else {
         BlockPos ground = BlockPos.containing(this.getX(), this.getBoundingBox().minY - 1.0, this.getZ());
         float f = 0.91F;
         if (this.onGround()) {
            f = this.level().getBlockState(ground).getFriction(this.level(), ground, this) * 0.91F;
         }

         float f1 = 0.16277137F / (f * f * f);
         f = 0.91F;
         if (this.onGround()) {
            f = this.level().getBlockState(ground).getFriction(this.level(), ground, this) * 0.91F;
         }

         this.moveRelative(this.onGround() ? 0.1F * f1 : 0.02F, movement);
         this.move(MoverType.SELF, this.getDeltaMovement());
         this.setDeltaMovement(this.getDeltaMovement().scale(f));
      }

      this.calculateEntityAnimation(true);
   }

   @Override
   protected ConfigHandler.SpawnConfig getSpawnConfig() {
      return ConfigHandler.COMMON.MOBS.LANTERN.spawnConfig;
   }

   public boolean onClimbable() {
      return false;
   }

   @Override
   public Animation getDeathAnimation() {
      return DIE_ANIMATION;
   }

   @Override
   public Animation getHurtAnimation() {
      return HURT_ANIMATION;
   }

   @Override
   public Animation[] getAnimations() {
      return ANIMATIONS;
   }

   protected ResourceKey<LootTable> getDefaultLootTable() {
      return LootTableHandler.LANTERN;
   }

   @Override
   protected ConfigHandler.CombatConfig getCombatConfig() {
      return ConfigHandler.COMMON.MOBS.LANTERN.combatConfig;
   }

   public EntityLantern.MoveHelperController getMoveHelperController() {
      return this.getMoveControl() instanceof EntityLantern.MoveHelperController ? (EntityLantern.MoveHelperController)super.getMoveControl() : null;
   }

   static class MoveHelperController extends MoveControl {
      private final EntityLantern parentEntity;
      protected int courseChangeCooldown;

      public MoveHelperController(EntityLantern p_i45838_1_) {
         super(p_i45838_1_);
         this.parentEntity = p_i45838_1_;
      }

      public void tick() {
         if (this.operation == Operation.MOVE_TO && this.courseChangeCooldown-- <= 0) {
            this.courseChangeCooldown = this.courseChangeCooldown + this.parentEntity.getRandom().nextInt(5) + 2;
            Vec3 lvt_1_1_ = new Vec3(this.wantedX - this.parentEntity.getX(), this.wantedY - this.parentEntity.getY(), this.wantedZ - this.parentEntity.getZ());
            double lvt_2_1_ = lvt_1_1_.length();
            lvt_1_1_ = lvt_1_1_.normalize();
            if (!this.canReach(lvt_1_1_, Mth.ceil(lvt_2_1_))) {
               this.operation = Operation.WAIT;
            }
         }
      }

      public boolean canReach(Vec3 p_220673_1_, int p_220673_2_) {
         AABB lvt_3_1_ = this.parentEntity.getBoundingBox();

         for (int lvt_4_1_ = 1; lvt_4_1_ < p_220673_2_; lvt_4_1_++) {
            lvt_3_1_ = lvt_3_1_.move(p_220673_1_);
            if (!this.parentEntity.level().noCollision(this.parentEntity, lvt_3_1_)) {
               return false;
            }
         }

         return true;
      }

      public boolean isMovingTo() {
         return this.operation == Operation.MOVE_TO;
      }
   }

   static class RandomFlyGoal extends Goal {
      private final EntityLantern parentEntity;

      public RandomFlyGoal(EntityLantern p_i45836_1_) {
         this.parentEntity = p_i45836_1_;
         this.setFlags(EnumSet.of(Flag.MOVE));
      }

      public boolean canUse() {
         MoveControl lvt_1_1_ = this.parentEntity.getMoveControl();
         if (!lvt_1_1_.hasWanted()) {
            return true;
         }

         double lvt_2_1_ = lvt_1_1_.getWantedX() - this.parentEntity.getX();
         double lvt_4_1_ = lvt_1_1_.getWantedY() - this.parentEntity.getY();
         double lvt_6_1_ = lvt_1_1_.getWantedZ() - this.parentEntity.getZ();
         double lvt_8_1_ = lvt_2_1_ * lvt_2_1_ + lvt_4_1_ * lvt_4_1_ + lvt_6_1_ * lvt_6_1_;
         return lvt_8_1_ < 1.0 || lvt_8_1_ > 3600.0;
      }

      public boolean canContinueToUse() {
         return false;
      }

      public void start() {
         RandomSource lvt_1_1_ = this.parentEntity.getRandom();
         double lvt_2_1_ = this.parentEntity.getX() + (lvt_1_1_.nextFloat() * 2.0F - 1.0F) * 16.0F;
         double lvt_4_1_ = this.parentEntity.getY() + (lvt_1_1_.nextFloat() * 2.0F - 1.0F) * 16.0F;
         double lvt_6_1_ = this.parentEntity.getZ() + (lvt_1_1_.nextFloat() * 2.0F - 1.0F) * 16.0F;
         this.parentEntity.getMoveControl().setWantedPosition(lvt_2_1_, lvt_4_1_, lvt_6_1_, 1.0);
      }
   }
}
