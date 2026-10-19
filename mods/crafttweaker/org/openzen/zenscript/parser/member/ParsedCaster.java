package org.openzen.zenscript.parser.member;

import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.codemodel.context.TypeResolutionContext;
import org.openzen.zenscript.codemodel.member.CasterMember;
import org.openzen.zenscript.codemodel.member.FunctionalMember;
import org.openzen.zenscript.codemodel.scope.TypeScope;
import org.openzen.zenscript.codemodel.type.TypeID;
import org.openzen.zenscript.parser.ParsedAnnotation;
import org.openzen.zenscript.parser.statements.ParsedFunctionBody;
import org.openzen.zenscript.parser.type.IParsedType;

public class ParsedCaster extends ParsedFunctionalMember {
   private final IParsedType type;
   private CasterMember compiled;

   public ParsedCaster(
      CodePosition position,
      HighLevelDefinition definition,
      ParsedImplementation implementation,
      int modifiers,
      ParsedAnnotation[] annotations,
      IParsedType type,
      ParsedFunctionBody body
   ) {
      super(position, definition, implementation, modifiers, annotations, body);
      this.type = type;
   }

   @Override
   public void linkTypes(TypeResolutionContext context) {
      this.compiled = new CasterMember(this.position, this.definition, this.modifiers, this.type.compile(context), null);
   }

   @Override
   public FunctionalMember getCompiled() {
      return this.compiled;
   }

   @Override
   protected void fillOverride(TypeScope scope, TypeID baseType) {
      this.compiled.overrides = scope.getTypeMembers(baseType).getCaster(this.compiled.toType);
   }
}
