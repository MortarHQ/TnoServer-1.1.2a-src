package org.openzen.zenscript.javashared;

import org.openzen.zenscript.codemodel.FunctionParameter;
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
import org.openzen.zenscript.codemodel.type.TypeID;
import org.openzen.zenscript.codemodel.type.TypeVisitor;

public class JavaTypeNameVisitor implements TypeVisitor<String> {
   public static final JavaTypeNameVisitor INSTANCE = new JavaTypeNameVisitor();

   private JavaTypeNameVisitor() {
   }

   public String process(TypeID type) {
      return type.accept(this);
   }

   public String visitBasic(BasicTypeID basic) {
      switch (basic) {
         case VOID:
            return "Void";
         case BOOL:
            return "Bool";
         case BYTE:
            return "Byte";
         case SBYTE:
            return "SByte";
         case SHORT:
            return "Short";
         case USHORT:
            return "UShort";
         case INT:
            return "Int";
         case UINT:
            return "UInt";
         case LONG:
            return "Long";
         case ULONG:
            return "ULong";
         case FLOAT:
            return "Float";
         case DOUBLE:
            return "Double";
         case CHAR:
            return "Char";
         case STRING:
            return "String";
         default:
            throw new IllegalArgumentException("Invalid type: " + basic);
      }
   }

   public String visitArray(ArrayTypeID array) {
      return array.dimension == 1 ? this.process(array.elementType) + "Array" : this.process(array.elementType) + array.dimension + "DArray";
   }

   public String visitAssoc(AssocTypeID assoc) {
      return this.process(assoc.keyType) + this.process(assoc.valueType) + "Map";
   }

   public String visitGenericMap(GenericMapTypeID map) {
      return "GenericMap";
   }

   public String visitIterator(IteratorTypeID iterator) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   public String visitFunction(FunctionTypeID function) {
      StringBuilder result = new StringBuilder();

      for (FunctionParameter parameter : function.header.parameters) {
         result.append(this.process(parameter.type));
      }

      result.append("To");
      result.append(this.process(function.header.getReturnType()));
      result.append("Function");
      return result.toString();
   }

   public String visitDefinition(DefinitionTypeID definition) {
      return definition.definition.name;
   }

   public String visitGeneric(GenericTypeID generic) {
      return generic.parameter.name;
   }

   public String visitRange(RangeTypeID range) {
      return range.baseType.accept(this) + "Range";
   }

   public String visitOptional(OptionalTypeID type) {
      StringBuilder result = new StringBuilder();
      if (type.isOptional()) {
         result.append("Optional");
      }

      result.append(type.baseType.accept(this));
      return result.toString();
   }
}
