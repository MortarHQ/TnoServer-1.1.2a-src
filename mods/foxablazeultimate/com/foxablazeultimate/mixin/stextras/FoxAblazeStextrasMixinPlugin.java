package com.foxablazeultimate.mixin.stextras;

import java.util.List;
import java.util.Set;
import net.neoforged.fml.loading.LoadingModList;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

public final class FoxAblazeStextrasMixinPlugin implements IMixinConfigPlugin {
   private boolean stextrasLoaded;

   public void onLoad(String mixinPackage) {
      try {
         this.stextrasLoaded = LoadingModList.get() != null && LoadingModList.get().getModFileById("stextras") != null;
      } catch (Throwable t) {
         this.stextrasLoaded = false;
      }
   }

   public String getRefMapperConfig() {
      return null;
   }

   public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
      return this.stextrasLoaded;
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
