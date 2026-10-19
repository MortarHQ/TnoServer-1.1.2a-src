package com.blorb.morerelics.mixin;

import net.minecraft.world.entity.WalkAnimationState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(WalkAnimationState.class)
public interface WalkAnimationStateAccessor {
   @Accessor("speedOld")
   float getSpeedOld();

   @Accessor("speedOld")
   void setSpeedOld(float var1);

   @Accessor("position")
   void setPosition(float var1);

   @Accessor("position")
   float getPosition();
}
