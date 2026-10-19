package org.openzen.zenscript.codemodel.expression.switchvalue;

public interface SwitchValueVisitorWithContext<C, R> {
   R acceptInt(C var1, IntSwitchValue var2);

   R acceptChar(C var1, CharSwitchValue var2);

   R acceptString(C var1, StringSwitchValue var2);

   R acceptEnumConstant(C var1, EnumConstantSwitchValue var2);

   R acceptVariantOption(C var1, VariantOptionSwitchValue var2);
}
