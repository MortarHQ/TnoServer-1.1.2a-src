package org.openzen.zenscript.javashared;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openzen.zenscript.codemodel.FunctionParameter;
import org.openzen.zenscript.codemodel.generic.TypeParameter;
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

public class JavaSyntheticTypeSignatureConverter implements TypeVisitor<String> {
   private static final String[] TYPE_PARAMETER_NAMES = new String[]{"T", "U", "V", "W", "X", "Y", "Z"};
   public final List<TypeParameter> typeParameterList = new ArrayList<>();
   private final Map<TypeParameter, String> typeParameters = new HashMap<>();

   private String process(TypeID type) {
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
         case USIZE:
            return "USize";
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
      StringBuilder result = new StringBuilder();
      result.append(this.process(array.elementType));
      if (array.dimension > 1) {
         result.append(array.dimension).append("D");
      }

      result.append("Array");
      return result.toString();
   }

   public String visitAssoc(AssocTypeID assoc) {
      return this.process(assoc.keyType) + this.process(assoc.valueType) + "Map";
   }

   public String visitGenericMap(GenericMapTypeID map) {
      return map.value.accept(this).concat("GenericMap");
   }

   public String visitIterator(IteratorTypeID iterator) {
      StringBuilder result = new StringBuilder();

      for (TypeID type : iterator.iteratorTypes) {
         result.append(this.process(type));
      }

      result.append("Iterator");
      return result.toString();
   }

   public String visitFunction(FunctionTypeID function) {
      StringBuilder result = new StringBuilder();

      for (FunctionParameter parameter : function.header.parameters) {
         result.append(this.process(parameter.type));
      }

      result.append("To");
      result.append(this.process(function.header.getReturnType()));
      if (function.header.thrownType != null) {
         result.append("Throwing");
         result.append(this.process(function.header.thrownType));
      }

      return result.toString();
   }

   public String visitDefinition(DefinitionTypeID definition) {
      StringBuilder result = new StringBuilder();
      result.append(definition.definition.name);
      if (definition.typeArguments.length > 0) {
         result.append("With");

         for (int i = 0; i < definition.typeArguments.length; i++) {
            result.append(definition.typeArguments[i].accept(this));
         }
      }

      return result.toString();
   }

   public String visitGeneric(GenericTypeID generic) {
      if (this.typeParameters.containsKey(generic.parameter)) {
         return this.typeParameters.get(generic.parameter);
      }

      String name = this.typeParameters.size() < TYPE_PARAMETER_NAMES.length
         ? TYPE_PARAMETER_NAMES[this.typeParameters.size()]
         : "T" + this.typeParameters.size();
      this.typeParameters.put(generic.parameter, name);
      this.typeParameterList.add(generic.parameter);
      return name;
   }

   public String visitRange(RangeTypeID range) {
      return this.process(range.baseType) + "Range";
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
