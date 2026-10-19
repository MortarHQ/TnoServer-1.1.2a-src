package io.github.Memoires.mysticism.ability.skill.unique;

import io.github.Memoires.mysticism.config.ability.skill.UniqueMysticismSkillConfig;
import io.github.Memoires.mysticism.registry.effects.MysticismMobEffects;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.particle.TensuraParticleUtils;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public class CultivatorSkill extends Skill {
   public static final UniqueMysticismSkillConfig.Cultivator CONFIG = ((UniqueMysticismSkillConfig)ConfigRegistry.getConfig(UniqueMysticismSkillConfig.class)).Cultivator;
   protected static final ResourceLocation CULTIVATOR = ResourceLocation.fromNamespaceAndPath("mysticism", "cultivator");

   public CultivatorSkill() {
      super(SkillType.UNIQUE);
   }

   @Nullable
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/skill/unique/cultivator.png");
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return "cultivator.breakthrough";
   }

   public double getAcquiringMagiculeCost(ManasSkillInstance instance) {
      return CONFIG.mpAcquirement;
   }

   public boolean onDamageEntity(ManasSkillInstance instance, LivingEntity owner, LivingEntity target, DamageSource source, Changeable<Float> amount) {
      if (!this.isInSlot(owner, instance)) {
         return true;
      }

      if (owner.getMainHandItem().isEmpty() && owner.getOffhandItem().isEmpty()) {
         if (source.getDirectEntity() != owner) {
            return true;
         }

         if (!TensuraDamageHelper.isPhysicalAttack(source)) {
            return true;
         }

         float damage = instance.isMastered(owner) ? CONFIG.attackBoostMastered : CONFIG.attackBoost;
         Float point = (Float)amount.get();
         if (point != null) {
            amount.set(point + damage);
         }

         return true;
      } else {
         return true;
      }
   }

   public boolean canIgnoreCoolDown(ManasSkillInstance instance, LivingEntity entity, int mode) {
      return instance.getOrCreateTag().getBoolean("Cultivating");
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return instance.getMastery() >= 0.0;
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      double bonusMagicule = instance.isMastered(entity) ? CONFIG.magiculePercentageMastered : CONFIG.magiculePercentage;
      AttributeInstance magicule = entity.getAttribute(TensuraAttributes.MAGICULE_GAIN);
      if (magicule != null && !magicule.hasModifier(CULTIVATOR)) {
         magicule.addOrReplacePermanentModifier(new AttributeModifier(CULTIVATOR, bonusMagicule, Operation.ADD_VALUE));
      }
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      AttributeInstance magicule = entity.getAttribute(TensuraAttributes.MAGICULE_GAIN);
      if (magicule != null) {
         magicule.removeModifier(CULTIVATOR);
      }
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      CompoundTag tag = instance.getOrCreateTag();
      if (entity.hasEffect(TensuraMobEffects.getReference(MysticismMobEffects.CULTIVATING))) {
         if (entity instanceof Player player) {
            player.displayClientMessage(
               Component.translatable("mysticism.skill.mode.cultivator.breakthrough.already_cultivating")
                  .setStyle(Style.EMPTY.withColor(ChatFormatting.YELLOW)),
               false
            );
         }
      } else {
         int mobKills = tag.getInt("MobKills");
         if (mobKills < CONFIG.mobKillsNeeded) {
            if (entity instanceof Player player) {
               player.displayClientMessage(
                  Component.translatable("mysticism.skill.mode.cultivator.breakthrough.not_enough_kills", new Object[]{CONFIG.mobKillsNeeded - mobKills})
                     .setStyle(Style.EMPTY.withColor(ChatFormatting.RED)),
                  false
               );
            }
         } else if (entity instanceof Player player) {
            tag.putInt("MobKills", 0);
            player.level()
               .playSound(null, player.getX(), player.getY(), player.getZ(), TensuraSoundEvents.BUFF_ACTIVATE, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
            TensuraParticleHelper.spawnServerParticles(
               entity.level(),
               TensuraParticleUtils.getColorlessReversedWave(0.9F, entity.getBbWidth() * 3.0F),
               entity.getX(),
               entity.getY() + entity.getBbHeight() * 0.33,
               entity.getZ()
            );
            TensuraParticleHelper.spawnServerParticles(
               entity.level(),
               TensuraParticleUtils.getColorlessReversedWave(0.9F, entity.getBbWidth() * 3.0F),
               entity.getX(),
               entity.getY() + entity.getBbHeight() * 0.66,
               entity.getZ()
            );
            player.displayClientMessage(
               Component.translatable("mysticism.skill.mode.cultivator.breakthrough.cultivation_started").setStyle(Style.EMPTY.withColor(ChatFormatting.GREEN)),
               false
            );
            player.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(MysticismMobEffects.CULTIVATING), 6000, 0, false, false, true));
            instance.setCoolDown(3600, mode);
         }
      }
   }

   public void onLearnSkill(ManasSkillInstance instance, LivingEntity entity) {
      CompoundTag tag = instance.getOrCreateTag();
      tag.putInt("MobKills", 0);
      tag.putInt("CultivationLevel", 0);
   }

   public boolean onDeath(ManasSkillInstance instance, LivingEntity owner, DamageSource source) {
      CompoundTag skillTag = instance.getOrCreateTag();
      if (owner.hasEffect(TensuraMobEffects.getReference(MysticismMobEffects.CULTIVATING))) {
         skillTag.putInt("CultivationLevel", 0);
         if (owner instanceof Player player) {
            player.displayClientMessage(
               Component.translatable("mysticism.skill.mode.cultivator.breakthrough.died").setStyle(Style.EMPTY.withColor(ChatFormatting.DARK_RED)), false
            );
         }

         instance.setMastery(0.0);
      }

      return true;
   }
}
