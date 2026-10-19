package org.openzen.zenscript.javabytecode.compiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.LocalVariablesSorter;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.logging.IZSLogger;
import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.codemodel.statement.VariableID;
import org.openzen.zenscript.javabytecode.JavaLocalVariableInfo;
import org.openzen.zenscript.javashared.JavaClass;
import org.openzen.zenscript.javashared.JavaField;
import org.openzen.zenscript.javashared.JavaMethod;
import org.openzen.zenscript.javashared.JavaParameterInfo;

public class JavaWriter {
   private static final JavaMethod STRING_CONCAT = JavaMethod.getNativeStatic(JavaClass.STRING, "concat", "(Ljava/lang/String;)Ljava/lang/String;");
   public final JavaMethod method;
   public final HighLevelDefinition forDefinition;
   public final ClassVisitor clazzVisitor;
   private final IZSLogger logger;
   private final CodePosition position;
   private final LocalVariablesSorter visitor;
   private final List<JavaLocalVariableInfo> localVariableInfos = new ArrayList<>();
   private final Map<VariableID, JavaLocalVariableInfo> localVariables = new HashMap<>();
   private final List<Integer> lineNumberLabels = new ArrayList<>();
   private boolean debug = false;
   private boolean nameVariables;
   private int labelIndex = 1;
   private Map<Label, String> labelNames = new HashMap<>();

   public JavaWriter(
      IZSLogger logger,
      CodePosition position,
      ClassVisitor visitor,
      boolean nameVariables,
      JavaMethod method,
      HighLevelDefinition forDefinition,
      String signature,
      String[] exceptions,
      String... annotations
   ) {
      this(logger, position, visitor, nameVariables, method, forDefinition, false, signature, method.descriptor, exceptions, annotations);
      this.position(position.fromLine);
   }

   public JavaWriter(
      IZSLogger logger,
      CodePosition position,
      ClassVisitor visitor,
      boolean nameVariables,
      JavaMethod method,
      HighLevelDefinition forDefinition,
      boolean isExtension,
      String signature,
      String descriptor,
      String[] exceptions,
      String... annotations
   ) {
      this.logger = logger;
      this.clazzVisitor = visitor;
      this.method = method;
      this.forDefinition = forDefinition;
      this.position = position;
      int access = isExtension ? method.modifiers | 8 : method.modifiers;
      MethodVisitor methodVisitor = visitor.visitMethod(access, method.name, descriptor, signature, exceptions);

      for (String annotation : annotations) {
         methodVisitor.visitAnnotation(annotation, true).visitEnd();
      }

      this.visitor = new LocalVariablesSorter(access, descriptor, methodVisitor);
      this.nameVariables = nameVariables;
   }

   public JavaWriter(
      IZSLogger logger,
      CodePosition position,
      ClassVisitor visitor,
      JavaMethod method,
      HighLevelDefinition forDefinition,
      String signature,
      String[] exceptions,
      String... annotations
   ) {
      this(logger, position, visitor, true, method, forDefinition, signature, exceptions, annotations);
   }

   public void setLocalVariable(VariableID variable, JavaLocalVariableInfo info) {
      this.localVariables.put(variable, info);
   }

   public JavaLocalVariableInfo tryGetLocalVariable(VariableID variable) {
      return this.localVariables.get(variable);
   }

   public JavaLocalVariableInfo getLocalVariable(VariableID variable) {
      JavaLocalVariableInfo result = this.tryGetLocalVariable(variable);
      if (result == null) {
         throw new IllegalStateException("Local variable unknown");
      } else {
         return result;
      }
   }

   public void enableDebug() {
      this.debug = true;
   }

   public LocalVariablesSorter getVisitor() {
      return this.visitor;
   }

   public void start() {
      if (this.debug) {
         this.logger.debug("--start--");
      }

      this.visitor.visitCode();
   }

   public void end() {
      if (this.debug) {
         this.logger.debug("--end--");
      }

      try {
         this.visitor.visitMaxs(0, 0);
      } catch (ArrayIndexOutOfBoundsException | NegativeArraySizeException ex) {
         throw ex;
      }

      if (this.nameVariables) {
         for (JavaLocalVariableInfo info : this.localVariableInfos) {
            this.nameVariable(info.local, info.name, info.start, info.end, info.type);
         }
      }

      this.visitor.visitEnd();
   }

   public void label(Label label) {
      if (this.debug) {
         this.logger.debug("Label " + this.getLabelName(label));
      }

      this.visitor.visitLabel(label);
   }

   public int local(Type type) {
      return this.visitor.newLocal(type);
   }

   public int local(Class cls) {
      return this.visitor.newLocal(Type.getType(cls));
   }

   public void iConst0() {
      if (this.debug) {
         this.logger.debug("iconst0");
      }

      this.visitor.visitInsn(3);
   }

   public void iConst1() {
      if (this.debug) {
         this.logger.debug("iconst1");
      }

      this.visitor.visitInsn(4);
   }

   public void biPush(byte value) {
      if (this.debug) {
         this.logger.debug("bipush");
      }

      this.visitor.visitIntInsn(16, value);
   }

   public void siPush(short value) {
      if (this.debug) {
         this.logger.debug("sipush");
      }

      this.visitor.visitIntInsn(17, value);
   }

   public void aConstNull() {
      if (this.debug) {
         this.logger.debug("aConstNull");
      }

      this.visitor.visitInsn(1);
   }

   public void constant(Object value) {
      if (value == null) {
         throw new NullPointerException("Value cannot be null");
      }

      if (this.debug) {
         this.logger.debug("ldc " + value);
      }

      this.visitor.visitLdcInsn(value);
   }

   public void constant(Class<?> cls) {
      this.constant(Type.getType(cls));
   }

   public void constantClass(JavaClass cls) {
      this.visitor.visitLdcInsn(Type.getObjectType(cls.internalName));
   }

   public void pop() {
      if (this.debug) {
         this.logger.debug("pop");
      }

      this.visitor.visitInsn(87);
   }

   public void pop(boolean large) {
      if (this.debug) {
         this.logger.debug("pop");
      }

      this.visitor.visitInsn(large ? 88 : 87);
   }

   public void pop2() {
      if (this.debug) {
         this.logger.debug("pop2");
      }

      this.visitor.visitInsn(88);
   }

   public void dup() {
      if (this.debug) {
         this.logger.debug("dup");
      }

      this.visitor.visitInsn(89);
   }

   public void dup(Type type) {
      if (this.debug) {
         this.logger.debug("dup");
      }

      this.visitor.visitInsn(type.getSize() == 2 ? 92 : 89);
   }

   public void dup(boolean large) {
      if (this.debug) {
         this.logger.debug("dup");
      }

      this.visitor.visitInsn(large ? 92 : 89);
   }

   public void dup2() {
      if (this.debug) {
         this.logger.debug("dup2");
      }

      this.visitor.visitInsn(92);
   }

   public void dupX1() {
      if (this.debug) {
         this.logger.debug("dupx1");
      }

      this.visitor.visitInsn(90);
   }

   public void dupX1(boolean tosLarge, boolean large) {
      if (this.debug) {
         this.logger.debug("dupx1");
      }

      if (tosLarge) {
         this.visitor.visitInsn(large ? 94 : 91);
      } else {
         this.visitor.visitInsn(large ? 93 : 90);
      }
   }

   public void dupX2() {
      if (this.debug) {
         this.logger.debug("dupx2");
      }

      this.visitor.visitInsn(91);
   }

   public void dup2X1() {
      if (this.debug) {
         this.logger.debug("dup2_x1");
      }

      this.visitor.visitInsn(93);
   }

   public void dup2X2() {
      if (this.debug) {
         this.logger.debug("dup2_x2");
      }

      this.visitor.visitInsn(94);
   }

   public void store(Type type, int local) {
      if (this.debug) {
         this.logger.debug("store " + local);
      }

      this.visitor.visitVarInsn(type.getOpcode(54), local);
   }

   public void load(Type type, int local) {
      if (this.debug) {
         this.logger.debug("load " + local);
      }

      this.visitor.visitVarInsn(type.getOpcode(21), local);
   }

   public void load(JavaParameterInfo parameter) {
      if (this.debug) {
         this.logger.debug("load " + parameter.index);
      }

      this.visitor.visitVarInsn(Type.getType(parameter.typeDescriptor).getOpcode(21), parameter.index);
   }

   public void load(JavaLocalVariableInfo localVariable) {
      if (this.debug) {
         this.logger.debug("load " + localVariable.local);
      }

      this.visitor.visitVarInsn(localVariable.type.getOpcode(21), localVariable.local);
   }

   public void store(JavaParameterInfo parameter) {
      if (this.debug) {
         this.logger.debug("store " + parameter.index);
      }

      this.visitor.visitVarInsn(Type.getType(parameter.typeDescriptor).getOpcode(54), parameter.index);
   }

   public void store(JavaLocalVariableInfo localVariable) {
      if (this.debug) {
         this.logger.debug("store " + localVariable.local);
      }

      this.visitor.visitVarInsn(localVariable.type.getOpcode(54), localVariable.local);
   }

   public void storeInt(int local) {
      if (this.debug) {
         this.logger.debug("storeInt " + local);
      }

      this.visitor.visitVarInsn(54, local);
   }

   public void loadInt(int local) {
      if (this.debug) {
         this.logger.debug("loadInt " + local);
      }

      this.visitor.visitVarInsn(21, local);
   }

   public void storeObject(int local) {
      if (this.debug) {
         this.logger.debug("storeObject " + local);
      }

      this.visitor.visitVarInsn(58, local);
   }

   public void loadObject(int local) {
      if (this.debug) {
         this.logger.debug("loadObject " + local);
      }

      this.visitor.visitVarInsn(25, local);
   }

   public void arrayLength() {
      if (this.debug) {
         this.logger.debug("arrayLength");
      }

      this.visitor.visitInsn(190);
   }

   public void arrayLoad(Type type) {
      if (this.debug) {
         this.logger.debug("arrayLoad");
      }

      this.visitor.visitInsn(type.getOpcode(46));
   }

   public void arrayStore(Type type) {
      if (this.debug) {
         this.logger.debug("arrayStore");
      }

      this.visitor.visitInsn(type.getOpcode(79));
   }

   public void newArray(Type componentType) {
      if (this.debug) {
         this.logger.debug("newArray");
      }

      int sort = componentType.getSort();
      if (sort == 11) {
         throw new RuntimeException("Unsupported array type: " + componentType);
      }

      if (sort != 10 && sort != 9) {
         this.visitor.visitIntInsn(188, switch (sort) {
            case 1 -> 4;
            case 2 -> 5;
            case 3 -> 8;
            case 4 -> 9;
            case 5 -> 10;
            case 6 -> 6;
            case 7 -> 11;
            case 8 -> 7;
            default -> throw new RuntimeException("Unsupported array type: " + componentType);
         });
      } else {
         this.visitor.visitTypeInsn(189, componentType.getInternalName());
      }
   }

   public void checkCast(String internalName) {
      if (this.debug) {
         this.logger.debug("checkCast " + internalName);
      }

      this.visitor.visitTypeInsn(192, internalName);
   }

   public void checkCast(Type type) {
      if (this.debug) {
         this.logger.debug("checkCast " + type.getDescriptor());
      }

      this.visitor.visitTypeInsn(192, type.getInternalName());
   }

   public void iNeg() {
      if (this.debug) {
         this.logger.debug("iNeg");
      }

      this.visitor.visitInsn(116);
   }

   public void iAdd() {
      if (this.debug) {
         this.logger.debug("iAdd");
      }

      this.visitor.visitInsn(96);
   }

   public void iSub() {
      if (this.debug) {
         this.logger.debug("iSub");
      }

      this.visitor.visitInsn(100);
   }

   public void iMul() {
      if (this.debug) {
         this.logger.debug("iMul");
      }

      this.visitor.visitInsn(104);
   }

   public void iDiv() {
      if (this.debug) {
         this.logger.debug("iDiv");
      }

      this.visitor.visitInsn(108);
   }

   public void iRem() {
      if (this.debug) {
         this.logger.debug("iRem");
      }

      this.visitor.visitInsn(112);
   }

   public void iAnd() {
      if (this.debug) {
         this.logger.debug("iAnd");
      }

      this.visitor.visitInsn(126);
   }

   public void iOr() {
      if (this.debug) {
         this.logger.debug("iOr");
      }

      this.visitor.visitInsn(128);
   }

   public void iXor() {
      if (this.debug) {
         this.logger.debug("iXor");
      }

      this.visitor.visitInsn(130);
   }

   public void iNot() {
      if (this.debug) {
         this.logger.debug("iNot");
      }

      this.visitor.visitInsn(2);
      this.visitor.visitInsn(130);
   }

   public void invertBoolean() {
      if (this.debug) {
         this.logger.debug("invert bool");
      }

      Label l1 = new Label();
      Label l2 = new Label();
      this.ifEQ(l1);
      this.iConst0();
      this.goTo(l2);
      this.label(l1);
      this.iConst1();
      this.label(l2);
   }

   public void iShr() {
      if (this.debug) {
         this.logger.debug("iShr");
      }

      this.visitor.visitInsn(122);
   }

   public void iUShr() {
      if (this.debug) {
         this.logger.debug("iUShr");
      }

      this.visitor.visitInsn(124);
   }

   public void iShl() {
      if (this.debug) {
         this.logger.debug("iShl");
      }

      this.visitor.visitInsn(120);
   }

   public void lNeg() {
      if (this.debug) {
         this.logger.debug("lNeg");
      }

      this.visitor.visitInsn(117);
   }

   public void lAdd() {
      if (this.debug) {
         this.logger.debug("lAdd");
      }

      this.visitor.visitInsn(97);
   }

   public void lSub() {
      if (this.debug) {
         this.logger.debug("lSub");
      }

      this.visitor.visitInsn(101);
   }

   public void lMul() {
      if (this.debug) {
         this.logger.debug("lMul");
      }

      this.visitor.visitInsn(105);
   }

   public void lDiv() {
      if (this.debug) {
         this.logger.debug("lDiv");
      }

      this.visitor.visitInsn(109);
   }

   public void lRem() {
      if (this.debug) {
         this.logger.debug("lRem");
      }

      this.visitor.visitInsn(113);
   }

   public void lAnd() {
      if (this.debug) {
         this.logger.debug("lAnd");
      }

      this.visitor.visitInsn(127);
   }

   public void lOr() {
      if (this.debug) {
         this.logger.debug("lOr");
      }

      this.visitor.visitInsn(129);
   }

   public void lXor() {
      if (this.debug) {
         this.logger.debug("lXor");
      }

      this.visitor.visitInsn(131);
   }

   public void lNot() {
      if (this.debug) {
         this.logger.debug("lNot");
      }

      this.constant(-1L);
      this.lXor();
   }

   public void lShr() {
      if (this.debug) {
         this.logger.debug("lShr");
      }

      this.visitor.visitInsn(123);
   }

   public void lUShr() {
      if (this.debug) {
         this.logger.debug("lUShr");
      }

      this.visitor.visitInsn(125);
   }

   public void lShl() {
      if (this.debug) {
         this.logger.debug("lShl");
      }

      this.visitor.visitInsn(121);
   }

   public void fNeg() {
      if (this.debug) {
         this.logger.debug("fNeg");
      }

      this.visitor.visitInsn(118);
   }

   public void fAdd() {
      if (this.debug) {
         this.logger.debug("fAdd");
      }

      this.visitor.visitInsn(98);
   }

   public void fSub() {
      if (this.debug) {
         this.logger.debug("fSub");
      }

      this.visitor.visitInsn(102);
   }

   public void fMul() {
      if (this.debug) {
         this.logger.debug("fMul");
      }

      this.visitor.visitInsn(106);
   }

   public void fDiv() {
      if (this.debug) {
         this.logger.debug("fDiv");
      }

      this.visitor.visitInsn(110);
   }

   public void fRem() {
      if (this.debug) {
         this.logger.debug("fRem");
      }

      this.visitor.visitInsn(114);
   }

   public void dNeg() {
      if (this.debug) {
         this.logger.debug("dNeg");
      }

      this.visitor.visitInsn(119);
   }

   public void dAdd() {
      if (this.debug) {
         this.logger.debug("dAdd");
      }

      this.visitor.visitInsn(99);
   }

   public void dSub() {
      if (this.debug) {
         this.logger.debug("dSub");
      }

      this.visitor.visitInsn(103);
   }

   public void dMul() {
      if (this.debug) {
         this.logger.debug("dMul");
      }

      this.visitor.visitInsn(107);
   }

   public void dDiv() {
      if (this.debug) {
         this.logger.debug("dDiv");
      }

      this.visitor.visitInsn(111);
   }

   public void dRem() {
      if (this.debug) {
         this.logger.debug("dRem");
      }

      this.visitor.visitInsn(115);
   }

   public void iinc(int local) {
      this.iinc(local, 1);
   }

   public void idec(int local) {
      this.iinc(local, -1);
   }

   public void iinc(int local, int increment) {
      if (this.debug) {
         this.logger.debug("iinc " + local + " + " + increment);
      }

      this.visitor.visitIincInsn(local, increment);
   }

   public void i2b() {
      if (this.debug) {
         this.logger.debug("i2b");
      }

      this.visitor.visitInsn(145);
   }

   public void i2s() {
      if (this.debug) {
         this.logger.debug("i2s");
      }

      this.visitor.visitInsn(147);
   }

   public void i2l() {
      if (this.debug) {
         this.logger.debug("i2l");
      }

      this.visitor.visitInsn(133);
   }

   public void i2f() {
      if (this.debug) {
         this.logger.debug("i2f");
      }

      this.visitor.visitInsn(134);
   }

   public void i2d() {
      if (this.debug) {
         this.logger.debug("i2d");
      }

      this.visitor.visitInsn(135);
   }

   public void l2i() {
      if (this.debug) {
         this.logger.debug("l2i");
      }

      this.visitor.visitInsn(136);
   }

   public void l2f() {
      if (this.debug) {
         this.logger.debug("l2f");
      }

      this.visitor.visitInsn(137);
   }

   public void l2d() {
      if (this.debug) {
         this.logger.debug("l2d");
      }

      this.visitor.visitInsn(138);
   }

   public void f2i() {
      if (this.debug) {
         this.logger.debug("f2i");
      }

      this.visitor.visitInsn(139);
   }

   public void f2l() {
      if (this.debug) {
         this.logger.debug("f2l");
      }

      this.visitor.visitInsn(140);
   }

   public void f2d() {
      if (this.debug) {
         this.logger.debug("f2d");
      }

      this.visitor.visitInsn(141);
   }

   public void d2i() {
      if (this.debug) {
         this.logger.debug("d2i");
      }

      this.visitor.visitInsn(142);
   }

   public void d2l() {
      if (this.debug) {
         this.logger.debug("d2l");
      }

      this.visitor.visitInsn(143);
   }

   public void d2f() {
      if (this.debug) {
         this.logger.debug("d2f");
      }

      this.visitor.visitInsn(144);
   }

   public void lCmp() {
      if (this.debug) {
         this.logger.debug("lCmp");
      }

      this.visitor.visitInsn(148);
   }

   public void fCmp() {
      if (this.debug) {
         this.logger.debug("fCmp");
      }

      this.visitor.visitInsn(149);
   }

   public void dCmp() {
      if (this.debug) {
         this.logger.debug("dCmp");
      }

      this.visitor.visitInsn(151);
   }

   public void instanceOf(String descriptor) {
      if (this.debug) {
         this.logger.debug("instanceOf " + descriptor);
      }

      this.visitor.visitTypeInsn(193, descriptor);
   }

   public void instanceOf(Type type) {
      if (this.debug) {
         this.logger.debug("instanceOf " + type.getDescriptor());
      }

      this.visitor.visitTypeInsn(193, type.getDescriptor());
   }

   public void invokeStatic(JavaMethod method) {
      this.visitor.visitMethodInsn(184, method.cls.internalName, method.name, method.descriptor, method.cls.isInterface());
   }

   public void invokeSpecial(String ownerInternalName, String name, String descriptor) {
      if (this.debug) {
         this.logger.debug("invokeSpecial " + ownerInternalName + "." + name + descriptor);
      }

      this.visitor.visitMethodInsn(183, ownerInternalName, name, descriptor, false);
   }

   public void invokeSpecial(Class owner, String name, String descriptor) {
      this.invokeSpecial(Type.getInternalName(owner), name, descriptor);
   }

   public void invokeSpecial(JavaMethod method) {
      this.invokeSpecial(method.cls.internalName, method.name, method.descriptor);
   }

   public void invokeVirtual(JavaMethod method) {
      if (method.kind == JavaMethod.Kind.INTERFACE) {
         this.invokeInterface(method);
      } else {
         if (this.debug) {
            this.logger.debug("invokeVirtual " + method.cls.internalName + "." + method.name + method.descriptor);
         }

         this.visitor.visitMethodInsn(182, method.cls.internalName, method.name, method.descriptor, false);
      }
   }

   public void invokeInterface(JavaMethod method) {
      if (this.debug) {
         this.logger.debug("invokeInterface " + method.cls.internalName + "." + method.name + method.descriptor);
      }

      this.visitor.visitMethodInsn(185, method.cls.internalName, method.name, method.descriptor, true);
   }

   public void newObject(String internalName) {
      if (this.debug) {
         this.logger.debug("newObject " + internalName);
      }

      this.visitor.visitTypeInsn(187, internalName);
   }

   public void newObject(JavaClass cls) {
      if (this.debug) {
         this.logger.debug("newObject " + cls.internalName);
      }

      this.visitor.visitTypeInsn(187, cls.internalName);
   }

   public void goTo(Label lbl) {
      if (this.debug) {
         this.logger.debug("goTo " + this.getLabelName(lbl));
      }

      this.visitor.visitJumpInsn(167, lbl);
   }

   public void ifEQ(Label lbl) {
      if (this.debug) {
         this.logger.debug("ifEQ " + this.getLabelName(lbl));
      }

      this.visitor.visitJumpInsn(153, lbl);
   }

   public void ifNE(Label lbl) {
      if (this.debug) {
         this.logger.debug("ifNE " + this.getLabelName(lbl));
      }

      this.visitor.visitJumpInsn(154, lbl);
   }

   public void ifLT(Label lbl) {
      if (this.debug) {
         this.logger.debug("ifLT " + this.getLabelName(lbl));
      }

      this.visitor.visitJumpInsn(155, lbl);
   }

   public void ifGT(Label lbl) {
      if (this.debug) {
         this.logger.debug("ifGT " + this.getLabelName(lbl));
      }

      this.visitor.visitJumpInsn(157, lbl);
   }

   public void ifGE(Label lbl) {
      if (this.debug) {
         this.logger.debug("ifGE " + this.getLabelName(lbl));
      }

      this.visitor.visitJumpInsn(156, lbl);
   }

   public void ifLE(Label lbl) {
      if (this.debug) {
         this.logger.debug("ifLE " + this.getLabelName(lbl));
      }

      this.visitor.visitJumpInsn(158, lbl);
   }

   public void ifICmpLE(Label lbl) {
      if (this.debug) {
         this.logger.debug("ifICmpLE " + this.getLabelName(lbl));
      }

      this.visitor.visitJumpInsn(164, lbl);
   }

   public void ifICmpGE(Label lbl) {
      if (this.debug) {
         this.logger.debug("ifICmpGE " + this.getLabelName(lbl));
      }

      this.visitor.visitJumpInsn(162, lbl);
   }

   public void ifICmpEQ(Label lbl) {
      if (this.debug) {
         this.logger.debug("ifICmpEQ " + this.getLabelName(lbl));
      }

      this.visitor.visitJumpInsn(159, lbl);
   }

   public void ifICmpNE(Label lbl) {
      if (this.debug) {
         this.logger.debug("ifICmpNE " + this.getLabelName(lbl));
      }

      this.visitor.visitJumpInsn(160, lbl);
   }

   public void ifICmpGT(Label lbl) {
      if (this.debug) {
         this.logger.debug("ifICmpGT " + this.getLabelName(lbl));
      }

      this.visitor.visitJumpInsn(163, lbl);
   }

   public void ifICmpLT(Label lbl) {
      if (this.debug) {
         this.logger.debug("ifICmpLT " + this.getLabelName(lbl));
      }

      this.visitor.visitJumpInsn(161, lbl);
   }

   public void ifACmpEq(Label lbl) {
      if (this.debug) {
         this.logger.debug("ifICmpEQ " + this.getLabelName(lbl));
      }

      this.visitor.visitJumpInsn(165, lbl);
   }

   public void ifACmpNe(Label lbl) {
      if (this.debug) {
         this.logger.debug("ifACmpNE " + this.getLabelName(lbl));
      }

      this.visitor.visitJumpInsn(166, lbl);
   }

   public void ifNull(Label lbl) {
      if (this.debug) {
         this.logger.debug("ifNull " + this.getLabelName(lbl));
      }

      this.visitor.visitJumpInsn(198, lbl);
   }

   public void ifNonNull(Label lbl) {
      if (this.debug) {
         this.logger.debug("ifNonNull " + this.getLabelName(lbl));
      }

      this.visitor.visitJumpInsn(199, lbl);
   }

   public void ret() {
      if (this.debug) {
         this.logger.debug("ret");
      }

      this.visitor.visitInsn(177);
   }

   public void returnType(Type type) {
      if (this.debug) {
         this.logger.debug("return " + type.getDescriptor());
      }

      this.visitor.visitInsn(type.getOpcode(172));
   }

   public void returnInt() {
      if (this.debug) {
         this.logger.debug("ireturn");
      }

      this.visitor.visitInsn(172);
   }

   public void returnObject() {
      if (this.debug) {
         this.logger.debug("areturn");
      }

      this.visitor.visitInsn(176);
   }

   public void getField(String owner, String name, String descriptor) {
      if (this.debug) {
         this.logger.debug("getField " + owner + "." + name + ":" + descriptor);
      }

      this.visitor.visitFieldInsn(180, owner, name, descriptor);
   }

   public void getField(JavaField field) {
      if (this.debug) {
         this.logger.debug("getField " + field.cls.internalName + "." + field.name + ":" + field.descriptor);
      }

      this.visitor.visitFieldInsn(180, field.cls.internalName, field.name, field.descriptor);
   }

   public void putField(String owner, String name, String descriptor) {
      if (this.debug) {
         this.logger.debug("putField " + owner + "." + name + ":" + descriptor);
      }

      this.visitor.visitFieldInsn(181, owner, name, descriptor);
   }

   public void putField(JavaField field) {
      if (this.debug) {
         this.logger.debug("putField " + field.cls.internalName + "." + field.name + ":" + field.descriptor);
      }

      this.visitor.visitFieldInsn(181, field.cls.internalName, field.name, field.descriptor);
   }

   public void getStaticField(String owner, String name, String descriptor) {
      if (this.debug) {
         this.logger.debug("getStatic " + owner + "." + name + ":" + descriptor);
      }

      this.visitor.visitFieldInsn(178, owner, name, descriptor);
   }

   public void getStaticField(JavaField field) {
      if (this.debug) {
         this.logger.debug("getStaticField " + field.cls.internalName + "." + field.name + ":" + field.descriptor);
      }

      this.visitor.visitFieldInsn(178, field.cls.internalName, field.name, field.descriptor);
   }

   public void putStaticField(String owner, String name, String descriptor) {
      if (this.debug) {
         this.logger.debug("putStatic " + owner + "." + name + ":" + descriptor);
      }

      this.visitor.visitFieldInsn(179, owner, name, descriptor);
   }

   public void putStaticField(JavaField field) {
      if (this.debug) {
         this.logger.debug("putStaticField " + field.cls.internalName + "." + field.name + ":" + field.descriptor);
      }

      this.visitor.visitFieldInsn(179, field.cls.internalName, field.name, field.descriptor);
   }

   public void aThrow() {
      this.visitor.visitInsn(191);
   }

   public void position(int position) {
      if (!this.lineNumberLabels.contains(position)) {
         Label label = new Label();
         this.visitor.visitLabel(label);
         this.visitor.visitLineNumber(position, label);
         this.lineNumberLabels.add(position);
      }
   }

   public void swap() {
      if (this.debug) {
         this.logger.debug("swap");
      }

      this.visitor.visitInsn(95);
   }

   private String getLabelName(Label lbl) {
      if (this.labelNames == null) {
         this.labelNames = new HashMap<>();
      }

      if (!this.labelNames.containsKey(lbl)) {
         this.labelNames.put(lbl, "L" + this.labelIndex++);
      }

      return this.labelNames.get(lbl);
   }

   public String createLabelName() {
      return "L" + this.labelIndex++;
   }

   public void putNamedLabel(Label lbl, String name) {
      if (this.labelNames == null) {
         this.labelNames = new HashMap<>();
      }

      this.labelNames.put(lbl, name);
   }

   public void stringAdd() {
      this.invokeVirtual(STRING_CONCAT);
   }

   public Label getNamedLabel(String label) {
      for (Entry<Label, String> entry : this.labelNames.entrySet()) {
         if (entry.getValue().matches(label)) {
            return entry.getKey();
         }
      }

      throw new RuntimeException("Label " + label + " not found!");
   }

   public void tryCatch(Label start, Label end, Label handler, String type) {
      if (this.debug) {
         this.logger.debug("TryCatch " + this.getLabelName(start) + ", " + this.getLabelName(end) + ", " + this.getLabelName(handler) + ", TYPE: " + type);
      }

      this.visitor.visitTryCatchBlock(start, end, handler, type);
   }

   public void nameVariable(int local, String name, Label start, Label end, Type type) {
      if (this.nameVariables && name != null && type != null && end != null && end != start) {
         this.visitor.visitLocalVariable(name, type.getDescriptor(), null, start, end, local);
      }
   }

   public void nameParameter(int modifier, String name) {
      if (this.nameVariables) {
         this.visitor.visitParameter(name, modifier);
      }
   }

   public void lookupSwitch(Label defaultLabel, JavaSwitchLabel[] switchLabels) {
      int[] keys = new int[switchLabels.length];
      Label[] labels = new Label[switchLabels.length];

      for (int i = 0; i < switchLabels.length; i++) {
         keys[i] = switchLabels[i].key;
         labels[i] = switchLabels[i].label;
      }

      if (this.debug) {
         this.logger.debug("lookupSwitch");

         for (int i = 0; i < switchLabels.length; i++) {
            this.logger.debug("  " + keys[i] + " -> " + this.getLabelName(labels[i]));
         }

         this.logger.debug("  default -> " + this.getLabelName(defaultLabel));
      }

      this.visitor.visitLookupSwitchInsn(defaultLabel, keys, labels);
   }

   public void addVariableInfo(JavaLocalVariableInfo info) {
      this.localVariableInfos.add(info);
   }
}
