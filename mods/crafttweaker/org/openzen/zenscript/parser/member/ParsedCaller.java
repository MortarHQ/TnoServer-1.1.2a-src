package org.openzen.zenscript.parser.member;

import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.codemodel.OperatorType;
import org.openzen.zenscript.codemodel.context.TypeResolutionContext;
import org.openzen.zenscript.codemodel.member.CallerMember;
import org.openzen.zenscript.codemodel.member.FunctionalMember;
import org.openzen.zenscript.codemodel.member.ref.FunctionalMemberRef;
import org.openzen.zenscript.codemodel.scope.TypeScope;
import org.openzen.zenscript.codemodel.type.TypeID;
import org.openzen.zenscript.parser.ParsedAnnotation;
import org.openzen.zenscript.parser.definitions.ParsedFunctionHeader;
import org.openzen.zenscript.parser.statements.ParsedFunctionBody;

public class ParsedCaller extends ParsedFunctionalMember {
   private final ParsedFunctionHeader header;
   private CallerMember compiled;

   public ParsedCaller(
      CodePosition position,
      HighLevelDefinition definition,
      ParsedImplementation implementation,
      int modifiers,
      ParsedAnnotation[] annotations,
      ParsedFunctionHeader header,
      ParsedFunctionBody body
   ) {
      super(position, definition, implementation, modifiers, annotations, body);
      this.header = header;
   }

   @Override
   public void linkTypes(TypeResolutionContext context) {
      this.compiled = new CallerMember(this.position, this.definition, this.modifiers, this.header.compile(context), null);
   }

   @Override
   public FunctionalMember getCompiled() {
      return this.compiled;
   }

   @Override
   protected void fillOverride(TypeScope scope, TypeID baseType) throws CompileException {
      FunctionalMemberRef base = scope.getTypeMembers(baseType).getOrCreateGroup(OperatorType.CALL).getOverride(this.position, scope, this.compiled);
      if (base.getHeader().hasUnknowns) {
         scope.getPreparer().prepare(base.getTarget());
         base = scope.getTypeMembers(baseType).getOrCreateGroup(OperatorType.CALL).getOverride(this.position, scope, this.compiled);
      }

      this.compiled.setOverrides(scope.getTypeRegistry(), base);
   }
}
