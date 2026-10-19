package io.github.Memoires.mysticism.effect.ability.constant;

import io.github.Memoires.mysticism.registry.effects.MysticismMobEffects;
import io.github.Memoires.mysticism.util.effect.EffectValues;
import io.github.Memoires.mysticism.util.effect.EffectValuesData;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.tensura.effect.template.DamageAction;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import java.awt.Color;
import net.minecraft.core.Holder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class ConstantDestructionEffect extends TensuraMobEffect implements DamageAction {
   public ConstantDestructionEffect() {
      super(MobEffectCategory.BENEFICIAL, new Color(0, 56, 56).getRGB());
   }

   public boolean onDamagingEntity(LivingEntity attacker, LivingEntity target, DamageSource source, Changeable<Float> amount) {
      Holder<MobEffect> effect = TensuraMobEffects.getReference(MysticismMobEffects.CONSTANT_DESTRUCTION);
      EffectValuesData data = EffectValues.getData(attacker, effect);
      if (data != null) {
         Float damage = data.getFloat("damage");
         if (damage != null) {
            amount.set(damage);
         }
      }

      return true;
   }
}
