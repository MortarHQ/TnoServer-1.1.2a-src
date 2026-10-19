package org.openzen.zenscript.codemodel.expression.switchvalue;

public interface SwitchValueVisitor<T> {
   T acceptInt(IntSwitchValue var1);

   T acceptChar(CharSwitchValue var1);

   T acceptString(StringSwitchValue var1);

   T acceptEnumConstant(EnumConstantSwitchValue var1);

   T acceptVariantOption(VariantOptionSwitchValue var1);
}
