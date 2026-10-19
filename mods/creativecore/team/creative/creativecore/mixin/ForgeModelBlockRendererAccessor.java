package team.creative.creativecore.mixin;

import net.neoforged.neoforge.client.model.lighting.LightPipelineAwareModelBlockRenderer;
import net.neoforged.neoforge.client.model.lighting.QuadLighter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = LightPipelineAwareModelBlockRenderer.class, remap = false)
public interface ForgeModelBlockRendererAccessor {
   @Accessor
   ThreadLocal<QuadLighter> getFlatLighter();

   @Accessor
   ThreadLocal<QuadLighter> getSmoothLighter();
}
