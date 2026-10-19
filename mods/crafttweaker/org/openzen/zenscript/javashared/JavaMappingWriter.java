package org.openzen.zenscript.javashared;

import org.openzen.zenscript.codemodel.FunctionHeader;
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
import org.openzen.zenscript.codemodel.member.CallerMember;
import org.openzen.zenscript.codemodel.member.CasterMember;
import org.openzen.zenscript.codemodel.member.ConstMember;
import org.openzen.zenscript.codemodel.member.ConstructorMember;
import org.openzen.zenscript.codemodel.member.DestructorMember;
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

public class JavaMappingWriter implements DefinitionVisitor<Void> {
   private final JavaCompiledModule module;
   private final StringBuilder result = new StringBuilder();

   public JavaMappingWriter(JavaCompiledModule module) {
      this.module = module;
   }

   public String getOutput() {
      return this.result.toString();
   }

   public Void visitClass(ClassDefinition definition) {
      this.result.append("class:");
      this.writeDefinitionDescriptor(definition);
      return null;
   }

   public Void visitInterface(InterfaceDefinition definition) {
      this.result.append("interface:");
      this.writeDefinitionDescriptor(definition);
      return null;
   }

   public Void visitEnum(EnumDefinition definition) {
      this.result.append("enum:");
      this.writeDefinitionDescriptor(definition);
      return null;
   }

   public Void visitStruct(StructDefinition definition) {
      this.result.append("struct:");
      this.writeDefinitionDescriptor(definition);
      return null;
   }

   public Void visitFunction(FunctionDefinition definition) {
      this.result.append("function:").append(definition.name);
      this.writeFunctionDescriptor(definition.header);
      return null;
   }

   public Void visitExpansion(ExpansionDefinition definition) {
      this.result.append("expansion:");
      this.writeDefinitionDescriptor(definition);
      return null;
   }

   public Void visitAlias(AliasDefinition definition) {
      return null;
   }

   public Void visitVariant(VariantDefinition variant) {
      this.result.append("variant:");
      this.writeDefinitionDescriptor(variant);
      return null;
   }

   private void writeFunctionDescriptor(FunctionHeader header) {
      this.result.append(header.getCanonical());
   }

   private void writeDefinitionDescriptor(HighLevelDefinition definition) {
      if (definition.name != null) {
         this.result.append(definition.name);
      }

      if (definition.typeParameters.length > 0) {
         this.result.append('<');

         for (int i = 0; i < definition.typeParameters.length; i++) {
            if (i > 0) {
               this.result.append(',');
            }

            this.result.append(definition.typeParameters[i].getCanonical());
         }

         this.result.append('>');
      }

      JavaClass definitionClass = definition instanceof ExpansionDefinition
         ? this.module.getExpansionClassInfo(definition)
         : this.module.getClassInfo(definition);
      if (definitionClass != null) {
         this.result.append('@').append(definitionClass.internalName);
      }

      this.result.append('\n');
      JavaMappingWriter.MemberMappingWriter memberWriter = new JavaMappingWriter.MemberMappingWriter(definitionClass);

      for (IDefinitionMember member : definition.members) {
         member.accept(memberWriter);
      }
   }

   private class MemberMappingWriter implements MemberVisitor<Void> {
      private final JavaClass definition;

      public MemberMappingWriter(JavaClass definition) {
         this.definition = definition;
      }

      public Void visitConst(ConstMember member) {
         JavaField field = JavaMappingWriter.this.module.optFieldInfo(member);
         if (field == null) {
            return null;
         }

         JavaMappingWriter.this.result.append(":const:");
         JavaMappingWriter.this.result.append(member.name);
         JavaMappingWriter.this.result.append("=");
         JavaMappingWriter.this.result.append(field.getMapping(this.definition));
         JavaMappingWriter.this.result.append('\n');
         return null;
      }

      public Void visitField(FieldMember member) {
         JavaField field = JavaMappingWriter.this.module.optFieldInfo(member);
         if (field == null) {
            return null;
         }

         JavaMappingWriter.this.result.append(":field:");
         JavaMappingWriter.this.result.append(member.name);
         JavaMappingWriter.this.result.append("=");
         JavaMappingWriter.this.result.append(field.getMapping(this.definition));
         JavaMappingWriter.this.result.append('\n');
         return null;
      }

      public Void visitConstructor(ConstructorMember member) {
         JavaMethod method = JavaMappingWriter.this.module.optMethodInfo(member);
         if (method == null) {
            return null;
         }

         JavaMappingWriter.this.result.append(":constructor:");
         JavaMappingWriter.this.result.append(member.header.getCanonicalWithoutReturnType());
         JavaMappingWriter.this.result.append("=");
         JavaMappingWriter.this.result.append(method.getMapping(this.definition));
         JavaMappingWriter.this.result.append('\n');
         return null;
      }

      public Void visitDestructor(DestructorMember member) {
         JavaMethod method = JavaMappingWriter.this.module.optMethodInfo(member);
         if (method == null) {
            return null;
         }

         JavaMappingWriter.this.result.append(":destructor=");
         JavaMappingWriter.this.result.append(method.getMapping(this.definition));
         JavaMappingWriter.this.result.append('\n');
         return null;
      }

      public Void visitMethod(MethodMember member) {
         JavaMethod method = JavaMappingWriter.this.module.optMethodInfo(member);
         if (method == null) {
            return null;
         }

         JavaMappingWriter.this.result.append(":method:");
         JavaMappingWriter.this.result.append(member.name);
         JavaMappingWriter.this.result.append(member.header.getCanonical());
         JavaMappingWriter.this.result.append("=");
         JavaMappingWriter.this.result.append(method.getMapping(this.definition));
         JavaMappingWriter.this.result.append('\n');
         return null;
      }

      public Void visitGetter(GetterMember member) {
         JavaMethod method = JavaMappingWriter.this.module.optMethodInfo(member);
         if (method == null) {
            return null;
         }

         JavaMappingWriter.this.result.append(":getter:");
         JavaMappingWriter.this.result.append(member.name);
         JavaMappingWriter.this.result.append("=");
         JavaMappingWriter.this.result.append(method.getMapping(this.definition));
         JavaMappingWriter.this.result.append('\n');
         return null;
      }

      public Void visitSetter(SetterMember member) {
         JavaMethod method = JavaMappingWriter.this.module.optMethodInfo(member);
         if (method == null) {
            return null;
         }

         JavaMappingWriter.this.result.append(":setter:");
         JavaMappingWriter.this.result.append(member.name);
         JavaMappingWriter.this.result.append("=");
         JavaMappingWriter.this.result.append(method.getMapping(this.definition));
         JavaMappingWriter.this.result.append('\n');
         return null;
      }

      public Void visitOperator(OperatorMember member) {
         JavaMethod method = JavaMappingWriter.this.module.optMethodInfo(member);
         if (method == null) {
            return null;
         }

         JavaMappingWriter.this.result.append(":operator:");
         JavaMappingWriter.this.result.append(member.operator.name().toLowerCase());
         JavaMappingWriter.this.result.append(member.header.getCanonical());
         JavaMappingWriter.this.result.append("=");
         JavaMappingWriter.this.result.append(method.getMapping(this.definition));
         JavaMappingWriter.this.result.append('\n');
         return null;
      }

      public Void visitCaster(CasterMember member) {
         JavaMethod method = JavaMappingWriter.this.module.optMethodInfo(member);
         if (method == null) {
            return null;
         }

         JavaMappingWriter.this.result.append(":caster:");
         JavaMappingWriter.this.result.append(member.toType.toString());
         JavaMappingWriter.this.result.append("=");
         JavaMappingWriter.this.result.append(method.getMapping(this.definition));
         JavaMappingWriter.this.result.append('\n');
         return null;
      }

      public Void visitCustomIterator(IteratorMember member) {
         return null;
      }

      public Void visitCaller(CallerMember member) {
         JavaMethod method = JavaMappingWriter.this.module.optMethodInfo(member);
         if (method == null) {
            return null;
         }

         JavaMappingWriter.this.result.append(":caller:");
         JavaMappingWriter.this.result.append(member.header.getCanonical());
         JavaMappingWriter.this.result.append("=");
         JavaMappingWriter.this.result.append(method.getMapping(this.definition));
         JavaMappingWriter.this.result.append('\n');
         return null;
      }

      public Void visitImplementation(ImplementationMember member) {
         JavaImplementation implementation = JavaMappingWriter.this.module.getImplementationInfo(member);
         return null;
      }

      public Void visitInnerDefinition(InnerDefinitionMember member) {
         return null;
      }

      public Void visitStaticInitializer(StaticInitializerMember member) {
         return null;
      }
   }
}
