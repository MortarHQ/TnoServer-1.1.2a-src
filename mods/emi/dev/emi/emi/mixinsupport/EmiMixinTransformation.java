package dev.emi.emi.mixinsupport;

import com.google.common.collect.Maps;
import dev.emi.emi.mixinsupport.annotation.Transform;
import java.util.Map;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.util.Annotations;

public class EmiMixinTransformation {
   private static int VISIBILITY_MASK = -8;

   public static void relinkTransforms(ClassNode clazz) {
      Map<String, MethodNode> changedMethods = Maps.newHashMap();

      for (MethodNode method : clazz.methods) {
         String target = method.name + method.desc;
         if (applyTransform(method)) {
            changedMethods.put(target, method);
         }
      }

      Map<String, FieldNode> changedFields = Maps.newHashMap();

      for (FieldNode field : clazz.fields) {
         String target = field.name + ":" + field.desc;
         if (applyTransform(field)) {
            changedFields.put(target, field);
         }
      }

      for (MethodNode method : clazz.methods) {
         for (AbstractInsnNode node : method.instructions) {
            if (node instanceof MethodInsnNode min) {
               String target = min.name + min.desc;
               if (changedMethods.containsKey(target)) {
                  MethodNode changed = changedMethods.get(target);
                  min.name = changed.name;
                  min.desc = changed.desc;
               }
            } else if (node instanceof FieldInsnNode fin) {
               String target = fin.name + ":" + fin.desc;
               if (changedFields.containsKey(target)) {
                  FieldNode changed = changedFields.get(target);
                  fin.name = changed.name;
                  fin.desc = changed.desc;
               }
            }
         }
      }
   }

   public static void applyTransform(ClassNode clazz) {
      AnnotationNode transform = Annotations.getInvisible(clazz, Transform.class);
      if (transform != null) {
         String visibility = (String)Annotations.getValue(transform, "visibility", "");
         int flags = (Integer)Annotations.getValue(transform, "flags", 0);
         clazz.access = mutateAccess(clazz.access, visibility);
         clazz.access |= flags;
      }
   }

   public static boolean applyTransform(MethodNode method) {
      AnnotationNode transform = Annotations.getInvisible(method, Transform.class);
      if (transform != null) {
         String name = (String)Annotations.getValue(transform, "name", "");
         String desc = (String)Annotations.getValue(transform, "desc", "");
         String visibility = (String)Annotations.getValue(transform, "visibility", "");
         int flags = (Integer)Annotations.getValue(transform, "flags", 0);
         if (!name.isEmpty()) {
            method.name = name;
         }

         if (!desc.isEmpty()) {
            method.desc = desc;
         }

         method.access = mutateAccess(method.access, visibility);
         method.access |= flags;
         return true;
      } else {
         return false;
      }
   }

   public static boolean applyTransform(FieldNode field) {
      AnnotationNode transform = Annotations.getInvisible(field, Transform.class);
      if (transform != null) {
         String name = (String)Annotations.getValue(transform, "name", "");
         String desc = (String)Annotations.getValue(transform, "desc", "");
         String visibility = (String)Annotations.getValue(transform, "visibility", "");
         int flags = (Integer)Annotations.getValue(transform, "flags", 0);
         if (!name.isEmpty()) {
            field.name = name;
         }

         if (!desc.isEmpty()) {
            field.desc = desc;
         }

         field.access = mutateAccess(field.access, visibility);
         field.access |= flags;
         return true;
      } else {
         return false;
      }
   }

   private static int mutateAccess(int access, String visibility) {
      return switch (visibility) {
         case "PRIVATE" -> access & VISIBILITY_MASK | 2;
         case "PROTECTED" -> access & VISIBILITY_MASK | 4;
         case "PUBLIC" -> access & VISIBILITY_MASK | 1;
         case "PACKAGE" -> access & VISIBILITY_MASK;
         default -> access;
      };
   }
}
