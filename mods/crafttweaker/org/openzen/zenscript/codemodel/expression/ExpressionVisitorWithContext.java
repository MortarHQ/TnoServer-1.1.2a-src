package org.openzen.zenscript.codemodel.expression;

public interface ExpressionVisitorWithContext<C, R> {
   R visitAndAnd(C var1, AndAndExpression var2);

   R visitArray(C var1, ArrayExpression var2);

   R visitCompare(C var1, CompareExpression var2);

   R visitCall(C var1, CallExpression var2);

   R visitCallStatic(C var1, CallStaticExpression var2);

   R visitCapturedClosure(C var1, CapturedClosureExpression var2);

   R visitCapturedDirect(C var1, CapturedDirectExpression var2);

   R visitCapturedLocalVariable(C var1, CapturedLocalVariableExpression var2);

   R visitCapturedParameter(C var1, CapturedParameterExpression var2);

   R visitCapturedThis(C var1, CapturedThisExpression var2);

   R visitCast(C var1, CastExpression var2);

   R visitCheckNull(C var1, CheckNullExpression var2);

   R visitCoalesce(C var1, CoalesceExpression var2);

   R visitConditional(C var1, ConditionalExpression var2);

   R visitConst(C var1, ConstExpression var2);

   R visitConstantBool(C var1, ConstantBoolExpression var2);

   R visitConstantByte(C var1, ConstantByteExpression var2);

   R visitConstantChar(C var1, ConstantCharExpression var2);

   R visitConstantDouble(C var1, ConstantDoubleExpression var2);

   R visitConstantFloat(C var1, ConstantFloatExpression var2);

   R visitConstantInt(C var1, ConstantIntExpression var2);

   R visitConstantLong(C var1, ConstantLongExpression var2);

   R visitConstantSByte(C var1, ConstantSByteExpression var2);

   R visitConstantShort(C var1, ConstantShortExpression var2);

   R visitConstantString(C var1, ConstantStringExpression var2);

   R visitConstantUInt(C var1, ConstantUIntExpression var2);

   R visitConstantULong(C var1, ConstantULongExpression var2);

   R visitConstantUShort(C var1, ConstantUShortExpression var2);

   R visitConstantUSize(C var1, ConstantUSizeExpression var2);

   R visitConstructorThisCall(C var1, ConstructorThisCallExpression var2);

   R visitConstructorSuperCall(C var1, ConstructorSuperCallExpression var2);

   R visitEnumConstant(C var1, EnumConstantExpression var2);

   R visitFunction(C var1, FunctionExpression var2);

   R visitGetField(C var1, GetFieldExpression var2);

   R visitGetFunctionParameter(C var1, GetFunctionParameterExpression var2);

   R visitGetLocalVariable(C var1, GetLocalVariableExpression var2);

   R visitGetMatchingVariantField(C var1, GetMatchingVariantField var2);

   R visitGetStaticField(C var1, GetStaticFieldExpression var2);

   R visitGetter(C var1, GetterExpression var2);

   R visitGlobal(C var1, GlobalExpression var2);

   R visitGlobalCall(C var1, GlobalCallExpression var2);

   R visitInterfaceCast(C var1, InterfaceCastExpression var2);

   R visitInvalid(C var1, InvalidExpression var2);

   R visitInvalidAssign(C var1, InvalidAssignExpression var2);

   R visitIs(C var1, IsExpression var2);

   R visitMakeConst(C var1, MakeConstExpression var2);

   R visitMap(C var1, MapExpression var2);

   R visitMatch(C var1, MatchExpression var2);

   R visitNew(C var1, NewExpression var2);

   R visitNull(C var1, NullExpression var2);

   R visitOrOr(C var1, OrOrExpression var2);

   R visitPanic(C var1, PanicExpression var2);

   R visitPlatformSpecific(C var1, Expression var2);

   R visitPostCall(C var1, PostCallExpression var2);

   R visitRange(C var1, RangeExpression var2);

   R visitSameObject(C var1, SameObjectExpression var2);

   R visitSetField(C var1, SetFieldExpression var2);

   R visitSetFunctionParameter(C var1, SetFunctionParameterExpression var2);

   R visitSetLocalVariable(C var1, SetLocalVariableExpression var2);

   R visitSetStaticField(C var1, SetStaticFieldExpression var2);

   R visitSetter(C var1, SetterExpression var2);

   R visitStaticGetter(C var1, StaticGetterExpression var2);

   R visitStaticSetter(C var1, StaticSetterExpression var2);

   R visitSupertypeCast(C var1, SupertypeCastExpression var2);

   R visitSubtypeCast(C var1, SubtypeCastExpression var2);

   R visitThis(C var1, ThisExpression var2);

   R visitThrow(C var1, ThrowExpression var2);

   R visitTryConvert(C var1, TryConvertExpression var2);

   R visitTryRethrowAsException(C var1, TryRethrowAsExceptionExpression var2);

   R visitTryRethrowAsResult(C var1, TryRethrowAsResultExpression var2);

   R visitVariantValue(C var1, VariantValueExpression var2);

   R visitWrapOptional(C var1, WrapOptionalExpression var2);
}
