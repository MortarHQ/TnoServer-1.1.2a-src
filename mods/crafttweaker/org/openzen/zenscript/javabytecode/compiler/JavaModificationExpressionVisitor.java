package org.openzen.zenscript.javabytecode.compiler;

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
import org.openzen.zenscript.codemodel.type.BasicTypeID;
import org.openzen.zenscript.codemodel.type.TypeID;
import org.openzen.zenscript.javabytecode.JavaBytecodeContext;
import org.openzen.zenscript.javabytecode.JavaLocalVariableInfo;
import org.openzen.zenscript.javashared.JavaCompiledModule;
import org.openzen.zenscript.javashared.JavaField;
import org.openzen.zenscript.javashared.JavaParameterInfo;

public class JavaModificationExpressionVisitor implements ExpressionVisitor<Void> {
   private final JavaBytecodeContext context;
   private final JavaCompiledModule module;
   private final JavaWriter javaWriter;
   private final JavaExpressionVisitor expressionVisitor;
   private final Runnable modification;
   private final JavaModificationExpressionVisitor.PushOption push;

   public JavaModificationExpressionVisitor(
      JavaBytecodeContext context,
      JavaCompiledModule module,
      JavaWriter javaWriter,
      JavaExpressionVisitor expressionVisitor,
      Runnable modification,
      JavaModificationExpressionVisitor.PushOption push
   ) {
      this.context = context;
      this.module = module;
      this.javaWriter = javaWriter;
      this.expressionVisitor = expressionVisitor;
      this.modification = modification;
      this.push = push;
   }

   private void modify(TypeID type) {
      boolean large = type == BasicTypeID.DOUBLE || type == BasicTypeID.LONG;
      this.modify(large);
   }

   private void modify(boolean large) {
      if (this.push == JavaModificationExpressionVisitor.PushOption.BEFORE) {
         this.javaWriter.dup(large);
      }

      this.modification.run();
      if (this.push == JavaModificationExpressionVisitor.PushOption.AFTER) {
         this.javaWriter.dup(large);
      }
   }

   public Void visitAndAnd(AndAndExpression expression) {
      throw new UnsupportedOperationException("Invalid lvalue: &&");
   }

   public Void visitArray(ArrayExpression expression) {
      throw new UnsupportedOperationException("Invalid lvalue: array");
   }

   public Void visitCompare(CompareExpression expression) {
      throw new UnsupportedOperationException("Invalid lvalue: comparison");
   }

   public Void visitCall(CallExpression expression) {
      throw new UnsupportedOperationException("Invalid lvalue: call");
   }

   public Void visitCallStatic(CallStaticExpression expression) {
      throw new UnsupportedOperationException("Invalid lvalue: static call");
   }

   public Void visitCapturedClosure(CapturedClosureExpression expression) {
      throw new UnsupportedOperationException("Invalid lvalue: captured closure");
   }

   public Void visitCapturedDirect(CapturedDirectExpression expression) {
      throw new UnsupportedOperationException("Invalid lvalue: captured direct");
   }

   public Void visitCapturedLocalVariable(CapturedLocalVariableExpression expression) {
      throw new UnsupportedOperationException("Invalid lvalue: captured local variable cannot be modified");
   }

   public Void visitCapturedParameter(CapturedParameterExpression expression) {
      throw new UnsupportedOperationException("Invalid lvalue: captured parameter cannot be modified");
   }

   public Void visitCapturedThis(CapturedThisExpression expression) {
      throw new UnsupportedOperationException("Invalid lvalue: this");
   }

   public Void visitCast(CastExpression expression) {
      throw new UnsupportedOperationException("Invalid lvalue: cast");
   }

   public Void visitCheckNull(CheckNullExpression expression) {
      throw new UnsupportedOperationException("Invalid lvalue: null check");
   }

   public Void visitCoalesce(CoalesceExpression expression) {
      throw new UnsupportedOperationException("Invalid lvalue: coalesce operator");
   }

   public Void visitConditional(ConditionalExpression expression) {
      throw new UnsupportedOperationException("Invalid lvalue: conditional expression");
   }

   public Void visitConst(ConstExpression expression) {
      throw new UnsupportedOperationException("Invalid lvalue: constant cannot be modified");
   }

   public Void visitConstantBool(ConstantBoolExpression expression) {
      throw new UnsupportedOperationException("Invalid lvalue: constant bool");
   }

   public Void visitConstantByte(ConstantByteExpression expression) {
      throw new UnsupportedOperationException("Invalid lvalue: constant byte");
   }

   public Void visitConstantChar(ConstantCharExpression expression) {
      throw new UnsupportedOperationException("Invalid lvalue: constant char");
   }

   public Void visitConstantDouble(ConstantDoubleExpression expression) {
      throw new UnsupportedOperationException("Invalid lvalue: constant double");
   }

   public Void visitConstantFloat(ConstantFloatExpression expression) {
      throw new UnsupportedOperationException("Invalid lvalue: constant float");
   }

   public Void visitConstantInt(ConstantIntExpression expression) {
      throw new UnsupportedOperationException("Invalid lvalue: constant int");
   }

   public Void visitConstantLong(ConstantLongExpression expression) {
      throw new UnsupportedOperationException("Invalid lvalue: constant long");
   }

   public Void visitConstantSByte(ConstantSByteExpression expression) {
      throw new UnsupportedOperationException("Invalid lvalue: constant sbyte");
   }

   public Void visitConstantShort(ConstantShortExpression expression) {
      throw new UnsupportedOperationException("Invalid lvalue: constant short");
   }

   public Void visitConstantString(ConstantStringExpression expression) {
      throw new UnsupportedOperationException("Invalid lvalue: constant string");
   }

   public Void visitConstantUInt(ConstantUIntExpression expression) {
      throw new UnsupportedOperationException("Invalid lvalue: constant uint");
   }

   public Void visitConstantULong(ConstantULongExpression expression) {
      throw new UnsupportedOperationException("Invalid lvalue: constant ulong");
   }

   public Void visitConstantUShort(ConstantUShortExpression expression) {
      throw new UnsupportedOperationException("Invalid lvalue: constant ushort");
   }

   public Void visitConstantUSize(ConstantUSizeExpression expression) {
      throw new UnsupportedOperationException("Invalid lvalue: constant usize");
   }

   public Void visitConstructorThisCall(ConstructorThisCallExpression expression) {
      throw new UnsupportedOperationException("Invalid lvalue: constructor forwarding call");
   }

   public Void visitConstructorSuperCall(ConstructorSuperCallExpression expression) {
      throw new UnsupportedOperationException("Invalid lvalue: super forwarding call");
   }

   public Void visitEnumConstant(EnumConstantExpression expression) {
      throw new UnsupportedOperationException("Invalid lvalue: enum constant");
   }

   public Void visitFunction(FunctionExpression expression) {
      throw new UnsupportedOperationException("Invalid lvalue: function");
   }

   public Void visitGetField(GetFieldExpression expression) {
      JavaField field = this.context.getJavaField(expression.field);
      expression.target.accept(this.expressionVisitor);
      this.javaWriter.getField(field);
      this.modify(expression.field.getType());
      this.javaWriter.putField(field);
      return null;
   }

   public Void visitGetFunctionParameter(GetFunctionParameterExpression expression) {
      JavaParameterInfo parameter = this.module.getParameterInfo(expression.parameter);
      this.javaWriter.load(parameter);
      this.modify(expression.type);
      this.javaWriter.store(parameter);
      return null;
   }

   public Void visitGetLocalVariable(GetLocalVariableExpression expression) {
      JavaLocalVariableInfo variable = this.javaWriter.getLocalVariable(expression.variable.variable);
      this.javaWriter.load(variable);
      this.modify(expression.type);
      this.javaWriter.store(variable);
      return null;
   }

   public Void visitGetMatchingVariantField(GetMatchingVariantField expression) {
      throw new UnsupportedOperationException("Invalid lvalue: matching variant field");
   }

   public Void visitGetStaticField(GetStaticFieldExpression expression) {
      JavaField field = this.context.getJavaField(expression.field);
      this.javaWriter.getStaticField(field);
      this.modify(expression.type);
      this.javaWriter.putStaticField(field);
      return null;
   }

   public Void visitGetter(GetterExpression expression) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   public Void visitGlobal(GlobalExpression expression) {
      throw new UnsupportedOperationException("Invalid lvalue: global");
   }

   public Void visitGlobalCall(GlobalCallExpression expression) {
      throw new UnsupportedOperationException("Invalid lvalue: global call");
   }

   public Void visitInterfaceCast(InterfaceCastExpression expression) {
      throw new UnsupportedOperationException("Invalid lvalue: cast");
   }

   public Void visitIs(IsExpression expression) {
      throw new UnsupportedOperationException("Invalid lvalue: is");
   }

   public Void visitMakeConst(MakeConstExpression expression) {
      throw new UnsupportedOperationException("Invalid lvalue: const");
   }

   public Void visitMap(MapExpression expression) {
      throw new UnsupportedOperationException("Invalid lvalue: map");
   }

   public Void visitMatch(MatchExpression expression) {
      throw new UnsupportedOperationException("Invalid lvalue: match");
   }

   public Void visitNew(NewExpression expression) {
      throw new UnsupportedOperationException("Invalid lvalue: new");
   }

   public Void visitNull(NullExpression expression) {
      throw new UnsupportedOperationException("Invalid lvalue: null");
   }

   public Void visitOrOr(OrOrExpression expression) {
      throw new UnsupportedOperationException("Invalid lvalue: ||");
   }

   public Void visitPanic(PanicExpression expression) {
      throw new UnsupportedOperationException("Invalid lvalue: panic");
   }

   public Void visitPlatformSpecific(Expression expression) {
      throw new UnsupportedOperationException("Invalid lvalue: platform-specific expression");
   }

   public Void visitPostCall(PostCallExpression expression) {
      throw new UnsupportedOperationException("Invalid lvalue: post call");
   }

   public Void visitRange(RangeExpression expression) {
      throw new UnsupportedOperationException("Invalid lvalue: range");
   }

   public Void visitSameObject(SameObjectExpression expression) {
      throw new UnsupportedOperationException("Invalid lvalue: ===");
   }

   public Void visitSetField(SetFieldExpression expression) {
      throw new UnsupportedOperationException("Invalid lvalue: set field");
   }

   public Void visitSetFunctionParameter(SetFunctionParameterExpression expression) {
      throw new UnsupportedOperationException("Invalid lvalue: set function parameter");
   }

   public Void visitSetLocalVariable(SetLocalVariableExpression expression) {
      throw new UnsupportedOperationException("Invalid lvalue: set local variable");
   }

   public Void visitSetStaticField(SetStaticFieldExpression expression) {
      throw new UnsupportedOperationException("Invalid lvalue: set static field");
   }

   public Void visitSetter(SetterExpression expression) {
      throw new UnsupportedOperationException("Invalid lvalue: setter");
   }

   public Void visitStaticGetter(StaticGetterExpression expression) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   public Void visitStaticSetter(StaticSetterExpression expression) {
      throw new UnsupportedOperationException("Invalid lvalue: static setter");
   }

   public Void visitSupertypeCast(SupertypeCastExpression expression) {
      throw new UnsupportedOperationException("Invalid lvalue: cast");
   }

   public Void visitSubtypeCast(SubtypeCastExpression expression) {
      throw new UnsupportedOperationException("Invalid lvalue: cast");
   }

   public Void visitThis(ThisExpression expression) {
      throw new UnsupportedOperationException("Invalid lvalue: this");
   }

   public Void visitThrow(ThrowExpression expression) {
      throw new UnsupportedOperationException("Invalid lvalue: throw");
   }

   public Void visitTryConvert(TryConvertExpression expression) {
      throw new UnsupportedOperationException("Invalid lvalue: try conversion");
   }

   public Void visitTryRethrowAsException(TryRethrowAsExceptionExpression expression) {
      throw new UnsupportedOperationException("Invalid lvalue: try rethrow");
   }

   public Void visitTryRethrowAsResult(TryRethrowAsResultExpression expression) {
      throw new UnsupportedOperationException("Invalid lvalue: try rethrow");
   }

   public Void visitVariantValue(VariantValueExpression expression) {
      throw new UnsupportedOperationException("Invalid lvalue: variant value");
   }

   public Void visitWrapOptional(WrapOptionalExpression expression) {
      throw new UnsupportedOperationException("Invalid lvalue: wrap optional");
   }

   public enum PushOption {
      NONE,
      BEFORE,
      AFTER;
   }
}
