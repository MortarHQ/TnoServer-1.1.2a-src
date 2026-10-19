package org.openzen.zenscript.codemodel.annotations;

import org.openzen.zenscript.codemodel.scope.StatementScope;
import org.openzen.zenscript.codemodel.statement.Statement;

public interface StatementAnnotation {
   StatementAnnotation[] NONE = new StatementAnnotation[0];

   AnnotationDefinition getDefinition();

   Statement apply(Statement var1, StatementScope var2);
}
