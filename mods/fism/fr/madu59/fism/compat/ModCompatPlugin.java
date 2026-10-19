package fr.madu59.fism.compat;

import fr.madu59.fism.platform.PlatformHelper;
import java.util.List;
import java.util.Set;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

public class ModCompatPlugin implements IMixinConfigPlugin {
   public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
      if (!mixinClassName.contains("compat.iris.")) {
         return mixinClassName.contains("compat.entityculling.") ? PlatformHelper.isModLoaded("entityculling") : true;
      } else {
         return PlatformHelper.isModLoaded("iris") || PlatformHelper.isModLoaded("oculus");
      }
   }

   public void onLoad(String mixinPackage) {
   }

   public String getRefMapperConfig() {
      return null;
   }

   public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
   }

   public List<String> getMixins() {
      return null;
   }

   public void preApply(String target, ClassNode targetClass, String mixin, IMixinInfo mixinInfo) {
   }

   public void postApply(String target, ClassNode targetClass, String mixin, IMixinInfo mixinInfo) {
   }
}
