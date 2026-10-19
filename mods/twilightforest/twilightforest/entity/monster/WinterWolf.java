package twilightforest.entity.monster;

import java.util.Objects;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.phys.Vec3;
import twilightforest.entity.IBreathAttacker;
import twilightforest.entity.ai.goal.BreathAttackGoal;
import twilightforest.init.TFBiomes;
import twilightforest.init.TFParticleType;
import twilightforest.init.TFSounds;

public class WinterWolf extends HostileWolf implements IBreathAttacker {
   private static final EntityDataAccessor<Boolean> BREATH_FLAG = SynchedEntityData.defineId(WinterWolf.class, EntityDataSerializers.BOOLEAN);
   private static final float BREATH_DAMAGE = 2.0F;

   public WinterWolf(EntityType<? extends WinterWolf> type, Level world) {
      super(type, world);
   }

   @Override
   protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(2, new BreathAttackGoal(this, 5.0F, 30, 0.1F));
      this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.0, false));
      this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true));
   }

   public static Builder registerAttributes() {
      return HostileWolf.registerAttributes().add(Attributes.MAX_HEALTH, 30.0).add(Attributes.ATTACK_DAMAGE, 6.0);
   }

   @Override
   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(BREATH_FLAG, false);
   }

   public void aiStep() {
      super.aiStep();
      if (this.isBreathing()) {
         if (this.level().isClientSide()) {
            this.spawnBreathParticles();
         }

         this.playBreathSound();
      }
   }

   private void spawnBreathParticles() {
      Vec3 look = this.getLookAngle();
      double dist = 0.5;
      double px = this.getX() + look.x() * 0.5;
      double py = this.getY() + 1.25 + look.y() * 0.5;
      double pz = this.getZ() + look.z() * 0.5;

      for (int i = 0; i < 10; i++) {
         double dx = look.x();
         double dy = look.y();
         double dz = look.z();
         double spread = 5.0 + this.getRandom().nextDouble() * 2.5;
         double velocity = 3.0 + this.getRandom().nextDouble() * 0.15;
         dx += this.getRandom().nextGaussian() * 0.0075 * spread;
         dy += this.getRandom().nextGaussian() * 0.0075 * spread;
         dz += this.getRandom().nextGaussian() * 0.0075 * spread;
         dx *= velocity;
         dy *= velocity;
         dz *= velocity;
         this.level().addParticle((ParticleOptions)TFParticleType.SNOW.get(), px, py, pz, dx, dy, dz);
      }
   }

   @Override
   protected SoundEvent getTargetSound() {
      return (SoundEvent)TFSounds.WINTER_WOLF_TARGET.get();
   }

   @Override
   protected SoundEvent getAmbientSound() {
      return (SoundEvent)TFSounds.WINTER_WOLF_AMBIENT.get();
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return (SoundEvent)TFSounds.WINTER_WOLF_HURT.get();
   }

   private void playBreathSound() {
      this.playSound((SoundEvent)TFSounds.WINTER_WOLF_SHOOT.get(), this.getRandom().nextFloat() * 0.5F, this.getRandom().nextFloat() * 0.5F);
   }

   @Override
   protected SoundEvent getDeathSound() {
      return (SoundEvent)TFSounds.WINTER_WOLF_DEATH.get();
   }

   public float getVoicePitch() {
      return (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.2F + 0.6F;
   }

   @Override
   public boolean isBreathing() {
      return (Boolean)this.getEntityData().get(BREATH_FLAG);
   }

   @Override
   public void setBreathing(boolean flag) {
      this.getEntityData().set(BREATH_FLAG, flag);
   }

   @Override
   public void doBreathAttack(Entity target) {
      target.hurt(this.damageSources().mobAttack(this), 2.0F);
   }

   public static boolean canSpawnHere(
      EntityType<? extends WinterWolf> entity, ServerLevelAccessor accessor, MobSpawnType reason, BlockPos pos, RandomSource random
   ) {
      Optional<ResourceKey<Biome>> key = accessor.getBiome(pos).unwrapKey();
      return accessor.getDifficulty() != Difficulty.PEACEFUL && Objects.equals(key, Optional.of(TFBiomes.SNOWY_FOREST))
         || Monster.isDarkEnoughToSpawn(accessor, pos, random);
   }
}
