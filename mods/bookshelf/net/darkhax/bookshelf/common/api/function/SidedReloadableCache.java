package net.darkhax.bookshelf.common.api.function;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import net.darkhax.bookshelf.common.mixin.access.level.AccessorRecipeManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class SidedReloadableCache<T> implements Function<Level, T> {
   private final ReloadableCache<T> client;
   private final ReloadableCache<T> server;

   public SidedReloadableCache(ReloadableCache<T> client, ReloadableCache<T> server) {
      this.client = client;
      this.server = server;
   }

   public ReloadableCache<T> getCache(Level level) {
      return level.isClientSide ? this.client : this.server;
   }

   @Nullable
   public T apply(Level level) {
      return this.getCache(level).apply(level);
   }

   public void invalidate(Level level) {
      this.getCache(level).invalidate();
   }

   public boolean isCached(Level level) {
      return this.getCache(level).isCached();
   }

   public void apply(Level level, Consumer<T> consumer) {
      this.getCache(level).apply(level, consumer);
   }

   public void ifPresent(Level level, Consumer<T> consumer) {
      this.getCache(level).ifPresent(level, consumer);
   }

   @Nullable
   public <R> R map(Level level, Function<T, R> mapper) {
      return this.getCache(level).map(level, mapper);
   }

   public static <T> SidedReloadableCache<T> of(Function<Level, T> cacheFunc) {
      return new SidedReloadableCache<>(new ReloadableCache<>(cacheFunc), new ReloadableCache<>(cacheFunc));
   }

   public static <T extends Recipe<?>> SidedReloadableCache<Map<ResourceLocation, RecipeHolder<T>>> recipes(Supplier<RecipeType<T>> type) {
      return of(level -> {
         Map<ResourceLocation, RecipeHolder<T>> byId = new HashMap<>();
         if (level.getRecipeManager() instanceof AccessorRecipeManager accessor) {
            Collection<RecipeHolder<?>> recipes = accessor.bookshelf$byTypeMap().get(type.get());
            recipes.forEach(entry -> byId.put(entry.id(), (RecipeHolder<T>)entry));
         }

         return byId;
      });
   }
}
