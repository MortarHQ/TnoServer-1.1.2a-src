package com.nododiiiii.ponderer.compat.jei;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import javax.annotation.Nullable;
import mezz.jei.api.ingredients.IIngredientHelper;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.ingredients.ITypedIngredient;
import mezz.jei.api.runtime.IIngredientManager;
import mezz.jei.api.runtime.IJeiRuntime;
import net.createmod.catnip.gui.element.ScreenElement;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;

final class JeiIngredientHelper {
   private JeiIngredientHelper() {
   }

   @Nullable
   static ScreenElement createScreenElement(Object ingredient) {
      IJeiRuntime rt = PondererJeiPlugin.getRuntime();
      if (rt == null) {
         return null;
      }

      IIngredientManager mgr = rt.getIngredientManager();
      Optional<? extends ITypedIngredient<?>> typed = createTyped(mgr, ingredient);
      return typed.map(JeiIngredientScreenElement::of).orElse(null);
   }

   @Nullable
   static ScreenElement createFluidScreenElement(Fluid fluid, int amount) {
      IJeiRuntime rt = PondererJeiPlugin.getRuntime();
      if (rt == null) {
         return null;
      }

      IIngredientManager mgr = rt.getIngredientManager();
      ResourceLocation fluidId = BuiltInRegistries.FLUID.getKey(fluid);
      return fluidId == null ? null : resolveById(fluidId.toString());
   }

   @Nullable
   static ScreenElement resolveById(String id) {
      return resolveById(id, null);
   }

   @Nullable
   static ScreenElement resolveById(String id, @Nullable String preferredKind) {
      IJeiRuntime rt = PondererJeiPlugin.getRuntime();
      if (rt == null) {
         return null;
      }

      IIngredientManager mgr = rt.getIngredientManager();
      ResourceLocation target = ResourceLocation.tryParse(id);
      if (target == null) {
         return null;
      }

      if (preferredKind != null && !preferredKind.isBlank()) {
         String normalizedKind = preferredKind.toLowerCase(Locale.ROOT);

         for (IIngredientType<?> type : mgr.getRegisteredIngredientTypes()) {
            if (normalizedKind.equals(inferKind(type))) {
               ScreenElement result = searchType(mgr, type, target);
               if (result != null) {
                  return result;
               }
            }
         }
      }

      for (IIngredientType<?> type : mgr.getRegisteredIngredientTypes()) {
         ScreenElement result = searchType(mgr, type, target);
         if (result != null) {
            return result;
         }
      }

      return null;
   }

   @Nullable
   static String resolveId(Object typedIngredientObj) {
      if (typedIngredientObj instanceof ITypedIngredient<?> typed) {
         IJeiRuntime rt = PondererJeiPlugin.getRuntime();
         if (rt == null) {
            return null;
         }

         IIngredientManager mgr = rt.getIngredientManager();
         return resolveIdTyped(mgr, typed);
      } else {
         return null;
      }
   }

   @Nullable
   static JeiCompat.IngredientDescriptor resolveDescriptor(Object typedIngredientObj) {
      if (typedIngredientObj instanceof ITypedIngredient<?> typed) {
         IJeiRuntime rt = PondererJeiPlugin.getRuntime();
         if (rt == null) {
            return null;
         }

         IIngredientManager mgr = rt.getIngredientManager();
         ITypedIngredient<Object> cast = (ITypedIngredient<Object>)typed;
         String id = resolveIdTyped(mgr, cast);
         if (id == null) {
            return null;
         }

         String kind = inferKind(cast.getType());
         return new JeiCompat.IngredientDescriptor(id, kind);
      } else {
         return null;
      }
   }

   private static <T> Optional<ITypedIngredient<T>> createTyped(IIngredientManager mgr, Object ingredient) {
      Optional<IIngredientType<T>> typeOpt = mgr.getIngredientTypeChecked(ingredient);
      return typeOpt.flatMap(type -> mgr.createTypedIngredient(type, ingredient));
   }

   private static <T> ScreenElement searchType(IIngredientManager mgr, IIngredientType<T> type, ResourceLocation target) {
      IIngredientHelper<T> helper = mgr.getIngredientHelper(type);

      for (T ingredient : mgr.getAllIngredients(type)) {
         ResourceLocation loc = helper.getResourceLocation(ingredient);
         if (target.equals(loc)) {
            Optional<ITypedIngredient<T>> typed = mgr.createTypedIngredient(type, ingredient);
            if (typed.isPresent()) {
               return JeiIngredientScreenElement.of(typed.get());
            }
         }
      }

      return null;
   }

   private static <T> String resolveIdTyped(IIngredientManager mgr, ITypedIngredient<T> typed) {
      IIngredientHelper<T> helper = mgr.getIngredientHelper(typed.getType());
      ResourceLocation loc = helper.getResourceLocation(typed.getIngredient());
      return loc != null ? loc.toString() : null;
   }

   static List<String[]> getAllExtraEntries() {
      IJeiRuntime rt = PondererJeiPlugin.getRuntime();
      if (rt == null) {
         return List.of();
      }

      IIngredientManager mgr = rt.getIngredientManager();
      List<String[]> result = new ArrayList<>();

      for (IIngredientType<?> type : mgr.getRegisteredIngredientTypes()) {
         collectEntriesForType(mgr, type, result);
      }

      return result;
   }

   private static String inferKind(IIngredientType<?> type) {
      String className = type.getIngredientClass().getSimpleName();
      if (className.contains("ItemStack")) {
         return "item";
      } else {
         return className.contains("FluidStack") ? "fluid" : className.toLowerCase(Locale.ROOT).replace("stack", "");
      }
   }

   private static <T> void collectEntriesForType(IIngredientManager mgr, IIngredientType<T> type, List<String[]> result) {
      IIngredientHelper<T> helper = mgr.getIngredientHelper(type);
      String kind = inferKind(type);

      for (T ingredient : mgr.getAllIngredients(type)) {
         ResourceLocation loc = helper.getResourceLocation(ingredient);
         if (loc != null) {
            String id = loc.toString();
            String displayName = helper.getDisplayName(ingredient).toLowerCase(Locale.ROOT);
            String path = loc.getPath().toLowerCase(Locale.ROOT);
            result.add(new String[]{id, displayName, path, kind});
         }
      }
   }
}
