package org.openzen.zenscript.validator.visitors;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.codemodel.AccessScope;
import org.openzen.zenscript.codemodel.FunctionHeader;
import org.openzen.zenscript.codemodel.FunctionParameter;
import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.codemodel.Modifiers;
import org.openzen.zenscript.codemodel.expression.Expression;
import org.openzen.zenscript.codemodel.generic.GenericParameterBoundVisitor;
import org.openzen.zenscript.codemodel.generic.ParameterSuperBound;
import org.openzen.zenscript.codemodel.generic.ParameterTypeBound;
import org.openzen.zenscript.codemodel.generic.TypeParameter;
import org.openzen.zenscript.codemodel.generic.TypeParameterBound;
import org.openzen.zenscript.codemodel.member.EnumConstantMember;
import org.openzen.zenscript.codemodel.member.FieldMember;
import org.openzen.zenscript.codemodel.scope.TypeScope;
import org.openzen.zenscript.codemodel.statement.VarStatement;
import org.openzen.zenscript.codemodel.type.ArrayTypeID;
import org.openzen.zenscript.codemodel.type.TypeID;
import org.openzen.zenscript.codemodel.type.member.LocalMemberCache;
import org.openzen.zenscript.validator.TypeContext;
import org.openzen.zenscript.validator.ValidationLogEntry;
import org.openzen.zenscript.validator.Validator;
import org.openzen.zenscript.validator.analysis.ExpressionScope;

public class ValidationUtils {
   private static final Pattern IDENTIFIER_PATTERN = Pattern.compile("^[a-zA-Z_][a-zA-Z_0-9]*$");

   private ValidationUtils() {
   }

   public static void validateValidOverride(Validator target, CodePosition position, TypeScope scope, FunctionHeader header, FunctionHeader overridden) {
      if (!header.canOverride(scope, overridden)) {
         target.logError(ValidationLogEntry.Code.INVALID_OVERRIDE, position, "Invalid override: incompatible parameters or return type");
      }
   }

   public static void validateIdentifier(Validator target, CodePosition position, String identifier) {
      if (identifier == null || !IDENTIFIER_PATTERN.matcher(identifier).matches()) {
         target.logError(ValidationLogEntry.Code.INVALID_IDENTIFIER, position, "Invalid identifier: " + identifier);
      }
   }

   public static void validateHeader(Validator target, CodePosition position, FunctionHeader header, AccessScope access) {
      validateHeader(target, position, header, access, new LocalMemberCache(target.registry, target.expansions));
   }

   public static void validateHeader(Validator target, CodePosition position, FunctionHeader header, AccessScope access, LocalMemberCache localMemberCache) {
      TypeValidator typeValidator = new TypeValidator(target, position);
      typeValidator.validate(TypeContext.RETURN_TYPE, header.getReturnType());
      Set<String> parameterNames = new HashSet<>();
      int i = 0;

      for (FunctionParameter parameter : header.parameters) {
         if (parameterNames.contains(parameter.name)) {
            target.logError(ValidationLogEntry.Code.DUPLICATE_PARAMETER_NAME, position, "Duplicate parameter name: " + parameter.name);
         }

         parameterNames.add(parameter.name);
         typeValidator.validate(TypeContext.PARAMETER_TYPE, parameter.type);
         Expression defaultValue = parameter.defaultValue;
         if (defaultValue != null) {
            defaultValue.accept(new ExpressionValidator(target, new ValidationUtils.DefaultParameterValueExpressionScope(access)));
            typeValidator.validate(TypeContext.PARAMETER_TYPE, defaultValue.type);
            if (!defaultValue.type.equals(parameter.type) && !localMemberCache.get(defaultValue.type).canCastImplicit(parameter.type)) {
               target.logError(ValidationLogEntry.Code.INVALID_TYPE, position, "default value does not match parameter type");
            }
         }

         if (parameter.variadic) {
            if (i != header.parameters.length - 1) {
               target.logError(ValidationLogEntry.Code.VARIADIC_PARAMETER_MUST_BE_LAST, position, "variadic parameter must be the last parameter");
            }

            if (!(parameter.type instanceof ArrayTypeID)) {
               target.logError(ValidationLogEntry.Code.INVALID_TYPE, position, "variadic parameter must be an array");
            }
         }

         i++;
      }
   }

   public static void validateModifiers(Validator target, int modifiers, int allowedModifiers, CodePosition position, String error) {
      if (Modifiers.isPublic(modifiers) && Modifiers.isInternal(modifiers)) {
         target.logError(ValidationLogEntry.Code.INVALID_MODIFIER, position, error + ": cannot combine public and internal");
      }

      if (Modifiers.isPublic(modifiers) && Modifiers.isPrivate(modifiers)) {
         target.logError(ValidationLogEntry.Code.INVALID_MODIFIER, position, error + ": cannot combine public and private");
      }

      if (Modifiers.isPublic(modifiers) && Modifiers.isProtected(modifiers)) {
         target.logError(ValidationLogEntry.Code.INVALID_MODIFIER, position, error + ": cannot combine public and protected");
      }

      if (Modifiers.isInternal(modifiers) && Modifiers.isPrivate(modifiers)) {
         target.logError(ValidationLogEntry.Code.INVALID_MODIFIER, position, error + ": cannot combine internal and private");
      }

      if (Modifiers.isInternal(modifiers) && Modifiers.isProtected(modifiers)) {
         target.logError(ValidationLogEntry.Code.INVALID_MODIFIER, position, error + ": cannot combine internal and protected");
      }

      if (Modifiers.isPrivate(modifiers) && Modifiers.isProtected(modifiers)) {
         target.logError(ValidationLogEntry.Code.INVALID_MODIFIER, position, error + ": cannot combine private and protected");
      }

      if (Modifiers.isConst(modifiers) && Modifiers.isConstOptional(modifiers)) {
         target.logError(ValidationLogEntry.Code.INVALID_MODIFIER, position, error + ": cannot combine const and const?");
      }

      if (Modifiers.isFinal(modifiers) && Modifiers.isAbstract(modifiers)) {
         target.logError(ValidationLogEntry.Code.INVALID_MODIFIER, position, error + ": cannot combine abstract and final");
      }

      if (Modifiers.isFinal(modifiers) && Modifiers.isVirtual(modifiers)) {
         target.logError(ValidationLogEntry.Code.INVALID_MODIFIER, position, error + ": cannot combine final and virtual");
      }

      int invalid = modifiers & ~allowedModifiers;
      if (invalid != 0) {
         if (Modifiers.isPublic(invalid)) {
            target.logError(ValidationLogEntry.Code.INVALID_MODIFIER, position, error + ": public");
         }

         if (Modifiers.isInternal(invalid)) {
            target.logError(ValidationLogEntry.Code.INVALID_MODIFIER, position, error + ": internal");
         }

         if (Modifiers.isProtected(invalid)) {
            target.logError(ValidationLogEntry.Code.INVALID_MODIFIER, position, error + ": protected");
         }

         if (Modifiers.isPrivate(invalid)) {
            target.logError(ValidationLogEntry.Code.INVALID_MODIFIER, position, error + ": private");
         }

         if (Modifiers.isFinal(invalid)) {
            target.logError(ValidationLogEntry.Code.INVALID_MODIFIER, position, error + ": final");
         }

         if (Modifiers.isConst(invalid)) {
            target.logError(ValidationLogEntry.Code.INVALID_MODIFIER, position, error + ": const");
         }

         if (Modifiers.isConstOptional(invalid)) {
            target.logError(ValidationLogEntry.Code.INVALID_MODIFIER, position, error + ": const?");
         }

         if (Modifiers.isStatic(invalid)) {
            target.logError(ValidationLogEntry.Code.INVALID_MODIFIER, position, error + ": static");
         }

         if (Modifiers.isImplicit(invalid)) {
            target.logError(ValidationLogEntry.Code.INVALID_MODIFIER, position, error + ": implicit");
         }

         if (Modifiers.isVirtual(invalid)) {
            target.logError(ValidationLogEntry.Code.INVALID_MODIFIER, position, error + ": virtual");
         }
      }
   }

   public static void validateTypeArguments(Validator target, CodePosition position, TypeParameter[] typeParameters, TypeID[] typeArguments) {
      if (typeParameters != null && typeParameters.length != 0) {
         if (typeArguments != null && typeArguments.length != 0) {
            if (typeParameters.length != typeArguments.length) {
               target.logError(
                  ValidationLogEntry.Code.INVALID_TYPE_ARGUMENT,
                  position,
                  "Invalid number of type arguments: " + typeArguments.length + " arguments given but " + typeParameters.length + " expected"
               );
            } else {
               for (int i = 0; i < typeParameters.length; i++) {
                  TypeParameter typeParameter = typeParameters[i];

                  for (TypeParameterBound var7 : typeParameter.bounds) {
                     ;
                  }
               }
            }
         } else {
            target.logError(
               ValidationLogEntry.Code.INVALID_TYPE_ARGUMENT,
               position,
               "Invalid number of type arguments: " + typeParameters.length + " arguments expected but none given"
            );
         }
      } else if (typeArguments != null && typeArguments.length != 0) {
         target.logError(
            ValidationLogEntry.Code.INVALID_TYPE_ARGUMENT,
            position,
            "Invalid number of type arguments: " + typeArguments.length + " arguments given but none expected"
         );
      }
   }

   private static class DefaultParameterValueExpressionScope implements ExpressionScope {
      private final AccessScope access;

      public DefaultParameterValueExpressionScope(AccessScope access) {
         this.access = access;
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
         return true;
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
         return null;
      }

      @Override
      public AccessScope getAccessScope() {
         return this.access;
      }
   }

   private static class TypeParameterBoundErrorVisitor implements GenericParameterBoundVisitor<String> {
      private final TypeID type;
      private final Validator target;

      public TypeParameterBoundErrorVisitor(TypeID type, Validator target) {
         this.type = type;
         this.target = target;
      }

      public String visitSuper(ParameterSuperBound bound) {
         return this.type.toString() + " is not a superclass of " + bound.type.toString();
      }

      public String visitType(ParameterTypeBound bound) {
         return this.type.toString() + " is does not extend or implement " + bound.type.toString();
      }
   }
}
