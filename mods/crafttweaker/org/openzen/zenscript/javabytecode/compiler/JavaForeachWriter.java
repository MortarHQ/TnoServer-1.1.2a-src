package org.openzen.zenscript.javabytecode.compiler;

import java.util.Map.Entry;
import org.objectweb.asm.Label;
import org.objectweb.asm.Type;
import org.openzen.zenscript.codemodel.statement.ForeachStatement;
import org.openzen.zenscript.codemodel.type.ArrayTypeID;
import org.openzen.zenscript.codemodel.type.BasicTypeID;
import org.openzen.zenscript.codemodel.type.OptionalTypeID;
import org.openzen.zenscript.codemodel.type.RangeTypeID;
import org.openzen.zenscript.codemodel.type.TypeID;
import org.openzen.zenscript.javabytecode.JavaLocalVariableInfo;
import org.openzen.zenscript.javashared.JavaClass;
import org.openzen.zenscript.javashared.JavaMethod;

public class JavaForeachWriter {
   private final JavaWriter javaWriter;
   private final ForeachStatement statement;
   private final Label startLabel;
   private final Label endLabel;
   private final JavaStatementVisitor statementVisitor;
   private final JavaUnboxingTypeVisitor unboxingTypeVisitor;

   public JavaForeachWriter(JavaStatementVisitor statementVisitor, ForeachStatement statement, Label start, Label end) {
      this.statementVisitor = statementVisitor;
      this.javaWriter = statementVisitor.getJavaWriter();
      this.statement = statement;
      this.startLabel = start;
      this.endLabel = end;
      this.unboxingTypeVisitor = new JavaUnboxingTypeVisitor(this.javaWriter);
   }

   public void visitIntRange(RangeTypeID type) {
      String owner = this.statementVisitor.context.getInternalName(type);
      this.javaWriter.dup();
      this.javaWriter.getField(owner, "to", "I");
      this.javaWriter.swap();
      this.javaWriter.getField(owner, "from", "I");
      int z = this.javaWriter.getLocalVariable(this.statement.loopVariables[0].variable).local;
      this.javaWriter.storeInt(z);
      this.javaWriter.label(this.startLabel);
      this.javaWriter.dup();
      this.javaWriter.loadInt(z);
      this.javaWriter.ifICmpLE(this.endLabel);
      this.statement.content.accept(this.statementVisitor);
      this.javaWriter.iinc(z);
   }

   public void visitArrayValueIterator() {
      this.handleArray(this.javaWriter.local(int.class), this.javaWriter.getLocalVariable(this.statement.loopVariables[0].variable));
   }

   public void visitArrayKeyValueIterator() {
      this.handleArray(
         this.javaWriter.getLocalVariable(this.statement.loopVariables[0].variable).local,
         this.javaWriter.getLocalVariable(this.statement.loopVariables[1].variable)
      );
   }

   public void visitStringCharacterIterator() {
      this.javaWriter.invokeVirtual(JavaMethod.getVirtual(JavaClass.STRING, "toCharArray", "()[C", 1));
      this.handleArray(this.javaWriter.local(int.class), this.javaWriter.getLocalVariable(this.statement.loopVariables[0].variable));
   }

   public void visitIteratorIterator(Type targetType) {
      this.javaWriter.invokeInterface(JavaMethod.getVirtual(JavaClass.ITERABLE, "iterator", "()Ljava/lang/Iterator;", 0));
      this.javaWriter.label(this.startLabel);
      this.javaWriter.dup();
      this.javaWriter.invokeInterface(JavaMethod.getVirtual(JavaClass.ITERATOR, "hasNext", "()Z", 0));
      this.javaWriter.ifEQ(this.endLabel);
      this.javaWriter.invokeInterface(JavaMethod.getVirtual(JavaClass.ITERATOR, "next", "()Ljava/lang/Object;", 0, true));
      this.javaWriter.checkCast(targetType);
      JavaLocalVariableInfo variable = this.javaWriter.getLocalVariable(this.statement.loopVariables[0].variable);
      this.javaWriter.store(variable.type, variable.local);
      this.statement.content.accept(this.statementVisitor);
   }

   private void handleArray(int z, JavaLocalVariableInfo arrayTypeInfo) {
      this.javaWriter.iConst0();
      this.javaWriter.storeInt(z);
      this.javaWriter.label(this.startLabel);
      this.javaWriter.dup();
      this.javaWriter.arrayLength();
      this.javaWriter.loadInt(z);
      this.javaWriter.ifICmpLE(this.endLabel);
      this.javaWriter.dup();
      this.javaWriter.loadInt(z);
      ArrayTypeID listType = (ArrayTypeID)this.statement.list.type;
      if (listType.elementType == BasicTypeID.BYTE) {
         this.javaWriter.arrayLoad(Type.BYTE_TYPE);
         this.javaWriter.siPush((short)255);
         this.javaWriter.iAnd();
      } else if (listType.elementType == BasicTypeID.USHORT) {
         this.javaWriter.arrayLoad(Type.SHORT_TYPE);
         this.javaWriter.constant(65535);
         this.javaWriter.iAnd();
      } else {
         this.javaWriter.arrayLoad(arrayTypeInfo.type);
      }

      this.javaWriter.store(arrayTypeInfo.type, arrayTypeInfo.local);
      this.statement.content.accept(this.statementVisitor);
      this.javaWriter.iinc(z);
   }

   public void visitCustomIterator() {
      this.javaWriter
         .invokeInterface(JavaMethod.getVirtual(new JavaClass("java.lang", "Iterable", JavaClass.Kind.INTERFACE), "iterator", "()Ljava/util/Iterator;", 0));
      this.javaWriter.label(this.startLabel);
      this.javaWriter.dup();
      this.javaWriter.invokeInterface(JavaMethod.getVirtual(JavaClass.ITERATOR, "hasNext", "()Z", 0));
      this.javaWriter.ifEQ(this.endLabel);
      this.javaWriter.dup();
      this.javaWriter.invokeInterface(JavaMethod.getVirtual(JavaClass.ITERATOR, "next", "()Ljava/lang/Object;", 0, true));
      JavaLocalVariableInfo keyVariable = this.javaWriter.getLocalVariable(this.statement.loopVariables[0].variable);
      this.downCast(0, keyVariable.type);
      this.javaWriter.store(keyVariable.type, keyVariable.local);
      this.statement.content.accept(this.statementVisitor);
   }

   public void visitAssocKeyIterator() {
      this.javaWriter.invokeInterface(JavaMethod.getVirtual(JavaClass.MAP, "keySet", "()Ljava/util/Set;", 0));
      this.javaWriter.invokeInterface(JavaMethod.getVirtual(JavaClass.COLLECTION, "iterator", "()Ljava/util/Iterator;", 0));
      this.javaWriter.label(this.startLabel);
      this.javaWriter.dup();
      this.javaWriter.invokeInterface(JavaMethod.getVirtual(JavaClass.ITERATOR, "hasNext", "()Z", 0));
      this.javaWriter.ifEQ(this.endLabel);
      this.javaWriter.dup();
      this.javaWriter.invokeInterface(JavaMethod.getVirtual(JavaClass.ITERATOR, "next", "()Ljava/lang/Object;", 0, true));
      JavaLocalVariableInfo keyVariable = this.javaWriter.getLocalVariable(this.statement.loopVariables[0].variable);
      this.downCast(0, keyVariable.type);
      this.javaWriter.store(keyVariable.type, keyVariable.local);
      this.statement.content.accept(this.statementVisitor);
   }

   public void visitAssocKeyValueIterator() {
      this.javaWriter.invokeInterface(JavaMethod.getVirtual(JavaClass.MAP, "entrySet", "()Ljava/util/Set;", 0));
      this.javaWriter.invokeInterface(JavaMethod.getVirtual(JavaClass.COLLECTION, "iterator", "()Ljava/util/Iterator;", 0));
      this.javaWriter.label(this.startLabel);
      this.javaWriter.dup();
      this.javaWriter.invokeInterface(JavaMethod.getVirtual(JavaClass.ITERATOR, "hasNext", "()Z", 0));
      this.javaWriter.ifEQ(this.endLabel);
      this.javaWriter.dup();
      this.javaWriter.invokeInterface(JavaMethod.getVirtual(JavaClass.ITERATOR, "next", "()Ljava/lang/Object;", 0, true));
      this.javaWriter.checkCast(Type.getType(Entry.class));
      this.javaWriter.dup(false);
      JavaLocalVariableInfo keyVariable = this.javaWriter.getLocalVariable(this.statement.loopVariables[0].variable);
      JavaLocalVariableInfo valueVariable = this.javaWriter.getLocalVariable(this.statement.loopVariables[1].variable);
      this.javaWriter
         .invokeInterface(
            JavaMethod.getVirtual(JavaClass.fromInternalName("java/util/Map$Entry", JavaClass.Kind.INTERFACE), "getKey", "()Ljava/lang/Object;", 0, true)
         );
      this.downCast(0, keyVariable.type);
      this.javaWriter.store(keyVariable.type, keyVariable.local);
      this.javaWriter
         .invokeInterface(
            JavaMethod.getVirtual(JavaClass.fromInternalName("java/util/Map$Entry", JavaClass.Kind.INTERFACE), "getValue", "()Ljava/lang/Object;", 0, true)
         );
      this.downCast(1, valueVariable.type);
      this.javaWriter.store(valueVariable.type, valueVariable.local);
      this.statement.content.accept(this.statementVisitor);
   }

   private void downCast(int typeNumber, Type t) {
      TypeID type = this.statement.loopVariables[typeNumber].type;
      if (CompilerUtils.isPrimitive(type)) {
         this.javaWriter.checkCast(this.statementVisitor.context.getInternalName(new OptionalTypeID(null, type)));
         type.accept(type, this.unboxingTypeVisitor);
      } else {
         this.javaWriter.checkCast(t);
      }
   }
}
