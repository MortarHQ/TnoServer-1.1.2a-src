package org.openzen.zenscript.javashared;

import org.openzen.zenscript.codemodel.expression.Expression;

@FunctionalInterface
public interface JavaNativeTranslation<T> {
   T translate(Expression var1, JavaNativeTranslator<T> var2);
}
