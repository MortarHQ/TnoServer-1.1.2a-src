package net.darkhax.bookshelf.common.api.commands.args;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.MapCodec;
import java.util.function.BiFunction;
import java.util.function.Function;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfo.Template;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

public class ArgumentSerializer<T extends ArgumentType<?>, V> implements ArgumentTypeInfo<T, ArgumentSerializer.ArgTemplate<T, V>> {
   private final MapCodec<V> codec;
   private final StreamCodec<FriendlyByteBuf, V> stream;
   private final BiFunction<CommandBuildContext, V, T> fromData;
   private final Function<T, V> toData;

   public ArgumentSerializer(MapCodec<V> codec, StreamCodec<FriendlyByteBuf, V> stream, BiFunction<CommandBuildContext, V, T> mapFunc, Function<T, V> toData) {
      this.codec = codec;
      this.stream = stream;
      this.fromData = mapFunc;
      this.toData = toData;
   }

   public void serializeToNetwork(ArgumentSerializer.ArgTemplate<T, V> template, @NotNull FriendlyByteBuf buf) {
      this.stream.encode(buf, template.data);
   }

   @NotNull
   public ArgumentSerializer.ArgTemplate<T, V> deserializeFromNetwork(@NotNull FriendlyByteBuf buf) {
      return new ArgumentSerializer.ArgTemplate<>(this, (V)this.stream.decode(buf));
   }

   public void serializeToJson(@NotNull ArgumentSerializer.ArgTemplate<T, V> template, @NotNull JsonObject json) {
      json.add("value", (JsonElement)this.codec.codec().encodeStart(JsonOps.INSTANCE, template.data).getOrThrow());
   }

   @NotNull
   public ArgumentSerializer.ArgTemplate<T, V> unpack(@NotNull T t) {
      return new ArgumentSerializer.ArgTemplate<>(this, this.toData.apply(t));
   }

   public static class ArgTemplate<T extends ArgumentType<?>, V> implements Template<T> {
      private final ArgumentSerializer<T, V> type;
      private final V data;

      protected ArgTemplate(ArgumentSerializer<T, V> type, V data) {
         this.type = type;
         this.data = data;
      }

      @NotNull
      public T instantiate(@NotNull CommandBuildContext ctx) {
         return this.type.fromData.apply(ctx, this.data);
      }

      @NotNull
      public ArgumentTypeInfo<T, ?> type() {
         return this.type;
      }
   }
}
