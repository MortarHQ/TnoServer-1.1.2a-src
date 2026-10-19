package io.github.Memoires.mysticism.ability.skill.unique;

import io.github.Memoires.mysticism.ability.subclass.IButchering;
import io.github.Memoires.mysticism.ability.subclass.IRupturing;
import io.github.Memoires.mysticism.config.ability.skill.UniqueMysticismSkillConfig;
import io.github.Memoires.mysticism.registry.effects.MysticismMobEffects;
import io.github.Memoires.mysticism.registry.sound.MysticismSoundEvents;
import io.github.Memoires.mysticism.storage.MysticismStorages;
import io.github.Memoires.mysticism.storage.entity.IMysticismEntity;
import io.github.Memoires.mysticism.storage.player.IMysticismPlayer;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.skill.CommonSkills;
import io.github.manasmods.tensura.registry.skill.ExtraSkills;
import io.github.manasmods.tensura.registry.skill.UniqueSkills;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public class ButcherSkill extends Skill implements IButchering<ButcherSkill>, IRupturing<ButcherSkill> {
   protected static final UniqueMysticismSkillConfig.Butcher CONFIG = ((UniqueMysticismSkillConfig)ConfigRegistry.getConfig(UniqueMysticismSkillConfig.class)).Butcher;
   protected static final ResourceLocation BUTCHER = ResourceLocation.fromNamespaceAndPath("mysticism", "butcher");

   public ButcherSkill() {
      super(SkillType.UNIQUE);
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      if (reverse) {
         return mode == 0 ? 2 : mode - 1;
      } else {
         return mode == 2 ? 0 : mode + 1;
      }
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return instance.getMastery() >= 0.0;
   }

   @Nullable
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/skill/unique/butcher.png");
   }

   public List<Integer> getModeLearningList(ManasSkillInstance instance) {
      return List.of(2);
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> CONFIG.heightenCost;
         case 1 -> CONFIG.ruptureCost;
         case 2 -> CONFIG.血淋林的爱Cost;
         default -> 0.0;
      };
   }

   public double getAcquiringMagiculeCost(ManasSkillInstance instance) {
      return CONFIG.mpAcquirement;
   }

   public int getModes(ManasSkillInstance instance) {
      return 3;
   }

   public boolean onDamageEntity(ManasSkillInstance instance, LivingEntity owner, LivingEntity target, DamageSource source, Changeable<Float> amount) {
      if (!this.isInSlot(owner)) {
         return true;
      }

      UUID targetUUID = target.getUUID();
      IMysticismPlayer ownerStorage = MysticismStorages.getPlayerDataFrom(owner);
      IMysticismEntity targetStorage = MysticismStorages.getEntityDataFrom(target);
      if (ownerStorage == null) {
         return true;
      }

      ownerStorage.setLastHitTarget(targetUUID);
      boolean lethal = target.getHealth() - (Float)amount.get() <= 0.0F;
      if (TensuraDamageHelper.isPhysicalAttack(source)) {
         owner.level().playSound(null, owner.getX(), owner.getY(), owner.getZ(), MysticismSoundEvents.BUTCHER_STAB, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
         ownerStorage.incrementMarks(targetUUID);
         targetStorage.incrementButcherMarks();
         targetStorage.markDirty();
         targetStorage.setLastMarkApplier(owner.getUUID());
         if (owner instanceof Player p) {
            p.displayClientMessage(
               Component.translatable(
                     "mysticism.skill.mode.butcher.aortic_work_of_art.target_marks", new Object[]{target.getDisplayName(), targetStorage.getButcherMarks()}
                  )
                  .withStyle(ChatFormatting.AQUA),
               true
            );
         }

         if (targetStorage.getButcherMarks() == 10) {
            TensuraMobEffect.addEffect(
               target, new MobEffectInstance(TensuraMobEffects.getReference(MysticismMobEffects.RUPTURING), switch (this.getRegenerationLevel(target)) {
                  case 1 -> 2400;
                  case 2 -> 1200;
                  case 3 -> 300;
                  default -> -1;
               }, 0, true, true, true), owner, this
            );
         }
      }

      if (ownerStorage.getMarks(targetUUID) >= CONFIG.血淋林的爱MarkRequirement && lethal) {
         ownerStorage.clearMarks(targetUUID);
         instance.getOrCreateTag().putInt("BloodyLoveCharges", instance.getOrCreateTag().getInt("BloodyLoveCharges") + CONFIG.chargesGained);
         instance.markDirty();
      }

      return true;
   }

   private int getRegenerationLevel(LivingEntity target) {
      if (SkillUtils.isSkillToggled(target, (ManasSkill)ExtraSkills.INFINITE_REGENERATION.get())) {
         return 3;
      } else if (SkillUtils.isSkillToggled(target, (ManasSkill)ExtraSkills.ULTRASPEED_REGENERATION.get())
         || SkillUtils.isSkillToggled(target, (ManasSkill)UniqueSkills.SURVIVOR.get())) {
         return 2;
      } else {
         return SkillUtils.isSkillToggled(target, (ManasSkill)CommonSkills.SELF_REGENERATION.get()) ? 1 : 0;
      }
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> "butcher.heighten";
         case 1 -> "butcher.rupture";
         case 2 -> "butcher.血淋林的爱";
         default -> super.getModeId(instance, mode);
      };
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      CompoundTag tag = instance.getOrCreateTag();
      switch (mode) {
         case 0:
            AttributeInstance movementSpeed = entity.getAttribute(Attributes.MOVEMENT_SPEED);
            AttributeInstance attackSpeed = entity.getAttribute(Attributes.ATTACK_SPEED);
            AttributeInstance stepHeight = entity.getAttribute(Attributes.STEP_HEIGHT);
            if (!tag.getBoolean("HeightenActivated") && !EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
               tag.putBoolean("HeightenActivated", true);
               if (movementSpeed != null) {
                  movementSpeed.addOrReplacePermanentModifier(new AttributeModifier(BUTCHER, 1.0, Operation.ADD_MULTIPLIED_TOTAL));
               }

               if (attackSpeed != null) {
                  attackSpeed.addOrReplacePermanentModifier(new AttributeModifier(BUTCHER, 1.0, Operation.ADD_MULTIPLIED_TOTAL));
               }

               if (stepHeight != null) {
                  stepHeight.addOrReplacePermanentModifier(new AttributeModifier(BUTCHER, 0.5, Operation.ADD_VALUE));
               }
            } else {
               tag.putBoolean("HeightenActivated", false);
               if (movementSpeed != null) {
                  movementSpeed.removeModifier(BUTCHER);
               }

               if (attackSpeed != null) {
                  attackSpeed.removeModifier(BUTCHER);
               }

               if (stepHeight != null) {
                  stepHeight.removeModifier(BUTCHER);
               }
            }
            break;
         case 1:
            List<Integer> targets = new ArrayList<>();
            List<UUID> targetUUIDs = MysticismStorages.getPlayerDataFrom(entity).getTargetsOver(CONFIG.ruptureMarkRequirement);
            if (targetUUIDs != null) {
               for (UUID uuid : targetUUIDs) {
                  ServerLevel level = (ServerLevel)entity.level();
                  if (level.getEntity(uuid) instanceof LivingEntity livingEntity) {
                     targets.add(livingEntity.getId());
                  }
               }
            }

            if (!targets.isEmpty()) {
               this.openRupturingMenu(entity, this, targets);
            } else if (entity instanceof Player p) {
               p.playNotifySound((SoundEvent)TensuraSoundEvents.GENERIC_CAST_FAIL.get(), TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
               p.displayClientMessage(
                  Component.translatable("mysticism.skill.mode.butcher.rupture.no_eligible_targets", new Object[]{CONFIG.ruptureMarkRequirement})
                     .withStyle(ChatFormatting.RED),
                  false
               );
            }
            break;
         case 2:
            if (!this.learnMode(instance, entity, mode)) {
               if (instance.getOrCreateTag().getInt("BloodyLoveCharges") > 0) {
                  UUID targetUUID = MysticismStorages.getPlayerDataFrom(entity).getLastHitTarget();
                  ServerLevel serverLevel = (ServerLevel)entity.level();
                  if (serverLevel.getEntity(targetUUID) instanceof LivingEntity livingTarget) {
                     this.openButcheringMenu(entity, this, livingTarget);
                  }
               } else if (entity instanceof Player player) {
                  player.playNotifySound((SoundEvent)TensuraSoundEvents.GENERIC_CAST_FAIL.get(), TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
                  player.displayClientMessage(Component.translatable("mysticism.skill.mode.butcher.血淋林的爱.no_charges").withStyle(ChatFormatting.RED), false);
               }
            }
      }
   }

   public void onForgetSkill(ManasSkillInstance instance, LivingEntity entity) {
      super.onForgetSkill(instance, entity);
      AttributeInstance movementSpeed = entity.getAttribute(Attributes.MOVEMENT_SPEED);
      AttributeInstance attackSpeed = entity.getAttribute(Attributes.ATTACK_SPEED);
      AttributeInstance stepHeight = entity.getAttribute(Attributes.STEP_HEIGHT);
      if (movementSpeed != null) {
         movementSpeed.removeModifier(BUTCHER);
      }

      if (attackSpeed != null) {
         attackSpeed.removeModifier(BUTCHER);
      }

      if (stepHeight != null) {
         stepHeight.removeModifier(BUTCHER);
      }
   }
}
