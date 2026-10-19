package org.openzen.zenscript.formatter;

import org.openzen.zenscript.codemodel.generic.GenericParameterBoundVisitor;
import org.openzen.zenscript.codemodel.generic.ParameterSuperBound;
import org.openzen.zenscript.codemodel.generic.ParameterTypeBound;
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
import org.openzen.zenscript.formattershared.Importer;
import stdlib.Chars;

public class TypeFormatter implements TypeVisitor<String>, GenericParameterBoundVisitor<String> {
   private final ScriptFormattingSettings settings;
   private final Importer importer;

   public TypeFormatter(ScriptFormattingSettings settings, Importer importer) {
      this.settings = settings;
      this.importer = importer;
   }

   public String format(TypeID type) {
      return type.accept(this);
   }

   public String visitBasic(BasicTypeID basic) {
      return basic.name;
   }

   public String visitArray(ArrayTypeID array) {
      String element = this.format(array.elementType);
      String result;
      if (array.dimension == 1) {
         result = element + "[]";
      } else {
         result = element + "[" + Chars.times(',', array.dimension - 1) + "]";
      }

      return result;
   }

   public String visitAssoc(AssocTypeID assoc) {
      return this.format(assoc.valueType) + "[" + this.format(assoc.keyType) + "]";
   }

   public String visitIterator(IteratorTypeID iterator) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   public String visitFunction(FunctionTypeID function) {
      StringBuilder result = new StringBuilder();
      result.append("function");
      FormattingUtils.formatHeader(result, this.settings, function.header, this);
      return result.toString();
   }

   public String visitDefinition(DefinitionTypeID definition) {
      String importedName = this.importer.importDefinition(definition.definition);
      if (definition.typeArguments != null && definition.typeArguments.length != 0) {
         StringBuilder result = new StringBuilder();
         result.append(importedName);
         result.append("<");
         int index = 0;

         for (TypeID typeParameter : definition.typeArguments) {
            if (index > 0) {
               result.append(", ");
            }

            result.append(this.format(typeParameter));
         }

         result.append(">");
         return result.toString();
      } else {
         return importedName;
      }
   }

   public String visitGeneric(GenericTypeID generic) {
      return generic.parameter.name;
   }

   public String visitRange(RangeTypeID range) {
      return this.format(range.baseType) + " .. " + this.format(range.baseType);
   }

   public String visitOptional(OptionalTypeID type) {
      return type.accept(this) + "?";
   }

   public String visitSuper(ParameterSuperBound bound) {
      return "super " + bound.type.accept(this);
   }

   public String visitType(ParameterTypeBound bound) {
      return bound.type.accept(this);
   }

   public String visitGenericMap(GenericMapTypeID map) {
      StringBuilder result = new StringBuilder();
      result.append(this.format(map.value));
      result.append("[<");
      FormattingUtils.formatTypeParameters(result, new TypeParameter[]{map.key}, this);
      result.append("]>");
      return result.toString();
   }
}
