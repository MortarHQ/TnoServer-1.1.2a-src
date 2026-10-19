package org.openzen.zenscript.parser.member;

import java.util.ArrayList;
import java.util.List;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.codemodel.context.TypeResolutionContext;
import org.openzen.zenscript.codemodel.member.ImplementationMember;
import org.openzen.zenscript.codemodel.scope.BaseScope;
import org.openzen.zenscript.codemodel.scope.ImplementationScope;
import org.openzen.zenscript.parser.ParsedAnnotation;
import org.openzen.zenscript.parser.PrecompilationState;
import org.openzen.zenscript.parser.type.IParsedType;

public class ParsedImplementation extends ParsedDefinitionMember {
   private final CodePosition position;
   private final int modifiers;
   private final IParsedType type;
   private final List<ParsedDefinitionMember> members = new ArrayList<>();
   private ImplementationMember compiled;

   public ParsedImplementation(CodePosition position, HighLevelDefinition definition, int modifiers, ParsedAnnotation[] annotations, IParsedType type) {
      super(definition, annotations);
      this.position = position;
      this.modifiers = modifiers;
      this.type = type;
   }

   public void addMember(ParsedDefinitionMember member) {
      this.members.add(member);
   }

   @Override
   public void linkTypes(TypeResolutionContext context) {
      this.compiled = new ImplementationMember(this.position, this.definition, this.modifiers, this.type.compile(context));

      for (ParsedDefinitionMember member : this.members) {
         member.linkTypes(context);
         this.compiled.addMember(member.getCompiled());
      }
   }

   public ImplementationMember getCompiled() {
      return this.compiled;
   }

   @Override
   public void compile(BaseScope scope) throws CompileException {
      this.compiled.annotations = ParsedAnnotation.compileForMember(this.annotations, this.compiled, scope);
      ImplementationScope innerScope = new ImplementationScope(scope, this.compiled);

      for (ParsedDefinitionMember member : this.members) {
         member.compile(innerScope);
      }
   }

   @Override
   public void registerMembers(BaseScope scope, PrecompilationState state) {
      ImplementationScope innerScope = new ImplementationScope(scope, this.compiled);

      for (ParsedDefinitionMember member : this.members) {
         state.register(innerScope, member);
      }
   }
}
