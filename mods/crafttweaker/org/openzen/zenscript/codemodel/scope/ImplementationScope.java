package org.openzen.zenscript.codemodel.scope;

import java.util.List;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zenscript.codemodel.FunctionHeader;
import org.openzen.zenscript.codemodel.GenericMapper;
import org.openzen.zenscript.codemodel.GenericName;
import org.openzen.zenscript.codemodel.annotations.AnnotationDefinition;
import org.openzen.zenscript.codemodel.definition.ZSPackage;
import org.openzen.zenscript.codemodel.expression.ThisExpression;
import org.openzen.zenscript.codemodel.member.ImplementationMember;
import org.openzen.zenscript.codemodel.partial.IPartialExpression;
import org.openzen.zenscript.codemodel.partial.PartialTypeExpression;
import org.openzen.zenscript.codemodel.statement.LoopStatement;
import org.openzen.zenscript.codemodel.type.TypeID;
import org.openzen.zenscript.codemodel.type.member.LocalMemberCache;
import org.openzen.zenscript.codemodel.type.member.TypeMemberPreparer;
import org.openzen.zenscript.codemodel.type.member.TypeMembers;

public class ImplementationScope extends BaseScope {
   private final BaseScope outer;
   private final TypeMembers members;

   public ImplementationScope(BaseScope outer, ImplementationMember implementation) {
      this.outer = outer;
      this.members = outer.getTypeMembers(implementation.type);
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
      if (this.members.hasInnerType(name.name)) {
         return new PartialTypeExpression(position, this.members.getInnerType(position, name), name.arguments);
      } else {
         return this.members.hasMember(name.name)
            ? this.members.getMemberExpression(position, this, new ThisExpression(position, this.outer.getThisType()), name, true)
            : this.outer.get(position, name);
      }
   }

   @Override
   public TypeID getType(CodePosition position, List<GenericName> name) {
      if (!this.members.hasInnerType(name.get(0).name)) {
         return this.outer.getType(position, name);
      }

      TypeID result = this.members.getInnerType(position, name.get(0));

      for (int i = 1; i < name.size(); i++) {
         result = this.getTypeMembers(result).getInnerType(position, name.get(i));
      }

      return result;
   }

   @Override
   public LoopStatement getLoop(String name) {
      return null;
   }

   @Override
   public FunctionHeader getFunctionHeader() {
      return null;
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
   public IPartialExpression getOuterInstance(CodePosition position) {
      return new ThisExpression(position, this.outer.getThisType());
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
