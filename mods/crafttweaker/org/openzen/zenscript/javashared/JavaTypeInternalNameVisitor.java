package org.openzen.zenscript.javashared;

import org.openzen.zenscript.codemodel.generic.ParameterTypeBound;
import org.openzen.zenscript.codemodel.generic.TypeParameterBound;
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

public class JavaTypeInternalNameVisitor implements TypeVisitor<String> {
   private final JavaTypeInternalNameVisitor forOptional;
   private final JavaContext context;
   private final boolean optional;

   public JavaTypeInternalNameVisitor(JavaContext context) {
      this(context, false);
   }

   private JavaTypeInternalNameVisitor(JavaContext context, boolean optional) {
      this.optional = optional;
      this.context = context;
      this.forOptional = optional ? this : new JavaTypeInternalNameVisitor(context, true);
   }

   public String visitBasic(BasicTypeID basic) {
      if (this.optional) {
         switch (basic) {
            case BOOL:
               return "java/lang/Boolean";
            case CHAR:
               return "java/lang/Character";
            case BYTE:
               return "java/lang/Integer";
            case SBYTE:
               return "java/lang/Byte";
            case SHORT:
               return "java/lang/Short";
            case USHORT:
               return "java/lang/Integer";
            case INT:
               return "java/lang/Integer";
            case UINT:
               return "java/lang/Integer";
            case LONG:
               return "java/lang/Long";
            case ULONG:
               return "java/lang/Long";
            case USIZE:
               return "java/lang/Integer";
            case FLOAT:
               return "java/lang/Float";
            case DOUBLE:
               return "java/lang/Double";
            case STRING:
               return "java/lang/String";
            default:
               throw new IllegalArgumentException("Not a valid type: " + basic);
         }
      } else {
         switch (basic) {
            case BOOL:
               return "Z";
            case CHAR:
               return "C";
            case BYTE:
               return "I";
            case SBYTE:
               return "B";
            case SHORT:
               return "S";
            case USHORT:
               return "I";
            case INT:
               return "I";
            case UINT:
               return "I";
            case LONG:
               return "J";
            case ULONG:
               return "J";
            case USIZE:
               return "I";
            case FLOAT:
               return "F";
            case DOUBLE:
               return "D";
            case STRING:
               return "java/lang/String";
            case VOID:
               return "V";
            default:
               throw new IllegalArgumentException("Not a valid type: " + basic);
         }
      }
   }

   public String visitArray(ArrayTypeID array) {
      return "[" + array.elementType.accept(this);
   }

   public String visitAssoc(AssocTypeID assoc) {
      return "java/util/Map;";
   }

   public String visitIterator(IteratorTypeID iterator) {
      return "java/lang/Iterator;";
   }

   public String visitFunction(FunctionTypeID function) {
      return this.context.getFunction(function).getCls().internalName;
   }

   public String visitDefinition(DefinitionTypeID definition) {
      return this.context.getJavaClass(definition.definition).internalName;
   }

   public String visitGeneric(GenericTypeID generic) {
      for (TypeParameterBound bound : generic.parameter.bounds) {
         if (bound instanceof ParameterTypeBound) {
            return ((ParameterTypeBound)bound).type.accept(this);
         }
      }

      return "java/lang/Object";
   }

   public String visitRange(RangeTypeID range) {
      return this.context.getRange(range).cls.internalName;
   }

   public String visitOptional(OptionalTypeID modified) {
      return modified.isOptional() ? modified.withoutOptional().accept(this.forOptional) : modified.baseType.accept(this);
   }

   public String visitGenericMap(GenericMapTypeID map) {
      return "java/util/Map";
   }
}
