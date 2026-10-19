package org.openzen.zenscript.codemodel.annotations;

import java.util.List;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.codemodel.FunctionHeader;
import org.openzen.zenscript.codemodel.FunctionParameter;
import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.codemodel.context.StatementContext;
import org.openzen.zenscript.codemodel.context.TypeContext;
import org.openzen.zenscript.codemodel.expression.CallArguments;
import org.openzen.zenscript.codemodel.member.IDefinitionMember;
import org.openzen.zenscript.codemodel.scope.BaseScope;
import org.openzen.zenscript.codemodel.scope.ExpressionScope;
import org.openzen.zenscript.codemodel.scope.StatementScope;
import org.openzen.zenscript.codemodel.serialization.CodeSerializationInput;
import org.openzen.zenscript.codemodel.statement.Statement;

public interface AnnotationDefinition {
   String getAnnotationName();

   List<FunctionHeader> getInitializers(BaseScope var1);

   ExpressionScope getScopeForMember(IDefinitionMember var1, BaseScope var2);

   ExpressionScope getScopeForType(HighLevelDefinition var1, BaseScope var2);

   ExpressionScope getScopeForStatement(Statement var1, StatementScope var2);

   ExpressionScope getScopeForParameter(FunctionHeader var1, FunctionParameter var2, BaseScope var3);

   MemberAnnotation createForMember(CodePosition var1, CallArguments var2);

   DefinitionAnnotation createForDefinition(CodePosition var1, CallArguments var2);

   StatementAnnotation createForStatement(CodePosition var1, CallArguments var2);

   ParameterAnnotation createForParameter(CodePosition var1, CallArguments var2);

   MemberAnnotation deserializeForMember(CodeSerializationInput var1, TypeContext var2, IDefinitionMember var3);

   DefinitionAnnotation deserializeForDefinition(CodeSerializationInput var1, TypeContext var2);

   StatementAnnotation deserializeForStatement(CodeSerializationInput var1, StatementContext var2);

   ParameterAnnotation deserializeForParameter(CodeSerializationInput var1, TypeContext var2);
}
