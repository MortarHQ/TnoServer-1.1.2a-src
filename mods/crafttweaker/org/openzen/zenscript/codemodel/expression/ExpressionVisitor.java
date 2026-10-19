package org.openzen.zenscript.codemodel.expression;

public interface ExpressionVisitor<T> {
   T visitAndAnd(AndAndExpression var1);

   T visitArray(ArrayExpression var1);

   T visitCompare(CompareExpression var1);

   T visitCall(CallExpression var1);

   T visitCallStatic(CallStaticExpression var1);

   T visitCapturedClosure(CapturedClosureExpression var1);

   T visitCapturedDirect(CapturedDirectExpression var1);

   T visitCapturedLocalVariable(CapturedLocalVariableExpression var1);

   T visitCapturedParameter(CapturedParameterExpression var1);

   T visitCapturedThis(CapturedThisExpression var1);

   T visitCast(CastExpression var1);

   T visitCheckNull(CheckNullExpression var1);

   T visitCoalesce(CoalesceExpression var1);

   T visitConditional(ConditionalExpression var1);

   T visitConst(ConstExpression var1);

   T visitConstantBool(ConstantBoolExpression var1);

   T visitConstantByte(ConstantByteExpression var1);

   T visitConstantChar(ConstantCharExpression var1);

   T visitConstantDouble(ConstantDoubleExpression var1);

   T visitConstantFloat(ConstantFloatExpression var1);

   T visitConstantInt(ConstantIntExpression var1);

   T visitConstantLong(ConstantLongExpression var1);

   T visitConstantSByte(ConstantSByteExpression var1);

   T visitConstantShort(ConstantShortExpression var1);

   T visitConstantString(ConstantStringExpression var1);

   T visitConstantUInt(ConstantUIntExpression var1);

   T visitConstantULong(ConstantULongExpression var1);

   T visitConstantUShort(ConstantUShortExpression var1);

   T visitConstantUSize(ConstantUSizeExpression var1);

   T visitConstructorThisCall(ConstructorThisCallExpression var1);

   T visitConstructorSuperCall(ConstructorSuperCallExpression var1);

   T visitEnumConstant(EnumConstantExpression var1);

   T visitFunction(FunctionExpression var1);

   T visitGetField(GetFieldExpression var1);

   T visitGetFunctionParameter(GetFunctionParameterExpression var1);

   T visitGetLocalVariable(GetLocalVariableExpression var1);

   T visitGetMatchingVariantField(GetMatchingVariantField var1);

   T visitGetStaticField(GetStaticFieldExpression var1);

   T visitGetter(GetterExpression var1);

   T visitGlobal(GlobalExpression var1);

   T visitGlobalCall(GlobalCallExpression var1);

   T visitInterfaceCast(InterfaceCastExpression var1);

   default T visitInvalid(InvalidExpression expression) {
      throw new RuntimeException("Invalid expression @ " + expression.position + ": " + expression.message);
   }

   default T visitInvalidAssign(InvalidAssignExpression expression) {
      throw new RuntimeException("Invalid expression @ " + expression.position + ": " + expression.target.message);
   }

   T visitIs(IsExpression var1);

   T visitMakeConst(MakeConstExpression var1);

   T visitMap(MapExpression var1);

   T visitMatch(MatchExpression var1);

   T visitNew(NewExpression var1);

   T visitNull(NullExpression var1);

   T visitOrOr(OrOrExpression var1);

   T visitPanic(PanicExpression var1);

   T visitPlatformSpecific(Expression var1);

   T visitPostCall(PostCallExpression var1);

   T visitRange(RangeExpression var1);

   T visitSameObject(SameObjectExpression var1);

   T visitSetField(SetFieldExpression var1);

   T visitSetFunctionParameter(SetFunctionParameterExpression var1);

   T visitSetLocalVariable(SetLocalVariableExpression var1);

   T visitSetStaticField(SetStaticFieldExpression var1);

   T visitSetter(SetterExpression var1);

   T visitStaticGetter(StaticGetterExpression var1);

   T visitStaticSetter(StaticSetterExpression var1);

   T visitSupertypeCast(SupertypeCastExpression var1);

   T visitSubtypeCast(SubtypeCastExpression var1);

   T visitThis(ThisExpression var1);

   T visitThrow(ThrowExpression var1);

   T visitTryConvert(TryConvertExpression var1);

   T visitTryRethrowAsException(TryRethrowAsExceptionExpression var1);

   T visitTryRethrowAsResult(TryRethrowAsResultExpression var1);

   T visitVariantValue(VariantValueExpression var1);

   T visitWrapOptional(WrapOptionalExpression var1);
}
