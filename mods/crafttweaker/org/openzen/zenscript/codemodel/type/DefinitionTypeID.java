package org.openzen.zenscript.codemodel.type;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.codemodel.GenericMapper;
import org.openzen.zenscript.codemodel.GenericName;
import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.codemodel.definition.AliasDefinition;
import org.openzen.zenscript.codemodel.definition.EnumDefinition;
import org.openzen.zenscript.codemodel.definition.StructDefinition;
import org.openzen.zenscript.codemodel.definition.VariantDefinition;
import org.openzen.zenscript.codemodel.expression.Expression;
import org.openzen.zenscript.codemodel.expression.SubtypeCastExpression;
import org.openzen.zenscript.codemodel.generic.TypeParameter;
import org.openzen.zenscript.codemodel.member.IDefinitionMember;
import org.openzen.zenscript.codemodel.member.ImplementationMember;

public class DefinitionTypeID implements TypeID {
   public final HighLevelDefinition definition;
   public final TypeID[] typeArguments;
   public final DefinitionTypeID outer;
   private TypeID normalized;

   public DefinitionTypeID(GlobalTypeRegistry typeRegistry, HighLevelDefinition definition, TypeID[] typeArguments) {
      this(typeRegistry, definition, typeArguments, null);
   }

   public DefinitionTypeID(GlobalTypeRegistry typeRegistry, HighLevelDefinition definition, TypeID[] typeArguments, DefinitionTypeID outer) {
      if (typeArguments == null) {
         throw new NullPointerException("typeParameters cannot be null");
      }

      if (typeArguments.length != definition.getNumberOfGenericParameters()) {
         throw new IllegalArgumentException(
            "Wrong number of type parameters! " + definition.name + " expected: " + definition.getNumberOfGenericParameters() + " got: " + typeArguments.length
         );
      }

      if (definition.isInnerDefinition() && !definition.isStatic() && outer == null) {
         throw new IllegalArgumentException("Inner definition requires outer instance");
      }

      if ((!definition.isInnerDefinition() || definition.isStatic()) && outer != null) {
         throw new IllegalArgumentException("Static inner definition must not have outer instance");
      }

      this.definition = definition;
      this.typeArguments = typeArguments;
      this.outer = outer;
      this.normalized = this.isDenormalized() ? this.normalize(typeRegistry) : this;
      if (this.normalized instanceof DefinitionTypeID && ((DefinitionTypeID)this.normalized).isDenormalized()) {
         throw new AssertionError();
      }
   }

   public DefinitionTypeID(HighLevelDefinition definition) {
      this.definition = definition;
      this.typeArguments = TypeID.NONE;
      this.outer = null;
   }

   private boolean isDenormalized() {
      if (this.definition instanceof AliasDefinition) {
         return true;
      }

      for (TypeID typeArgument : this.typeArguments) {
         if (!typeArgument.getNormalized().equals(typeArgument)) {
            return true;
         }
      }

      return this.outer != null && !this.outer.getNormalized().equals(this.outer);
   }

   private TypeID normalize(GlobalTypeRegistry typeRegistry) {
      if (this.definition instanceof AliasDefinition alias) {
         if (alias.type == null) {
            throw new IllegalStateException("Alias type not yet initialized!");
         }

         Map<TypeParameter, TypeID> typeMapping = new HashMap<>();

         for (int i = 0; i < this.definition.typeParameters.length; i++) {
            typeMapping.put(this.definition.typeParameters[i], this.typeArguments[i].getNormalized());
         }

         GenericMapper mapper = new GenericMapper(this.definition.position, typeRegistry, typeMapping);
         return alias.type.instance(mapper).getNormalized();
      } else {
         TypeID[] normalizedTypeParameters = new TypeID[this.typeArguments.length];

         for (int i = 0; i < normalizedTypeParameters.length; i++) {
            normalizedTypeParameters[i] = this.typeArguments[i].getNormalized();
         }

         return typeRegistry.getForDefinition(
            this.definition, normalizedTypeParameters, this.outer == null ? null : (DefinitionTypeID)this.outer.getNormalized()
         );
      }
   }

   public boolean hasTypeParameters() {
      return this.typeArguments.length > 0;
   }

   public Map<TypeParameter, TypeID> getTypeParameterMapping() {
      Map<TypeParameter, TypeID> mapping = new HashMap<>();
      DefinitionTypeID current = this;

      do {
         if (current.typeArguments != null && current.definition.typeParameters != null) {
            for (int i = 0; i < current.typeArguments.length; i++) {
               mapping.put(current.definition.typeParameters[i], current.typeArguments[i]);
            }
         }

         current = current.outer;
      } while (current != null && !current.definition.isStatic());

      return mapping;
   }

   @Override
   public TypeID getNormalized() {
      return this.normalized;
   }

   @Override
   public TypeID instance(GenericMapper mapper) {
      if (!this.hasTypeParameters() && this.outer == null) {
         return this;
      }

      if (mapper == null || mapper.getMapping().isEmpty()) {
         return this;
      }

      if (mapper.registry == null) {
         throw new NullPointerException();
      }

      TypeID[] instancedArguments = TypeID.NONE;
      if (this.hasTypeParameters()) {
         instancedArguments = new TypeID[this.typeArguments.length];

         for (int i = 0; i < this.typeArguments.length; i++) {
            instancedArguments[i] = this.typeArguments[i].instance(mapper);
         }
      }

      DefinitionTypeID instancedOuter = this.outer == null ? null : (DefinitionTypeID)this.outer.instance(mapper);
      return mapper.registry.getForDefinition(this.definition, instancedArguments, instancedOuter);
   }

   @Override
   public TypeID getSuperType(GlobalTypeRegistry registry) {
      return this.definition.getSuperType() == null
         ? null
         : this.definition.getSuperType().instance(new GenericMapper(this.definition.position, registry, this.getTypeParameterMapping()));
   }

   @Override
   public <R> R accept(TypeVisitor<R> visitor) {
      return visitor.visitDefinition(this);
   }

   @Override
   public <C, R, E extends Exception> R accept(C context, TypeVisitorWithContext<C, R, E> visitor) throws E {
      return visitor.visitDefinition(context, this);
   }

   @Override
   public boolean isOptional() {
      return false;
   }

   @Override
   public boolean isValueType() {
      return this.definition instanceof StructDefinition || this.definition instanceof EnumDefinition;
   }

   @Override
   public boolean isEnum() {
      return this.definition instanceof EnumDefinition;
   }

   @Override
   public boolean isVariant() {
      return this.definition instanceof VariantDefinition;
   }

   @Override
   public boolean isDefinition(HighLevelDefinition definition) {
      return this.definition.equals(definition);
   }

   @Override
   public int hashCode() {
      int hash = 7;
      hash = 97 * hash + this.definition.hashCode();
      hash = 97 * hash + Arrays.deepHashCode(this.typeArguments);
      return 97 * hash + Objects.hashCode(this.outer);
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      }

      if (obj == null) {
         return false;
      }

      if (this.getClass() != obj.getClass()) {
         return false;
      }

      DefinitionTypeID other = (DefinitionTypeID)obj;
      return this.definition == other.definition && Arrays.deepEquals(this.typeArguments, other.typeArguments) && Objects.equals(this.outer, this.outer);
   }

   @Override
   public String toString() {
      if (!this.hasTypeParameters() && this.outer == null) {
         return this.definition.name;
      }

      StringBuilder result = new StringBuilder();
      if (this.outer != null) {
         result.append(this.outer.toString()).append('.');
      }

      result.append(this.definition.name);
      result.append('<');

      for (int i = 0; i < this.typeArguments.length; i++) {
         if (i > 0) {
            result.append(", ");
         }

         result.append(this.typeArguments[i].toString());
      }

      result.append('>');
      return result.toString();
   }

   @Override
   public boolean hasDefaultValue() {
      return this.definition.hasEmptyConstructor();
   }

   @Override
   public void extractTypeParameters(List<TypeParameter> typeParameters) {
      for (TypeID type : this.typeArguments) {
         type.extractTypeParameters(typeParameters);
      }
   }

   public DefinitionTypeID getInnerType(GenericName name, GlobalTypeRegistry registry) {
      HighLevelDefinition type = this.definition.getInnerType(name.name);
      return registry.getForDefinition(type, name.arguments, this);
   }

   @Override
   public boolean canCastImplicitFrom(TypeID other) {
      if (!(other instanceof DefinitionTypeID)) {
         return false;
      }

      if (this.definition.isSubclassOf(((DefinitionTypeID)other).definition)) {
         return true;
      }

      Predicate<IDefinitionMember> search = member -> member instanceof ImplementationMember && ((ImplementationMember)member).type == other;
      return this.searchSuperTypes(this, search) || this.searchImplementationMembers(this, search);
   }

   private boolean searchSuperTypes(DefinitionTypeID type, Predicate<IDefinitionMember> predicate) {
      if (!(type.definition.getSuperType() instanceof DefinitionTypeID definitionSuperType)) {
         return false;
      } else {
         boolean found = definitionSuperType.definition.members.stream().anyMatch(predicate);
         return found || this.searchSuperTypes(definitionSuperType, predicate);
      }
   }

   private boolean searchImplementationMembers(DefinitionTypeID type, Predicate<IDefinitionMember> predicate) {
      for (IDefinitionMember member : type.definition.members) {
         if (member instanceof ImplementationMember implMember) {
            if (predicate.test(implMember)) {
               return true;
            }

            if (implMember.type instanceof DefinitionTypeID && this.searchImplementationMembers((DefinitionTypeID)implMember.type, predicate)) {
               return true;
            }
         }
      }

      return false;
   }

   @Override
   public Expression castImplicitFrom(CodePosition position, Expression value) {
      return new SubtypeCastExpression(position, value, this);
   }
}
