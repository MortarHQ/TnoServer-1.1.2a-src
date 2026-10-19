package org.openzen.zenscript.javashared;

import java.util.Arrays;
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
import org.openzen.zenscript.codemodel.type.TypeID;
import org.openzen.zenscript.codemodel.type.TypeVisitor;
import org.openzen.zenscript.javashared.types.JavaFunctionalInterfaceTypeID;

public class JavaTypeDescriptorVisitor implements TypeVisitor<String> {
   private final JavaTypeDescriptorVisitor forOptional;
   private final JavaContext context;
   private final boolean optional;

   public JavaTypeDescriptorVisitor(JavaContext context) {
      this(context, false);
   }

   private JavaTypeDescriptorVisitor(JavaContext context, boolean optional) {
      this.optional = optional;
      this.context = context;
      this.forOptional = optional ? this : new JavaTypeDescriptorVisitor(context, true);
   }

   public String process(TypeID type) {
      return type.accept(this);
   }

   public String visitBasic(BasicTypeID basic) {
      if (this.optional) {
         switch (basic) {
            case BOOL:
               return "Ljava/lang/Boolean;";
            case CHAR:
               return "Ljava/lang/Character;";
            case BYTE:
               return "Ljava/lang/Integer;";
            case SBYTE:
               return "Ljava/lang/Byte;";
            case SHORT:
               return "Ljava/lang/Short;";
            case USHORT:
               return "Ljava/lang/Integer;";
            case INT:
               return "Ljava/lang/Integer;";
            case UINT:
               return "Ljava/lang/Integer;";
            case LONG:
               return "Ljava/lang/Long;";
            case ULONG:
               return "Ljava/lang/Long;";
            case USIZE:
               return "I";
            case FLOAT:
               return "Ljava/lang/Float;";
            case DOUBLE:
               return "Ljava/lang/Double;";
            case STRING:
               return "Ljava/lang/String;";
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
               return "Ljava/lang/String;";
            case VOID:
               return "V";
            default:
               throw new IllegalArgumentException("Not a valid type: " + basic);
         }
      }
   }

   public String visitArray(ArrayTypeID array) {
      if (array.elementType == BasicTypeID.BYTE) {
         return "[B";
      }

      if (array.elementType == BasicTypeID.USHORT) {
         return "[S";
      }

      char[] arrayDepth = new char[array.dimension];
      Arrays.fill(arrayDepth, '[');
      return new String(arrayDepth) + this.context.getDescriptor(array.elementType);
   }

   public String visitAssoc(AssocTypeID assoc) {
      return "Ljava/util/Map;";
   }

   public String visitIterator(IteratorTypeID iterator) {
      return "Ljava/lang/Iterator;";
   }

   public String visitFunction(FunctionTypeID function) {
      return function instanceof JavaFunctionalInterfaceTypeID
         ? "L" + ((JavaFunctionalInterfaceTypeID)function).method.cls.internalName + ";"
         : "L" + this.context.getFunction(function).getCls().internalName + ";";
   }

   public String visitDefinition(DefinitionTypeID definition) {
      JavaClass cls = this.context.getJavaClass(definition.definition);
      return "L" + cls.internalName + ";";
   }

   public String visitGeneric(GenericTypeID generic) {
      for (TypeParameterBound bound : generic.parameter.bounds) {
         if (bound instanceof ParameterTypeBound) {
            return this.process(((ParameterTypeBound)bound).type);
         }
      }

      return "Ljava/lang/Object;";
   }

   public String visitRange(RangeTypeID range) {
      return "L" + this.context.getRange(range).cls.internalName + ";";
   }

   public String visitOptional(OptionalTypeID modified) {
      return modified.withoutOptional().accept(this.forOptional);
   }

   public String visitGenericMap(GenericMapTypeID map) {
      return "Ljava/util/Map;";
   }
}
