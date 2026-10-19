package org.openzen.zenscript.parser.member;

import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zencode.shared.CompileExceptionCode;
import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.codemodel.Modifiers;
import org.openzen.zenscript.codemodel.member.FunctionalMember;
import org.openzen.zenscript.codemodel.scope.BaseScope;
import org.openzen.zenscript.codemodel.scope.FunctionScope;
import org.openzen.zenscript.codemodel.scope.TypeScope;
import org.openzen.zenscript.codemodel.type.BasicTypeID;
import org.openzen.zenscript.codemodel.type.TypeID;
import org.openzen.zenscript.parser.ParsedAnnotation;
import org.openzen.zenscript.parser.statements.ParsedFunctionBody;

public abstract class ParsedFunctionalMember extends ParsedDefinitionMember {
   protected final CodePosition position;
   protected final int modifiers;
   protected final ParsedImplementation implementation;
   protected final ParsedFunctionBody body;
   private boolean isCompiled = false;

   public ParsedFunctionalMember(
      CodePosition position,
      HighLevelDefinition definition,
      ParsedImplementation implementation,
      int modifiers,
      ParsedAnnotation[] annotations,
      ParsedFunctionBody body
   ) {
      super(definition, annotations);
      this.implementation = implementation;
      this.position = position;
      this.modifiers = modifiers;
      this.body = body;
   }

   public abstract FunctionalMember getCompiled();

   protected void inferHeaders(BaseScope scope) throws CompileException {
      if (this.implementation != null && !Modifiers.isPrivate(this.modifiers)) {
         this.fillOverride(scope, this.implementation.getCompiled().type);
      } else if (this.implementation == null && Modifiers.isOverride(this.modifiers)) {
         if (this.definition.getSuperType() == null) {
            throw new CompileException(this.position, CompileExceptionCode.OVERRIDE_WITHOUT_BASE, "Override specified without base type");
         }

         this.fillOverride(scope, this.definition.getSuperType());
      }

      if (this.getCompiled() == null || this.getCompiled().header == null) {
         throw new IllegalStateException("Types not yet linked");
      }
   }

   @Override
   public final void compile(BaseScope scope) throws CompileException {
      if (!this.isCompiled) {
         this.isCompiled = true;
         this.inferHeaders(scope);
         FunctionScope innerScope = new FunctionScope(this.position, scope, this.getCompiled().header);
         this.getCompiled().annotations = ParsedAnnotation.compileForMember(this.annotations, this.getCompiled(), scope);
         this.getCompiled().setBody(this.body.compile(innerScope, this.getCompiled().header));
         if (this.getCompiled().header.getReturnType() == BasicTypeID.UNDETERMINED) {
            if (this.getCompiled().body == null) {
               throw new CompileException(this.position, CompileExceptionCode.CANNOT_INFER_RETURN_TYPE, "Method return type could not be inferred");
            }

            TypeID returnType = this.getCompiled().body.getReturnType();
            if (returnType == null) {
               throw new CompileException(this.position, CompileExceptionCode.CANNOT_INFER_RETURN_TYPE, "Method return type could not be inferred");
            }

            this.getCompiled().header.setReturnType(returnType);
         }
      }
   }

   protected abstract void fillOverride(TypeScope var1, TypeID var2) throws CompileException;
}
