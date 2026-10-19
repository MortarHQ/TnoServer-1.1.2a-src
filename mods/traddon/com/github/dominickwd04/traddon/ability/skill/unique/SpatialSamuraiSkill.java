package com.github.dominickwd04.traddon.ability.skill.unique;

import com.github.dominickwd04.traddon.config.ability.skill.TrAddonSkillConfig;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.entity.projectile.TensuraFlyingProjectile.PositionDirection;
import io.github.manasmods.tensura.entity.projectile.magic.SeveranceCutterProjectile;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class SpatialSamuraiSkill extends Skill {
   public static final TrAddonSkillConfig.SpatialSamurai CONFIG = ((TrAddonSkillConfig)ConfigRegistry.getConfig(TrAddonSkillConfig.class)).spatialSamurai;

   public SpatialSamuraiSkill() {
      super(SkillType.UNIQUE);
   }

   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tensura", "textures/skill/unique/predator.png");
   }

   public double getDefaultAcquiringMagiculeCost() {
      return CONFIG.mpAcquirement;
   }

   public double getObtainingEpCost() {
      return 75000.0;
   }

   public double learningCost() {
      return 1000.0;
   }

   public int getModes(ManasSkillInstance instance) {
      return 3;
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      if (instance.isMastered(entity)) {
         return reverse ? (mode == 0 ? 2 : mode - 1) : (mode == 2 ? 0 : mode + 1);
      } else {
         return mode == 0 ? 1 : 0;
      }
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return instance.getMastery() >= 0.0;
   }

   public boolean onTouchEntity(ManasSkillInstance instance, LivingEntity owner, LivingEntity target, DamageSource source, Changeable<Float> amount) {
      if ((this.isInSlot(owner) || instance.isToggled()) && source.getEntity() == owner && TensuraDamageHelper.isPhysicalAttack(source)) {
         IExistence existence = TensuraStorages.getExistenceFrom(owner);
         double extraDamage = target.getMaxHealth() * CONFIG.cleaveMaxHealthMultiplier;
         double magiculeCost = extraDamage * CONFIG.cleaveMagiculePerDamage;
         double userMagicules = existence.getMagicule();
         if (userMagicules < magiculeCost) {
            extraDamage = (userMagicules - 100.0) / magiculeCost;
            magiculeCost = userMagicules;
         }

         if (!EnergyHelper.isOutOfEnergy(owner, 0.0, magiculeCost)) {
            amount.set((Float)amount.get() + (float)extraDamage);
            return true;
         }
      }

      return false;
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
         switch (mode) {
            case 0:
               SeveranceCutterProjectile spaceCutter = new SeveranceCutterProjectile(entity.level(), entity);
               spaceCutter.setSpeed(2.5F);
               spaceCutter.setDamage((float)(instance.isMastered(entity) ? CONFIG.projectileDamageMastered : CONFIG.projectileDamage));
               spaceCutter.setSize((float)(instance.isMastered(entity) ? CONFIG.projectileSizeMastered : CONFIG.projectileSize));
               spaceCutter.setSkill(entity, instance, this, mode);
               spaceCutter.setNoGravity(true);
               spaceCutter.setPosAndShoot(entity);
               spaceCutter.setPosDirection(entity, PositionDirection.MIDDLE);
               entity.level().addFreshEntity(spaceCutter);
               instance.addMasteryPoint(entity);
               instance.setCoolDown(CONFIG.projectileCooldown, mode);
               entity.swing(InteractionHand.MAIN_HAND, true);
               entity.swing(InteractionHand.OFF_HAND, true);
               entity.level()
                  .playSound(
                     (Player)null,
                     entity.getX(),
                     entity.getY(),
                     entity.getZ(),
                     (SoundEvent)TensuraSoundEvents.CAST_SPACE.get(),
                     SoundSource.PLAYERS,
                     1.0F,
                     1.0F
                  );
            case 1:
            case 2:
         }
      }
   }
}
