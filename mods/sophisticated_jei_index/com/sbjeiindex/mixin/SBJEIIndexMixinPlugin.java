package com.sbjeiindex.mixin;

import java.util.List;
import java.util.Set;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

public class SBJEIIndexMixinPlugin implements IMixinConfigPlugin {
   private boolean jeiInternalsPresent;

   public void onLoad(String mixinPackage) {
      this.jeiInternalsPresent = hasClass("mezz.jei.common.network.packets.PacketRecipeTransfer") || hasClass("mezz.jei.common.network.ServerPacketData");
   }

   public String getRefMapperConfig() {
      return null;
   }

   public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
      return this.jeiInternalsPresent
         || !mixinClassName.startsWith("com.sbjeiindex.mixin.")
         || !mixinClassName.endsWith("BasicRecipeTransferHandlerMixin")
            && !mixinClassName.endsWith("PacketRecipeTransferMixin")
            && !mixinClassName.endsWith("TransferOperationMixin");
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

   private static boolean hasClass(String name) {
      String path = name.replace('.', '/') + ".class";
      return SBJEIIndexMixinPlugin.class.getClassLoader().getResource(path) != null;
   }
}
