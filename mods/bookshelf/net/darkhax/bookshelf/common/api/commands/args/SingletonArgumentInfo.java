package net.darkhax.bookshelf.common.api.commands.args;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.ArgumentType;
import java.util.function.Supplier;
import net.darkhax.bookshelf.common.api.function.CachedSupplier;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;

public final class SingletonArgumentInfo<T extends ArgumentType<?>> implements ArgumentTypeInfo<T, SingletonArgumentInfo.Template<T>> {
   private final CachedSupplier<SingletonArgumentInfo.Template<T>> templateSupplier;

   public static <T extends ArgumentType<?>> SingletonArgumentInfo<T> of(Supplier<T> argSupplier) {
      return new SingletonArgumentInfo<>(argSupplier);
   }

   private SingletonArgumentInfo(Supplier<T> singletonSupplier) {
      this.templateSupplier = CachedSupplier.cache(() -> new SingletonArgumentInfo.Template<>(singletonSupplier, this));
   }

   public void serializeToNetwork(@NotNull SingletonArgumentInfo.Template<T> tTemplate, @NotNull FriendlyByteBuf friendlyByteBuf) {
   }

   @NotNull
   public SingletonArgumentInfo.Template<T> deserializeFromNetwork(@NotNull FriendlyByteBuf buffer) {
      return this.templateSupplier.get();
   }

   public void serializeToJson(@NotNull SingletonArgumentInfo.Template<T> tTemplate, @NotNull JsonObject jsonObject) {
   }

   @NotNull
   public SingletonArgumentInfo.Template<T> unpack(@NotNull T template) {
      return this.templateSupplier.get();
   }

   public static class Template<T extends ArgumentType<?>> implements net.minecraft.commands.synchronization.ArgumentTypeInfo.Template<T> {
      private final ArgumentTypeInfo<T, ?> info;
      private final Supplier<T> singletonSupplier;

      protected Template(Supplier<T> supplier, ArgumentTypeInfo<T, ?> info) {
         this.singletonSupplier = supplier;
         this.info = info;
      }

      @NotNull
      public T instantiate(@NotNull CommandBuildContext ctx) {
         return this.singletonSupplier.get();
      }

      @NotNull
      public ArgumentTypeInfo<T, ?> type() {
         return this.info;
      }
   }
}
