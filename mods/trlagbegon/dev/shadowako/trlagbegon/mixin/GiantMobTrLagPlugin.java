package dev.shadowako.trlagbegon.mixin;

import java.util.List;
import java.util.Set;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

public final class GiantMobTrLagPlugin implements IMixinConfigPlugin {
   private static final String GIANT_IFACE = "io.github.manasmods.tensura.entity.template.subclass.IGiantMob";
   private static final String BLOCK_POS = "net/minecraft/core/BlockPos";
   private static final String HOOK = "dev/shadowako/trlagbegon/hook/IGiantMobBlockScanHooks";
   private static final String BT = "(Lnet/minecraft/world/entity/LivingEntity;FZILnet/minecraft/world/SimpleContainer;Z)I";
   private static final String DIG = "(Lnet/minecraft/world/entity/LivingEntity;FIFZLnet/minecraft/world/SimpleContainer;)V";
   private static final String FL = "(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/tags/TagKey;FZI)Z";

   public void onLoad(String mixinPackage) {
   }

   public String getRefMapperConfig() {
      return null;
   }

   public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
      return true;
   }

   public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
   }

   public List<String> getMixins() {
      return null;
   }

   public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
   }

   public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
      if (mixinClassName != null && mixinClassName.replace('/', '.').endsWith("IGiantMobTrLagMixin")) {
         if ("io.github.manasmods.tensura.entity.template.subclass.IGiantMob".equals(targetClassName.replace('/', '.'))) {
            for (MethodNode m : targetClass.methods) {
               String hook = hook(m.name, m.desc);
               if (hook != null && m.instructions != null) {
                  for (AbstractInsnNode ins = m.instructions.getFirst(); ins != null; ins = ins.getNext()) {
                     if (ins instanceof MethodInsnNode min
                        && min.getOpcode() == 184
                        && "net/minecraft/core/BlockPos".equals(min.owner)
                        && "betweenClosed".equals(min.name)
                        && "(IIIIII)Ljava/lang/Iterable;".equals(min.desc)) {
                        min.owner = "dev/shadowako/trlagbegon/hook/IGiantMobBlockScanHooks";
                        min.name = hook;
                        min.desc = "(IIIIII)Ljava/lang/Iterable;";
                        min.itf = false;
                        break;
                     }
                  }
               }
            }
         }
      }
   }

   private static String hook(String name, String desc) {
      if ("breakBlocks".equals(name) && "(Lnet/minecraft/world/entity/LivingEntity;FZILnet/minecraft/world/SimpleContainer;Z)I".equals(desc)) {
         return "trlag$breakScan";
      } else if ("digBlocks".equals(name) && "(Lnet/minecraft/world/entity/LivingEntity;FIFZLnet/minecraft/world/SimpleContainer;)V".equals(desc)) {
         return "trlag$digScan";
      } else {
         return "removeFluid".equals(name) && "(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/tags/TagKey;FZI)Z".equals(desc)
            ? "trlag$fluidScan"
            : null;
      }
   }
}
