package org.openzen.zenscript.validator.analysis;

import org.openzen.zenscript.codemodel.AccessScope;
import org.openzen.zenscript.codemodel.FunctionHeader;
import org.openzen.zenscript.codemodel.HighLevelDefinition;

public interface StatementScope {
   boolean isConstructor();

   boolean isStatic();

   FunctionHeader getFunctionHeader();

   boolean isStaticInitializer();

   HighLevelDefinition getDefinition();

   AccessScope getAccessScope();
}
