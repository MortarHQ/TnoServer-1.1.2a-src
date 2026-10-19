package team.lodestar.lodestone.helpers;

import com.mojang.blaze3d.pipeline.RenderTarget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderStateShard.OutputStateShard;

public class StateShardHelper {
   public static OutputStateShard createOutputState(String name, RenderTarget outputTarget) {
      return new OutputStateShard(name, () -> outputTarget.bindWrite(false), () -> Minecraft.getInstance().getMainRenderTarget().bindWrite(false));
   }

   public static OutputStateShard createOutputState(String name, Runnable outputTarget) {
      return new OutputStateShard(name, outputTarget, () -> Minecraft.getInstance().getMainRenderTarget().bindWrite(false));
   }
}
