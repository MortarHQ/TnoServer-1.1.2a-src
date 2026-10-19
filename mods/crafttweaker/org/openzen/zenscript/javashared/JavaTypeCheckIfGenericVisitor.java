package org.openzen.zenscript.javashared;

import java.util.Arrays;
import org.openzen.zenscript.codemodel.type.ArrayTypeID;
import org.openzen.zenscript.codemodel.type.AssocTypeID;
import org.openzen.zenscript.codemodel.type.BasicTypeID;
import org.openzen.zenscript.codemodel.type.DefinitionTypeID;
import org.openzen.zenscript.codemodel.type.FunctionTypeID;
import org.openzen.zenscript.codemodel.type.GenericMapTypeID;
import org.openzen.zenscript.codemodel.type.GenericTypeID;
import org.openzen.zenscript.codemodel.type.IteratorTypeID;
import org.openzen.zenscript.codemodel.type.OptionalTypeID;
import org.openzen.zenscript.codemodel.type.RangeTypeID;
import org.openzen.zenscript.codemodel.type.TypeVisitor;

public class JavaTypeCheckIfGenericVisitor implements TypeVisitor<Boolean> {
   public Boolean visitBasic(BasicTypeID basic) {
      return false;
   }

   public Boolean visitArray(ArrayTypeID array) {
      return array.elementType.accept(this);
   }

   public Boolean visitAssoc(AssocTypeID assoc) {
      return assoc.keyType.accept(this) || assoc.valueType.accept(this);
   }

   public Boolean visitGenericMap(GenericMapTypeID map) {
      return true;
   }

   public Boolean visitIterator(IteratorTypeID iterator) {
      return true;
   }

   public Boolean visitFunction(FunctionTypeID function) {
      return function.header.typeParameters.length > 0
         || function.header.getReturnType().accept(this)
         || Arrays.stream(function.header.parameters).anyMatch(functionParameter -> functionParameter.type.accept(this));
   }

   public Boolean visitDefinition(DefinitionTypeID definition) {
      return definition.isGeneric();
   }

   public Boolean visitGeneric(GenericTypeID generic) {
      return true;
   }

   public Boolean visitRange(RangeTypeID range) {
      return range.baseType.accept(this);
   }

   public Boolean visitOptional(OptionalTypeID type) {
      return type.baseType.accept(this);
   }
}
