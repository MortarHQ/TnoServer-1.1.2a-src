package xueluoanping.swayinggarden.client.shader;

import net.irisshaders.iris.Iris;
import net.irisshaders.iris.shaderpack.materialmap.WorldRenderingSettings;
import xueluoanping.swayinggarden.mixin.SimpleMixinPlugin;

public class IrisHook {
   public static void reload() {
      if (SimpleMixinPlugin.isIrisLikeLoad()) {
         IrisAttach.reload(WorldRenderingSettings.INSTANCE.getBlockStateIds(), Iris.getIrisConfig().getShaderPackName().orElse(""));

         try {
            ((IAttach)WorldRenderingSettings.INSTANCE).swaying_garden$set(true);
         } catch (Exception e) {
            e.printStackTrace();
         }
      }
   }
}
