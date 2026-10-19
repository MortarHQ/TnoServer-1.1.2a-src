package org.openzen.zenscript.validator.visitors;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.codemodel.FunctionHeader;
import org.openzen.zenscript.codemodel.FunctionParameter;
import org.openzen.zenscript.codemodel.Modifiers;
import org.openzen.zenscript.codemodel.definition.EnumDefinition;
import org.openzen.zenscript.codemodel.definition.VariantDefinition;
import org.openzen.zenscript.codemodel.expression.AndAndExpression;
import org.openzen.zenscript.codemodel.expression.ArrayExpression;
import org.openzen.zenscript.codemodel.expression.CallArguments;
import org.openzen.zenscript.codemodel.expression.CallExpression;
import org.openzen.zenscript.codemodel.expression.CallStaticExpression;
import org.openzen.zenscript.codemodel.expression.CapturedClosureExpression;
import org.openzen.zenscript.codemodel.expression.CapturedDirectExpression;
import org.openzen.zenscript.codemodel.expression.CapturedLocalVariableExpression;
import org.openzen.zenscript.codemodel.expression.CapturedParameterExpression;
import org.openzen.zenscript.codemodel.expression.CapturedThisExpression;
import org.openzen.zenscript.codemodel.expression.CastExpression;
import org.openzen.zenscript.codemodel.expression.CheckNullExpression;
import org.openzen.zenscript.codemodel.expression.CoalesceExpression;
import org.openzen.zenscript.codemodel.expression.CompareExpression;
import org.openzen.zenscript.codemodel.expression.ConditionalExpression;
import org.openzen.zenscript.codemodel.expression.ConstExpression;
import org.openzen.zenscript.codemodel.expression.ConstantBoolExpression;
import org.openzen.zenscript.codemodel.expression.ConstantByteExpression;
import org.openzen.zenscript.codemodel.expression.ConstantCharExpression;
import org.openzen.zenscript.codemodel.expression.ConstantDoubleExpression;
import org.openzen.zenscript.codemodel.expression.ConstantFloatExpression;
import org.openzen.zenscript.codemodel.expression.ConstantIntExpression;
import org.openzen.zenscript.codemodel.expression.ConstantLongExpression;
import org.openzen.zenscript.codemodel.expression.ConstantSByteExpression;
import org.openzen.zenscript.codemodel.expression.ConstantShortExpression;
import org.openzen.zenscript.codemodel.expression.ConstantStringExpression;
import org.openzen.zenscript.codemodel.expression.ConstantUIntExpression;
import org.openzen.zenscript.codemodel.expression.ConstantULongExpression;
import org.openzen.zenscript.codemodel.expression.ConstantUShortExpression;
import org.openzen.zenscript.codemodel.expression.ConstantUSizeExpression;
import org.openzen.zenscript.codemodel.expression.ConstructorSuperCallExpression;
import org.openzen.zenscript.codemodel.expression.ConstructorThisCallExpression;
import org.openzen.zenscript.codemodel.expression.EnumConstantExpression;
import org.openzen.zenscript.codemodel.expression.Expression;
import org.openzen.zenscript.codemodel.expression.ExpressionVisitor;
import org.openzen.zenscript.codemodel.expression.FunctionExpression;
import org.openzen.zenscript.codemodel.expression.GetFieldExpression;
import org.openzen.zenscript.codemodel.expression.GetFunctionParameterExpression;
import org.openzen.zenscript.codemodel.expression.GetLocalVariableExpression;
import org.openzen.zenscript.codemodel.expression.GetMatchingVariantField;
import org.openzen.zenscript.codemodel.expression.GetStaticFieldExpression;
import org.openzen.zenscript.codemodel.expression.GetterExpression;
import org.openzen.zenscript.codemodel.expression.GlobalCallExpression;
import org.openzen.zenscript.codemodel.expression.GlobalExpression;
import org.openzen.zenscript.codemodel.expression.InterfaceCastExpression;
import org.openzen.zenscript.codemodel.expression.InvalidAssignExpression;
import org.openzen.zenscript.codemodel.expression.InvalidExpression;
import org.openzen.zenscript.codemodel.expression.IsExpression;
import org.openzen.zenscript.codemodel.expression.MakeConstExpression;
import org.openzen.zenscript.codemodel.expression.MapExpression;
import org.openzen.zenscript.codemodel.expression.MatchExpression;
import org.openzen.zenscript.codemodel.expression.NewExpression;
import org.openzen.zenscript.codemodel.expression.NullExpression;
import org.openzen.zenscript.codemodel.expression.OrOrExpression;
import org.openzen.zenscript.codemodel.expression.PanicExpression;
import org.openzen.zenscript.codemodel.expression.PostCallExpression;
import org.openzen.zenscript.codemodel.expression.RangeExpression;
import org.openzen.zenscript.codemodel.expression.SameObjectExpression;
import org.openzen.zenscript.codemodel.expression.SetFieldExpression;
import org.openzen.zenscript.codemodel.expression.SetFunctionParameterExpression;
import org.openzen.zenscript.codemodel.expression.SetLocalVariableExpression;
import org.openzen.zenscript.codemodel.expression.SetStaticFieldExpression;
import org.openzen.zenscript.codemodel.expression.SetterExpression;
import org.openzen.zenscript.codemodel.expression.StaticGetterExpression;
import org.openzen.zenscript.codemodel.expression.StaticSetterExpression;
import org.openzen.zenscript.codemodel.expression.SubtypeCastExpression;
import org.openzen.zenscript.codemodel.expression.SupertypeCastExpression;
import org.openzen.zenscript.codemodel.expression.ThisExpression;
import org.openzen.zenscript.codemodel.expression.ThrowExpression;
import org.openzen.zenscript.codemodel.expression.TryConvertExpression;
import org.openzen.zenscript.codemodel.expression.TryRethrowAsExceptionExpression;
import org.openzen.zenscript.codemodel.expression.TryRethrowAsResultExpression;
import org.openzen.zenscript.codemodel.expression.VariantValueExpression;
import org.openzen.zenscript.codemodel.expression.WrapOptionalExpression;
import org.openzen.zenscript.codemodel.expression.switchvalue.EnumConstantSwitchValue;
import org.openzen.zenscript.codemodel.expression.switchvalue.VariantOptionSwitchValue;
import org.openzen.zenscript.codemodel.member.EnumConstantMember;
import org.openzen.zenscript.codemodel.member.ref.DefinitionMemberRef;
import org.openzen.zenscript.codemodel.member.ref.FieldMemberRef;
import org.openzen.zenscript.codemodel.type.ArrayTypeID;
import org.openzen.zenscript.codemodel.type.AssocTypeID;
import org.openzen.zenscript.codemodel.type.BasicTypeID;
import org.openzen.zenscript.codemodel.type.DefinitionTypeID;
import org.openzen.zenscript.codemodel.type.RangeTypeID;
import org.openzen.zenscript.codemodel.type.TypeID;
import org.openzen.zenscript.validator.TypeContext;
import org.openzen.zenscript.validator.ValidationLogEntry;
import org.openzen.zenscript.validator.Validator;
import org.openzen.zenscript.validator.analysis.ExpressionScope;

public class ExpressionValidator implements ExpressionVisitor<Void> {
   private final Validator validator;
   private final ExpressionScope scope;

   public ExpressionValidator(Validator validator, ExpressionScope scope) {
      this.validator = validator;
      this.scope = scope;
   }

   public Void visitAndAnd(AndAndExpression expression) {
      expression.left.accept(this);
      expression.right.accept(this);
      if (expression.left.type != BasicTypeID.BOOL) {
         this.validator.logError(ValidationLogEntry.Code.INVALID_OPERAND_TYPE, expression.position, "left hand side operand of && must be a bool");
      }

      if (expression.right.type != BasicTypeID.BOOL) {
         this.validator.logError(ValidationLogEntry.Code.INVALID_OPERAND_TYPE, expression.position, "right hand side operand of && must be a bool");
      }

      return null;
   }

   public Void visitArray(ArrayExpression expression) {
      for (Expression element : expression.expressions) {
         if (!element.type.equals(expression.arrayType.elementType)) {
            this.validator
               .logError(
                  ValidationLogEntry.Code.INVALID_OPERAND_TYPE,
                  expression.position,
                  "array element expression type " + element.type + " doesn't match array type " + expression.arrayType.elementType
               );
         }

         element.accept(this);
      }

      return null;
   }

   public Void visitCompare(CompareExpression expression) {
      if (!expression.right.type.equals(expression.operator.getHeader().parameters[0].type)) {
         this.validator.logError(ValidationLogEntry.Code.INVALID_OPERAND_TYPE, expression.position, "comparison has invalid right type!");
      }

      this.checkMemberAccess(expression.position, expression.operator);
      this.checkNotStatic(expression.position, expression.operator);
      expression.left.accept(this);
      expression.right.accept(this);
      return null;
   }

   public Void visitCall(CallExpression expression) {
      expression.target.accept(this);
      this.checkMemberAccess(expression.position, expression.member);
      this.checkCallArguments(expression.position, expression.member.getHeader(), expression.instancedHeader, expression.arguments);
      this.checkNotStatic(expression.position, expression.member);
      return null;
   }

   public Void visitCallStatic(CallStaticExpression expression) {
      this.checkMemberAccess(expression.position, expression.member);
      this.checkCallArguments(expression.position, expression.member.getHeader(), expression.instancedHeader, expression.arguments);
      this.checkStatic(expression.position, expression.member);
      return null;
   }

   public Void visitConst(ConstExpression expression) {
      this.checkMemberAccess(expression.position, expression.constant);
      return null;
   }

   public Void visitCapturedClosure(CapturedClosureExpression expression) {
      return null;
   }

   public Void visitCapturedDirect(CapturedDirectExpression expression) {
      return null;
   }

   public Void visitCapturedLocalVariable(CapturedLocalVariableExpression expression) {
      return null;
   }

   public Void visitCapturedParameter(CapturedParameterExpression expression) {
      return null;
   }

   public Void visitCapturedThis(CapturedThisExpression expression) {
      return null;
   }

   public Void visitCast(CastExpression expression) {
      this.checkMemberAccess(expression.position, expression.member);
      return expression.target.accept(this);
   }

   public Void visitCheckNull(CheckNullExpression expression) {
      expression.value.accept(this);
      if (!expression.value.type.isOptional()) {
         this.validator.logError(ValidationLogEntry.Code.INVALID_OPERAND_TYPE, expression.position, "target of a null check is not optional");
      }

      return null;
   }

   public Void visitCoalesce(CoalesceExpression expression) {
      expression.left.accept(this);
      expression.right.accept(this);
      if (!expression.left.type.isOptional()) {
         this.validator.logError(ValidationLogEntry.Code.INVALID_OPERAND_TYPE, expression.position, "target of a null coalesce is not optional");
      }

      return null;
   }

   public Void visitConditional(ConditionalExpression expression) {
      expression.condition.accept(this);
      expression.ifThen.accept(this);
      expression.ifElse.accept(this);
      if (expression.condition.type != BasicTypeID.BOOL) {
         this.validator.logError(ValidationLogEntry.Code.INVALID_OPERAND_TYPE, expression.position, "conditional expression condition must be a bool");
      }

      return null;
   }

   public Void visitConstantBool(ConstantBoolExpression expression) {
      return null;
   }

   public Void visitConstantByte(ConstantByteExpression expression) {
      return null;
   }

   public Void visitConstantChar(ConstantCharExpression expression) {
      return null;
   }

   public Void visitConstantDouble(ConstantDoubleExpression expression) {
      return null;
   }

   public Void visitConstantFloat(ConstantFloatExpression expression) {
      return null;
   }

   public Void visitConstantInt(ConstantIntExpression expression) {
      return null;
   }

   public Void visitConstantLong(ConstantLongExpression expression) {
      return null;
   }

   public Void visitConstantSByte(ConstantSByteExpression expression) {
      return null;
   }

   public Void visitConstantShort(ConstantShortExpression expression) {
      return null;
   }

   public Void visitConstantString(ConstantStringExpression expression) {
      return null;
   }

   public Void visitConstantUInt(ConstantUIntExpression expression) {
      return null;
   }

   public Void visitConstantULong(ConstantULongExpression expression) {
      return null;
   }

   public Void visitConstantUShort(ConstantUShortExpression expression) {
      return null;
   }

   public Void visitConstantUSize(ConstantUSizeExpression expression) {
      return null;
   }

   public Void visitConstructorThisCall(ConstructorThisCallExpression expression) {
      if (!this.scope.isConstructor()) {
         this.validator
            .logError(ValidationLogEntry.Code.CONSTRUCTOR_FORWARD_OUTSIDE_CONSTRUCTOR, expression.position, "Can only forward constructors inside constructors");
      }

      if (!this.scope.isFirstStatement()) {
         this.validator
            .logError(ValidationLogEntry.Code.CONSTRUCTOR_FORWARD_NOT_FIRST_STATEMENT, expression.position, "Constructor forwarder must be first expression");
      }

      this.scope.markConstructorForwarded();
      this.checkCallArguments(expression.position, expression.constructor.getHeader(), expression.constructor.getHeader(), expression.arguments);
      return null;
   }

   public Void visitConstructorSuperCall(ConstructorSuperCallExpression expression) {
      this.checkMemberAccess(expression.position, expression.constructor);
      if (!this.scope.isConstructor()) {
         this.validator
            .logError(ValidationLogEntry.Code.CONSTRUCTOR_FORWARD_OUTSIDE_CONSTRUCTOR, expression.position, "Can only forward constructors inside constructors");
      }

      if (!this.scope.isFirstStatement()) {
         this.validator
            .logError(ValidationLogEntry.Code.CONSTRUCTOR_FORWARD_NOT_FIRST_STATEMENT, expression.position, "Constructor forwarder must be first expression");
      }

      this.scope.markConstructorForwarded();
      this.checkCallArguments(expression.position, expression.constructor.getHeader(), expression.constructor.getHeader(), expression.arguments);
      return null;
   }

   public Void visitEnumConstant(EnumConstantExpression expression) {
      if (!this.scope.isEnumConstantInitialized(expression.value)) {
         this.validator
            .logError(
               ValidationLogEntry.Code.ENUM_CONSTANT_NOT_YET_INITIALIZED,
               expression.position,
               "Using an enum constant that is not yet initialized: " + expression.value.name
            );
      }

      return null;
   }

   public Void visitFunction(FunctionExpression expression) {
      return null;
   }

   public Void visitGetField(GetFieldExpression expression) {
      this.checkFieldAccess(expression.position, expression.field);
      this.checkNotStatic(expression.position, expression.field);
      expression.target.accept(this);
      if (expression.target instanceof ThisExpression && !this.scope.isFieldInitialized(expression.field.member)) {
         this.validator.logError(ValidationLogEntry.Code.FIELD_NOT_YET_INITIALIZED, expression.position, "Using a field that was not yet initialized");
      }

      return null;
   }

   public Void visitGetFunctionParameter(GetFunctionParameterExpression expression) {
      return null;
   }

   public Void visitGetLocalVariable(GetLocalVariableExpression expression) {
      if (!this.scope.isLocalVariableInitialized(expression.variable)) {
         this.validator.logError(ValidationLogEntry.Code.LOCAL_VARIABLE_NOT_YET_INITIALIZED, expression.position, "Local variable not yet initialized");
      }

      return null;
   }

   public Void visitGetMatchingVariantField(GetMatchingVariantField expression) {
      if (expression.index >= expression.value.parameters.length) {
         this.validator.logError(ValidationLogEntry.Code.MATCHING_VARIANT_FIELD_INVALID, expression.position, "Invalid matching variant field");
      }

      return null;
   }

   public Void visitGetStaticField(GetStaticFieldExpression expression) {
      this.checkFieldAccess(expression.position, expression.field);
      this.checkStatic(expression.position, expression.field);
      return null;
   }

   public Void visitGetter(GetterExpression expression) {
      this.checkMemberAccess(expression.position, expression.getter);
      this.checkNotStatic(expression.position, expression.getter);
      return expression.target.accept(this);
   }

   public Void visitGlobal(GlobalExpression expression) {
      return expression.resolution.accept(this);
   }

   public Void visitGlobalCall(GlobalCallExpression expression) {
      return expression.resolution.accept(this);
   }

   public Void visitInterfaceCast(InterfaceCastExpression expression) {
      expression.value.accept(this);
      new TypeValidator(this.validator, expression.position).validate(TypeContext.CAST_TARGET_TYPE, expression.type);
      return null;
   }

   public Void visitInvalid(InvalidExpression expression) {
      this.validator.logError(ValidationLogEntry.Code.INVALID_EXPRESSION, expression.position, expression.message);
      return null;
   }

   public Void visitInvalidAssign(InvalidAssignExpression expression) {
      expression.target.accept(this);
      expression.source.accept(this);
      return null;
   }

   public Void visitIs(IsExpression expression) {
      expression.value.accept(this);
      new TypeValidator(this.validator, expression.position).validate(TypeContext.TYPE_CHECK_TYPE, expression.isType);
      return null;
   }

   public Void visitMakeConst(MakeConstExpression expression) {
      return expression.value.accept(this);
   }

   public Void visitMap(MapExpression expression) {
      AssocTypeID type = (AssocTypeID)expression.type;

      for (int i = 0; i < expression.keys.length; i++) {
         Expression key = expression.keys[i];
         Expression value = expression.values[i];
         key.accept(this);
         value.accept(this);
         if (!key.type.equals(type.keyType)) {
            this.validator
               .logError(
                  ValidationLogEntry.Code.INVALID_OPERAND_TYPE,
                  key.position,
                  "Key type " + key.type + " must match the associative array key type " + type.keyType
               );
         }

         if (!value.type.equals(type.valueType)) {
            this.validator
               .logError(
                  ValidationLogEntry.Code.INVALID_OPERAND_TYPE,
                  key.position,
                  "Value type " + value.type + " must match the associative array value type " + type.valueType
               );
         }
      }

      return null;
   }

   public Void visitMatch(MatchExpression expression) {
      expression.value.accept(this);

      for (MatchExpression.Case case_ : expression.cases) {
         case_.value.accept(this);
      }

      boolean hasDefault = false;
      if (expression.value.type.isVariant()) {
         Set<VariantDefinition.Option> options = new HashSet<>();

         for (MatchExpression.Case case_ : expression.cases) {
            if (case_.key == null) {
               if (hasDefault) {
                  this.validator.logError(ValidationLogEntry.Code.DUPLICATE_DEFAULT_CASE, expression.position, "Duplicate default in match");
               }

               hasDefault = true;
            } else if (case_.key instanceof VariantOptionSwitchValue) {
               VariantDefinition.Option option = ((VariantOptionSwitchValue)case_.key).option.getOption();
               if (options.contains(option)) {
                  this.validator.logError(ValidationLogEntry.Code.DUPLICATE_CASE, expression.position, "Duplicate case in match: " + option.name);
               }

               options.add(option);
            } else {
               this.validator.logError(ValidationLogEntry.Code.INVALID_CASE, expression.position, "Invalid case: must be default or option value");
            }
         }

         if (!hasDefault) {
            VariantDefinition variant = (VariantDefinition)((DefinitionTypeID)expression.value.type).definition;

            for (VariantDefinition.Option option : variant.options) {
               if (!options.contains(option)) {
                  this.validator.logError(ValidationLogEntry.Code.INCOMPLETE_MATCH, expression.position, "Incomplete match: missing option for " + option.name);
               }
            }
         }
      } else if (expression.type.isEnum()) {
         Set<EnumConstantMember> options = new HashSet<>();

         for (MatchExpression.Case case_ : expression.cases) {
            if (case_.key == null) {
               if (hasDefault) {
                  this.validator.logError(ValidationLogEntry.Code.DUPLICATE_DEFAULT_CASE, expression.position, "Duplicate default in match");
               }

               hasDefault = true;
            } else if (case_.key instanceof EnumConstantSwitchValue) {
               EnumConstantMember option = ((EnumConstantSwitchValue)case_.key).constant;
               if (options.contains(option)) {
                  this.validator.logError(ValidationLogEntry.Code.DUPLICATE_CASE, expression.position, "Duplicate case in match: " + option.name);
               }

               options.add(option);
            } else {
               this.validator.logError(ValidationLogEntry.Code.INVALID_CASE, expression.position, "Invalid case: must be default or enum value");
            }
         }

         if (!hasDefault) {
            EnumDefinition enum_ = (EnumDefinition)((DefinitionTypeID)expression.value.type).definition;

            for (EnumConstantMember option : enum_.enumConstants) {
               if (!options.contains(option)) {
                  this.validator.logError(ValidationLogEntry.Code.INCOMPLETE_MATCH, expression.position, "Incomplete match: missing option for " + option.name);
               }
            }
         }
      } else {
         for (MatchExpression.Case case_ : expression.cases) {
            if (case_.key == null) {
               if (hasDefault) {
                  this.validator.logError(ValidationLogEntry.Code.DUPLICATE_DEFAULT_CASE, expression.position, "Duplicate default in match");
               }

               hasDefault = true;
            }
         }

         if (!hasDefault) {
            this.validator.logError(ValidationLogEntry.Code.INCOMPLETE_MATCH, expression.position, "Incomplete match: must have a default option");
         }
      }

      return null;
   }

   public Void visitNew(NewExpression expression) {
      new TypeValidator(this.validator, expression.position).validate(TypeContext.CONSTRUCTOR_TYPE, expression.constructor.getOwnerType());
      this.checkMemberAccess(expression.position, expression.constructor);
      this.checkCallArguments(expression.position, expression.constructor.getHeader(), expression.instancedHeader, expression.arguments);
      return null;
   }

   public Void visitNull(NullExpression expression) {
      return null;
   }

   public Void visitOrOr(OrOrExpression expression) {
      expression.left.accept(this);
      expression.right.accept(this);
      if (expression.left.type != BasicTypeID.BOOL) {
         this.validator.logError(ValidationLogEntry.Code.INVALID_OPERAND_TYPE, expression.position, "Left hand side of || expression is not a bool");
      }

      if (expression.right.type != BasicTypeID.BOOL) {
         this.validator.logError(ValidationLogEntry.Code.INVALID_OPERAND_TYPE, expression.position, "Right hand side of || expression is not a bool");
      }

      return null;
   }

   public Void visitPanic(PanicExpression expression) {
      expression.value.accept(this);
      if (expression.value.type != BasicTypeID.STRING) {
         this.validator.logError(ValidationLogEntry.Code.PANIC_ARGUMENT_NO_STRING, expression.position, "Argument to a panic must be a string");
      }

      return null;
   }

   public Void visitPlatformSpecific(Expression expression) {
      return null;
   }

   public Void visitPostCall(PostCallExpression expression) {
      this.checkMemberAccess(expression.position, expression.member);
      this.checkNotStatic(expression.position, expression.member);
      expression.target.accept(this);
      return null;
   }

   public Void visitRange(RangeExpression expression) {
      expression.from.accept(this);
      expression.to.accept(this);
      RangeTypeID rangeType = (RangeTypeID)expression.type;
      if (!expression.from.type.equals(rangeType.baseType)) {
         this.validator.logError(ValidationLogEntry.Code.INVALID_OPERAND_TYPE, expression.position, "From operand is not a " + rangeType.baseType.toString());
      }

      if (!expression.to.type.equals(rangeType.baseType)) {
         this.validator.logError(ValidationLogEntry.Code.INVALID_OPERAND_TYPE, expression.position, "To operand is not a " + rangeType.baseType.toString());
      }

      return null;
   }

   public Void visitSameObject(SameObjectExpression expression) {
      expression.left.accept(this);
      expression.right.accept(this);
      return null;
   }

   public Void visitSetField(SetFieldExpression expression) {
      this.checkFieldAccess(expression.position, expression.field);
      this.checkNotStatic(expression.position, expression.field);
      expression.target.accept(this);
      expression.value.accept(this);
      if (!expression.value.type.equals(expression.field.getType())) {
         this.validator
            .logError(
               ValidationLogEntry.Code.INVALID_SOURCE_TYPE,
               expression.position,
               "Trying to set a field of type " + expression.field.getType() + " to a value of type " + expression.value.type
            );
      }

      if (expression.field.isFinal() && (!(expression.target instanceof ThisExpression) || !this.scope.isConstructor())) {
         this.validator.logError(ValidationLogEntry.Code.SETTING_FINAL_FIELD, expression.position, "Cannot set a final field");
      }

      return null;
   }

   public Void visitSetFunctionParameter(SetFunctionParameterExpression expression) {
      expression.value.accept(this);
      if (!expression.value.type.equals(expression.parameter.type)) {
         this.validator
            .logError(
               ValidationLogEntry.Code.INVALID_SOURCE_TYPE,
               expression.position,
               "Trying to set a parameter of type " + expression.parameter.type + " to a value of type " + expression.value.type
            );
      }

      return null;
   }

   public Void visitSetLocalVariable(SetLocalVariableExpression expression) {
      expression.value.accept(this);
      if (!expression.value.type.equals(expression.variable.type)) {
         this.validator
            .logError(
               ValidationLogEntry.Code.INVALID_SOURCE_TYPE,
               expression.position,
               "Trying to set a variable of type " + expression.variable.type + " to a value of type " + expression.value.type
            );
      }

      if (expression.variable.isFinal) {
         this.validator
            .logError(ValidationLogEntry.Code.SETTING_FINAL_VARIABLE, expression.position, "Trying to set final variable " + expression.variable.name);
      }

      return null;
   }

   public Void visitSetStaticField(SetStaticFieldExpression expression) {
      this.checkFieldAccess(expression.position, expression.field);
      this.checkStatic(expression.position, expression.field);
      expression.value.accept(this);
      if (!expression.value.type.equals(expression.field.getType())) {
         this.validator
            .logError(
               ValidationLogEntry.Code.INVALID_SOURCE_TYPE,
               expression.position,
               "Trying to set a static field of type " + expression.field.getType() + " to a value of type " + expression.value.type
            );
      }

      if (expression.field.isFinal() && (!this.scope.isStaticInitializer() || expression.field.member.definition != this.scope.getDefinition())) {
         this.validator.logError(ValidationLogEntry.Code.SETTING_FINAL_FIELD, expression.position, "Trying to set final field " + expression.field.member.name);
      }

      return null;
   }

   public Void visitSetter(SetterExpression expression) {
      this.checkMemberAccess(expression.position, expression.setter);
      this.checkNotStatic(expression.position, expression.setter);
      expression.target.accept(this);
      expression.value.accept(this);
      if (!expression.value.type.equals(expression.setter.getType())) {
         this.validator
            .logError(
               ValidationLogEntry.Code.INVALID_SOURCE_TYPE,
               expression.position,
               "Trying to set a property of type " + expression.setter.getType() + " to a value of type " + expression.value.type
            );
      }

      return null;
   }

   public Void visitStaticGetter(StaticGetterExpression expression) {
      this.checkMemberAccess(expression.position, expression.getter);
      this.checkStatic(expression.position, expression.getter);
      return null;
   }

   public Void visitStaticSetter(StaticSetterExpression expression) {
      this.checkMemberAccess(expression.position, expression.setter);
      this.checkStatic(expression.position, expression.setter);
      expression.value.accept(this);
      if (!expression.value.type.equals(expression.setter.getType())) {
         this.validator
            .logError(
               ValidationLogEntry.Code.INVALID_SOURCE_TYPE,
               expression.position,
               "Trying to set a static property of type " + expression.setter.getType() + " to a value of type " + expression.value.type
            );
      }

      return null;
   }

   public Void visitSupertypeCast(SupertypeCastExpression expression) {
      expression.value.accept(this);
      return null;
   }

   public Void visitSubtypeCast(SubtypeCastExpression expression) {
      expression.value.accept(this);
      return null;
   }

   public Void visitThis(ThisExpression expression) {
      if (!this.scope.hasThis()) {
         this.validator.logError(ValidationLogEntry.Code.THIS_IN_STATIC_SCOPE, expression.position, "Cannot use this in a static scope");
      }

      return null;
   }

   public Void visitThrow(ThrowExpression expression) {
      return expression.value.accept(this);
   }

   public Void visitTryConvert(TryConvertExpression expression) {
      expression.value.accept(this);
      return null;
   }

   public Void visitTryRethrowAsException(TryRethrowAsExceptionExpression expression) {
      expression.value.accept(this);
      return null;
   }

   public Void visitTryRethrowAsResult(TryRethrowAsResultExpression expression) {
      expression.value.accept(this);
      return null;
   }

   public Void visitVariantValue(VariantValueExpression expression) {
      if (expression.getNumberOfArguments() != expression.option.types.length) {
         this.validator
            .logError(
               ValidationLogEntry.Code.INVALID_CALL_ARGUMENT,
               expression.position,
               "Invalid number of variant arguments for variant element " + expression.option.getName()
            );
      }

      for (int i = 0; i < expression.getNumberOfArguments(); i++) {
         if (!expression.arguments[i].type.equals(expression.option.types[i])) {
            this.validator
               .logError(
                  ValidationLogEntry.Code.INVALID_CALL_ARGUMENT,
                  expression.position,
                  "Invalid variant argument for argument " + i + ": " + expression.arguments[i].type + " given but " + expression.option.types[i] + " expected"
               );
         }
      }

      return null;
   }

   public Void visitWrapOptional(WrapOptionalExpression expression) {
      expression.value.accept(this);
      if (expression.value.type.isOptional()) {
         this.validator.logError(ValidationLogEntry.Code.INVALID_OPERAND_TYPE, expression.position, "expression value is already optional");
      }

      return null;
   }

   private void checkMemberAccess(CodePosition position, DefinitionMemberRef member) {
      if (!this.scope.getAccessScope().hasAccessTo(member.getTarget().getAccessScope(), member.getTarget().getEffectiveModifiers())) {
         this.validator.logError(ValidationLogEntry.Code.NO_ACCESS, position, "no access to " + member.describe());
      }
   }

   private void checkFieldAccess(CodePosition position, FieldMemberRef field) {
      if (!this.scope.getAccessScope().hasAccessTo(field.getTarget().getAccessScope(), field.getTarget().getEffectiveModifiers())) {
         this.validator.logError(ValidationLogEntry.Code.NO_ACCESS, position, "no field access to " + field.describe());
      }
   }

   private void checkStatic(CodePosition position, DefinitionMemberRef member) {
      if (!Modifiers.isStatic(member.getTarget().getSpecifiedModifiers())) {
         this.validator.logError(ValidationLogEntry.Code.MUST_BE_STATIC, position, "Member is not static");
      }
   }

   private void checkNotStatic(CodePosition position, DefinitionMemberRef member) {
      if (Modifiers.isStatic(member.getTarget().getSpecifiedModifiers())) {
         this.validator.logError(ValidationLogEntry.Code.MUST_NOT_BE_STATIC, position, "Member must not be static");
      }
   }

   private void checkCallArguments(CodePosition position, FunctionHeader originalHeader, FunctionHeader instancedHeader, CallArguments arguments) {
      ValidationUtils.validateTypeArguments(this.validator, position, originalHeader.typeParameters, arguments.typeArguments);
      boolean isVariadic = instancedHeader.isVariadicCall(arguments);

      for (int i = 0; i < arguments.arguments.length; i++) {
         Expression argument = arguments.arguments[i];
         argument.accept(this);
         if (i >= instancedHeader.parameters.length) {
            FunctionParameter variadic = instancedHeader.getVariadicParameter();
            if (variadic == null) {
               this.validator.logError(ValidationLogEntry.Code.INVALID_CALL_ARGUMENT, position, "too many call arguments");
               break;
            }

            if (variadic.type instanceof ArrayTypeID) {
               TypeID elementType = ((ArrayTypeID)variadic.type).elementType;
               if (!elementType.equals(argument.type)) {
                  this.validator.logError(ValidationLogEntry.Code.INVALID_CALL_ARGUMENT, position, "invalid type for variadic call argument");
                  break;
               }
            }
         }

         FunctionParameter parameter = instancedHeader.getParameter(isVariadic, i);
         if (!parameter.type.equals(argument.type)
            && (parameter.defaultValue == null || !Objects.equals(parameter.defaultValue.type, argument.type))
            && !parameter.type.equals(argument.type)) {
            this.validator
               .logError(
                  ValidationLogEntry.Code.INVALID_CALL_ARGUMENT,
                  position,
                  "invalid value for parameter "
                     + parameter.name
                     + ": "
                     + parameter.type.toString()
                     + " expected but "
                     + arguments.arguments[i].type
                     + " given"
               );
         }
      }

      for (int i = arguments.arguments.length; i < instancedHeader.parameters.length; i++) {
         FunctionParameter parameter = instancedHeader.parameters[i];
         if (parameter.defaultValue == null && !parameter.variadic) {
            this.validator.logError(ValidationLogEntry.Code.INVALID_CALL_ARGUMENT, position, "missing call argument for " + parameter.name);
         }
      }
   }
}
