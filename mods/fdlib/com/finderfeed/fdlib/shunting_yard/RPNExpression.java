package com.finderfeed.fdlib.shunting_yard;

import com.finderfeed.fdlib.shunting_yard.sy_base.ExpressionContext;
import com.finderfeed.fdlib.shunting_yard.sy_base.SYFunction;
import com.finderfeed.fdlib.shunting_yard.sy_base.SYNode;
import com.finderfeed.fdlib.shunting_yard.sy_base.SYStaticValue;
import com.finderfeed.fdlib.shunting_yard.sy_base.SYVariable;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class RPNExpression {
   private List<SYNode> nodes;

   public RPNExpression(List<SYNode> nodes) {
      this.nodes = nodes;
   }

   public float compute(ExpressionContext ctx) {
      Stack<SYNode> computation = new Stack<>();

      for (int i = 0; i < this.nodes.size(); i++) {
         SYNode node = this.nodes.get(i);
         if (node instanceof SYVariable variable) {
            computation.push(variable);
         } else {
            if (!(node instanceof SYFunction function)) {
               throw new RuntimeException("Wtf is this doing here.");
            }

            int args = function.getArgumentCount();
            List<Float> values = new ArrayList<>();

            for (int g = 0; g < args; g++) {
               SYVariable variable = (SYVariable)computation.pop();
               values.add(0, variable.getValue(ctx));
            }

            float value = function.compute(values);
            computation.add(new SYStaticValue(value));
         }
      }

      if (computation.size() == 1) {
         return ((SYStaticValue)computation.pop()).getValue(ctx);
      } else {
         throw new RuntimeException("Expression is incorrect");
      }
   }
}
