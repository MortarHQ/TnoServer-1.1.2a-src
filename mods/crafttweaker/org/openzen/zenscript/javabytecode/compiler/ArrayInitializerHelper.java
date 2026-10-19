package org.openzen.zenscript.javabytecode.compiler;

import java.util.ArrayList;
import org.objectweb.asm.Label;
import org.objectweb.asm.Type;
import org.openzen.zenscript.codemodel.expression.Expression;
import org.openzen.zenscript.codemodel.expression.FunctionExpression;
import org.openzen.zenscript.codemodel.statement.ReturnStatement;

class ArrayInitializerHelper {
   static int[] getArraySizeLocationsProjected(int dimension, Type originArrayType, int originArrayLocation, JavaWriter javaWriter) {
      ArrayList<Integer> list = new ArrayList<>();
      javaWriter.loadObject(originArrayLocation);
      Type currentElementType = originArrayType;

      for (int i = 0; i < dimension; i++) {
         currentElementType = Type.getType(currentElementType.getDescriptor().substring(1));
         int location = javaWriter.local(int.class);
         javaWriter.dup();
         javaWriter.arrayLength();
         javaWriter.storeInt(location);
         list.add(location);
         if (i < dimension - 1) {
            javaWriter.iConst0();
            javaWriter.arrayLoad(currentElementType);
         }
      }

      javaWriter.pop();
      int[] arraySizes = new int[list.size()];

      for (int i = 0; i < list.size(); i++) {
         arraySizes[i] = list.get(i);
      }

      return arraySizes;
   }

   static int[] getArraySizeLocationsFromConstructor(int dimension, Expression[] arguments, JavaExpressionVisitor visitor) {
      ArrayList<Integer> list = new ArrayList<>();

      for (int i = 0; i < dimension; i++) {
         int location = visitor.javaWriter.local(int.class);
         arguments[i].accept(visitor);
         visitor.javaWriter.storeInt(location);
         list.add(location);
      }

      int[] arraySizes = new int[list.size()];

      for (int i = 0; i < list.size(); i++) {
         arraySizes[i] = list.get(i);
      }

      return arraySizes;
   }

   static void visitProjected(
      JavaWriter javaWriter,
      int[] sizeLocations,
      int dim,
      int originArrayLocation,
      Type originArrayType,
      ArrayHelperType currentArrayType,
      ArrayInitializerHelper.InnermostFunction innermostFunction
   ) {
      visitMultiDimArray(javaWriter, sizeLocations, dim, currentArrayType, (elementType, counterLocations) -> {
         javaWriter.loadObject(originArrayLocation);
         Type modifiedOriginArrayType = originArrayType;

         for (int location : counterLocations) {
            javaWriter.loadInt(location);
            javaWriter.arrayLoad(modifiedOriginArrayType = Type.getType(modifiedOriginArrayType.getDescriptor().substring(1)));
         }

         innermostFunction.apply(elementType, counterLocations);
      });
   }

   static void visitMultiDimArrayWithDefaultValue(JavaWriter javaWriter, int[] sizeLocations, int dim, ArrayHelperType currentArrayType, int defaultLocation) {
      visitMultiDimArray(
         javaWriter,
         sizeLocations,
         new int[dim],
         dim,
         currentArrayType,
         (elementType, counterLocations) -> javaWriter.load(elementType.getASMElementType(), defaultLocation)
      );
   }

   private static void visitMultiDimArray(
      JavaWriter javaWriter, int[] sizeLocations, int dim, ArrayHelperType currentArrayType, ArrayInitializerHelper.InnermostFunction innermostFunction
   ) {
      visitMultiDimArray(javaWriter, sizeLocations, new int[dim], dim, currentArrayType, innermostFunction);
   }

   static boolean canBeInLined(Expression expression) {
      return expression instanceof FunctionExpression && ((FunctionExpression)expression).body instanceof ReturnStatement;
   }

   static void visitMultiDimArray(
      JavaWriter javaWriter,
      int[] sizeLocations,
      int[] counterLocations,
      int dim,
      ArrayHelperType currentArrayType,
      ArrayInitializerHelper.InnermostFunction innermostFunction
   ) {
      Label begin = new Label();
      Label end = new Label();
      javaWriter.label(begin);
      int currentArraySizeLocation = sizeLocations[sizeLocations.length - dim];
      javaWriter.loadInt(currentArraySizeLocation);
      ArrayHelperType elementType = currentArrayType.getWithOneDimensionLess();
      elementType.newArray(javaWriter);
      int forLoopCounter = javaWriter.local(int.class);
      javaWriter.iConst0();
      javaWriter.storeInt(forLoopCounter);
      counterLocations[counterLocations.length - dim] = forLoopCounter;
      Label loopStart = new Label();
      Label loopEnd = new Label();
      javaWriter.label(loopStart);
      javaWriter.loadInt(forLoopCounter);
      javaWriter.loadInt(currentArraySizeLocation);
      javaWriter.ifICmpGE(loopEnd);
      javaWriter.dup();
      javaWriter.loadInt(forLoopCounter);
      if (dim == 1) {
         innermostFunction.apply(elementType, counterLocations);
      } else {
         visitMultiDimArray(javaWriter, sizeLocations, counterLocations, dim - 1, elementType, innermostFunction);
      }

      javaWriter.arrayStore(elementType.getASMElementType());
      javaWriter.iinc(forLoopCounter);
      javaWriter.goTo(loopStart);
      javaWriter.label(loopEnd);
      javaWriter.nameVariable(forLoopCounter, "i" + dim, loopStart, loopEnd, Type.getType(int.class));
      javaWriter.label(end);

      for (int i = 0; i < sizeLocations.length; i++) {
         javaWriter.nameVariable(sizeLocations[i], "size" + (sizeLocations.length - i), begin, end, Type.getType(int.class));
      }
   }

   @FunctionalInterface
   public interface InnermostFunction {
      void apply(ArrayHelperType var1, int[] var2);
   }
}
