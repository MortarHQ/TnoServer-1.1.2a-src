package org.openzen.zenscript.parser.type;

import java.util.List;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.codemodel.GenericName;
import org.openzen.zenscript.codemodel.annotations.AnnotationDefinition;
import org.openzen.zenscript.codemodel.context.TypeResolutionContext;
import org.openzen.zenscript.codemodel.definition.ZSPackage;
import org.openzen.zenscript.codemodel.generic.TypeParameter;
import org.openzen.zenscript.codemodel.type.GlobalTypeRegistry;
import org.openzen.zenscript.codemodel.type.TypeID;
import org.openzen.zenscript.parser.definitions.ParsedTypeParameter;

public class ParsedTypeGenericMap implements IParsedType {
   private final ParsedTypeParameter key;
   private final IParsedType value;

   public ParsedTypeGenericMap(ParsedTypeParameter key, IParsedType value) {
      this.key = key;
      this.value = value;
   }

   @Override
   public TypeID compile(TypeResolutionContext context) {
      TypeParameter cKey = this.key.compiled;
      TypeID valueType = this.value.compile(new ParsedTypeGenericMap.GenericMapScope(context, cKey));
      GlobalTypeRegistry registry = context.getTypeRegistry();
      return registry.getGenericMap(valueType, cKey);
   }

   private class GenericMapScope implements TypeResolutionContext {
      private final TypeResolutionContext outer;
      private final TypeParameter parameter;

      public GenericMapScope(TypeResolutionContext outer, TypeParameter parameter) {
         this.outer = outer;
         this.parameter = parameter;
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
      public TypeID getType(CodePosition position, List<GenericName> name) {
         return name.get(0).name.equals(this.parameter.name) && name.size() == 1 && name.get(0).hasNoArguments()
            ? this.getTypeRegistry().getGeneric(this.parameter)
            : this.outer.getType(position, name);
      }

      @Override
      public TypeID getThisType() {
         return this.outer.getThisType();
      }

      @Override
      public AnnotationDefinition getAnnotation(String name) {
         return this.outer.getAnnotation(name);
      }
   }
}
