package org.openzen.zenscript.codemodel.expression;

import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.codemodel.scope.TypeScope;
import org.openzen.zenscript.codemodel.type.TypeID;

public class MapExpression extends Expression {
   public final Expression[] keys;
   public final Expression[] values;

   public MapExpression(CodePosition position, Expression[] keys, Expression[] values, TypeID type) {
      super(position, type, binaryThrow(position, multiThrow(position, keys), multiThrow(position, values)));
      this.keys = keys;
      this.values = values;
   }

   @Override
   public <T> T accept(ExpressionVisitor<T> visitor) {
      return visitor.visitMap(this);
   }

   @Override
   public <C, R> R accept(C context, ExpressionVisitorWithContext<C, R> visitor) {
      return visitor.visitMap(context, this);
   }

   @Override
   public Expression transform(ExpressionTransformer transformer) {
      Expression[] tKeys = Expression.transform(this.keys, transformer);
      Expression[] tValues = Expression.transform(this.values, transformer);
      return tKeys == this.keys && tValues == this.values ? this : new MapExpression(this.position, tKeys, tValues, this.type);
   }

   @Override
   public Expression normalize(TypeScope scope) {
      Expression[] normalizedKeys = new Expression[this.keys.length];

      for (int i = 0; i < normalizedKeys.length; i++) {
         normalizedKeys[i] = this.keys[i].normalize(scope);
      }

      Expression[] normalizedValues = new Expression[this.values.length];

      for (int i = 0; i < normalizedValues.length; i++) {
         normalizedValues[i] = this.values[i].normalize(scope);
      }

      return new MapExpression(this.position, normalizedKeys, normalizedValues, this.type.getNormalized());
   }
}
