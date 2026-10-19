package dev.aika.smsn;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.objectweb.asm.tree.ClassNode;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

public class ModMixinPlugin implements IMixinConfigPlugin {
   private static final Logger log = SMSN.LOGGER;
   private static final Marker marker = MarkerFactory.getMarker("ModMixinPlugin");

   public void onLoad(String mixinPackage) {
   }

   public String getRefMapperConfig() {
      return null;
   }

   public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
      Map<String, String> mixinClassNames = SMSNPlatform.getMixinClassNames();
      if (mixinClassNames.containsKey(mixinClassName)) {
         return SMSNPlatform.isModLoaded(mixinClassNames.get(mixinClassName));
      }

      log.warn(marker, "Unknown mixin class: {}", mixinClassName);
      return false;
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
