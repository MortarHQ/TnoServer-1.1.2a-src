package org.openzen.zenscript.codemodel.type.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zencode.shared.CompileExceptionCode;
import org.openzen.zenscript.codemodel.CompareType;
import org.openzen.zenscript.codemodel.GenericName;
import org.openzen.zenscript.codemodel.OperatorType;
import org.openzen.zenscript.codemodel.expression.CallArguments;
import org.openzen.zenscript.codemodel.expression.CheckNullExpression;
import org.openzen.zenscript.codemodel.expression.EnumConstantExpression;
import org.openzen.zenscript.codemodel.expression.Expression;
import org.openzen.zenscript.codemodel.expression.InterfaceCastExpression;
import org.openzen.zenscript.codemodel.expression.InvalidExpression;
import org.openzen.zenscript.codemodel.expression.NullExpression;
import org.openzen.zenscript.codemodel.expression.SupertypeCastExpression;
import org.openzen.zenscript.codemodel.expression.WrapOptionalExpression;
import org.openzen.zenscript.codemodel.member.EnumConstantMember;
import org.openzen.zenscript.codemodel.member.IDefinitionMember;
import org.openzen.zenscript.codemodel.member.InnerDefinition;
import org.openzen.zenscript.codemodel.member.ref.CasterMemberRef;
import org.openzen.zenscript.codemodel.member.ref.ConstMemberRef;
import org.openzen.zenscript.codemodel.member.ref.DefinitionMemberRef;
import org.openzen.zenscript.codemodel.member.ref.FieldMemberRef;
import org.openzen.zenscript.codemodel.member.ref.FunctionalMemberRef;
import org.openzen.zenscript.codemodel.member.ref.GetterMemberRef;
import org.openzen.zenscript.codemodel.member.ref.ImplementationMemberRef;
import org.openzen.zenscript.codemodel.member.ref.IteratorMemberRef;
import org.openzen.zenscript.codemodel.member.ref.SetterMemberRef;
import org.openzen.zenscript.codemodel.member.ref.VariantOptionRef;
import org.openzen.zenscript.codemodel.partial.IPartialExpression;
import org.openzen.zenscript.codemodel.partial.PartialMemberGroupExpression;
import org.openzen.zenscript.codemodel.partial.PartialStaticMemberGroupExpression;
import org.openzen.zenscript.codemodel.partial.PartialTypeExpression;
import org.openzen.zenscript.codemodel.partial.PartialVariantOptionExpression;
import org.openzen.zenscript.codemodel.scope.TypeScope;
import org.openzen.zenscript.codemodel.type.ArrayTypeID;
import org.openzen.zenscript.codemodel.type.BasicTypeID;
import org.openzen.zenscript.codemodel.type.DefinitionTypeID;
import org.openzen.zenscript.codemodel.type.GenericTypeID;
import org.openzen.zenscript.codemodel.type.GlobalTypeRegistry;
import org.openzen.zenscript.codemodel.type.InvalidTypeID;
import org.openzen.zenscript.codemodel.type.TypeID;

public final class TypeMembers {
   public final TypeID type;
   private final LocalMemberCache cache;
   private final List<TypeMember<CasterMemberRef>> casters = new ArrayList<>();
   private final List<TypeMember<ImplementationMemberRef>> implementations = new ArrayList<>();
   private final List<TypeMember<IteratorMemberRef>> iterators = new ArrayList<>();
   private final Map<String, EnumConstantMember> enumMembers = new HashMap<>();
   private final Map<String, VariantOptionRef> variantOptions = new HashMap<>();
   private final Map<String, TypeMemberGroup> members = new HashMap<>();
   private final Map<String, InnerDefinition> innerTypes = new HashMap<>();
   private final Map<OperatorType, TypeMemberGroup> operators = new HashMap<>();

   public TypeMembers(LocalMemberCache cache, TypeID type) {
      if (type == null) {
         throw new NullPointerException("Type must not be null!");
      }

      if (type == BasicTypeID.UNDETERMINED) {
         throw new IllegalArgumentException("Cannot retrieve members of undetermined type");
      }

      this.cache = cache;
      this.type = type;
   }

   public LocalMemberCache getMemberCache() {
      return this.cache;
   }

   public boolean extendsOrImplements(TypeID other) {
      other = other.getNormalized();
      if (this.type instanceof DefinitionTypeID && other instanceof DefinitionTypeID) {
         DefinitionTypeID thisTypeId = (DefinitionTypeID)this.type;
         DefinitionTypeID otherTypeId = (DefinitionTypeID)other;
         if (thisTypeId.definition == otherTypeId.definition && thisTypeId.definition.typeParameters.length == otherTypeId.typeArguments.length) {
            int i = 0;

            while (true) {
               if (i >= thisTypeId.definition.typeParameters.length) {
                  return true;
               }

               TypeID type = otherTypeId.typeArguments[i];
               if (type != BasicTypeID.UNDETERMINED
                  && (!(type instanceof InvalidTypeID) || ((InvalidTypeID)type).code != CompileExceptionCode.TYPE_ARGUMENTS_NOT_INFERRABLE)
                  && !thisTypeId.definition.typeParameters[i].matches(this.cache, type)) {
                  break;
               }

               i++;
            }
         }
      }

      TypeID superType = this.type.getSuperType(this.cache.getRegistry());
      if (superType != null) {
         if (superType == other) {
            return true;
         }

         if (this.cache.get(superType).extendsOrImplements(other)) {
            return true;
         }
      }

      for (TypeMember<ImplementationMemberRef> implementation : this.implementations) {
         if (implementation.member.implementsType == other) {
            return true;
         }

         if (this.cache.get(implementation.member.implementsType).extendsOrImplements(other)) {
            return true;
         }
      }

      return false;
   }

   public boolean extendsType(TypeID other) {
      other = other.getNormalized();
      TypeID superType = this.type.getSuperType(this.cache.getRegistry());
      return superType == null ? false : superType == other || this.cache.get(superType).extendsType(other);
   }

   public GlobalTypeRegistry getTypeRegistry() {
      return this.cache.getRegistry();
   }

   public void copyMembersTo(TypeMembers other, TypeMemberPriority priority) {
      for (TypeMember<CasterMemberRef> caster : this.casters) {
         other.casters.add(new TypeMember<>(priority, caster.member));
      }

      for (TypeMember<IteratorMemberRef> iterator : this.iterators) {
         other.addIterator(iterator.member, priority);
      }

      for (Entry<String, EnumConstantMember> entry : this.enumMembers.entrySet()) {
         other.addEnumMember(entry.getValue(), priority);
      }

      for (Entry<String, VariantOptionRef> entry : this.variantOptions.entrySet()) {
         other.addVariantOption(entry.getValue());
      }

      for (Entry<String, TypeMemberGroup> entry : this.members.entrySet()) {
         other.getOrCreateGroup(entry.getKey(), entry.getValue().isStatic).merge(entry.getValue(), priority);
      }

      for (Entry<String, InnerDefinition> entry : this.innerTypes.entrySet()) {
         other.innerTypes.put(entry.getKey(), entry.getValue());
      }

      for (Entry<OperatorType, TypeMemberGroup> entry : this.operators.entrySet()) {
         other.getOrCreateGroup(entry.getKey()).merge(entry.getValue(), priority);
      }
   }

   public DefinitionMemberRef getBuiltin(BuiltinID builtin) {
      for (TypeMemberGroup group : this.members.values()) {
         if (group.getConstant() != null && group.getConstant().member.builtin == builtin) {
            return group.getConstant();
         }

         if (group.getField() != null && group.getField().member.builtin == builtin) {
            return group.getField();
         }

         for (TypeMember<FunctionalMemberRef> member : group.getMethodMembers()) {
            if (member.member.getBuiltin() == builtin) {
               return member.member;
            }
         }
      }

      for (TypeMemberGroup group : this.operators.values()) {
         if (group.getConstant() != null && group.getConstant().member.builtin == builtin) {
            return group.getConstant();
         }

         if (group.getField() != null && group.getField().member.builtin == builtin) {
            return group.getField();
         }

         for (TypeMember<FunctionalMemberRef> member : group.getMethodMembers()) {
            if (member.member.getBuiltin() == builtin) {
               return member.member;
            }
         }
      }

      return null;
   }

   public TypeID union(TypeID other) {
      other = other.getNormalized();
      if (this.type == other) {
         return this.type;
      }

      if (this.canCastImplicit(other)) {
         return other;
      }

      if (this.cache.get(other).canCastImplicit(this.type)) {
         return this.type;
      }

      for (TypeMember<ImplementationMemberRef> implementation : this.implementations) {
         TypeID union = this.cache.get(implementation.member.implementsType).union(other);
         if (union != null) {
            return union;
         }
      }

      if (this.type instanceof ArrayTypeID && other instanceof ArrayTypeID) {
         ArrayTypeID thisArray = (ArrayTypeID)this.type;
         ArrayTypeID otherArray = (ArrayTypeID)other;
         if (thisArray.dimension == otherArray.dimension) {
            TypeID union = this.cache.get(thisArray.elementType).union(otherArray.elementType);
            if (union != null) {
               return this.getTypeRegistry().getArray(union, thisArray.dimension);
            }
         }
      }

      return null;
   }

   public List<IDefinitionMember> getUnimplementedMembers(Set<IDefinitionMember> implemented) {
      List<IDefinitionMember> result = new ArrayList<>();

      for (TypeMember<CasterMemberRef> caster : this.casters) {
         if (caster.member.member.isAbstract() && !implemented.contains(caster.member.member)) {
            result.add(caster.member.member);
         }
      }

      for (TypeMember<IteratorMemberRef> iterator : this.iterators) {
         if (iterator.member.target.isAbstract() && !implemented.contains(iterator.member.target)) {
            result.add(iterator.member.target);
         }
      }

      for (Entry<String, TypeMemberGroup> entry : this.members.entrySet()) {
         TypeMemberGroup group = entry.getValue();
         if (group.getGetter() != null && group.getGetter().member.isAbstract() && !implemented.contains(group.getGetter().member)) {
            result.add(group.getGetter().member);
         }

         if (group.getSetter() != null && group.getSetter().member.isAbstract() && !implemented.contains(group.getSetter().member)) {
            result.add(group.getSetter().member);
         }

         for (TypeMember<FunctionalMemberRef> member : group.getMethodMembers()) {
            if (member.member.getTarget().isAbstract() && !implemented.contains(member.member.getTarget())) {
               result.add(member.member.getTarget());
            }
         }
      }

      for (Entry<OperatorType, TypeMemberGroup> entry : this.operators.entrySet()) {
         if (entry.getKey() != OperatorType.DESTRUCTOR) {
            TypeMemberGroup group = entry.getValue();
            if (group.getGetter() != null && group.getGetter().member.isAbstract() && !implemented.contains(group.getGetter().member)) {
               result.add(group.getGetter().member);
            }

            if (group.getSetter() != null && group.getSetter().member.isAbstract() && !implemented.contains(group.getSetter().member)) {
               result.add(group.getSetter().member);
            }

            for (TypeMember<FunctionalMemberRef> member : group.getMethodMembers()) {
               if (member.member.getTarget().isAbstract() && !implemented.contains(member.member.getTarget())) {
                  result.add(member.member.getTarget());
               }
            }
         }
      }

      return result;
   }

   public void addConstructor(FunctionalMemberRef constructor, TypeMemberPriority priority) {
      this.getOrCreateGroup(OperatorType.CONSTRUCTOR).addMethod(constructor, priority);
   }

   public void addConstructor(FunctionalMemberRef constructor) {
      this.getOrCreateGroup(OperatorType.CONSTRUCTOR).addMethod(constructor, TypeMemberPriority.SPECIFIED);
   }

   public void addDestructor(FunctionalMemberRef destructor, TypeMemberPriority priority) {
      this.getOrCreateGroup(OperatorType.DESTRUCTOR).addMethod(destructor, priority);
   }

   public void addCaller(FunctionalMemberRef caller, TypeMemberPriority priority) {
      this.getOrCreateGroup(OperatorType.CALL).addMethod(caller, priority);
   }

   public void addCaster(CasterMemberRef caster, TypeMemberPriority priority) {
      for (int i = 0; i < this.casters.size(); i++) {
         if (((CasterMemberRef)this.casters.get(i).member).toType == caster.toType) {
            this.casters.set(i, this.casters.get(i).resolve(new TypeMember<>(priority, caster)));
         }
      }

      this.casters.add(new TypeMember<>(priority, caster));
   }

   public void addConst(ConstMemberRef member) {
      TypeMemberGroup group = this.getOrCreateGroup(member.member.name, true);
      group.setConst(member, TypeMemberPriority.SPECIFIED);
   }

   public void addField(FieldMemberRef member, TypeMemberPriority priority) {
      TypeMemberGroup group = this.getOrCreateGroup(member.member.name, member.isStatic());
      group.setField(member, priority);
   }

   public void addGetter(GetterMemberRef member, TypeMemberPriority priority) {
      TypeMemberGroup group = this.getOrCreateGroup(member.member.name, member.isStatic());
      group.setGetter(member, priority);
   }

   public void addSetter(SetterMemberRef member, TypeMemberPriority priority) {
      TypeMemberGroup group = this.getOrCreateGroup(member.member.name, member.isStatic());
      group.setSetter(member, priority);
   }

   public void addMethod(String name, FunctionalMemberRef member, TypeMemberPriority priority) {
      TypeMemberGroup group = this.getOrCreateGroup(name, member.isStatic());
      group.addMethod(member, priority);
   }

   public void addOperator(OperatorType operator, FunctionalMemberRef member) {
      this.addOperator(operator, member, TypeMemberPriority.SPECIFIED);
   }

   public boolean hasOperator(OperatorType operator) {
      return this.operators.containsKey(operator) && this.operators.get(operator).hasMethods();
   }

   public void addOperator(OperatorType operator, FunctionalMemberRef member, TypeMemberPriority priority) {
      TypeMemberGroup group = this.getOrCreateGroup(operator);
      group.addMethod(member, priority);
   }

   public void addVariantOption(VariantOptionRef option) {
      this.variantOptions.put(option.getName(), option);
   }

   public void addIterator(IteratorMemberRef iterator, TypeMemberPriority priority) {
      for (int i = 0; i < this.iterators.size(); i++) {
         if (((IteratorMemberRef)this.iterators.get(i).member).getLoopVariableCount() == iterator.getLoopVariableCount()) {
            this.iterators.set(i, this.iterators.get(i).resolve(new TypeMember<>(priority, iterator)));
            return;
         }
      }

      this.iterators.add(new TypeMember<>(priority, iterator));
   }

   public void addImplementation(ImplementationMemberRef member, TypeMemberPriority priority) {
      for (int i = 0; i < this.implementations.size(); i++) {
         if (((ImplementationMemberRef)this.implementations.get(i).member).implementsType == member.implementsType) {
            this.implementations.set(i, this.implementations.get(i).resolve(new TypeMember<>(priority, member)));
            return;
         }
      }

      this.implementations.add(new TypeMember<>(priority, member));
   }

   public void addInnerType(String name, InnerDefinition type) {
      this.innerTypes.put(name, type);
   }

   public TypeMemberGroup getOrCreateGroup(String name, boolean isStatic) {
      if (!this.members.containsKey(name)) {
         this.members.put(name, new TypeMemberGroup(isStatic, name));
      }

      return this.members.get(name);
   }

   public TypeMemberGroup getGroup(String name) {
      return !this.members.containsKey(name) ? new TypeMemberGroup(false, name) : this.members.get(name);
   }

   public TypeMemberGroup getOrCreateGroup(OperatorType operator) {
      if (!this.operators.containsKey(operator)) {
         this.operators.put(operator, new TypeMemberGroup(false, operator.operator + " operator"));
      }

      return this.operators.get(operator);
   }

   public TypeMemberGroup getGroup(OperatorType operator) {
      return !this.operators.containsKey(operator) ? new TypeMemberGroup(false, operator.operator + " operator") : this.operators.get(operator);
   }

   public void addEnumMember(EnumConstantMember member, TypeMemberPriority priority) {
      this.enumMembers.put(member.name, member);
   }

   public EnumConstantMember getEnumMember(String name) {
      return this.enumMembers.get(name);
   }

   public VariantOptionRef getVariantOption(String name) {
      return this.variantOptions.get(name);
   }

   public Expression compare(CodePosition position, TypeScope scope, CompareType operator, Expression left, Expression right) throws CompileException {
      if (operator == CompareType.EQ) {
         TypeMemberGroup equal = this.getOrCreateGroup(OperatorType.EQUALS);

         for (TypeMember<FunctionalMemberRef> member : equal.getMethodMembers()) {
            if (member.member.getHeader().accepts(scope, right)) {
               return equal.call(position, scope, left, new CallArguments(right), false);
            }
         }
      } else if (operator == CompareType.NE) {
         TypeMemberGroup equal = this.getOrCreateGroup(OperatorType.NOTEQUALS);

         for (TypeMember<FunctionalMemberRef> member : equal.getMethodMembers()) {
            if (member.member.getHeader().accepts(scope, right)) {
               return equal.call(position, scope, left, new CallArguments(right), false);
            }
         }

         Expression compare = this.compare(position, scope, CompareType.EQ, left, right);
         return scope.getTypeMembers(compare.type).unary(position, scope, OperatorType.NOT, compare);
      }

      TypeMemberGroup compare = this.getOrCreateGroup(OperatorType.COMPARE);
      return compare.callWithComparator(position, scope, left, new CallArguments(right), operator);
   }

   public Expression unary(CodePosition position, TypeScope scope, OperatorType operator, Expression value) throws CompileException {
      TypeMemberGroup members = this.getOrCreateGroup(operator);
      return members.call(position, scope, value, new CallArguments(Expression.NONE), false);
   }

   public IteratorMemberRef getIterator(int variables) {
      for (TypeMember<IteratorMemberRef> iterator : this.iterators) {
         if (iterator.member.getLoopVariableCount() == variables) {
            return iterator.member;
         }
      }

      return null;
   }

   public TypeID[] getLoopTypes(int variables) {
      for (TypeMember<IteratorMemberRef> iterator : this.iterators) {
         if (iterator.member.getLoopVariableCount() == variables) {
            return iterator.member.types;
         }
      }

      return null;
   }

   public boolean canCastImplicit(TypeID toType) {
      toType = toType.getNormalized();
      if (this.type == toType) {
         return true;
      } else if (toType == BasicTypeID.UNDETERMINED) {
         throw new IllegalArgumentException("Cannot cast to undetermined type!");
      } else if (this.type == BasicTypeID.NULL && toType.isOptional()) {
         return true;
      } else if (this.type.canCastImplicitTo(toType)) {
         return true;
      } else if (toType.canCastImplicitFrom(this.type)) {
         return true;
      } else if (toType.isOptional() && this.canCastImplicit(toType.withoutOptional())) {
         return true;
      } else if (this.type.isOptional() && this.type.withoutOptional() == toType) {
         return true;
      } else if (this.getImplicitCaster(toType) != null || this.extendsOrImplements(toType)) {
         return true;
      } else {
         return this.type.isGeneric() && this.type instanceof GenericTypeID genericTypeID ? genericTypeID.parameter.matches(this.cache, toType) : false;
      }
   }

   public CasterMemberRef getImplicitCaster(TypeID toType) {
      return this.getCaster(toType, false);
   }

   public CasterMemberRef getCaster(TypeID toType) {
      return this.getCaster(toType, true);
   }

   private CasterMemberRef getCaster(TypeID toType, boolean explicit) {
      toType = toType.getNormalized();
      CasterMemberRef foundCaster = null;
      TypeMemberPriority priority = null;

      for (TypeMember<CasterMemberRef> caster : this.casters) {
         if ((explicit || caster.member.isImplicit()) && caster.member.toType == toType && (foundCaster == null || priority.compareTo(caster.priority) < 0)) {
            foundCaster = caster.member;
            priority = caster.priority;
         }
      }

      return foundCaster;
   }

   public boolean canCast(TypeID toType) {
      toType = toType.getNormalized();
      if (this.canCastImplicit(toType)) {
         return true;
      }

      if (this.type.canCastExplicitTo(toType)) {
         return true;
      }

      if (toType.canCastImplicitFrom(this.type)) {
         return true;
      }

      for (TypeMember<CasterMemberRef> caster : this.casters) {
         if (caster.member.toType == toType) {
            return true;
         }
      }

      return false;
   }

   public Map<DefinitionMemberRef, IDefinitionMember> borrowInterfaceMembersFromDefinition(Set<IDefinitionMember> implemented, TypeMembers definitionMembers) {
      Map<DefinitionMemberRef, IDefinitionMember> result = new HashMap<>();

      for (TypeMember<CasterMemberRef> caster : this.casters) {
         if (!implemented.contains(caster.member.member)) {
            CasterMemberRef implementation = definitionMembers.getCaster(caster.member.toType);
            if (implementation != null) {
               result.put(caster.member, implementation.getTarget());
            }
         }
      }

      for (TypeMember<IteratorMemberRef> iterator : this.iterators) {
         if (!implemented.contains(iterator.member.target)) {
            IteratorMemberRef implementation = definitionMembers.getIterator(iterator.member.getLoopVariableCount());
            if (implementation != null) {
               result.put(iterator.member, implementation.getTarget());
            }
         }
      }

      for (Entry<String, TypeMemberGroup> entry : this.members.entrySet()) {
         TypeMemberGroup group = entry.getValue();
         TypeMemberGroup definitionGroup = definitionMembers.getGroup(entry.getKey());
         if (definitionGroup != null) {
            if (group.getGetter() != null && !implemented.contains(group.getGetter().member)) {
               GetterMemberRef implementation = definitionGroup.getGetter();
               if (implementation != null) {
                  result.put(group.getGetter(), implementation.getTarget());
               }
            }

            if (group.getSetter() != null && !implemented.contains(group.getSetter().member)) {
               SetterMemberRef implementation = definitionGroup.getSetter();
               if (implementation != null) {
                  result.put(group.getSetter(), implementation.getTarget());
               }
            }

            for (TypeMember<FunctionalMemberRef> member : group.getMethodMembers()) {
               if (!implemented.contains(member.member.getTarget())) {
                  FunctionalMemberRef functional = definitionGroup.getMethod(member.member.getHeader());
                  if (functional != null) {
                     result.put(member.member, functional.getTarget());
                  }
               }
            }
         }
      }

      for (Entry<OperatorType, TypeMemberGroup> entry : this.operators.entrySet()) {
         if (entry.getKey() != OperatorType.DESTRUCTOR) {
            TypeMemberGroup group = entry.getValue();
            TypeMemberGroup definitionGroup = definitionMembers.getGroup(entry.getKey());
            if (definitionGroup != null) {
               for (TypeMember<FunctionalMemberRef> member : group.getMethodMembers()) {
                  if (!implemented.contains(member.member.getTarget())) {
                     FunctionalMemberRef functional = definitionGroup.getMethod(member.member.getHeader());
                     if (functional != null) {
                        result.put(member.member, functional.getTarget());
                     }
                  }
               }
            }
         }
      }

      return result;
   }

   public Expression castImplicit(CodePosition position, Expression value, TypeID toType, boolean implicit) {
      if (toType == null) {
         throw new NullPointerException();
      }

      toType = toType.getNormalized();
      if (toType == BasicTypeID.UNDETERMINED) {
         return value;
      }

      if (this.type == toType) {
         return value;
      }

      if (this.type.canCastImplicitTo(toType)) {
         return this.type.castImplicitTo(position, value, toType);
      }

      if (toType.canCastImplicitFrom(this.type)) {
         return toType.castImplicitFrom(position, value);
      }

      if (this.type == BasicTypeID.NULL && toType.isOptional()) {
         return new NullExpression(position, toType);
      }

      if (toType.isOptional() && this.canCastImplicit(toType.withoutOptional())) {
         return new WrapOptionalExpression(position, this.castImplicit(position, value, toType.withoutOptional(), implicit), toType);
      }

      if (this.type.isOptional() && this.type.withoutOptional() == toType) {
         return new CheckNullExpression(position, value);
      }

      if (this.extendsOrImplements(toType)) {
         return new SupertypeCastExpression(position, value, toType);
      }

      for (TypeMember<ImplementationMemberRef> implementation : this.implementations) {
         if (implementation.member.implementsType.getNormalized() == toType) {
            return new InterfaceCastExpression(position, value, implementation.member);
         }
      }

      for (TypeMember<CasterMemberRef> caster : this.casters) {
         if (caster.member.isImplicit() && caster.member.toType == toType) {
            return caster.member.cast(position, value, implicit);
         }
      }

      return new InvalidExpression(position, toType, CompileExceptionCode.INVALID_CAST, "Could not cast " + this + " to " + toType);
   }

   public Expression castExplicit(CodePosition position, Expression value, TypeID toType, boolean optional) {
      toType = toType.getNormalized();
      if (this.canCastImplicit(toType)) {
         return this.castImplicit(position, value, toType, false);
      }

      if (this.type.canCastExplicitTo(toType)) {
         return this.type.castExplicitTo(position, value, toType);
      }

      TypeMembers typeMembers = this.cache.get(this.type);
      if (this.type != typeMembers.type && typeMembers.canCast(toType)) {
         return typeMembers.castExplicit(position, value, toType, optional);
      }

      for (TypeMember<CasterMemberRef> caster : this.casters) {
         if (caster.member.toType == toType) {
            return caster.member.cast(position, value, false);
         }
      }

      return toType.canCastImplicitFrom(this.type)
         ? toType.castImplicitFrom(position, value)
         : new InvalidExpression(position, toType, CompileExceptionCode.INVALID_CAST, "Cannot cast " + this + " to " + toType + ", even explicitly");
   }

   public boolean hasMember(String name) {
      return this.members.containsKey(name);
   }

   public IPartialExpression getMemberExpression(CodePosition position, TypeScope scope, Expression target, GenericName name, boolean allowStatic) {
      if (this.members.containsKey(name.name)) {
         TypeMemberGroup group = this.members.get(name.name);
         return group.isStatic
            ? new PartialStaticMemberGroupExpression(position, scope, this.type, group, name.arguments)
            : new PartialMemberGroupExpression(position, scope, target, group, name.arguments, allowStatic);
      } else {
         return this.type.isOptional()
            ? scope.getTypeMembers(this.type.withoutOptional()).getMemberExpression(position, scope, target, name, allowStatic)
            : null;
      }
   }

   public IPartialExpression getStaticMemberExpression(CodePosition position, TypeScope scope, GenericName name) {
      if (this.members.containsKey(name.name)) {
         return new PartialStaticMemberGroupExpression(position, scope, this.type, this.members.get(name.name), name.arguments);
      } else if (this.innerTypes.containsKey(name.name)) {
         return new PartialTypeExpression(
            position, this.innerTypes.get(name.name).instance(this.cache.getRegistry(), name.arguments, (DefinitionTypeID)this.type), name.arguments
         );
      } else if (this.variantOptions.containsKey(name.name)) {
         return new PartialVariantOptionExpression(position, scope, this.variantOptions.get(name.name));
      } else if (this.enumMembers.containsKey(name.name)) {
         return new EnumConstantExpression(position, this.type, this.enumMembers.get(name.name));
      } else {
         return this.type.isOptional() ? scope.getTypeMembers(this.type.withoutOptional()).getStaticMemberExpression(position, scope, name) : null;
      }
   }

   public boolean hasInnerType(String name) {
      return this.innerTypes.containsKey(name);
   }

   public DefinitionTypeID getInnerType(CodePosition position, GenericName name) {
      if (!this.innerTypes.containsKey(name.name)) {
         throw new RuntimeException("No such inner type in " + this.type + ": " + name.name);
      } else {
         return this.innerTypes.get(name.name).instance(this.cache.getRegistry(), name.arguments, (DefinitionTypeID)this.type);
      }
   }

   @Override
   public String toString() {
      return this.type.toString();
   }
}
