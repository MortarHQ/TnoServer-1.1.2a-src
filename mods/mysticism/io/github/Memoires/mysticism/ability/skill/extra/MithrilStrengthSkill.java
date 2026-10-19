package io.github.Memoires.mysticism.ability.skill.extra;

import io.github.Memoires.mysticism.config.ability.skill.ExtraMysticismSkillConfig;
import io.github.Memoires.mysticism.race.insect.beetle.stag.DivineStagBeetleRace;
import io.github.Memoires.mysticism.race.insect.beetle.stag.StagBeetleSavantRace;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.race.api.RaceAPI;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.particle.TensuraParticleUtils;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.skill.ExtraSkills;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.util.EnergyHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public class MithrilStrengthSkill extends Skill {
   private static final ExtraMysticismSkillConfig.MithrilStrength CONFIG = ((ExtraMysticismSkillConfig)ConfigRegistry.getConfig(ExtraMysticismSkillConfig.class))
      .MithrilStrength;

   public MithrilStrengthSkill() {
      super(SkillType.EXTRA);
   }

   @Nullable
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/skill/extra/mithril_strength.png");
   }

   public boolean checkAcquiringRequirement(Player entity, double newEP) {
      return this.checkStagBeetleRequirement(entity) && SkillUtils.isSkillMastered(entity, (ManasSkill)ExtraSkills.STEEL_STRENGTH.get());
   }

   private boolean checkStagBeetleRequirement(Player entity) {
      return RaceAPI.getRaceFrom(entity) instanceof StagBeetleSavantRace || RaceAPI.getRaceFrom(entity) instanceof DivineStagBeetleRace;
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return CONFIG.magiculeCost;
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return instance.isMastered(entity);
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      return instance.isToggled();
   }

   public void onTick(ManasSkillInstance instance, LivingEntity entity) {
      if (EnergyHelper.isOutOfEnergy(entity, instance, 0)) {
         entity.sendSystemMessage(
            Component.translatable("tensura.skill.lack_magicule.toggled_off", new Object[]{instance.getChatDisplayName(true)})
               .setStyle(Style.EMPTY.withColor(ChatFormatting.RED))
         );
         instance.setToggled(false);
         instance.onToggleOff(entity);
      } else {
         TensuraMobEffect.addEffect(
            entity,
            new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.STRENGTHEN), 240, CONFIG.strengthenLevel - 1, false, false, false),
            entity,
            this
         );
      }
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if ((!entity.hasEffect(TensuraMobEffects.getReference(TensuraMobEffects.STRENGTHEN)) || instance.isToggled())
         && !EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
         instance.addMasteryPoint(entity);
         TensuraMobEffect.addEffect(
            entity,
            new MobEffectInstance(
               TensuraMobEffects.getReference(TensuraMobEffects.STRENGTHEN),
               instance.isMastered(entity) ? CONFIG.strengthenDuration * 2 : CONFIG.strengthenDuration,
               instance.isMastered(entity) ? CONFIG.strengthenLevelMastered - 1 : CONFIG.strengthenLevel - 1,
               false,
               false,
               true
            ),
            entity,
            this,
            mode
         );
         entity.level()
            .playSound(
               null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.BUFF_ACTIVATE.get(), TensuraSkill.ABILITY_SOUND, 0.5F, 0.5F
            );
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
      }
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      this.onTick(instance, entity);
      entity.level()
         .playSound(
            null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.BUFF_ACTIVATE.get(), TensuraSkill.ABILITY_SOUND, 0.5F, 0.5F
         );
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
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      MobEffectInstance effect = entity.getEffect(TensuraMobEffects.getReference(TensuraMobEffects.STRENGTHEN));
      if (effect != null && effect.getAmplifier() <= CONFIG.strengthenLevel - 1) {
         entity.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.STRENGTHEN));
      }

      entity.level()
         .playSound(
            null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.BUFF_DEACTIVATE.get(), TensuraSkill.ABILITY_SOUND, 0.5F, 0.5F
         );
   }
}
