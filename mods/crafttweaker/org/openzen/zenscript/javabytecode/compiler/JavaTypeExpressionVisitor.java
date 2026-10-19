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
import org.openzen.zenscript.codemodel.type.TypeVisitorWithContext;
import org.openzen.zenscript.javabytecode.JavaBytecodeContext;
import org.openzen.zenscript.javashared.JavaClass;
import org.openzen.zenscript.javashared.JavaTypeParameterInfo;

public class JavaTypeExpressionVisitor implements TypeVisitorWithContext<JavaWriter, Void, RuntimeException> {
   private final JavaBytecodeContext context;

   public JavaTypeExpressionVisitor(JavaBytecodeContext context) {
      this.context = context;
   }

   public Void visitBasic(JavaWriter writer, BasicTypeID basic) {
      switch (basic) {
         case BOOL:
            writer.constantClass(JavaClass.BOOLEAN);
            return null;
         case SBYTE:
            writer.constantClass(JavaClass.BYTE);
            return null;
         case SHORT:
            writer.constantClass(JavaClass.SHORT);
            return null;
         case BYTE:
         case USHORT:
         case INT:
         case UINT:
         case USIZE:
            writer.constantClass(JavaClass.INTEGER);
            return null;
         case LONG:
         case ULONG:
            writer.constantClass(JavaClass.LONG);
            return null;
         case FLOAT:
            writer.constantClass(JavaClass.FLOAT);
            return null;
         case DOUBLE:
            writer.constantClass(JavaClass.DOUBLE);
            return null;
         case CHAR:
            writer.constantClass(JavaClass.CHARACTER);
            return null;
         case STRING:
            writer.constantClass(JavaClass.STRING);
            return null;
         default:
            throw new IllegalArgumentException();
      }
   }

   public Void visitArray(JavaWriter writer, ArrayTypeID array) {
      throw new UnsupportedOperationException("Not yet implemented");
   }

   public Void visitAssoc(JavaWriter writer, AssocTypeID assoc) {
      writer.constantClass(JavaClass.MAP);
      return null;
   }

   public Void visitGenericMap(JavaWriter writer, GenericMapTypeID map) {
      writer.constantClass(JavaClass.MAP);
      return null;
   }

   public Void visitIterator(JavaWriter writer, IteratorTypeID iterator) {
      writer.constantClass(JavaClass.ITERATOR);
      return null;
   }

   public Void visitFunction(JavaWriter writer, FunctionTypeID function) {
      writer.constantClass(this.context.getFunction(function).getCls());
      return null;
   }

   public Void visitDefinition(JavaWriter writer, DefinitionTypeID definition) {
      writer.constantClass(this.context.getJavaClass(definition.definition));
      return null;
   }

   public Void visitGeneric(JavaWriter writer, GenericTypeID generic) {
      JavaTypeParameterInfo info = this.context.target.getTypeParameterInfo(generic.parameter);
      if (info.field != null) {
         writer.loadObject(0);
         writer.getField(info.field);
      } else {
         writer.loadObject(info.parameterIndex);
      }

      return null;
   }

   public Void visitRange(JavaWriter writer, RangeTypeID range) {
      writer.constantClass(this.context.getRange(range).cls);
      return null;
   }

   public Void visitOptional(JavaWriter writer, OptionalTypeID type) {
      return type.baseType.accept(writer, this);
   }
}
