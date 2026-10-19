package io.github.Memoires.mysticism.effect.ability.constant;

import io.github.Memoires.mysticism.registry.effects.MysticismMobEffects;
import io.github.Memoires.mysticism.util.effect.EffectValues;
import io.github.Memoires.mysticism.util.effect.EffectValuesData;
import io.github.manasmods.tensura.effect.template.DamageAction;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import java.awt.Color;
import net.minecraft.core.Holder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class ConstantHealthEffect extends TensuraMobEffect implements DamageAction {
   public ConstantHealthEffect() {
      super(MobEffectCategory.BENEFICIAL, new Color(0, 56, 56).getRGB());
   }

   public void onPostBeingDamaged(LivingEntity entity, DamageSource source, float amount) {
      Holder<MobEffect> effect = TensuraMobEffects.getReference(MysticismMobEffects.CONSTANT_HEALTH);
      EffectValuesData data = EffectValues.getData(entity, effect);
      if (data != null) {
         Float health = data.getFloat("health");
         if (health != null) {
            entity.setHealth(health);
         }

         IExistence existence = TensuraStorages.getExistenceFrom(entity);
         Float spiritualHealth = data.getFloat("shp");
         if (spiritualHealth != null) {
            existence.setSpiritualHealth(spiritualHealth.floatValue());
         }
      }
   }
}
