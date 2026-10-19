package dev.ftb.mods.ftbteams.api.property;

import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftblibrary.config.ConfigValue;
import dev.ftb.mods.ftblibrary.config.StringConfig;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class StringSetProperty extends TeamProperty<Set<String>> {
   public StringSetProperty(ResourceLocation id, Supplier<Set<String>> def) {
      super(id, def);
   }

   public StringSetProperty(ResourceLocation id, Set<String> def) {
      this(id, () -> def);
   }

   static StringSetProperty fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
      return new StringSetProperty(id, new HashSet<>(buf.readList(b -> b.readUtf(32767))));
   }

   @Override
   public TeamPropertyType<Set<String>> getType() {
      return TeamPropertyType.STRING_SET;
   }

   @Override
   public Optional<Set<String>> fromString(String string) {
      return string.length() > 2 && string.startsWith("[") && string.endsWith("]")
         ? Optional.of(new HashSet<>(Arrays.asList(string.substring(1, string.length() - 1).split("\t"))))
         : Optional.empty();
   }

   @Override
   public void write(RegistryFriendlyByteBuf buf) {
      buf.writeCollection(this.getDefaultValue(), FriendlyByteBuf::writeUtf);
   }

   public String toString(Set<String> value) {
      return "[" + String.join("\t", value) + "]";
   }

   @Override
   public ConfigValue<?> config(ConfigGroup config, TeamPropertyValue<Set<String>> value) {
      return config.addList(this.id.getPath(), new ArrayList<>(value.getValue()), new StringConfig(), "");
   }

   public Tag toNBT(Set<String> value) {
      ListTag res = new ListTag();
      value.forEach(s -> res.add(StringTag.valueOf(s)));
      return res;
   }

   @Override
   public Optional<Set<String>> fromNBT(Tag tag) {
      return tag instanceof ListTag l ? Optional.of(l.stream().<String>map(Tag::getAsString).collect(Collectors.toSet())) : Optional.empty();
   }

   public void writeValue(RegistryFriendlyByteBuf buf, Set<String> value) {
      buf.writeCollection(value, FriendlyByteBuf::writeUtf);
   }

   public Set<String> readValue(RegistryFriendlyByteBuf buf) {
      return (Set<String>)buf.readCollection(HashSet::new, FriendlyByteBuf::readUtf);
   }
}
