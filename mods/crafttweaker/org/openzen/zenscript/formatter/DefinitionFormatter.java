package org.openzen.zenscript.formatter;

import java.util.List;
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
import org.openzen.zenscript.codemodel.member.EnumConstantMember;
import org.openzen.zenscript.codemodel.member.IDefinitionMember;

public class DefinitionFormatter implements DefinitionVisitor<Void> {
   private final ScriptFormattingSettings settings;
   private final TypeFormatter typeFormatter;
   private final StringBuilder output = new StringBuilder();
   private final String indent;

   public DefinitionFormatter(ScriptFormattingSettings settings, TypeFormatter typeFormatter, String indent) {
      this.settings = settings;
      this.typeFormatter = typeFormatter;
      this.indent = indent;
   }

   public Void visitClass(ClassDefinition definition) {
      FormattingUtils.formatModifiers(this.output, definition.modifiers);
      this.output.append("class ");
      this.output.append(definition.name);
      FormattingUtils.formatTypeParameters(this.output, definition.typeParameters, this.typeFormatter);
      this.output.append(" ");
      if (definition.getSuperType() != null) {
         this.output.append("extends ");
         this.output.append(definition.getSuperType().accept(this.typeFormatter));
         this.output.append(" ");
      }

      if (this.settings.classBracketOnSameLine) {
         this.output.append("{\n");
      } else {
         this.output.append("\n").append(this.indent).append("{\n");
      }

      MemberFormatter memberFormatter = new MemberFormatter(this.settings, this.output, this.indent + this.settings.indent, this.typeFormatter);

      for (IDefinitionMember member : definition.members) {
         member.accept(memberFormatter);
      }

      this.output.append("}\n");
      return null;
   }

   public Void visitInterface(InterfaceDefinition definition) {
      FormattingUtils.formatModifiers(this.output, definition.modifiers);
      this.output.append("class ");
      this.output.append(definition.name);
      FormattingUtils.formatTypeParameters(this.output, definition.typeParameters, this.typeFormatter);
      this.output.append(" ");
      if (this.settings.classBracketOnSameLine) {
         this.output.append("{\n");
      } else {
         this.output.append("\n").append(this.indent).append("{\n");
      }

      MemberFormatter memberFormatter = new MemberFormatter(this.settings, this.output, this.indent + this.settings.indent, this.typeFormatter);

      for (IDefinitionMember member : definition.members) {
         member.accept(memberFormatter);
      }

      this.output.append("}\n");
      return null;
   }

   public Void visitEnum(EnumDefinition definition) {
      FormattingUtils.formatModifiers(this.output, definition.modifiers);
      this.output.append("enum ");
      this.output.append(definition.name);
      FormattingUtils.formatTypeParameters(this.output, definition.typeParameters, this.typeFormatter);
      this.output.append(" ");
      if (this.settings.classBracketOnSameLine) {
         this.output.append("{\n");
      } else {
         this.output.append("\n").append(this.indent).append("{\n");
      }

      List<EnumConstantMember> enumConstants = definition.enumConstants;
      boolean first = true;
      ExpressionFormatter expressionFormatter = new ExpressionFormatter(this.settings, this.typeFormatter, this.indent);

      for (EnumConstantMember enumConstant : enumConstants) {
         if (first) {
            first = false;
         } else {
            this.output.append(",\n");
         }

         this.output.append(this.indent).append(this.settings.indent).append(enumConstant.name);
         if (enumConstant.constructor != null) {
            FormattingUtils.formatCall(this.output, this.typeFormatter, expressionFormatter, enumConstant.constructor.arguments);
         }
      }

      if (definition.members.size() > enumConstants.size()) {
         this.output.append(";\n").append(this.indent).append(this.settings.indent).append("\n");
         MemberFormatter memberFormatter = new MemberFormatter(this.settings, this.output, this.indent + this.settings.indent, this.typeFormatter);

         for (IDefinitionMember member : definition.members) {
            member.accept(memberFormatter);
         }
      }

      this.output.append("}\n");
      return null;
   }

   public Void visitStruct(StructDefinition definition) {
      return null;
   }

   public Void visitFunction(FunctionDefinition definition) {
      for (IDefinitionMember member : definition.members) {
         if (member instanceof CallerMember caller) {
            FormattingUtils.formatModifiers(this.output, definition.modifiers);
            this.output.append("function ");
            this.output.append(definition.name);
            FormattingUtils.formatHeader(this.output, this.settings, caller.header, this.typeFormatter);
            FormattingUtils.formatBody(this.output, this.settings, this.indent, this.typeFormatter, caller.body);
         }
      }

      return null;
   }

   public Void visitExpansion(ExpansionDefinition definition) {
      return null;
   }

   public Void visitAlias(AliasDefinition definition) {
      return null;
   }

   public Void visitVariant(VariantDefinition variant) {
      FormattingUtils.formatModifiers(this.output, variant.modifiers);
      this.output.append("variant ");
      this.output.append(variant.name);
      FormattingUtils.formatTypeParameters(this.output, variant.typeParameters, this.typeFormatter);
      this.output.append(" ");
      if (this.settings.classBracketOnSameLine) {
         this.output.append("{\n");
      } else {
         this.output.append("\n").append(this.indent).append("{\n");
      }

      List<VariantDefinition.Option> options = variant.options;
      boolean first = true;

      for (VariantDefinition.Option option : options) {
         if (first) {
            first = false;
         } else {
            this.output.append(",\n");
         }

         this.output.append(this.indent).append(this.settings.indent).append(option.name);
         if (option.types.length > 0) {
            this.output.append("(");

            for (int i = 0; i < option.types.length; i++) {
               if (i > 0) {
                  this.output.append(", ");
               }

               this.output.append(this.typeFormatter.format(option.types[i]));
            }
         }
      }

      if (variant.members.size() > options.size()) {
         this.output.append(";\n").append(this.indent).append(this.settings.indent).append("\n");
         MemberFormatter memberFormatter = new MemberFormatter(this.settings, this.output, this.indent + this.settings.indent, this.typeFormatter);

         for (IDefinitionMember member : variant.members) {
            member.accept(memberFormatter);
         }
      }

      this.output.append("}\n");
      return null;
   }

   @Override
   public String toString() {
      return this.output.toString();
   }
}
