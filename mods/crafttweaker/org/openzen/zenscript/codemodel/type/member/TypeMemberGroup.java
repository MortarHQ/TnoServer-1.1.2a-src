package org.openzen.zenscript.codemodel.type.member;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zencode.shared.CompileExceptionCode;
import org.openzen.zenscript.codemodel.CompareType;
import org.openzen.zenscript.codemodel.FunctionHeader;
import org.openzen.zenscript.codemodel.expression.CallArguments;
import org.openzen.zenscript.codemodel.expression.ConstExpression;
import org.openzen.zenscript.codemodel.expression.Expression;
import org.openzen.zenscript.codemodel.expression.GetFieldExpression;
import org.openzen.zenscript.codemodel.expression.GetStaticFieldExpression;
import org.openzen.zenscript.codemodel.expression.InvalidExpression;
import org.openzen.zenscript.codemodel.expression.PostCallExpression;
import org.openzen.zenscript.codemodel.expression.SetFieldExpression;
import org.openzen.zenscript.codemodel.expression.SetStaticFieldExpression;
import org.openzen.zenscript.codemodel.expression.SetterExpression;
import org.openzen.zenscript.codemodel.expression.StaticSetterExpression;
import org.openzen.zenscript.codemodel.generic.TypeParameter;
import org.openzen.zenscript.codemodel.member.FunctionalMember;
import org.openzen.zenscript.codemodel.member.ref.ConstMemberRef;
import org.openzen.zenscript.codemodel.member.ref.FieldMemberRef;
import org.openzen.zenscript.codemodel.member.ref.FunctionalMemberRef;
import org.openzen.zenscript.codemodel.member.ref.GetterMemberRef;
import org.openzen.zenscript.codemodel.member.ref.SetterMemberRef;
import org.openzen.zenscript.codemodel.scope.TypeScope;
import org.openzen.zenscript.codemodel.type.TypeID;

public class TypeMemberGroup {
   public static final TypeMemberGroup EMPTY = new TypeMemberGroup(false, "");
   public final boolean isStatic;
   public final String name;
   private final List<TypeMember<FunctionalMemberRef>> methods = new ArrayList<>();
   private TypeMember<ConstMemberRef> constant;
   private TypeMember<FieldMemberRef> field;
   private TypeMember<GetterMemberRef> getter;
   private TypeMember<SetterMemberRef> setter;

   public TypeMemberGroup(boolean isStatic, String name) {
      this.isStatic = isStatic;
      this.name = name;
   }

   public static TypeMemberGroup forMethod(String name, FunctionalMemberRef member) {
      TypeMemberGroup instance = new TypeMemberGroup(member.isStatic(), name);
      instance.addMethod(member, TypeMemberPriority.SPECIFIED);
      return instance;
   }

   public void merge(TypeMemberGroup other, TypeMemberPriority priority) {
      if (other.constant != null) {
         this.setConst(other.constant.member, priority);
      }

      if (other.field != null) {
         this.setField(other.field.member, priority);
      }

      if (other.getter != null) {
         this.setGetter(other.getter.member, priority);
      }

      if (other.setter != null) {
         this.setSetter(other.setter.member, priority);
      }

      for (TypeMember<FunctionalMemberRef> method : other.methods) {
         this.addMethod(method.member, priority);
      }
   }

   public FieldMemberRef getField() {
      return this.field == null ? null : this.field.member;
   }

   public GetterMemberRef getGetter() {
      return this.getter == null ? null : this.getter.member;
   }

   public SetterMemberRef getSetter() {
      return this.setter == null ? null : this.setter.member;
   }

   public FunctionalMemberRef getMethod(FunctionHeader header) {
      for (TypeMember<FunctionalMemberRef> method : this.methods) {
         if (method.member.getHeader().isEquivalentTo(header)) {
            return method.member;
         }
      }

      return null;
   }

   public FunctionalMemberRef getUnaryMethod() {
      for (TypeMember<FunctionalMemberRef> method : this.methods) {
         if (method.member.getHeader().parameters.length == 0) {
            return method.member;
         }
      }

      return null;
   }

   public boolean hasMethods() {
      return !this.methods.isEmpty();
   }

   public boolean hasMethod(FunctionHeader header) {
      for (TypeMember<FunctionalMemberRef> method : this.methods) {
         if (method.member.getHeader().isEquivalentTo(header)) {
            return true;
         }
      }

      return false;
   }

   public FunctionalMemberRef getStaticMethod(int arguments, TypeID returnType) {
      for (TypeMember<FunctionalMemberRef> method : this.methods) {
         if (method.member.isStatic() && method.member.getHeader().accepts(arguments) && method.member.getHeader().getReturnType().equals(returnType)) {
            return method.member;
         }
      }

      return null;
   }

   public List<TypeMember<FunctionalMemberRef>> getMethodMembers() {
      return this.methods;
   }

   public ConstMemberRef getConstant() {
      return this.constant.member;
   }

   public void setConst(ConstMemberRef constant, TypeMemberPriority priority) {
      if (this.constant != null) {
         this.constant = this.constant.resolve(new TypeMember<>(priority, constant));
      } else {
         this.constant = new TypeMember<>(priority, constant);
      }
   }

   public void setField(FieldMemberRef field, TypeMemberPriority priority) {
      if (this.field != null) {
         this.field = this.field.resolve(new TypeMember<>(priority, field));
      } else {
         this.field = new TypeMember<>(priority, field);
      }
   }

   public void setGetter(GetterMemberRef getter, TypeMemberPriority priority) {
      if (this.getter != null) {
         this.getter = this.getter.resolve(new TypeMember<>(priority, getter));
      } else {
         this.getter = new TypeMember<>(priority, getter);
      }
   }

   public void setSetter(SetterMemberRef setter, TypeMemberPriority priority) {
      if (this.setter != null) {
         this.setter = this.setter.resolve(new TypeMember<>(priority, setter));
      } else {
         this.setter = new TypeMember<>(priority, setter);
      }
   }

   public void addMethod(FunctionalMemberRef method, TypeMemberPriority priority) {
      this.methods.add(new TypeMember<>(priority, method));
   }

   public Expression getter(CodePosition position, TypeScope scope, Expression target, boolean allowStaticUsage) throws CompileException {
      if (this.getter != null) {
         if (this.getter.member.isStatic()) {
            return !allowStaticUsage
               ? new InvalidExpression(position, this.getter.member.getType(), CompileExceptionCode.USING_STATIC_ON_INSTANCE, "This field is static")
               : this.getter.member.getStatic(position);
         }

         scope.getPreparer().prepare(this.getter.member.member);
         return this.getter.member.get(position, target);
      } else if (this.field != null) {
         if (this.field.member.isStatic()) {
            return !allowStaticUsage
               ? new InvalidExpression(position, this.field.member.getType(), CompileExceptionCode.USING_STATIC_ON_INSTANCE, "This field is static")
               : new GetStaticFieldExpression(position, this.field.member);
         }

         scope.getPreparer().prepare(this.field.member.member);
         return new GetFieldExpression(position, target, this.field.member);
      } else {
         throw new CompileException(position, CompileExceptionCode.MEMBER_NO_GETTER, "Value is not a property");
      }
   }

   public Expression setter(CodePosition position, TypeScope scope, Expression target, Expression value, boolean allowStaticUsage) throws CompileException {
      if (this.setter != null) {
         if (this.setter.member.isStatic()) {
            if (!allowStaticUsage) {
               return new InvalidExpression(position, this.setter.member.getType(), CompileExceptionCode.USING_STATIC_ON_INSTANCE, "This field is static");
            }

            scope.getPreparer().prepare(this.setter.member.member);
            return new StaticSetterExpression(position, this.setter.member, value.castImplicit(position, scope, this.setter.member.getType()));
         } else {
            scope.getPreparer().prepare(this.setter.member.member);
            return new SetterExpression(position, target, this.setter.member, value.castImplicit(position, scope, this.setter.member.getType()));
         }
      } else if (this.field != null) {
         if (this.field.member.isStatic()) {
            if (!allowStaticUsage) {
               return new InvalidExpression(position, this.field.member.getType(), CompileExceptionCode.USING_STATIC_ON_INSTANCE, "This field is static");
            }

            scope.getPreparer().prepare(this.field.member.member);
            return new SetStaticFieldExpression(position, this.field.member, value.castImplicit(position, scope, this.field.member.getType()));
         } else {
            scope.getPreparer().prepare(this.field.member.member);
            return new SetFieldExpression(position, target, this.field.member, value.castImplicit(position, scope, this.field.member.getType()));
         }
      } else {
         throw new CompileException(position, CompileExceptionCode.MEMBER_NO_SETTER, "Value is not settable");
      }
   }

   public Expression staticGetter(CodePosition position, TypeScope scope) throws CompileException {
      if (this.constant != null) {
         return new ConstExpression(position, this.constant.member);
      }

      if (this.getter != null) {
         if (!this.getter.member.isStatic()) {
            return new InvalidExpression(position, this.getter.member.getType(), CompileExceptionCode.MEMBER_NOT_STATIC, "This getter is not static");
         }

         scope.getPreparer().prepare(this.getter.member.member);
         return this.getter.member.getStatic(position);
      } else if (this.field != null) {
         if (!this.field.member.isStatic()) {
            return new InvalidExpression(position, this.field.member.getType(), CompileExceptionCode.MEMBER_NOT_STATIC, "This field is not static");
         }

         scope.getPreparer().prepare(this.field.member.member);
         return new GetStaticFieldExpression(position, this.field.member);
      } else {
         throw new CompileException(position, CompileExceptionCode.MEMBER_NO_GETTER, "Member is not gettable");
      }
   }

   public Expression staticSetter(CodePosition position, TypeScope scope, Expression value) throws CompileException {
      if (this.getter != null) {
         if (!this.getter.member.isStatic()) {
            return new InvalidExpression(position, this.getter.member.getType(), CompileExceptionCode.MEMBER_NOT_STATIC, "This getter is not static");
         }

         scope.getPreparer().prepare(this.setter.member.member);
         return new StaticSetterExpression(position, this.setter.member, value.castImplicit(position, scope, this.setter.member.getType()));
      } else if (this.field != null) {
         if (!this.field.member.isStatic()) {
            return new InvalidExpression(position, this.field.member.getType(), CompileExceptionCode.MEMBER_NOT_STATIC, "This field is not static");
         }

         scope.getPreparer().prepare(this.field.member.member);
         return new SetStaticFieldExpression(position, this.field.member, value.castImplicit(position, scope, this.field.member.getType()));
      } else {
         throw new CompileException(position, CompileExceptionCode.MEMBER_NO_SETTER, "Member is not settable");
      }
   }

   public List<TypeID>[] predictCallTypes(CodePosition position, TypeScope scope, List<TypeID> typeHints, int arguments) {
      List<TypeID>[] result = new List[arguments];

      for (int i = 0; i < result.length; i++) {
         result[i] = new ArrayList<>();
      }

      for (TypeMember<FunctionalMemberRef> method : this.methods) {
         FunctionHeader header = method.member.getHeader();
         if (header.parameters.length == arguments) {
            for (TypeID resultHint : typeHints) {
               Map<TypeParameter, TypeID> mapping = header.getReturnType().inferTypeParameters(scope.getMemberCache(), resultHint);
               if (mapping != null) {
                  header = header.withGenericArguments(scope.getLocalTypeParameters().getInner(position, scope.getTypeRegistry(), mapping));
                  break;
               }
            }

            for (int i = 0; i < header.parameters.length; i++) {
               if (!result[i].contains(header.parameters[i].type)) {
                  result[i].add(header.parameters[i].type);
               }
            }
         }
      }

      return result;
   }

   public Expression call(CodePosition position, TypeScope scope, Expression target, CallArguments arguments, boolean allowStaticUsage) throws CompileException {
      FunctionalMemberRef method = this.selectMethod(position, scope, arguments, true, allowStaticUsage);
      FunctionHeader instancedHeader = method.getHeader().fillGenericArguments(position, scope, arguments.typeArguments);
      boolean isVariadicCall = instancedHeader.isVariadicCall(arguments, scope);

      for (int i = 0; i < arguments.arguments.length; i++) {
         arguments.arguments[i] = arguments.arguments[i].castImplicit(position, scope, instancedHeader.getParameterType(isVariadicCall, i));
      }

      scope.getPreparer().prepare(method.getTarget());
      return method.call(position, target, instancedHeader, arguments, scope);
   }

   public Expression callPostfix(CodePosition position, TypeScope scope, Expression target) throws CompileException {
      if (this.methods.isEmpty()) {
         throw new CompileException(position, CompileExceptionCode.NO_SUCH_MEMBER, "There is no such operator");
      }

      FunctionalMemberRef method = (FunctionalMemberRef)this.methods.get(0).member;
      if (!method.isOperator()) {
         throw new CompileException(position, CompileExceptionCode.NO_SUCH_MEMBER, "Member is not an operator");
      }

      scope.getPreparer().prepare(method.getTarget());
      return new PostCallExpression(position, target, method, method.getHeader());
   }

   public Expression callWithComparator(CodePosition position, TypeScope scope, Expression target, CallArguments arguments, CompareType compareType) throws CompileException {
      FunctionalMemberRef method = this.selectMethod(position, scope, arguments, true, false);
      FunctionHeader instancedHeader = method.getHeader().fillGenericArguments(position, scope, arguments.typeArguments);
      return method.callWithComparator(position, compareType, target, instancedHeader, arguments, scope);
   }

   public Expression callStatic(CodePosition position, TypeID target, TypeScope scope, CallArguments arguments) throws CompileException {
      FunctionalMemberRef method = this.selectMethod(position, scope, arguments, false, true);
      FunctionHeader instancedHeader = method.getHeader().fillGenericArguments(position, scope, arguments.typeArguments);
      return method.callStatic(position, target, instancedHeader, arguments, scope);
   }

   public FunctionalMemberRef selectMethod(CodePosition position, TypeScope scope, CallArguments arguments, boolean allowNonStatic, boolean allowStatic) throws CompileException {
      List<TypeMember<FunctionalMemberRef>> possibleMethods = new ArrayList<>();

      for (TypeMember<FunctionalMemberRef> method : this.methods) {
         if (method.member.isStatic() ? allowStatic : allowNonStatic) {
            FunctionHeader header = method.member.getHeader().instanceForCall(position, scope.getTypeRegistry(), arguments);
            if (header.matchesExactly(position, arguments, scope)) {
               possibleMethods.add(method);
            }
         }
      }

      if (!possibleMethods.isEmpty()) {
         TypeMember<FunctionalMemberRef> selectedMethod = null;

         for (TypeMember<FunctionalMemberRef> method : possibleMethods) {
            if (selectedMethod == null) {
               selectedMethod = method;
            } else {
               selectedMethod = selectedMethod.resolve(method);
            }
         }

         if (selectedMethod != null) {
            return selectedMethod.member;
         }
      }

      TypeMember<FunctionalMemberRef> selected = null;

      for (TypeMember<FunctionalMemberRef> method : this.methods) {
         if ((method.member.isStatic() ? allowStatic : allowNonStatic)
            && arguments.arguments.length >= method.member.getHeader().minParameters
            && arguments.arguments.length <= method.member.getHeader().maxParameters) {
            scope.getPreparer().prepare(method.member.getTarget());
            FunctionHeader header = method.member.getHeader().instanceForCall(position, scope.getTypeRegistry(), arguments);
            if (header.matchesImplicitly(position, arguments, scope)) {
               if (selected == null) {
                  selected = method;
               } else if (selected.member.equals(method.member)) {
                  selected = selected.resolve(method);
               } else {
                  if (selected.priority == method.priority) {
                     StringBuilder explanation = new StringBuilder();
                     FunctionHeader selectedHeader = selected.member.getHeader().instanceForCall(position, scope.getTypeRegistry(), arguments);
                     explanation.append("Function A: ").append(selectedHeader.toString()).append("\n");
                     explanation.append("Function B: ").append(header.toString());
                     throw new CompileException(
                        position, CompileExceptionCode.CALL_AMBIGUOUS, "Ambiguous call; multiple methods match:\n" + explanation.toString()
                     );
                  }

                  selected = selected.resolve(method);
               }
            }
         }
      }

      if (selected != null) {
         return selected.member;
      }

      StringBuilder message = new StringBuilder();
      if (this.methods.isEmpty()) {
         throw new CompileException(position, CompileExceptionCode.CALL_NO_VALID_METHOD, "This type has no " + this.name);
      }

      for (TypeMember<FunctionalMemberRef> method : this.methods) {
         if (method.member.isStatic() ? !allowStatic : !allowNonStatic) {
            message.append(method.member.isStatic() ? "Method must not be static" : "Method must be static").append('\n');
         } else {
            FunctionHeader instancedHeader = method.member.getHeader().instanceForCall(position, scope.getTypeRegistry(), arguments);
            message.append(instancedHeader.explainWhyIncompatible(scope, arguments)).append("\n");
         }
      }

      throw new CompileException(position, CompileExceptionCode.CALL_NO_VALID_METHOD, "No matching method found for " + this.name + ":\n" + message.toString());
   }

   public FunctionalMemberRef getOverride(CodePosition position, TypeScope scope, FunctionalMember member) throws CompileException {
      List<FunctionalMemberRef> candidates = new ArrayList<>();

      for (TypeMember<FunctionalMemberRef> method : this.methods) {
         if (member.header.canOverride(scope, method.member.getHeader())) {
            candidates.add(method.member);
         }
      }

      if (candidates.isEmpty()) {
         return null;
      } else if (candidates.size() == 1) {
         return candidates.get(0);
      } else {
         throw new CompileException(position, CompileExceptionCode.OVERRIDE_AMBIGUOUS, "Ambiguous override: has " + candidates.size() + " base candidates");
      }
   }
}
