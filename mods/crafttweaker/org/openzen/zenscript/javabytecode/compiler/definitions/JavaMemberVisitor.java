package org.openzen.zenscript.javabytecode.compiler.definitions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.Type;
import org.openzen.zenscript.codemodel.FunctionParameter;
import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.codemodel.Modifiers;
import org.openzen.zenscript.codemodel.annotations.NativeTag;
import org.openzen.zenscript.codemodel.definition.EnumDefinition;
import org.openzen.zenscript.codemodel.expression.Expression;
import org.openzen.zenscript.codemodel.generic.TypeParameter;
import org.openzen.zenscript.codemodel.member.CallerMember;
import org.openzen.zenscript.codemodel.member.CasterMember;
import org.openzen.zenscript.codemodel.member.ConstMember;
import org.openzen.zenscript.codemodel.member.ConstructorMember;
import org.openzen.zenscript.codemodel.member.DestructorMember;
import org.openzen.zenscript.codemodel.member.EnumConstantMember;
import org.openzen.zenscript.codemodel.member.FieldMember;
import org.openzen.zenscript.codemodel.member.GetterMember;
import org.openzen.zenscript.codemodel.member.IDefinitionMember;
import org.openzen.zenscript.codemodel.member.ImplementationMember;
import org.openzen.zenscript.codemodel.member.InnerDefinitionMember;
import org.openzen.zenscript.codemodel.member.IteratorMember;
import org.openzen.zenscript.codemodel.member.MemberVisitor;
import org.openzen.zenscript.codemodel.member.MethodMember;
import org.openzen.zenscript.codemodel.member.OperatorMember;
import org.openzen.zenscript.codemodel.member.SetterMember;
import org.openzen.zenscript.codemodel.member.StaticInitializerMember;
import org.openzen.zenscript.codemodel.type.DefinitionTypeID;
import org.openzen.zenscript.codemodel.type.member.BuiltinID;
import org.openzen.zenscript.javabytecode.JavaBytecodeContext;
import org.openzen.zenscript.javabytecode.compiler.CompilerUtils;
import org.openzen.zenscript.javabytecode.compiler.JavaStatementVisitor;
import org.openzen.zenscript.javabytecode.compiler.JavaWriter;
import org.openzen.zenscript.javashared.JavaClass;
import org.openzen.zenscript.javashared.JavaCompiledModule;
import org.openzen.zenscript.javashared.JavaField;
import org.openzen.zenscript.javashared.JavaImplementation;
import org.openzen.zenscript.javashared.JavaMethod;
import org.openzen.zenscript.javashared.JavaParameterInfo;
import org.openzen.zenscript.javashared.JavaTypeParameterInfo;

public class JavaMemberVisitor implements MemberVisitor<Void> {
   private final ClassWriter writer;
   private final JavaBytecodeContext context;
   private final JavaClass toClass;
   private final HighLevelDefinition definition;
   private final JavaStatementVisitor clinitStatementVisitor;
   private final JavaCompiledModule javaModule;
   private EnumDefinition enumDefinition = null;

   public JavaMemberVisitor(JavaBytecodeContext context, ClassWriter writer, JavaClass toClass, HighLevelDefinition definition) {
      this.writer = writer;
      this.toClass = toClass;
      this.definition = definition;
      this.context = context;
      this.javaModule = context.getJavaModule(definition.module);
      JavaWriter javaWriter = new JavaWriter(
         context.logger,
         definition.position,
         writer,
         new JavaMethod(toClass, JavaMethod.Kind.STATICINIT, "<clinit>", true, "()V", 8, false),
         definition,
         null,
         null
      );
      this.clinitStatementVisitor = new JavaStatementVisitor(context, this.javaModule, javaWriter);
      this.clinitStatementVisitor.start();
      CompilerUtils.writeDefaultFieldInitializers(context, javaWriter, definition, true);
      if (definition instanceof EnumDefinition) {
         this.enumDefinition = (EnumDefinition)definition;
      }
   }

   public Void visitConst(ConstMember member) {
      JavaField field = this.context.getJavaField(member);
      this.writer.visitField(CompilerUtils.calcAccess(member.getEffectiveModifiers()), field.name, field.descriptor, field.signature, null).visitEnd();
      return null;
   }

   public Void visitField(FieldMember member) {
      JavaField field = this.context.getJavaField(member);
      this.writer.visitField(CompilerUtils.calcAccess(member.getEffectiveModifiers()), field.name, field.descriptor, field.signature, null).visitEnd();
      return null;
   }

   public Void visitConstructor(ConstructorMember member) {
      boolean isEnum = this.definition instanceof EnumDefinition;
      JavaMethod method = this.context.getJavaMethod(member);
      Label constructorStart = new Label();
      Label constructorEnd = new Label();
      JavaWriter constructorWriter = new JavaWriter(
         this.context.logger, member.position, this.writer, method, this.definition, this.context.getMethodSignature(member.header), null
      );
      constructorWriter.label(constructorStart);
      CompilerUtils.tagConstructorParameters(this.context, this.javaModule, member.definition, member.header, isEnum);
      if (isEnum) {
         constructorWriter.nameParameter(0, "name");
         constructorWriter.nameParameter(0, "index");
      }

      for (TypeParameter typeParameter : this.definition.typeParameters) {
         constructorWriter.nameParameter(0, "typeof" + typeParameter.name);
         constructorWriter.nameVariable(
            this.javaModule.getTypeParameterInfo(typeParameter).parameterIndex,
            "typeOf" + typeParameter.name,
            constructorStart,
            constructorEnd,
            Type.getType(Class.class)
         );
      }

      for (FunctionParameter parameter : member.header.parameters) {
         constructorWriter.nameVariable(
            this.javaModule.getParameterInfo(parameter).index, parameter.name, constructorStart, constructorEnd, this.context.getType(parameter.type)
         );
      }

      JavaStatementVisitor statementVisitor = new JavaStatementVisitor(this.context, this.javaModule, constructorWriter);
      statementVisitor.start();
      if (!member.isConstructorForwarded()) {
         if (isEnum) {
            this.context.logger.trace("Writing enum constructor");
            constructorWriter.loadObject(0);
            constructorWriter.loadObject(1);
            constructorWriter.loadInt(2);
            constructorWriter.invokeSpecial(Type.getInternalName(Enum.class), "<init>", "(Ljava/lang/String;I)V");
         } else if (this.definition.getSuperType() == null) {
            this.context.logger.trace("Writing regular constructor");
            constructorWriter.loadObject(0);
            constructorWriter.invokeSpecial(Type.getInternalName(Object.class), "<init>", "()V");
         } else if (member.builtin == BuiltinID.CLASS_DEFAULT_CONSTRUCTOR) {
            HighLevelDefinition superType = ((DefinitionTypeID)this.definition.getSuperType()).definition;
            constructorWriter.loadObject(0);
            constructorWriter.invokeSpecial(this.context.getJavaClass(superType).internalName, "<init>", "()V");
         }
      }

      for (IDefinitionMember membersOfSameType : member.definition.members) {
         if (membersOfSameType instanceof FieldMember fieldMember && !fieldMember.isStatic()) {
            Expression initializer = fieldMember.initializer;
            if (initializer != null) {
               constructorWriter.loadObject(0);
               initializer.accept(statementVisitor.expressionVisitor);
               constructorWriter.putField(this.context.getJavaField(fieldMember));
            }
         }
      }

      for (TypeParameter typeParameter : this.definition.typeParameters) {
         JavaTypeParameterInfo typeParameterInfo = this.javaModule.getTypeParameterInfo(typeParameter);
         JavaField field = typeParameterInfo.field;
         int parameterIndex = typeParameterInfo.parameterIndex;
         constructorWriter.loadObject(0);
         constructorWriter.loadObject(parameterIndex);
         constructorWriter.putField(field);
      }

      if (member.body != null) {
         member.body.accept(statementVisitor);
      }

      constructorWriter.label(constructorEnd);
      statementVisitor.end();
      return null;
   }

   public Void visitDestructor(DestructorMember member) {
      int modifiers = 1;
      if (member.body == null) {
         modifiers |= 1024;
      }

      JavaMethod method = JavaMethod.getVirtual(this.toClass, "close", "()V", modifiers);
      if (member.body == null) {
         return null;
      }

      Label constructorStart = new Label();
      Label constructorEnd = new Label();
      JavaWriter destructorWriter = new JavaWriter(this.context.logger, member.position, this.writer, method, this.definition, null, null);
      destructorWriter.label(constructorStart);
      JavaStatementVisitor statementVisitor = new JavaStatementVisitor(this.context, this.javaModule, destructorWriter);
      statementVisitor.start();
      member.body.accept(statementVisitor);
      destructorWriter.label(constructorEnd);
      statementVisitor.end();
      return null;
   }

   public Void visitMethod(MethodMember member) {
      CompilerUtils.tagMethodParameters(this.context, this.javaModule, member.header, member.isStatic(), Collections.emptyList());
      boolean isAbstract = member.body == null || Modifiers.isAbstract(member.getEffectiveModifiers());
      JavaMethod method = this.context.getJavaMethod(member);
      JavaWriter methodWriter = new JavaWriter(
         this.context.logger, member.position, this.writer, method, this.definition, this.context.getMethodSignature(member.header), null
      );
      if (!isAbstract) {
         if (method.isAbstract() || method.cls.kind == JavaClass.Kind.INTERFACE) {
            throw new IllegalArgumentException();
         }

         new Label();
         Label methodEnd = new Label();
         JavaStatementVisitor statementVisitor = new JavaStatementVisitor(this.context, this.javaModule, methodWriter);
         statementVisitor.start();
         member.body.accept(statementVisitor);
         methodWriter.label(methodEnd);
         statementVisitor.end();
      }

      return null;
   }

   public Void visitGetter(GetterMember member) {
      if (member.hasTag(NativeTag.class)) {
         return null;
      }

      String descriptor = this.context.getMethodDescriptor(member.getHeader());
      String signature = this.context.getMethodSignature(member.getHeader(), true);
      Label methodStart = new Label();
      Label methodEnd = new Label();
      JavaMethod method = this.context.getJavaMethod(member);
      JavaWriter methodWriter = new JavaWriter(
         this.context.logger, member.position, this.writer, true, method, this.definition, false, signature, descriptor, new String[0]
      );
      methodWriter.label(methodStart);
      JavaStatementVisitor statementVisitor = new JavaStatementVisitor(this.context, this.javaModule, methodWriter);
      statementVisitor.start();
      member.body.accept(statementVisitor);
      methodWriter.label(methodEnd);
      statementVisitor.end();
      return null;
   }

   public Void visitSetter(SetterMember member) {
      String signature = this.context.getMethodSignature(member.getHeader());
      String description = this.context.getMethodDescriptor(member.getHeader());
      Label methodStart = new Label();
      Label methodEnd = new Label();
      JavaMethod javaMethod = this.context.getJavaMethod(member);
      JavaWriter methodWriter = new JavaWriter(
         this.context.logger, member.position, this.writer, true, javaMethod, member.definition, false, signature, description, new String[0]
      );
      methodWriter.label(methodStart);
      String name = "$";
      methodWriter.nameVariable(1, "$", methodStart, methodEnd, this.context.getType(member.getType()));
      methodWriter.nameParameter(0, "$");
      this.javaModule.setParameterInfo(member.parameter, new JavaParameterInfo(1, this.context.getDescriptor(member.getType())));
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
      JavaMethod javaMethod = this.context.getJavaMethod(member);
      if (javaMethod != null && javaMethod.compile) {
         ArrayList<TypeParameter> typeParameters = new ArrayList<>(Arrays.asList(this.definition.typeParameters));
         CompilerUtils.tagMethodParameters(this.context, this.javaModule, member.getHeader(), false, typeParameters);
         member.toType.extractTypeParameters(typeParameters);
         String methodSignature = this.context.getMethodSignature(member.getHeader());
         String methodDescriptor = this.context.getMethodDescriptor(member.getHeader());
         Label methodStart = new Label();
         Label methodEnd = new Label();
         JavaWriter methodWriter = new JavaWriter(
            this.context.logger, member.position, this.writer, true, javaMethod, member.definition, false, methodSignature, methodDescriptor, new String[0]
         );
         methodWriter.label(methodStart);
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
      } else {
         return null;
      }
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
      JavaImplementation implementation = this.context.getJavaImplementation(member);
      if (!implementation.inline) {
         if (this.javaModule.getNativeClassInfo(member.definition) != null) {
            return null;
         } else {
            throw new UnsupportedOperationException("Non-inline interface implementations not yet available");
         }
      } else {
         for (IDefinitionMember imember : member.members) {
            imember.accept(this);
         }

         return null;
      }
   }

   public Void visitInnerDefinition(InnerDefinitionMember member) {
      return null;
   }

   public Void visitStaticInitializer(StaticInitializerMember member) {
      member.body.accept(this.clinitStatementVisitor);
      return null;
   }

   public void end() {
      if (this.enumDefinition != null) {
         JavaClass toClass = this.context.getJavaClass(this.enumDefinition);

         for (EnumConstantMember constant : this.enumDefinition.enumConstants) {
            this.writer.visitField(16409, constant.name, "L" + toClass.internalName + ";", null, null).visitEnd();
            String internalName = this.context.getInternalName(constant.constructor.type);
            JavaWriter clinitWriter = this.clinitStatementVisitor.getJavaWriter();
            clinitWriter.newObject(internalName);
            clinitWriter.dup();
            clinitWriter.constant(constant.name);
            clinitWriter.constant(constant.ordinal);

            for (Expression argument : constant.constructor.arguments.arguments) {
               argument.accept(this.clinitStatementVisitor.expressionVisitor);
            }

            clinitWriter.invokeSpecial(internalName, "<init>", this.context.getEnumConstructorDescriptor(constant.constructor.constructor.getHeader()));
            clinitWriter.putStaticField(internalName, constant.name, "L" + internalName + ";");
         }

         JavaWriter clinitWriter = this.clinitStatementVisitor.getJavaWriter();
         List<EnumConstantMember> enumConstants = this.enumDefinition.enumConstants;
         clinitWriter.constant(enumConstants.size());
         clinitWriter.newArray(Type.getType("L" + toClass.internalName + ";"));

         for (EnumConstantMember enumConstant : enumConstants) {
            clinitWriter.dup();
            clinitWriter.constant(enumConstant.ordinal);
            clinitWriter.getStaticField(toClass.internalName, enumConstant.name, "L" + toClass.internalName + ";");
            clinitWriter.arrayStore(Type.getType("L" + toClass.internalName + ";"));
         }

         clinitWriter.putStaticField(toClass.internalName, "$VALUES", "[L" + toClass.internalName + ";");
      }

      this.clinitStatementVisitor.end();
   }
}
