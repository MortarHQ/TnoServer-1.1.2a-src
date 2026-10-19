package com.natamus.collective.forge.mixin.plugin;

import com.natamus.collective.forge.bundle.ForgeBundleConfigCheck;
import com.natamus.collective.forge.bundle.ForgeBundleJarJarCheck;
import java.util.List;
import java.util.Set;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

public class ForgeMixinConfigPlugin implements IMixinConfigPlugin {
   public void onLoad(String mixinPackage) {
   }

   public String getRefMapperConfig() {
      return null;
   }

   public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
      if ((!mixinClassName.contains(".neoforge.") && !mixinClassName.contains("_neoforge.") || isNeoForge())
         && (!mixinClassName.contains(".forge.") && !mixinClassName.contains("_forge.") || isForge())
         && (!mixinClassName.contains(".fabric.") && !mixinClassName.contains("_fabric.") || isFabric())) {
         String[] pSpl = mixinClassName.split("\\.");
         if (pSpl.length < 3) {
            return true;
         }

         String modId = pSpl[2].split("_")[0];
         return ForgeBundleJarJarCheck.isModJarJard(modId) ? ForgeBundleConfigCheck.isBundleModEnabled(modId) : true;
      } else {
         return false;
      }
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

   private static boolean isFabric() {
      try {
         Class.forName("net.fabricmc.api.EnvType");
         return true;
      } catch (ClassNotFoundException e) {
         return false;
      }
   }

   private static boolean isForge() {
      try {
         Class.forName("net.minecraftforge.fml.loading.FMLEnvironment");
         return true;
      } catch (ClassNotFoundException e) {
         return false;
      }
   }

   private static boolean isNeoForge() {
      try {
         Class.forName("net.neoforged.fml.loading.FMLEnvironment");
         return true;
      } catch (ClassNotFoundException e) {
         return false;
      }
   }
}
