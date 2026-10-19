package twilightforest.potions;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import twilightforest.TwilightForestMod;

public class FrostedEffect extends MobEffect {
   public static final ResourceLocation MOVEMENT_SPEED_MODIFIER = TwilightForestMod.prefix("frosted_slowdown");
   public static final double FROST_MULTIPLIER = -0.15;

   public FrostedEffect() {
      super(MobEffectCategory.HARMFUL, 5688317);
      this.addAttributeModifier(Attributes.MOVEMENT_SPEED, MOVEMENT_SPEED_MODIFIER, -0.15, Operation.ADD_MULTIPLIED_TOTAL);
   }

   public boolean applyEffectTick(LivingEntity living, int amplifier) {
      living.setIsInPowderSnow(true);
      if (amplifier > 0 && living.canFreeze()) {
         living.setTicksFrozen(Math.min(living.getTicksRequiredToFreeze(), living.getTicksFrozen() + amplifier));
      }

      return true;
   }

   public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
      return true;
   }
}
