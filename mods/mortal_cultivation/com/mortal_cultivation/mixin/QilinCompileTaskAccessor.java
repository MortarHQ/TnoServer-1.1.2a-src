package com.mortal_cultivation.mixin;

import java.util.concurrent.atomic.AtomicBoolean;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(targets = "net.minecraft.client.renderer.chunk.SectionRenderDispatcher$RenderSection$CompileTask")
public interface QilinCompileTaskAccessor {
   @Accessor(value = "isCancelled", remap = false)
   AtomicBoolean mortalCultivation$getCancellationFlag();
}
