package org.openzen.zenscript.codemodel.scope;

import java.util.HashMap;
import java.util.Map;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zenscript.codemodel.GenericName;
import org.openzen.zenscript.codemodel.expression.GetLocalVariableExpression;
import org.openzen.zenscript.codemodel.partial.IPartialExpression;
import org.openzen.zenscript.codemodel.statement.VarStatement;

public abstract class StatementScope extends BaseScope {
   private final Map<String, VarStatement> variables = new HashMap<>();

   public void defineVariable(VarStatement variable) {
      this.variables.put(variable.name, variable);
   }

   @Override
   public IPartialExpression get(CodePosition position, GenericName name) throws CompileException {
      return this.variables.containsKey(name.name) && name.hasNoArguments() ? new GetLocalVariableExpression(position, this.variables.get(name.name)) : null;
   }
}
