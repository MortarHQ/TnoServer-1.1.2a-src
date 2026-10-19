package org.openzen.zenscript.parser.member;

import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zencode.shared.CompileExceptionCode;
import org.openzen.zenscript.codemodel.FunctionHeader;
import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.codemodel.Modifiers;
import org.openzen.zenscript.codemodel.context.TypeResolutionContext;
import org.openzen.zenscript.codemodel.expression.GetFunctionParameterExpression;
import org.openzen.zenscript.codemodel.member.SetterMember;
import org.openzen.zenscript.codemodel.scope.BaseScope;
import org.openzen.zenscript.codemodel.scope.FunctionScope;
import org.openzen.zenscript.codemodel.scope.TypeScope;
import org.openzen.zenscript.codemodel.type.TypeID;
import org.openzen.zenscript.parser.ParsedAnnotation;
import org.openzen.zenscript.parser.statements.ParsedFunctionBody;
import org.openzen.zenscript.parser.type.IParsedType;

public class ParsedSetter extends ParsedDefinitionMember {
   private final CodePosition position;
   private final int modifiers;
   private final ParsedImplementation implementation;
   private final ParsedFunctionBody body;
   private final String name;
   private final IParsedType type;
   private SetterMember compiled;
   private boolean isCompiled = false;

   public ParsedSetter(
      CodePosition position,
      HighLevelDefinition definition,
      ParsedImplementation implementation,
      int modifiers,
      ParsedAnnotation[] annotations,
      String name,
      IParsedType type,
      ParsedFunctionBody body
   ) {
      super(definition, annotations);
      this.implementation = implementation;
      this.position = position;
      this.modifiers = modifiers;
      this.body = body;
      this.name = name;
      this.type = type;
   }

   @Override
   public void linkTypes(TypeResolutionContext context) {
      this.compiled = new SetterMember(this.position, this.definition, this.modifiers, this.name, this.type.compile(context), null);
   }

   public SetterMember getCompiled() {
      return this.compiled;
   }

   private void inferHeaders(BaseScope scope) throws CompileException {
      if (this.implementation != null && !Modifiers.isPrivate(this.modifiers)) {
         this.fillOverride(scope, this.implementation.getCompiled().type);
      } else if (this.implementation == null && Modifiers.isOverride(this.modifiers)) {
         if (this.definition.getSuperType() == null) {
            throw new CompileException(this.position, CompileExceptionCode.OVERRIDE_WITHOUT_BASE, "Override specified without base type");
         }

         this.fillOverride(scope, this.definition.getSuperType());
      }

      if (this.compiled == null) {
         throw new IllegalStateException("Types not yet linked");
      }
   }

   private void fillOverride(TypeScope scope, TypeID baseType) {
      this.compiled.setOverrides(scope.getTypeMembers(baseType).getOrCreateGroup(this.name, true).getSetter());
   }

   @Override
   public final void compile(BaseScope scope) throws CompileException {
      if (!this.isCompiled) {
         this.isCompiled = true;
         this.inferHeaders(scope);
         FunctionHeader header = new FunctionHeader(this.compiled.getType());
         FunctionScope innerScope = new FunctionScope(
            this.position, scope, header, position -> new GetFunctionParameterExpression(position, this.compiled.parameter)
         );
         this.compiled.annotations = ParsedAnnotation.compileForMember(this.annotations, this.getCompiled(), scope);
         this.compiled.setBody(this.body.compile(innerScope, header));
      }
   }
}
