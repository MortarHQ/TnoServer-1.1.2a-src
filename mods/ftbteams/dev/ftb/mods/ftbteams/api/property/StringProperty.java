package dev.ftb.mods.ftbteams.api.property;

import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftblibrary.config.ConfigValue;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class StringProperty extends TeamProperty<String> {
   private final Pattern pattern;

   public StringProperty(ResourceLocation id, Supplier<String> def, @Nullable Pattern pattern) {
      super(id, def);
      this.pattern = pattern;
   }

   public StringProperty(ResourceLocation id, Supplier<String> def) {
      this(id, def, null);
   }

   public StringProperty(ResourceLocation id, String def, @Nullable Pattern pattern) {
      this(id, () -> def, pattern);
   }

   public StringProperty(ResourceLocation id, String def) {
      this(id, () -> def);
   }

   static StringProperty fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
      String def = buf.readUtf(32767);
      int flags = buf.readVarInt();
      String patVal = buf.readUtf(32767);
      return new StringProperty(id, def, patVal.isEmpty() ? null : Pattern.compile(patVal, flags));
   }

   @Override
   public TeamPropertyType<String> getType() {
      return TeamPropertyType.STRING;
   }

   @Override
   public Optional<String> fromString(String string) {
      return this.pattern != null && !this.pattern.matcher(string).matches() ? Optional.empty() : Optional.of(string);
   }

   @Override
   public void write(RegistryFriendlyByteBuf buf) {
      buf.writeUtf(this.getDefaultValue(), 32767);
      buf.writeVarInt(this.pattern == null ? 0 : this.pattern.flags());
      buf.writeUtf(this.pattern == null ? "" : this.pattern.pattern(), 32767);
   }

   @Override
   public ConfigValue<?> config(ConfigGroup config, TeamPropertyValue<String> value) {
      return config.addString(this.id.getPath(), value.getValue(), value::setValue, this.getDefaultValue(), this.pattern);
   }
}
