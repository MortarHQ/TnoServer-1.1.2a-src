package org.openzen.zenscript.codemodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openzen.zencode.shared.logging.IZSLogger;
import org.openzen.zenscript.codemodel.annotations.AnnotationDefinition;
import org.openzen.zenscript.codemodel.annotations.AnnotationProcessor;
import org.openzen.zenscript.codemodel.context.ModuleContext;
import org.openzen.zenscript.codemodel.context.ModuleTypeResolutionContext;
import org.openzen.zenscript.codemodel.definition.ExpansionDefinition;
import org.openzen.zenscript.codemodel.definition.ZSPackage;
import org.openzen.zenscript.codemodel.scope.FileScope;
import org.openzen.zenscript.codemodel.type.GlobalTypeRegistry;
import org.openzen.zenscript.codemodel.type.ISymbol;

public class SemanticModule {
   public static final SemanticModule[] NONE = new SemanticModule[0];
   public final String name;
   public final SemanticModule[] dependencies;
   public final FunctionParameter[] parameters;
   public final SemanticModule.State state;
   public final Module module;
   public final ZSPackage rootPackage;
   public final ZSPackage modulePackage;
   public final PackageDefinitions definitions;
   public final List<ScriptBlock> scripts;
   public final Map<String, ISymbol> globals = new HashMap<>();
   public final GlobalTypeRegistry registry;
   public final List<ExpansionDefinition> expansions;
   public final AnnotationDefinition[] annotations;
   public final IZSLogger logger;

   public SemanticModule(
      Module module,
      SemanticModule[] dependencies,
      FunctionParameter[] parameters,
      SemanticModule.State state,
      ZSPackage rootPackage,
      ZSPackage modulePackage,
      PackageDefinitions definitions,
      List<ScriptBlock> scripts,
      GlobalTypeRegistry registry,
      List<ExpansionDefinition> expansions,
      AnnotationDefinition[] annotations,
      IZSLogger logger
   ) {
      this.name = module.name;
      this.module = module;
      this.dependencies = dependencies;
      this.parameters = parameters;
      this.state = state;
      this.rootPackage = rootPackage;
      this.modulePackage = modulePackage;
      this.definitions = definitions;
      this.scripts = scripts;
      this.registry = registry;
      this.expansions = expansions;
      this.annotations = annotations;
      this.logger = logger;
   }

   public boolean isValid() {
      return this.state != SemanticModule.State.INVALID;
   }

   public SemanticModule normalize() {
      if (this.state != SemanticModule.State.ASSEMBLED) {
         throw new IllegalStateException("Module is invalid");
      }

      ModuleTypeResolutionContext context = new ModuleTypeResolutionContext(this.registry, this.annotations, this.rootPackage, null, this.globals);
      AnnotationProcessor annotationProcessor = new AnnotationProcessor(context, this.expansions);
      List<ScriptBlock> processedScripts = new ArrayList<>();
      FileScope fileScope = new FileScope(context, this.expansions, this.globals, member -> {});

      for (ScriptBlock block : this.scripts) {
         processedScripts.add(annotationProcessor.process(block).normalize(fileScope));
      }

      for (HighLevelDefinition definition : this.definitions.getAll()) {
         annotationProcessor.process(definition);
         definition.normalize(fileScope);
      }

      return new SemanticModule(
         this.module,
         this.dependencies,
         this.parameters,
         SemanticModule.State.NORMALIZED,
         this.rootPackage,
         this.modulePackage,
         this.definitions,
         processedScripts,
         this.registry,
         this.expansions,
         this.annotations,
         this.logger
      );
   }

   public ModuleContext getContext() {
      return new ModuleContext(this.registry, this.module, this.expansions, this.rootPackage);
   }

   public enum State {
      INVALID,
      ASSEMBLED,
      NORMALIZED,
      VALIDATED;
   }
}
