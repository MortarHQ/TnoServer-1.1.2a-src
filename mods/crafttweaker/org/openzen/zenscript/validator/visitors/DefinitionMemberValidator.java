package org.openzen.zenscript.validator.visitors;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.openzen.zenscript.codemodel.AccessScope;
import org.openzen.zenscript.codemodel.FunctionHeader;
import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.codemodel.Modifiers;
import org.openzen.zenscript.codemodel.definition.EnumDefinition;
import org.openzen.zenscript.codemodel.member.CallerMember;
import org.openzen.zenscript.codemodel.member.CasterMember;
import org.openzen.zenscript.codemodel.member.ConstMember;
import org.openzen.zenscript.codemodel.member.ConstructorMember;
import org.openzen.zenscript.codemodel.member.DefinitionMember;
import org.openzen.zenscript.codemodel.member.DestructorMember;
import org.openzen.zenscript.codemodel.member.EnumConstantMember;
import org.openzen.zenscript.codemodel.member.FieldMember;
import org.openzen.zenscript.codemodel.member.FunctionalMember;
import org.openzen.zenscript.codemodel.member.GetterMember;
import org.openzen.zenscript.codemodel.member.IDefinitionMember;
import org.openzen.zenscript.codemodel.member.ImplementationMember;
import org.openzen.zenscript.codemodel.member.InnerDefinitionMember;
import org.openzen.zenscript.codemodel.member.IteratorMember;
import org.openzen.zenscript.codemodel.member.MemberVisitor;
import org.openzen.zenscript.codemodel.member.MethodMember;
import org.openzen.zenscript.codemodel.member.OperatorMember;
import org.openzen.zenscript.codemodel.member.SetterMember;
import org.openzen.zenscript.codemodel.member.StaticInitializerMember;
import org.openzen.zenscript.codemodel.member.ref.DefinitionMemberRef;
import org.openzen.zenscript.codemodel.scope.TypeScope;
import org.openzen.zenscript.codemodel.statement.EmptyStatement;
import org.openzen.zenscript.codemodel.statement.Statement;
import org.openzen.zenscript.codemodel.statement.VarStatement;
import org.openzen.zenscript.codemodel.type.BasicTypeID;
import org.openzen.zenscript.codemodel.type.DefinitionTypeID;
import org.openzen.zenscript.codemodel.type.TypeID;
import org.openzen.zenscript.codemodel.type.member.BuiltinID;
import org.openzen.zenscript.codemodel.type.member.TypeMembers;
import org.openzen.zenscript.validator.TypeContext;
import org.openzen.zenscript.validator.ValidationLogEntry;
import org.openzen.zenscript.validator.Validator;
import org.openzen.zenscript.validator.analysis.ExpressionScope;
import org.openzen.zenscript.validator.analysis.StatementScope;

public class DefinitionMemberValidator implements MemberVisitor<Void> {
   private final Validator validator;
   private final HighLevelDefinition definition;
   private final TypeScope scope;
   private final DefinitionMemberContext context;
   private final Set<String> fieldNames = new HashSet<>();
   private final Set<String> members = new HashSet<>();
   private final Set<FieldMember> initializedFields = new HashSet<>();
   private final List<FunctionHeader> constructors = new ArrayList<>();
   private final Set<EnumConstantMember> initializedEnumConstants = new HashSet<>();
   private final Set<TypeID> implementedTypes = new HashSet<>();
   private boolean hasDestructor = false;

   public DefinitionMemberValidator(Validator validator, HighLevelDefinition definition, TypeScope scope, DefinitionMemberContext context) {
      this.validator = validator;
      this.definition = definition;
      this.scope = scope;
      this.context = context;
   }

   public Void visitConst(ConstMember member) {
      ValidationUtils.validateModifiers(this.validator, member.getEffectiveModifiers(), 261, member.position, "Invalid modifier");
      if (member.getType() != member.value.type) {
         this.validator.logError(ValidationLogEntry.Code.INVALID_TYPE, member.position, "Expression type doesn't match const type");
      }

      member.value.accept(new ExpressionValidator(this.validator, new DefinitionMemberValidator.FieldInitializerScope(member)));
      return null;
   }

   public Void visitField(FieldMember member) {
      if (this.fieldNames.contains(member.name)) {
         this.validator.logError(ValidationLogEntry.Code.DUPLICATE_FIELD_NAME, member.position, "Duplicate field name: " + member.name);
      }

      this.fieldNames.add(member.name);
      new TypeValidator(this.validator, member.position).validate(TypeContext.FIELD_TYPE, member.getType());
      if (member.initializer != null) {
         member.initializer.accept(new ExpressionValidator(this.validator, new DefinitionMemberValidator.FieldInitializerScope(member)));
      }

      return null;
   }

   public Void visitConstructor(ConstructorMember member) {
      if (member.getDefinition() instanceof EnumDefinition) {
         ValidationUtils.validateModifiers(this.validator, member.getEffectiveModifiers(), 4, member.position, "Invalid modifier");
      }

      for (FunctionHeader existing : this.constructors) {
         if (existing.isSimilarTo(member.header)) {
            this.validator
               .logError(ValidationLogEntry.Code.DUPLICATE_CONSTRUCTOR, member.position, "Duplicate constructor, conflicts with this" + existing.toString());
         }
      }

      this.constructors.add(member.header);
      ValidationUtils.validateHeader(this.validator, member.position, member.header, member.getAccessScope(), this.scope.getMemberCache());
      if (member.body == null) {
         if (member.getBuiltin() == BuiltinID.CLASS_DEFAULT_CONSTRUCTOR) {
            this.checkConstructorForwarded(member);
         }

         if (!member.isExtern()) {
            this.validator.logError(ValidationLogEntry.Code.BODY_REQUIRED, member.position, "Constructors must have a body");
            return null;
         }
      } else {
         this.checkConstructorForwarded(member);
      }

      return null;
   }

   private void checkConstructorForwarded(ConstructorMember member) {
      Statement body = member.body == null ? new EmptyStatement(member.position) : member.body;
      StatementValidator statementValidator = new StatementValidator(
         this.validator, new DefinitionMemberValidator.ConstructorStatementScope(member.header, member.getAccessScope())
      );
      body.accept(statementValidator);
      this.validateThrow(member, member.header, body);
      if (member.definition.getSuperType() != null && !statementValidator.constructorForwarded) {
         if (member.definition.getSuperType() instanceof DefinitionTypeID
            && ((DefinitionTypeID)member.definition.getSuperType()).definition.hasEmptyConstructor()) {
            return;
         }

         this.validator.logError(ValidationLogEntry.Code.CONSTRUCTOR_FORWARD_MISSING, member.position, "Constructor not forwarded to base type");
      }
   }

   public Void visitDestructor(DestructorMember member) {
      if (this.hasDestructor) {
         this.validator.logError(ValidationLogEntry.Code.MULTIPLE_DESTRUCTORS, member.position, "A type have only a single destructor");
      }

      this.hasDestructor = true;
      if (member.header.thrownType != null) {
         this.validator.logError(ValidationLogEntry.Code.DESTRUCTOR_CANNOT_THROW, member.position, "Destructor cannot throw");
      }

      this.validateFunctional(member, new DefinitionMemberValidator.MethodStatementScope(member.header, member.getAccessScope()));
      return null;
   }

   public Void visitMethod(MethodMember member) {
      ValidationUtils.validateIdentifier(this.validator, member.position, member.name);
      ValidationUtils.validateHeader(this.validator, member.position, member.header, member.getAccessScope(), this.scope.getMemberCache());
      this.validateFunctional(member, new DefinitionMemberValidator.MethodStatementScope(member.header, member.getAccessScope()));
      return null;
   }

   public Void visitGetter(GetterMember member) {
      ValidationUtils.validateIdentifier(this.validator, member.position, member.name);
      new TypeValidator(this.validator, member.position).validate(TypeContext.GETTER_TYPE, member.getType());
      this.validateGetter(member, new DefinitionMemberValidator.MethodStatementScope(new FunctionHeader(member.getType()), member.getAccessScope()));
      return null;
   }

   public Void visitSetter(SetterMember member) {
      ValidationUtils.validateIdentifier(this.validator, member.position, member.name);
      new TypeValidator(this.validator, member.position).validate(TypeContext.SETTER_TYPE, member.getType());
      this.validateSetter(
         member, new DefinitionMemberValidator.MethodStatementScope(new FunctionHeader(BasicTypeID.VOID, member.parameter), member.getAccessScope())
      );
      return null;
   }

   public void visitEnumConstant(EnumConstantMember member) {
      ValidationUtils.validateIdentifier(this.validator, member.position, member.name);
      if (member.constructor != null) {
         member.constructor.accept(new ExpressionValidator(this.validator, new DefinitionMemberValidator.EnumConstantInitializerScope()));
      }

      if (this.members.contains(member.name)) {
         this.validator.logError(ValidationLogEntry.Code.INVALID_TYPE, member.position, "Duplicate enum value: " + member.name);
      }

      this.initializedEnumConstants.add(member);
   }

   public Void visitOperator(OperatorMember member) {
      ValidationUtils.validateHeader(this.validator, member.position, member.header, member.getAccessScope(), this.scope.getMemberCache());
      this.validateFunctional(member, new DefinitionMemberValidator.MethodStatementScope(member.header, member.getAccessScope()));
      return null;
   }

   public Void visitCaster(CasterMember member) {
      new TypeValidator(this.validator, member.position).validate(TypeContext.CASTER_TYPE, member.toType);
      this.validateFunctional(member, new DefinitionMemberValidator.MethodStatementScope(member.header, member.getAccessScope()));
      return null;
   }

   public Void visitCustomIterator(IteratorMember member) {
      TypeValidator typeValidator = new TypeValidator(this.validator, member.position);

      for (TypeID type : member.getLoopVariableTypes()) {
         typeValidator.validate(TypeContext.ITERATOR_TYPE, type);
      }

      this.validateFunctional(
         member,
         new DefinitionMemberValidator.MethodStatementScope(
            new FunctionHeader(this.scope.getTypeRegistry().getIterator(member.getLoopVariableTypes())), member.getAccessScope()
         )
      );
      return null;
   }

   public Void visitCaller(CallerMember member) {
      ValidationUtils.validateHeader(this.validator, member.position, member.header, member.getAccessScope(), this.scope.getMemberCache());
      this.validateFunctional(member, new DefinitionMemberValidator.MethodStatementScope(member.header, member.getAccessScope()));
      return null;
   }

   public Void visitImplementation(ImplementationMember implementation) {
      if (this.context == DefinitionMemberContext.IMPLEMENTATION) {
         this.validator.logError(ValidationLogEntry.Code.IMPLEMENTATION_NESTED, implementation.position, "Cannot nest implementations");
         return null;
      }

      if (this.implementedTypes.contains(implementation.type)) {
         this.validator
            .logError(ValidationLogEntry.Code.TYPE_ALREADY_IMPLEMENTED, implementation.position, "Type is already implemented: " + implementation.type);
      }

      this.implementedTypes.add(implementation.type);
      if (!(implementation.type instanceof DefinitionTypeID type)) {
         this.validator.logError(ValidationLogEntry.Code.INVALID_IMPLEMENTATION_TYPE, implementation.position, "Implementation type must be an interface");
      } else if (!type.definition.isInterface()) {
         this.validator.logError(ValidationLogEntry.Code.INVALID_IMPLEMENTATION_TYPE, implementation.position, "Implementation type must be an interface");
      }

      DefinitionMemberValidator memberValidator = new DefinitionMemberValidator(
         this.validator, this.definition, this.scope, DefinitionMemberContext.IMPLEMENTATION
      );

      for (IDefinitionMember member : implementation.members) {
         member.accept(memberValidator);
      }

      this.checkImplementationComplete(implementation);
      return null;
   }

   private void checkImplementationComplete(ImplementationMember implementation) {
      Set<IDefinitionMember> implemented = new HashSet<>();

      for (IDefinitionMember member : implementation.members) {
         if (member.getOverrides() != null) {
            implemented.add(member.getOverrides().getTarget());
         }
      }

      for (DefinitionMemberRef member : implementation.definitionBorrowedMembers.keySet()) {
         implemented.add(member.getTarget());
      }

      TypeMembers members = this.scope.getTypeMembers(implementation.type);
      List<IDefinitionMember> unimplemented = members.getUnimplementedMembers(implemented);
      if (unimplemented.size() == 1) {
         this.validator
            .logError(ValidationLogEntry.Code.INCOMPLETE_IMPLEMENTATION, implementation.position, unimplemented.get(0).describe() + " not implemented");
      } else if (unimplemented.size() > 1) {
         StringBuilder message = new StringBuilder();
         message.append("Implementation incomplete: ").append(unimplemented.size()).append(" members not yet implemented:");

         for (IDefinitionMember member : unimplemented) {
            message.append("\n").append("  - ").append(member.describe());
         }

         this.validator.logError(ValidationLogEntry.Code.INCOMPLETE_IMPLEMENTATION, implementation.position, message.toString());
      }
   }

   public Void visitInnerDefinition(InnerDefinitionMember innerDefinition) {
      if (this.members.contains(innerDefinition.innerDefinition.name)) {
         this.validator
            .logError(ValidationLogEntry.Code.DUPLICATE_MEMBER_NAME, innerDefinition.position, "Duplicate member name: " + innerDefinition.innerDefinition.name);
      }

      innerDefinition.innerDefinition.accept(new DefinitionValidator(this.validator));
      return null;
   }

   public Void visitStaticInitializer(StaticInitializerMember member) {
      member.body.accept(new StatementValidator(this.validator, new DefinitionMemberValidator.StaticInitializerScope(member.getAccessScope())));
      if (member.body.thrownType != null) {
         this.validator.logError(ValidationLogEntry.Code.STATIC_INITIALIZER_CANNOT_THROW, member.position, "Static initializer cannot throw");
      }

      return null;
   }

   private void validateThrow(DefinitionMember member, FunctionHeader header, Statement body) {
      if (body.thrownType != null && header.thrownType == null) {
         this.validator.logError(ValidationLogEntry.Code.THROW_WITHOUT_THROWS, member.position, "Method is throwing but doesn't declare throws type");
      }
   }

   private void validateFunctional(FunctionalMember member, StatementScope scope) {
      if (Modifiers.isOverride(member.getEffectiveModifiers()) || this.context == DefinitionMemberContext.IMPLEMENTATION && !member.isPrivate()) {
         if (member.getOverrides() == null) {
            this.validator.logError(ValidationLogEntry.Code.OVERRIDE_MISSING_BASE, member.position, "Overridden method not identified");
         } else {
            ValidationUtils.validateValidOverride(this.validator, member.position, this.scope, member.header, member.getOverrides().getHeader());
         }
      }

      if (member.body != null) {
         StatementValidator statementValidator = new StatementValidator(this.validator, scope);
         member.body.accept(statementValidator);
         this.validateThrow(member, member.header, member.body);
      }
   }

   private void validateGetter(GetterMember member, StatementScope scope) {
      if ((Modifiers.isOverride(member.getEffectiveModifiers()) || this.context == DefinitionMemberContext.IMPLEMENTATION && !member.isPrivate())
         && member.getOverrides() == null) {
         this.validator.logError(ValidationLogEntry.Code.OVERRIDE_MISSING_BASE, member.position, "Overridden method not identified");
      }

      if (member.body != null) {
         StatementValidator statementValidator = new StatementValidator(this.validator, scope);
         member.body.accept(statementValidator);
         this.validateThrow(member, new FunctionHeader(member.getType()), member.body);
      }
   }

   private void validateSetter(SetterMember member, StatementScope scope) {
      if ((Modifiers.isOverride(member.getEffectiveModifiers()) || this.context == DefinitionMemberContext.IMPLEMENTATION && !member.isPrivate())
         && member.getOverrides() == null) {
         this.validator.logError(ValidationLogEntry.Code.OVERRIDE_MISSING_BASE, member.position, "Overridden method not identified");
      }

      if (member.body != null) {
         StatementValidator statementValidator = new StatementValidator(this.validator, scope);
         member.body.accept(statementValidator);
         this.validateThrow(member, new FunctionHeader(BasicTypeID.VOID, member.getType()), member.body);
      }
   }

   private class ConstructorStatementScope implements StatementScope {
      private final FunctionHeader header;
      private final AccessScope access;

      public ConstructorStatementScope(FunctionHeader header, AccessScope access) {
         this.header = header;
         this.access = access;
      }

      @Override
      public boolean isConstructor() {
         return true;
      }

      @Override
      public boolean isStatic() {
         return false;
      }

      @Override
      public FunctionHeader getFunctionHeader() {
         return this.header;
      }

      @Override
      public boolean isStaticInitializer() {
         return false;
      }

      @Override
      public HighLevelDefinition getDefinition() {
         return DefinitionMemberValidator.this.definition;
      }

      @Override
      public AccessScope getAccessScope() {
         return this.access;
      }
   }

   private class EnumConstantInitializerScope implements ExpressionScope {
      @Override
      public boolean isConstructor() {
         return false;
      }

      @Override
      public boolean isFirstStatement() {
         return false;
      }

      @Override
      public boolean hasThis() {
         return false;
      }

      @Override
      public boolean isFieldInitialized(FieldMember field) {
         return false;
      }

      @Override
      public void markConstructorForwarded() {
      }

      @Override
      public boolean isEnumConstantInitialized(EnumConstantMember member) {
         return member.definition == DefinitionMemberValidator.this.definition
            ? DefinitionMemberValidator.this.initializedEnumConstants.contains(member)
            : true;
      }

      @Override
      public boolean isLocalVariableInitialized(VarStatement variable) {
         return false;
      }

      @Override
      public boolean isStaticInitializer() {
         return false;
      }

      @Override
      public HighLevelDefinition getDefinition() {
         return DefinitionMemberValidator.this.definition;
      }

      @Override
      public AccessScope getAccessScope() {
         return DefinitionMemberValidator.this.definition.getAccessScope();
      }
   }

   private class FieldInitializerScope implements ExpressionScope {
      private final DefinitionMember field;

      public FieldInitializerScope(DefinitionMember field) {
         this.field = field;
      }

      @Override
      public boolean isConstructor() {
         return false;
      }

      @Override
      public boolean isFirstStatement() {
         return true;
      }

      @Override
      public boolean hasThis() {
         return !this.field.isStatic();
      }

      @Override
      public boolean isFieldInitialized(FieldMember field) {
         return DefinitionMemberValidator.this.initializedFields.contains(field);
      }

      @Override
      public void markConstructorForwarded() {
      }

      @Override
      public boolean isEnumConstantInitialized(EnumConstantMember member) {
         return true;
      }

      @Override
      public boolean isLocalVariableInitialized(VarStatement variable) {
         return true;
      }

      @Override
      public boolean isStaticInitializer() {
         return false;
      }

      @Override
      public HighLevelDefinition getDefinition() {
         return DefinitionMemberValidator.this.definition;
      }

      @Override
      public AccessScope getAccessScope() {
         return this.field.getAccessScope();
      }
   }

   private class MethodStatementScope implements StatementScope {
      private final FunctionHeader header;
      private final AccessScope access;

      public MethodStatementScope(FunctionHeader header, AccessScope access) {
         this.header = header;
         this.access = access;
      }

      @Override
      public boolean isConstructor() {
         return false;
      }

      @Override
      public boolean isStatic() {
         return false;
      }

      @Override
      public FunctionHeader getFunctionHeader() {
         return this.header;
      }

      @Override
      public boolean isStaticInitializer() {
         return false;
      }

      @Override
      public HighLevelDefinition getDefinition() {
         return DefinitionMemberValidator.this.definition;
      }

      @Override
      public AccessScope getAccessScope() {
         return this.access;
      }
   }

   private class StaticInitializerScope implements StatementScope {
      private final FunctionHeader header = new FunctionHeader(BasicTypeID.VOID);
      private final AccessScope access;

      public StaticInitializerScope(AccessScope access) {
         this.access = access;
      }

      @Override
      public boolean isConstructor() {
         return false;
      }

      @Override
      public boolean isStatic() {
         return true;
      }

      @Override
      public FunctionHeader getFunctionHeader() {
         return this.header;
      }

      @Override
      public boolean isStaticInitializer() {
         return true;
      }

      @Override
      public HighLevelDefinition getDefinition() {
         return DefinitionMemberValidator.this.definition;
      }

      @Override
      public AccessScope getAccessScope() {
         return this.access;
      }
   }
}
