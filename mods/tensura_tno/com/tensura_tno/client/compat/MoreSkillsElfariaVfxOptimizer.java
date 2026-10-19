package com.tensura_tno.client.compat;

import com.tensura_tno.TensuraTNOMod;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.ParticleStatus;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

public final class MoreSkillsElfariaVfxOptimizer {
   private static final double NEAR_DISTANCE = 64.0;
   private static final double MID_DISTANCE = 160.0;
   private static final double MINIMUM_RENDER_DISTANCE = 256.0;
   private static final double RENDER_DISTANCE_PADDING = 96.0;
   private static final MoreSkillsElfariaVfxOptimizer.MeshBudget ORIGINAL_MESH_BUDGET = new MoreSkillsElfariaVfxOptimizer.MeshBudget(18, 144, 18, 26, 280, 96);
   private static final MoreSkillsElfariaVfxOptimizer.MeshBudget NEAR_MESH_BUDGET = new MoreSkillsElfariaVfxOptimizer.MeshBudget(12, 96, 12, 18, 180, 64);
   private static final MoreSkillsElfariaVfxOptimizer.MeshBudget MID_MESH_BUDGET = new MoreSkillsElfariaVfxOptimizer.MeshBudget(8, 64, 8, 12, 96, 40);
   private static final MoreSkillsElfariaVfxOptimizer.MeshBudget FAR_MESH_BUDGET = new MoreSkillsElfariaVfxOptimizer.MeshBudget(4, 36, 4, 8, 48, 20);
   private static final String[] EFFECT_RENDERERS = new String[]{
      "com.github.wal_bos.moreskills.client.fx.ElfariaMyrdasFridolieteVfxClient",
      "com.github.wal_bos.moreskills.client.fx.ElfariaFruzelCardeneiaVfxClient",
      "com.github.wal_bos.moreskills.client.fx.ElfariaGlaciaLastAlbisVfxClient",
      "com.github.wal_bos.moreskills.client.fx.ElfariaStellasNateaVfxClient",
      "com.github.wal_bos.moreskills.client.fx.ElfariaPassiveIceStepsVfxClient",
      "com.github.wal_bos.moreskills.client.fx.ElfariaArsWeissBloomVfxClient",
      "com.github.wal_bos.moreskills.client.fx.ElfariaOpIceShineVfxClient"
   };
   private static final IdentityHashMap<List<?>, MoreSkillsElfariaVfxOptimizer.Selection> FRAME_SELECTIONS = new IdentityHashMap<>();
   private static final ClassValue<MoreSkillsElfariaVfxOptimizer.EffectLayout> EFFECT_LAYOUTS = new ClassValue<MoreSkillsElfariaVfxOptimizer.EffectLayout>() {
      protected MoreSkillsElfariaVfxOptimizer.EffectLayout computeValue(Class<?> type) {
         return MoreSkillsElfariaVfxOptimizer.EffectLayout.inspect(type);
      }
   };
   private static MoreSkillsElfariaVfxOptimizer.FrameContext frameContext;
   private static MoreSkillsElfariaVfxOptimizer.Detail currentDetail = MoreSkillsElfariaVfxOptimizer.Detail.ORIGINAL;
   private static ClientLevel observedLevel;
   private static boolean reflectionWarningLogged;

   private MoreSkillsElfariaVfxOptimizer() {
   }

   public static void beginFrame(RenderLevelStageEvent event) {
      endFrame();
      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft.level != null && event.getCamera() != null) {
         int renderDistanceChunks = (Integer)minecraft.options.renderDistance().get();
         double renderDistance = Math.max(256.0, renderDistanceChunks * 16.0 + 96.0);
         frameContext = new MoreSkillsElfariaVfxOptimizer.FrameContext(
            event.getCamera().getPosition(),
            event.getFrustum(),
            renderDistance * renderDistance,
            particlePenalty((ParticleStatus)minecraft.options.particles().get())
         );
      }
   }

   public static void endFrame() {
      frameContext = null;
      currentDetail = MoreSkillsElfariaVfxOptimizer.Detail.ORIGINAL;
      FRAME_SELECTIONS.clear();
   }

   public static void observeClientLevel() {
      ClientLevel currentLevel = Minecraft.getInstance().level;
      if (observedLevel == null) {
         observedLevel = currentLevel;
      } else {
         if (currentLevel != observedLevel) {
            clearEffectCollections();
            observedLevel = currentLevel;
         }
      }
   }

   public static boolean isEffectivelyEmpty(List<?> effects) {
      return frameContext != null && !effects.isEmpty() ? selectionFor(effects).effects().isEmpty() : effects.isEmpty();
   }

   public static Iterator<?> visibleIterator(List<?> effects) {
      if (frameContext != null && !effects.isEmpty()) {
         return new MoreSkillsElfariaVfxOptimizer.DetailIterator(selectionFor(effects).effects().iterator());
      }

      currentDetail = MoreSkillsElfariaVfxOptimizer.Detail.ORIGINAL;
      return effects.iterator();
   }

   public static boolean shouldOptimizeIceMesh() {
      return frameContext != null && currentDetail != MoreSkillsElfariaVfxOptimizer.Detail.ORIGINAL;
   }

   public static boolean isGeometryVisible(Vec3 cameraRelativeCenter, double extent) {
      MoreSkillsElfariaVfxOptimizer.FrameContext context = frameContext;
      if (context == null || currentDetail == MoreSkillsElfariaVfxOptimizer.Detail.ORIGINAL) {
         return true;
      } else if (isFinite(cameraRelativeCenter) && Double.isFinite(extent)) {
         double safeExtent = Math.max(0.25, Math.abs(extent));
         Vec3 worldCenter = cameraRelativeCenter.add(context.cameraPosition());
         AABB bounds = new AABB(worldCenter, worldCenter).inflate(safeExtent);
         return isVisible(context, bounds);
      } else {
         return true;
      }
   }

   public static int discRings() {
      return meshBudget(currentDetail).discRings();
   }

   public static int discSegments() {
      return meshBudget(currentDetail).discSegments();
   }

   public static int rectUSteps() {
      return meshBudget(currentDetail).rectUSteps();
   }

   public static int rectVSteps() {
      return meshBudget(currentDetail).rectVSteps();
   }

   public static int discLineCount(int original) {
      return Math.min(original, meshBudget(currentDetail).discLines());
   }

   public static int sheenCount(int original) {
      return Math.min(original, meshBudget(currentDetail).sheenLines());
   }

   static MoreSkillsElfariaVfxOptimizer.Detail detailForDistanceSquared(double distanceSquared, int particlePenalty) {
      MoreSkillsElfariaVfxOptimizer.Detail detail;
      if (distanceSquared <= 4096.0) {
         detail = MoreSkillsElfariaVfxOptimizer.Detail.NEAR;
      } else if (distanceSquared <= 25600.0) {
         detail = MoreSkillsElfariaVfxOptimizer.Detail.MID;
      } else {
         detail = MoreSkillsElfariaVfxOptimizer.Detail.FAR;
      }

      int shifted = Math.min(MoreSkillsElfariaVfxOptimizer.Detail.FAR.ordinal(), detail.ordinal() + Math.max(0, particlePenalty));
      return MoreSkillsElfariaVfxOptimizer.Detail.values()[shifted];
   }

   static int renderBudget(String simpleName, int particlePenalty) {
      MoreSkillsElfariaVfxOptimizer.EffectRule rule = MoreSkillsElfariaVfxOptimizer.EffectRule.forName(simpleName);
      if (rule == null) {
         return Integer.MAX_VALUE;
      }

      double multiplier = switch (Math.max(0, particlePenalty)) {
         case 0 -> 1.0;
         case 1 -> 0.75;
         default -> 0.5;
      };
      return Math.max(1, (int)Math.ceil(rule.renderBudget() * multiplier));
   }

   static double effectExtent(String simpleName, double radius) {
      MoreSkillsElfariaVfxOptimizer.EffectRule rule = MoreSkillsElfariaVfxOptimizer.EffectRule.forName(simpleName);
      return rule == null ? Double.POSITIVE_INFINITY : rule.padding() + Math.abs(radius) * rule.radiusScale();
   }

   static MoreSkillsElfariaVfxOptimizer.MeshBudget meshBudget(MoreSkillsElfariaVfxOptimizer.Detail detail) {
      return switch (detail) {
         case ORIGINAL -> ORIGINAL_MESH_BUDGET;
         case NEAR -> NEAR_MESH_BUDGET;
         case MID -> MID_MESH_BUDGET;
         case FAR -> FAR_MESH_BUDGET;
      };
   }

   private static MoreSkillsElfariaVfxOptimizer.Selection selectionFor(List<?> effects) {
      MoreSkillsElfariaVfxOptimizer.Selection cached = FRAME_SELECTIONS.get(effects);
      if (cached != null) {
         return cached;
      }

      MoreSkillsElfariaVfxOptimizer.Selection computed = buildSelection(effects);
      FRAME_SELECTIONS.put(effects, computed);
      return computed;
   }

   private static MoreSkillsElfariaVfxOptimizer.Selection buildSelection(List<?> effects) {
      if (effects.isEmpty()) {
         return MoreSkillsElfariaVfxOptimizer.Selection.EMPTY;
      }

      Object first = effects.getFirst();
      if (first == null) {
         return originalSelection(effects);
      }

      MoreSkillsElfariaVfxOptimizer.EffectRule rule = MoreSkillsElfariaVfxOptimizer.EffectRule.forName(first.getClass().getSimpleName());
      MoreSkillsElfariaVfxOptimizer.EffectLayout layout = EFFECT_LAYOUTS.get(first.getClass());
      if (rule != null && layout.valid()) {
         MoreSkillsElfariaVfxOptimizer.FrameContext context = frameContext;
         if (context == null) {
            return originalSelection(effects);
         }

         List<MoreSkillsElfariaVfxOptimizer.SelectedEffect> visible = new ArrayList<>(effects.size());

         try {
            for (int index = 0; index < effects.size(); index++) {
               Object effect = effects.get(index);
               if (effect == null || effect.getClass() != first.getClass()) {
                  return originalSelection(effects);
               }

               AABB bounds = layout.bounds(effect, rule);
               if (bounds == null) {
                  return originalSelection(effects);
               }

               if (isVisible(context, bounds)) {
                  double distanceSquared = distanceSquaredToAabb(context.cameraPosition(), bounds);
                  int priority = layout.priority(effect);
                  MoreSkillsElfariaVfxOptimizer.Detail detail = detailForDistanceSquared(distanceSquared, context.particlePenalty());
                  visible.add(new MoreSkillsElfariaVfxOptimizer.SelectedEffect(effect, detail, index, priority, distanceSquared));
               }
            }
         } catch (ReflectiveOperationException | RuntimeException exception) {
            warnReflectionFailure(first.getClass(), exception);
            return originalSelection(effects);
         }

         int budget = renderBudget(first.getClass().getSimpleName(), context.particlePenalty());
         if (visible.size() > budget) {
            visible.sort(
               Comparator.comparingInt(MoreSkillsElfariaVfxOptimizer.SelectedEffect::priority)
                  .reversed()
                  .thenComparingDouble(MoreSkillsElfariaVfxOptimizer.SelectedEffect::distanceSquared)
                  .thenComparingInt(MoreSkillsElfariaVfxOptimizer.SelectedEffect::sourceIndex)
            );
            visible = new ArrayList<>(visible.subList(0, budget));
            visible.sort(Comparator.comparingInt(MoreSkillsElfariaVfxOptimizer.SelectedEffect::sourceIndex));
         }

         return new MoreSkillsElfariaVfxOptimizer.Selection(List.copyOf(visible));
      } else {
         return originalSelection(effects);
      }
   }

   private static MoreSkillsElfariaVfxOptimizer.Selection originalSelection(List<?> effects) {
      List<MoreSkillsElfariaVfxOptimizer.SelectedEffect> original = new ArrayList<>(effects.size());

      for (int index = 0; index < effects.size(); index++) {
         original.add(new MoreSkillsElfariaVfxOptimizer.SelectedEffect(effects.get(index), MoreSkillsElfariaVfxOptimizer.Detail.ORIGINAL, index, 0, 0.0));
      }

      return new MoreSkillsElfariaVfxOptimizer.Selection(List.copyOf(original));
   }

   private static boolean isVisible(MoreSkillsElfariaVfxOptimizer.FrameContext context, AABB bounds) {
      Frustum frustum = context.frustum();
      return frustum != null && !frustum.isVisible(bounds)
         ? false
         : distanceSquaredToAabb(context.cameraPosition(), bounds) <= context.maximumDistanceSquared();
   }

   private static double distanceSquaredToAabb(Vec3 point, AABB bounds) {
      double dx = axisDistance(point.x, bounds.minX, bounds.maxX);
      double dy = axisDistance(point.y, bounds.minY, bounds.maxY);
      double dz = axisDistance(point.z, bounds.minZ, bounds.maxZ);
      return dx * dx + dy * dy + dz * dz;
   }

   private static double axisDistance(double value, double minimum, double maximum) {
      if (value < minimum) {
         return minimum - value;
      } else {
         return value > maximum ? value - maximum : 0.0;
      }
   }

   private static int particlePenalty(ParticleStatus status) {
      return switch (status) {
         case ALL -> 0;
         case DECREASED -> 1;
         case MINIMAL -> 2;
         default -> throw new MatchException(null, null);
      };
   }

   private static boolean isFinite(Vec3 value) {
      return value != null && Double.isFinite(value.x) && Double.isFinite(value.y) && Double.isFinite(value.z);
   }

   private static void clearEffectCollections() {
      ClassLoader loader = Thread.currentThread().getContextClassLoader();

      for (String className : EFFECT_RENDERERS) {
         try {
            Class<?> renderer = Class.forName(className, false, loader);

            for (Field field : renderer.getDeclaredFields()) {
               if (Modifier.isStatic(field.getModifiers())
                  && Collection.class.isAssignableFrom(field.getType())
                  && field.trySetAccessible()
                  && field.get(null) instanceof Collection<?> collection) {
                  collection.clear();
               }
            }
         } catch (ReflectiveOperationException | RuntimeException exception) {
            warnReflectionFailure(null, exception);
            return;
         }
      }
   }

   private static void warnReflectionFailure(Class<?> effectType, Throwable throwable) {
      if (!reflectionWarningLogged) {
         reflectionWarningLogged = true;
         String target = effectType == null ? "renderer lists" : effectType.getName();
         TensuraTNOMod.LOGGER.warn("[TensuraTNO] MoreSkills Elfaria optimization could not inspect {}; using original rendering", target, throwable);
      }
   }

   enum Detail {
      ORIGINAL,
      NEAR,
      MID,
      FAR;
   }

   private static final class DetailIterator implements Iterator<Object> {
      private final Iterator<MoreSkillsElfariaVfxOptimizer.SelectedEffect> delegate;

      private DetailIterator(Iterator<MoreSkillsElfariaVfxOptimizer.SelectedEffect> delegate) {
         this.delegate = delegate;
      }

      @Override
      public boolean hasNext() {
         boolean hasNext = this.delegate.hasNext();
         if (!hasNext) {
            MoreSkillsElfariaVfxOptimizer.currentDetail = MoreSkillsElfariaVfxOptimizer.Detail.ORIGINAL;
         }

         return hasNext;
      }

      @Override
      public Object next() {
         if (!this.delegate.hasNext()) {
            MoreSkillsElfariaVfxOptimizer.currentDetail = MoreSkillsElfariaVfxOptimizer.Detail.ORIGINAL;
            throw new NoSuchElementException();
         } else {
            MoreSkillsElfariaVfxOptimizer.SelectedEffect selected = this.delegate.next();
            MoreSkillsElfariaVfxOptimizer.currentDetail = selected.detail();
            return selected.effect();
         }
      }
   }

   private record EffectLayout(List<Field> positionFields, List<Field> radiusFields, List<Field> priorityFields, boolean valid) {
      private static MoreSkillsElfariaVfxOptimizer.EffectLayout inspect(Class<?> type) {
         List<Field> positions = new ArrayList<>();
         List<Field> radii = new ArrayList<>();
         List<Field> priorities = new ArrayList<>();

         for (Field field : type.getDeclaredFields()) {
            String name = field.getName().toLowerCase(Locale.ROOT);
            boolean relevant = field.getType() == Vec3.class && isPositionField(name) || isRadiusField(field, name) || isPriorityField(field, name);
            if (relevant && field.trySetAccessible()) {
               if (field.getType() == Vec3.class) {
                  positions.add(field);
               } else if (isRadiusField(field, name)) {
                  radii.add(field);
               } else {
                  priorities.add(field);
               }
            }
         }

         return new MoreSkillsElfariaVfxOptimizer.EffectLayout(List.copyOf(positions), List.copyOf(radii), List.copyOf(priorities), !positions.isEmpty());
      }

      private AABB bounds(Object effect, MoreSkillsElfariaVfxOptimizer.EffectRule rule) throws IllegalAccessException {
         double minX = Double.POSITIVE_INFINITY;
         double minY = Double.POSITIVE_INFINITY;
         double minZ = Double.POSITIVE_INFINITY;
         double maxX = Double.NEGATIVE_INFINITY;
         double maxY = Double.NEGATIVE_INFINITY;
         double maxZ = Double.NEGATIVE_INFINITY;

         for (Field field : this.positionFields) {
            if (!(field.get(effect) instanceof Vec3 position) || !MoreSkillsElfariaVfxOptimizer.isFinite(position)) {
               return null;
            }

            minX = Math.min(minX, position.x);
            minY = Math.min(minY, position.y);
            minZ = Math.min(minZ, position.z);
            maxX = Math.max(maxX, position.x);
            maxY = Math.max(maxY, position.y);
            maxZ = Math.max(maxZ, position.z);
         }

         double radius = 0.0;

         for (Field field : this.radiusFields) {
            if (!(field.get(effect) instanceof Number number) || !Double.isFinite(number.doubleValue())) {
               return null;
            }

            radius = Math.max(radius, Math.abs(number.doubleValue()));
         }

         double extent = rule.padding() + radius * rule.radiusScale();
         return new AABB(minX, minY, minZ, maxX, maxY, maxZ).inflate(extent);
      }

      private int priority(Object effect) throws IllegalAccessException {
         int priority = 0;

         for (Field field : this.priorityFields) {
            if (field.getBoolean(effect)) {
               String name = field.getName().toLowerCase(Locale.ROOT);
               priority += name.equals("huge") ? 4 : (name.equals("main") ? 3 : 1);
            }
         }

         return priority;
      }

      private static boolean isPositionField(String name) {
         return switch (name) {
            case "center", "caster", "start", "target", "visualend", "top", "impact", "pos", "origin", "lastplayerpos" -> true;
            default -> false;
         };
      }

      private static boolean isRadiusField(Field field, String name) {
         Class<?> type = field.getType();
         return (type == float.class || type == double.class) && name.contains("radius");
      }

      private static boolean isPriorityField(Field field, String name) {
         return field.getType() == boolean.class && (name.equals("main") || name.equals("huge") || name.equals("revive"));
      }
   }

   private record EffectRule(int renderBudget, double radiusScale, double padding) {
      private static final Map<String, MoreSkillsElfariaVfxOptimizer.EffectRule> RULES = Map.ofEntries(
         Map.entry("Barrier", new MoreSkillsElfariaVfxOptimizer.EffectRule(8, 2.5, 8.0)),
         Map.entry("FreezeField", new MoreSkillsElfariaVfxOptimizer.EffectRule(10, 4.0, 8.0)),
         Map.entry("Cathedral", new MoreSkillsElfariaVfxOptimizer.EffectRule(1, 5.0, 24.0)),
         Map.entry("Spike", new MoreSkillsElfariaVfxOptimizer.EffectRule(12, 4.0, 8.0)),
         Map.entry("Charge", new MoreSkillsElfariaVfxOptimizer.EffectRule(3, 22.0, 32.0)),
         Map.entry("Strike", new MoreSkillsElfariaVfxOptimizer.EffectRule(40, 16.0, 8.0)),
         Map.entry("Phoenix", new MoreSkillsElfariaVfxOptimizer.EffectRule(4, 42.0, 64.0)),
         Map.entry("Burst", new MoreSkillsElfariaVfxOptimizer.EffectRule(3, 9.0, 24.0)),
         Map.entry("Step", new MoreSkillsElfariaVfxOptimizer.EffectRule(48, 2.5, 4.0)),
         Map.entry("Bloom", new MoreSkillsElfariaVfxOptimizer.EffectRule(24, 2.0, 6.0)),
         Map.entry("Storm", new MoreSkillsElfariaVfxOptimizer.EffectRule(18, 4.0, 20.0)),
         Map.entry("Flight", new MoreSkillsElfariaVfxOptimizer.EffectRule(8, 32.0, 48.0))
      );

      private static MoreSkillsElfariaVfxOptimizer.EffectRule forName(String simpleName) {
         return RULES.get(simpleName);
      }
   }

   private record FrameContext(Vec3 cameraPosition, Frustum frustum, double maximumDistanceSquared, int particlePenalty) {
   }

   record MeshBudget(int discRings, int discSegments, int rectUSteps, int rectVSteps, int discLines, int sheenLines) {
   }

   private record SelectedEffect(Object effect, MoreSkillsElfariaVfxOptimizer.Detail detail, int sourceIndex, int priority, double distanceSquared) {
   }

   private record Selection(List<MoreSkillsElfariaVfxOptimizer.SelectedEffect> effects) {
      private static final MoreSkillsElfariaVfxOptimizer.Selection EMPTY = new MoreSkillsElfariaVfxOptimizer.Selection(List.of());
   }
}
