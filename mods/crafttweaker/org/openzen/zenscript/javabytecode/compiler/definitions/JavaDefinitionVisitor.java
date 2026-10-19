package org.openzen.zenscript.javabytecode.compiler.definitions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.objectweb.asm.ClassWriter;
import org.openzen.zenscript.codemodel.definition.AliasDefinition;
import org.openzen.zenscript.codemodel.definition.ClassDefinition;
import org.openzen.zenscript.codemodel.definition.DefinitionVisitor;
import org.openzen.zenscript.codemodel.definition.EnumDefinition;
import org.openzen.zenscript.codemodel.definition.ExpansionDefinition;
import org.openzen.zenscript.codemodel.definition.FunctionDefinition;
import org.openzen.zenscript.codemodel.definition.InterfaceDefinition;
import org.openzen.zenscript.codemodel.definition.StructDefinition;
import org.openzen.zenscript.codemodel.definition.VariantDefinition;
import org.openzen.zenscript.codemodel.generic.TypeParameter;
import org.openzen.zenscript.codemodel.member.IDefinitionMember;
import org.openzen.zenscript.codemodel.member.ImplementationMember;
import org.openzen.zenscript.codemodel.type.BasicTypeID;
import org.openzen.zenscript.codemodel.type.GenericTypeID;
import org.openzen.zenscript.codemodel.type.TypeID;
import org.openzen.zenscript.javabytecode.JavaBytecodeContext;
import org.openzen.zenscript.javabytecode.compiler.CompilerUtils;
import org.openzen.zenscript.javabytecode.compiler.JavaClassWriter;
import org.openzen.zenscript.javabytecode.compiler.JavaStatementVisitor;
import org.openzen.zenscript.javabytecode.compiler.JavaWriter;
import org.openzen.zenscript.javashared.JavaClass;
import org.openzen.zenscript.javashared.JavaMethod;
import org.openzen.zenscript.javashared.JavaModifiers;
import org.openzen.zenscript.javashared.JavaTypeGenericVisitor;
import org.openzen.zenscript.javashared.JavaVariantOption;

public class JavaDefinitionVisitor implements DefinitionVisitor<byte[]> {
   final JavaTypeGenericVisitor javaTypeGenericVisitor;
   private final JavaMethod ENUM_VALUEOF = JavaMethod.getNativeStatic(JavaClass.CLASS, "valueOf", "(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;");
   private final JavaClassWriter outerWriter;
   private final JavaBytecodeContext context;

   public JavaDefinitionVisitor(JavaBytecodeContext context, JavaClassWriter outerWriter) {
      this.context = context;
      this.outerWriter = outerWriter;
      this.javaTypeGenericVisitor = new JavaTypeGenericVisitor(context);
   }

   public byte[] visitClass(ClassDefinition definition) {
      String superTypeInternalName = definition.getSuperType() == null ? "java/lang/Object" : this.context.getInternalName(definition.getSuperType());
      JavaClass toClass = this.context.getJavaClass(definition);
      JavaClassWriter writer = new JavaClassWriter(2);
      List<String> interfaces = new ArrayList<>();

      for (IDefinitionMember member : definition.members) {
         if (member instanceof ImplementationMember) {
            interfaces.add(this.context.getInternalName(((ImplementationMember)member).type));
         }
      }

      StringBuilder signatureBuilder = new StringBuilder();
      if (definition.typeParameters.length != 0) {
         signatureBuilder.append("<");

         for (TypeParameter typeParameter : definition.typeParameters) {
            signatureBuilder.append(typeParameter.name);
            signatureBuilder.append(":");
            signatureBuilder.append("Ljava/lang/Object;");
         }

         signatureBuilder.append(">");
      }

      signatureBuilder.append("L").append(superTypeInternalName).append(";");

      for (IDefinitionMember member : definition.members) {
         if (member instanceof ImplementationMember) {
            signatureBuilder.append(this.context.getInternalName(((ImplementationMember)member).type));
         }
      }

      String signature = signatureBuilder.toString();
      writer.visit(
         52, JavaModifiers.getJavaModifiers(definition.modifiers), toClass.internalName, signature, superTypeInternalName, interfaces.toArray(new String[0])
      );

      for (TypeParameter typeParameter : definition.typeParameters) {
         writer.visitField(18, "typeOf" + typeParameter.name, "Ljava/lang/Class;", "Ljava/lang/Class<T" + typeParameter.name + ";>;", null);
      }

      JavaMemberVisitor memberVisitor = new JavaMemberVisitor(this.context, writer, toClass, definition);

      for (IDefinitionMember member : definition.members) {
         member.accept(memberVisitor);
      }

      memberVisitor.end();
      writer.visitEnd();
      return writer.toByteArray();
   }

   public byte[] visitInterface(InterfaceDefinition definition) {
      JavaClass toClass = this.context.getJavaClass(definition);
      ClassWriter writer = new JavaClassWriter(2);
      String signature = null;
      String[] baseInterfaces = new String[definition.baseInterfaces.size()];

      for (int i = 0; i < baseInterfaces.length; i++) {
         baseInterfaces[i] = this.context.getInternalName(definition.baseInterfaces.get(i));
      }

      writer.visit(52, JavaModifiers.getJavaModifiers(definition.modifiers) | 512 | 1024, toClass.internalName, signature, "java/lang/Object", baseInterfaces);
      JavaMemberVisitor memberVisitor = new JavaMemberVisitor(this.context, writer, toClass, definition);

      for (IDefinitionMember member : definition.members) {
         member.accept(memberVisitor);
      }

      memberVisitor.end();
      writer.visitEnd();
      return writer.toByteArray();
   }

   public byte[] visitEnum(EnumDefinition definition) {
      this.context.logger.trace("Compiling enum " + definition.name + " in " + definition.position.getFilename());
      String superTypeInternalName = definition.getSuperType() == null ? "java/lang/Enum" : this.context.getInternalName(definition.getSuperType());
      ClassWriter writer = new JavaClassWriter(2);
      JavaClass toClass = this.context.getJavaClass(definition);
      writer.visit(52, 16433, toClass.internalName, null, superTypeInternalName, null);
      writer.visitField(4122, "$VALUES", "[L" + toClass.internalName + ";", null, null).visitEnd();
      JavaMemberVisitor visitor = new JavaMemberVisitor(this.context, writer, toClass, definition);

      for (IDefinitionMember member : definition.members) {
         member.accept(visitor);
      }

      visitor.end();
      JavaMethod valuesMethod = JavaMethod.getStatic(toClass, "values", "()[L" + toClass.internalName + ";", 9);
      JavaWriter valuesWriter = new JavaWriter(this.context.logger, definition.position, writer, true, valuesMethod, definition, null, null);
      valuesWriter.start();
      valuesWriter.getStaticField(toClass.internalName, "$VALUES", "[L" + toClass.internalName + ";");
      JavaMethod arrayClone = JavaMethod.getNativeVirtual(
         JavaClass.fromInternalName("[L" + toClass.internalName + ";", JavaClass.Kind.ARRAY), "clone", "()Ljava/lang/Object;"
      );
      valuesWriter.invokeVirtual(arrayClone);
      valuesWriter.checkCast("[L" + toClass.internalName + ";");
      valuesWriter.returnObject();
      valuesWriter.end();
      JavaMethod valueOfMethod = JavaMethod.getStatic(toClass, "valueOf", "(Ljava/lang/String;)L" + toClass.internalName + ";", 9);
      JavaWriter valueOfWriter = new JavaWriter(this.context.logger, definition.position, writer, true, valueOfMethod, definition, null, null);
      valueOfWriter.start();
      valueOfWriter.constantClass(toClass);
      valueOfWriter.loadObject(0);
      valueOfWriter.invokeStatic(this.ENUM_VALUEOF);
      valueOfWriter.checkCast(toClass.internalName);
      valueOfWriter.returnObject();
      valueOfWriter.end();
      writer.visitEnd();
      return writer.toByteArray();
   }

   public byte[] visitStruct(StructDefinition definition) {
      return null;
   }

   public byte[] visitFunction(FunctionDefinition definition) {
      CompilerUtils.tagMethodParameters(this.context, this.context.getJavaModule(definition.module), definition.header, true, Collections.emptyList());
      String signature = this.context.getMethodSignature(definition.header);
      JavaMethod method = this.context.getJavaMethod(definition.caller);
      JavaWriter writer = new JavaWriter(this.context.logger, definition.position, this.outerWriter, true, method, definition, signature, null);
      JavaStatementVisitor statementVisitor = new JavaStatementVisitor(this.context, this.context.getJavaModule(definition.module), writer);
      statementVisitor.start();
      boolean returns = definition.caller.body.accept(statementVisitor);
      if (!returns) {
         TypeID type = definition.header.getReturnType();
         if (type != BasicTypeID.VOID) {
            if (CompilerUtils.isPrimitive(type)) {
               writer.iConst0();
            } else {
               writer.aConstNull();
            }
         }

         writer.returnType(this.context.getType(type));
      }

      statementVisitor.end();
      return null;
   }

   public byte[] visitExpansion(ExpansionDefinition definition) {
      JavaClassWriter writer = new JavaClassWriter(2);
      writer.visitSource(definition.position.getFilename(), null);
      JavaClass expansionClassInfo = this.context.getJavaModule(definition.module).getExpansionClassInfo(definition);
      String internalName = expansionClassInfo.internalName;
      writer.visit(52, 9, internalName, null, "java/lang/Object", null);
      JavaExpansionMemberVisitor memberVisitor = new JavaExpansionMemberVisitor(this.context, writer, definition.target, definition);

      for (IDefinitionMember member : definition.members) {
         member.accept(memberVisitor);
      }

      memberVisitor.end();
      writer.visitEnd();
      return writer.toByteArray();
   }

   public byte[] visitAlias(AliasDefinition definition) {
      throw new AssertionError("Aliases shouldn't exist here...");
   }

   public byte[] visitVariant(VariantDefinition variant) {
      JavaClass toClass = this.context.getJavaClass(variant);
      JavaClassWriter writer = new JavaClassWriter(2);
      writer.visitSource(variant.position.getFilename(), null);
      String variantName = variant.name;
      String ss = "<" + this.javaTypeGenericVisitor.getGenericSignature(variant.typeParameters) + ">Ljava/lang/Object;";
      JavaClassWriter.registerSuperClass(variantName, "java/lang/Object");
      writer.visit(52, 1025, toClass.internalName, ss, "java/lang/Object", null);
      writer.visitMethod(1025, "getDenominator", "()I", null, null).visitEnd();
      JavaMemberVisitor visitor = new JavaMemberVisitor(this.context, writer, toClass, variant);

      for (VariantDefinition.Option option : variant.options) {
         JavaVariantOption optionTag = this.context.getJavaVariantOption(option);
         JavaClassWriter optionWriter = new JavaClassWriter(2);
         String optionClassName = variantName + "$" + option.name;
         JavaClassWriter.registerSuperClass(optionClassName, variantName);
         writer.visitInnerClass(optionTag.variantOptionClass.internalName, optionTag.variantClass.internalName, option.name, 25);
         StringBuilder builder = new StringBuilder();
         builder.append("<");

         for (TypeID type : option.types) {
            builder.append(this.javaTypeGenericVisitor.getSignatureWithBound(type));
         }

         builder.append(">");
         builder.append("L").append(toClass.internalName).append("<");

         for (TypeParameter genericParameter : variant.typeParameters) {
            boolean t = true;

            for (TypeID type : option.types) {
               if (type instanceof GenericTypeID genericTypeID && genericParameter.equals(genericTypeID.parameter)) {
                  builder.append("T").append(genericParameter.name).append(";");
                  t = false;
               }
            }

            if (t) {
               builder.append(this.javaTypeGenericVisitor.getGenericBounds(genericParameter.bounds));
            }
         }

         String signature = builder.append(">;").toString();
         optionWriter.visit(52, 25, optionTag.variantOptionClass.internalName, signature, optionTag.variantClass.internalName, null);
         JavaMemberVisitor optionVisitor = new JavaMemberVisitor(this.context, optionWriter, optionTag.variantOptionClass, variant);
         StringBuilder optionInitDescBuilder = new StringBuilder("(");
         StringBuilder optionInitSignatureBuilder = new StringBuilder("(");
         TypeID[] types = option.types;

         for (int i = 0; i < types.length; i++) {
            String descriptor = this.context.getDescriptor(types[i]);
            optionInitDescBuilder.append(descriptor);
            optionInitSignatureBuilder.append("T").append(((GenericTypeID)types[i]).parameter.name).append(";");
            optionWriter.visitField(17, "field" + i, descriptor, "T" + ((GenericTypeID)types[i]).parameter.name + ";", null).visitEnd();
         }

         optionInitDescBuilder.append(")V");
         optionInitSignatureBuilder.append(")V");
         JavaMethod constructorMethod = JavaMethod.getConstructor(optionTag.variantOptionClass, optionInitDescBuilder.toString(), 1);
         JavaWriter initWriter = new JavaWriter(
            this.context.logger, option.position, optionWriter, constructorMethod, variant, optionInitSignatureBuilder.toString(), null
         );
         initWriter.start();
         initWriter.loadObject(0);
         initWriter.dup();
         initWriter.invokeSpecial(toClass.internalName, "<init>", "()V");

         for (int i = 0; i < types.length; i++) {
            initWriter.dup();
            initWriter.loadObject(i + 1);
            String descriptor = this.context.getDescriptor(types[i]);
            initWriter.putField(optionTag.variantOptionClass.internalName, "field" + i, descriptor);
         }

         initWriter.pop();
         initWriter.ret();
         initWriter.end();
         JavaMethod denominator = JavaMethod.getVirtual(optionTag.variantOptionClass, "getDenominator", "()I", 1);
         JavaWriter getDenominator = new JavaWriter(
            this.context.logger, option.position, optionWriter, denominator, variant, null, null, "Ljava/lang/Override;"
         );
         getDenominator.start();
         getDenominator.constant(option.ordinal);
         getDenominator.returnInt();
         getDenominator.end();
         optionVisitor.end();
         optionWriter.visitEnd();
         byte[] byteArray = optionWriter.toByteArray();
         this.context.register(optionTag.variantOptionClass.internalName, byteArray);
      }

      for (IDefinitionMember member : variant.members) {
         member.accept(visitor);
      }

      JavaWriter superInitWriter = new JavaWriter(
         this.context.logger, variant.position, writer, JavaMethod.getConstructor(toClass, "()V", 1), variant, "()V", null
      );
      superInitWriter.start();
      superInitWriter.loadObject(0);
      superInitWriter.invokeSpecial("java/lang/Object", "<init>", "()V");
      superInitWriter.ret();
      superInitWriter.end();
      visitor.end();
      writer.visitEnd();
      return writer.toByteArray();
   }
}
