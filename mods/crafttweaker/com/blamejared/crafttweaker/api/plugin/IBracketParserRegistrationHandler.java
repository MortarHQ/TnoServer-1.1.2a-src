package com.blamejared.crafttweaker.api.plugin;

import java.lang.reflect.Method;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zenscript.parser.BracketExpressionParser;

public interface IBracketParserRegistrationHandler {
   void registerParserFor(String var1, String var2, BracketExpressionParser var3, IBracketParserRegistrationHandler.DumperData var4);

   default void registerParserFor(String loader, String parserName, BracketExpressionParser parser) {
      this.registerParserFor(loader, parserName, parser, null);
   }

   void registerParserFor(String var1, String var2, Method var3, Method var4, IBracketParserRegistrationHandler.DumperData var5);

   default void registerParserFor(String loader, String parserName, Method parser, IBracketParserRegistrationHandler.DumperData dumper) {
      this.registerParserFor(loader, parserName, parser, null, dumper);
   }

   default void registerParserFor(String loader, String parserName, Method parser, Method validator) {
      this.registerParserFor(loader, parserName, parser, validator, null);
   }

   default void registerParserFor(String loader, String parserName, Method parser) {
      this.registerParserFor(loader, parserName, parser, (Method)null);
   }

   <T extends Enum<T>> void registerEnumForBracket(String var1, ResourceLocation var2, Class<T> var3);

   record DumperData(String subCommandName, String outputFileName, Supplier<Stream<String>> data) {
      public DumperData(String subCommandName, Supplier<Stream<String>> data) {
         this(subCommandName, subCommandName, data);
      }
   }
}
