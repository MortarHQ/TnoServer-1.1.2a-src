package com.blackgear.platform.common.data;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.resources.ResourceLocation;

public class DataTransformer {
   private static final ThreadLocal<Boolean> REENTRANT_GUARD = ThreadLocal.withInitial(() -> false);
   private static final List<Function<ResourceLocation, ResourceLocation>> TRANSFORMERS = new CopyOnWriteArrayList<>();

   public static void onDataTransformation(Consumer<DataTransformer.Transformer> consumer) {
      consumer.accept(TRANSFORMERS::add);
   }

   public static boolean shouldCheckNamespace() {
      return !TRANSFORMERS.isEmpty();
   }

   public static ResourceLocation applyTransformsIfPossible(String namespace, String path) {
      if (!TRANSFORMERS.isEmpty() && !REENTRANT_GUARD.get()) {
         REENTRANT_GUARD.set(true);

         try {
            ResourceLocation original = ResourceLocation.fromNamespaceAndPath(namespace, path);

            for (Function<ResourceLocation, ResourceLocation> transformer : TRANSFORMERS) {
               ResourceLocation result = transformer.apply(original);
               if (result != null) {
                  return result;
               }
            }

            return null;
         } finally {
            REENTRANT_GUARD.set(false);
         }
      } else {
         return null;
      }
   }

   public interface Transformer {
      void add(Function<ResourceLocation, ResourceLocation> var1);

      default void remap(ResourceLocation original, ResourceLocation remapped) {
         this.add(path -> path.equals(original) ? remapped : null);
      }
   }
}
