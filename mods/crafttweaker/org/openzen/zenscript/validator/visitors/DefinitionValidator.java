package org.openzen.zenscript.validator.visitors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.codemodel.AccessScope;
import org.openzen.zenscript.codemodel.FunctionHeader;
import org.openzen.zenscript.codemodel.GenericMapper;
import org.openzen.zenscript.codemodel.GenericName;
import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.codemodel.annotations.AnnotationDefinition;
import org.openzen.zenscript.codemodel.definition.AliasDefinition;
import org.openzen.zenscript.codemodel.definition.ClassDefinition;
import org.openzen.zenscript.codemodel.definition.DefinitionVisitor;
import org.openzen.zenscript.codemodel.definition.EnumDefinition;
import org.openzen.zenscript.codemodel.definition.ExpansionDefinition;
import org.openzen.zenscript.codemodel.definition.FunctionDefinition;
import org.openzen.zenscript.codemodel.definition.InterfaceDefinition;
import org.openzen.zenscript.codemodel.definition.StructDefinition;
import org.openzen.zenscript.codemodel.definition.VariantDefinition;
import org.openzen.zenscript.codemodel.definition.ZSPackage;
import org.openzen.zenscript.codemodel.member.EnumConstantMember;
import org.openzen.zenscript.codemodel.member.IDefinitionMember;
import org.openzen.zenscript.codemodel.scope.TypeScope;
import org.openzen.zenscript.codemodel.type.GlobalTypeRegistry;
import org.openzen.zenscript.codemodel.type.TypeID;
import org.openzen.zenscript.codemodel.type.member.LocalMemberCache;
import org.openzen.zenscript.codemodel.type.member.TypeMemberPreparer;
import org.openzen.zenscript.validator.TypeContext;
import org.openzen.zenscript.validator.Validator;
import org.openzen.zenscript.validator.analysis.StatementScope;

public class DefinitionValidator implements DefinitionVisitor<Void> {
   private final Validator validator;

   public DefinitionValidator(Validator validator) {
      this.validator = validator;
   }

   public Void visitClass(ClassDefinition definition) {
      ValidationUtils.validateModifiers(this.validator, definition.modifiers, 1423, definition.position, "Invalid class modifier");
      ValidationUtils.validateIdentifier(this.validator, definition.position, definition.name);
      if (definition.getSuperType() != null) {
         definition.getSuperType().accept(new SupertypeValidator(this.validator, definition.position, definition));
      }

      this.validateMembers(definition, DefinitionMemberContext.DEFINITION);
      return null;
   }

   public Void visitInterface(InterfaceDefinition definition) {
      ValidationUtils.validateModifiers(this.validator, definition.modifiers, 263, definition.position, "Invalid interface modifier");
      ValidationUtils.validateIdentifier(this.validator, definition.position, definition.name);
      this.validateMembers(definition, DefinitionMemberContext.DEFINITION);
      return null;
   }

   public Void visitEnum(EnumDefinition definition) {
      ValidationUtils.validateModifiers(this.validator, definition.modifiers, 263, definition.position, "Invalid enum modifier");
      ValidationUtils.validateIdentifier(this.validator, definition.position, definition.name);
      this.validateMembers(definition, DefinitionMemberContext.DEFINITION);
      return null;
   }

   public Void visitStruct(StructDefinition definition) {
      int validModifiers = 263;
      if (definition.outerDefinition != null) {
         validModifiers |= 128;
      }

      ValidationUtils.validateModifiers(this.validator, definition.modifiers, validModifiers, definition.position, "Invalid struct modifier");
      ValidationUtils.validateIdentifier(this.validator, definition.position, definition.name);
      this.validateMembers(definition, DefinitionMemberContext.DEFINITION);
      return null;
   }

   public Void visitFunction(FunctionDefinition definition) {
      ValidationUtils.validateModifiers(this.validator, definition.modifiers, 263, definition.position, "Invalid function modifier");
      ValidationUtils.validateIdentifier(this.validator, definition.position, definition.name);
      StatementValidator statementValidator = new StatementValidator(
         this.validator, new DefinitionValidator.FunctionStatementScope(definition.header, definition.getAccessScope())
      );
      definition.caller.body.accept(statementValidator);
      return null;
   }

   public Void visitExpansion(ExpansionDefinition definition) {
      ValidationUtils.validateModifiers(this.validator, definition.modifiers, 263, definition.position, "Invalid expansion modifier");
      new TypeValidator(this.validator, definition.position).validate(TypeContext.EXPANSION_TARGET_TYPE, definition.target);
      this.validateMembers(definition, DefinitionMemberContext.EXPANSION);
      return null;
   }

   public Void visitAlias(AliasDefinition definition) {
      ValidationUtils.validateModifiers(this.validator, definition.modifiers, 263, definition.position, "Invalid alias modifier");
      ValidationUtils.validateIdentifier(this.validator, definition.position, definition.name);
      return null;
   }

   private void validateMembers(HighLevelDefinition definition, DefinitionMemberContext context) {
      DefinitionValidator.SimpleTypeScope scope = new DefinitionValidator.SimpleTypeScope(
         this.validator.registry, this.validator.expansions, this.validator.annotations
      );
      DefinitionMemberValidator memberValidator = new DefinitionMemberValidator(this.validator, definition, scope, context);

      for (IDefinitionMember member : definition.members) {
         member.accept(memberValidator);
      }

      if (definition instanceof EnumDefinition) {
         for (EnumConstantMember constant : ((EnumDefinition)definition).enumConstants) {
            memberValidator.visitEnumConstant(constant);
         }
      }
   }

   public Void visitVariant(VariantDefinition variant) {
      ValidationUtils.validateModifiers(this.validator, variant.modifiers, 263, variant.position, "Invalid variant modifier");
      ValidationUtils.validateIdentifier(this.validator, variant.position, variant.name);

      for (VariantDefinition.Option option : variant.options) {
         this.validate(option);
      }

      this.validateMembers(variant, DefinitionMemberContext.DEFINITION);
      return null;
   }

   private void validate(VariantDefinition.Option option) {
      ValidationUtils.validateIdentifier(this.validator, option.position, option.name);
      TypeValidator typeValidator = new TypeValidator(this.validator, option.position);

      for (TypeID type : option.types) {
         typeValidator.validate(TypeContext.OPTION_MEMBER_TYPE, type);
      }
   }

   private class FunctionStatementScope implements StatementScope {
      private final FunctionHeader header;
      private final AccessScope access;

      public FunctionStatementScope(FunctionHeader header, AccessScope access) {
         this.header = header;
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
         return false;
      }

      @Override
      public HighLevelDefinition getDefinition() {
         return null;
      }

      @Override
      public AccessScope getAccessScope() {
         return this.access;
      }
   }

   private class SimpleTypeScope implements TypeScope {
      private final LocalMemberCache memberCache;
      private final Map<String, AnnotationDefinition> annotations = new HashMap<>();

      public SimpleTypeScope(GlobalTypeRegistry typeRegistry, List<ExpansionDefinition> expansions, AnnotationDefinition[] annotations) {
         this.memberCache = new LocalMemberCache(typeRegistry, expansions);

         for (AnnotationDefinition annotation : annotations) {
            this.annotations.put(annotation.getAnnotationName(), annotation);
         }
      }

      @Override
      public ZSPackage getRootPackage() {
         throw new UnsupportedOperationException();
      }

      @Override
      public LocalMemberCache getMemberCache() {
         return this.memberCache;
      }

      @Override
      public AnnotationDefinition getAnnotation(String name) {
         return this.annotations.get(name);
      }

      @Override
      public TypeID getType(CodePosition position, List<GenericName> name) {
         throw new UnsupportedOperationException();
      }

      @Override
      public TypeID getThisType() {
         throw new UnsupportedOperationException();
      }

      @Override
      public TypeMemberPreparer getPreparer() {
         return member -> {};
      }

      @Override
      public GenericMapper getLocalTypeParameters() {
         throw new UnsupportedOperationException();
      }
   }
}
