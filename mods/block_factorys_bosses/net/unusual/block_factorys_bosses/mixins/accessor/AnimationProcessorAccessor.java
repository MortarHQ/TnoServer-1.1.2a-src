package net.unusual.block_factorys_bosses.mixins.accessor;

import java.util.Map;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import software.bernie.geckolib.animation.AnimationProcessor;
import software.bernie.geckolib.cache.object.GeoBone;

@Mixin(value = AnimationProcessor.class, remap = false)
public interface AnimationProcessorAccessor {
   @Accessor(value = "bones", remap = false)
   Map<String, GeoBone> getBones();
}
