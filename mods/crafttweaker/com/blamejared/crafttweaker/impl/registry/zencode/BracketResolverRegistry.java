package com.blamejared.crafttweaker.impl.registry.zencode;

import com.blamejared.crafttweaker.api.command.type.IBracketDumperInfo;
import com.blamejared.crafttweaker.api.plugin.IBracketParserRegistrationHandler;
import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.openzen.zenscript.parser.BracketExpressionParser;

public final class BracketResolverRegistry {
   private final Map<IScriptLoader, BracketResolverRegistry.BracketData> brackets = new HashMap<>();

   public void registerBracket(
      IScriptLoader loader, String name, BracketExpressionParser bracketParser, IBracketParserRegistrationHandler.DumperData dumperData
   ) {
      BracketResolverRegistry.BracketData data = this.brackets.computeIfAbsent(loader, it -> {
         throw new IllegalStateException("No data for loader " + loader + ": this cannot happen");
      });
      Map<String, BracketResolverRegistry.BracketHandle> loaderBrackets = data.brackets();
      if (loaderBrackets.containsKey(name)) {
         throw new IllegalArgumentException("A bracket handler with the name '" + name + "' is already registered in loader '" + loader + "'");
      }

      BracketDumperInfo info = dumperData == null ? null : new BracketDumperInfo(name, dumperData);
      loaderBrackets.put(name, new BracketResolverRegistry.BracketHandle(bracketParser, info));
   }

   public void fillLoaderData(Collection<IScriptLoader> loader) {
      loader.forEach(it -> this.brackets.put(it, new BracketResolverRegistry.BracketData()));
   }

   public void applyInheritanceRules() {
      Map<IScriptLoader, BracketResolverRegistry.BracketData> snapshot = this.createSnapshot();
      Map<IScriptLoader, BracketResolverRegistry.BracketData> inherited = this.applyInheritanceRules(snapshot);
      this.brackets.clear();
      this.brackets.putAll(inherited);
   }

   public Map<String, IBracketDumperInfo> getBracketDumpers(IScriptLoader loader) {
      return this.brackets
         .getOrDefault(loader, new BracketResolverRegistry.BracketData())
         .brackets()
         .entrySet()
         .stream()
         .filter(it -> it.getValue().dumperInfo() != null)
         .collect(Collectors.toMap(Entry::getKey, it -> it.getValue().dumperInfo()));
   }

   public Map<String, BracketExpressionParser> getBracketResolvers(IScriptLoader loader) {
      return this.brackets
         .getOrDefault(loader, new BracketResolverRegistry.BracketData())
         .brackets()
         .entrySet()
         .stream()
         .collect(Collectors.toMap(Entry::getKey, it -> it.getValue().parser()));
   }

   private Map<IScriptLoader, BracketResolverRegistry.BracketData> createSnapshot() {
      return this.brackets
         .entrySet()
         .stream()
         .collect(Collectors.toMap(Entry::getKey, it -> new BracketResolverRegistry.BracketData(new HashMap<>(it.getValue().brackets()))));
   }

   private Map<IScriptLoader, BracketResolverRegistry.BracketData> applyInheritanceRules(Map<IScriptLoader, BracketResolverRegistry.BracketData> snapshot) {
      return snapshot.entrySet().stream().collect(Collectors.toMap(Entry::getKey, it -> this.applyInheritanceRules(it.getKey(), snapshot)));
   }

   private BracketResolverRegistry.BracketData applyInheritanceRules(IScriptLoader loader, Map<IScriptLoader, BracketResolverRegistry.BracketData> snapshot) {
      Map<String, BracketResolverRegistry.BracketHandle> inheritedData = new HashMap<>();
      Collection<Map<String, BracketResolverRegistry.BracketHandle>> inheritanceData = this.computeInheritanceData(loader, snapshot);
      inheritanceData.forEach(it -> {
         try {
            this.tryMerge(inheritedData, (Map<String, BracketResolverRegistry.BracketHandle>)it);
         } catch (Exception e) {
            throw new IllegalStateException("Unable to apply inheritance rules for " + loader.name(), e);
         }
      });
      return new BracketResolverRegistry.BracketData(inheritedData);
   }

   private Collection<Map<String, BracketResolverRegistry.BracketHandle>> computeInheritanceData(
      IScriptLoader loader, Map<IScriptLoader, BracketResolverRegistry.BracketData> snapshot
   ) {
      return Stream.concat(loader.allInheritedLoaders().stream(), Stream.of(loader))
         .map(snapshot::get)
         .filter(Objects::nonNull)
         .map(BracketResolverRegistry.BracketData::brackets)
         .toList();
   }

   private void tryMerge(Map<String, BracketResolverRegistry.BracketHandle> loaderData, Map<String, BracketResolverRegistry.BracketHandle> inheritedData) {
      inheritedData.forEach((name, handle) -> {
         if (loaderData.containsKey(name)) {
            throw new IllegalStateException("A bracket handler with the name " + name + " is already registered");
         }

         loaderData.put(name, handle);
      });
   }

   private record BracketData(Map<String, BracketResolverRegistry.BracketHandle> brackets) {
      BracketData() {
         this(new HashMap<>());
      }
   }

   private record BracketHandle(BracketExpressionParser parser, IBracketDumperInfo dumperInfo) {
   }
}
