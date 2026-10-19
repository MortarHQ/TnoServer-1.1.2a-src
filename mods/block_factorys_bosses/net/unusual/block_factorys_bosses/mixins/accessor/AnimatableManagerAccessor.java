package net.unusual.block_factorys_bosses.mixins.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import software.bernie.geckolib.animation.AnimatableManager;

@Mixin(value = AnimatableManager.class, remap = false)
public interface AnimatableManagerAccessor {
   @Invoker(value = "finishFirstTick", remap = false)
   void invokeFinishFirstTick();
}
