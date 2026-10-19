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

public class BlockScope extends StatementScope {
   private final StatementScope parent;

   public BlockScope(StatementScope parent) {
      this.parent = parent;
   }

   @Override
   public ZSPackage getRootPackage() {
      return this.parent.getRootPackage();
   }

   @Override
   public LocalMemberCache getMemberCache() {
      return this.parent.getMemberCache();
   }

   @Override
   public IPartialExpression get(CodePosition position, GenericName name) throws CompileException {
      IPartialExpression fromSuper = super.get(position, name);
      return fromSuper != null ? fromSuper : this.parent.get(position, name);
   }

   @Override
   public LoopStatement getLoop(String name) {
      return this.parent.getLoop(name);
   }

   @Override
   public FunctionHeader getFunctionHeader() {
      return this.parent.getFunctionHeader();
   }

   @Override
   public TypeID getType(CodePosition position, List<GenericName> name) {
      return this.parent.getType(position, name);
   }

   @Override
   public TypeID getThisType() {
      return this.parent.getThisType();
   }

   @Override
   public BaseScope.DollarEvaluator getDollar() {
      return this.parent.getDollar();
   }

   @Override
   public IPartialExpression getOuterInstance(CodePosition position) throws CompileException {
      return this.parent.getOuterInstance(position);
   }

   @Override
   public AnnotationDefinition getAnnotation(String name) {
      return this.parent.getAnnotation(name);
   }

   @Override
   public TypeMemberPreparer getPreparer() {
      return this.parent.getPreparer();
   }

   @Override
   public GenericMapper getLocalTypeParameters() {
      return this.parent.getLocalTypeParameters();
   }
}
