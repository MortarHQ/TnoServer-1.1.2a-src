package org.openzen.zenscript.parser;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zenscript.codemodel.member.IDefinitionMember;
import org.openzen.zenscript.codemodel.scope.BaseScope;
import org.openzen.zenscript.codemodel.type.member.TypeMemberPreparer;
import org.openzen.zenscript.parser.member.ParsedDefinitionMember;

public class PrecompilationState implements TypeMemberPreparer {
   private final Map<IDefinitionMember, PrecompilationState.CompilableMember> members = new HashMap<>();
   private final Set<ParsedDefinitionMember> compilingMembers = new HashSet<>();

   public void register(BaseScope definitionScope, ParsedDefinitionMember member) {
      this.members.put(member.getCompiled(), new PrecompilationState.CompilableMember(member, definitionScope));
   }

   public boolean precompile(IDefinitionMember member) throws CompileException {
      if (!this.members.containsKey(member)) {
         return true;
      }

      PrecompilationState.CompilableMember cMember = this.members.get(member);
      if (this.compilingMembers.contains(cMember.member)) {
         return false;
      }

      this.compilingMembers.add(cMember.member);
      cMember.member.compile(cMember.definitionScope);
      this.compilingMembers.remove(cMember.member);
      return true;
   }

   public void end(ParsedDefinitionMember member) {
      this.compilingMembers.remove(member);
   }

   @Override
   public void prepare(IDefinitionMember member) throws CompileException {
      this.precompile(member);
   }

   private class CompilableMember {
      private final ParsedDefinitionMember member;
      private final BaseScope definitionScope;

      public CompilableMember(ParsedDefinitionMember member, BaseScope definitionScope) {
         this.member = member;
         this.definitionScope = definitionScope;
      }
   }
}
