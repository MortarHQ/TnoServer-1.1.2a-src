package me.wesley1808.servercore.common.config.serialization;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.DataResult.Error;
import java.util.Optional;
import net.minecraft.network.chat.TextColor;
import space.arim.dazzleconf.error.BadValueException;
import space.arim.dazzleconf.serialiser.Decomposer;
import space.arim.dazzleconf.serialiser.FlexibleType;
import space.arim.dazzleconf.serialiser.ValueSerialiser;

public class TextColorSerializer implements ValueSerialiser<TextColor> {
   public Class<TextColor> getTargetClass() {
      return TextColor.class;
   }

   public TextColor deserialise(FlexibleType flexibleType) throws BadValueException {
      String color = flexibleType.getString();
      DataResult<TextColor> result = TextColor.parseColor(color);
      Optional<Error<TextColor>> error = result.error();
      if (error.isPresent()) {
         throw flexibleType.badValueExceptionBuilder().message(error.get().message()).build();
      } else {
         Optional<TextColor> value = result.result();
         if (value.isEmpty()) {
            throw flexibleType.badValueExceptionBuilder().message("Invalid color: " + color).build();
         } else {
            return value.get();
         }
      }
   }

   public Object serialise(TextColor value, Decomposer decomposer) {
      return value.serialize();
   }
}
