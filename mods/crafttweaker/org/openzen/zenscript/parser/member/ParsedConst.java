package org.openzen.zenscript.parser.member;

import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zencode.shared.CompileExceptionCode;
import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.codemodel.context.TypeResolutionContext;
import org.openzen.zenscript.codemodel.expression.Expression;
import org.openzen.zenscript.codemodel.member.ConstMember;
import org.openzen.zenscript.codemodel.scope.BaseScope;
import org.openzen.zenscript.codemodel.scope.ExpressionScope;
import org.openzen.zenscript.codemodel.type.BasicTypeID;
import org.openzen.zenscript.parser.ParsedAnnotation;
import org.openzen.zenscript.parser.expression.ParsedExpression;
import org.openzen.zenscript.parser.type.IParsedType;

public class ParsedConst extends ParsedDefinitionMember {
   private final CodePosition position;
   private final int modifiers;
   private final String name;
   private final IParsedType type;
   private final ParsedExpression expression;
   private boolean isCompiled = false;
   private ConstMember compiled;

   public ParsedConst(
      CodePosition position,
      HighLevelDefinition definition,
      int modifiers,
      ParsedAnnotation[] annotations,
      String name,
      IParsedType type,
      ParsedExpression expression
   ) {
      super(definition, annotations);
      this.position = position;
      this.modifiers = modifiers;
      this.name = name;
      this.type = type;
      this.expression = expression;
   }

   @Override
   public void linkTypes(TypeResolutionContext context) {
      this.compiled = new ConstMember(this.position, this.definition, this.modifiers, this.name, this.type.compile(context), null);
   }

   public ConstMember getCompiled() {
      return this.compiled;
   }

   @Override
   public void compile(BaseScope scope) throws CompileException {
      if (!this.isCompiled) {
         this.isCompiled = true;
         this.compiled.annotations = ParsedAnnotation.compileForMember(this.annotations, this.compiled, scope);
         if (this.expression != null) {
            Expression initializer = this.expression
               .compile(new ExpressionScope(scope, this.compiled.getType()))
               .eval()
               .castImplicit(this.position, scope, this.compiled.getType());
            this.compiled.value = initializer;
            if (this.compiled.getType() == BasicTypeID.UNDETERMINED) {
               this.compiled.setType(initializer.type);
            }
         } else if (this.compiled.getType() == BasicTypeID.UNDETERMINED) {
            throw new CompileException(this.position, CompileExceptionCode.PRECOMPILE_FAILED, "No type or initializer given");
         }
      }
   }
}
