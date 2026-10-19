package org.openzen.zenscript.codemodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zencode.shared.CompileExceptionCode;
import org.openzen.zenscript.codemodel.annotations.AnnotationDefinition;
import org.openzen.zenscript.codemodel.annotations.NativeAnnotationDefinition;
import org.openzen.zenscript.codemodel.annotations.PreconditionAnnotationDefinition;
import org.openzen.zenscript.codemodel.definition.ExpansionDefinition;
import org.openzen.zenscript.codemodel.definition.ZSPackage;
import org.openzen.zenscript.codemodel.type.GlobalTypeRegistry;
import org.openzen.zenscript.codemodel.type.ISymbol;

public final class ModuleSpace {
   public final ZSPackage rootPackage = new ZSPackage(null, "");
   public final GlobalTypeRegistry registry;
   public final ZSPackage globalsPackage = new ZSPackage(null, "");
   private final List<ExpansionDefinition> expansions = new ArrayList<>();
   private final Map<String, ISymbol> globals = new HashMap<>();
   private final AnnotationDefinition[] annotations;
   private final Map<String, SemanticModule> modules = new HashMap<>();

   public ModuleSpace(GlobalTypeRegistry registry, List<AnnotationDefinition> annotations) {
      this.registry = registry;
      annotations.add(NativeAnnotationDefinition.INSTANCE);
      annotations.add(PreconditionAnnotationDefinition.INSTANCE);
      this.annotations = annotations.toArray(new AnnotationDefinition[annotations.size()]);
   }

   public void addModule(String name, SemanticModule dependency) throws CompileException {
      this.modules.put(name, dependency);
      this.rootPackage.add(name, dependency.modulePackage);
      dependency.definitions.registerExpansionsTo(this.expansions);

      for (Entry<String, ISymbol> globalEntry : dependency.globals.entrySet()) {
         if (this.globals.containsKey(globalEntry.getKey())) {
            throw new CompileException(CodePosition.META, CompileExceptionCode.DUPLICATE_GLOBAL, "Duplicate global: " + globalEntry.getKey());
         }

         this.globals.put(globalEntry.getKey(), globalEntry.getValue());
      }
   }

   public void addGlobal(String name, ISymbol global) {
      this.globals.put(name, global);
   }

   public SemanticModule getModule(String name) {
      return this.modules.get(name);
   }

   public ZSPackage collectPackages() {
      return this.rootPackage;
   }

   public List<ExpansionDefinition> collectExpansions() {
      return this.expansions;
   }

   public Map<String, ISymbol> collectGlobals() {
      return this.globals;
   }

   public AnnotationDefinition[] getAnnotations() {
      return this.annotations;
   }
}
