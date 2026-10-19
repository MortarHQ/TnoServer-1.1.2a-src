package org.openzen.zenscript.codemodel.context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.codemodel.FunctionHeader;
import org.openzen.zenscript.codemodel.FunctionParameter;
import org.openzen.zenscript.codemodel.expression.LambdaClosure;
import org.openzen.zenscript.codemodel.expression.switchvalue.VariantOptionSwitchValue;
import org.openzen.zenscript.codemodel.generic.TypeParameter;
import org.openzen.zenscript.codemodel.statement.LoopStatement;
import org.openzen.zenscript.codemodel.statement.VarStatement;
import org.openzen.zenscript.codemodel.type.TypeID;

public class StatementContext extends TypeContext {
   private final List<VarStatement> variables = new ArrayList<>();
   private final LoopStatement[] loops;
   private final FunctionHeader header;
   private final LambdaClosure closure;
   private final StatementContext lambdaOuter;
   public VariantOptionSwitchValue variantOptionSwitchValue;

   public StatementContext(CodePosition position, ModuleContext module, TypeID thisType) {
      super(position, module, TypeParameter.NONE, thisType);
      this.loops = LoopStatement.NONE;
      this.header = null;
      this.closure = null;
      this.lambdaOuter = null;
   }

   public StatementContext(CodePosition position, ModuleContext module, TypeID thisType, FunctionHeader header) {
      super(position, module, header.typeParameters, thisType);
      this.loops = LoopStatement.NONE;
      this.header = header;
      this.closure = null;
      this.lambdaOuter = null;
   }

   public StatementContext(CodePosition position, TypeContext outer) {
      super(position, outer.moduleContext, outer.typeParameters, outer.thisType);
      this.loops = LoopStatement.NONE;
      this.header = null;
      this.closure = null;
      this.lambdaOuter = null;
   }

   @Deprecated
   public StatementContext(TypeContext outer) {
      this(outer.getPosition(), outer);
   }

   @Deprecated
   public StatementContext(TypeContext outer, FunctionHeader header) {
      this(outer.getPosition(), outer, header);
   }

   public StatementContext(CodePosition position, TypeContext outer, FunctionHeader header) {
      super(position, outer, outer.thisType, header == null ? TypeParameter.NONE : header.typeParameters);
      this.loops = LoopStatement.NONE;
      this.header = header;
      this.closure = null;
      this.lambdaOuter = null;
   }

   public StatementContext(StatementContext outer) {
      super(outer.position, outer.moduleContext, outer.typeParameters, outer.thisType);
      this.variables.addAll(outer.variables);
      this.loops = outer.loops;
      this.header = outer.header;
      this.closure = outer.closure;
      this.lambdaOuter = outer.lambdaOuter;
   }

   public StatementContext(StatementContext outer, LoopStatement loop) {
      super(outer.position, outer.moduleContext, outer.typeParameters, outer.thisType);
      this.variables.addAll(outer.variables);
      this.loops = Arrays.copyOf(outer.loops, outer.loops.length + 1);
      this.loops[this.loops.length - 1] = loop;
      this.header = outer.header;
      this.closure = outer.closure;
      this.lambdaOuter = outer.lambdaOuter;
   }

   public StatementContext(StatementContext outer, FunctionHeader lambdaHeader, LambdaClosure lambdaClosure) {
      super(outer.position, outer, outer.thisType, lambdaHeader.typeParameters);
      this.loops = LoopStatement.NONE;
      this.header = lambdaHeader;
      this.closure = lambdaClosure;
      this.lambdaOuter = outer;
   }

   public void add(VarStatement variable) {
      this.variables.add(variable);
   }

   public int getVariableId(VarStatement variable) {
      int id = this.variables.indexOf(variable);
      if (id < 0) {
         throw new IllegalArgumentException("Variable not in scope: " + variable.name);
      } else {
         return id;
      }
   }

   public int getLoopId(LoopStatement loop) {
      for (int i = 0; i < this.loops.length; i++) {
         if (this.loops[i] == loop) {
            return i;
         }
      }

      throw new IllegalArgumentException("Loop@" + loop.position + " not in scope");
   }

   public int getParameterIndex(FunctionParameter parameter) {
      if (this.header == null) {
         throw new IllegalStateException("No parameters available");
      }

      for (int i = 0; i < this.header.parameters.length; i++) {
         if (this.header.parameters[i] == parameter) {
            return i;
         }
      }

      throw new IllegalArgumentException("Parameter" + parameter.name + " not in scope");
   }

   public VarStatement getVariable(int id) {
      return this.variables.get(id);
   }

   public LoopStatement getLoop(int id) {
      return this.loops[id];
   }

   public FunctionParameter getParameter(int id) {
      return this.header.parameters[id];
   }

   public LambdaClosure getLambdaClosure() {
      return this.closure;
   }

   public StatementContext getLambdaOuter() {
      return this.lambdaOuter;
   }
}
