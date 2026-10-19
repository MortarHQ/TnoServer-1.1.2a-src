package org.openzen.zenscript.codemodel.scope;

import java.util.List;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zenscript.codemodel.FunctionHeader;
import org.openzen.zenscript.codemodel.FunctionParameter;
import org.openzen.zenscript.codemodel.GenericMapper;
import org.openzen.zenscript.codemodel.GenericName;
import org.openzen.zenscript.codemodel.annotations.AnnotationDefinition;
import org.openzen.zenscript.codemodel.definition.ZSPackage;
import org.openzen.zenscript.codemodel.expression.GetFunctionParameterExpression;
import org.openzen.zenscript.codemodel.generic.TypeParameter;
import org.openzen.zenscript.codemodel.partial.IPartialExpression;
import org.openzen.zenscript.codemodel.partial.PartialTypeExpression;
import org.openzen.zenscript.codemodel.statement.LoopStatement;
import org.openzen.zenscript.codemodel.type.TypeID;
import org.openzen.zenscript.codemodel.type.member.LocalMemberCache;
import org.openzen.zenscript.codemodel.type.member.TypeMemberPreparer;

public class FunctionScope extends StatementScope {
   private final BaseScope outer;
   private final FunctionHeader header;
   private final GenericMapper typeParameterMap;
   private final TypeID thisType;
   private final BaseScope.DollarEvaluator dollar;

   public FunctionScope(CodePosition position, BaseScope outer, FunctionHeader header) {
      this(position, outer, header, null);
   }

   public FunctionScope(CodePosition position, BaseScope outer, FunctionHeader header, BaseScope.DollarEvaluator dollar) {
      this.outer = outer;
      this.header = header;
      this.thisType = outer.getThisType();
      this.dollar = dollar;
      if (outer.getLocalTypeParameters() == null) {
         throw new NullPointerException();
      }

      if (header == null) {
         throw new NullPointerException();
      }

      this.typeParameterMap = outer.getLocalTypeParameters().getInner(position, outer.getTypeRegistry(), header.typeParameters);
   }

   @Override
   public ZSPackage getRootPackage() {
      return this.outer.getRootPackage();
   }

   @Override
   public LocalMemberCache getMemberCache() {
      return this.outer.getMemberCache();
   }

   @Override
   public LoopStatement getLoop(String name) {
      return null;
   }

   @Override
   public FunctionHeader getFunctionHeader() {
      return this.header;
   }

   @Override
   public IPartialExpression get(CodePosition position, GenericName name) throws CompileException {
      IPartialExpression fromSuper = super.get(position, name);
      if (fromSuper != null) {
         return fromSuper;
      }

      if (name.hasNoArguments()) {
         for (FunctionParameter parameter : this.header.parameters) {
            if (parameter.name.equals(name.name)) {
               return new GetFunctionParameterExpression(position, parameter);
            }
         }

         if (this.header.typeParameters != null) {
            for (TypeParameter parameter : this.header.typeParameters) {
               if (parameter.name.equals(name.name)) {
                  return new PartialTypeExpression(position, this.getTypeRegistry().getGeneric(parameter), name.arguments);
               }
            }
         }
      }

      return this.outer.get(position, name);
   }

   @Override
   public TypeID getType(CodePosition position, List<GenericName> name) {
      if (name.size() == 1 && name.get(0).hasNoArguments() && this.header.typeParameters != null) {
         for (TypeParameter parameter : this.header.typeParameters) {
            if (parameter.name.equals(name.get(0).name)) {
               return this.getTypeRegistry().getGeneric(parameter);
            }
         }
      }

      return this.outer.getType(position, name);
   }

   @Override
   public TypeID getThisType() {
      return this.thisType;
   }

   @Override
   public BaseScope.DollarEvaluator getDollar() {
      return this.dollar;
   }

   @Override
   public IPartialExpression getOuterInstance(CodePosition position) throws CompileException {
      return this.outer.getOuterInstance(position);
   }

   @Override
   public AnnotationDefinition getAnnotation(String name) {
      return this.outer.getAnnotation(name);
   }

   @Override
   public TypeMemberPreparer getPreparer() {
      return this.outer.getPreparer();
   }

   @Override
   public GenericMapper getLocalTypeParameters() {
      return this.typeParameterMap;
   }
}
