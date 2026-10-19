package net.darkhax.bookshelf.common.api.function;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import net.darkhax.bookshelf.common.impl.Constants;
import net.darkhax.bookshelf.common.mixin.access.level.AccessorRecipeManager;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class ReloadableCache<T> implements Function<Level, T> {
   public static final ReloadableCache EMPTY = of(level -> null);
   private final Function<Level, T> delegate;
   private boolean cached = false;
   private int revision = 0;
   @Nullable
   private T cachedValue;

   protected ReloadableCache(Function<Level, T> delegate) {
      this.delegate = delegate;
   }

   @Nullable
   public T apply(Level level) {
      if (!this.isCached() || this.revision != (level.isClientSide ? Constants.CLIENT_REVISION : Constants.SERVER_REVISION)) {
         this.cachedValue = this.delegate.apply(level);
         this.revision = level.isClientSide ? Constants.CLIENT_REVISION : Constants.SERVER_REVISION;
         this.cached = true;
      }

      return this.cachedValue;
   }

   public void invalidate() {
      this.cached = false;
      this.cachedValue = null;
      this.revision = -1;
   }

   public boolean isCached() {
      return this.cached;
   }

   public void apply(Level level, Consumer<T> consumer) {
      consumer.accept(this.apply(level));
   }

   public void ifPresent(Level level, Consumer<T> consumer) {
      T value = this.apply(level);
      if (value != null) {
         consumer.accept(value);
      }
   }

   @Nullable
   public <R> R map(Level level, Function<T, R> mapper) {
      T value = this.apply(level);
      return value != null ? mapper.apply(value) : null;
   }

   public static <T> ReloadableCache<T> of(Supplier<T> supplier) {
      return new ReloadableCache<>(level -> supplier.get());
   }

   public static <T> ReloadableCache<T> of(Function<Level, T> delegate) {
      return new ReloadableCache<>(delegate);
   }

   public static <T> ReloadableCache<T> of(ResourceKey<? extends Registry<T>> registry, ResourceLocation id) {
      return of(level -> (T)level.registryAccess().registryOrThrow(registry).get(id));
   }

   public static <T extends Recipe<?>> ReloadableCache<Map<ResourceLocation, RecipeHolder<T>>> of(RecipeType<T> type) {
      return of(level -> {
         Map<ResourceLocation, RecipeHolder<T>> byId = new HashMap<>();
         if (level.getRecipeManager() instanceof AccessorRecipeManager accessor) {
            Collection<RecipeHolder<?>> recipes = accessor.bookshelf$byTypeMap().get(type);
            recipes.forEach(entry -> byId.put(entry.id(), (RecipeHolder<T>)entry));
         }

         return byId;
      });
   }

   public static <T extends Recipe<?>> ReloadableCache<Map<ResourceLocation, RecipeHolder<T>>> recipes(Supplier<RecipeType<T>> type) {
      return of(level -> {
         Map<ResourceLocation, RecipeHolder<T>> byId = new HashMap<>();
         if (level.getRecipeManager() instanceof AccessorRecipeManager accessor) {
            Collection<RecipeHolder<?>> recipes = accessor.bookshelf$byTypeMap().get(type.get());
            recipes.forEach(entry -> byId.put(entry.id(), (RecipeHolder<T>)entry));
         }

         return byId;
      });
   }

   public static ReloadableCache<Entity> entity(CompoundTag entityData) {
      if (entityData != null && entityData.contains("id", 8)) {
         return of(level -> {
            try {
               return EntityType.loadEntityRecursive(entityData, level, Function.identity());
            } catch (Exception e) {
               throw new IllegalStateException("Encountered an error while constructing the target entity.", e);
            }
         });
      } else {
         throw new IllegalStateException("The provided entity data does not contain an entity ID! data=" + entityData);
      }
   }

   public static ReloadableCache<LivingEntity> living(CompoundTag entityData) {
      ReloadableCache<Entity> entityCache = entity(entityData);
      return of(level -> {
         if (entityCache.apply(level) instanceof LivingEntity living) {
            return living;
         } else {
            throw new IllegalStateException("Constructed entity was not a LivingEntity type. data=" + entityData);
         }
      });
   }
}
