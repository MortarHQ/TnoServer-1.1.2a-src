package com.mongoose.royalvariations.common.effects;

import com.mongoose.royalvariations.init.RVSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class RVBaseEffect extends MobEffect {
   public boolean isVisible;

   public RVBaseEffect(MobEffectCategory category, int color) {
      this(category, color, true);
   }

   public RVBaseEffect(MobEffectCategory category, int color, boolean isVisible) {
      super(category, color);
      this.isVisible = isVisible;
   }

   public boolean isVisible() {
      return this.isVisible;
   }

   public boolean applyEffectTick(LivingEntity effected, int amplify) {
      Level world = effected.level();
      if (effected.isAlive() && this == RVEffects.TIME_BOMB.value()) {
         world.playSound(
            null,
            effected.getX(),
            effected.getY(),
            effected.getZ(),
            (SoundEvent)RVSounds.TIMER_TICKING.get(),
            effected.getSoundSource(),
            2.0F,
            1.0F + world.getRandom().nextIntBetweenInclusive(3, 5) * 0.1F
         );
      }

      return true;
   }

   public boolean shouldApplyEffectTickThisTick(int tick, int amplify) {
      return this != RVEffects.TIME_BOMB.value() ? super.shouldApplyEffectTickThisTick(tick, amplify) : tick > 0 && tick % 21 == 0;
   }
}
