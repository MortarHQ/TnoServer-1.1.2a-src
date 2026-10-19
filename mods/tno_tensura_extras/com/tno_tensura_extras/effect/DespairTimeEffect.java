package com.tno_tensura_extras.effect;

import com.tno_tensura_extras.registry.ExtrasMobEffects;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.impl.SkillStorage;
import io.github.manasmods.manascore.skill.impl.TickingSkill;
import io.github.manasmods.tensura.ability.magic.Magic;
import io.github.manasmods.tensura.data.TensuraSkillTags;
import java.util.List;
import java.util.Set;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.EffectCure;

public final class DespairTimeEffect extends MobEffect {
   public static final int DURATION_TICKS = 2400;
   public static final int COOLDOWN_TICKS = 6000;

   public DespairTimeEffect() {
      super(MobEffectCategory.HARMFUL, 4471634);
   }

   public static boolean blocks(LivingEntity entity, ManasSkillInstance instance) {
      return entity.hasEffect(ExtrasMobEffects.DESPAIR_TIME) && (instance.getSkill() instanceof Magic || instance.is(TensuraSkillTags.MAGIC));
   }

   public static void interruptMagic(LivingEntity entity) {
      SkillStorage storage = SkillAPI.getSkillsFrom(entity);

      for (TickingSkill held : List.copyOf(storage.getHeldSkills())) {
         ManasSkillInstance instance = (ManasSkillInstance)storage.getSkill(held.getSkill()).orElse(null);
         if (instance != null && blocks(entity, instance)) {
            instance.removeAttributeModifiers(entity, held.getMode());
            storage.getHeldSkills().remove(held);
            storage.markDirty();
         }
      }
   }

   public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
      return true;
   }

   public boolean applyEffectTick(LivingEntity entity, int amplifier) {
      if (!entity.level().isClientSide()) {
         interruptMagic(entity);
      }

      return true;
   }

   public void fillEffectCures(Set<EffectCure> cures, MobEffectInstance effectInstance) {
   }
}
