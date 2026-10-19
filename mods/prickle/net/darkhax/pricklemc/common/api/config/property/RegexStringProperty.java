package net.darkhax.pricklemc.common.api.config.property;

import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.regex.Pattern;
import net.darkhax.pricklemc.common.api.annotations.Regex;
import net.darkhax.pricklemc.common.api.annotations.Value;
import net.darkhax.pricklemc.common.api.config.PropertyResolver;
import net.darkhax.pricklemc.common.api.config.comment.IComment;
import org.slf4j.Logger;

public class RegexStringProperty extends ObjectProperty<String> {
   public static IPropertyAdapter<RegexStringProperty> ADAPTER = new RegexStringProperty.Adapter();
   private final String regex;
   private final Pattern pattern;

   private RegexStringProperty(Field field, Object parent, String value, String regex, Value valueMeta, IComment comment) {
      super(field, parent, value, valueMeta, comment);
      this.regex = regex;
      this.pattern = Pattern.compile(this.regex);
   }

   @Override
   public void writeAdditionalComments(JsonWriter writer, PropertyResolver resolver, Logger log) throws IOException {
      if (this.regex != null) {
         writer.name("//regex");
         writer.value(this.regex);
      }
   }

   public boolean validate(String value) throws IllegalArgumentException {
      return value != null && this.pattern.matcher(value).matches();
   }

   private static class Adapter implements IPropertyAdapter<RegexStringProperty> {
      public RegexStringProperty toValue(PropertyResolver resolver, Field field, Object parent, Object value, Value valueMeta) throws IOException {
         if (value instanceof String stringVal) {
            Regex regex = field.getAnnotation(Regex.class);
            if (regex != null) {
               return new RegexStringProperty(field, parent, stringVal, regex.value(), valueMeta, resolver.toComment(field, value, valueMeta));
            }
         }

         return null;
      }
   }
}
