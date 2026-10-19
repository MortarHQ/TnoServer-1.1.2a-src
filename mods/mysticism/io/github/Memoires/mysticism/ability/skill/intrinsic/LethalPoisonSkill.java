package io.github.Memoires.mysticism.ability.skill.intrinsic;

import io.github.Memoires.mysticism.config.ability.skill.IntrinsicMysticismSkillConfig;
import io.github.Memoires.mysticism.race.insect.scorpion.loxodrome.DivineLoxodromeScorpionRace;
import io.github.Memoires.mysticism.race.insect.scorpion.loxodrome.LoxodromeScorpionInsectarRace;
import io.github.Memoires.mysticism.race.insect.scorpion.loxodrome.LoxodromeScorpionRace;
import io.github.Memoires.mysticism.race.insect.scorpion.loxodrome.LoxodromeScorpionSavantRace;
import io.github.Memoires.mysticism.race.insect.scorpion.loxodrome.PoisonSoulInsectRace;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.race.api.RaceAPI;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.particle.TensuraParticleUtils;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.skill.CommonSkills;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class LethalPoisonSkill extends Skill {
   private static final IntrinsicMysticismSkillConfig.LethalPoison CONFIG = ((IntrinsicMysticismSkillConfig)ConfigRegistry.getConfig(
         IntrinsicMysticismSkillConfig.class
      ))
      .LethalPoison;

   public LethalPoisonSkill() {
      super(SkillType.INTRINSIC);
   }

   @Nullable
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/skill/intrinsic/lethal_poison.png");
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return instance.isMastered(entity);
   }

   public boolean checkAcquiringRequirement(Player entity, double newEP) {
      return this.checkLoxodromeRequirement(entity) && SkillUtils.isSkillMastered(entity, (ManasSkill)CommonSkills.POISON.get());
   }

   private boolean checkLoxodromeRequirement(Player entity) {
      return RaceAPI.getRaceFrom(entity) instanceof LoxodromeScorpionRace
         || RaceAPI.getRaceFrom(entity) instanceof LoxodromeScorpionInsectarRace
         || RaceAPI.getRaceFrom(entity) instanceof LoxodromeScorpionSavantRace
         || RaceAPI.getRaceFrom(entity) instanceof PoisonSoulInsectRace
         || RaceAPI.getRaceFrom(entity) instanceof DivineLoxodromeScorpionRace;
   }

   public boolean onTouchEntity(ManasSkillInstance instance, LivingEntity attacker, LivingEntity target, DamageSource source, Changeable<Float> amount) {
      if (!this.isInSlot(attacker, instance) && !instance.isToggled()) {
         return true;
      }

      if (source.getDirectEntity() != attacker) {
         return true;
      }

      if (!TensuraDamageHelper.isPhysicalAttack(source)) {
         return true;
      }

      Level level = attacker.level();
      Holder<MobEffect> poison = instance.isMastered(attacker) ? TensuraMobEffects.getReference(TensuraMobEffects.FATAL_POISON) : MobEffects.POISON;
      TensuraMobEffect.addEffect(
         target,
         new MobEffectInstance(poison, CONFIG.duration, (instance.isMastered(attacker) ? CONFIG.levelMastered : CONFIG.level) - 1, true, true, true),
         attacker,
         this
      );
      level.playSound(
         null, attacker.getX(), attacker.getY(), attacker.getZ(), (SoundEvent)TensuraSoundEvents.ACID_SIZZLE.get(), TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F
      );
      ((ServerLevel)level)
         .sendParticles(
            TensuraParticleUtils.getPoisonBubble(),
            target.position().x,
            target.position().y + target.getBbHeight() / 2.0,
            target.position().z,
            20,
            0.08,
            0.08,
            0.08,
            0.15
         );
      CompoundTag tag = instance.getOrCreateTag();
      int time = tag.getInt("activatedTimes");
      if (time % BASE_CONFIG.Mastery.masteryActivateTime == 0) {
         instance.addMasteryPoint(attacker);
      }

      tag.putInt("activatedTimes", time + 1);
      return true;
   }
}
