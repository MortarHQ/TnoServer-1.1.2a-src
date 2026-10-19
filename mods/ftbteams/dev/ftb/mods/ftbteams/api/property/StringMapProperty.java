package dev.ftb.mods.ftbteams.api.property;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class StringMapProperty<T> extends TeamProperty<Map<String, T>> {
   private final TeamPropertyType<Map<String, T>> propType;
   private final Function<String, T> fromString;
   private final BiConsumer<FriendlyByteBuf, T> toNet;
   private final Function<FriendlyByteBuf, T> fromNet;

   protected StringMapProperty(
      ResourceLocation id,
      Supplier<Map<String, T>> defaultValue,
      TeamPropertyType<Map<String, T>> propType,
      Function<String, T> fromString,
      BiConsumer<FriendlyByteBuf, T> toNet,
      Function<FriendlyByteBuf, T> fromNet
   ) {
      super(id, defaultValue);
      this.propType = propType;
      this.fromString = fromString;
      this.toNet = toNet;
      this.fromNet = fromNet;
   }

   @Override
   public TeamPropertyType<Map<String, T>> getType() {
      return this.propType;
   }

   @Override
   public Optional<Map<String, T>> fromString(String string) {
      try {
         Map<String, T> res = new HashMap<>();
         Splitter.on(",").withKeyValueSeparator("=").split(string).forEach((k, v) -> res.put(k, this.fromString.apply(v)));
         return Optional.of(res);
      } catch (IllegalArgumentException e) {
         return Optional.empty();
      }
   }

   public String toString(Map<String, T> value) {
      return Joiner.on(",").withKeyValueSeparator("=").join(value);
   }

   @Override
   public void write(RegistryFriendlyByteBuf buf) {
      buf.writeMap(this.getDefaultValue(), FriendlyByteBuf::writeUtf, this.toNet::accept);
   }

   public Tag toNBT(Map<String, T> value) {
      CompoundTag res = new CompoundTag();
      value.forEach((k, v) -> res.putString(k, v.toString()));
      return res;
   }

   @Override
   public Optional<Map<String, T>> fromNBT(Tag tag) {
      if (tag instanceof CompoundTag c) {
         Map<String, T> res = new HashMap<>();
         c.getAllKeys().forEach(k -> res.put(k, this.fromString.apply(c.getString(k))));
         return Optional.of(res);
      } else {
         return Optional.empty();
      }
   }

   public Map<String, T> readValue(RegistryFriendlyByteBuf buf) {
      return buf.readMap(FriendlyByteBuf::readUtf, this.fromNet::apply);
   }

   public void writeValue(RegistryFriendlyByteBuf buf, Map<String, T> value) {
      buf.writeMap(value, FriendlyByteBuf::writeUtf, this.toNet::accept);
   }

   protected static <T> Map<String, T> mapFromNetwork(FriendlyByteBuf buf, Function<FriendlyByteBuf, T> fromNet) {
      return buf.readMap(FriendlyByteBuf::readUtf, fromNet::apply);
   }

   public static class ToBoolean extends StringMapProperty<Boolean> {
      public ToBoolean(ResourceLocation id, Map<String, Boolean> defaultValue) {
         super(id, () -> defaultValue, TeamPropertyType.BOOL_MAP, Boolean::parseBoolean, FriendlyByteBuf::writeBoolean, FriendlyByteBuf::readBoolean);
      }

      static StringMapProperty.ToBoolean fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
         return new StringMapProperty.ToBoolean(id, mapFromNetwork(buf, FriendlyByteBuf::readBoolean));
      }
   }

   public static class ToInteger extends StringMapProperty<Integer> {
      public ToInteger(ResourceLocation id, Map<String, Integer> defaultValue) {
         super(id, () -> defaultValue, TeamPropertyType.INT_MAP, Integer::parseInt, FriendlyByteBuf::writeVarInt, FriendlyByteBuf::readVarInt);
      }

      static StringMapProperty.ToInteger fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
         return new StringMapProperty.ToInteger(id, mapFromNetwork(buf, FriendlyByteBuf::readVarInt));
      }
   }

   public static class ToString extends StringMapProperty<String> {
      public ToString(ResourceLocation id, Map<String, String> defaultValue) {
         super(id, () -> defaultValue, TeamPropertyType.STRING_MAP, Function.identity(), FriendlyByteBuf::writeUtf, FriendlyByteBuf::readUtf);
      }

      static StringMapProperty.ToString fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
         return new StringMapProperty.ToString(id, mapFromNetwork(buf, FriendlyByteBuf::readUtf));
      }
   }
}
