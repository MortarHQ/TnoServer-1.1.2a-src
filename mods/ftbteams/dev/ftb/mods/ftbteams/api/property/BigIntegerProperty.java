package dev.ftb.mods.ftbteams.api.property;

import dev.ftb.mods.ftblibrary.config.ConfigFromString;
import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftblibrary.config.ConfigValue;
import java.math.BigInteger;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class BigIntegerProperty extends TeamProperty<BigInteger> {
   public BigIntegerProperty(ResourceLocation id, Supplier<BigInteger> def) {
      super(id, def);
   }

   public BigIntegerProperty(ResourceLocation id, BigInteger def) {
      this(id, () -> def);
   }

   @Override
   public TeamPropertyType<BigInteger> getType() {
      return TeamPropertyType.BIG_INTEGER;
   }

   @Override
   public Optional<BigInteger> fromString(String string) {
      try {
         return Optional.of(new BigInteger(string));
      } catch (NumberFormatException e) {
         return Optional.empty();
      }
   }

   @Override
   public void write(RegistryFriendlyByteBuf buf) {
      buf.writeByteArray(this.getDefaultValue().toByteArray());
   }

   public static TeamProperty<BigInteger> fromNetwork(ResourceLocation id, RegistryFriendlyByteBuf buf) {
      return new BigIntegerProperty(id, new BigInteger(buf.readByteArray()));
   }

   public BigInteger readValue(RegistryFriendlyByteBuf buf) {
      return new BigInteger(buf.readByteArray());
   }

   public void writeValue(RegistryFriendlyByteBuf buf, BigInteger value) {
      buf.writeByteArray(value.toByteArray());
   }

   @Override
   public ConfigValue<?> config(ConfigGroup config, TeamPropertyValue<BigInteger> value) {
      return config.add(this.id.getPath(), new BigIntegerProperty.BigIntegerConfig(), value.getValue(), value::setValue, this.getDefaultValue());
   }

   private static class BigIntegerConfig extends ConfigFromString<BigInteger> {
      public boolean parse(@Nullable Consumer<BigInteger> consumer, String s) {
         try {
            BigInteger b = new BigInteger(s);
            return this.okValue(consumer, b);
         } catch (NumberFormatException var4) {
            return false;
         }
      }
   }
}
