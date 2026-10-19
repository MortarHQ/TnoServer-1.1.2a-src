package com.shin.tensura_kumodesu.ability.skill.intrinsic;

import com.shin.tensura_kumodesu.config.skill.KumodesuIntrinsicSkillConfig;
import com.shin.tensura_kumodesu.registry.effects.KumodesuEffects;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.particle.TensuraParticleUtils;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class DeadlySpiderPoisonSkill extends Skill {
   private static final KumodesuIntrinsicSkillConfig.DeadlySpiderPoison CONFIG = ((KumodesuIntrinsicSkillConfig)ConfigRegistry.getConfig(
         KumodesuIntrinsicSkillConfig.class
      ))
      .DeadlySpiderPoison;

   public DeadlySpiderPoisonSkill() {
      super(SkillType.INTRINSIC);
   }

   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "textures/skill/intrinsic/deadlyspiderpoison.png");
   }

   public int getMaxMastery() {
      return 300;
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return instance.isMastered(entity);
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
      Holder<MobEffect> poison = instance.isMastered(attacker)
         ? level.registryAccess()
            .registryOrThrow(Registries.MOB_EFFECT)
            .getHolderOrThrow(ResourceKey.create(Registries.MOB_EFFECT, KumodesuEffects.DEADLY_SPIDER_POISON.getId()))
         : level.registryAccess()
            .registryOrThrow(Registries.MOB_EFFECT)
            .getHolderOrThrow(ResourceKey.create(Registries.MOB_EFFECT, KumodesuEffects.DEADLY_POISON.getId()));
      TensuraMobEffect.addEffect(target, poison, CONFIG.poisonDuration, CONFIG.poisonLevel - 1, true, false, true, attacker.getUUID(), this, 0);
      level.playSound(
         (Player)null, target.getX(), target.getY(), target.getZ(), (SoundEvent)TensuraSoundEvents.ACID_SIZZLE.get(), SoundSource.PLAYERS, 0.5F, 1.0F
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
