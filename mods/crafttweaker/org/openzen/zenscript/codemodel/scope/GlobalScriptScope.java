package org.openzen.zenscript.codemodel.scope;

import java.util.List;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zenscript.codemodel.FunctionHeader;
import org.openzen.zenscript.codemodel.FunctionParameter;
import org.openzen.zenscript.codemodel.GenericMapper;
import org.openzen.zenscript.codemodel.GenericName;
import org.openzen.zenscript.codemodel.annotations.AnnotationDefinition;
import org.openzen.zenscript.codemodel.definition.ZSPackage;
import org.openzen.zenscript.codemodel.expression.GetFunctionParameterExpression;
import org.openzen.zenscript.codemodel.partial.IPartialExpression;
import org.openzen.zenscript.codemodel.statement.LoopStatement;
import org.openzen.zenscript.codemodel.type.TypeID;
import org.openzen.zenscript.codemodel.type.member.LocalMemberCache;
import org.openzen.zenscript.codemodel.type.member.TypeMemberPreparer;

public class GlobalScriptScope extends StatementScope {
   private final BaseScope file;
   private final FunctionHeader header;

   public GlobalScriptScope(BaseScope file, FunctionHeader scriptHeader) {
      this.file = file;
      this.header = scriptHeader;
   }

   @Override
   public ZSPackage getRootPackage() {
      return this.file.getRootPackage();
   }

   @Override
   public LocalMemberCache getMemberCache() {
      return this.file.getMemberCache();
   }

   @Override
   public IPartialExpression get(CodePosition position, GenericName name) throws CompileException {
      IPartialExpression result = super.get(position, name);
      if (result != null) {
         return result;
      }

      if (name.hasNoArguments()) {
         for (FunctionParameter parameter : this.header.parameters) {
            if (parameter.name.equals(name.name)) {
               return new GetFunctionParameterExpression(position, parameter);
            }
         }
      }

      return this.file.get(position, name);
   }

   @Override
   public TypeID getType(CodePosition position, List<GenericName> name) {
      return this.file.getType(position, name);
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
   public TypeID getThisType() {
      return null;
   }

   @Override
   public BaseScope.DollarEvaluator getDollar() {
      return null;
   }

   @Override
   public IPartialExpression getOuterInstance(CodePosition position) {
      return null;
   }

   @Override
   public AnnotationDefinition getAnnotation(String name) {
      return this.file.getAnnotation(name);
   }

   @Override
   public TypeMemberPreparer getPreparer() {
      return this.file.getPreparer();
   }

   @Override
   public GenericMapper getLocalTypeParameters() {
      return GenericMapper.EMPTY;
   }
}
