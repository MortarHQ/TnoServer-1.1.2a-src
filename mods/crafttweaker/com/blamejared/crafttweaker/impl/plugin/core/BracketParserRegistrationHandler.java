package com.blamejared.crafttweaker.impl.plugin.core;

import com.blamejared.crafttweaker.api.plugin.IBracketParserRegistrationHandler;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zenscript.parser.BracketExpressionParser;

final class BracketParserRegistrationHandler implements IBracketParserRegistrationHandler {
   private final List<BracketParserRegistrationHandler.BracketData> bracketRequests = new ArrayList<>();
   private final List<BracketParserRegistrationHandler.EnumData> enumRequests = new ArrayList<>();

   private BracketParserRegistrationHandler() {
   }

   static BracketParserRegistrationHandler of(Consumer<IBracketParserRegistrationHandler> consumer) {
      BracketParserRegistrationHandler parser = new BracketParserRegistrationHandler();
      consumer.accept(parser);
      return parser;
   }

   @Override
   public void registerParserFor(String loader, String parserName, BracketExpressionParser parser, IBracketParserRegistrationHandler.DumperData parserDumper) {
      this.bracketRequests.add(new BracketParserRegistrationHandler.BracketData(loader, parserName, parser, parserDumper));
   }

   @Override
   public void registerParserFor(String loader, String parserName, Method parser, Method validator, IBracketParserRegistrationHandler.DumperData dumper) {
      this.registerParserFor(loader, parserName, new ValidatedEscapableBracketParser(parserName, parser, this.lookup(validator)), dumper);
   }

   @Override
   public <T extends Enum<T>> void registerEnumForBracket(String loader, ResourceLocation id, Class<T> enumClass) {
      this.enumRequests.add(new BracketParserRegistrationHandler.EnumData(loader, id, enumClass));
   }

   List<BracketParserRegistrationHandler.BracketData> bracketRequests() {
      return Collections.unmodifiableList(this.bracketRequests);
   }

   List<BracketParserRegistrationHandler.EnumData> enumRequests() {
      return Collections.unmodifiableList(this.enumRequests);
   }

   private MethodHandle lookup(Method method) {
      if (method == null) {
         return null;
      }

      try {
         return MethodHandles.publicLookup().unreflect(method);
      } catch (IllegalAccessException e) {
         throw new IllegalArgumentException("Invalid validator specified", e);
      }
   }

   record BracketData(String loader, String parserName, BracketExpressionParser parser, IBracketParserRegistrationHandler.DumperData parserDumper) {
   }

   record EnumData(String loader, ResourceLocation id, Class<? extends Enum<?>> enumClass) {
   }
}
