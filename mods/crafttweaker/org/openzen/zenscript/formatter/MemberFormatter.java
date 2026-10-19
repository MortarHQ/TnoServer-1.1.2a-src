package org.openzen.zenscript.formatter;

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
import org.openzen.zenscript.codemodel.statement.Statement;

public class MemberFormatter implements MemberVisitor<Void> {
   private final ScriptFormattingSettings settings;
   private final StringBuilder output;
   private final String indent;
   private final TypeFormatter typeFormatter;
   private boolean isFirst = true;
   private boolean wasField = false;

   public MemberFormatter(ScriptFormattingSettings settings, StringBuilder output, String indent, TypeFormatter typeFormatter) {
      this.settings = settings;
      this.output = output;
      this.indent = indent;
      this.typeFormatter = typeFormatter;
   }

   private void visit(boolean field) {
      this.output.append(this.indent);
      if (this.isFirst) {
         this.isFirst = false;
      } else if (!field || this.wasField != field) {
         this.output.append("\n").append(this.indent);
      }

      this.wasField = field;
   }

   public Void visitConst(ConstMember member) {
      this.visit(true);
      FormattingUtils.formatModifiers(this.output, member.getSpecifiedModifiers());
      this.output
         .append("const")
         .append(member.name)
         .append(" as ")
         .append(this.typeFormatter.format(member.getType()))
         .append(" = ")
         .append(member.value.accept(new ExpressionFormatter(this.settings, this.typeFormatter, this.indent)))
         .append(";\n");
      return null;
   }

   public Void visitField(FieldMember member) {
      this.visit(true);
      FormattingUtils.formatModifiers(this.output, member.getSpecifiedModifiers());
      this.output.append(member.isFinal() ? "val " : "var ").append(member.name).append(" as ").append(this.typeFormatter.format(member.getType()));
      if (member.initializer != null) {
         this.output.append(" = ").append(member.initializer.accept(new ExpressionFormatter(this.settings, this.typeFormatter, this.indent)));
      }

      this.output.append(";\n");
      return null;
   }

   public Void visitConstructor(ConstructorMember member) {
      this.visit(false);
      FormattingUtils.formatModifiers(this.output, member.getSpecifiedModifiers());
      this.output.append("this");
      FormattingUtils.formatHeader(this.output, this.settings, member.header, this.typeFormatter);
      this.formatBody(member.body);
      return null;
   }

   public Void visitDestructor(DestructorMember member) {
      this.visit(false);
      FormattingUtils.formatModifiers(this.output, member.getSpecifiedModifiers());
      this.output.append("this");
      this.formatBody(member.body);
      return null;
   }

   public Void visitMethod(MethodMember member) {
      this.visit(false);
      FormattingUtils.formatModifiers(this.output, member.getSpecifiedModifiers());
      this.output.append(member.name);
      FormattingUtils.formatHeader(this.output, this.settings, member.header, this.typeFormatter);
      this.formatBody(member.body);
      return null;
   }

   public Void visitGetter(GetterMember member) {
      this.visit(false);
      FormattingUtils.formatModifiers(this.output, member.getSpecifiedModifiers());
      this.output.append("get ");
      this.output.append(member.name);
      this.output.append(" as ");
      this.output.append(this.typeFormatter.format(member.getType()));
      this.formatBody(member.body);
      return null;
   }

   public Void visitSetter(SetterMember member) {
      this.visit(false);
      FormattingUtils.formatModifiers(this.output, member.getSpecifiedModifiers());
      this.output.append("set ");
      this.output.append(member.name);
      this.output.append(" as ");
      this.output.append(this.typeFormatter.format(member.getType()));
      this.formatBody(member.body);
      return null;
   }

   public Void visitOperator(OperatorMember member) {
      this.visit(false);
      FormattingUtils.formatModifiers(this.output, member.getSpecifiedModifiers());
      switch (member.operator) {
         case ADD:
            this.output.append("+");
            break;
         case SUB:
            this.output.append("-");
            break;
         case MUL:
            this.output.append("*");
            break;
         case DIV:
            this.output.append("/");
            break;
         case MOD:
            this.output.append("%");
            break;
         case CAT:
            this.output.append("~");
            break;
         case OR:
            this.output.append("|");
            break;
         case AND:
            this.output.append("&");
            break;
         case XOR:
            this.output.append("^");
            break;
         case NEG:
            this.output.append("-");
            break;
         case NOT:
            this.output.append("!");
            break;
         case INDEXSET:
            this.output.append("[]=");
            break;
         case INDEXGET:
            this.output.append("[]");
            break;
         case CONTAINS:
            this.output.append("in");
            break;
         case MEMBERGETTER:
            this.output.append(".");
            break;
         case MEMBERSETTER:
            this.output.append(".=");
            break;
         case EQUALS:
            this.output.append("==");
            break;
         case ADDASSIGN:
            this.output.append("+=");
            break;
         case SUBASSIGN:
            this.output.append("-=");
            break;
         case MULASSIGN:
            this.output.append("*=");
            break;
         case DIVASSIGN:
            this.output.append("/=");
            break;
         case MODASSIGN:
            this.output.append("%=");
            break;
         case CATASSIGN:
            this.output.append("~=");
            break;
         case ORASSIGN:
            this.output.append("|=");
            break;
         case ANDASSIGN:
            this.output.append("&=");
            break;
         case XORASSIGN:
            this.output.append("^=");
            break;
         case INCREMENT:
            this.output.append("++");
            break;
         case DECREMENT:
            this.output.append("--");
            break;
         default:
            throw new UnsupportedOperationException("Unknown operator: " + member.operator);
      }

      FormattingUtils.formatHeader(this.output, this.settings, member.header, this.typeFormatter);
      this.formatBody(member.body);
      return null;
   }

   public Void visitCaster(CasterMember member) {
      this.visit(false);
      FormattingUtils.formatModifiers(this.output, member.getSpecifiedModifiers());
      this.output.append(" as ");
      this.output.append(this.typeFormatter.format(member.toType));
      this.formatBody(member.body);
      return null;
   }

   public Void visitCustomIterator(IteratorMember member) {
      this.visit(false);
      throw new UnsupportedOperationException("Not supported yet.");
   }

   public Void visitCaller(CallerMember member) {
      this.visit(false);
      FormattingUtils.formatModifiers(this.output, member.getSpecifiedModifiers());
      FormattingUtils.formatHeader(this.output, this.settings, member.header, this.typeFormatter);
      this.formatBody(member.body);
      return null;
   }

   public Void visitImplementation(ImplementationMember implementation) {
      this.visit(false);
      FormattingUtils.formatModifiers(this.output, implementation.getSpecifiedModifiers());
      this.output.append("implements ");
      this.output.append(implementation.type.accept(this.typeFormatter));
      if (this.settings.classBracketOnSameLine) {
         this.output.append("{\n");
      } else {
         this.output.append("\n").append(this.indent).append("{\n");
      }

      for (IDefinitionMember member : implementation.members) {
         member.accept(new MemberFormatter(this.settings, this.output, this.indent + this.settings.indent, this.typeFormatter));
      }

      this.output.append("}\n");
      return null;
   }

   public Void visitInnerDefinition(InnerDefinitionMember member) {
      this.visit(false);
      String formatted = member.innerDefinition
         .accept(new DefinitionFormatter(this.settings, this.typeFormatter, this.indent + this.settings.indent))
         .toString();
      this.output.append(formatted);
      return null;
   }

   private void formatBody(Statement body) {
      FormattingUtils.formatBody(this.output, this.settings, this.indent, this.typeFormatter, body);
   }

   public Void visitStaticInitializer(StaticInitializerMember member) {
      this.visit(false);
      this.output.append("static ");
      this.formatBody(member.body);
      return null;
   }
}
