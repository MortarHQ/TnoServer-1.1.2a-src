package xueluoanping.swayinggarden.mixin;

import java.util.List;
import java.util.Set;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

public class SimpleMixinPlugin implements IMixinConfigPlugin {
   private static int isIrisLikeLoad = 0;

   public static boolean isIrisLikeLoad() {
      return isIrisLikeLoad == 1;
   }

   public void onLoad(String mixinPackage) {
   }

   public String getRefMapperConfig() {
      return null;
   }

   public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
      if (isIrisLikeLoad == 0) {
         try {
            isIrisLikeLoad = 1;
         } catch (Exception ignored) {
            isIrisLikeLoad = 2;
         }
      }

      return isIrisLikeLoad();
   }

   public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
   }

   public List<String> getMixins() {
      return null;
   }

   public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
   }

   public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
   }
}
