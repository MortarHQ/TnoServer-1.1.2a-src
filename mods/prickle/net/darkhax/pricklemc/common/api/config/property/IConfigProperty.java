package net.darkhax.pricklemc.common.api.config.property;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import net.darkhax.pricklemc.common.api.config.PropertyResolver;
import org.slf4j.Logger;

public interface IConfigProperty<T> {
   T value();

   void read(JsonReader var1, PropertyResolver var2, Logger var3) throws IOException;

   void write(JsonWriter var1, PropertyResolver var2, Logger var3) throws IOException;

   boolean validate(T var1) throws IllegalArgumentException;
}
