package org.openzen.zenscript.parser.expression;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zencode.shared.CompileExceptionCode;
import org.openzen.zenscript.codemodel.OperatorType;
import org.openzen.zenscript.codemodel.expression.CallArguments;
import org.openzen.zenscript.codemodel.expression.Expression;
import org.openzen.zenscript.codemodel.expression.InvalidExpression;
import org.openzen.zenscript.codemodel.expression.MapExpression;
import org.openzen.zenscript.codemodel.expression.NewExpression;
import org.openzen.zenscript.codemodel.member.ref.FunctionalMemberRef;
import org.openzen.zenscript.codemodel.partial.IPartialExpression;
import org.openzen.zenscript.codemodel.scope.ExpressionScope;
import org.openzen.zenscript.codemodel.type.AssocTypeID;
import org.openzen.zenscript.codemodel.type.GenericMapTypeID;
import org.openzen.zenscript.codemodel.type.TypeID;

public class ParsedExpressionMap extends ParsedExpression {
   public static final List<BiFunction<ParsedExpressionMap, ExpressionScope, ? extends IPartialExpression>> compileOverrides = new ArrayList<>(0);
   public final List<ParsedExpression> keys;
   public final List<ParsedExpression> values;

   public ParsedExpressionMap(CodePosition position, List<ParsedExpression> keys, List<ParsedExpression> values) {
      super(position);
      this.keys = keys;
      this.values = values;
   }

   @Override
   public IPartialExpression compile(ExpressionScope scope) throws CompileException {
      for (BiFunction<ParsedExpressionMap, ExpressionScope, ? extends IPartialExpression> compileOverride : compileOverrides) {
         IPartialExpression apply = compileOverride.apply(this, scope);
         if (apply != null) {
            return apply;
         }
      }

      TypeID usedHint = null;
      List<TypeID> keyHints = new ArrayList<>();
      List<TypeID> valueHints = new ArrayList<>();
      boolean hasAssocHint = false;

      for (TypeID hint : scope.hints) {
         if (hint instanceof AssocTypeID) {
            usedHint = hint;
            AssocTypeID assocHint = (AssocTypeID)hint;
            if (!keyHints.contains(assocHint.keyType)) {
               keyHints.add(assocHint.keyType);
            }

            if (!valueHints.contains(assocHint.valueType)) {
               valueHints.add(assocHint.valueType);
            }

            hasAssocHint = true;
         } else if (hint instanceof GenericMapTypeID) {
            try {
               FunctionalMemberRef constructor = scope.getTypeMembers(hint)
                  .getOrCreateGroup(OperatorType.CONSTRUCTOR)
                  .selectMethod(this.position, scope, CallArguments.EMPTY, true, true);
               return new NewExpression(this.position, hint, constructor, CallArguments.EMPTY);
            } catch (CompileException ex) {
               return new InvalidExpression(ex.position, hint, ex.code, ex.getMessage());
            }
         }
      }

      if (this.keys.isEmpty() && keyHints.size() == 1) {
         FunctionalMemberRef constructor = scope.getTypeMembers(usedHint)
            .getOrCreateGroup(OperatorType.CONSTRUCTOR)
            .selectMethod(this.position, scope, CallArguments.EMPTY, true, true);
         return new NewExpression(this.position, usedHint, constructor, CallArguments.EMPTY);
      }

      Expression[] cKeys = new Expression[this.keys.size()];
      Expression[] cValues = new Expression[this.values.size()];

      for (int i = 0; i < this.keys.size(); i++) {
         if (this.keys.get(i) == null) {
            throw new CompileException(this.position, CompileExceptionCode.MISSING_MAP_KEY, "Missing key");
         }

         cKeys[i] = this.keys.get(i).compileKey(scope.withHints(keyHints));
         cValues[i] = this.values.get(i).compile(scope.withHints(valueHints)).eval();
      }

      TypeID keyType = null;

      for (Expression key : cKeys) {
         if (key.type != keyType) {
            if (keyType == null) {
               keyType = key.type;
            } else {
               keyType = scope.getTypeMembers(keyType).union(key.type);
            }
         }
      }

      if (keyType == null) {
         throw new CompileException(this.position, CompileExceptionCode.UNTYPED_EMPTY_MAP, "Empty map without known type");
      }

      for (int i = 0; i < cKeys.length; i++) {
         cKeys[i] = cKeys[i].castImplicit(this.position, scope, keyType);
      }

      TypeID valueType = null;

      for (Expression value : cValues) {
         if (value.type != valueType) {
            if (valueType == null) {
               valueType = value.type;
            } else {
               valueType = scope.getTypeMembers(valueType).union(value.type);
            }
         }
      }

      if (valueType == null) {
         throw new CompileException(this.position, CompileExceptionCode.UNTYPED_EMPTY_MAP, "Empty map without known type");
      }

      for (int i = 0; i < cValues.length; i++) {
         cValues[i] = cValues[i].castImplicit(this.position, scope, valueType);
      }

      AssocTypeID asType = scope.getTypeRegistry().getAssociative(keyType, valueType);
      return new MapExpression(this.position, cKeys, cValues, asType);
   }

   @Override
   public boolean hasStrongType() {
      return false;
   }
}
