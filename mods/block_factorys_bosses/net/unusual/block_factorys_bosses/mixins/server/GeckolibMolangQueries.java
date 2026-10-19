package net.unusual.block_factorys_bosses.mixins.server;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.loading.math.MolangQueries;
import software.bernie.geckolib.loading.math.value.Variable;

@Mixin(MolangQueries.class)
public abstract class GeckolibMolangQueries {
   @Unique
   private static AnimationState<? extends GeoAnimatable> bosses_rise_java$animationState;

   @Shadow
   static Variable getVariableFor(String name) {
      throw new IllegalStateException("placeholder");
   }

   @Redirect(method = "<clinit>", at = @At(value = "INVOKE", target = "Lsoftware/bernie/geckolib/loading/math/MolangQueries;setDefaultQueryValues()V"))
   private static void setDefaultServerSafeQueryValues() {
      getVariableFor("PI").set(Math.PI);
      getVariableFor("E").set(Math.E);
   }

   @Overwrite
   public static void updateActor(AnimationState<? extends GeoAnimatable> animationState, double animTime) {
      bosses_rise_java$animationState = animationState;
   }

   @Overwrite
   public static void clearActor() {
      bosses_rise_java$animationState = null;
   }
}
