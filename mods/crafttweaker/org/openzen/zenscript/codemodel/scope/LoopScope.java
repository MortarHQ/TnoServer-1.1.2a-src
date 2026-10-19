package org.openzen.zenscript.codemodel.scope;

import java.util.List;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zenscript.codemodel.FunctionHeader;
import org.openzen.zenscript.codemodel.GenericMapper;
import org.openzen.zenscript.codemodel.GenericName;
import org.openzen.zenscript.codemodel.annotations.AnnotationDefinition;
import org.openzen.zenscript.codemodel.definition.ZSPackage;
import org.openzen.zenscript.codemodel.partial.IPartialExpression;
import org.openzen.zenscript.codemodel.statement.LoopStatement;
import org.openzen.zenscript.codemodel.type.TypeID;
import org.openzen.zenscript.codemodel.type.member.LocalMemberCache;
import org.openzen.zenscript.codemodel.type.member.TypeMemberPreparer;

public class LoopScope extends StatementScope {
   private final StatementScope outer;
   private final LoopStatement statement;

   public LoopScope(LoopStatement statement, StatementScope outer) {
      this.statement = statement;
      this.outer = outer;
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
      IPartialExpression result = super.get(position, name);
      return result != null ? result : this.outer.get(position, name);
   }

   @Override
   public TypeID getType(CodePosition position, List<GenericName> name) {
      return this.outer.getType(position, name);
   }

   @Override
   public LoopStatement getLoop(String name) {
      if (name == null) {
         return this.statement;
      } else {
         return this.statement.label != null && this.statement.label.equals(name) ? this.statement : this.outer.getLoop(name);
      }
   }

   @Override
   public FunctionHeader getFunctionHeader() {
      return this.outer.getFunctionHeader();
   }

   @Override
   public TypeID getThisType() {
      return this.outer.getThisType();
   }

   @Override
   public BaseScope.DollarEvaluator getDollar() {
      return this.outer.getDollar();
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
