package team.creative.creativecore.common.util.mc;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import team.creative.creativecore.client.CreativeCoreClient;

public class TickUtils {
   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   private static float getFrameTimeClient() {
      return CreativeCoreClient.getFrameTime();
   }

   public static float getFrameTime(LevelAccessor level) {
      return level.isClientSide() ? getFrameTimeClient() : 1.0F;
   }
}
