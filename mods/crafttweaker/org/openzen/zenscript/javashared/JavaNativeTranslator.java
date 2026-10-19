package org.openzen.zenscript.javashared;

import org.openzen.zenscript.codemodel.expression.CallExpression;
import org.openzen.zenscript.codemodel.expression.CastExpression;
import org.openzen.zenscript.codemodel.expression.Expression;

public interface JavaNativeTranslator<T> {
   T isEmptyAsLengthZero(Expression var1);

   T listToArray(CastExpression var1);

   T setToArray(CastExpression var1);

   T containsAsIndexOf(Expression var1, Expression var2);

   T sorted(Expression var1);

   T sortedWithComparator(Expression var1, Expression var2);

   T arrayCopy(Expression var1);

   T arrayCopyResize(CallExpression var1);

   T arrayCopyTo(CallExpression var1);

   T stringToAscii(Expression var1);

   T stringToUTF8(Expression var1);

   T bytesAsciiToString(Expression var1);

   T bytesUTF8ToString(Expression var1);
}
