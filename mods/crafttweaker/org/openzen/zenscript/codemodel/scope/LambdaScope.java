package org.openzen.zenscript.codemodel.scope;

import java.util.List;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zencode.shared.CompileExceptionCode;
import org.openzen.zenscript.codemodel.FunctionHeader;
import org.openzen.zenscript.codemodel.FunctionParameter;
import org.openzen.zenscript.codemodel.GenericMapper;
import org.openzen.zenscript.codemodel.GenericName;
import org.openzen.zenscript.codemodel.annotations.AnnotationDefinition;
import org.openzen.zenscript.codemodel.definition.ZSPackage;
import org.openzen.zenscript.codemodel.expression.GetFunctionParameterExpression;
import org.openzen.zenscript.codemodel.expression.LambdaClosure;
import org.openzen.zenscript.codemodel.partial.IPartialExpression;
import org.openzen.zenscript.codemodel.statement.LoopStatement;
import org.openzen.zenscript.codemodel.type.BasicTypeID;
import org.openzen.zenscript.codemodel.type.TypeID;
import org.openzen.zenscript.codemodel.type.member.LocalMemberCache;
import org.openzen.zenscript.codemodel.type.member.TypeMemberPreparer;

public class LambdaScope extends StatementScope {
   private final BaseScope outer;
   private final FunctionHeader header;
   private final LambdaClosure closure;

   public LambdaScope(BaseScope outer, LambdaClosure closure, FunctionHeader header) {
      this.outer = outer;
      this.header = header;
      this.closure = closure;
   }

   @Override
   public ZSPackage getRootPackage() {
      return this.outer.getRootPackage();
   }

   @Override
   public LocalMemberCache getMemberCache() {
      return this.outer.getMemberCache();
   }

   @Override
   public IPartialExpression get(CodePosition position, GenericName name) throws CompileException {
      IPartialExpression outer = this.outer.get(position, name);
      if (outer != null) {
         return outer.capture(position, this.closure);
      }

      if (name.hasNoArguments()) {
         for (FunctionParameter parameter : this.header.parameters) {
            if (parameter.name.equals(name.name)) {
               if (parameter.type == BasicTypeID.UNDETERMINED) {
                  throw new CompileException(position, CompileExceptionCode.CALL_NO_VALID_METHOD, "parameter with undetermined type");
               }

               return new GetFunctionParameterExpression(position, parameter);
            }
         }
      }

      return null;
   }

   @Override
   public LoopStatement getLoop(String name) {
      return null;
   }

   @Override
   public FunctionHeader getFunctionHeader() {
      return this.header;
   }

   @Override
   public TypeID getType(CodePosition position, List<GenericName> name) {
      return this.outer.getType(position, name);
   }

   @Override
   public TypeID getThisType() {
      return this.outer.getThisType();
   }

   @Override
   public BaseScope.DollarEvaluator getDollar() {
      BaseScope.DollarEvaluator outerDollar = this.outer.getDollar();
      return outerDollar == null ? null : position -> outerDollar.apply(position).capture(position, this.closure).eval();
   }

   @Override
   public IPartialExpression getOuterInstance(CodePosition position) throws CompileException {
      return this.outer.getOuterInstance(position);
   }

   @Override
   public AnnotationDefinition getAnnotation(String name) {
      return this.outer.getAnnotation(name);
   }

   @Override
   public TypeMemberPreparer getPreparer() {
      return this.outer.getPreparer();
   }

   @Override
   public GenericMapper getLocalTypeParameters() {
      return this.outer.getLocalTypeParameters();
   }
}
