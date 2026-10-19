package org.openzen.zenscript.codemodel.type;

import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.codemodel.context.TypeResolutionContext;
import org.openzen.zenscript.codemodel.partial.IPartialExpression;
import org.openzen.zenscript.codemodel.scope.BaseScope;

public interface ISymbol {
   IPartialExpression getExpression(CodePosition var1, BaseScope var2, TypeID[] var3);

   TypeID getType(CodePosition var1, TypeResolutionContext var2, TypeID[] var3);
}
