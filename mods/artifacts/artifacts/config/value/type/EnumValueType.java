package artifacts.config.value.type;

import artifacts.config.screen.ConfigEntries;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;

public class EnumValueType<T extends Enum<T> & StringRepresentable> extends ValueType<T, String> {
   private final Function<T, Component> toText;
   private final Class<T> enumClass;

   public EnumValueType(Class<T> enumClass, Function<T, Component> toText) {
      this.enumClass = enumClass;
      this.toText = toText;
   }

   public List<T> getValues() {
      return Arrays.asList(this.enumClass.getEnumConstants());
   }

   public boolean isCorrect(T value) {
      return true;
   }

   public String makeError(T value) {
      return "";
   }

   @Override
   public String getAllowedValuesComment() {
      StringBuilder builder = new StringBuilder("Allowed Values: ");
      builder.append(((StringRepresentable)((Enum[])this.enumClass.getEnumConstants())[0]).getSerializedName());

      for (int i = 1; i < ((Enum[])this.enumClass.getEnumConstants()).length; i++) {
         builder.append(", ").append(((StringRepresentable)((Enum[])this.enumClass.getEnumConstants())[i]).getSerializedName());
      }

      return builder.toString();
   }

   public T read(String configValue) {
      for (T value : (Enum[])this.enumClass.getEnumConstants()) {
         if (value.getSerializedName().equalsIgnoreCase(configValue)) {
            return value;
         }
      }

      throw new IllegalArgumentException("Unknown value '%s'".formatted(configValue));
   }

   public String write(T c) {
      return c.getSerializedName();
   }

   @Override
   protected Codec<T> valueCodec() {
      return StringRepresentable.fromValues(this.enumClass::getEnumConstants);
   }

   @Override
   public StreamCodec<ByteBuf, T> valueStreamCodec() {
      return ByteBufCodecs.idMapper(
         i -> ((Enum[])this.enumClass.getEnumConstants())[i], Util.createIndexLookup(Arrays.asList(this.enumClass.getEnumConstants()))
      );
   }

   public Component getAsComponent(T value) {
      return this.toText.apply(value);
   }

   @Override
   public ConfigEntries.ConfigEntryFactory<T> getConfigEntryFactory() {
      return ConfigEntries.enumConfigEntryFactory(this);
   }
}
