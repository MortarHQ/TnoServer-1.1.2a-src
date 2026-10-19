package twilightforest.entity.monster;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import twilightforest.entity.projectile.SlimeProjectile;
import twilightforest.init.TFEntities;
import twilightforest.init.TFSounds;

public class SlimeBeetle extends Monster implements RangedAttackMob {
   public SlimeBeetle(EntityType<? extends SlimeBeetle> type, Level world) {
      super(type, world);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(2, new AvoidEntityGoal(this, Player.class, 3.0F, 1.25, 2.0));
      this.goalSelector.addGoal(3, new RangedAttackGoal(this, 1.0, 30, 10.0F));
      this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0));
      this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true));
   }

   public static Builder registerAttributes() {
      return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 25.0).add(Attributes.MOVEMENT_SPEED, 0.23).add(Attributes.ATTACK_DAMAGE, 4.0);
   }

   @Nullable
   protected SoundEvent getAmbientSound() {
      return (SoundEvent)TFSounds.SLIME_BEETLE_AMBIENT.get();
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return (SoundEvent)TFSounds.SLIME_BEETLE_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)TFSounds.SLIME_BEETLE_DEATH.get();
   }

   protected void playStepSound(BlockPos pos, BlockState state) {
      this.playSound((SoundEvent)TFSounds.SLIME_BEETLE_STEP.get(), 0.15F, 1.0F);
   }

   public void performRangedAttack(LivingEntity target, float distanceFactor) {
      ThrowableProjectile projectile = new SlimeProjectile((EntityType<? extends SlimeProjectile>)TFEntities.SLIME_BLOB.get(), this.level(), this);
      this.playSound((SoundEvent)TFSounds.SLIME_BEETLE_SQUISH.get(), 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
      double tx = target.getX() - this.getX();
      double ty = target.getY() + target.getEyeHeight() - 1.1 - projectile.getY();
      double tz = target.getZ() - this.getZ();
      float heightOffset = Mth.sqrt((float)(tx * tx + tz * tz)) * 0.2F;
      projectile.shoot(tx, ty + heightOffset, tz, 0.6F, 6.0F);
      this.level().addFreshEntity(projectile);
   }
}
