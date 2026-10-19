package dev.ftb.mods.ftbteams.api.property;

import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftblibrary.config.ConfigValue;
import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.NumericTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class BooleanProperty extends TeamProperty<Boolean> {
   private static final Optional<Boolean> TRUE = Optional.of(Boolean.TRUE);
   private static final Optional<Boolean> FALSE = Optional.of(Boolean.FALSE);

   public BooleanProperty(ResourceLocation id, Supplier<Boolean> def) {
      super(id, def);
   }

   public BooleanProperty(ResourceLocation id, Boolean def) {
      this(id, () -> def);
   }

   static BooleanProperty fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
      return new BooleanProperty(id, buf.readBoolean());
   }

   @Override
   public TeamPropertyType<Boolean> getType() {
      return TeamPropertyType.BOOLEAN;
   }

   @Override
   public Optional<Boolean> fromString(String string) {
      if (string.equals("true")) {
         return TRUE;
      } else {
         return string.equals("false") ? FALSE : Optional.empty();
      }
   }

   @Override
   public void write(RegistryFriendlyByteBuf buf) {
      buf.writeBoolean(this.getDefaultValue());
   }

   @Override
   public ConfigValue<?> config(ConfigGroup config, TeamPropertyValue<Boolean> value) {
      return config.addBool(this.id.getPath(), value.getValue(), value::setValue, this.getDefaultValue());
   }

   public Tag toNBT(Boolean value) {
      return ByteTag.valueOf(value);
   }

   @Override
   public Optional<Boolean> fromNBT(Tag tag) {
      return tag instanceof NumericTag n ? (n.getAsByte() == 1 ? TRUE : FALSE) : Optional.empty();
   }

   public void writeValue(RegistryFriendlyByteBuf buf, Boolean value) {
      buf.writeBoolean(value);
   }

   public Boolean readValue(RegistryFriendlyByteBuf buf) {
      return buf.readBoolean();
   }
}
