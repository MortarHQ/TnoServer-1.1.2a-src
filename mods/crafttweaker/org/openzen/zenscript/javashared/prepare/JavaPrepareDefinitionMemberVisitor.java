package org.openzen.zenscript.javashared.prepare;

import org.openzen.zenscript.codemodel.HighLevelDefinition;
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
import org.openzen.zenscript.codemodel.type.DefinitionTypeID;
import org.openzen.zenscript.codemodel.type.TypeID;
import org.openzen.zenscript.javashared.JavaClass;
import org.openzen.zenscript.javashared.JavaCompiledModule;
import org.openzen.zenscript.javashared.JavaContext;
import org.openzen.zenscript.javashared.JavaMethod;
import org.openzen.zenscript.javashared.JavaModifiers;
import org.openzen.zenscript.javashared.JavaNativeClass;
import org.openzen.zenscript.javashared.JavaTypeParameterInfo;

public class JavaPrepareDefinitionMemberVisitor implements DefinitionVisitor<JavaClass> {
   private final JavaContext context;
   private final JavaCompiledModule module;

   public JavaPrepareDefinitionMemberVisitor(JavaContext context, JavaCompiledModule module) {
      this.context = context;
      this.module = module;
   }

   private boolean isPrepared(HighLevelDefinition definition) {
      return this.context.getJavaClass(definition).membersPrepared;
   }

   public void prepare(TypeID type) {
      if (type instanceof DefinitionTypeID) {
         HighLevelDefinition definition = ((DefinitionTypeID)type).definition;
         this.prepare(definition);
      }
   }

   public void prepare(HighLevelDefinition definition) {
      if (!this.isPrepared(definition)) {
         if (definition.module != this.module.module) {
            throw new IllegalArgumentException("Definition is not in the same module as the current module!");
         }

         this.context.logger.trace("~~ Preparing " + definition.name);
         definition.accept(this);
      }
   }

   public JavaClass visitClass(ClassDefinition definition) {
      return this.isPrepared(definition) ? this.context.getJavaClass(definition) : this.visitClassCompiled(definition, true, JavaClass.Kind.CLASS);
   }

   public JavaClass visitInterface(InterfaceDefinition definition) {
      if (this.isPrepared(definition)) {
         return this.context.getJavaClass(definition);
      }

      for (TypeID baseType : definition.baseInterfaces) {
         this.prepare(baseType);
      }

      return this.visitClassCompiled(definition, true, JavaClass.Kind.INTERFACE);
   }

   public JavaClass visitEnum(EnumDefinition definition) {
      return this.isPrepared(definition) ? this.context.getJavaClass(definition) : this.visitClassCompiled(definition, false, JavaClass.Kind.ENUM);
   }

   public JavaClass visitStruct(StructDefinition definition) {
      return this.isPrepared(definition) ? this.context.getJavaClass(definition) : this.visitClassCompiled(definition, true, JavaClass.Kind.CLASS);
   }

   public JavaClass visitFunction(FunctionDefinition definition) {
      if (this.isPrepared(definition)) {
         return this.context.getJavaClass(definition);
      }

      JavaClass cls = this.context.getJavaClass(definition);
      JavaMethod method = JavaMethod.getStatic(
         cls,
         definition.name,
         this.context.getMethodDescriptor(definition.header),
         JavaModifiers.getJavaModifiers(definition.modifiers),
         definition.header.getReturnType().isGeneric()
      );
      this.module.setMethodInfo(definition.caller, method);
      return cls;
   }

   public JavaClass visitExpansion(ExpansionDefinition definition) {
      if (this.isPrepared(definition)) {
         return this.context.getJavaClass(definition);
      }

      JavaNativeClass nativeClass = this.context.getJavaNativeClass(definition);
      JavaClass cls = this.context.getJavaExpansionClass(definition);
      this.visitExpansionMembers(definition, cls, nativeClass);
      return cls;
   }

   public JavaClass visitAlias(AliasDefinition definition) {
      return null;
   }

   public JavaClass visitVariant(VariantDefinition variant) {
      JavaClass cls = this.context.getJavaClass(variant);
      if (cls.membersPrepared) {
         return cls;
      }

      this.visitClassMembers(variant, cls, null, false);
      return cls;
   }

   private JavaClass visitClassCompiled(HighLevelDefinition definition, boolean startsEmpty, JavaClass.Kind kind) {
      for (TypeParameter typeParameter : definition.typeParameters) {
         this.module.setTypeParameterInfo(typeParameter, new JavaTypeParameterInfo(-1));
      }

      if (definition.getSuperType() != null) {
         this.prepare(definition.getSuperType());
      }

      JavaNativeClass nativeClass = this.context.getJavaNativeClass(definition);
      JavaClass cls = this.context.getJavaClass(definition);
      if (nativeClass == null) {
         this.visitClassMembers(definition, cls, null, startsEmpty);
      } else {
         cls.membersPrepared = true;
         JavaClass expansionCls = this.context.getJavaExpansionClass(definition);
         this.visitExpansionMembers(definition, expansionCls, nativeClass);
         cls.empty = expansionCls.empty;
      }

      return cls;
   }

   private void visitClassMembers(HighLevelDefinition definition, JavaClass cls, JavaNativeClass nativeClass, boolean startsEmpty) {
      this.context.logger.trace("Preparing " + cls.internalName);
      JavaPrepareClassMethodVisitor methodVisitor = new JavaPrepareClassMethodVisitor(this.context, this.module, cls, nativeClass, this, startsEmpty);

      for (IDefinitionMember member : definition.members) {
         member.accept(methodVisitor);
      }

      cls.membersPrepared = true;
   }

   private void visitExpansionMembers(HighLevelDefinition definition, JavaClass cls, JavaNativeClass nativeClass) {
      this.context.logger.trace("Preparing " + cls.internalName);
      JavaPrepareExpansionMethodVisitor methodVisitor = new JavaPrepareExpansionMethodVisitor(this.context, this.module, cls, nativeClass);

      for (IDefinitionMember member : definition.members) {
         member.accept(methodVisitor);
      }

      cls.membersPrepared = true;
   }
}
