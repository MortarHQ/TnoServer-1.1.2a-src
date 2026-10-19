package org.openzen.zenscript.codemodel.scope;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zencode.shared.CompileExceptionCode;
import org.openzen.zenscript.codemodel.FunctionHeader;
import org.openzen.zenscript.codemodel.GenericMapper;
import org.openzen.zenscript.codemodel.GenericName;
import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.codemodel.annotations.AnnotationDefinition;
import org.openzen.zenscript.codemodel.definition.ExpansionDefinition;
import org.openzen.zenscript.codemodel.definition.ZSPackage;
import org.openzen.zenscript.codemodel.expression.InvalidExpression;
import org.openzen.zenscript.codemodel.expression.ThisExpression;
import org.openzen.zenscript.codemodel.generic.TypeParameter;
import org.openzen.zenscript.codemodel.partial.IPartialExpression;
import org.openzen.zenscript.codemodel.partial.PartialTypeExpression;
import org.openzen.zenscript.codemodel.statement.LoopStatement;
import org.openzen.zenscript.codemodel.type.DefinitionTypeID;
import org.openzen.zenscript.codemodel.type.TypeID;
import org.openzen.zenscript.codemodel.type.member.LocalMemberCache;
import org.openzen.zenscript.codemodel.type.member.TypeMemberPreparer;
import org.openzen.zenscript.codemodel.type.member.TypeMembers;

public class DefinitionScope extends BaseScope {
   private final BaseScope outer;
   private final HighLevelDefinition definition;
   private final TypeID type;
   private final TypeMembers members;
   private final TypeParameter[] typeParameters;
   private final GenericMapper typeParameterMap;

   public DefinitionScope(BaseScope outer, HighLevelDefinition definition) {
      this(outer, definition, true);
   }

   public DefinitionScope(BaseScope outer, HighLevelDefinition definition, boolean withMembers) {
      this.outer = outer;
      this.definition = definition;
      Map<TypeParameter, TypeID> typeParameters = new HashMap<>();
      if (definition instanceof ExpansionDefinition expansion) {
         this.type = expansion.target;
         this.typeParameters = expansion.typeParameters;
         typeParameters = TypeID.getSelfMapping(outer.getTypeRegistry(), expansion.typeParameters);
      } else {
         DefinitionTypeID definitionType = outer.getTypeRegistry().getForMyDefinition(definition);
         this.type = definitionType;
         List<TypeParameter> typeParameterList = new ArrayList<>();

         while (definitionType != null) {
            typeParameters = TypeID.getSelfMapping(outer.getTypeRegistry(), definitionType.definition.typeParameters);
            typeParameterList.addAll(Arrays.asList(definitionType.definition.typeParameters));
            definitionType = definitionType.definition.isStatic() ? null : definitionType.outer;
         }

         this.typeParameters = typeParameterList.toArray(new TypeParameter[typeParameterList.size()]);
      }

      this.members = withMembers ? outer.getMemberCache().get(this.type) : null;
      this.typeParameterMap = outer.getLocalTypeParameters().getInner(definition.position, this.getTypeRegistry(), typeParameters);
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
   public IPartialExpression get(CodePosition position, GenericName name) throws CompileException {
      if (this.members != null) {
         if (this.members.hasInnerType(name.name)) {
            return new PartialTypeExpression(position, this.members.getInnerType(position, name), name.arguments);
         }

         if (this.members.hasMember(name.name) && !name.hasArguments()) {
            return this.members.getMemberExpression(position, this, new ThisExpression(position, this.type), name, true);
         }
      }

      if (!name.hasArguments()) {
         for (TypeParameter parameter : this.typeParameters) {
            if (parameter.name.equals(name.name)) {
               return new PartialTypeExpression(position, this.getTypeRegistry().getGeneric(parameter), name.arguments);
            }
         }
      }

      return this.outer.get(position, name);
   }

   @Override
   public TypeID getType(CodePosition position, List<GenericName> name) {
      if (this.members != null && this.members.hasInnerType(name.get(0).name)) {
         TypeID result = this.members.getInnerType(position, name.get(0));

         for (int i = 1; i < name.size(); i++) {
            result = this.getTypeMembers(result).getInnerType(position, name.get(i));
         }

         return result;
      } else {
         if (name.size() == 1 && !name.get(0).hasArguments()) {
            for (TypeParameter parameter : this.typeParameters) {
               if (parameter.name.equals(name.get(0).name)) {
                  return this.getTypeRegistry().getGeneric(parameter);
               }
            }
         }

         return this.outer.getType(position, name);
      }
   }

   @Override
   public LoopStatement getLoop(String name) {
      return null;
   }

   @Override
   public FunctionHeader getFunctionHeader() {
      return null;
   }

   @Override
   public TypeID getThisType() {
      return this.type;
   }

   @Override
   public BaseScope.DollarEvaluator getDollar() {
      return this.outer.getDollar();
   }

   @Override
   public IPartialExpression getOuterInstance(CodePosition position) throws CompileException {
      if (!this.definition.isInnerDefinition()) {
         throw new CompileException(position, CompileExceptionCode.NO_OUTER_BECAUSE_NOT_INNER, "Type is not an inner type; cannot access outer type");
      } else if (this.definition.isStatic()) {
         return new InvalidExpression(
            position, this.outer.getThisType(), CompileExceptionCode.NO_OUTER_BECAUSE_STATIC, "Inner type is static; cannot access outer type reference"
         );
      } else {
         throw new UnsupportedOperationException("not yet supported");
      }
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
