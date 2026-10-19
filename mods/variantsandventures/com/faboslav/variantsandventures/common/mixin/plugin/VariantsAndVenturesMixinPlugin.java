package com.faboslav.variantsandventures.common.mixin.plugin;

import java.util.List;
import java.util.Set;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

public class VariantsAndVenturesMixinPlugin implements IMixinConfigPlugin {
   public void onLoad(String mixinPackage) {
   }

   public String getRefMapperConfig() {
      return null;
   }

   public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
      return mixinClassName.equals("com.faboslav.variantsandventures.common.mixin.IntegratedServerLoaderMixin")
         ? this.isClassAvailable("me.earth.mc_runtime_test.McRuntimeTest")
         : true;
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

   private boolean isClassAvailable(String className) {
      String classPath = className.replace('.', '/') + ".class";
      return this.getClass().getClassLoader().getResource(classPath) != null;
   }
}
