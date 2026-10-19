package org.openzen.zenscript.codemodel.context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.codemodel.GenericName;
import org.openzen.zenscript.codemodel.annotations.AnnotationDefinition;
import org.openzen.zenscript.codemodel.definition.ZSPackage;
import org.openzen.zenscript.codemodel.type.GlobalTypeRegistry;
import org.openzen.zenscript.codemodel.type.ISymbol;
import org.openzen.zenscript.codemodel.type.TypeID;

public class ModuleTypeResolutionContext implements TypeResolutionContext {
   private final GlobalTypeRegistry registry;
   private final Map<String, AnnotationDefinition> annotations = new HashMap<>();
   private final Map<String, ISymbol> globals;
   private final ZSPackage rootPackage;
   private final CompilingPackage rootCompiling;

   public ModuleTypeResolutionContext(
      GlobalTypeRegistry registry, AnnotationDefinition[] annotations, ZSPackage rootPackage, CompilingPackage rootCompiling, Map<String, ISymbol> globals
   ) {
      this.registry = registry;
      this.rootPackage = rootPackage;
      this.rootCompiling = rootCompiling;
      this.globals = globals;

      for (AnnotationDefinition annotation : annotations) {
         this.annotations.put(annotation.getAnnotationName(), annotation);
      }
   }

   @Override
   public ZSPackage getRootPackage() {
      return this.rootPackage;
   }

   @Override
   public GlobalTypeRegistry getTypeRegistry() {
      return this.registry;
   }

   @Override
   public AnnotationDefinition getAnnotation(String name) {
      return this.annotations.get(name);
   }

   @Override
   public TypeID getType(CodePosition position, List<GenericName> name) {
      if (this.rootCompiling != null) {
         TypeID compiling = this.rootCompiling.getType(this, name);
         if (compiling != null) {
            return compiling;
         }
      }

      return name.size() == 1 && this.globals.containsKey(name.get(0).name)
         ? this.globals.get(name.get(0).name).getType(position, this, name.get(0).arguments)
         : this.rootPackage.getType(position, this, name);
   }

   @Override
   public TypeID getThisType() {
      return null;
   }
}
