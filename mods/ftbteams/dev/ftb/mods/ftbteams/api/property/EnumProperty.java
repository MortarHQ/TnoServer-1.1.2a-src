package dev.ftb.mods.ftbteams.api.property;

import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftblibrary.config.ConfigValue;
import dev.ftb.mods.ftblibrary.config.NameMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.resources.ResourceLocation;

public class EnumProperty extends TeamProperty<String> {
   private final List<String> values;
   private final Map<String, Component> names;

   public EnumProperty(ResourceLocation id, Supplier<String> def, List<String> values, Map<String, Component> names) {
      super(id, def);
      this.values = values;
      this.names = names;
   }

   public <T> EnumProperty(ResourceLocation id, NameMap<T> nameMap) {
      this(id, () -> nameMap.getName(nameMap.defaultValue), nameMap.keys, buildMap(nameMap));
   }

   private static <T> Map<String, Component> buildMap(NameMap<T> nameMap) {
      Map<String, Component> res = new HashMap<>();
      nameMap.forEach(val -> res.put(nameMap.getName(val), nameMap.getDisplayName(val)));
      return res;
   }

   static EnumProperty fromNetwork(ResourceLocation id, RegistryFriendlyByteBuf buf) {
      String def = buf.readUtf(32767);
      List<String> values = buf.readList(b -> b.readUtf(32767));
      int len = buf.readVarInt();
      Map<String, Component> names = new HashMap<>();

      for (int i = 0; i < len; i++) {
         names.put(buf.readUtf(), (Component)ComponentSerialization.STREAM_CODEC.decode(buf));
      }

      return new EnumProperty(id, () -> def, values, names);
   }

   @Override
   public TeamPropertyType<String> getType() {
      return TeamPropertyType.ENUM;
   }

   @Override
   public Optional<String> fromString(String string) {
      return Optional.of(string);
   }

   @Override
   public void write(RegistryFriendlyByteBuf buf) {
      buf.writeUtf(this.getDefaultValue(), 32767);
      buf.writeCollection(this.values, FriendlyByteBuf::writeUtf);
      buf.writeVarInt(this.names.size());
      this.names.forEach((k, v) -> {
         buf.writeUtf(k);
         ComponentSerialization.STREAM_CODEC.encode(buf, v);
      });
   }

   @Override
   public ConfigValue<?> config(ConfigGroup config, TeamPropertyValue<String> value) {
      return config.addEnum(
         this.id.getPath(),
         value.getValue(),
         value::setValue,
         NameMap.of(this.getDefaultValue(), this.values).name(s -> this.names.getOrDefault(s, Component.literal(s))).create()
      );
   }

   public Tag toNBT(String value) {
      return StringTag.valueOf(value);
   }

   @Override
   public Optional<String> fromNBT(Tag tag) {
      return tag instanceof StringTag ? Optional.of(tag.getAsString()) : Optional.empty();
   }
}
