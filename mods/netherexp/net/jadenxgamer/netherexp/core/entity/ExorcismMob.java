package net.jadenxgamer.netherexp.core.entity;

import java.util.Optional;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions.ParticleSpritePicker;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;

public abstract class ExorcismMob extends PathfinderMob {
   protected ExorcismMob(EntityType<? extends PathfinderMob> entityType, Level level) {
      super(entityType, level);
   }

   public void aiStep() {
      if (!this.level().isClientSide && this.isInWaterOrRain()) {
         this.doExorcism();
      }

      super.aiStep();
   }

   public boolean isSensitiveToWater() {
      return true;
   }

   public boolean hurtWithCleanWater(ThrownPotion potion) {
      ItemStack stack = potion.getItem();
      PotionContents contents = (PotionContents)stack.get(DataComponents.POTION_CONTENTS);
      if (contents == null) {
         return false;
      }

      Optional<Holder<Potion>> potionContents = contents.potion();
      return potionContents.isPresent() && potionContents.get().is(Potions.WATER) && contents.customEffects().isEmpty();
   }

   public boolean hurt(DamageSource source, float amount) {
      if (source.getDirectEntity() instanceof ThrownPotion potion && this.hurtWithCleanWater(potion)) {
         this.doExorcism();
      } else if (source.getEntity() instanceof Player player && player.getMainHandItem().is(JNETags.Items.SILVER_WEAPONS)) {
         amount = (float)(amount * (Double)JNEConfigs.SILVER_PARANORMAL_DAMAGE_MULTIPLIER.get());
         if ((Boolean)JNEConfigs.SILVER_PARANORMAL_INFLICTS_SLOWNESS.get()) {
            this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 4));
         }

         this.level().broadcastEntityEvent(this, (byte)91);
      }

      return super.hurt(source, amount);
   }

   public boolean doHurtTarget(Entity target) {
      if (target instanceof Player player) {
         boolean isWearingSilverArmor = player.getInventory().armor.stream().anyMatch(stack -> stack.is(JNETags.Items.SILVER_ARMORS));
         if (isWearingSilverArmor) {
            this.hurt(this.level().damageSources().playerAttack(player), (float)JNEConfigs.SILVER_PARANORMAL_PROTECTION_DAMAGE.getAsDouble());
            if ((Boolean)JNEConfigs.SILVER_PARANORMAL_INFLICTS_SLOWNESS.get()) {
               this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 4));
            }

            this.level().broadcastEntityEvent(this, (byte)91);
         }
      }

      return super.doHurtTarget(target);
   }

   public void doExorcism() {
      BlockPos pos = this.blockPosition();
      if (this.getExorcismSound() != null) {
         this.level().playSound(null, pos, this.getExorcismSound(), SoundSource.NEUTRAL, 1.0F, 1.0F);
      }

      this.level().broadcastEntityEvent(this, (byte)92);
      this.discard();
   }

   @Nullable
   public SoundEvent getExorcismSound() {
      return this.getDeathSound();
   }

   public void handleEntityEvent(byte id) {
      if (id == 91) {
         ExorcismMob.Client.silverParticle(this.level(), this.random, this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5));
      }

      if (id == 92) {
         ExorcismMob.Client.possessionParticle(this.level(), this.random, this);
      }

      super.handleEntityEvent(id);
   }

   public static boolean checkSpawnRules(
      EntityType<? extends ExorcismMob> type, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random
   ) {
      return level.getDifficulty() != Difficulty.PEACEFUL
         && (MobSpawnType.ignoresLightRequirements(spawnType) || Monster.isDarkEnoughToSpawn(level, pos, random))
         && checkMobSpawnRules(type, level, spawnType, pos, random);
   }

   @OnlyIn(Dist.CLIENT)
   public static class Client {
      public static void silverParticle(Level level, RandomSource random, double x, double y, double z) {
         for (int i = 0; i < 9; i++) {
            WorldParticleBuilder.create(JNEParticleTypes.SILVER_GLIMMER.get())
               .setFullBrightLighting()
               .setSpinData(SpinParticleData.createRandomDirection(random, 0.0F, 1.0F).setCoefficient(0.7F).setEasing(Easing.SINE_IN).build())
               .setScaleData(GenericParticleData.create(0.165F, 0.0F).setEasing(Easing.BOUNCE_IN_OUT).build())
               .setTransparencyData(GenericParticleData.create(1.0F).build())
               .setRenderType(LodestoneWorldParticleRenderType.TRANSPARENT)
               .setSpritePicker(ParticleSpritePicker.WITH_AGE)
               .setLifetime(random.nextInt(10, 28))
               .enableNoClip()
               .setMotion(random.nextDouble() * 0.08, random.nextDouble() * 0.08, random.nextDouble() * 0.08)
               .spawn(level, x, y, z);
         }
      }

      public static void possessionParticle(Level level, RandomSource random, ExorcismMob mob) {
         for (int i = 0; i < 12; i++) {
            double x = mob.getRandomX(0.5);
            double y = mob.getRandomY();
            double z = mob.getRandomZ(0.5);
            WorldParticleBuilder.create(JNEParticleTypes.POSSESSION.get())
               .setFullBrightLighting()
               .setSpinData(SpinParticleData.create(0.0F).build())
               .setScaleData(GenericParticleData.create(0.595F).build())
               .setTransparencyData(GenericParticleData.create(1.0F).build())
               .setRenderType(LodestoneWorldParticleRenderType.TRANSPARENT)
               .setSpritePicker(ParticleSpritePicker.WITH_AGE)
               .setLifetime(random.nextInt(10, 28))
               .enableNoClip()
               .setMotion(random.nextDouble() * 0.02, random.nextDouble() * 0.02, random.nextDouble() * 0.02)
               .spawn(level, x, y, z);
         }
      }
   }
}
