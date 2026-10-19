package org.openzen.zenscript.javabytecode;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.logging.IZSLogger;
import org.openzen.zenscript.codemodel.definition.ZSPackage;
import org.openzen.zenscript.codemodel.type.TypeID;
import org.openzen.zenscript.javabytecode.compiler.JavaWriter;
import org.openzen.zenscript.javashared.JavaCompileSpace;
import org.openzen.zenscript.javashared.JavaContext;
import org.openzen.zenscript.javashared.JavaMethod;
import org.openzen.zenscript.javashared.JavaSynthesizedFunction;
import org.openzen.zenscript.javashared.JavaSynthesizedRange;
import org.openzen.zenscript.javashared.JavaSyntheticClassGenerator;
import org.openzen.zenscript.javashared.JavaTypeDescriptorVisitor;
import org.openzen.zenscript.javashared.JavaTypeGenericVisitor;
import org.openzen.zenscript.javashared.JavaTypeInternalNameVisitor;

public class JavaBytecodeContext extends JavaContext {
   public final JavaBytecodeModule target;
   private final JavaBytecodeContext.TypeGenerator typeGenerator;
   private final JavaTypeInternalNameVisitor internalNameVisitor;
   private final JavaTypeDescriptorVisitor descriptorVisitor;
   private int lambdaCounter = 0;

   public JavaBytecodeContext(JavaBytecodeModule target, JavaCompileSpace space, ZSPackage modulePackage, String basePackage, IZSLogger logger) {
      super(space, modulePackage, basePackage, logger);
      this.target = target;
      this.typeGenerator = new JavaBytecodeContext.TypeGenerator();
      this.internalNameVisitor = new JavaTypeInternalNameVisitor(this);
      this.descriptorVisitor = new JavaTypeDescriptorVisitor(this);
   }

   @Override
   protected JavaSyntheticClassGenerator getTypeGenerator() {
      return this.typeGenerator;
   }

   @Override
   public String getDescriptor(TypeID type) {
      return type.accept(this.descriptorVisitor);
   }

   public String getInternalName(TypeID type) {
      return type.accept(this.internalNameVisitor);
   }

   public Type getType(TypeID type) {
      return Type.getType(this.getDescriptor(type));
   }

   public void register(String name, byte[] bytecode) {
      this.target.addClass(name, bytecode);
   }

   private void createLambdaInterface(JavaSynthesizedFunction function) {
      String signature = "<" + new JavaTypeGenericVisitor(this).getGenericSignature(function.typeParameters) + ">Ljava/lang/Object;";
      ClassWriter ifaceWriter = new ClassWriter(2);
      ifaceWriter.visitAnnotation("Ljava/lang/FunctionalInterface;", true).visitEnd();
      ifaceWriter.visit(52, 1545, function.cls.internalName, signature, "java/lang/Object", null);
      ifaceWriter.visitMethod(1025, function.method, this.getMethodDescriptor(function.header), this.getMethodSignature(function.header), null).visitEnd();
      this.register(function.cls.internalName, ifaceWriter.toByteArray());
   }

   private void createRangeClass(JavaSynthesizedRange range) {
      ClassWriter rangeWriter = new ClassWriter(2);
      rangeWriter.visit(52, 1, range.cls.internalName, null, "java/lang/Object", null);
      rangeWriter.visitField(17, "from", this.getDescriptor(range.baseType), null, null).visitEnd();
      rangeWriter.visitField(17, "to", this.getDescriptor(range.baseType), null, null).visitEnd();
      JavaMethod method = JavaMethod.getConstructor(range.cls, "(" + this.getDescriptor(range.baseType) + this.getDescriptor(range.baseType) + ")V", 1);
      JavaWriter constructorWriter = new JavaWriter(this.logger, CodePosition.GENERATED, rangeWriter, method, null, method.descriptor, null);
      constructorWriter.loadObject(0);
      constructorWriter.invokeSpecial("java/lang/Object", "<init>", "()V");
      constructorWriter.loadObject(0);
      constructorWriter.load(this.getType(range.baseType), 1);
      constructorWriter.putField(range.cls.internalName, "from", this.getDescriptor(range.baseType));
      constructorWriter.loadObject(0);
      constructorWriter.load(this.getType(range.baseType), 2);
      constructorWriter.putField(range.cls.internalName, "to", this.getDescriptor(range.baseType));
      constructorWriter.ret();
      constructorWriter.end();
      rangeWriter.visitEnd();
      this.register(range.cls.internalName, rangeWriter.toByteArray());
   }

   private void createSharedClass() {
   }

   public int getLambdaCounter() {
      return ++this.lambdaCounter;
   }

   private class TypeGenerator implements JavaSyntheticClassGenerator {
      @Override
      public void synthesizeFunction(JavaSynthesizedFunction function) {
         JavaBytecodeContext.this.createLambdaInterface(function);
      }

      @Override
      public void synthesizeRange(JavaSynthesizedRange range) {
         JavaBytecodeContext.this.createRangeClass(range);
      }
   }
}
