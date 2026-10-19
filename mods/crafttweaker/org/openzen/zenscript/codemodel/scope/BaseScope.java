package org.openzen.zenscript.codemodel.scope;

import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zenscript.codemodel.FunctionHeader;
import org.openzen.zenscript.codemodel.GenericName;
import org.openzen.zenscript.codemodel.expression.Expression;
import org.openzen.zenscript.codemodel.partial.IPartialExpression;
import org.openzen.zenscript.codemodel.statement.LoopStatement;
import org.openzen.zenscript.codemodel.type.GlobalTypeRegistry;
import org.openzen.zenscript.codemodel.type.TypeID;
import org.openzen.zenscript.codemodel.type.member.TypeMembers;

public abstract class BaseScope implements TypeScope {
   public abstract IPartialExpression get(CodePosition var1, GenericName var2) throws CompileException;

   public abstract LoopStatement getLoop(String var1);

   public abstract FunctionHeader getFunctionHeader();

   @Override
   public TypeMembers getTypeMembers(TypeID type) {
      return this.getMemberCache().get(type);
   }

   @Override
   public GlobalTypeRegistry getTypeRegistry() {
      return this.getMemberCache().getRegistry();
   }

   public abstract BaseScope.DollarEvaluator getDollar();

   public abstract IPartialExpression getOuterInstance(CodePosition var1) throws CompileException;

   public interface DollarEvaluator {
      Expression apply(CodePosition var1) throws CompileException;
   }
}
