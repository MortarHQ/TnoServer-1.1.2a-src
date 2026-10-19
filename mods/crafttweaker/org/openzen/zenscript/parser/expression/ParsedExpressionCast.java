package org.openzen.zenscript.parser.expression;

import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zenscript.codemodel.partial.IPartialExpression;
import org.openzen.zenscript.codemodel.scope.ExpressionScope;
import org.openzen.zenscript.codemodel.type.TypeID;
import org.openzen.zenscript.lexer.ParseException;
import org.openzen.zenscript.parser.ParsedAnnotation;
import org.openzen.zenscript.parser.definitions.ParsedFunctionHeader;
import org.openzen.zenscript.parser.definitions.ParsedFunctionParameter;
import org.openzen.zenscript.parser.type.IParsedType;
import org.openzen.zenscript.parser.type.ParsedTypeBasic;

public class ParsedExpressionCast extends ParsedExpression {
   private final ParsedExpression value;
   private final IParsedType type;
   private final boolean optional;

   public ParsedExpressionCast(CodePosition position, ParsedExpression value, IParsedType type, boolean optional) {
      super(position);
      this.value = value;
      this.type = type;
      this.optional = optional;
   }

   @Override
   public IPartialExpression compile(ExpressionScope scope) throws CompileException {
      TypeID type = this.type.compile(scope);
      return this.value.compile(scope.withHint(type)).eval().castExplicit(this.position, scope, type, this.optional);
   }

   @Override
   public ParsedFunctionHeader toLambdaHeader() throws ParseException {
      if (this.optional) {
         throw new ParseException(this.position, "Not a valid lambda header");
      } else {
         ParsedFunctionHeader header = this.value.toLambdaHeader();
         if (header.returnType != ParsedTypeBasic.UNDETERMINED) {
            throw new ParseException(this.position, "Lambda parameter already has a return type");
         } else {
            return new ParsedFunctionHeader(this.position, header.genericParameters, header.parameters, this.type, null);
         }
      }
   }

   @Override
   public ParsedFunctionParameter toLambdaParameter() throws ParseException {
      if (this.optional) {
         throw new ParseException(this.position, "Not a valid lambda header");
      } else {
         ParsedFunctionParameter parameter = this.value.toLambdaParameter();
         if (parameter.type != ParsedTypeBasic.UNDETERMINED) {
            throw new ParseException(this.position, "Lambda parameter already has a type");
         } else {
            return new ParsedFunctionParameter(ParsedAnnotation.NONE, parameter.name, this.type, null, false);
         }
      }
   }

   @Override
   public boolean hasStrongType() {
      return true;
   }
}
