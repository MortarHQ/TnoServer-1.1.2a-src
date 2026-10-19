package org.openzen.zenscript.javabytecode.compiler.definitions;

import java.util.ArrayList;
import java.util.stream.Collectors;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.Type;
import org.openzen.zenscript.codemodel.FunctionParameter;
import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.codemodel.annotations.NativeTag;
import org.openzen.zenscript.codemodel.generic.TypeParameter;
import org.openzen.zenscript.codemodel.member.CallerMember;
import org.openzen.zenscript.codemodel.member.CasterMember;
import org.openzen.zenscript.codemodel.member.ConstMember;
import org.openzen.zenscript.codemodel.member.ConstructorMember;
import org.openzen.zenscript.codemodel.member.DestructorMember;
import org.openzen.zenscript.codemodel.member.FieldMember;
import org.openzen.zenscript.codemodel.member.GetterMember;
import org.openzen.zenscript.codemodel.member.ImplementationMember;
import org.openzen.zenscript.codemodel.member.InnerDefinitionMember;
import org.openzen.zenscript.codemodel.member.IteratorMember;
import org.openzen.zenscript.codemodel.member.MemberVisitor;
import org.openzen.zenscript.codemodel.member.MethodMember;
import org.openzen.zenscript.codemodel.member.OperatorMember;
import org.openzen.zenscript.codemodel.member.SetterMember;
import org.openzen.zenscript.codemodel.member.StaticInitializerMember;
import org.openzen.zenscript.codemodel.type.TypeID;
import org.openzen.zenscript.javabytecode.JavaBytecodeContext;
import org.openzen.zenscript.javabytecode.compiler.CompilerUtils;
import org.openzen.zenscript.javabytecode.compiler.JavaStatementVisitor;
import org.openzen.zenscript.javabytecode.compiler.JavaWriter;
import org.openzen.zenscript.javashared.JavaCompiledModule;
import org.openzen.zenscript.javashared.JavaField;
import org.openzen.zenscript.javashared.JavaMethod;
import org.openzen.zenscript.javashared.JavaParameterInfo;

public class JavaExpansionMemberVisitor implements MemberVisitor<Void> {
   private final ClassWriter writer;
   private final JavaBytecodeContext context;
   private final TypeID expandedClass;
   private final HighLevelDefinition definition;
   private final JavaCompiledModule javaModule;
   private final JavaStatementVisitor clinitStatementVisitor;

   public JavaExpansionMemberVisitor(JavaBytecodeContext context, ClassWriter writer, TypeID expandedClass, HighLevelDefinition definition) {
      this.writer = writer;
      this.expandedClass = expandedClass;
      this.definition = definition;
      this.context = context;
      this.javaModule = context.getJavaModule(definition.module);
      JavaWriter javaWriter = new JavaWriter(
         context.logger,
         definition.position,
         writer,
         new JavaMethod(context.getJavaClass(definition), JavaMethod.Kind.STATICINIT, "<clinit>", true, "()V", 8, false),
         definition,
         null,
         null
      );
      this.clinitStatementVisitor = new JavaStatementVisitor(context, this.javaModule, javaWriter);
      this.clinitStatementVisitor.start();
      CompilerUtils.writeDefaultFieldInitializers(context, javaWriter, definition, true);
   }

   public void end() {
      this.clinitStatementVisitor.end();
   }

   public Void visitConst(ConstMember member) {
      JavaField field = this.context.getJavaField(member);
      this.writer.visitField(CompilerUtils.calcAccess(member.getEffectiveModifiers()), field.name, field.descriptor, field.signature, null).visitEnd();
      return null;
   }

   public Void visitField(FieldMember member) {
      if (!member.isStatic()) {
         throw new IllegalStateException("Cannot add fields via expansions");
      }

      JavaField field = this.context.getJavaField(member);
      this.writer.visitField(CompilerUtils.calcAccess(member.getEffectiveModifiers()), field.name, field.descriptor, field.signature, null).visitEnd();
      return null;
   }

   public Void visitConstructor(ConstructorMember member) {
      throw new IllegalStateException("Cannot add constructors via expansions");
   }

   public Void visitDestructor(DestructorMember member) {
      throw new IllegalStateException("Cannot add constructors via expansions");
   }

   public Void visitMethod(MethodMember member) {
      boolean isStatic = member.isStatic();
      JavaMethod method = this.context.getJavaMethod(member);
      if (!method.compile) {
         return null;
      }

      if (member.body == null && member.hasTag(NativeTag.class)) {
         return null;
      }

      ArrayList<TypeParameter> typeParameters = new ArrayList<>();
      this.expandedClass.extractTypeParameters(typeParameters);
      CompilerUtils.tagMethodParameters(this.context, this.javaModule, member.header, member.isStatic(), typeParameters);
      String expandedClassDescriptor = this.context.getDescriptor(this.expandedClass);
      String expandedClassSignature = this.context.getSignature(this.expandedClass);
      Label methodStart = new Label();
      Label methodEnd = new Label();
      String methodSignature;
      String methodDescriptor;
      if (!isStatic) {
         String methodSignature1 = this.context.getMethodSignature(member.header);
         if (!typeParameters.isEmpty()) {
            String collect = typeParameters.stream().map(t -> t.name + ":Ljava/lang/Object;").collect(Collectors.joining("", "<", ""));
            if (methodSignature1.startsWith("<")) {
               methodSignature1 = collect + methodSignature1.substring(1);
            } else {
               methodSignature1 = collect + ">" + methodSignature1;
            }
         }

         StringBuilder typeParamSigBuilder = new StringBuilder();
         StringBuilder typeParamDescBuilder = new StringBuilder();
         int i = 1;

         for (TypeParameter typeParameter : typeParameters) {
            typeParamSigBuilder.append("Ljava/lang/Class<T").append(typeParameter.name).append(";>;");
            typeParamDescBuilder.append("Ljava/lang/Class;");
         }

         int index = methodSignature1.lastIndexOf(40) + 1;
         methodSignature = methodSignature1.substring(0, index) + expandedClassSignature + typeParamSigBuilder + methodSignature1.substring(index);
         methodDescriptor = "(" + expandedClassDescriptor + typeParamDescBuilder + this.context.getMethodDescriptor(member.header).substring(1);
      } else {
         methodSignature = this.context.getMethodSignature(member.header);
         methodDescriptor = this.context.getMethodDescriptor(member.header);
      }

      JavaWriter methodWriter = new JavaWriter(
         this.context.logger, member.position, this.writer, true, method, this.definition, true, methodSignature, methodDescriptor, null
      );
      methodWriter.label(methodStart);
      if (!isStatic) {
         methodWriter.nameVariable(0, "expandedObj", methodStart, methodEnd, Type.getType(expandedClassDescriptor));
         methodWriter.nameParameter(0, "expandedObj");

         for (TypeParameter typeParameter : typeParameters) {
            methodWriter.nameParameter(0, "typeOf" + typeParameter.name);
            methodWriter.nameVariable(
               this.javaModule.getTypeParameterInfo(typeParameter).parameterIndex,
               "typeOf" + typeParameter.name,
               methodStart,
               methodEnd,
               Type.getType(Class.class)
            );
         }
      }

      for (TypeParameter typeParameter : member.header.typeParameters) {
         methodWriter.nameParameter(0, "typeOf" + typeParameter.name);
         methodWriter.nameVariable(
            this.javaModule.getTypeParameterInfo(typeParameter).parameterIndex,
            "typeOf" + typeParameter.name,
            methodStart,
            methodEnd,
            Type.getType(Class.class)
         );
      }

      for (FunctionParameter parameter : member.header.parameters) {
         methodWriter.nameParameter(0, parameter.name);
         methodWriter.nameVariable(
            this.javaModule.getParameterInfo(parameter).index, parameter.name, methodStart, methodEnd, this.context.getType(parameter.type)
         );
      }

      JavaStatementVisitor statementVisitor = new JavaStatementVisitor(this.context, this.javaModule, methodWriter);
      statementVisitor.start();
      member.body.accept(statementVisitor);
      methodWriter.label(methodEnd);
      statementVisitor.end();
      return null;
   }

   public Void visitGetter(GetterMember member) {
      boolean isStatic = member.isStatic();
      TypeID returnType = member.getType();
      ArrayList<TypeParameter> typeParameters = new ArrayList<>();
      this.expandedClass.extractTypeParameters(typeParameters);
      String descMiddle;
      String signatureMiddle;
      String signatureStart;
      if (typeParameters.isEmpty()) {
         signatureStart = "";
         signatureMiddle = "";
         descMiddle = "";
      } else {
         StringBuilder descMiddleBuilder = new StringBuilder();
         StringBuilder signatureMiddleBuilder = new StringBuilder();
         StringBuilder signatureStartBuilder = new StringBuilder("<");

         for (TypeParameter typeParameter : typeParameters) {
            descMiddleBuilder.append("Ljava/lang/Class;");
            signatureMiddleBuilder.append("Ljava/lang/Class<T").append(typeParameter.name).append(";>;");
            signatureStartBuilder.append(typeParameter.name).append(":Ljava/lang/Object;");
         }

         descMiddle = descMiddleBuilder.toString();
         signatureMiddle = signatureMiddleBuilder.toString();
         signatureStart = signatureStartBuilder.append(">").toString();
      }

      String descriptor;
      String signature;
      if (isStatic) {
         descriptor = "(" + descMiddle + ")" + this.context.getDescriptor(returnType);
         signature = signatureStart + "(" + signatureMiddle + ")" + this.context.getSignature(returnType);
      } else {
         descriptor = "(" + this.context.getDescriptor(this.expandedClass) + descMiddle + ")" + this.context.getDescriptor(returnType);
         signature = signatureStart + "(" + this.context.getSignature(this.expandedClass) + signatureMiddle + ")" + this.context.getSignature(returnType);
      }

      Label methodStart = new Label();
      Label methodEnd = new Label();
      JavaMethod method = this.context.getJavaMethod(member);
      JavaWriter methodWriter = new JavaWriter(
         this.context.logger, member.position, this.writer, true, method, this.definition, true, signature, descriptor, new String[0]
      );
      methodWriter.label(methodStart);
      if (!isStatic) {
         methodWriter.nameVariable(0, "expandedObj", methodStart, methodEnd, this.context.getType(this.expandedClass));
         methodWriter.nameParameter(0, "expandedObj");
      }

      int i = isStatic ? 0 : 1;

      for (TypeParameter typeParameter : typeParameters) {
         String name = "typeOf" + typeParameter.name;
         methodWriter.nameVariable(i, name, methodStart, methodEnd, Type.getType(Class.class));
         methodWriter.nameParameter(0, name);
      }

      JavaStatementVisitor statementVisitor = new JavaStatementVisitor(this.context, this.javaModule, methodWriter);
      statementVisitor.start();
      member.body.accept(statementVisitor);
      methodWriter.label(methodEnd);
      statementVisitor.end();
      return null;
   }

   public Void visitSetter(SetterMember member) {
      boolean isStatic = member.isStatic();
      TypeID setterType = member.parameter.type;
      ArrayList<TypeParameter> typeParameters = new ArrayList<>();
      this.expandedClass.extractTypeParameters(typeParameters);
      CompilerUtils.tagMethodParameters(this.context, this.javaModule, member.getHeader(), isStatic, typeParameters);
      setterType.extractTypeParameters(typeParameters);
      String signature = this.context.getMethodSignatureExpansion(member.getHeader(), this.expandedClass);
      String description = this.context.getMethodDescriptorExpansion(member.getHeader(), this.expandedClass);
      Label methodStart = new Label();
      Label methodEnd = new Label();
      JavaMethod javaMethod = this.context.getJavaMethod(member);
      JavaWriter methodWriter = new JavaWriter(
         this.context.logger, member.position, this.writer, true, javaMethod, member.definition, true, signature, description, new String[0]
      );
      methodWriter.label(methodStart);
      if (!isStatic) {
         methodWriter.nameVariable(0, "expandedObj", methodStart, methodEnd, this.context.getType(this.expandedClass));
         methodWriter.nameParameter(0, "expandedObj");
      }

      int i = isStatic ? 0 : 1;

      for (TypeParameter typeParameter : typeParameters) {
         String name = "typeOf" + typeParameter.name;
         methodWriter.nameVariable(i, name, methodStart, methodEnd, Type.getType(Class.class));
         methodWriter.nameParameter(0, name);
         i++;
      }

      String name = "$";
      methodWriter.nameVariable(i, "$", methodStart, methodEnd, this.context.getType(setterType));
      methodWriter.nameParameter(0, "$");
      this.javaModule.setParameterInfo(member.parameter, new JavaParameterInfo(i, this.context.getDescriptor(setterType)));
      JavaStatementVisitor javaStatementVisitor = new JavaStatementVisitor(this.context, this.javaModule, methodWriter);
      javaStatementVisitor.start();
      member.body.accept(javaStatementVisitor);
      javaStatementVisitor.end();
      methodWriter.label(methodEnd);
      return null;
   }

   public Void visitOperator(OperatorMember member) {
      JavaMethod javaMethod = this.context.getJavaMethod(member);
      MethodMember methodMember = new MethodMember(
         member.position, member.definition, member.getEffectiveModifiers(), javaMethod.name, member.header, member.builtin
      );
      methodMember.body = member.body;
      methodMember.annotations = member.annotations;
      this.javaModule.setMethodInfo(methodMember, javaMethod);
      return methodMember.accept(this);
   }

   public Void visitCaster(CasterMember member) {
      ArrayList<TypeParameter> typeParameters = new ArrayList<>();
      this.expandedClass.extractTypeParameters(typeParameters);
      CompilerUtils.tagMethodParameters(this.context, this.javaModule, member.getHeader(), false, typeParameters);
      member.toType.extractTypeParameters(typeParameters);
      String methodSignature = this.context.getMethodSignatureExpansion(member.getHeader(), this.expandedClass);
      String methodDescriptor = this.context.getMethodDescriptorExpansion(member.getHeader(), this.expandedClass);
      Label methodStart = new Label();
      Label methodEnd = new Label();
      JavaMethod javaMethod = this.context.getJavaMethod(member);
      JavaWriter methodWriter = new JavaWriter(
         this.context.logger, member.position, this.writer, true, javaMethod, member.definition, true, methodSignature, methodDescriptor, new String[0]
      );
      methodWriter.label(methodStart);
      methodWriter.nameVariable(0, "expandedObj", methodStart, methodEnd, this.context.getType(this.expandedClass));
      methodWriter.nameParameter(0, "expandedObj");
      int i = 1;

      for (TypeParameter typeParameter : typeParameters) {
         String name = "typeOf" + typeParameter.name;
         methodWriter.nameVariable(i, name, methodStart, methodEnd, Type.getType(Class.class));
         methodWriter.nameParameter(0, name);
      }

      JavaStatementVisitor javaStatementVisitor = new JavaStatementVisitor(this.context, this.javaModule, methodWriter);
      javaStatementVisitor.start();
      member.body.accept(javaStatementVisitor);
      javaStatementVisitor.end();
      methodWriter.label(methodEnd);
      return null;
   }

   public Void visitCustomIterator(IteratorMember member) {
      return null;
   }

   public Void visitCaller(CallerMember member) {
      JavaMethod javaMethod = this.context.getJavaMethod(member);
      MethodMember call = new MethodMember(member.position, member.definition, member.getEffectiveModifiers(), javaMethod.name, member.header, member.builtin);
      call.body = member.body;
      call.annotations = member.annotations;
      this.javaModule.setMethodInfo(call, javaMethod);
      return call.accept(this);
   }

   public Void visitImplementation(ImplementationMember member) {
      return null;
   }

   public Void visitInnerDefinition(InnerDefinitionMember member) {
      return null;
   }

   public Void visitStaticInitializer(StaticInitializerMember member) {
      member.body.accept(this.clinitStatementVisitor);
      return null;
   }
}
