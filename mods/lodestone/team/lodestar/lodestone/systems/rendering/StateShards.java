package team.lodestar.lodestone.systems.rendering;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderStateShard.TransparencyStateShard;
import team.lodestar.lodestone.registry.client.LodestoneRenderTypes;

public class StateShards extends RenderStateShard {
   public static final TransparencyStateShard ADDITIVE_TRANSPARENCY = new TransparencyStateShard("additive_transparency", () -> {
      RenderSystem.enableBlend();
      LodestoneRenderTypes.ADDITIVE_FUNCTION.run();
   }, () -> {
      RenderSystem.disableBlend();
      RenderSystem.defaultBlendFunc();
   });
   public static final TransparencyStateShard NORMAL_TRANSPARENCY = new TransparencyStateShard("normal_transparency", () -> {
      RenderSystem.enableBlend();
      LodestoneRenderTypes.TRANSPARENT_FUNCTION.run();
   }, () -> {
      RenderSystem.disableBlend();
      RenderSystem.defaultBlendFunc();
   });

   public StateShards(String p_110161_, Runnable p_110162_, Runnable p_110163_) {
      super(p_110161_, p_110162_, p_110163_);
   }
}
