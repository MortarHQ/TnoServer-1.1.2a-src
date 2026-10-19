package org.openzen.zenscript.parser.member;

import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.codemodel.OperatorType;
import org.openzen.zenscript.codemodel.context.TypeResolutionContext;
import org.openzen.zenscript.codemodel.member.FunctionalMember;
import org.openzen.zenscript.codemodel.member.OperatorMember;
import org.openzen.zenscript.codemodel.member.ref.FunctionalMemberRef;
import org.openzen.zenscript.codemodel.scope.TypeScope;
import org.openzen.zenscript.codemodel.type.TypeID;
import org.openzen.zenscript.codemodel.type.member.TypeMemberGroup;
import org.openzen.zenscript.parser.ParsedAnnotation;
import org.openzen.zenscript.parser.definitions.ParsedFunctionHeader;
import org.openzen.zenscript.parser.statements.ParsedFunctionBody;

public class ParsedOperator extends ParsedFunctionalMember {
   private final OperatorType operator;
   private final ParsedFunctionHeader header;
   private OperatorMember compiled;

   public ParsedOperator(
      CodePosition position,
      HighLevelDefinition definition,
      ParsedImplementation implementation,
      int modifiers,
      ParsedAnnotation[] annotations,
      OperatorType operator,
      ParsedFunctionHeader header,
      ParsedFunctionBody body
   ) {
      super(position, definition, implementation, modifiers, annotations, body);
      this.operator = operator;
      this.header = header;
   }

   @Override
   public void linkTypes(TypeResolutionContext context) {
      this.compiled = new OperatorMember(this.position, this.definition, this.modifiers, this.operator, this.header.compile(context), null);
   }

   @Override
   public FunctionalMember getCompiled() {
      return this.compiled;
   }

   @Override
   protected void fillOverride(TypeScope scope, TypeID baseType) throws CompileException {
      TypeMemberGroup group = scope.getTypeMembers(baseType).getOrCreateGroup(this.operator);
      FunctionalMemberRef override = group.getOverride(this.position, scope, this.compiled);
      if (override != null) {
         if (override.getHeader().hasUnknowns) {
            scope.getPreparer().prepare(override.getTarget());
            override = scope.getTypeMembers(baseType).getOrCreateGroup(this.operator).getOverride(this.position, scope, this.compiled);
         }

         this.compiled.setOverrides(scope.getTypeRegistry(), override);
      }
   }
}
