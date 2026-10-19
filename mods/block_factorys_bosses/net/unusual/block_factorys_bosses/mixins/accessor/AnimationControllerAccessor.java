package net.unusual.block_factorys_bosses.mixins.accessor;

import java.util.function.Function;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.EasingType;

@Mixin(value = AnimationController.class, remap = false)
public interface AnimationControllerAccessor<T extends GeoAnimatable> {
   @Accessor(value = "isJustStarting", remap = false)
   void setIsJustStarting(boolean var1);

   @Accessor(value = "overrideEasingTypeFunction", remap = false)
   Function<T, EasingType> getOverrideEasingTypeFunction();
}
