package com.moepus.byepregen;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.service.MixinService;

final class MixinGateEvaluator {
   private static final String GATE_DESCRIPTOR = Type.getDescriptor(MixinGate.class);
   private final MixinGateEvaluator.ClassNodeSource classNodes;
   private final Predicate<String> modExists;
   private final Predicate<String> classExists;

   MixinGateEvaluator(MixinGateEvaluator.ClassNodeSource classNodes, Predicate<String> modExists, Predicate<String> classExists) {
      this.classNodes = classNodes;
      this.modExists = modExists;
      this.classExists = classExists;
   }

   static MixinGateEvaluator createDefault() {
      return new MixinGateEvaluator(
         className -> MixinService.getService().getBytecodeProvider().getClassNode(className), MixinPlugin::isModExist, MixinPlugin::hasClass
      );
   }

   boolean shouldApply(String targetClassName, String mixinClassName, Config config) {
      MixinGateEvaluator.GateSpec gate = this.readGate(mixinClassName);
      validate(gate, mixinClassName);
      if (!isConfigEnabled(gate.config(), config, mixinClassName)) {
         return false;
      } else if (!this.allModsExist(gate.requiredMods())) {
         return false;
      } else {
         return this.hasConflictingMod(gate.conflictingMods()) ? false : gate.requiredMods().isEmpty() || this.classExists.test(targetClassName);
      }
   }

   private MixinGateEvaluator.GateSpec readGate(String mixinClassName) {
      try {
         ClassNode classNode = this.classNodes.get(mixinClassName);
         AnnotationNode annotation = findGate(classNode.invisibleAnnotations);
         if (annotation == null) {
            annotation = findGate(classNode.visibleAnnotations);
         }

         return annotation == null ? MixinGateEvaluator.GateSpec.NONE : MixinGateEvaluator.GateSpec.from(annotation, mixinClassName);
      } catch (IllegalStateException exception) {
         throw exception;
      } catch (Exception exception) {
         throw invalidGate(mixinClassName, "could not read mixin bytecode", exception);
      }
   }

   private boolean allModsExist(List<String> requiredMods) {
      return requiredMods.stream().allMatch(this.modExists);
   }

   private boolean hasConflictingMod(List<String> conflictingMods) {
      return conflictingMods.stream().anyMatch(this.modExists);
   }

   private static AnnotationNode findGate(List<AnnotationNode> annotations) {
      return annotations == null ? null : annotations.stream().filter(annotation -> GATE_DESCRIPTOR.equals(annotation.desc)).findFirst().orElse(null);
   }

   private static void validate(MixinGateEvaluator.GateSpec gate, String mixinClassName) {
      validateModIds(gate.requiredMods(), mixinClassName);
      validateModIds(gate.conflictingMods(), mixinClassName);
      Set<String> conflicts = new HashSet<>(gate.conflictingMods());
      if (gate.requiredMods().stream().anyMatch(conflicts::contains)) {
         throw invalidGate(mixinClassName, "a mod cannot be both required and conflicting", null);
      }
   }

   private static void validateModIds(List<String> modIds, String mixinClassName) {
      if (modIds.stream().anyMatch(modId -> modId == null || modId.isBlank())) {
         throw invalidGate(mixinClassName, "mod ids must not be blank", null);
      }
   }

   private static boolean isConfigEnabled(String fieldName, Config config, String mixinClassName) {
      if (fieldName.isEmpty()) {
         return true;
      }

      try {
         Field field = Config.class.getField(fieldName);
         if (field.getType() != boolean.class) {
            throw invalidGate(mixinClassName, "Config." + fieldName + " is not boolean", null);
         } else {
            return field.getBoolean(config);
         }
      } catch (NoSuchFieldException | IllegalAccessException exception) {
         throw invalidGate(mixinClassName, "invalid Config field: " + fieldName, exception);
      }
   }

   private static IllegalStateException invalidGate(String mixinClassName, String message, Throwable cause) {
      return new IllegalStateException("Invalid @MixinGate on " + mixinClassName + ": " + message, cause);
   }

   @FunctionalInterface
   interface ClassNodeSource {
      ClassNode get(String var1) throws Exception;
   }

   private record GateSpec(List<String> requiredMods, List<String> conflictingMods, String config) {
      private static final MixinGateEvaluator.GateSpec NONE = new MixinGateEvaluator.GateSpec(List.of(), List.of(), "");

      static MixinGateEvaluator.GateSpec from(AnnotationNode annotation, String mixinClassName) {
         return new MixinGateEvaluator.GateSpec(
            stringListValue(annotation, "requiredMods", mixinClassName),
            stringListValue(annotation, "conflictingMods", mixinClassName),
            stringValue(annotation, "config", mixinClassName)
         );
      }

      private static List<String> stringListValue(AnnotationNode annotation, String name, String mixinClassName) {
         Object value = value(annotation, name);
         if (value == null) {
            return List.of();
         } else if (value instanceof List<?> values && !values.stream().anyMatch(item -> !(item instanceof String))) {
            return values.stream().map(String.class::cast).toList();
         } else {
            throw MixinGateEvaluator.invalidGate(mixinClassName, name + " must be a string array", null);
         }
      }

      private static String stringValue(AnnotationNode annotation, String name, String mixinClassName) {
         Object value = value(annotation, name);
         if (value == null) {
            return "";
         } else if (value instanceof String string) {
            return string;
         } else {
            throw MixinGateEvaluator.invalidGate(mixinClassName, name + " must be a string", null);
         }
      }

      private static Object value(AnnotationNode annotation, String name) {
         if (annotation.values == null) {
            return null;
         }

         for (int index = 0; index < annotation.values.size(); index += 2) {
            if (name.equals(annotation.values.get(index))) {
               return annotation.values.get(index + 1);
            }
         }

         return null;
      }
   }
}
