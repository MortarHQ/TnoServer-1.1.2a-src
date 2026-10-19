package org.openzen.zenscript.codemodel.expression.switchvalue;

public interface SwitchValue {
   <T> T accept(SwitchValueVisitor<T> var1);

   <C, R> R accept(C var1, SwitchValueVisitorWithContext<C, R> var2);
}
