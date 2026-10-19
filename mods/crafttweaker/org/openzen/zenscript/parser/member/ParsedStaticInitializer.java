package org.openzen.zenscript.parser.member;

import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.codemodel.FunctionHeader;
import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.codemodel.context.TypeResolutionContext;
import org.openzen.zenscript.codemodel.member.IDefinitionMember;
import org.openzen.zenscript.codemodel.member.StaticInitializerMember;
import org.openzen.zenscript.codemodel.scope.BaseScope;
import org.openzen.zenscript.codemodel.scope.FunctionScope;
import org.openzen.zenscript.codemodel.type.BasicTypeID;
import org.openzen.zenscript.parser.ParsedAnnotation;
import org.openzen.zenscript.parser.statements.ParsedStatement;

public class ParsedStaticInitializer extends ParsedDefinitionMember {
   private final ParsedStatement body;
   private final StaticInitializerMember compiled;

   public ParsedStaticInitializer(HighLevelDefinition definition, CodePosition position, ParsedAnnotation[] annotations, ParsedStatement body) {
      super(definition, annotations);
      this.body = body;
      this.compiled = new StaticInitializerMember(position, definition);
   }

   @Override
   public void linkTypes(TypeResolutionContext context) {
   }

   @Override
   public IDefinitionMember getCompiled() {
      return this.compiled;
   }

   @Override
   public void compile(BaseScope scope) {
      this.compiled.annotations = ParsedAnnotation.compileForMember(this.annotations, this.compiled, scope);
      this.compiled.body = this.body.compile(new FunctionScope(this.compiled.position, scope, new FunctionHeader(BasicTypeID.VOID)));
   }
}
