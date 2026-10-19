package org.openzen.zenscript.javabytecode.compiler;

import org.objectweb.asm.Label;
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
import org.openzen.zenscript.codemodel.generic.TypeParameter;
import org.openzen.zenscript.codemodel.type.BasicTypeID;
import org.openzen.zenscript.codemodel.type.member.BuiltinID;
import org.openzen.zenscript.javabytecode.JavaBytecodeContext;
import org.openzen.zenscript.javabytecode.JavaLocalVariableInfo;
import org.openzen.zenscript.javashared.JavaCompiledModule;
import org.openzen.zenscript.javashared.JavaParameterInfo;
import org.openzen.zenscript.javashared.expressions.JavaFunctionInterfaceCastExpression;

public class JavaNonPushingExpressionVisitor implements ExpressionVisitor<Void> {
   private final JavaBytecodeContext context;
   private final JavaCompiledModule module;
   private final JavaWriter javaWriter;
   private final JavaExpressionVisitor original;

   public JavaNonPushingExpressionVisitor(JavaBytecodeContext context, JavaCompiledModule module, JavaWriter javaWriter, JavaExpressionVisitor original) {
      this.context = context;
      this.module = module;
      this.javaWriter = javaWriter;
      this.original = original;
   }

   public Void visitAndAnd(AndAndExpression expression) {
      Label end = new Label();
      expression.left.accept(this.original);
      this.javaWriter.ifEQ(end);
      expression.right.accept(this);
      this.javaWriter.label(end);
      return null;
   }

   private void fallback(Expression expression) {
      expression.accept(this.original);
      if (expression.type != BasicTypeID.VOID) {
         this.javaWriter.pop(CompilerUtils.isLarge(expression.type));
      }
   }

   private void modify(Expression source, Runnable modification) {
      source.accept(
         new JavaModificationExpressionVisitor(
            this.context, this.module, this.javaWriter, this.original, modification, JavaModificationExpressionVisitor.PushOption.NONE
         )
      );
   }

   private boolean compileIncrementOrDecrement(Expression target, BuiltinID builtin) {
      if (builtin == null) {
         return false;
      }

      switch (builtin) {
         case BYTE_INC:
            this.modify(target, () -> {
               this.javaWriter.iConst1();
               this.javaWriter.iAdd();
               this.javaWriter.constant(255);
               this.javaWriter.iAnd();
            });
            return true;
         case BYTE_DEC:
            this.modify(target, () -> {
               this.javaWriter.iConst1();
               this.javaWriter.iSub();
               this.javaWriter.constant(255);
               this.javaWriter.iAnd();
            });
            return true;
         case SBYTE_INC:
            this.modify(target, () -> {
               this.javaWriter.iConst1();
               this.javaWriter.iAdd();
               this.javaWriter.i2b();
            });
            return true;
         case SBYTE_DEC:
            this.modify(target, () -> {
               this.javaWriter.iConst1();
               this.javaWriter.iSub();
               this.javaWriter.i2b();
            });
            return true;
         case SHORT_INC:
            this.modify(target, () -> {
               this.javaWriter.iConst1();
               this.javaWriter.iAdd();
               this.javaWriter.i2s();
            });
            return true;
         case SHORT_DEC:
            this.modify(target, () -> {
               this.javaWriter.iConst1();
               this.javaWriter.iSub();
               this.javaWriter.i2s();
            });
            return true;
         case USHORT_INC:
            this.modify(target, () -> {
               this.javaWriter.iConst1();
               this.javaWriter.iAdd();
               this.javaWriter.constant(65535);
               this.javaWriter.iAnd();
            });
            return true;
         case USHORT_DEC:
            this.modify(target, () -> {
               this.javaWriter.iConst1();
               this.javaWriter.iSub();
               this.javaWriter.constant(65535);
               this.javaWriter.iAnd();
            });
            return true;
         case INT_INC:
         case UINT_INC:
         case USIZE_INC:
            if (target instanceof GetLocalVariableExpression) {
               JavaLocalVariableInfo local = this.javaWriter.getLocalVariable(((GetLocalVariableExpression)target).variable.variable);
               this.javaWriter.iinc(local.local);
            } else {
               this.modify(target, () -> {
                  this.javaWriter.iConst1();
                  this.javaWriter.iAdd();
               });
            }

            return true;
         case INT_DEC:
         case UINT_DEC:
         case USIZE_DEC:
            if (target instanceof GetLocalVariableExpression) {
               JavaLocalVariableInfo local = this.javaWriter.getLocalVariable(((GetLocalVariableExpression)target).variable.variable);
               this.javaWriter.iinc(local.local, -1);
            } else {
               this.modify(target, () -> {
                  this.javaWriter.iConst1();
                  this.javaWriter.iSub();
               });
            }

            return true;
         case LONG_INC:
         case ULONG_INC:
            this.modify(target, () -> {
               this.javaWriter.constant(1L);
               this.javaWriter.lAdd();
            });
            return true;
         case LONG_DEC:
         case ULONG_DEC:
            this.modify(target, () -> {
               this.javaWriter.constant(1L);
               this.javaWriter.lSub();
            });
            return true;
         case FLOAT_INC:
            this.modify(target, () -> {
               this.javaWriter.constant(1.0F);
               this.javaWriter.fAdd();
            });
            return true;
         case FLOAT_DEC:
            this.modify(target, () -> {
               this.javaWriter.constant(1.0F);
               this.javaWriter.fSub();
            });
            return true;
         case DOUBLE_INC:
            this.modify(target, () -> {
               this.javaWriter.constant(1.0);
               this.javaWriter.dAdd();
            });
            return true;
         case DOUBLE_DEC:
            this.modify(target, () -> {
               this.javaWriter.constant(1.0);
               this.javaWriter.dSub();
            });
            return true;
         default:
            return false;
      }
   }

   public Void visitArray(ArrayExpression expression) {
      for (Expression value : expression.expressions) {
         value.accept(this);
      }

      return null;
   }

   public Void visitCompare(CompareExpression expression) {
      expression.left.accept(this);
      expression.right.accept(this);
      return null;
   }

   public Void visitCall(CallExpression expression) {
      if (!this.compileIncrementOrDecrement(expression.target, expression.member.getBuiltin())) {
         this.fallback(expression);
      }

      return null;
   }

   public Void visitCallStatic(CallStaticExpression expression) {
      this.fallback(expression);
      return null;
   }

   public Void visitCapturedClosure(CapturedClosureExpression expression) {
      return expression.value.accept(this);
   }

   public Void visitCapturedDirect(CapturedDirectExpression expression) {
      return expression.value.accept(this);
   }

   public Void visitCapturedLocalVariable(CapturedLocalVariableExpression expression) {
      return null;
   }

   public Void visitCapturedParameter(CapturedParameterExpression expression) {
      return null;
   }

   public Void visitCapturedThis(CapturedThisExpression expression) {
      return null;
   }

   public Void visitCast(CastExpression expression) {
      return expression.target.accept(this);
   }

   public Void visitCheckNull(CheckNullExpression expression) {
      return expression.value.accept(this);
   }

   public Void visitCoalesce(CoalesceExpression expression) {
      Label end = new Label();
      expression.left.accept(this.original);
      this.javaWriter.ifNonNull(end);
      expression.right.accept(this);
      this.javaWriter.label(end);
      return null;
   }

   public Void visitConditional(ConditionalExpression expression) {
      Label end = new Label();
      Label onElse = new Label();
      expression.condition.accept(this.original);
      this.javaWriter.ifEQ(onElse);
      expression.ifThen.accept(this);
      this.javaWriter.goTo(end);
      this.javaWriter.label(onElse);
      expression.ifElse.accept(this);
      this.javaWriter.label(end);
      return null;
   }

   public Void visitConst(ConstExpression expression) {
      return null;
   }

   public Void visitConstantBool(ConstantBoolExpression expression) {
      return null;
   }

   public Void visitConstantByte(ConstantByteExpression expression) {
      return null;
   }

   public Void visitConstantChar(ConstantCharExpression expression) {
      return null;
   }

   public Void visitConstantDouble(ConstantDoubleExpression expression) {
      return null;
   }

   public Void visitConstantFloat(ConstantFloatExpression expression) {
      return null;
   }

   public Void visitConstantInt(ConstantIntExpression expression) {
      return null;
   }

   public Void visitConstantLong(ConstantLongExpression expression) {
      return null;
   }

   public Void visitConstantSByte(ConstantSByteExpression expression) {
      return null;
   }

   public Void visitConstantShort(ConstantShortExpression expression) {
      return null;
   }

   public Void visitConstantString(ConstantStringExpression expression) {
      return null;
   }

   public Void visitConstantUInt(ConstantUIntExpression expression) {
      return null;
   }

   public Void visitConstantULong(ConstantULongExpression expression) {
      return null;
   }

   public Void visitConstantUShort(ConstantUShortExpression expression) {
      return null;
   }

   public Void visitConstantUSize(ConstantUSizeExpression expression) {
      return null;
   }

   public Void visitConstructorThisCall(ConstructorThisCallExpression expression) {
      this.javaWriter.loadObject(0);
      if (this.javaWriter.method.cls.isEnum()) {
         this.javaWriter.loadObject(1);
         this.javaWriter.loadInt(2);
      }

      for (Expression argument : expression.arguments.arguments) {
         argument.accept(this.original);
      }

      String internalName = this.context.getInternalName(expression.objectType);
      this.javaWriter
         .invokeSpecial(
            internalName,
            "<init>",
            this.javaWriter.method.cls.isEnum()
               ? this.context.getEnumConstructorDescriptor(expression.constructor.getHeader())
               : this.context.getMethodDescriptor(expression.constructor.getHeader())
         );
      return null;
   }

   public Void visitConstructorSuperCall(ConstructorSuperCallExpression expression) {
      this.javaWriter.loadObject(0);

      for (Expression argument : expression.arguments.arguments) {
         argument.accept(this.original);
      }

      this.javaWriter
         .invokeSpecial(
            this.context.getInternalName(expression.constructor.getOwnerType()), "<init>", this.context.getMethodDescriptor(expression.constructor.getHeader())
         );
      CompilerUtils.writeDefaultFieldInitializers(this.context, this.javaWriter, this.javaWriter.forDefinition, false);
      return null;
   }

   public Void visitEnumConstant(EnumConstantExpression expression) {
      return null;
   }

   public Void visitFunction(FunctionExpression expression) {
      return null;
   }

   public Void visitGetField(GetFieldExpression expression) {
      return expression.target.accept(this);
   }

   public Void visitGetFunctionParameter(GetFunctionParameterExpression expression) {
      return null;
   }

   public Void visitGetLocalVariable(GetLocalVariableExpression expression) {
      return null;
   }

   public Void visitGetMatchingVariantField(GetMatchingVariantField expression) {
      this.fallback(expression);
      return null;
   }

   public Void visitGetStaticField(GetStaticFieldExpression expression) {
      return null;
   }

   public Void visitGetter(GetterExpression expression) {
      return expression.target.accept(this);
   }

   public Void visitGlobal(GlobalExpression expression) {
      return null;
   }

   public Void visitGlobalCall(GlobalCallExpression expression) {
      this.fallback(expression);
      return null;
   }

   public Void visitInterfaceCast(InterfaceCastExpression expression) {
      return expression.value.accept(this);
   }

   public Void visitIs(IsExpression expression) {
      return expression.value.accept(this);
   }

   public Void visitMakeConst(MakeConstExpression expression) {
      return expression.value.accept(this);
   }

   public Void visitMap(MapExpression expression) {
      this.fallback(expression);
      return null;
   }

   public Void visitMatch(MatchExpression expression) {
      this.fallback(expression);
      return null;
   }

   public Void visitNew(NewExpression expression) {
      this.fallback(expression);
      return null;
   }

   public Void visitNull(NullExpression expression) {
      return null;
   }

   public Void visitOrOr(OrOrExpression expression) {
      Label end = new Label();
      expression.left.accept(this.original);
      this.javaWriter.ifNE(end);
      expression.right.accept(this);
      this.javaWriter.label(end);
      return null;
   }

   public Void visitPanic(PanicExpression expression) {
      this.javaWriter.newObject("java/lang/AssertionError");
      this.javaWriter.dup();
      expression.value.accept(this.original);
      this.javaWriter.invokeSpecial(AssertionError.class, "<init>", "(Ljava/lang/Object;)V");
      this.javaWriter.aThrow();
      return null;
   }

   public Void visitPlatformSpecific(Expression expression) {
      if (expression instanceof JavaFunctionInterfaceCastExpression) {
         ((JavaFunctionInterfaceCastExpression)expression).value.accept(this);
         return null;
      } else {
         throw new AssertionError("Unrecognized platform specific expression: " + expression);
      }
   }

   public Void visitPostCall(PostCallExpression expression) {
      if (!this.compileIncrementOrDecrement(expression.target, expression.member.getBuiltin())) {
         this.fallback(expression);
      }

      return null;
   }

   public Void visitRange(RangeExpression expression) {
      expression.from.accept(this);
      expression.to.accept(this);
      return null;
   }

   public Void visitSameObject(SameObjectExpression expression) {
      expression.left.accept(this);
      expression.right.accept(this);
      return null;
   }

   public Void visitSetField(SetFieldExpression expression) {
      expression.target.accept(this.original);
      expression.value.accept(this.original);
      this.original.putField(expression.field);
      return null;
   }

   public Void visitSetFunctionParameter(SetFunctionParameterExpression expression) {
      expression.value.accept(this.original);
      JavaParameterInfo parameter = this.module.getParameterInfo(expression.parameter);
      this.javaWriter.store(this.context.getType(expression.type), parameter.index);
      return null;
   }

   public Void visitSetLocalVariable(SetLocalVariableExpression expression) {
      expression.value.accept(this.original);
      Label label = new Label();
      this.javaWriter.label(label);
      JavaLocalVariableInfo tag = this.javaWriter.getLocalVariable(expression.variable.variable);
      tag.end = label;
      this.javaWriter.store(tag.type, tag.local);
      return null;
   }

   public Void visitSetStaticField(SetStaticFieldExpression expression) {
      expression.value.accept(this.original);
      this.javaWriter.putStaticField(this.context.getJavaField(expression.field));
      return null;
   }

   public Void visitSetter(SetterExpression expression) {
      expression.target.accept(this.original);
      if (expression.setter.member.definition.isExpansion()) {
         for (TypeParameter typeParameter : expression.setter.member.definition.typeParameters) {
            this.javaWriter.aConstNull();
            this.javaWriter.checkCast("java/lang/Class");
         }
      }

      expression.value.accept(this.original);
      this.original.checkAndExecuteMethodInfo(expression.setter, BasicTypeID.VOID, expression);
      return null;
   }

   public Void visitStaticGetter(StaticGetterExpression expression) {
      return null;
   }

   public Void visitStaticSetter(StaticSetterExpression expression) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   public Void visitSupertypeCast(SupertypeCastExpression expression) {
      return expression.value.accept(this);
   }

   public Void visitSubtypeCast(SubtypeCastExpression expression) {
      return expression.value.accept(this);
   }

   public Void visitThis(ThisExpression expression) {
      return null;
   }

   public Void visitThrow(ThrowExpression expression) {
      expression.value.accept(this);
      this.javaWriter.aThrow();
      return null;
   }

   public Void visitTryConvert(TryConvertExpression expression) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   public Void visitTryRethrowAsException(TryRethrowAsExceptionExpression expression) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   public Void visitTryRethrowAsResult(TryRethrowAsResultExpression expression) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   public Void visitVariantValue(VariantValueExpression expression) {
      this.fallback(expression);
      return null;
   }

   public Void visitWrapOptional(WrapOptionalExpression expression) {
      return expression.value.accept(this);
   }
}
