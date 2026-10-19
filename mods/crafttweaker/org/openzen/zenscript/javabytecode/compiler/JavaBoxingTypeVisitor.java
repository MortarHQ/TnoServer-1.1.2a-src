package org.openzen.zenscript.javabytecode.compiler;

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
import org.openzen.zenscript.codemodel.type.TypeVisitorWithContext;
import org.openzen.zenscript.javashared.JavaClass;
import org.openzen.zenscript.javashared.JavaMethod;

public class JavaBoxingTypeVisitor implements TypeVisitorWithContext<TypeID, Void, RuntimeException> {
   private static final JavaMethod BOOLEAN_VALUEOF = JavaMethod.getNativeStatic(JavaClass.BOOLEAN, "valueOf", "(Z)Ljava/lang/Boolean;");
   private static final JavaMethod BYTE_VALUEOF = JavaMethod.getNativeStatic(JavaClass.BYTE, "valueOf", "(B)Ljava/lang/Byte;");
   private static final JavaMethod SHORT_VALUEOF = JavaMethod.getNativeStatic(JavaClass.SHORT, "valueOf", "(S)Ljava/lang/Short;");
   private static final JavaMethod INTEGER_VALUEOF = JavaMethod.getNativeStatic(JavaClass.INTEGER, "valueOf", "(I)Ljava/lang/Integer;");
   private static final JavaMethod LONG_VALUEOF = JavaMethod.getNativeStatic(JavaClass.LONG, "valueOf", "(J)Ljava/lang/Long;");
   private static final JavaMethod FLOAT_VALUEOF = JavaMethod.getNativeStatic(JavaClass.FLOAT, "valueOf", "(F)Ljava/lang/Float;");
   private static final JavaMethod DOUBLE_VALUEOF = JavaMethod.getNativeStatic(JavaClass.DOUBLE, "valueOf", "(D)Ljava/lang/Double;");
   private static final JavaMethod CHARACTER_VALUEOF = JavaMethod.getNativeStatic(JavaClass.CHARACTER, "valueOf", "(C)Ljava/lang/Character;");
   private final JavaWriter writer;

   public JavaBoxingTypeVisitor(JavaWriter writer) {
      this.writer = writer;
   }

   public Void visitBasic(TypeID context, BasicTypeID basic) {
      JavaMethod method;
      switch (basic) {
         case BOOL:
            method = BOOLEAN_VALUEOF;
            break;
         case BYTE:
            method = INTEGER_VALUEOF;
            break;
         case SBYTE:
            method = BYTE_VALUEOF;
            break;
         case SHORT:
            method = SHORT_VALUEOF;
            break;
         case USHORT:
            method = INTEGER_VALUEOF;
            break;
         case INT:
         case UINT:
         case USIZE:
            method = INTEGER_VALUEOF;
            break;
         case LONG:
         case ULONG:
            method = LONG_VALUEOF;
            break;
         case FLOAT:
            method = FLOAT_VALUEOF;
            break;
         case DOUBLE:
            method = DOUBLE_VALUEOF;
            break;
         case CHAR:
            method = CHARACTER_VALUEOF;
            break;
         case STRING:
            return null;
         default:
            return null;
      }

      if (method != null) {
         this.writer.invokeStatic(method);
      }

      return null;
   }

   public Void visitArray(TypeID context, ArrayTypeID array) {
      return null;
   }

   public Void visitAssoc(TypeID context, AssocTypeID assoc) {
      return null;
   }

   public Void visitGenericMap(TypeID context, GenericMapTypeID map) {
      return null;
   }

   public Void visitIterator(TypeID context, IteratorTypeID iterator) {
      return null;
   }

   public Void visitFunction(TypeID context, FunctionTypeID function) {
      return null;
   }

   public Void visitDefinition(TypeID context, DefinitionTypeID definition) {
      return null;
   }

   public Void visitGeneric(TypeID context, GenericTypeID generic) {
      return null;
   }

   public Void visitRange(TypeID context, RangeTypeID range) {
      return null;
   }

   public Void visitOptional(TypeID context, OptionalTypeID type) {
      return null;
   }
}
