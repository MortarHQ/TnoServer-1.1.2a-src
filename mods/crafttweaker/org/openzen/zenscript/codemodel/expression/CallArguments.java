package org.openzen.zenscript.codemodel.expression;

import java.util.Arrays;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.CompileExceptionCode;
import org.openzen.zenscript.codemodel.FunctionHeader;
import org.openzen.zenscript.codemodel.FunctionParameter;
import org.openzen.zenscript.codemodel.scope.TypeScope;
import org.openzen.zenscript.codemodel.type.TypeID;

public class CallArguments {
   public static final CallArguments EMPTY = new CallArguments(Expression.NONE);
   public final TypeID[] typeArguments;
   public final Expression[] arguments;

   public CallArguments(Expression... arguments) {
      this.typeArguments = TypeID.NONE;
      this.arguments = arguments;
   }

   public CallArguments(TypeID[] typeArguments, Expression[] arguments) {
      if (typeArguments == null) {
         typeArguments = TypeID.NONE;
      }

      if (arguments == null) {
         throw new IllegalArgumentException("Arguments cannot be null!");
      }

      this.typeArguments = typeArguments;
      this.arguments = arguments;
   }

   public CallArguments(TypeID... dummy) {
      this.typeArguments = TypeID.NONE;
      this.arguments = new Expression[dummy.length];

      for (int i = 0; i < dummy.length; i++) {
         this.arguments[i] = new DummyExpression(dummy[i]);
      }
   }

   public int getNumberOfTypeArguments() {
      return this.typeArguments.length;
   }

   public CallArguments transform(ExpressionTransformer transformer) {
      Expression[] tArguments = Expression.transform(this.arguments, transformer);
      return tArguments == this.arguments ? this : new CallArguments(this.typeArguments, tArguments);
   }

   public CallArguments normalize(CodePosition position, TypeScope scope, FunctionHeader header) {
      CallArguments result = this;
      boolean isVariadic = header.isVariadicCall(this, scope);

      for (int i = 0; i < this.arguments.length; i++) {
         this.arguments[i] = this.arguments[i].normalize(scope).castImplicit(position, scope, header.getParameterType(isVariadic, i));
      }

      if (this.arguments.length < header.parameters.length) {
         Expression[] newArguments = Arrays.copyOf(this.arguments, header.parameters.length);

         for (int i = this.arguments.length; i < header.parameters.length; i++) {
            FunctionParameter parameter = header.parameters[i];
            if (parameter.defaultValue == null) {
               if (parameter.variadic) {
                  newArguments[i] = new ArrayExpression(position, Expression.NONE, parameter.type);
               } else {
                  newArguments[i] = new InvalidExpression(
                     position, parameter.type, CompileExceptionCode.MISSING_PARAMETER, "Parameter missing and no default value specified"
                  );
               }
            } else {
               newArguments[i] = parameter.defaultValue;
            }
         }

         result = new CallArguments(this.typeArguments, newArguments);
      }

      return result;
   }
}
