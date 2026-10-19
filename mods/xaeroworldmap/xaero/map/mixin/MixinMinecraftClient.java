package xaero.map.mixin;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import xaero.map.core.IWorldMapMinecraftClient;

@Mixin(Minecraft.class)
public class MixinMinecraftClient implements IWorldMapMinecraftClient {
   @Shadow
   private static int fps;

   @Override
   public int getXaeroWorldMap_fps() {
      return fps;
   }
}
