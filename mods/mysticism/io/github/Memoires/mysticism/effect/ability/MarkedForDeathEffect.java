package io.github.Memoires.mysticism.effect.ability;

import io.github.Memoires.mysticism.registry.effects.MysticismMobEffects;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.tensura.effect.template.DamageAction;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import java.awt.Color;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class MarkedForDeathEffect extends TensuraMobEffect implements DamageAction {
   public MarkedForDeathEffect() {
      super(MobEffectCategory.HARMFUL, new Color(255, 181, 0, 255).getRGB());
   }

   public boolean onBeingDamaged(LivingEntity entity, DamageSource source, Changeable<Float> amount) {
      MobEffectInstance instance = entity.getEffect(TensuraMobEffects.getReference(MysticismMobEffects.MARKED_FOR_DEATH));
      if (instance != null) {
         int effectLevel = instance.getAmplifier() + 1;
         float newAmount = (Float)amount.get() * (2.0F * effectLevel);
         if (!Float.isFinite(newAmount) || newAmount > Float.MAX_VALUE) {
            newAmount = Float.MAX_VALUE;
         }

         amount.set(newAmount);
      }

      return true;
   }
}
