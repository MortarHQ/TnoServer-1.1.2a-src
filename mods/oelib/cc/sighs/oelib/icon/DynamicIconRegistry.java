package cc.sighs.oelib.icon;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public final class DynamicIconRegistry {
   private static final Map<String, List<IconVariant>> VARIANTS = new ConcurrentHashMap<>();
   private static final Map<String, String> SELECTED_FABRIC_PATH = new ConcurrentHashMap<>();
   private static final Map<String, String> SELECTED_NEOFORGE_PATH = new ConcurrentHashMap<>();
   private static final Random RANDOM = new Random();

   private DynamicIconRegistry() {
   }

   public static DynamicIconRegistry.Builder forMod(String modId) {
      return new DynamicIconRegistry.Builder(modId);
   }

   public static boolean hasConfig(String modId) {
      return VARIANTS.containsKey(modId);
   }

   public static void register(String modId, List<IconVariant> variants) {
      if (variants != null && !variants.isEmpty()) {
         VARIANTS.put(modId, List.copyOf(variants));
      }
   }

   public static Optional<String> getSelectedFabricPath(String modId) {
      String cached = SELECTED_FABRIC_PATH.get(modId);
      if (cached != null) {
         return Optional.of(cached);
      }

      List<IconVariant> variants = VARIANTS.get(modId);
      if (variants != null && !variants.isEmpty()) {
         List<IconVariant> effective = filterForFabric(variants);
         if (effective.isEmpty()) {
            return Optional.empty();
         }

         IconVariant picked = pickWeighted(effective);
         String path = resolveFabricPath(modId, picked);
         SELECTED_FABRIC_PATH.put(modId, path);
         return Optional.of(path);
      } else {
         return Optional.empty();
      }
   }

   public static Optional<String> getSelectedNeoForgePath(String modId) {
      String cached = SELECTED_NEOFORGE_PATH.get(modId);
      if (cached != null) {
         return Optional.of(cached);
      }

      List<IconVariant> variants = VARIANTS.get(modId);
      if (variants != null && !variants.isEmpty()) {
         List<IconVariant> effective = filterForNeoForge(variants);
         if (effective.isEmpty()) {
            return Optional.empty();
         }

         IconVariant picked = pickWeighted(effective);
         String path = resolveNeoForgePath(picked);
         SELECTED_NEOFORGE_PATH.put(modId, path);
         return Optional.of(path);
      } else {
         return Optional.empty();
      }
   }

   private static IconVariant pickWeighted(List<IconVariant> variants) {
      int total = 0;

      for (IconVariant v : variants) {
         total += Math.max(1, v.getWeight());
      }

      int r = RANDOM.nextInt(total);
      int acc = 0;

      for (IconVariant v : variants) {
         acc += Math.max(1, v.getWeight());
         if (r < acc) {
            return v;
         }
      }

      return variants.getLast();
   }

   private static List<IconVariant> filterForFabric(List<IconVariant> variants) {
      List<IconVariant> out = new ArrayList<>();

      for (IconVariant v : variants) {
         if (v.appliesToFabric()) {
            out.add(v);
         }
      }

      return out;
   }

   private static List<IconVariant> filterForNeoForge(List<IconVariant> variants) {
      List<IconVariant> out = new ArrayList<>();

      for (IconVariant v : variants) {
         if (v.appliesToNeoForge()) {
            out.add(v);
         }
      }

      return out;
   }

   private static String resolveFabricPath(String modId, IconVariant variant) {
      return variant.isPath() ? variant.getPath() : "assets/" + modId + "/" + variant.getName();
   }

   private static String resolveNeoForgePath(IconVariant variant) {
      return variant.isPath() ? variant.getPath() : variant.getName();
   }

   public static final class Builder {
      private final String modId;
      private final List<IconVariant> localVariants = new ArrayList<>();

      private Builder(String modId) {
         this.modId = modId;
      }

      public DynamicIconRegistry.Builder addName(String fileName, int weight) {
         this.localVariants.add(IconVariant.name(fileName, weight));
         return this;
      }

      public DynamicIconRegistry.Builder addPath(String path, int weight) {
         this.localVariants.add(IconVariant.path(path, weight));
         return this;
      }

      public DynamicIconRegistry.Builder addNameFabric(String fileName, int weight) {
         this.localVariants.add(IconVariant.nameFabric(fileName, weight));
         return this;
      }

      public DynamicIconRegistry.Builder addPathFabric(String path, int weight) {
         this.localVariants.add(IconVariant.pathFabric(path, weight));
         return this;
      }

      public DynamicIconRegistry.Builder addNameNeoForge(String fileName, int weight) {
         this.localVariants.add(IconVariant.nameNeoForge(fileName, weight));
         return this;
      }

      public DynamicIconRegistry.Builder addPathNeoForge(String path, int weight) {
         this.localVariants.add(IconVariant.pathNeoForge(path, weight));
         return this;
      }

      public void register() {
         DynamicIconRegistry.register(this.modId, this.localVariants);
      }
   }
}
