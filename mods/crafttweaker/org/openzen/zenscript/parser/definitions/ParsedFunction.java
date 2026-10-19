package org.openzen.zenscript.parser.definitions;

import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.codemodel.context.CompilingPackage;
import org.openzen.zenscript.codemodel.context.CompilingType;
import org.openzen.zenscript.codemodel.context.TypeResolutionContext;
import org.openzen.zenscript.codemodel.definition.FunctionDefinition;
import org.openzen.zenscript.codemodel.scope.BaseScope;
import org.openzen.zenscript.codemodel.scope.FunctionScope;
import org.openzen.zenscript.codemodel.type.BasicTypeID;
import org.openzen.zenscript.lexer.ParseException;
import org.openzen.zenscript.lexer.ZSTokenParser;
import org.openzen.zenscript.lexer.ZSTokenType;
import org.openzen.zenscript.parser.ParsedAnnotation;
import org.openzen.zenscript.parser.ParsedDefinition;
import org.openzen.zenscript.parser.PrecompilationState;
import org.openzen.zenscript.parser.statements.ParsedFunctionBody;
import org.openzen.zenscript.parser.statements.ParsedStatement;

public class ParsedFunction extends ParsedDefinition {
   private final ParsedFunctionHeader header;
   private final ParsedFunctionBody body;
   private final FunctionDefinition compiled;

   private ParsedFunction(
      CompilingPackage pkg,
      CodePosition position,
      int modifiers,
      ParsedAnnotation[] annotations,
      String name,
      ParsedFunctionHeader header,
      ParsedFunctionBody body,
      HighLevelDefinition outerDefinition
   ) {
      super(position, modifiers, pkg, annotations);
      this.header = header;
      this.body = body;
      this.compiled = new FunctionDefinition(position, pkg.module, pkg.getPackage(), name, modifiers, outerDefinition);
   }

   public static ParsedFunction parseFunction(
      CompilingPackage pkg, CodePosition position, int modifiers, ParsedAnnotation[] annotations, ZSTokenParser parser, HighLevelDefinition outerDefinition
   ) throws ParseException {
      String name = parser.required(ZSTokenType.T_IDENTIFIER, "identifier expected").content;
      ParsedFunctionHeader header = ParsedFunctionHeader.parse(parser);
      ParsedFunctionBody body = ParsedStatement.parseFunctionBody(parser);
      return new ParsedFunction(pkg, position, modifiers, annotations, name, header, body, outerDefinition);
   }

   @Override
   public HighLevelDefinition getCompiled() {
      return this.compiled;
   }

   @Override
   public void linkTypes(TypeResolutionContext context) {
      if (this.compiled.header == null) {
         this.compiled.setHeader(context.getTypeRegistry(), this.header.compile(context));
      }
   }

   @Override
   public void registerMembers(BaseScope scope, PrecompilationState state) {
   }

   @Override
   public void compile(BaseScope scope) {
      FunctionScope innerScope = new FunctionScope(this.position, scope, this.compiled.header);
      this.compiled.setCode(this.body.compile(innerScope, this.compiled.header));
      if (this.compiled.header.getReturnType() == BasicTypeID.UNDETERMINED) {
         this.compiled.header.setReturnType(this.compiled.caller.body.getReturnType());
      }
   }

   @Override
   public CompilingType getCompiling(TypeResolutionContext context) {
      return new ParsedFunction.Compiling(context);
   }

   private class Compiling implements CompilingType {
      private final TypeResolutionContext context;

      public Compiling(TypeResolutionContext context) {
         this.context = context;
      }

      @Override
      public CompilingType getInner(String name) {
         return null;
      }

      @Override
      public HighLevelDefinition load() {
         ParsedFunction.this.linkTypes(this.context);
         return ParsedFunction.this.compiled;
      }
   }
}
