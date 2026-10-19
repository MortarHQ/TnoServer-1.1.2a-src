package org.openzen.zenscript.codemodel.partial;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zencode.shared.CompileExceptionCode;
import org.openzen.zenscript.codemodel.FunctionHeader;
import org.openzen.zenscript.codemodel.GenericName;
import org.openzen.zenscript.codemodel.expression.CallArguments;
import org.openzen.zenscript.codemodel.expression.Expression;
import org.openzen.zenscript.codemodel.expression.InvalidExpression;
import org.openzen.zenscript.codemodel.expression.VariantValueExpression;
import org.openzen.zenscript.codemodel.member.ref.VariantOptionRef;
import org.openzen.zenscript.codemodel.scope.TypeScope;
import org.openzen.zenscript.codemodel.type.TypeID;

public class PartialVariantOptionExpression implements IPartialExpression {
   private final CodePosition position;
   private final TypeScope scope;
   private final VariantOptionRef option;

   public PartialVariantOptionExpression(CodePosition position, TypeScope scope, VariantOptionRef option) {
      this.position = position;
      this.scope = scope;
      this.option = option;
   }

   @Override
   public Expression eval() {
      return new InvalidExpression(
         this.position, this.option.variant, CompileExceptionCode.VARIANT_OPTION_NOT_AN_EXPRESSION, "Cannot use a variant option as expression"
      );
   }

   @Override
   public List<TypeID>[] predictCallTypes(CodePosition position, TypeScope scope, List<TypeID> hints, int arguments) {
      return arguments != this.option.getOption().types.length ? new List[0] : new List[]{Arrays.asList(this.option.getOption().types)};
   }

   @Override
   public List<FunctionHeader> getPossibleFunctionHeaders(TypeScope scope, List<TypeID> hints, int arguments) {
      return arguments != this.option.getOption().types.length
         ? Collections.emptyList()
         : Collections.singletonList(new FunctionHeader(this.option.variant, this.option.types));
   }

   @Override
   public IPartialExpression getMember(CodePosition position, TypeScope scope, List<TypeID> hints, GenericName name) throws CompileException {
      throw new CompileException(position, CompileExceptionCode.NO_SUCH_MEMBER, "Variant options don't have members");
   }

   @Override
   public Expression call(CodePosition position, TypeScope scope, List<TypeID> hints, CallArguments arguments) {
      return new VariantValueExpression(position, this.option.variant, this.option, arguments.arguments);
   }

   @Override
   public TypeID[] getTypeArguments() {
      return TypeID.NONE;
   }
}
