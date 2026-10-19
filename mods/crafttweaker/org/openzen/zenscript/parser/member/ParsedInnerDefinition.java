package org.openzen.zenscript.parser.member;

import java.util.Map;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.codemodel.context.TypeResolutionContext;
import org.openzen.zenscript.codemodel.member.InnerDefinitionMember;
import org.openzen.zenscript.codemodel.scope.BaseScope;
import org.openzen.zenscript.parser.ParsedAnnotation;
import org.openzen.zenscript.parser.ParsedDefinition;
import org.openzen.zenscript.parser.PrecompilationState;

public class ParsedInnerDefinition extends ParsedDefinitionMember {
   private final ParsedDefinition innerDefinition;
   private final InnerDefinitionMember member;
   private boolean typesCompiled = false;

   public ParsedInnerDefinition(HighLevelDefinition outer, ParsedDefinition definition) {
      super(outer, ParsedAnnotation.NONE);
      this.innerDefinition = definition;
      this.member = new InnerDefinitionMember(definition.getPosition(), outer, definition.getModifiers(), definition.getCompiled());
   }

   @Override
   public void registerInnerTypes(Map<String, ParsedDefinition> inner) {
      inner.put(this.innerDefinition.getCompiled().name, this.innerDefinition);
   }

   @Override
   public void linkTypes(TypeResolutionContext context) {
      if (!this.typesCompiled) {
         this.typesCompiled = true;
         this.innerDefinition.linkTypes(context);
      }
   }

   public InnerDefinitionMember getCompiled() {
      return this.member;
   }

   @Override
   public void compile(BaseScope scope) throws CompileException {
      this.innerDefinition.compile(scope);
   }

   @Override
   public void registerMembers(BaseScope scope, PrecompilationState state) {
      this.innerDefinition.registerMembers(scope, state);
   }
}
