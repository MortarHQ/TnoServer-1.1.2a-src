package org.openzen.zenscript.formatter;

import org.openzen.zencode.shared.StringExpansion;
import org.openzen.zenscript.codemodel.OperatorType;
import org.openzen.zenscript.codemodel.expression.AndAndExpression;
import org.openzen.zenscript.codemodel.expression.ArrayExpression;
import org.openzen.zenscript.codemodel.expression.CallExpression;
import org.openzen.zenscript.codemodel.expression.CallStaticExpression;
import org.openzen.zenscript.codemodel.expression.CapturedClosureExpression;
import org.openzen.zenscript.codemodel.expression.CapturedDirectExpression;
import org.openzen.zenscript.codemodel.expression.CapturedLocalVariableExpression;
import org.openzen.zenscript.codemodel.expression.CapturedParameterExpression;
import org.openzen.zenscript.codemodel.expression.CapturedThisExpression;
import org.openzen.zenscript.codemodel.expression.CastExpression;
import org.openzen.zenscript.codemodel.expression.CheckNullExpression;
import org.openzen.zenscript.codemodel.expression.CoalesceExpression;
import org.openzen.zenscript.codemodel.expression.CompareExpression;
import org.openzen.zenscript.codemodel.expression.ConditionalExpression;
import org.openzen.zenscript.codemodel.expression.ConstExpression;
import org.openzen.zenscript.codemodel.expression.ConstantBoolExpression;
import org.openzen.zenscript.codemodel.expression.ConstantByteExpression;
import org.openzen.zenscript.codemodel.expression.ConstantCharExpression;
import org.openzen.zenscript.codemodel.expression.ConstantDoubleExpression;
import org.openzen.zenscript.codemodel.expression.ConstantFloatExpression;
import org.openzen.zenscript.codemodel.expression.ConstantIntExpression;
import org.openzen.zenscript.codemodel.expression.ConstantLongExpression;
import org.openzen.zenscript.codemodel.expression.ConstantSByteExpression;
import org.openzen.zenscript.codemodel.expression.ConstantShortExpression;
import org.openzen.zenscript.codemodel.expression.ConstantStringExpression;
import org.openzen.zenscript.codemodel.expression.ConstantUIntExpression;
import org.openzen.zenscript.codemodel.expression.ConstantULongExpression;
import org.openzen.zenscript.codemodel.expression.ConstantUShortExpression;
import org.openzen.zenscript.codemodel.expression.ConstantUSizeExpression;
import org.openzen.zenscript.codemodel.expression.ConstructorSuperCallExpression;
import org.openzen.zenscript.codemodel.expression.ConstructorThisCallExpression;
import org.openzen.zenscript.codemodel.expression.EnumConstantExpression;
import org.openzen.zenscript.codemodel.expression.Expression;
import org.openzen.zenscript.codemodel.expression.ExpressionVisitor;
import org.openzen.zenscript.codemodel.expression.FunctionExpression;
import org.openzen.zenscript.codemodel.expression.GetFieldExpression;
import org.openzen.zenscript.codemodel.expression.GetFunctionParameterExpression;
import org.openzen.zenscript.codemodel.expression.GetLocalVariableExpression;
import org.openzen.zenscript.codemodel.expression.GetMatchingVariantField;
import org.openzen.zenscript.codemodel.expression.GetStaticFieldExpression;
import org.openzen.zenscript.codemodel.expression.GetterExpression;
import org.openzen.zenscript.codemodel.expression.GlobalCallExpression;
import org.openzen.zenscript.codemodel.expression.GlobalExpression;
import org.openzen.zenscript.codemodel.expression.InterfaceCastExpression;
import org.openzen.zenscript.codemodel.expression.IsExpression;
import org.openzen.zenscript.codemodel.expression.MakeConstExpression;
import org.openzen.zenscript.codemodel.expression.MapExpression;
import org.openzen.zenscript.codemodel.expression.MatchExpression;
import org.openzen.zenscript.codemodel.expression.NewExpression;
import org.openzen.zenscript.codemodel.expression.NullExpression;
import org.openzen.zenscript.codemodel.expression.OrOrExpression;
import org.openzen.zenscript.codemodel.expression.PanicExpression;
import org.openzen.zenscript.codemodel.expression.PostCallExpression;
import org.openzen.zenscript.codemodel.expression.RangeExpression;
import org.openzen.zenscript.codemodel.expression.SameObjectExpression;
import org.openzen.zenscript.codemodel.expression.SetFieldExpression;
import org.openzen.zenscript.codemodel.expression.SetFunctionParameterExpression;
import org.openzen.zenscript.codemodel.expression.SetLocalVariableExpression;
import org.openzen.zenscript.codemodel.expression.SetStaticFieldExpression;
import org.openzen.zenscript.codemodel.expression.SetterExpression;
import org.openzen.zenscript.codemodel.expression.StaticGetterExpression;
import org.openzen.zenscript.codemodel.expression.StaticSetterExpression;
import org.openzen.zenscript.codemodel.expression.SubtypeCastExpression;
import org.openzen.zenscript.codemodel.expression.SupertypeCastExpression;
import org.openzen.zenscript.codemodel.expression.ThisExpression;
import org.openzen.zenscript.codemodel.expression.ThrowExpression;
import org.openzen.zenscript.codemodel.expression.TryConvertExpression;
import org.openzen.zenscript.codemodel.expression.TryRethrowAsExceptionExpression;
import org.openzen.zenscript.codemodel.expression.TryRethrowAsResultExpression;
import org.openzen.zenscript.codemodel.expression.VariantValueExpression;
import org.openzen.zenscript.codemodel.expression.WrapOptionalExpression;
import org.openzen.zenscript.codemodel.statement.ExpressionStatement;
import org.openzen.zenscript.codemodel.statement.ReturnStatement;
import org.openzen.zenscript.formattershared.ExpressionString;

public class ExpressionFormatter implements ExpressionVisitor<ExpressionString> {
   public final TypeFormatter typeFormatter;
   public final String indent;
   private final ScriptFormattingSettings settings;

   public ExpressionFormatter(ScriptFormattingSettings settings, TypeFormatter typeFormatter, String indent) {
      this.settings = settings;
      this.typeFormatter = typeFormatter;
      this.indent = indent;
   }

   public ExpressionString visitAndAnd(AndAndExpression expression) {
      return this.binary(expression.left, expression.right, ZenScriptOperator.ANDAND);
   }

   public ExpressionString visitArray(ArrayExpression expression) {
      StringBuilder result = new StringBuilder();
      result.append("[");
      int index = 0;

      for (Expression element : expression.expressions) {
         if (index > 0) {
            result.append(", ");
         }

         result.append(element.accept(this).value);
         index++;
      }

      result.append("]");
      return new ExpressionString(result.toString(), ZenScriptOperator.PRIMARY);
   }

   public ExpressionString visitCompare(CompareExpression expression) {
      return this.binary(expression.left, expression.right, ZenScriptOperator.getComparison(expression.comparison));
   }

   public ExpressionString visitCall(CallExpression expression) {
      if (expression.member.isOperator()) {
         switch (expression.member.getOperator()) {
            case NOT:
               return this.unaryPrefix(expression.target, ZenScriptOperator.NOT, "!");
            case NEG:
               return this.unaryPrefix(expression.target, ZenScriptOperator.NEG, "-");
            case CAT:
               if (expression.arguments.arguments.length == 0) {
                  return this.unaryPrefix(expression.target, ZenScriptOperator.INVERT, "~");
               }

               return this.binary(expression.target, expression.getFirstArgument(), ZenScriptOperator.CAT);
            case ADD:
               return this.binary(expression.target, expression.getFirstArgument(), ZenScriptOperator.ADD);
            case SUB:
               return this.binary(expression.target, expression.getFirstArgument(), ZenScriptOperator.SUB);
            case MUL:
               return this.binary(expression.target, expression.getFirstArgument(), ZenScriptOperator.MUL);
            case DIV:
               return this.binary(expression.target, expression.getFirstArgument(), ZenScriptOperator.DIV);
            case MOD:
               return this.binary(expression.target, expression.getFirstArgument(), ZenScriptOperator.MOD);
            case AND:
               return this.binary(expression.target, expression.getFirstArgument(), ZenScriptOperator.AND);
            case OR:
               return this.binary(expression.target, expression.getFirstArgument(), ZenScriptOperator.OR);
            case XOR:
               return this.binary(expression.target, expression.getFirstArgument(), ZenScriptOperator.XOR);
            case CONTAINS:
               return this.binary(expression.getFirstArgument(), expression.target, ZenScriptOperator.CONTAINS);
            case EQUALS:
               return this.binary(expression.target, expression.getFirstArgument(), ZenScriptOperator.EQUALS);
            case NOTEQUALS:
               return this.binary(expression.target, expression.getFirstArgument(), ZenScriptOperator.NOTEQUALS);
            case INDEXSET: {
               StringBuilder result = new StringBuilder();
               result.append(expression.target);
               result.append("[");

               for (int i = 0; i < expression.arguments.arguments.length - 1; i++) {
                  if (i > 0) {
                     result.append(", ");
                  }

                  result.append(expression.arguments.arguments[i].accept(this).value);
               }

               result.append("] = ");
               result.append(expression.arguments.arguments[expression.arguments.arguments.length - 1].accept(this).value);
               return new ExpressionString(result.toString(), ZenScriptOperator.ASSIGN);
            }
            case INDEXGET: {
               StringBuilder result = new StringBuilder();
               if (expression.target instanceof GetLocalVariableExpression) {
                  result.append(((GetLocalVariableExpression)expression.target).variable.name);
               } else if (expression.target instanceof GetFunctionParameterExpression) {
                  result.append(((GetFunctionParameterExpression)expression.target).parameter.name);
               } else {
                  result.append(expression.target);
               }

               result.append("[");

               for (int i = 0; i < expression.arguments.arguments.length; i++) {
                  if (i > 0) {
                     result.append(", ");
                  }

                  result.append(expression.arguments.arguments[i].accept(this));
               }

               result.append("]");
               return new ExpressionString(result.toString(), ZenScriptOperator.INDEX);
            }
            case MEMBERGETTER: {
               StringBuilder result = new StringBuilder();
               result.append(expression.target);
               result.append(".get(");
               result.append(expression.getFirstArgument().accept(this));
               result.append(")");
               return new ExpressionString(result.toString(), ZenScriptOperator.MEMBER);
            }
            case MEMBERSETTER: {
               StringBuilder result = new StringBuilder();
               result.append(expression.target);
               result.append(".set(");
               result.append(expression.getFirstArgument().accept(this));
               result.append(", ");
               result.append(expression.arguments.arguments[1].accept(this));
               result.append(")");
               return new ExpressionString(result.toString(), ZenScriptOperator.MEMBER);
            }
            case ADDASSIGN:
               return this.binary(expression.target, expression.getFirstArgument(), ZenScriptOperator.ADDASSIGN);
            case SUBASSIGN:
               return this.binary(expression.target, expression.getFirstArgument(), ZenScriptOperator.SUBASSIGN);
            case MULASSIGN:
               return this.binary(expression.target, expression.getFirstArgument(), ZenScriptOperator.MULASSIGN);
            case DIVASSIGN:
               return this.binary(expression.target, expression.getFirstArgument(), ZenScriptOperator.DIVASSIGN);
            case MODASSIGN:
               return this.binary(expression.target, expression.getFirstArgument(), ZenScriptOperator.MODASSIGN);
            case CATASSIGN:
               return this.binary(expression.target, expression.getFirstArgument(), ZenScriptOperator.CATASSIGN);
            case ORASSIGN:
               return this.binary(expression.target, expression.getFirstArgument(), ZenScriptOperator.ORASSIGN);
            case ANDASSIGN:
               return this.binary(expression.target, expression.getFirstArgument(), ZenScriptOperator.ANDASSIGN);
            case XORASSIGN:
               return this.binary(expression.target, expression.getFirstArgument(), ZenScriptOperator.XORASSIGN);
            case INCREMENT:
               return this.unaryPrefix(expression.target, ZenScriptOperator.DECREMENT, "++");
            case DECREMENT:
               return this.unaryPrefix(expression.target, ZenScriptOperator.DECREMENT, "--");
            case CALL: {
               StringBuilder result = new StringBuilder();
               result.append(".");
               FormattingUtils.formatCall(result, this.typeFormatter, this, expression.arguments);
               return new ExpressionString(result.toString(), ZenScriptOperator.CALL);
            }
            case CAST: {
               StringBuilder result = new StringBuilder();
               result.append(" as ");
               result.append(this.typeFormatter.format(expression.arguments.typeArguments[0]));
               return new ExpressionString(result.toString(), ZenScriptOperator.CAST);
            }
            default:
               throw new UnsupportedOperationException("Unknown operator: " + expression.member.getOperator());
         }
      } else if (expression.member.isCaller()) {
         StringBuilder result = new StringBuilder();
         result.append(expression.target.accept(this).value);
         FormattingUtils.formatCall(result, this.typeFormatter, this, expression.arguments);
         return new ExpressionString(result.toString(), ZenScriptOperator.PRIMARY);
      } else {
         StringBuilder result = new StringBuilder();
         result.append(expression.target.accept(this).value);
         result.append(".");
         result.append(expression.member.getMethodName());
         FormattingUtils.formatCall(result, this.typeFormatter, this, expression.arguments);
         return new ExpressionString(result.toString(), ZenScriptOperator.PRIMARY);
      }
   }

   public ExpressionString visitCallStatic(CallStaticExpression expression) {
      StringBuilder result = new StringBuilder();
      result.append(expression.target.accept(this.typeFormatter));
      if (expression.member.isOperator()) {
         OperatorType operator = expression.member.getOperator();
         if (operator != OperatorType.CALL) {
            result.append(".");
            result.append(operator.operator);
         }
      } else if (!expression.member.isCaller()) {
         result.append(".");
         result.append(expression.member.getMethodName());
      }

      FormattingUtils.formatCall(result, this.typeFormatter, this, expression.arguments);
      return new ExpressionString(result.toString(), ZenScriptOperator.PRIMARY);
   }

   public ExpressionString visitCapturedClosure(CapturedClosureExpression expression) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   public ExpressionString visitCapturedDirect(CapturedDirectExpression expression) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   public ExpressionString visitCapturedLocalVariable(CapturedLocalVariableExpression expression) {
      return new ExpressionString(expression.variable.name, ZenScriptOperator.PRIMARY);
   }

   public ExpressionString visitCapturedParameter(CapturedParameterExpression expression) {
      return new ExpressionString(expression.parameter.name, ZenScriptOperator.PRIMARY);
   }

   public ExpressionString visitCapturedThis(CapturedThisExpression expression) {
      return new ExpressionString("this", ZenScriptOperator.PRIMARY);
   }

   public ExpressionString visitCast(CastExpression expression) {
      StringBuilder result = new StringBuilder();
      result.append(expression.target.accept(this).value);
      if (!expression.isImplicit) {
         result.append(" as ");
         result.append(this.typeFormatter.format(expression.member.toType));
      }

      return new ExpressionString(result.toString(), ZenScriptOperator.PRIMARY);
   }

   public ExpressionString visitCheckNull(CheckNullExpression expression) {
      return expression.value.accept(this);
   }

   public ExpressionString visitCoalesce(CoalesceExpression expression) {
      return this.binary(expression.left, expression.right, ZenScriptOperator.COALESCE);
   }

   public ExpressionString visitConditional(ConditionalExpression expression) {
      StringBuilder result = new StringBuilder();
      result.append(expression.condition.accept(this));
      result.append(" ? ");
      result.append(expression.ifThen.accept(this));
      result.append(" : ");
      result.append(expression.ifElse.accept(this));
      return new ExpressionString(result.toString(), ZenScriptOperator.TERNARY);
   }

   public ExpressionString visitConst(ConstExpression expression) {
      StringBuilder result = new StringBuilder();
      result.append(this.typeFormatter.format(expression.type));
      result.append('.');
      result.append(expression.constant.member.name);
      return new ExpressionString(result.toString(), ZenScriptOperator.MEMBER);
   }

   public ExpressionString visitConstantBool(ConstantBoolExpression expression) {
      return new ExpressionString(expression.value ? "true" : "false", ZenScriptOperator.PRIMARY);
   }

   public ExpressionString visitConstantByte(ConstantByteExpression expression) {
      return new ExpressionString(Integer.toString(expression.value) + " as byte", ZenScriptOperator.CAST);
   }

   public ExpressionString visitConstantChar(ConstantCharExpression expression) {
      return new ExpressionString(StringExpansion.escape(Character.toString(expression.value), '\'', true), ZenScriptOperator.PRIMARY);
   }

   public ExpressionString visitConstantDouble(ConstantDoubleExpression expression) {
      return new ExpressionString(Double.toString(expression.value), ZenScriptOperator.PRIMARY);
   }

   public ExpressionString visitConstantFloat(ConstantFloatExpression expression) {
      return new ExpressionString(Float.toString(expression.value), ZenScriptOperator.PRIMARY);
   }

   public ExpressionString visitConstantInt(ConstantIntExpression expression) {
      return new ExpressionString(Integer.toString(expression.value), ZenScriptOperator.PRIMARY);
   }

   public ExpressionString visitConstantLong(ConstantLongExpression expression) {
      return new ExpressionString(Long.toString(expression.value), ZenScriptOperator.PRIMARY);
   }

   public ExpressionString visitConstantSByte(ConstantSByteExpression expression) {
      return new ExpressionString(Byte.toString(expression.value) + " as sbyte", ZenScriptOperator.CAST);
   }

   public ExpressionString visitConstantShort(ConstantShortExpression expression) {
      return new ExpressionString(Integer.toString(expression.value) + " as short", ZenScriptOperator.CAST);
   }

   public ExpressionString visitConstantUSize(ConstantUSizeExpression expression) {
      return new ExpressionString(Long.toUnsignedString(expression.value) + " as usize", ZenScriptOperator.CAST);
   }

   public ExpressionString visitConstantString(ConstantStringExpression expression) {
      return new ExpressionString(
         StringExpansion.escape(expression.value, (char)(this.settings.useSingleQuotesForStrings ? '\'' : '"'), true), ZenScriptOperator.CAST
      );
   }

   public ExpressionString visitConstantUInt(ConstantUIntExpression expression) {
      return new ExpressionString(Integer.toUnsignedString(expression.value) + " as uint", ZenScriptOperator.CAST);
   }

   public ExpressionString visitConstantULong(ConstantULongExpression expression) {
      return new ExpressionString(Long.toUnsignedString(expression.value) + " as ulong", ZenScriptOperator.CAST);
   }

   public ExpressionString visitConstantUShort(ConstantUShortExpression expression) {
      return new ExpressionString(Integer.toUnsignedString(expression.value) + " as ushort", ZenScriptOperator.CAST);
   }

   public ExpressionString visitConstructorThisCall(ConstructorThisCallExpression expression) {
      StringBuilder result = new StringBuilder();
      result.append("this");
      FormattingUtils.formatCall(result, this.typeFormatter, this, expression.arguments);
      return new ExpressionString(result.toString(), ZenScriptOperator.PRIMARY);
   }

   public ExpressionString visitConstructorSuperCall(ConstructorSuperCallExpression expression) {
      StringBuilder result = new StringBuilder();
      result.append("super");
      FormattingUtils.formatCall(result, this.typeFormatter, this, expression.arguments);
      return new ExpressionString(result.toString(), ZenScriptOperator.PRIMARY);
   }

   public ExpressionString visitEnumConstant(EnumConstantExpression expression) {
      return new ExpressionString(this.typeFormatter.format(expression.type) + "." + expression.value.name, ZenScriptOperator.MEMBER);
   }

   public ExpressionString visitFunction(FunctionExpression expression) {
      StringBuilder result = new StringBuilder();
      if (expression.header.parameters.length == 1) {
         result.append(expression.header.parameters[0].name);
      } else {
         result.append('(');

         for (int i = 0; i < expression.header.parameters.length; i++) {
            if (i > 0) {
               result.append(", ");
            }

            result.append(expression.header.parameters[i].name);
         }

         result.append(')');
      }

      result.append(" => ");
      if (expression.body instanceof ReturnStatement) {
         result.append(((ReturnStatement)expression.body).value.accept(this));
      } else if (expression.body instanceof ExpressionStatement) {
         result.append(((ExpressionStatement)expression.body).expression.accept(this));
      } else {
         StatementFormatter formatter = new StatementFormatter(result, this.indent + this.settings.indent + this.settings.indent, this.settings, this);
         expression.body.accept(formatter);
      }

      return new ExpressionString(result.toString(), ZenScriptOperator.FUNCTION);
   }

   public ExpressionString visitGetField(GetFieldExpression expression) {
      StringBuilder result = new StringBuilder();
      result.append(expression.target.accept(this));
      result.append('.');
      result.append(expression.field.member.name);
      return new ExpressionString(result.toString(), ZenScriptOperator.MEMBER);
   }

   public ExpressionString visitGetFunctionParameter(GetFunctionParameterExpression expression) {
      return new ExpressionString(expression.parameter.name, ZenScriptOperator.PRIMARY);
   }

   public ExpressionString visitGetLocalVariable(GetLocalVariableExpression expression) {
      return new ExpressionString(expression.variable.name, ZenScriptOperator.PRIMARY);
   }

   public ExpressionString visitGetMatchingVariantField(GetMatchingVariantField expression) {
      return new ExpressionString(expression.value.parameters[expression.index], ZenScriptOperator.PRIMARY);
   }

   public ExpressionString visitGetStaticField(GetStaticFieldExpression expression) {
      StringBuilder result = new StringBuilder();
      result.append(this.typeFormatter.format(expression.type));
      result.append('.');
      result.append(expression.field.member.name);
      return new ExpressionString(result.toString(), ZenScriptOperator.MEMBER);
   }

   public ExpressionString visitGetter(GetterExpression expression) {
      StringBuilder result = new StringBuilder();
      result.append(expression.target.accept(this));
      result.append('.');
      result.append(expression.getter.member.name);
      return new ExpressionString(result.toString(), ZenScriptOperator.MEMBER);
   }

   public ExpressionString visitGlobal(GlobalExpression expression) {
      return new ExpressionString(expression.name, ZenScriptOperator.PRIMARY);
   }

   public ExpressionString visitGlobalCall(GlobalCallExpression expression) {
      StringBuilder result = new StringBuilder();
      result.append(expression.name);
      FormattingUtils.formatCall(result, this.typeFormatter, this, expression.arguments);
      return new ExpressionString(result.toString(), ZenScriptOperator.PRIMARY);
   }

   public ExpressionString visitInterfaceCast(InterfaceCastExpression expression) {
      StringBuilder result = new StringBuilder();
      result.append(expression.value.accept(this).value);
      result.append(" as ");
      result.append(this.typeFormatter.format(expression.type));
      return new ExpressionString(result.toString(), ZenScriptOperator.CAST);
   }

   public ExpressionString visitIs(IsExpression expression) {
      StringBuilder result = new StringBuilder();
      result.append(expression.value.accept(this).value);
      result.append(" is ");
      result.append(this.typeFormatter.format(expression.isType));
      return new ExpressionString(result.toString(), ZenScriptOperator.IS);
   }

   public ExpressionString visitMakeConst(MakeConstExpression expression) {
      return expression.accept(this);
   }

   public ExpressionString visitMap(MapExpression expression) {
      StringBuilder result = new StringBuilder();
      result.append("{");

      for (int i = 0; i < expression.keys.length; i++) {
         if (i > 0) {
            result.append(", ");
         }

         result.append(expression.keys[i].accept(this));
         result.append(": ");
         result.append(expression.values[i].accept(this));
      }

      result.append("}");
      return new ExpressionString(result.toString(), ZenScriptOperator.PRIMARY);
   }

   public ExpressionString visitMatch(MatchExpression expression) {
      StringBuilder result = new StringBuilder();
      result.append("match ");
      result.append(expression.value.accept(this));
      result.append(" {\n");
      return new ExpressionString(result.toString(), ZenScriptOperator.PRIMARY);
   }

   public ExpressionString visitNew(NewExpression expression) {
      StringBuilder result = new StringBuilder();
      result.append("new ");
      result.append(this.typeFormatter.format(expression.type));
      FormattingUtils.formatCall(result, this.typeFormatter, this, expression.arguments);
      return new ExpressionString(result.toString(), ZenScriptOperator.PRIMARY);
   }

   public ExpressionString visitNull(NullExpression expression) {
      return new ExpressionString("null", ZenScriptOperator.PRIMARY);
   }

   public ExpressionString visitOrOr(OrOrExpression expression) {
      return this.binary(expression.left, expression.right, ZenScriptOperator.OROR);
   }

   public ExpressionString visitPanic(PanicExpression expression) {
      return expression.value.accept(this).unaryPrefix(ZenScriptOperator.PANIC);
   }

   public ExpressionString visitPlatformSpecific(Expression expression) {
      throw new UnsupportedOperationException("Not yet implemented!");
   }

   public ExpressionString visitPostCall(PostCallExpression expression) {
      return this.unaryPostfix(expression.target, ZenScriptOperator.INCREMENT, expression.member.getOperator() == OperatorType.INCREMENT ? "++" : "--");
   }

   public ExpressionString visitRange(RangeExpression expression) {
      return this.binary(expression.from, expression.to, ZenScriptOperator.RANGE);
   }

   public ExpressionString visitSameObject(SameObjectExpression expression) {
      return this.binary(expression.left, expression.right, expression.inverted ? ZenScriptOperator.NOTSAME : ZenScriptOperator.SAME);
   }

   public ExpressionString visitSetField(SetFieldExpression expression) {
      return new ExpressionString(
         expression.target.accept(this) + "." + expression.field.member.name + " = " + expression.value.accept(this).value, ZenScriptOperator.ASSIGN
      );
   }

   public ExpressionString visitSetFunctionParameter(SetFunctionParameterExpression expression) {
      return new ExpressionString(expression.parameter.name + " = " + expression.value.accept(this).value, ZenScriptOperator.ASSIGN);
   }

   public ExpressionString visitSetLocalVariable(SetLocalVariableExpression expression) {
      return new ExpressionString(expression.variable.name + " = " + expression.value.accept(this).value, ZenScriptOperator.ASSIGN);
   }

   public ExpressionString visitSetStaticField(SetStaticFieldExpression expression) {
      return new ExpressionString(
         this.typeFormatter.format(expression.type) + "." + expression.field.member.name + " = " + expression.value.accept(this).value,
         ZenScriptOperator.ASSIGN
      );
   }

   public ExpressionString visitSetter(SetterExpression expression) {
      return new ExpressionString(
         expression.target.accept(this) + "." + expression.setter.member.name + " = " + expression.value.accept(this), ZenScriptOperator.ASSIGN
      );
   }

   public ExpressionString visitStaticGetter(StaticGetterExpression expression) {
      return new ExpressionString(this.typeFormatter.format(expression.type) + "." + expression.getter.member.name, ZenScriptOperator.MEMBER);
   }

   public ExpressionString visitStaticSetter(StaticSetterExpression expression) {
      return new ExpressionString(
         this.typeFormatter.format(expression.type) + "." + expression.setter.member.name + " = " + expression.setter.member.name, ZenScriptOperator.ASSIGN
      );
   }

   public ExpressionString visitSupertypeCast(SupertypeCastExpression expression) {
      return expression.value.accept(this);
   }

   public ExpressionString visitSubtypeCast(SubtypeCastExpression expression) {
      StringBuilder result = new StringBuilder(expression.value.accept(this).value);
      result.append(" as ");
      result.append(this.typeFormatter.format(expression.type));
      return new ExpressionString(result.toString(), ZenScriptOperator.CAST);
   }

   public ExpressionString visitThis(ThisExpression expression) {
      return new ExpressionString("this", ZenScriptOperator.PRIMARY);
   }

   public ExpressionString visitThrow(ThrowExpression expression) {
      return new ExpressionString("throw " + expression.value.accept(this), ZenScriptOperator.PRIMARY);
   }

   public ExpressionString visitTryConvert(TryConvertExpression expression) {
      ExpressionString value = expression.value.accept(this);
      return new ExpressionString("try?" + value.value, value.priority);
   }

   public ExpressionString visitTryRethrowAsException(TryRethrowAsExceptionExpression expression) {
      ExpressionString value = expression.value.accept(this);
      return new ExpressionString("try!" + value.value, value.priority);
   }

   public ExpressionString visitTryRethrowAsResult(TryRethrowAsResultExpression expression) {
      ExpressionString value = expression.value.accept(this);
      return new ExpressionString("try!" + value.value, value.priority);
   }

   public ExpressionString visitVariantValue(VariantValueExpression expression) {
      StringBuilder result = new StringBuilder();
      result.append("new ");
      return new ExpressionString(result.toString(), ZenScriptOperator.PRIMARY);
   }

   public ExpressionString visitWrapOptional(WrapOptionalExpression expression) {
      return expression.value.accept(this);
   }

   private ExpressionString unaryPrefix(Expression value, ZenScriptOperator operator, String operatorString) {
      return new ExpressionString(operatorString + value.accept(this).value, operator);
   }

   private ExpressionString unaryPostfix(Expression value, ZenScriptOperator operator, String operatorString) {
      return new ExpressionString(value.accept(this).value + operatorString, operator);
   }

   private ExpressionString binary(Expression left, Expression right, ZenScriptOperator operator) {
      return ExpressionString.binary(left.accept(this), right.accept(this), operator);
   }
}
