package cn.leolezury.eternalstarlight.common.effect;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public class DreamCatcherEffect extends MobEffect {
   public static final double ARMOR_ADDITION = 5.0;

   public DreamCatcherEffect(MobEffectCategory category, int color) {
      super(category, color);
      this.addAttributeModifier(Attributes.ARMOR, EternalStarlight.id("armor.dream_catcher"), 5.0, Operation.ADD_VALUE);
   }
}
