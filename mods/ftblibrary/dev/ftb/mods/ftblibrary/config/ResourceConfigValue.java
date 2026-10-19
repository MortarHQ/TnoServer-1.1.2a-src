package dev.ftb.mods.ftblibrary.config;

import dev.ftb.mods.ftblibrary.FTBLibrary;
import dev.ftb.mods.ftblibrary.config.ui.resource.SelectableResource;
import java.util.OptionalLong;
import java.util.function.Predicate;
import net.minecraft.resources.ResourceLocation;

public abstract class ResourceConfigValue<T> extends ConfigValue<T> {
   private boolean allowNBTEdit = true;
   private boolean allowEmpty = true;
   private Predicate<T> filter = s -> true;

   public boolean allowEmptyResource() {
      return this.allowEmpty;
   }

   public abstract OptionalLong fixedResourceSize();

   public abstract boolean isEmpty();

   public abstract SelectableResource<T> getResource();

   public abstract boolean setResource(SelectableResource<T> var1);

   public boolean canHaveNBT() {
      return this.allowNBTEdit;
   }

   public ResourceConfigValue<T> setAllowNBTEdit(boolean allow) {
      this.allowNBTEdit = allow;
      return this;
   }

   public ResourceConfigValue<T> withFilter(Predicate<T> filter) {
      this.filter = filter;
      return this;
   }

   public ResourceConfigValue<T> withAllowEmpty(boolean allowEmpty) {
      this.allowEmpty = allowEmpty;
      return this;
   }

   public boolean allowResource(T resource) {
      return this.filter.test(resource);
   }

   public abstract static class Image<T> extends ResourceConfigValue<T> {
      public static final ResourceLocation NONE = FTBLibrary.rl("none");

      @Override
      public boolean canHaveNBT() {
         return false;
      }

      @Override
      public OptionalLong fixedResourceSize() {
         return OptionalLong.of(1L);
      }

      @Override
      public boolean isEmpty() {
         return this.value == null || this.value.equals(NONE);
      }
   }
}
