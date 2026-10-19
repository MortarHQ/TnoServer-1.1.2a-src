package org.openzen.zenscript.parser.member;

import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.codemodel.FunctionHeader;
import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.codemodel.context.TypeResolutionContext;
import org.openzen.zenscript.codemodel.member.IteratorMember;
import org.openzen.zenscript.codemodel.scope.BaseScope;
import org.openzen.zenscript.codemodel.scope.FunctionScope;
import org.openzen.zenscript.codemodel.scope.StatementScope;
import org.openzen.zenscript.codemodel.type.TypeID;
import org.openzen.zenscript.parser.ParsedAnnotation;
import org.openzen.zenscript.parser.definitions.ParsedFunctionHeader;
import org.openzen.zenscript.parser.statements.ParsedFunctionBody;

public class ParsedIterator extends ParsedDefinitionMember {
   private final CodePosition position;
   private final int modifiers;
   private final ParsedFunctionHeader header;
   private final ParsedFunctionBody body;
   private IteratorMember compiled;

   public ParsedIterator(
      CodePosition position,
      HighLevelDefinition definition,
      int modifiers,
      ParsedAnnotation[] annotations,
      ParsedFunctionHeader header,
      ParsedFunctionBody body
   ) {
      super(definition, annotations);
      this.position = position;
      this.modifiers = modifiers;
      this.header = header;
      this.body = body;
   }

   @Override
   public void linkTypes(TypeResolutionContext context) {
      TypeID[] loopVariableTypes = new TypeID[this.header.parameters.size()];

      for (int i = 0; i < loopVariableTypes.length; i++) {
         loopVariableTypes[i] = this.header.parameters.get(i).type.compile(context);
      }

      this.compiled = new IteratorMember(this.position, this.definition, this.modifiers, loopVariableTypes, context.getTypeRegistry(), null);
   }

   public IteratorMember getCompiled() {
      return this.compiled;
   }

   @Override
   public void compile(BaseScope scope) {
      FunctionHeader header = new FunctionHeader(scope.getTypeRegistry().getIterator(this.compiled.getLoopVariableTypes()));
      StatementScope innerScope = new FunctionScope(this.position, scope, header);
      this.compiled.annotations = ParsedAnnotation.compileForMember(this.annotations, this.compiled, scope);
      this.compiled.setContent(this.body.compile(innerScope, header));
   }
}
