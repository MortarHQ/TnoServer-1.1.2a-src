package org.openzen.zenscript.codemodel.annotations;

import org.openzen.zenscript.codemodel.context.TypeContext;
import org.openzen.zenscript.codemodel.member.FunctionalMember;
import org.openzen.zenscript.codemodel.member.GetterMember;
import org.openzen.zenscript.codemodel.member.IDefinitionMember;
import org.openzen.zenscript.codemodel.member.SetterMember;
import org.openzen.zenscript.codemodel.scope.BaseScope;
import org.openzen.zenscript.codemodel.serialization.CodeSerializationOutput;

public interface MemberAnnotation {
   MemberAnnotation[] NONE = new MemberAnnotation[0];

   AnnotationDefinition getDefinition();

   void apply(IDefinitionMember var1, BaseScope var2);

   void applyOnOverridingMethod(FunctionalMember var1, BaseScope var2);

   void applyOnOverridingGetter(GetterMember var1, BaseScope var2);

   void applyOnOverridingSetter(SetterMember var1, BaseScope var2);

   void serialize(CodeSerializationOutput var1, IDefinitionMember var2, TypeContext var3);
}
