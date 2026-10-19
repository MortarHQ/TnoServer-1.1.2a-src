package org.openzen.zenscript.codemodel.context;

import java.util.List;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.codemodel.GenericName;
import org.openzen.zenscript.codemodel.annotations.AnnotationDefinition;
import org.openzen.zenscript.codemodel.definition.ZSPackage;
import org.openzen.zenscript.codemodel.generic.TypeParameter;
import org.openzen.zenscript.codemodel.type.DefinitionTypeID;
import org.openzen.zenscript.codemodel.type.GlobalTypeRegistry;
import org.openzen.zenscript.codemodel.type.TypeID;

public class LocalTypeResolutionContext implements TypeResolutionContext {
   private final TypeResolutionContext outer;
   private final CompilingType type;
   private final TypeParameter[] parameters;

   public LocalTypeResolutionContext(TypeResolutionContext outer, CompilingType type, TypeParameter[] parameters) {
      this.outer = outer;
      this.type = type;
      this.parameters = parameters;
   }

   @Override
   public ZSPackage getRootPackage() {
      return this.outer.getRootPackage();
   }

   @Override
   public GlobalTypeRegistry getTypeRegistry() {
      return this.outer.getTypeRegistry();
   }

   @Override
   public AnnotationDefinition getAnnotation(String name) {
      return this.outer.getAnnotation(name);
   }

   @Override
   public TypeID getType(CodePosition position, List<GenericName> name) {
      if (this.type != null) {
         CompilingType compiling = this.type.getInner(name.get(0).name);
         if (compiling != null) {
            DefinitionTypeID outer = this.getTypeRegistry().getForMyDefinition(this.type.load());
            return compiling.getInnerType(this.getTypeRegistry(), name, 0, outer);
         }
      }

      if (name.size() == 1 && name.get(0).hasNoArguments()) {
         for (TypeParameter parameter : this.parameters) {
            if (parameter.name.equals(name.get(0).name)) {
               return this.getTypeRegistry().getGeneric(parameter);
            }
         }
      }

      return this.outer.getType(position, name);
   }

   @Override
   public TypeID getThisType() {
      return this.type == null ? null : this.getTypeRegistry().getForMyDefinition(this.type.load());
   }
}
