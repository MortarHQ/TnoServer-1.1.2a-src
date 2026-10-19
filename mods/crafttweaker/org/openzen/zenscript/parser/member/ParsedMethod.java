package org.openzen.zenscript.parser.member;

import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zencode.shared.CompileExceptionCode;
import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.codemodel.context.TypeResolutionContext;
import org.openzen.zenscript.codemodel.member.FunctionalMember;
import org.openzen.zenscript.codemodel.member.MethodMember;
import org.openzen.zenscript.codemodel.member.ref.FunctionalMemberRef;
import org.openzen.zenscript.codemodel.scope.TypeScope;
import org.openzen.zenscript.codemodel.type.TypeID;
import org.openzen.zenscript.codemodel.type.member.TypeMembers;
import org.openzen.zenscript.parser.ParsedAnnotation;
import org.openzen.zenscript.parser.definitions.ParsedFunctionHeader;
import org.openzen.zenscript.parser.statements.ParsedFunctionBody;

public class ParsedMethod extends ParsedFunctionalMember {
   private final String name;
   private final ParsedFunctionHeader header;
   private MethodMember compiled;

   public ParsedMethod(
      CodePosition position,
      HighLevelDefinition definition,
      ParsedImplementation implementation,
      int modifiers,
      ParsedAnnotation[] annotations,
      String name,
      ParsedFunctionHeader header,
      ParsedFunctionBody body
   ) {
      super(position, definition, implementation, modifiers, annotations, body);
      this.name = name;
      this.header = header;
   }

   @Override
   public void linkTypes(TypeResolutionContext context) {
      this.compiled = new MethodMember(this.position, this.definition, this.modifiers, this.name, this.header.compile(context), null);
   }

   @Override
   public FunctionalMember getCompiled() {
      return this.compiled;
   }

   @Override
   protected void fillOverride(TypeScope scope, TypeID baseType) throws CompileException {
      TypeMembers typeMembers = scope.getTypeMembers(baseType);
      FunctionalMemberRef override = typeMembers.getOrCreateGroup(this.name, false).getOverride(this.position, scope, this.compiled);
      if (override == null) {
         throw new CompileException(this.position, CompileExceptionCode.PRECOMPILE_FAILED, "Could not determine overridden method");
      }

      if (override.getHeader().hasUnknowns) {
         scope.getPreparer().prepare(override.getTarget());
         override = scope.getTypeMembers(baseType).getOrCreateGroup(this.name, false).getOverride(this.position, scope, this.compiled);
      }

      this.compiled.setOverrides(scope.getTypeRegistry(), override);
   }
}
