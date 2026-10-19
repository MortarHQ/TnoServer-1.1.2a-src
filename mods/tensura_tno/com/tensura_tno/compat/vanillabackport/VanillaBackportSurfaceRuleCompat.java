package com.tensura_tno.compat.vanillabackport;

import com.mojang.logging.LogUtils;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.SurfaceRules.RuleSource;

public final class VanillaBackportSurfaceRuleCompat {
   private static final String VANILLA_RULE = "net.minecraft.world.level.levelgen.SurfaceRules$";
   private static final String SPATIAL_NOISE = "com.blackgear.vanillabackport.common.worldgen.surface.SpatialNoiseThresholdConditionSource";
   private static final AtomicBoolean WARNED = new AtomicBoolean();
   private static final ClassValue<Map<String, Field>> FIELDS = new ClassValue<Map<String, Field>>() {
      protected Map<String, Field> computeValue(Class<?> type) {
         Map<String, Field> fields = new HashMap<>();

         for (Field field : type.getDeclaredFields()) {
            if (field.trySetAccessible()) {
               fields.put(field.getName(), field);
            }
         }

         return Map.copyOf(fields);
      }
   };

   private VanillaBackportSurfaceRuleCompat() {
   }

   public static RuleSource deduplicate(RuleSource root) {
      if (isSequence(root)
         && BuiltInRegistries.BLOCK.containsKey(ResourceLocation.withDefaultNamespace("sulfur"))
         && BuiltInRegistries.BLOCK.containsKey(ResourceLocation.withDefaultNamespace("cinnabar"))) {
         try {
            Method factory = VanillaBackportSurfaceRuleCompat.BackportFactory.MAKE_RULES;
            if (factory == null) {
               return root;
            }

            RuleSource expected = (RuleSource)factory.invoke(null);
            return isKnownSulfurRule(expected) ? deduplicate(root, expected) : root;
         } catch (ReflectiveOperationException | RuntimeException | LinkageError exception) {
            warn(exception);
            return root;
         }
      } else {
         return root;
      }
   }

   static RuleSource deduplicate(RuleSource root, RuleSource expected) {
      if (!isSequence(root)) {
         return root;
      }

      try {
         ArrayDeque<VanillaBackportSurfaceRuleCompat.Frame> stack = new ArrayDeque<>();
         stack.push(new VanillaBackportSurfaceRuleCompat.Frame(root));
         boolean found = false;
         int visited = 0;

         while (!stack.isEmpty()) {
            if (++visited > 100000) {
               return root;
            }

            VanillaBackportSurfaceRuleCompat.Frame frame = stack.peek();
            if (frame.index < frame.children.size()) {
               RuleSource child = frame.children.get(frame.index++);
               if (isSequence(child)) {
                  stack.push(new VanillaBackportSurfaceRuleCompat.Frame(child));
               } else if (equivalent(child, expected)) {
                  frame.accept(child, found ? null : child);
                  found = true;
               } else {
                  frame.accept(child, child);
               }
            } else {
               RuleSource result = frame.finish();
               stack.pop();
               if (stack.isEmpty()) {
                  return result == null ? root : result;
               }

               stack.peek().accept(frame.original, result);
            }
         }
      } catch (ReflectiveOperationException | RuntimeException exception) {
         warn(exception);
      }

      return root;
   }

   private static boolean isKnownSulfurRule(RuleSource rule) throws ReflectiveOperationException {
      if (isType(rule, "TestRuleSource")
         && Objects.equals(
            read(rule, "ifTrue"),
            SurfaceRules.isBiome(new ResourceKey[]{ResourceKey.create(Registries.BIOME, ResourceLocation.withDefaultNamespace("sulfur_caves"))})
         )) {
         Object body = read(rule, "thenRun");
         if (!isSequence(body)) {
            return false;
         }

         List<RuleSource> branches = children(body);
         if (branches.size() != 4) {
            return false;
         }

         int i = 0;

         while (i < branches.size()) {
            Object branch = branches.get(i);
            if (isType(branch, "TestRuleSource") && isType(read(branch, "thenRun"), "BlockRuleSource")) {
               Object condition = read(branch, "ifTrue");
               if (i < 3
                  ? condition.getClass().getName().equals("com.blackgear.vanillabackport.common.worldgen.surface.SpatialNoiseThresholdConditionSource")
                  : condition == SurfaceRules.abovePreliminarySurface()) {
                  i++;
                  continue;
               }

               return false;
            }

            return false;
         }

         return true;
      } else {
         return false;
      }
   }

   private static boolean equivalent(Object left, Object right) throws ReflectiveOperationException {
      ArrayDeque<VanillaBackportSurfaceRuleCompat.Pair> pending = new ArrayDeque<>();
      pending.push(new VanillaBackportSurfaceRuleCompat.Pair(left, right));
      int compared = 0;

      while (!pending.isEmpty()) {
         if (++compared > 64) {
            return false;
         }

         VanillaBackportSurfaceRuleCompat.Pair pair = pending.pop();
         Object a = pair.left;
         Object b = pair.right;
         if (a != b) {
            if (a != null && b != null && a.getClass() == b.getClass()) {
               if (isSequence(a)) {
                  List<RuleSource> aa = children(a);
                  List<RuleSource> bb = children(b);
                  if (aa.size() == bb.size() && aa.size() <= 64) {
                     for (int i = 0; i < aa.size(); i++) {
                        pending.push(new VanillaBackportSurfaceRuleCompat.Pair(aa.get(i), bb.get(i)));
                     }
                     continue;
                  }

                  return false;
               }

               if (isType(a, "TestRuleSource")) {
                  pending.push(new VanillaBackportSurfaceRuleCompat.Pair(read(a, "ifTrue"), read(b, "ifTrue")));
                  pending.push(new VanillaBackportSurfaceRuleCompat.Pair(read(a, "thenRun"), read(b, "thenRun")));
                  continue;
               }

               if (a.getClass().getName().equals("com.blackgear.vanillabackport.common.worldgen.surface.SpatialNoiseThresholdConditionSource")) {
                  if (Objects.equals(read(a, "noise"), read(b, "noise"))
                     && Objects.equals(read(a, "minThreshold"), read(b, "minThreshold"))
                     && Objects.equals(read(a, "maxThreshold"), read(b, "maxThreshold"))) {
                     continue;
                  }

                  return false;
               }

               if (a.getClass().getName().startsWith("net.minecraft.world.level.levelgen.SurfaceRules$") && a.equals(b)) {
                  continue;
               }

               return false;
            }

            return false;
         }
      }

      return true;
   }

   private static boolean isType(Object value, String name) {
      return value != null && value.getClass().getName().equals("net.minecraft.world.level.levelgen.SurfaceRules$" + name);
   }

   private static boolean isSequence(Object value) {
      return isType(value, "SequenceRuleSource");
   }

   private static Object read(Object target, String name) throws ReflectiveOperationException {
      Field field = FIELDS.get(target.getClass()).get(name);
      if (field == null) {
         throw new NoSuchFieldException(target.getClass().getName() + "." + name);
      } else {
         return field.get(target);
      }
   }

   private static List<RuleSource> children(Object rule) throws ReflectiveOperationException {
      return (List<RuleSource>)read(rule, "sequence");
   }

   private static void warn(Throwable exception) {
      if (WARNED.compareAndSet(false, true)) {
         LogUtils.getLogger().warn("Could not deduplicate VanillaBackport surface rules; preserving original rules", exception);
      }
   }

   private static final class BackportFactory {
      static final Method MAKE_RULES = find();

      private static Method find() {
         try {
            return Class.forName(
                  "com.blackgear.vanillabackport.common.worldgen.ModSurfaceRuleData", false, VanillaBackportSurfaceRuleCompat.class.getClassLoader()
               )
               .getMethod("makeRules");
         } catch (ClassNotFoundException exception) {
            return null;
         } catch (ReflectiveOperationException | RuntimeException | LinkageError exception) {
            VanillaBackportSurfaceRuleCompat.warn(exception);
            return null;
         }
      }
   }

   private static final class Frame {
      final RuleSource original;
      final List<RuleSource> children;
      final List<RuleSource> kept = new ArrayList<>();
      int index;
      boolean changed;

      Frame(RuleSource original) throws ReflectiveOperationException {
         this.original = original;
         this.children = VanillaBackportSurfaceRuleCompat.children(original);
      }

      void accept(RuleSource before, RuleSource after) {
         this.changed |= before != after;
         if (after != null) {
            this.kept.add(after);
         }
      }

      RuleSource finish() {
         if (!this.changed) {
            return this.original;
         } else {
            return this.kept.isEmpty() ? null : (this.kept.size() == 1 ? this.kept.getFirst() : SurfaceRules.sequence(this.kept.toArray(RuleSource[]::new)));
         }
      }
   }

   private record Pair(Object left, Object right) {
   }
}
