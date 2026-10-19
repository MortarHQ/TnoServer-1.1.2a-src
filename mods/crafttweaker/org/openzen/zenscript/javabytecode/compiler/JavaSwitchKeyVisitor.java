package org.openzen.zenscript.javabytecode.compiler;

import org.openzen.zenscript.codemodel.expression.switchvalue.CharSwitchValue;
import org.openzen.zenscript.codemodel.expression.switchvalue.EnumConstantSwitchValue;
import org.openzen.zenscript.codemodel.expression.switchvalue.IntSwitchValue;
import org.openzen.zenscript.codemodel.expression.switchvalue.StringSwitchValue;
import org.openzen.zenscript.codemodel.expression.switchvalue.SwitchValueVisitor;
import org.openzen.zenscript.codemodel.expression.switchvalue.VariantOptionSwitchValue;

public class JavaSwitchKeyVisitor implements SwitchValueVisitor<Integer> {
   public static final JavaSwitchKeyVisitor INSTANCE = new JavaSwitchKeyVisitor();

   private JavaSwitchKeyVisitor() {
   }

   public Integer acceptInt(IntSwitchValue value) {
      return value.value;
   }

   public Integer acceptChar(CharSwitchValue value) {
      return Integer.valueOf(value.value);
   }

   public Integer acceptString(StringSwitchValue value) {
      return value.value.hashCode();
   }

   public Integer acceptEnumConstant(EnumConstantSwitchValue value) {
      return value.constant.ordinal;
   }

   public Integer acceptVariantOption(VariantOptionSwitchValue value) {
      return value.option.getOrdinal();
   }
}
