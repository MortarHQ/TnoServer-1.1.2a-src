package com.illusivesoulworks.polymorph.mixin;

import com.illusivesoulworks.polymorph.PolymorphConstants;
import com.illusivesoulworks.polymorph.common.integration.PolymorphIntegrations;
import com.illusivesoulworks.polymorph.platform.Services;
import java.util.List;
import java.util.Set;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.mixin.extensibility.IMixinConfig;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinErrorHandler;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.extensibility.IMixinErrorHandler.ErrorAction;

public class IntegratedMixinPlugin implements IMixinConfigPlugin, IMixinErrorHandler {
   private static final String PREFIX = "com.illusivesoulworks.polymorph.mixin.integration.";
   private static boolean isLoaded = false;

   public void onLoad(String mixinPackage) {
      Mixins.registerErrorHandlerClass("com.illusivesoulworks.polymorph.mixin.IntegratedMixinPlugin");
   }

   public String getRefMapperConfig() {
      return null;
   }

   public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
      if (!isLoaded) {
         PolymorphIntegrations.load();
         isLoaded = true;
      }

      if (!mixinClassName.startsWith("com.illusivesoulworks.polymorph.mixin.integration.")) {
         return true;
      }

      String modId = mixinClassName.substring("com.illusivesoulworks.polymorph.mixin.integration.".length()).split("\\.")[0];
      return PolymorphIntegrations.isActive(modId) && Services.PLATFORM.isModFileLoaded(modId);
   }

   public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
   }

   public List<String> getMixins() {
      return null;
   }

   public void postApply(String s, ClassNode classNode, String s1, IMixinInfo iMixinInfo) {
   }

   public void preApply(String s, ClassNode classNode, String s1, IMixinInfo iMixinInfo) {
   }

   public ErrorAction onPrepareError(IMixinConfig config, Throwable th, IMixinInfo mixin, ErrorAction action) {
      return null;
   }

   public ErrorAction onApplyError(String targetClassName, Throwable th, IMixinInfo mixin, ErrorAction action) {
      String pack = mixin.getClassName();
      if (pack.startsWith("com.illusivesoulworks.polymorph.mixin.integration.")) {
         String modId = pack.substring("com.illusivesoulworks.polymorph.mixin.integration.".length(), pack.lastIndexOf(46));
         PolymorphIntegrations.disable(modId);
         PolymorphConstants.LOG.error("Polymorph encountered an error while transforming: {}", targetClassName);
         PolymorphConstants.LOG.error("The integration module for {} will be disabled.", modId);
         PolymorphConstants.LOG.error("Please report this bug to Polymorph only, do not report this to {}.", modId);
         return ErrorAction.WARN;
      } else {
         return null;
      }
   }
}
