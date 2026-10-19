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

public class JavaUnboxingTypeVisitor implements TypeVisitorWithContext<TypeID, Void, RuntimeException> {
   private static final JavaMethod UNBOX_BOOLEAN = JavaMethod.getNativeVirtual(JavaClass.BOOLEAN, "booleanValue", "()Z");
   private static final JavaMethod UNBOX_BYTE = JavaMethod.getNativeVirtual(JavaClass.BYTE, "byteValue", "()B");
   private static final JavaMethod UNBOX_SHORT = JavaMethod.getNativeVirtual(JavaClass.SHORT, "shortValue", "()S");
   private static final JavaMethod UNBOX_INTEGER = JavaMethod.getNativeVirtual(JavaClass.INTEGER, "intValue", "()I");
   private static final JavaMethod UNBOX_LONG = JavaMethod.getNativeVirtual(JavaClass.LONG, "longValue", "()J");
   private static final JavaMethod UNBOX_FLOAT = JavaMethod.getNativeVirtual(JavaClass.FLOAT, "floatValue", "()F");
   private static final JavaMethod UNBOX_DOUBLE = JavaMethod.getNativeVirtual(JavaClass.DOUBLE, "doubleValue", "()D");
   private static final JavaMethod UNBOX_CHARACTER = JavaMethod.getNativeVirtual(JavaClass.CHARACTER, "charValue", "()C");
   private final JavaWriter writer;

   public JavaUnboxingTypeVisitor(JavaWriter writer) {
      this.writer = writer;
   }

   public Void visitBasic(TypeID context, BasicTypeID basic) throws RuntimeException {
      JavaMethod method;
      switch (basic) {
         case BOOL:
            method = UNBOX_BOOLEAN;
            break;
         case BYTE:
         case SBYTE:
            method = UNBOX_BYTE;
            break;
         case SHORT:
         case USHORT:
            method = UNBOX_SHORT;
            break;
         case INT:
         case UINT:
            method = UNBOX_INTEGER;
            break;
         case LONG:
         case ULONG:
         case USIZE:
            method = UNBOX_LONG;
            break;
         case FLOAT:
            method = UNBOX_FLOAT;
            break;
         case DOUBLE:
            method = UNBOX_DOUBLE;
            break;
         case CHAR:
            method = UNBOX_CHARACTER;
            break;
         case VOID:
         case UNDETERMINED:
         case NULL:
         default:
            return null;
      }

      this.writer.invokeVirtual(method);
      return null;
   }

   public Void visitArray(TypeID context, ArrayTypeID array) throws RuntimeException {
      return null;
   }

   public Void visitAssoc(TypeID context, AssocTypeID assoc) throws RuntimeException {
      return null;
   }

   public Void visitGenericMap(TypeID context, GenericMapTypeID map) throws RuntimeException {
      return null;
   }

   public Void visitIterator(TypeID context, IteratorTypeID iterator) throws RuntimeException {
      return null;
   }

   public Void visitFunction(TypeID context, FunctionTypeID function) throws RuntimeException {
      return null;
   }

   public Void visitDefinition(TypeID context, DefinitionTypeID definition) throws RuntimeException {
      return null;
   }

   public Void visitGeneric(TypeID context, GenericTypeID generic) throws RuntimeException {
      return null;
   }

   public Void visitRange(TypeID context, RangeTypeID range) throws RuntimeException {
      return null;
   }

   public Void visitOptional(TypeID context, OptionalTypeID type) throws RuntimeException {
      return null;
   }
}
