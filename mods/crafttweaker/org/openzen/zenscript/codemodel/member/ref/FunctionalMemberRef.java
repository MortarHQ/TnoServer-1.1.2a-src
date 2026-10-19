package org.openzen.zenscript.codemodel.member.ref;

import java.util.Objects;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.Tag;
import org.openzen.zenscript.codemodel.CompareType;
import org.openzen.zenscript.codemodel.FunctionHeader;
import org.openzen.zenscript.codemodel.GenericMapper;
import org.openzen.zenscript.codemodel.OperatorType;
import org.openzen.zenscript.codemodel.annotations.MemberAnnotation;
import org.openzen.zenscript.codemodel.expression.CallArguments;
import org.openzen.zenscript.codemodel.expression.CallExpression;
import org.openzen.zenscript.codemodel.expression.CallStaticExpression;
import org.openzen.zenscript.codemodel.expression.CompareExpression;
import org.openzen.zenscript.codemodel.expression.Expression;
import org.openzen.zenscript.codemodel.member.FunctionalKind;
import org.openzen.zenscript.codemodel.member.FunctionalMember;
import org.openzen.zenscript.codemodel.member.MethodMember;
import org.openzen.zenscript.codemodel.member.OperatorMember;
import org.openzen.zenscript.codemodel.scope.TypeScope;
import org.openzen.zenscript.codemodel.type.TypeID;
import org.openzen.zenscript.codemodel.type.member.BuiltinID;

public class FunctionalMemberRef implements DefinitionMemberRef {
   private final FunctionalMember target;
   private final TypeID type;
   private FunctionHeader header;
   private GenericMapper mapper;

   public FunctionalMemberRef(FunctionalMember target, TypeID type, GenericMapper mapper) {
      this.target = target;
      this.type = type;
      if (target.header.hasUnknowns) {
         this.header = null;
         this.mapper = mapper;
      } else {
         this.header = mapper == null ? target.header : mapper.map(target.header);
         this.mapper = null;
      }
   }

   public boolean accepts(int arguments) {
      return this.target.header.accepts(arguments);
   }

   @Override
   public FunctionHeader getHeader() {
      if (this.header == null) {
         if (this.target.header.hasUnknowns) {
            throw new IllegalStateException("member is not yet resolved!");
         }

         this.header = this.mapper == null ? this.target.header : this.mapper.map(this.target.header);
         this.mapper = null;
      }

      return this.header;
   }

   @Override
   public CodePosition getPosition() {
      return this.target.position;
   }

   @Override
   public TypeID getOwnerType() {
      return this.type;
   }

   public FunctionalMember getTarget() {
      return this.target;
   }

   public String getCanonicalName() {
      return this.target.getCanonicalName();
   }

   @Override
   public String describe() {
      return this.target.describe();
   }

   @Override
   public <T extends Tag> T getTag(Class<T> cls) {
      return this.target.getTag(cls);
   }

   @Override
   public MemberAnnotation[] getAnnotations() {
      return this.target.annotations;
   }

   @Override
   public DefinitionMemberRef getOverrides() {
      return this.target.getOverrides();
   }

   public BuiltinID getBuiltin() {
      return this.target.builtin;
   }

   public boolean isStatic() {
      return this.target.isStatic();
   }

   public boolean isConstructor() {
      return this.target.getKind() == FunctionalKind.CONSTRUCTOR;
   }

   public boolean isOperator() {
      return this.target.getKind() == FunctionalKind.OPERATOR;
   }

   public boolean isCaller() {
      return this.target.getKind() == FunctionalKind.CALLER;
   }

   public OperatorType getOperator() {
      return ((OperatorMember)this.target).operator;
   }

   public String getMethodName() {
      return ((MethodMember)this.target).name;
   }

   public Expression call(CodePosition position, Expression target, FunctionHeader instancedHeader, CallArguments arguments, TypeScope scope) {
      return new CallExpression(position, target, this, instancedHeader, arguments);
   }

   public final Expression call(CodePosition position, Expression target, CallArguments arguments, TypeScope scope) {
      return this.call(position, target, this.header, arguments, scope);
   }

   public Expression callWithComparator(
      CodePosition position, CompareType comparison, Expression target, FunctionHeader instancedHeader, CallArguments arguments, TypeScope scope
   ) {
      return new CompareExpression(position, target, arguments.arguments[0], this, comparison);
   }

   public Expression callStatic(CodePosition position, TypeID target, FunctionHeader instancedHeader, CallArguments arguments, TypeScope scope) {
      return new CallStaticExpression(position, target, this, instancedHeader, arguments);
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      }

      if (o != null && this.getClass() == o.getClass()) {
         FunctionalMemberRef that = (FunctionalMemberRef)o;
         if (!this.target.equals(that.target)) {
            return false;
         } else if (!this.header.equals(that.header)) {
            return false;
         } else {
            return !this.type.equals(that.type) ? false : Objects.equals(this.mapper, that.mapper);
         }
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      int result = this.target.hashCode();
      result = 31 * result + this.header.hashCode();
      result = 31 * result + this.type.hashCode();
      return 31 * result + (this.mapper != null ? this.mapper.hashCode() : 0);
   }
}
