package io.github.Memoires.mysticism.ability.skill.unique;

import io.github.Memoires.mysticism.config.ability.skill.UniqueMysticismSkillConfig;
import io.github.Memoires.mysticism.entity.projectile.BlueFlareProjectile;
import io.github.Memoires.mysticism.entity.projectile.DraconicMeteorProjectile;
import io.github.Memoires.mysticism.registry.item.MysticismMobDropItems;
import io.github.Memoires.mysticism.registry.skill.UniqueMysticismSkills;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.impl.TickingSkill;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.entity.magic.breath.BreathEntity;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.entity.MiscEntityTypes;
import io.github.manasmods.tensura.registry.item.TensuraMobDropItems;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.util.AttributeHelper;
import io.github.manasmods.tensura.util.EnergyHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

public class ReshiramSkill extends Skill {
   protected static final UniqueMysticismSkillConfig.Reshiram CONFIG = ((UniqueMysticismSkillConfig)ConfigRegistry.getConfig(UniqueMysticismSkillConfig.class)).Reshiram;
   protected static final ResourceLocation RESHIRAM = ResourceLocation.fromNamespaceAndPath("mysticism", "reshiram");

   public ReshiramSkill() {
      super(SkillType.UNIQUE);
   }

   @Nullable
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/skill/unique/reshiram.png");
   }

   public int getModes(ManasSkillInstance instance) {
      return 4;
   }

   public double getDefaultAcquiringMagiculeCost() {
      return CONFIG.mpAcquirement;
   }

   public boolean checkAcquiringRequirement(Player entity, double newEP) {
      if (!CONFIG.canAcquireWithOtherSkills) {
         return false;
      } else if (!(entity instanceof ServerPlayer player)) {
         return false;
      } else {
         boolean hasZekrom = SkillUtils.hasSkillFully(player, (ManasSkill)UniqueMysticismSkills.ZEKROM.get());
         boolean hasKyurem = SkillUtils.hasSkillFully(player, (ManasSkill)UniqueMysticismSkills.KYUREM.get());
         boolean hasReshiram = SkillUtils.hasSkillFully(player, (ManasSkill)UniqueMysticismSkills.RESHIRAM.get());
         boolean isZekromMastered = SkillUtils.isSkillMastered(player, (ManasSkill)UniqueMysticismSkills.ZEKROM.get());
         boolean isKyuremMastered = SkillUtils.isSkillMastered(player, (ManasSkill)UniqueMysticismSkills.KYUREM.get());
         boolean canLearnSkill = !hasReshiram && (hasZekrom && isZekromMastered || hasKyurem && isKyuremMastered);
         boolean hasEnoughFlameEssence = player.getStats().getValue(Stats.ITEM_USED.get((Item)MysticismMobDropItems.FLAME_ESSENCE.get()))
            >= CONFIG.blazeEssenceAmount;
         boolean hasEnoughDragonEssence = player.getStats().getValue(Stats.ITEM_USED.get((Item)TensuraMobDropItems.DRAGON_ESSENCE.get()))
            >= CONFIG.dragonEssenceAmount;
         boolean hasEnoughEP = entity.getAttributeValue(TensuraAttributes.MAX_AURA) + entity.getAttributeValue(TensuraAttributes.MAX_MAGICULE)
            >= CONFIG.epRequirement;
         return canLearnSkill && hasEnoughFlameEssence && hasEnoughDragonEssence && hasEnoughEP;
      }
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> "reshiram.blue_flare";
         case 1 -> "reshiram.fusion_flare";
         case 2 -> "reshiram.draconic_meteor";
         case 3 -> "reshiram.flamethrower";
         default -> super.getModeId(instance, mode);
      };
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> CONFIG.blueFlareMPCost;
         case 1 -> CONFIG.fusionFlareMPCost;
         case 2 -> CONFIG.draconicMeteorMPCost;
         case 3 -> CONFIG.flamethrowerMPCost;
         default -> 0.0;
      };
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      if (reverse) {
         return mode == 0 ? 3 : mode - 1;
      } else {
         return mode == 3 ? 0 : mode + 1;
      }
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return instance.getMastery() >= 0.0;
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      AttributeHelper.addPermanentAttribute(entity, TensuraAttributes.FLAME_BOOST, RESHIRAM, CONFIG.flameBoostAmount, Operation.ADD_VALUE);
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      AttributeHelper.removeAttributeIfCorrect(entity, TensuraAttributes.FLAME_BOOST, RESHIRAM, CONFIG.flameBoostAmount);
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      switch (mode) {
         case 0:
            if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
               BlueFlareProjectile projectile = new BlueFlareProjectile(entity.level(), entity);
               projectile.setDamage(CONFIG.blueFlareDamage);
               projectile.setSpeed(1.5F);
               projectile.setAreaLife(100);
               projectile.setAreaRadius(CONFIG.blueFlareRadius);
               projectile.setSkill(entity, instance, this, mode);
               projectile.setPosAndShoot(entity);
               entity.level().addFreshEntity(projectile);
               entity.level()
                  .playSound(
                     null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.CAST_FIRE.get(), TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F
                  );
               instance.addMasteryPoint(entity);
               instance.setCoolDown(instance.isMastered(entity) ? CONFIG.blueFlareCooldownMastered : CONFIG.blueFlareCooldown, mode);
            }
            break;
         case 1:
            boolean hasZekrom = TensuraStorages.getAbilityFrom(entity).isAbilityInActivePreset((ManasSkill)UniqueMysticismSkills.ZEKROM.get());
            if (!EnergyHelper.isOutOfEnergy(entity, instance, mode, hasZekrom ? (float)CONFIG.fusionFlareZekromMPCostBoost : 1.0F)) {
               BlueFlareProjectile projectile = new BlueFlareProjectile(entity.level(), entity);
               projectile.setDamage(CONFIG.fusionFlareDamage);
               projectile.setSpeed(2.5F);
               projectile.setAreaLife(100);
               projectile.setAreaRadius(CONFIG.fusionFlareRadius);
               projectile.setSkill(entity, instance, this, mode);
               projectile.setAttract(true);
               projectile.setHasZekrom(hasZekrom);
               projectile.setPosAndShoot(entity);
               entity.level().addFreshEntity(projectile);
               entity.level()
                  .playSound(
                     null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.CAST_FIRE.get(), TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F
                  );
               instance.addMasteryPoint(entity);
               instance.setCoolDown(CONFIG.fusionFlareCooldown, mode);
            }
            break;
         case 2:
            if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
               DraconicMeteorProjectile projectile = new DraconicMeteorProjectile(entity.level(), entity);
               projectile.setDamage(CONFIG.draconicMeteorDamage);
               projectile.setSkill(entity, instance, this, mode);
               projectile.setSpeed(1.0F);
               projectile.setExplosionRadius(CONFIG.draconicMeteorExplosionRadius);
               projectile.setPosAndShoot(entity);
               projectile.setKnockForce(CONFIG.draconicMeteorKnockbackForce);
               entity.level().addFreshEntity(projectile);
               entity.level()
                  .playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ENDER_DRAGON_SHOOT, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
               instance.addMasteryPoint(entity);
               instance.setCoolDown(CONFIG.draconicMeteorCooldown, mode);
            }
            break;
         case 3:
            instance.getOrCreateTag().putInt("BreathEntity", 0);
            instance.markDirty();
      }
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity living, int heldTicks, int mode) {
      if (mode == 3) {
         if (heldTicks % 20 == 0 && EnergyHelper.isOutOfEnergy(living, instance, mode)) {
            return false;
         }

         if (heldTicks % BASE_CONFIG.Mastery.masteryHoldTick == 0 && heldTicks > 0) {
            instance.addMasteryPoint(living);
         }

         BreathEntity.spawnBreathEntity((EntityType)MiscEntityTypes.FLAME_BREATH.get(), living, instance, CONFIG.flamethrowerDamage, this, mode);
         return true;
      } else {
         return false;
      }
   }

   public void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int keyNumber, int mode) {
      if (mode == 3 && TickingSkill.isTickingSkill(entity, this, 3)) {
         instance.setCoolDown(CONFIG.flamethrowerCooldown, mode);
      }
   }
}
