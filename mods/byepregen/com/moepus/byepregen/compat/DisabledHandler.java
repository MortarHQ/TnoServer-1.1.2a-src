package com.moepus.byepregen.compat;

import com.bawnorton.mixinsquared.adjuster.tools.AdjustableAnnotationNode;
import java.util.List;
import org.objectweb.asm.tree.MethodNode;

record DisabledHandler(String mixinClassName, String handlerName, String annotationDesc) {
   static DisabledHandler.Target target(String mixinClassName, MethodNode handlerNode, AdjustableAnnotationNode annotationNode) {
      return new DisabledHandler.Target(mixinClassName, handlerNode.name, annotationNode.desc);
   }

   static boolean matchesAny(List<DisabledHandler> handlers, DisabledHandler.Target target) {
      for (DisabledHandler handler : handlers) {
         if (handler.matches(target)) {
            return true;
         }
      }

      return false;
   }

   private boolean matches(DisabledHandler.Target target) {
      return this.mixinClassName.equals(target.mixinClassName)
         && this.handlerName.equals(target.handlerName)
         && this.annotationDesc.equals(target.annotationDesc);
   }

   record Target(String mixinClassName, String handlerName, String annotationDesc) {
   }
}
