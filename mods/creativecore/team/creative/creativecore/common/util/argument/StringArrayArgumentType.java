package team.creative.creativecore.common.util.argument;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class StringArrayArgumentType implements ArgumentType<String[]> {
   public static final List<String> EXAMPLES = Arrays.asList("name1", "name1,name2", "name1,name2,name3");

   public static String[] getStringArray(CommandContext<?> context, String name) {
      return (String[])context.getArgument(name, String[].class);
   }

   public static StringArrayArgumentType stringArray() {
      return new StringArrayArgumentType();
   }

   public String[] parse(StringReader reader) throws CommandSyntaxException {
      return reader.readString().split(",");
   }

   public Collection<String> getExamples() {
      return EXAMPLES;
   }
}
