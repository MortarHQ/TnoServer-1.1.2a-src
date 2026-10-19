package org.openzen.zenscript.codemodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openzen.zenscript.codemodel.definition.ExpansionDefinition;
import org.openzen.zenscript.codemodel.definition.ZSPackage;

public class PackageDefinitions {
   private final List<HighLevelDefinition> definitions = new ArrayList<>();
   private final Map<String, HighLevelDefinition> definitionsByName = new HashMap<>();

   public List<HighLevelDefinition> getAll() {
      return this.definitions;
   }

   public void add(HighLevelDefinition definition) {
      this.definitions.add(definition);
      this.definitionsByName.put(definition.name, definition);
   }

   public HighLevelDefinition getDefinition(String name) {
      return this.definitionsByName.get(name);
   }

   public void registerTo(ZSPackage pkg) {
      for (HighLevelDefinition definition : this.definitions) {
         pkg.register(definition);
      }
   }

   public void registerExpansionsTo(List<ExpansionDefinition> expansions) {
      for (HighLevelDefinition definition : this.definitions) {
         if (definition instanceof ExpansionDefinition) {
            expansions.add((ExpansionDefinition)definition);
         }
      }
   }
}
