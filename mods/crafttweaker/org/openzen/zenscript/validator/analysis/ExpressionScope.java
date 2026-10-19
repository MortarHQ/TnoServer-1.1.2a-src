package org.openzen.zenscript.validator.analysis;

import org.openzen.zenscript.codemodel.AccessScope;
import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.codemodel.member.EnumConstantMember;
import org.openzen.zenscript.codemodel.member.FieldMember;
import org.openzen.zenscript.codemodel.statement.VarStatement;

public interface ExpressionScope {
   boolean isConstructor();

   boolean isFirstStatement();

   boolean hasThis();

   boolean isFieldInitialized(FieldMember var1);

   boolean isEnumConstantInitialized(EnumConstantMember var1);

   boolean isLocalVariableInitialized(VarStatement var1);

   void markConstructorForwarded();

   boolean isStaticInitializer();

   HighLevelDefinition getDefinition();

   AccessScope getAccessScope();
}
