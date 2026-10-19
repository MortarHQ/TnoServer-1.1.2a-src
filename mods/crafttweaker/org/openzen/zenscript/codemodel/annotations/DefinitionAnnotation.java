package org.openzen.zenscript.codemodel.annotations;

import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.codemodel.context.TypeContext;
import org.openzen.zenscript.codemodel.scope.BaseScope;
import org.openzen.zenscript.codemodel.serialization.CodeSerializationOutput;

public interface DefinitionAnnotation {
   DefinitionAnnotation[] NONE = new DefinitionAnnotation[0];

   AnnotationDefinition getDefinition();

   void apply(HighLevelDefinition var1, BaseScope var2);

   void applyOnSubtype(HighLevelDefinition var1, BaseScope var2);

   void serialize(CodeSerializationOutput var1, HighLevelDefinition var2, TypeContext var3);
}
