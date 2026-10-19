package org.openzen.zenscript.javashared;

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

public class JavaTypeInfo {
   private static final JavaTypeInfo PRIMITIVE = new JavaTypeInfo(true);
   private static final JavaTypeInfo OBJECT = new JavaTypeInfo(false);
   private static final JavaTypeInfo.JavaTypeInfoVisitor VISITOR = new JavaTypeInfo.JavaTypeInfoVisitor();
   public final boolean primitive;

   private JavaTypeInfo(boolean primitive) {
      this.primitive = primitive;
   }

   public static JavaTypeInfo get(TypeID type) {
      return type.accept(type, VISITOR);
   }

   public static boolean isPrimitive(TypeID type) {
      return type.accept(type, VISITOR).primitive;
   }

   private static class JavaTypeInfoVisitor implements TypeVisitorWithContext<TypeID, JavaTypeInfo, RuntimeException> {
      public JavaTypeInfo visitBasic(TypeID context, BasicTypeID basic) {
         return basic != BasicTypeID.NULL && basic != BasicTypeID.STRING ? JavaTypeInfo.PRIMITIVE : JavaTypeInfo.OBJECT;
      }

      public JavaTypeInfo visitArray(TypeID context, ArrayTypeID array) {
         return JavaTypeInfo.OBJECT;
      }

      public JavaTypeInfo visitAssoc(TypeID context, AssocTypeID assoc) {
         return JavaTypeInfo.OBJECT;
      }

      public JavaTypeInfo visitGenericMap(TypeID context, GenericMapTypeID map) {
         return JavaTypeInfo.OBJECT;
      }

      public JavaTypeInfo visitIterator(TypeID context, IteratorTypeID iterator) {
         return JavaTypeInfo.OBJECT;
      }

      public JavaTypeInfo visitFunction(TypeID context, FunctionTypeID function) {
         return JavaTypeInfo.OBJECT;
      }

      public JavaTypeInfo visitDefinition(TypeID context, DefinitionTypeID definition) {
         return JavaTypeInfo.OBJECT;
      }

      public JavaTypeInfo visitGeneric(TypeID context, GenericTypeID generic) {
         return JavaTypeInfo.OBJECT;
      }

      public JavaTypeInfo visitRange(TypeID context, RangeTypeID range) {
         return JavaTypeInfo.OBJECT;
      }

      public JavaTypeInfo visitOptional(TypeID context, OptionalTypeID type) {
         return type.baseType.accept(null, this);
      }
   }
}
