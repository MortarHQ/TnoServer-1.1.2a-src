package io.github.Memoires.mysticism.ability.skill.unique;

import io.github.Memoires.mysticism.config.ability.skill.UniqueMysticismSkillConfig;
import io.github.Memoires.mysticism.registry.effects.MysticismMobEffects;
import io.github.Memoires.mysticism.util.TimerUtils;
import io.github.Memoires.mysticism.util.effect.EffectValues;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.impl.TickingSkill;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.ability.skill.unique.CookSkill;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.effect.IEffect;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public class ConstantSkill extends Skill {
   public static final UniqueMysticismSkillConfig.Constant CONFIG = ((UniqueMysticismSkillConfig)ConfigRegistry.getConfig(UniqueMysticismSkillConfig.class)).Constant;

   public ConstantSkill() {
      super(SkillType.UNIQUE);
   }

   @Nullable
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/skill/unique/constant.png");
   }

   public double getAcquiringMagiculeCost(ManasSkillInstance instance) {
      return CONFIG.mpAcquirement;
   }

   public void onLearnSkill(ManasSkillInstance instance, LivingEntity entity) {
      super.onLearnSkill(instance, entity);
      CompoundTag tag = instance.getOrCreateTag();
      tag.putFloat("ConstantDestruction", 0.0F);
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> "constant.health";
         case 1 -> "constant.physical";
         case 2 -> "constant.destruction";
         case 3 -> "constant.energy";
         default -> super.getModeId(instance, mode);
      };
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return instance.isMastered(entity);
   }

   public boolean onBeingDamaged(ManasSkillInstance instance, LivingEntity entity, DamageSource source, float amount) {
      if (instance.isToggled()) {
         AttributeInstance health = entity.getAttribute(Attributes.MAX_HEALTH);
         if (health != null && health.getModifier(CookSkill.COOK) != null) {
            health.removeModifier(CookSkill.COOK);
         }

         IEffect effect = TensuraStorages.getEffectFrom(entity);
         effect.setSeveranceAmount(0.0F);
         effect.markDirty();
      }

      return true;
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> TickingSkill.isTickingSkill(entity, this) ? CONFIG.constantHealthTickCost : CONFIG.constantHealthCost;
         case 1 -> CONFIG.constantPhysicalCost;
         case 2 -> CONFIG.constantDestructionCost;
         case 3 -> CONFIG.constantEnergyCost;
         default -> 0.0;
      };
   }

   public int getModes(ManasSkillInstance instance) {
      return 4;
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      if (reverse) {
         return switch (mode) {
            case 0 -> instance.isMastered(entity) ? 3 : (instance.getOrCreateTag().getFloat("ConstantDamage") != 0.0F ? 2 : 1);
            default -> 0;
            case 2 -> 1;
            case 3 -> instance.getOrCreateTag().getFloat("ConstantDamage") != 0.0F ? 2 : 0;
         };
      } else {
         return switch (mode) {
            case 0 -> 1;
            case 1 -> instance.isMastered(entity)
               ? (instance.getOrCreateTag().getFloat("ConstantDamage") != 0.0F ? 2 : 3)
               : (instance.getOrCreateTag().getFloat("ConstantDamage") != 0.0F ? 2 : 0);
            case 2 -> instance.isMastered(entity) ? 3 : 0;
            default -> 0;
         };
      }
   }

   public boolean onDamageEntity(ManasSkillInstance instance, LivingEntity owner, LivingEntity target, DamageSource source, Changeable<Float> amount) {
      float highestDamage = instance.getOrCreateTag().getFloat("ConstantDamage");
      if (instance.getOrCreateTag().getBoolean("ConstantPhysical")) {
         Float point = (Float)amount.get();
         if (point != null && point > highestDamage) {
            highestDamage = point;
            if (owner instanceof Player player) {
               player.displayClientMessage(Component.literal(String.valueOf(highestDamage)).setStyle(Style.EMPTY).withStyle(ChatFormatting.GREEN), false);
            }

            instance.getOrCreateTag().putFloat("ConstantDamage", highestDamage);
         }
      }

      return true;
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      switch (mode) {
         case 0:
            IExistence existence = TensuraStorages.getExistenceFrom(entity);
            double shp = existence.getSpiritualHealth();
            if (!EnergyHelper.isOutOfEnergy(entity, instance, 0)) {
               entity.level()
                  .playSound(null, entity.getX(), entity.getY(), entity.getZ(), TensuraSoundEvents.GENERIC_CAST, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
               Holder<MobEffect> effectx = TensuraMobEffects.getReference(MysticismMobEffects.CONSTANT_HEALTH);
               TensuraMobEffect.addEffect(entity, new MobEffectInstance(effectx, CONFIG.constantHealthHeldTicks, 0, false, false, false), entity, this, mode);
               EffectValues.set(entity, effectx, "health", entity.getHealth());
               EffectValues.set(entity, effectx, "shp", shp);
            }

            if (entity instanceof Player player) {
               player.displayClientMessage(Component.translatable("mysticism.skill.mode.constant.health.activated").withStyle(ChatFormatting.BLUE), false);
            }
            break;
         case 1:
            instance.getOrCreateTag().putBoolean("ConstantPhysical", true);
            instance.getOrCreateTag().putFloat("ConstantDamage", 0.0F);
            AtomicInteger ticksRun = new AtomicInteger();
            TimerUtils.getOrCreateRepeating("ConstantPhysical")
               .start(
                  CONFIG.constantPhysicalTimer,
                  () -> {
                     ticksRun.getAndIncrement();
                     if (entity instanceof Player playerx) {
                        playerx.displayClientMessage(
                           Component.translatable(
                                 "mysticism.skill.mode.constant.time_remaining", new Object[]{(CONFIG.constantPhysicalTimer - ticksRun.get()) / 20}
                              )
                              .withStyle(ChatFormatting.DARK_AQUA),
                           true
                        );
                     }
                  },
                  () -> {
                     instance.getOrCreateTag().putBoolean("ConstantPhysical", false);
                     int cooldown = instance.isMastered(entity) ? CONFIG.constantPhysicalCooldownMastered : CONFIG.constantPhysicalCooldown;
                     if (entity instanceof Player playerx) {
                        playerx.displayClientMessage(
                           Component.translatable(
                                 "mysticism.skill.mode.constant.destruction.available", new Object[]{instance.getOrCreateTag().getFloat("ConstantDamage")}
                              )
                              .withStyle(ChatFormatting.GREEN),
                           false
                        );
                     }

                     entity.level()
                        .playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.PLAYER_LEVELUP, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
                     instance.addMasteryPoint(entity);
                     instance.setCoolDown(cooldown, 1);
                  }
               );
            break;
         case 2: {
            Holder<MobEffect> effect = TensuraMobEffects.getReference(MysticismMobEffects.CONSTANT_DESTRUCTION);
            TensuraMobEffect.addEffect(entity, new MobEffectInstance(effect, CONFIG.constantDestructionHeldTicks, 0, false, false, false), entity, this, mode);
            float damage = Math.min(instance.getOrCreateTag().getFloat("ConstantDamage"), CONFIG.constantDestructionMaxDamage);
            EffectValues.set(entity, effect, "damage", damage);
            break;
         }
         case 3: {
            Holder<MobEffect> effect = TensuraMobEffects.getReference(MysticismMobEffects.CONSTANT_ENERGY);
            Holder<MobEffect> debuff = TensuraMobEffects.getReference(MysticismMobEffects.CONSTANT_ENERGY_DEBUFF);
            if (!entity.hasEffect(effect) && !entity.hasEffect(debuff)) {
               TensuraMobEffect.addEffect(entity, new MobEffectInstance(effect, CONFIG.constantEnergyDuration * 20, 0, false, false, false), entity, this, mode);
               entity.level()
                  .playSound(null, entity.getX(), entity.getY(), entity.getZ(), TensuraSoundEvents.BUFF_ACTIVATE, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
               instance.setCoolDown(CONFIG.constantEnergyCooldown, 3);
            }
         }
      }
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity living, int heldTicks, int mode) {
      switch (mode) {
         case 0:
            int maxHeldTimex = instance.isMastered(living) ? CONFIG.constantHealthHeldTicksMastered : CONFIG.constantHealthHeldTicks;
            if (heldTicks > maxHeldTimex) {
               if (living.hasEffect(TensuraMobEffects.getReference(MysticismMobEffects.CONSTANT_HEALTH))) {
                  living.removeEffect(TensuraMobEffects.getReference(MysticismMobEffects.CONSTANT_HEALTH));
               }

               living.level()
                  .playSound(null, living.getX(), living.getY(), living.getZ(), TensuraSoundEvents.BUFF_DEACTIVATE, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
               this.addMasteryPoint(instance, living);
               int cooldown = instance.isMastered(living) ? CONFIG.constantHealthCooldownMastered : CONFIG.constantHealthCooldown;
               instance.setCoolDown(cooldown, 0);
               return false;
            } else {
               if (heldTicks % 20 == 0 && heldTicks > 0) {
                  return !EnergyHelper.isOutOfEnergy(living, instance, 0);
               }

               if (living instanceof Player player) {
                  player.displayClientMessage(
                     Component.translatable("mysticism.skill.mode.constant.time_remaining", new Object[]{(maxHeldTimex - heldTicks) / 20})
                        .withStyle(ChatFormatting.BLUE),
                     true
                  );
               }

               return true;
            }
         case 2:
            int maxHeldTime = instance.isMastered(living) ? CONFIG.constantDestructionHeldTicksMastered : CONFIG.constantDestructionHeldTicks;
            if (heldTicks > maxHeldTime) {
               if (living.hasEffect(TensuraMobEffects.getReference(MysticismMobEffects.CONSTANT_DESTRUCTION))) {
                  living.removeEffect(TensuraMobEffects.getReference(MysticismMobEffects.CONSTANT_DESTRUCTION));
               }

               living.level()
                  .playSound(null, living.getX(), living.getY(), living.getZ(), TensuraSoundEvents.BUFF_DEACTIVATE, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
               this.addMasteryPoint(instance, living);
               int cooldown = instance.isMastered(living) ? CONFIG.constantDestructionCooldownMastered : CONFIG.constantDestructionCooldown;
               instance.setCoolDown(cooldown, 2);
               return false;
            } else {
               if (heldTicks % 20 == 0 && heldTicks > 0) {
                  return !EnergyHelper.isOutOfEnergy(living, instance, 2);
               }

               if (living instanceof Player player) {
                  player.displayClientMessage(
                     Component.translatable("mysticism.skill.mode.constant.time_remaining", new Object[]{(maxHeldTime - heldTicks) / 20})
                        .withStyle(ChatFormatting.BLUE),
                     true
                  );
               }

               return true;
            }
         default:
            return false;
      }
   }

   public void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int keyNumber, int mode) {
      switch (mode) {
         case 0:
            if (TickingSkill.isTickingSkill(entity, this, 0)) {
               if (entity.hasEffect(TensuraMobEffects.getReference(MysticismMobEffects.CONSTANT_HEALTH))) {
                  entity.removeEffect(TensuraMobEffects.getReference(MysticismMobEffects.CONSTANT_HEALTH));
               }

               entity.level()
                  .playSound(null, entity.getX(), entity.getY(), entity.getZ(), TensuraSoundEvents.BUFF_DEACTIVATE, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
               int cooldown = instance.isMastered(entity) ? CONFIG.constantHealthCooldownMastered : CONFIG.constantHealthCooldown;
               this.addMasteryPoint(instance, entity);
               instance.setCoolDown(cooldown, 0);
            }
            break;
         case 2:
            if (TickingSkill.isTickingSkill(entity, this, 2)) {
               if (entity.hasEffect(TensuraMobEffects.getReference(MysticismMobEffects.CONSTANT_DESTRUCTION))) {
                  entity.removeEffect(TensuraMobEffects.getReference(MysticismMobEffects.CONSTANT_DESTRUCTION));
               }

               entity.level()
                  .playSound(null, entity.getX(), entity.getY(), entity.getZ(), TensuraSoundEvents.BUFF_DEACTIVATE, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
               int cooldown = instance.isMastered(entity) ? CONFIG.constantDestructionCooldownMastered : CONFIG.constantDestructionCooldown;
               this.addMasteryPoint(instance, entity);
               instance.setCoolDown(cooldown, 2);
            }
      }
   }
}
