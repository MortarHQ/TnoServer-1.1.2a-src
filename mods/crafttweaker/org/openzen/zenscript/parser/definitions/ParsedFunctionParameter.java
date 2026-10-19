package org.openzen.zenscript.parser.definitions;

import java.util.Collections;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zenscript.codemodel.FunctionParameter;
import org.openzen.zenscript.codemodel.context.TypeResolutionContext;
import org.openzen.zenscript.codemodel.expression.InvalidExpression;
import org.openzen.zenscript.codemodel.scope.BaseScope;
import org.openzen.zenscript.codemodel.scope.ExpressionScope;
import org.openzen.zenscript.codemodel.scope.FileScope;
import org.openzen.zenscript.codemodel.type.TypeID;
import org.openzen.zenscript.parser.ParsedAnnotation;
import org.openzen.zenscript.parser.PrecompilationState;
import org.openzen.zenscript.parser.expression.ParsedExpression;
import org.openzen.zenscript.parser.type.IParsedType;

public class ParsedFunctionParameter {
   public final ParsedAnnotation[] annotations;
   public final String name;
   public final IParsedType type;
   public final ParsedExpression defaultValue;
   public final boolean variadic;
   private FunctionParameter compiled;

   public ParsedFunctionParameter(ParsedAnnotation[] annotations, String name, IParsedType type, ParsedExpression defaultValue, boolean variadic) {
      this.annotations = annotations;
      this.name = name;
      this.type = type;
      this.defaultValue = defaultValue;
      this.variadic = variadic;
   }

   public FunctionParameter compile(TypeResolutionContext context) {
      if (this.compiled != null) {
         return this.compiled;
      }

      TypeID cType = this.type.compile(context);
      this.compiled = new FunctionParameter(cType, this.name, null, this.variadic);
      this.compileDefaultValue(new FileScope(context, Collections.emptyList(), Collections.emptyMap()), new PrecompilationState());
      return this.compiled;
   }

   public void compileDefaultValue(BaseScope scope, PrecompilationState state) {
      if (this.defaultValue != null) {
         try {
            this.compiled.defaultValue = this.defaultValue.compile(new ExpressionScope(scope, this.compiled.type)).eval();
         } catch (CompileException ex) {
            this.compiled.defaultValue = new InvalidExpression(this.compiled.type, ex);
         }
      }
   }
}
