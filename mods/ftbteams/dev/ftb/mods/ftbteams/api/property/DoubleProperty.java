package dev.ftb.mods.ftbteams.api.property;

import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftblibrary.config.ConfigValue;
import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.NumericTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class DoubleProperty extends TeamProperty<Double> {
   public final double minValue;
   public final double maxValue;

   public DoubleProperty(ResourceLocation id, Supplier<Double> def, double min, double max) {
      super(id, def);
      this.minValue = min;
      this.maxValue = max;
   }

   public DoubleProperty(ResourceLocation id, double def, double min, double max) {
      this(id, () -> def, min, max);
   }

   public DoubleProperty(ResourceLocation id, Supplier<Double> def) {
      this(id, def, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
   }

   static DoubleProperty fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
      return new DoubleProperty(id, buf.readDouble(), buf.readDouble(), buf.readDouble());
   }

   @Override
   public TeamPropertyType<Double> getType() {
      return TeamPropertyType.DOUBLE;
   }

   @Override
   public Optional<Double> fromString(String string) {
      try {
         double num = Double.parseDouble(string);
         return Optional.of(Mth.clamp(num, this.minValue, this.maxValue));
      } catch (Exception ex) {
         return Optional.empty();
      }
   }

   @Override
   public void write(RegistryFriendlyByteBuf buf) {
      buf.writeDouble(this.getDefaultValue());
      buf.writeDouble(this.minValue);
      buf.writeDouble(this.maxValue);
   }

   @Override
   public ConfigValue<?> config(ConfigGroup config, TeamPropertyValue<Double> value) {
      return config.addDouble(this.id.getPath(), value.getValue(), value::setValue, this.getDefaultValue(), this.minValue, this.maxValue);
   }

   public Tag toNBT(Double value) {
      return DoubleTag.valueOf(value);
   }

   @Override
   public Optional<Double> fromNBT(Tag tag) {
      return tag instanceof NumericTag n ? Optional.of(Mth.clamp(n.getAsDouble(), this.minValue, this.maxValue)) : Optional.empty();
   }

   public Double readValue(RegistryFriendlyByteBuf buf) {
      return buf.readDouble();
   }

   public void writeValue(RegistryFriendlyByteBuf buf, Double value) {
      buf.writeDouble(value);
   }
}
