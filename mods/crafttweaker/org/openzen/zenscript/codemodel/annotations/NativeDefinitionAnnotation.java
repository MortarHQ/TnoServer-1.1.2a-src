package org.openzen.zenscript.codemodel.annotations;

import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.codemodel.context.TypeContext;
import org.openzen.zenscript.codemodel.scope.BaseScope;
import org.openzen.zenscript.codemodel.serialization.CodeSerializationOutput;

public class NativeDefinitionAnnotation implements DefinitionAnnotation {
   private final String identifier;

   public NativeDefinitionAnnotation(String identifier) {
      this.identifier = identifier;
   }

   @Override
   public AnnotationDefinition getDefinition() {
      return NativeAnnotationDefinition.INSTANCE;
   }

   @Override
   public void apply(HighLevelDefinition definition, BaseScope scope) {
      definition.setTag(NativeTag.class, new NativeTag(this.identifier));
   }

   @Override
   public void applyOnSubtype(HighLevelDefinition definition, BaseScope scope) {
   }

   @Override
   public void serialize(CodeSerializationOutput output, HighLevelDefinition definition, TypeContext context) {
      output.writeString(this.identifier);
   }

   public String getIdentifier() {
      return this.identifier;
   }
}
