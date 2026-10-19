package com.cerbon.bosses_of_mass_destruction.entity.util.animation;

import com.cerbon.bosses_of_mass_destruction.entity.GeoModel;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animation.AnimationState;

public interface ICodeAnimations<T extends GeoAnimatable> {
   void animate(T var1, AnimationState<?> var2, GeoModel<T> var3);
}
