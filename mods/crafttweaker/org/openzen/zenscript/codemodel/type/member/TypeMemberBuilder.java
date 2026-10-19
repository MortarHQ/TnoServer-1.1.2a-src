package org.openzen.zenscript.codemodel.type.member;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.codemodel.FunctionHeader;
import org.openzen.zenscript.codemodel.FunctionParameter;
import org.openzen.zenscript.codemodel.GenericMapper;
import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.codemodel.Module;
import org.openzen.zenscript.codemodel.OperatorType;
import org.openzen.zenscript.codemodel.definition.ClassDefinition;
import org.openzen.zenscript.codemodel.definition.EnumDefinition;
import org.openzen.zenscript.codemodel.definition.ExpansionDefinition;
import org.openzen.zenscript.codemodel.definition.FunctionDefinition;
import org.openzen.zenscript.codemodel.definition.InterfaceDefinition;
import org.openzen.zenscript.codemodel.definition.StructDefinition;
import org.openzen.zenscript.codemodel.definition.VariantDefinition;
import org.openzen.zenscript.codemodel.expression.ArrayExpression;
import org.openzen.zenscript.codemodel.expression.CallArguments;
import org.openzen.zenscript.codemodel.expression.ConstantByteExpression;
import org.openzen.zenscript.codemodel.expression.ConstantDoubleExpression;
import org.openzen.zenscript.codemodel.expression.ConstantFloatExpression;
import org.openzen.zenscript.codemodel.expression.ConstantIntExpression;
import org.openzen.zenscript.codemodel.expression.ConstantLongExpression;
import org.openzen.zenscript.codemodel.expression.ConstantSByteExpression;
import org.openzen.zenscript.codemodel.expression.ConstantShortExpression;
import org.openzen.zenscript.codemodel.expression.ConstantUIntExpression;
import org.openzen.zenscript.codemodel.expression.ConstantULongExpression;
import org.openzen.zenscript.codemodel.expression.ConstantUShortExpression;
import org.openzen.zenscript.codemodel.expression.ConstantUSizeExpression;
import org.openzen.zenscript.codemodel.expression.EnumConstantExpression;
import org.openzen.zenscript.codemodel.expression.Expression;
import org.openzen.zenscript.codemodel.generic.TypeParameter;
import org.openzen.zenscript.codemodel.generic.TypeParameterBound;
import org.openzen.zenscript.codemodel.member.CallerMember;
import org.openzen.zenscript.codemodel.member.CasterMember;
import org.openzen.zenscript.codemodel.member.ConstMember;
import org.openzen.zenscript.codemodel.member.ConstructorMember;
import org.openzen.zenscript.codemodel.member.EnumConstantMember;
import org.openzen.zenscript.codemodel.member.FieldMember;
import org.openzen.zenscript.codemodel.member.GetterMember;
import org.openzen.zenscript.codemodel.member.IDefinitionMember;
import org.openzen.zenscript.codemodel.member.IteratorMember;
import org.openzen.zenscript.codemodel.member.MethodMember;
import org.openzen.zenscript.codemodel.member.OperatorMember;
import org.openzen.zenscript.codemodel.member.ref.CasterMemberRef;
import org.openzen.zenscript.codemodel.member.ref.TranslatedOperatorMemberRef;
import org.openzen.zenscript.codemodel.type.ArrayTypeID;
import org.openzen.zenscript.codemodel.type.AssocTypeID;
import org.openzen.zenscript.codemodel.type.BasicTypeID;
import org.openzen.zenscript.codemodel.type.DefinitionTypeID;
import org.openzen.zenscript.codemodel.type.FunctionTypeID;
import org.openzen.zenscript.codemodel.type.GenericMapTypeID;
import org.openzen.zenscript.codemodel.type.GenericTypeID;
import org.openzen.zenscript.codemodel.type.GlobalTypeRegistry;
import org.openzen.zenscript.codemodel.type.InvalidTypeID;
import org.openzen.zenscript.codemodel.type.IteratorTypeID;
import org.openzen.zenscript.codemodel.type.OptionalTypeID;
import org.openzen.zenscript.codemodel.type.RangeTypeID;
import org.openzen.zenscript.codemodel.type.TypeID;
import org.openzen.zenscript.codemodel.type.TypeVisitorWithContext;

public class TypeMemberBuilder implements TypeVisitorWithContext<Void, Void, RuntimeException> {
   private final GlobalTypeRegistry registry;
   private final TypeMembers members;
   private final LocalMemberCache cache;
   private final TypeID type;

   public TypeMemberBuilder(GlobalTypeRegistry registry, TypeMembers members, LocalMemberCache cache) {
      this.registry = registry;
      this.members = members;
      this.cache = cache;
      this.type = members.type;
   }

   private void processType(HighLevelDefinition definition) {
      for (ExpansionDefinition expansion : this.cache.getExpansions()) {
         if (expansion.target == null) {
            throw new RuntimeException(expansion.position.toString() + ": Missing expansion target");
         }

         Map<TypeParameter, TypeID> mapping = this.matchType(this.type, expansion.target);
         if (mapping != null) {
            GenericMapper mapper = new GenericMapper(definition.position, this.registry, mapping);

            for (IDefinitionMember member : expansion.members) {
               member.registerTo(this.members, TypeMemberPriority.SPECIFIED, mapper);
            }
         }
      }
   }

   private Map<TypeParameter, TypeID> matchType(TypeID type, TypeID pattern) {
      return type.inferTypeParameters(this.cache, pattern);
   }

   public Void visitBasic(Void context, BasicTypeID basic) {
      switch (basic) {
         case BOOL:
            this.visitBool();
            break;
         case BYTE:
            this.visitByte();
            break;
         case SBYTE:
            this.visitSByte();
            break;
         case SHORT:
            this.visitShort();
            break;
         case USHORT:
            this.visitUShort();
            break;
         case INT:
            this.visitInt();
            break;
         case UINT:
            this.visitUInt();
            break;
         case LONG:
            this.visitLong();
            break;
         case ULONG:
            this.visitULong();
            break;
         case USIZE:
            this.visitUSize();
            break;
         case FLOAT:
            this.visitFloat();
            break;
         case DOUBLE:
            this.visitDouble();
            break;
         case CHAR:
            this.visitChar();
            break;
         case STRING:
            this.visitString();
      }

      return null;
   }

   private void visitString() {
      ClassDefinition builtin = new ClassDefinition(CodePosition.BUILTIN, Module.BUILTIN, null, "string", 1, null);
      this.constructor(builtin, BuiltinID.STRING_CONSTRUCTOR_CHARACTERS, this.registry.getOptional(this.registry.getArray(BasicTypeID.CHAR, 1)));
      this.add(builtin, BuiltinID.STRING_ADD_STRING, BasicTypeID.STRING, BasicTypeID.STRING);
      this.indexGet(builtin, BuiltinID.STRING_INDEXGET, BasicTypeID.USIZE, BasicTypeID.CHAR);
      this.indexGet(builtin, BuiltinID.STRING_RANGEGET, RangeTypeID.USIZE, BasicTypeID.STRING);
      this.compare(builtin, BuiltinID.STRING_COMPARE, BasicTypeID.STRING);
      this.getter(builtin, BuiltinID.STRING_LENGTH, "length", BasicTypeID.USIZE);
      this.getter(builtin, BuiltinID.STRING_CHARACTERS, "characters", this.registry.getArray(BasicTypeID.CHAR, 1));
      this.getter(builtin, BuiltinID.STRING_CHARACTERS, "chars", this.registry.getArray(BasicTypeID.CHAR, 1));
      this.getter(builtin, BuiltinID.STRING_ISEMPTY, "isEmpty", BasicTypeID.BOOL);
      this.getter(builtin, BuiltinID.STRING_ISEMPTY, "empty", BasicTypeID.BOOL);
      this.method(builtin, BuiltinID.STRING_REMOVE_DIACRITICS, "removeDiacritics", BasicTypeID.STRING);
      this.method(builtin, BuiltinID.STRING_TRIM, "trim", BasicTypeID.STRING, BasicTypeID.STRING);
      this.method(builtin, BuiltinID.STRING_TO_LOWER_CASE, "toLowerCase", BasicTypeID.STRING);
      this.method(builtin, BuiltinID.STRING_TO_UPPER_CASE, "toUpperCase", BasicTypeID.STRING);
      this.iterator(builtin, BuiltinID.ITERATOR_STRING_CHARS, BasicTypeID.CHAR);
      this.processType(builtin);
   }

   public Void visitArray(Void context, ArrayTypeID array) {
      HighLevelDefinition definition = new ClassDefinition(CodePosition.BUILTIN, Module.BUILTIN, null, "", 1);
      TypeID baseType = array.elementType;
      int dimension = array.dimension;
      FunctionParameter[] indexGetParameters = new FunctionParameter[dimension];

      for (int i = 0; i < indexGetParameters.length; i++) {
         indexGetParameters[i] = new FunctionParameter(BasicTypeID.USIZE);
      }

      this.operator(definition, OperatorType.INDEXGET, new FunctionHeader(baseType, indexGetParameters), BuiltinID.ARRAY_INDEXGET);
      if (dimension == 1) {
         FunctionHeader sliceHeader = new FunctionHeader(this.type, new FunctionParameter(RangeTypeID.USIZE, "range"));
         this.operator(definition, OperatorType.INDEXGET, sliceHeader, BuiltinID.ARRAY_INDEXGETRANGE);
         if (baseType == BasicTypeID.BYTE) {
            this.castImplicit(definition, BuiltinID.BYTE_ARRAY_AS_SBYTE_ARRAY, this.registry.getArray(BasicTypeID.SBYTE, 1));
         }

         if (baseType == BasicTypeID.SBYTE) {
            this.castImplicit(definition, BuiltinID.SBYTE_ARRAY_AS_BYTE_ARRAY, this.registry.getArray(BasicTypeID.BYTE, 1));
         }

         if (baseType == BasicTypeID.SHORT) {
            this.castImplicit(definition, BuiltinID.SHORT_ARRAY_AS_USHORT_ARRAY, this.registry.getArray(BasicTypeID.USHORT, 1));
         }

         if (baseType == BasicTypeID.USHORT) {
            this.castImplicit(definition, BuiltinID.USHORT_ARRAY_AS_SHORT_ARRAY, this.registry.getArray(BasicTypeID.SHORT, 1));
         }

         if (baseType == BasicTypeID.INT) {
            this.castImplicit(definition, BuiltinID.INT_ARRAY_AS_UINT_ARRAY, this.registry.getArray(BasicTypeID.UINT, 1));
         }

         if (baseType == BasicTypeID.UINT) {
            this.castImplicit(definition, BuiltinID.UINT_ARRAY_AS_INT_ARRAY, this.registry.getArray(BasicTypeID.INT, 1));
         }

         if (baseType == BasicTypeID.LONG) {
            this.castImplicit(definition, BuiltinID.LONG_ARRAY_AS_ULONG_ARRAY, this.registry.getArray(BasicTypeID.ULONG, 1));
         }

         if (baseType == BasicTypeID.ULONG) {
            this.castImplicit(definition, BuiltinID.ULONG_ARRAY_AS_LONG_ARRAY, this.registry.getArray(BasicTypeID.LONG, 1));
         }
      }

      FunctionHeader containsHeader = new FunctionHeader(BasicTypeID.BOOL, new FunctionParameter(baseType, "value"));
      this.operator(definition, OperatorType.CONTAINS, containsHeader, BuiltinID.ARRAY_CONTAINS);
      if (baseType.hasDefaultValue()) {
         this.members
            .addConstructor(
               new ConstructorMember(
                     CodePosition.BUILTIN, definition, 1, new FunctionHeader(BasicTypeID.VOID, indexGetParameters), BuiltinID.ARRAY_CONSTRUCTOR_SIZED
                  )
                  .ref(this.type)
            );
      }

      FunctionParameter[] initialValueConstructorParameters = new FunctionParameter[dimension + 1];

      for (int i = 0; i < dimension; i++) {
         initialValueConstructorParameters[i] = new FunctionParameter(BasicTypeID.USIZE);
      }

      initialValueConstructorParameters[dimension] = new FunctionParameter(baseType);
      FunctionHeader initialValueConstructorHeader = new FunctionHeader(BasicTypeID.VOID, initialValueConstructorParameters);
      new ConstructorMember(CodePosition.BUILTIN, definition, 1, initialValueConstructorHeader, BuiltinID.ARRAY_CONSTRUCTOR_INITIAL_VALUE)
         .registerTo(this.members, TypeMemberPriority.SPECIFIED, null);
      FunctionParameter[] lambdaConstructorParameters = new FunctionParameter[dimension + 1];

      for (int i = 0; i < dimension; i++) {
         lambdaConstructorParameters[i] = new FunctionParameter(BasicTypeID.USIZE);
      }

      FunctionHeader lambdaConstructorFunction = new FunctionHeader(baseType, indexGetParameters);
      lambdaConstructorParameters[dimension] = new FunctionParameter(this.cache.getRegistry().getFunction(lambdaConstructorFunction));
      FunctionHeader lambdaConstructorHeader = new FunctionHeader(BasicTypeID.VOID, lambdaConstructorParameters);
      this.members
         .addConstructor(new ConstructorMember(CodePosition.BUILTIN, definition, 1, lambdaConstructorHeader, BuiltinID.ARRAY_CONSTRUCTOR_LAMBDA).ref(this.type));
      TypeParameter mappedConstructorParameter = new TypeParameter(CodePosition.BUILTIN, "T");
      FunctionHeader mappedConstructorHeaderWithoutIndex = new FunctionHeader(baseType, this.registry.getGeneric(mappedConstructorParameter));
      FunctionHeader mappedConstructorFunctionWithoutIndex = new FunctionHeader(
         new TypeParameter[]{mappedConstructorParameter},
         BasicTypeID.VOID,
         null,
         new FunctionParameter(this.registry.getArray(this.registry.getGeneric(mappedConstructorParameter), dimension), "original"),
         new FunctionParameter(this.registry.getFunction(mappedConstructorHeaderWithoutIndex), "projection")
      );
      this.members
         .addConstructor(
            new ConstructorMember(CodePosition.BUILTIN, definition, 1, mappedConstructorFunctionWithoutIndex, BuiltinID.ARRAY_CONSTRUCTOR_PROJECTED)
               .ref(this.type)
         );
      mappedConstructorParameter = new TypeParameter(CodePosition.BUILTIN, "T");
      FunctionParameter[] projectionParameters = new FunctionParameter[dimension + 1];

      for (int i = 0; i < dimension; i++) {
         projectionParameters[i] = new FunctionParameter(BasicTypeID.USIZE);
      }

      projectionParameters[dimension] = new FunctionParameter(this.registry.getGeneric(mappedConstructorParameter));
      mappedConstructorFunctionWithoutIndex = new FunctionHeader(baseType, projectionParameters);
      FunctionHeader mappedConstructorFunctionWithIndex = new FunctionHeader(
         new TypeParameter[]{mappedConstructorParameter},
         BasicTypeID.VOID,
         null,
         new FunctionParameter(this.registry.getArray(this.registry.getGeneric(mappedConstructorParameter), dimension), "original"),
         new FunctionParameter(this.registry.getFunction(mappedConstructorFunctionWithoutIndex), "projection")
      );
      this.constructor(definition, BuiltinID.ARRAY_CONSTRUCTOR_PROJECTED_INDEXED, mappedConstructorFunctionWithIndex);
      FunctionParameter[] indexSetParameters = new FunctionParameter[dimension + 1];

      for (int i = 0; i < dimension; i++) {
         indexSetParameters[i] = new FunctionParameter(BasicTypeID.USIZE);
      }

      indexSetParameters[dimension] = new FunctionParameter(baseType);
      mappedConstructorHeaderWithoutIndex = new FunctionHeader(BasicTypeID.VOID, indexSetParameters);
      this.operator(definition, OperatorType.INDEXSET, mappedConstructorHeaderWithoutIndex, BuiltinID.ARRAY_INDEXSET);
      if (dimension == 1) {
         this.getter(definition, BuiltinID.ARRAY_LENGTH, "length", BasicTypeID.USIZE);
      }

      this.getter(definition, BuiltinID.ARRAY_ISEMPTY, "isEmpty", BasicTypeID.BOOL);
      this.getter(definition, BuiltinID.ARRAY_HASHCODE, "objectHashCode", BasicTypeID.INT);
      this.iterator(definition, BuiltinID.ITERATOR_ARRAY_VALUES, baseType);
      this.iterator(definition, BuiltinID.ITERATOR_ARRAY_KEY_VALUES, BasicTypeID.USIZE, baseType);
      this.equals(definition, BuiltinID.ARRAY_EQUALS, this.type);
      this.notequals(definition, BuiltinID.ARRAY_NOTEQUALS, this.type);
      this.same(definition, BuiltinID.ARRAY_SAME, this.type);
      this.notsame(definition, BuiltinID.ARRAY_NOTSAME, this.type);
      this.processType(definition);
      return null;
   }

   public Void visitAssoc(Void context, AssocTypeID assoc) {
      TypeID keyType = assoc.keyType;
      TypeID valueType = assoc.valueType;
      ClassDefinition builtin = new ClassDefinition(CodePosition.BUILTIN, Module.BUILTIN, null, "", 1);
      this.constructor(builtin, BuiltinID.ASSOC_CONSTRUCTOR);
      this.indexGet(builtin, BuiltinID.ASSOC_INDEXGET, keyType, valueType);
      this.indexSet(builtin, BuiltinID.ASSOC_INDEXSET, keyType, valueType);
      this.method(builtin, BuiltinID.ASSOC_GETORDEFAULT, "getOrDefault", valueType, keyType, valueType);
      this.operator(builtin, OperatorType.CONTAINS, new FunctionHeader(BasicTypeID.BOOL, new FunctionParameter(keyType, "key")), BuiltinID.ASSOC_CONTAINS);
      this.getter(builtin, BuiltinID.ASSOC_SIZE, "size", BasicTypeID.USIZE);
      this.getter(builtin, BuiltinID.ASSOC_ISEMPTY, "isEmpty", BasicTypeID.BOOL);
      this.getter(builtin, BuiltinID.ASSOC_KEYS, "keys", this.cache.getRegistry().getArray(keyType, 1));
      this.getter(builtin, BuiltinID.ASSOC_VALUES, "values", this.cache.getRegistry().getArray(valueType, 1));
      this.getter(builtin, BuiltinID.ASSOC_HASHCODE, "objectHashCode", BasicTypeID.INT);
      this.iterator(builtin, BuiltinID.ITERATOR_ASSOC_KEYS, keyType);
      this.iterator(builtin, BuiltinID.ITERATOR_ASSOC_KEY_VALUES, keyType, valueType);
      this.equals(builtin, BuiltinID.ASSOC_EQUALS, this.type);
      this.notequals(builtin, BuiltinID.ASSOC_NOTEQUALS, this.type);
      this.same(builtin, BuiltinID.ASSOC_SAME, this.type);
      this.notsame(builtin, BuiltinID.ASSOC_NOTSAME, this.type);
      this.processType(builtin);
      return null;
   }

   public Void visitGenericMap(Void context, GenericMapTypeID map) {
      TypeParameter functionParameter = new TypeParameter(CodePosition.BUILTIN, "T");
      Map<TypeParameter, TypeID> parameterFilled = Collections.singletonMap(map.key, this.registry.getGeneric(functionParameter));
      TypeID valueType = map.value.instance(new GenericMapper(CodePosition.BUILTIN, this.registry, parameterFilled));
      FunctionHeader getOptionalHeader = new FunctionHeader(
         new TypeParameter[]{functionParameter}, this.registry.getOptional(valueType), null, FunctionParameter.NONE
      );
      FunctionHeader putHeader = new FunctionHeader(new TypeParameter[]{functionParameter}, BasicTypeID.VOID, null, new FunctionParameter(valueType));
      FunctionHeader containsHeader = new FunctionHeader(new TypeParameter[]{functionParameter}, BasicTypeID.BOOL, null, FunctionParameter.NONE);
      ClassDefinition builtin = new ClassDefinition(CodePosition.BUILTIN, Module.BUILTIN, null, "", 1);
      this.constructor(builtin, BuiltinID.GENERICMAP_CONSTRUCTOR);
      this.method(builtin, "getOptional", getOptionalHeader, BuiltinID.GENERICMAP_GETOPTIONAL);
      this.method(builtin, "put", putHeader, BuiltinID.GENERICMAP_PUT);
      this.method(builtin, "contains", containsHeader, BuiltinID.GENERICMAP_CONTAINS);
      this.method(builtin, "addAll", new FunctionHeader(BasicTypeID.VOID, this.type), BuiltinID.GENERICMAP_ADDALL);
      this.getter(builtin, BuiltinID.GENERICMAP_SIZE, "size", BasicTypeID.USIZE);
      this.getter(builtin, BuiltinID.GENERICMAP_ISEMPTY, "isEmpty", BasicTypeID.BOOL);
      this.getter(builtin, BuiltinID.GENERICMAP_HASHCODE, "objectHashCode", BasicTypeID.INT);
      this.equals(builtin, BuiltinID.GENERICMAP_EQUALS, this.type);
      this.notequals(builtin, BuiltinID.GENERICMAP_NOTEQUALS, this.type);
      this.same(builtin, BuiltinID.GENERICMAP_SAME, this.type);
      this.notsame(builtin, BuiltinID.GENERICMAP_NOTSAME, this.type);
      this.processType(builtin);
      return null;
   }

   public Void visitInvalid(Void context, InvalidTypeID invalid) {
      return null;
   }

   public Void visitIterator(Void context, IteratorTypeID iterator) {
      return null;
   }

   public Void visitFunction(Void context, FunctionTypeID function) {
      FunctionDefinition builtin = new FunctionDefinition(CodePosition.BUILTIN, Module.BUILTIN, null, "", 1, function.header, this.registry);
      new CallerMember(CodePosition.BUILTIN, builtin, 1, function.header, BuiltinID.FUNCTION_CALL).registerTo(this.members, TypeMemberPriority.SPECIFIED, null);
      this.same(builtin, BuiltinID.FUNCTION_SAME, this.type);
      this.notsame(builtin, BuiltinID.FUNCTION_NOTSAME, this.type);
      this.processType(builtin);
      return null;
   }

   public Void visitDefinition(Void context, DefinitionTypeID definitionType) {
      HighLevelDefinition definition = definitionType.definition;
      GenericMapper mapper = null;
      if (definitionType.hasTypeParameters() || definitionType.outer != null && definitionType.outer.hasTypeParameters()) {
         Map<TypeParameter, TypeID> mapping = definitionType.getTypeParameterMapping();
         mapper = new GenericMapper(CodePosition.BUILTIN, this.registry, mapping);
      }

      for (IDefinitionMember member : definition.members) {
         member.registerTo(this.members, TypeMemberPriority.SPECIFIED, mapper);
      }

      if (definition instanceof VariantDefinition variant) {
         for (VariantDefinition.Option option : variant.options) {
            this.members.addVariantOption(option.instance(this.type, mapper));
         }
      }

      if (definition instanceof EnumDefinition enumDef) {
         for (EnumConstantMember constant : enumDef.enumConstants) {
            this.members.addEnumMember(constant, TypeMemberPriority.SPECIFIED);
         }
      }

      TypeMemberGroup constructors = this.members.getOrCreateGroup(OperatorType.CONSTRUCTOR);
      if (constructors.getMethodMembers().isEmpty()) {
         if (definition instanceof ClassDefinition) {
            this.constructor(definition, BuiltinID.CLASS_DEFAULT_CONSTRUCTOR);
         } else if (definition instanceof StructDefinition) {
            this.constructor(definition, BuiltinID.STRUCT_EMPTY_CONSTRUCTOR);
            List<FieldMember> fields = definition.getFields();
            if (!fields.isEmpty()) {
               FunctionParameter[] parameters = new FunctionParameter[fields.size()];

               for (int i = 0; i < parameters.length; i++) {
                  FieldMember field = fields.get(i);
                  parameters[i] = new FunctionParameter(field.getType(), field.name, field.initializer, false);
               }

               constructors.addMethod(
                  new ConstructorMember(
                        CodePosition.BUILTIN, definition, 1, new FunctionHeader(BasicTypeID.VOID, parameters), BuiltinID.STRUCT_VALUE_CONSTRUCTOR
                     )
                     .ref(this.type),
                  TypeMemberPriority.SPECIFIED
               );
            }
         } else if (definition instanceof EnumDefinition) {
            constructors.addMethod(
               new ConstructorMember(CodePosition.BUILTIN, definition, 4, new FunctionHeader(BasicTypeID.VOID), BuiltinID.ENUM_EMPTY_CONSTRUCTOR)
                  .ref(this.type),
               TypeMemberPriority.SPECIFIED
            );
         }
      }

      if (definition instanceof EnumDefinition) {
         this.getter(definition, BuiltinID.ENUM_NAME, "name", BasicTypeID.STRING);
         this.getter(definition, BuiltinID.ENUM_ORDINAL, "ordinal", BasicTypeID.USIZE);
         List<EnumConstantMember> enumConstants = ((EnumDefinition)definition).enumConstants;
         Expression[] constValues = new Expression[enumConstants.size()];

         for (int i = 0; i < constValues.length; i++) {
            constValues[i] = new EnumConstantExpression(CodePosition.BUILTIN, definitionType, enumConstants.get(i));
         }

         this.constant(
            definition, BuiltinID.ENUM_VALUES, "values", new ArrayExpression(CodePosition.BUILTIN, constValues, this.registry.getArray(definitionType, 1))
         );
         this.compare(definition, BuiltinID.ENUM_COMPARE, this.type);
         if (!this.members.canCast(BasicTypeID.STRING)) {
            this.castImplicit(definition, BuiltinID.ENUM_TO_STRING, BasicTypeID.STRING);
         }
      }

      if (definition instanceof InterfaceDefinition interfaceDefinition) {
         for (TypeID baseType : interfaceDefinition.baseInterfaces) {
            this.cache.get(baseType.instance(mapper)).copyMembersTo(this.members, TypeMemberPriority.INHERITED);
         }
      }

      if (definition.getSuperType() != null) {
         this.cache.get(definition.getSuperType()).copyMembersTo(this.members, TypeMemberPriority.INHERITED);
      } else {
         this.getter(definition, BuiltinID.OBJECT_HASHCODE, "objectHashCode", BasicTypeID.INT);
      }

      this.same(definition, BuiltinID.OBJECT_SAME, this.type);
      this.notsame(definition, BuiltinID.OBJECT_NOTSAME, this.type);
      this.processType(definition);
      return null;
   }

   public Void visitGeneric(Void context, GenericTypeID generic) {
      TypeParameter parameter = generic.parameter;

      for (TypeParameterBound bound : parameter.bounds) {
         bound.registerMembers(this.cache, this.members);
      }

      return null;
   }

   public Void visitRange(Void context, RangeTypeID range) {
      TypeID baseType = range.baseType;
      ClassDefinition definition = new ClassDefinition(CodePosition.BUILTIN, Module.BUILTIN, null, "", 1);
      this.getter(definition, BuiltinID.RANGE_FROM, "from", baseType);
      this.getter(definition, BuiltinID.RANGE_TO, "to", baseType);
      if (baseType == BasicTypeID.BYTE
         || baseType == BasicTypeID.SBYTE
         || baseType == BasicTypeID.SHORT
         || baseType == BasicTypeID.USHORT
         || baseType == BasicTypeID.INT
         || baseType == BasicTypeID.UINT
         || baseType == BasicTypeID.LONG
         || baseType == BasicTypeID.ULONG
         || baseType == BasicTypeID.USIZE) {
         this.iterator(definition, BuiltinID.ITERATOR_INT_RANGE, baseType);
      }

      this.processType(definition);
      return null;
   }

   public Void visitOptional(Void context, OptionalTypeID modified) {
      ClassDefinition builtin = new ClassDefinition(CodePosition.BUILTIN, Module.BUILTIN, null, "modified", 1, null);
      modified.baseType.accept(context, this);
      if (modified.isOptional()) {
         this.operator(builtin, OperatorType.EQUALS, new FunctionHeader(BasicTypeID.BOOL, BasicTypeID.NULL), BuiltinID.OPTIONAL_IS_NULL);
         this.operator(builtin, OperatorType.NOTEQUALS, new FunctionHeader(BasicTypeID.BOOL, BasicTypeID.NULL), BuiltinID.OPTIONAL_IS_NOT_NULL);
         this.operator(builtin, OperatorType.SAME, new FunctionHeader(BasicTypeID.BOOL, BasicTypeID.NULL), BuiltinID.OPTIONAL_IS_NULL);
         this.operator(builtin, OperatorType.NOTSAME, new FunctionHeader(BasicTypeID.BOOL, BasicTypeID.NULL), BuiltinID.OPTIONAL_IS_NOT_NULL);
      }

      this.processType(builtin);
      return null;
   }

   private void visitBool() {
      ClassDefinition builtin = new ClassDefinition(CodePosition.BUILTIN, Module.BUILTIN, null, "bool", 1, null);
      this.not(builtin, BuiltinID.BOOL_NOT, BasicTypeID.BOOL);
      this.and(builtin, BuiltinID.BOOL_AND, BasicTypeID.BOOL, BasicTypeID.BOOL);
      this.or(builtin, BuiltinID.BOOL_OR, BasicTypeID.BOOL, BasicTypeID.BOOL);
      this.xor(builtin, BuiltinID.BOOL_XOR, BasicTypeID.BOOL, BasicTypeID.BOOL);
      this.equals(builtin, BuiltinID.BOOL_EQUALS, BasicTypeID.BOOL);
      this.notequals(builtin, BuiltinID.BOOL_NOTEQUALS, BasicTypeID.BOOL);
      this.castImplicit(builtin, BuiltinID.BOOL_TO_STRING, BasicTypeID.STRING);
      this.staticMethod(builtin, BuiltinID.BOOL_PARSE, "parse", BasicTypeID.BOOL, BasicTypeID.STRING);
      this.processType(builtin);
   }

   private void visitByte() {
      ClassDefinition builtin = new ClassDefinition(CodePosition.BUILTIN, Module.BUILTIN, null, "byte", 1, null);
      this.invert(builtin, BuiltinID.BYTE_NOT, BasicTypeID.BYTE);
      this.inc(builtin, BuiltinID.BYTE_INC, BasicTypeID.BYTE);
      this.dec(builtin, BuiltinID.BYTE_DEC, BasicTypeID.BYTE);
      this.add(builtin, BuiltinID.BYTE_ADD_BYTE, BasicTypeID.BYTE, BasicTypeID.BYTE);
      this.sub(builtin, BuiltinID.BYTE_SUB_BYTE, BasicTypeID.BYTE, BasicTypeID.BYTE);
      this.mul(builtin, BuiltinID.BYTE_MUL_BYTE, BasicTypeID.BYTE, BasicTypeID.BYTE);
      this.div(builtin, BuiltinID.BYTE_DIV_BYTE, BasicTypeID.BYTE, BasicTypeID.BYTE);
      this.mod(builtin, BuiltinID.BYTE_MOD_BYTE, BasicTypeID.BYTE, BasicTypeID.BYTE);
      this.and(builtin, BuiltinID.BYTE_AND_BYTE, BasicTypeID.BYTE, BasicTypeID.BYTE);
      this.or(builtin, BuiltinID.BYTE_OR_BYTE, BasicTypeID.BYTE, BasicTypeID.BYTE);
      this.xor(builtin, BuiltinID.BYTE_XOR_BYTE, BasicTypeID.BYTE, BasicTypeID.BYTE);
      this.shl(builtin, BuiltinID.BYTE_SHL, BasicTypeID.USIZE, BasicTypeID.BYTE);
      this.shr(builtin, BuiltinID.BYTE_SHR, BasicTypeID.USIZE, BasicTypeID.BYTE);
      this.compare(builtin, BuiltinID.BYTE_COMPARE, BasicTypeID.BYTE);
      this.castImplicit(builtin, BuiltinID.BYTE_TO_SBYTE, BasicTypeID.SBYTE);
      this.castImplicit(builtin, BuiltinID.BYTE_TO_SHORT, BasicTypeID.SHORT);
      this.castImplicit(builtin, BuiltinID.BYTE_TO_USHORT, BasicTypeID.USHORT);
      this.castImplicit(builtin, BuiltinID.BYTE_TO_INT, BasicTypeID.INT);
      this.castImplicit(builtin, BuiltinID.BYTE_TO_UINT, BasicTypeID.UINT);
      this.castImplicit(builtin, BuiltinID.BYTE_TO_LONG, BasicTypeID.LONG);
      this.castImplicit(builtin, BuiltinID.BYTE_TO_ULONG, BasicTypeID.ULONG);
      this.castImplicit(builtin, BuiltinID.BYTE_TO_USIZE, BasicTypeID.USIZE);
      this.castImplicit(builtin, BuiltinID.BYTE_TO_FLOAT, BasicTypeID.FLOAT);
      this.castImplicit(builtin, BuiltinID.BYTE_TO_DOUBLE, BasicTypeID.DOUBLE);
      this.castExplicit(builtin, BuiltinID.BYTE_TO_CHAR, BasicTypeID.CHAR);
      this.castImplicit(builtin, BuiltinID.BYTE_TO_STRING, BasicTypeID.STRING);
      this.staticMethod(builtin, BuiltinID.BYTE_PARSE, "parse", BasicTypeID.BYTE, BasicTypeID.STRING);
      this.staticMethod(builtin, BuiltinID.BYTE_PARSE_WITH_BASE, "parse", BasicTypeID.BYTE, BasicTypeID.STRING, BasicTypeID.INT);
      this.constant(builtin, BuiltinID.BYTE_GET_MIN_VALUE, "MIN_VALUE", new ConstantByteExpression(CodePosition.BUILTIN, 0));
      this.constant(builtin, BuiltinID.BYTE_GET_MAX_VALUE, "MAX_VALUE", new ConstantByteExpression(CodePosition.BUILTIN, 255));
      this.processType(builtin);
   }

   private void visitSByte() {
      ClassDefinition builtin = new ClassDefinition(CodePosition.BUILTIN, Module.BUILTIN, null, "sbyte", 1, null);
      this.invert(builtin, BuiltinID.SBYTE_NOT, BasicTypeID.SBYTE);
      this.neg(builtin, BuiltinID.SBYTE_NEG, BasicTypeID.SBYTE);
      this.inc(builtin, BuiltinID.SBYTE_INC, BasicTypeID.SBYTE);
      this.dec(builtin, BuiltinID.SBYTE_DEC, BasicTypeID.SBYTE);
      this.add(builtin, BuiltinID.SBYTE_ADD_SBYTE, BasicTypeID.SBYTE, BasicTypeID.SBYTE);
      this.sub(builtin, BuiltinID.SBYTE_SUB_SBYTE, BasicTypeID.SBYTE, BasicTypeID.SBYTE);
      this.mul(builtin, BuiltinID.SBYTE_MUL_SBYTE, BasicTypeID.SBYTE, BasicTypeID.SBYTE);
      this.div(builtin, BuiltinID.SBYTE_DIV_SBYTE, BasicTypeID.SBYTE, BasicTypeID.SBYTE);
      this.mod(builtin, BuiltinID.SBYTE_MOD_SBYTE, BasicTypeID.SBYTE, BasicTypeID.SBYTE);
      this.and(builtin, BuiltinID.SBYTE_AND_SBYTE, BasicTypeID.SBYTE, BasicTypeID.SBYTE);
      this.or(builtin, BuiltinID.SBYTE_OR_SBYTE, BasicTypeID.SBYTE, BasicTypeID.SBYTE);
      this.xor(builtin, BuiltinID.SBYTE_XOR_SBYTE, BasicTypeID.SBYTE, BasicTypeID.SBYTE);
      this.shl(builtin, BuiltinID.SBYTE_SHL, BasicTypeID.USIZE, BasicTypeID.SBYTE);
      this.shr(builtin, BuiltinID.SBYTE_SHR, BasicTypeID.USIZE, BasicTypeID.SBYTE);
      this.ushr(builtin, BuiltinID.SBYTE_USHR, BasicTypeID.USIZE, BasicTypeID.SBYTE);
      this.compare(builtin, BuiltinID.SBYTE_COMPARE, BasicTypeID.SBYTE);
      this.castImplicit(builtin, BuiltinID.SBYTE_TO_BYTE, BasicTypeID.BYTE);
      this.castImplicit(builtin, BuiltinID.SBYTE_TO_SHORT, BasicTypeID.SHORT);
      this.castImplicit(builtin, BuiltinID.SBYTE_TO_USHORT, BasicTypeID.USHORT);
      this.castImplicit(builtin, BuiltinID.SBYTE_TO_INT, BasicTypeID.INT);
      this.castImplicit(builtin, BuiltinID.SBYTE_TO_UINT, BasicTypeID.UINT);
      this.castImplicit(builtin, BuiltinID.SBYTE_TO_LONG, BasicTypeID.LONG);
      this.castImplicit(builtin, BuiltinID.SBYTE_TO_ULONG, BasicTypeID.ULONG);
      this.castImplicit(builtin, BuiltinID.SBYTE_TO_USIZE, BasicTypeID.USIZE);
      this.castImplicit(builtin, BuiltinID.SBYTE_TO_FLOAT, BasicTypeID.FLOAT);
      this.castImplicit(builtin, BuiltinID.SBYTE_TO_DOUBLE, BasicTypeID.DOUBLE);
      this.castExplicit(builtin, BuiltinID.SBYTE_TO_CHAR, BasicTypeID.CHAR);
      this.castImplicit(builtin, BuiltinID.SBYTE_TO_STRING, BasicTypeID.STRING);
      this.staticMethod(builtin, BuiltinID.SBYTE_PARSE, "parse", BasicTypeID.SBYTE, BasicTypeID.STRING);
      this.staticMethod(builtin, BuiltinID.SBYTE_PARSE_WITH_BASE, "parse", BasicTypeID.SBYTE, BasicTypeID.STRING, BasicTypeID.INT);
      this.constant(builtin, BuiltinID.SBYTE_GET_MIN_VALUE, "MIN_VALUE", new ConstantSByteExpression(CodePosition.BUILTIN, (byte)-128));
      this.constant(builtin, BuiltinID.SBYTE_GET_MAX_VALUE, "MAX_VALUE", new ConstantSByteExpression(CodePosition.BUILTIN, (byte)127));
      this.processType(builtin);
   }

   private void visitShort() {
      ClassDefinition builtin = new ClassDefinition(CodePosition.BUILTIN, Module.BUILTIN, null, "short", 1, null);
      this.invert(builtin, BuiltinID.SHORT_NOT, BasicTypeID.SHORT);
      this.neg(builtin, BuiltinID.SHORT_NEG, BasicTypeID.SHORT);
      this.inc(builtin, BuiltinID.SHORT_INC, BasicTypeID.SHORT);
      this.dec(builtin, BuiltinID.SHORT_DEC, BasicTypeID.SHORT);
      this.add(builtin, BuiltinID.SHORT_ADD_SHORT, BasicTypeID.SHORT, BasicTypeID.SHORT);
      this.sub(builtin, BuiltinID.SHORT_SUB_SHORT, BasicTypeID.SHORT, BasicTypeID.SHORT);
      this.mul(builtin, BuiltinID.SHORT_MUL_SHORT, BasicTypeID.SHORT, BasicTypeID.SHORT);
      this.div(builtin, BuiltinID.SHORT_DIV_SHORT, BasicTypeID.SHORT, BasicTypeID.SHORT);
      this.mod(builtin, BuiltinID.SHORT_MOD_SHORT, BasicTypeID.SHORT, BasicTypeID.SHORT);
      this.and(builtin, BuiltinID.SHORT_AND_SHORT, BasicTypeID.SHORT, BasicTypeID.SHORT);
      this.or(builtin, BuiltinID.SHORT_OR_SHORT, BasicTypeID.SHORT, BasicTypeID.SHORT);
      this.xor(builtin, BuiltinID.SHORT_XOR_SHORT, BasicTypeID.SHORT, BasicTypeID.SHORT);
      this.shl(builtin, BuiltinID.SHORT_SHL, BasicTypeID.USIZE, BasicTypeID.SHORT);
      this.shr(builtin, BuiltinID.SHORT_SHR, BasicTypeID.USIZE, BasicTypeID.SHORT);
      this.ushr(builtin, BuiltinID.SHORT_USHR, BasicTypeID.USIZE, BasicTypeID.SHORT);
      this.compare(builtin, BuiltinID.SHORT_COMPARE, BasicTypeID.SHORT);
      this.castExplicit(builtin, BuiltinID.SHORT_TO_BYTE, BasicTypeID.BYTE);
      this.castExplicit(builtin, BuiltinID.SHORT_TO_SBYTE, BasicTypeID.SBYTE);
      this.castImplicit(builtin, BuiltinID.SHORT_TO_USHORT, BasicTypeID.USHORT);
      this.castImplicit(builtin, BuiltinID.SHORT_TO_INT, BasicTypeID.INT);
      this.castImplicit(builtin, BuiltinID.SHORT_TO_UINT, BasicTypeID.UINT);
      this.castImplicit(builtin, BuiltinID.SHORT_TO_LONG, BasicTypeID.LONG);
      this.castImplicit(builtin, BuiltinID.SHORT_TO_ULONG, BasicTypeID.ULONG);
      this.castImplicit(builtin, BuiltinID.SHORT_TO_USIZE, BasicTypeID.USIZE);
      this.castImplicit(builtin, BuiltinID.SHORT_TO_FLOAT, BasicTypeID.FLOAT);
      this.castImplicit(builtin, BuiltinID.SHORT_TO_DOUBLE, BasicTypeID.DOUBLE);
      this.castExplicit(builtin, BuiltinID.SHORT_TO_CHAR, BasicTypeID.CHAR);
      this.castImplicit(builtin, BuiltinID.SHORT_TO_STRING, BasicTypeID.STRING);
      this.staticMethod(builtin, BuiltinID.SHORT_PARSE, "parse", BasicTypeID.SHORT, BasicTypeID.STRING);
      this.staticMethod(builtin, BuiltinID.SHORT_PARSE_WITH_BASE, "parse", BasicTypeID.SHORT, BasicTypeID.STRING, BasicTypeID.INT);
      this.constant(builtin, BuiltinID.SHORT_GET_MIN_VALUE, "MIN_VALUE", new ConstantShortExpression(CodePosition.BUILTIN, (short)-32768));
      this.constant(builtin, BuiltinID.SHORT_GET_MAX_VALUE, "MAX_VALUE", new ConstantShortExpression(CodePosition.BUILTIN, (short)32767));
      this.processType(builtin);
   }

   private void visitUShort() {
      ClassDefinition builtin = new ClassDefinition(CodePosition.BUILTIN, Module.BUILTIN, null, "ushort", 1, null);
      this.invert(builtin, BuiltinID.USHORT_NOT, BasicTypeID.USHORT);
      this.inc(builtin, BuiltinID.USHORT_INC, BasicTypeID.USHORT);
      this.dec(builtin, BuiltinID.USHORT_DEC, BasicTypeID.USHORT);
      this.add(builtin, BuiltinID.USHORT_ADD_USHORT, BasicTypeID.USHORT, BasicTypeID.USHORT);
      this.sub(builtin, BuiltinID.USHORT_SUB_USHORT, BasicTypeID.USHORT, BasicTypeID.USHORT);
      this.mul(builtin, BuiltinID.USHORT_MUL_USHORT, BasicTypeID.USHORT, BasicTypeID.USHORT);
      this.div(builtin, BuiltinID.USHORT_DIV_USHORT, BasicTypeID.USHORT, BasicTypeID.USHORT);
      this.mod(builtin, BuiltinID.USHORT_MOD_USHORT, BasicTypeID.USHORT, BasicTypeID.USHORT);
      this.and(builtin, BuiltinID.USHORT_AND_USHORT, BasicTypeID.USHORT, BasicTypeID.USHORT);
      this.or(builtin, BuiltinID.USHORT_OR_USHORT, BasicTypeID.USHORT, BasicTypeID.USHORT);
      this.xor(builtin, BuiltinID.USHORT_XOR_USHORT, BasicTypeID.USHORT, BasicTypeID.USHORT);
      this.shl(builtin, BuiltinID.USHORT_SHL, BasicTypeID.USIZE, BasicTypeID.USHORT);
      this.shr(builtin, BuiltinID.USHORT_SHR, BasicTypeID.USIZE, BasicTypeID.USHORT);
      this.compare(builtin, BuiltinID.USHORT_COMPARE, BasicTypeID.USHORT);
      this.castExplicit(builtin, BuiltinID.USHORT_TO_BYTE, BasicTypeID.BYTE);
      this.castExplicit(builtin, BuiltinID.USHORT_TO_SBYTE, BasicTypeID.SBYTE);
      this.castImplicit(builtin, BuiltinID.USHORT_TO_SHORT, BasicTypeID.SHORT);
      this.castImplicit(builtin, BuiltinID.USHORT_TO_INT, BasicTypeID.INT);
      this.castImplicit(builtin, BuiltinID.USHORT_TO_UINT, BasicTypeID.UINT);
      this.castImplicit(builtin, BuiltinID.USHORT_TO_LONG, BasicTypeID.LONG);
      this.castImplicit(builtin, BuiltinID.USHORT_TO_ULONG, BasicTypeID.ULONG);
      this.castImplicit(builtin, BuiltinID.USHORT_TO_USIZE, BasicTypeID.USIZE);
      this.castImplicit(builtin, BuiltinID.USHORT_TO_FLOAT, BasicTypeID.FLOAT);
      this.castImplicit(builtin, BuiltinID.USHORT_TO_DOUBLE, BasicTypeID.DOUBLE);
      this.castExplicit(builtin, BuiltinID.USHORT_TO_CHAR, BasicTypeID.CHAR);
      this.castImplicit(builtin, BuiltinID.USHORT_TO_STRING, BasicTypeID.STRING);
      this.staticMethod(builtin, BuiltinID.USHORT_PARSE, "parse", BasicTypeID.USHORT, BasicTypeID.STRING);
      this.staticMethod(builtin, BuiltinID.USHORT_PARSE_WITH_BASE, "parse", BasicTypeID.USHORT, BasicTypeID.STRING, BasicTypeID.INT);
      this.constant(builtin, BuiltinID.USHORT_GET_MIN_VALUE, "MIN_VALUE", new ConstantUShortExpression(CodePosition.BUILTIN, 0));
      this.constant(builtin, BuiltinID.USHORT_GET_MAX_VALUE, "MAX_VALUE", new ConstantUShortExpression(CodePosition.BUILTIN, 65535));
      this.processType(builtin);
   }

   private void visitInt() {
      ClassDefinition builtin = new ClassDefinition(CodePosition.BUILTIN, Module.BUILTIN, null, "int", 1, null);
      this.invert(builtin, BuiltinID.INT_NOT, BasicTypeID.INT);
      this.neg(builtin, BuiltinID.INT_NEG, BasicTypeID.INT);
      this.inc(builtin, BuiltinID.INT_INC, BasicTypeID.INT);
      this.dec(builtin, BuiltinID.INT_DEC, BasicTypeID.INT);
      this.add(builtin, BuiltinID.INT_ADD_INT, BasicTypeID.INT, BasicTypeID.INT);
      this.add(builtin, BuiltinID.LONG_ADD_LONG, BasicTypeID.LONG, BasicTypeID.LONG, BuiltinID.INT_TO_LONG);
      this.add(builtin, BuiltinID.FLOAT_ADD_FLOAT, BasicTypeID.FLOAT, BasicTypeID.FLOAT, BuiltinID.INT_TO_FLOAT);
      this.add(builtin, BuiltinID.DOUBLE_ADD_DOUBLE, BasicTypeID.DOUBLE, BasicTypeID.DOUBLE, BuiltinID.INT_TO_DOUBLE);
      this.sub(builtin, BuiltinID.INT_SUB_INT, BasicTypeID.INT, BasicTypeID.INT);
      this.sub(builtin, BuiltinID.LONG_SUB_LONG, BasicTypeID.LONG, BasicTypeID.LONG, BuiltinID.INT_TO_LONG);
      this.sub(builtin, BuiltinID.FLOAT_SUB_FLOAT, BasicTypeID.FLOAT, BasicTypeID.FLOAT, BuiltinID.INT_TO_FLOAT);
      this.sub(builtin, BuiltinID.DOUBLE_SUB_DOUBLE, BasicTypeID.DOUBLE, BasicTypeID.DOUBLE, BuiltinID.INT_TO_DOUBLE);
      this.mul(builtin, BuiltinID.INT_MUL_INT, BasicTypeID.INT, BasicTypeID.INT);
      this.mul(builtin, BuiltinID.LONG_MUL_LONG, BasicTypeID.LONG, BasicTypeID.LONG, BuiltinID.INT_TO_LONG);
      this.mul(builtin, BuiltinID.FLOAT_MUL_FLOAT, BasicTypeID.FLOAT, BasicTypeID.FLOAT, BuiltinID.INT_TO_FLOAT);
      this.mul(builtin, BuiltinID.DOUBLE_MUL_DOUBLE, BasicTypeID.DOUBLE, BasicTypeID.DOUBLE, BuiltinID.INT_TO_DOUBLE);
      this.div(builtin, BuiltinID.INT_DIV_INT, BasicTypeID.INT, BasicTypeID.INT);
      this.div(builtin, BuiltinID.LONG_DIV_LONG, BasicTypeID.LONG, BasicTypeID.LONG, BuiltinID.INT_TO_LONG);
      this.div(builtin, BuiltinID.FLOAT_DIV_FLOAT, BasicTypeID.FLOAT, BasicTypeID.FLOAT, BuiltinID.INT_TO_FLOAT);
      this.div(builtin, BuiltinID.DOUBLE_DIV_DOUBLE, BasicTypeID.DOUBLE, BasicTypeID.DOUBLE, BuiltinID.INT_TO_DOUBLE);
      this.mod(builtin, BuiltinID.INT_MOD_INT, BasicTypeID.INT, BasicTypeID.INT);
      this.mod(builtin, BuiltinID.LONG_MOD_LONG, BasicTypeID.LONG, BasicTypeID.LONG, BuiltinID.INT_TO_LONG);
      this.or(builtin, BuiltinID.INT_OR_INT, BasicTypeID.INT, BasicTypeID.INT);
      this.or(builtin, BuiltinID.LONG_OR_LONG, BasicTypeID.LONG, BasicTypeID.LONG, BuiltinID.INT_TO_LONG);
      this.and(builtin, BuiltinID.INT_AND_INT, BasicTypeID.INT, BasicTypeID.INT);
      this.and(builtin, BuiltinID.LONG_AND_LONG, BasicTypeID.LONG, BasicTypeID.LONG, BuiltinID.INT_TO_LONG);
      this.xor(builtin, BuiltinID.INT_XOR_INT, BasicTypeID.INT, BasicTypeID.INT);
      this.xor(builtin, BuiltinID.LONG_XOR_LONG, BasicTypeID.LONG, BasicTypeID.LONG, BuiltinID.INT_TO_LONG);
      this.shl(builtin, BuiltinID.INT_SHL, BasicTypeID.USIZE, BasicTypeID.INT);
      this.shr(builtin, BuiltinID.INT_SHR, BasicTypeID.USIZE, BasicTypeID.INT);
      this.ushr(builtin, BuiltinID.INT_USHR, BasicTypeID.USIZE, BasicTypeID.INT);
      this.compare(builtin, BuiltinID.INT_COMPARE, BasicTypeID.INT);
      this.compare(builtin, BuiltinID.LONG_COMPARE, BasicTypeID.LONG, BuiltinID.INT_TO_LONG);
      this.compare(builtin, BuiltinID.FLOAT_COMPARE, BasicTypeID.FLOAT, BuiltinID.INT_TO_FLOAT);
      this.compare(builtin, BuiltinID.DOUBLE_COMPARE, BasicTypeID.DOUBLE, BuiltinID.INT_TO_DOUBLE);
      this.constant(builtin, BuiltinID.INT_GET_MIN_VALUE, "MIN_VALUE", new ConstantIntExpression(CodePosition.BUILTIN, Integer.MIN_VALUE));
      this.constant(builtin, BuiltinID.INT_GET_MAX_VALUE, "MAX_VALUE", new ConstantIntExpression(CodePosition.BUILTIN, Integer.MAX_VALUE));
      this.castExplicit(builtin, BuiltinID.INT_TO_BYTE, BasicTypeID.BYTE);
      this.castExplicit(builtin, BuiltinID.INT_TO_SBYTE, BasicTypeID.SBYTE);
      this.castExplicit(builtin, BuiltinID.INT_TO_SHORT, BasicTypeID.SHORT);
      this.castExplicit(builtin, BuiltinID.INT_TO_USHORT, BasicTypeID.USHORT);
      this.castImplicit(builtin, BuiltinID.INT_TO_UINT, BasicTypeID.UINT);
      this.castImplicit(builtin, BuiltinID.INT_TO_LONG, BasicTypeID.LONG);
      this.castImplicit(builtin, BuiltinID.INT_TO_ULONG, BasicTypeID.ULONG);
      this.castImplicit(builtin, BuiltinID.INT_TO_USIZE, BasicTypeID.USIZE);
      this.castImplicit(builtin, BuiltinID.INT_TO_FLOAT, BasicTypeID.FLOAT);
      this.castImplicit(builtin, BuiltinID.INT_TO_DOUBLE, BasicTypeID.DOUBLE);
      this.castExplicit(builtin, BuiltinID.INT_TO_CHAR, BasicTypeID.CHAR);
      this.castImplicit(builtin, BuiltinID.INT_TO_STRING, BasicTypeID.STRING);
      this.staticMethod(builtin, BuiltinID.INT_PARSE, "parse", BasicTypeID.INT, BasicTypeID.STRING);
      this.staticMethod(builtin, BuiltinID.INT_PARSE_WITH_BASE, "parse", BasicTypeID.INT, BasicTypeID.STRING, BasicTypeID.INT);
      this.method(builtin, BuiltinID.INT_COUNT_LOW_ZEROES, "countLowZeroes", BasicTypeID.USIZE);
      this.method(builtin, BuiltinID.INT_COUNT_HIGH_ZEROES, "countHighZeroes", BasicTypeID.USIZE);
      this.method(builtin, BuiltinID.INT_COUNT_LOW_ONES, "countLowOnes", BasicTypeID.USIZE);
      this.method(builtin, BuiltinID.INT_COUNT_HIGH_ONES, "countHighOnes", BasicTypeID.USIZE);
      TypeID optionalUSize = this.registry.getOptional(BasicTypeID.USIZE);
      this.getter(builtin, BuiltinID.INT_HIGHEST_ONE_BIT, "highestOneBit", optionalUSize);
      this.getter(builtin, BuiltinID.INT_LOWEST_ONE_BIT, "lowestOneBit", optionalUSize);
      this.getter(builtin, BuiltinID.INT_HIGHEST_ZERO_BIT, "highestZeroBit", optionalUSize);
      this.getter(builtin, BuiltinID.INT_LOWEST_ZERO_BIT, "lowestZeroBit", optionalUSize);
      this.getter(builtin, BuiltinID.INT_BIT_COUNT, "bitCount", BasicTypeID.USIZE);
      this.processType(builtin);
   }

   private void visitUInt() {
      ClassDefinition builtin = new ClassDefinition(CodePosition.BUILTIN, Module.BUILTIN, null, "uint", 1, null);
      this.invert(builtin, BuiltinID.UINT_NOT, BasicTypeID.INT);
      this.inc(builtin, BuiltinID.UINT_INC, BasicTypeID.INT);
      this.dec(builtin, BuiltinID.UINT_DEC, BasicTypeID.INT);
      this.add(builtin, BuiltinID.UINT_ADD_UINT, BasicTypeID.UINT, BasicTypeID.UINT);
      this.add(builtin, BuiltinID.ULONG_ADD_ULONG, BasicTypeID.USIZE, BasicTypeID.ULONG, BuiltinID.UINT_TO_ULONG);
      this.add(builtin, BuiltinID.ULONG_ADD_ULONG, BasicTypeID.ULONG, BasicTypeID.ULONG, BuiltinID.UINT_TO_ULONG);
      this.add(builtin, BuiltinID.FLOAT_ADD_FLOAT, BasicTypeID.FLOAT, BasicTypeID.FLOAT, BuiltinID.UINT_TO_FLOAT);
      this.add(builtin, BuiltinID.DOUBLE_ADD_DOUBLE, BasicTypeID.DOUBLE, BasicTypeID.DOUBLE, BuiltinID.UINT_TO_DOUBLE);
      this.sub(builtin, BuiltinID.UINT_SUB_UINT, BasicTypeID.UINT, BasicTypeID.UINT);
      this.sub(builtin, BuiltinID.ULONG_SUB_ULONG, BasicTypeID.ULONG, BasicTypeID.ULONG, BuiltinID.UINT_TO_ULONG);
      this.sub(builtin, BuiltinID.FLOAT_SUB_FLOAT, BasicTypeID.FLOAT, BasicTypeID.FLOAT, BuiltinID.UINT_TO_FLOAT);
      this.sub(builtin, BuiltinID.DOUBLE_SUB_DOUBLE, BasicTypeID.DOUBLE, BasicTypeID.DOUBLE, BuiltinID.UINT_TO_DOUBLE);
      this.mul(builtin, BuiltinID.UINT_MUL_UINT, BasicTypeID.UINT, BasicTypeID.UINT);
      this.mul(builtin, BuiltinID.ULONG_MUL_ULONG, BasicTypeID.ULONG, BasicTypeID.ULONG, BuiltinID.UINT_TO_ULONG);
      this.mul(builtin, BuiltinID.FLOAT_MUL_FLOAT, BasicTypeID.FLOAT, BasicTypeID.FLOAT, BuiltinID.UINT_TO_FLOAT);
      this.mul(builtin, BuiltinID.DOUBLE_MUL_DOUBLE, BasicTypeID.DOUBLE, BasicTypeID.DOUBLE, BuiltinID.UINT_TO_DOUBLE);
      this.div(builtin, BuiltinID.UINT_DIV_UINT, BasicTypeID.UINT, BasicTypeID.UINT);
      this.div(builtin, BuiltinID.ULONG_DIV_ULONG, BasicTypeID.ULONG, BasicTypeID.ULONG, BuiltinID.UINT_TO_ULONG);
      this.div(builtin, BuiltinID.FLOAT_DIV_FLOAT, BasicTypeID.FLOAT, BasicTypeID.FLOAT, BuiltinID.UINT_TO_FLOAT);
      this.div(builtin, BuiltinID.DOUBLE_DIV_DOUBLE, BasicTypeID.DOUBLE, BasicTypeID.DOUBLE, BuiltinID.UINT_TO_DOUBLE);
      this.mod(builtin, BuiltinID.UINT_MOD_UINT, BasicTypeID.UINT, BasicTypeID.UINT);
      this.mod(builtin, BuiltinID.ULONG_MOD_ULONG, BasicTypeID.ULONG, BasicTypeID.ULONG, BuiltinID.UINT_TO_ULONG);
      this.or(builtin, BuiltinID.UINT_OR_UINT, BasicTypeID.UINT, BasicTypeID.UINT);
      this.or(builtin, BuiltinID.ULONG_OR_ULONG, BasicTypeID.ULONG, BasicTypeID.ULONG, BuiltinID.UINT_TO_ULONG);
      this.and(builtin, BuiltinID.UINT_AND_UINT, BasicTypeID.UINT, BasicTypeID.UINT);
      this.and(builtin, BuiltinID.ULONG_AND_ULONG, BasicTypeID.ULONG, BasicTypeID.ULONG, BuiltinID.UINT_TO_ULONG);
      this.xor(builtin, BuiltinID.UINT_XOR_UINT, BasicTypeID.UINT, BasicTypeID.UINT);
      this.xor(builtin, BuiltinID.ULONG_XOR_ULONG, BasicTypeID.ULONG, BasicTypeID.ULONG, BuiltinID.UINT_TO_ULONG);
      this.shl(builtin, BuiltinID.UINT_SHL, BasicTypeID.USIZE, BasicTypeID.UINT);
      this.shr(builtin, BuiltinID.UINT_SHR, BasicTypeID.USIZE, BasicTypeID.UINT);
      this.compare(builtin, BuiltinID.UINT_COMPARE, BasicTypeID.UINT);
      this.compare(builtin, BuiltinID.ULONG_COMPARE, BasicTypeID.ULONG, BuiltinID.UINT_TO_LONG);
      this.compare(builtin, BuiltinID.FLOAT_COMPARE, BasicTypeID.FLOAT, BuiltinID.UINT_TO_FLOAT);
      this.compare(builtin, BuiltinID.DOUBLE_COMPARE, BasicTypeID.DOUBLE, BuiltinID.UINT_TO_DOUBLE);
      this.constant(builtin, BuiltinID.UINT_GET_MIN_VALUE, "MIN_VALUE", new ConstantUIntExpression(CodePosition.BUILTIN, 0));
      this.constant(builtin, BuiltinID.UINT_GET_MAX_VALUE, "MAX_VALUE", new ConstantUIntExpression(CodePosition.BUILTIN, -1));
      this.castExplicit(builtin, BuiltinID.UINT_TO_BYTE, BasicTypeID.BYTE);
      this.castExplicit(builtin, BuiltinID.UINT_TO_SBYTE, BasicTypeID.SBYTE);
      this.castExplicit(builtin, BuiltinID.UINT_TO_SHORT, BasicTypeID.SHORT);
      this.castExplicit(builtin, BuiltinID.UINT_TO_USHORT, BasicTypeID.USHORT);
      this.castImplicit(builtin, BuiltinID.UINT_TO_INT, BasicTypeID.INT);
      this.castImplicit(builtin, BuiltinID.UINT_TO_LONG, BasicTypeID.LONG);
      this.castImplicit(builtin, BuiltinID.UINT_TO_ULONG, BasicTypeID.ULONG);
      this.castImplicit(builtin, BuiltinID.UINT_TO_USIZE, BasicTypeID.USIZE);
      this.castImplicit(builtin, BuiltinID.UINT_TO_FLOAT, BasicTypeID.FLOAT);
      this.castImplicit(builtin, BuiltinID.UINT_TO_DOUBLE, BasicTypeID.DOUBLE);
      this.castExplicit(builtin, BuiltinID.UINT_TO_CHAR, BasicTypeID.CHAR);
      this.castImplicit(builtin, BuiltinID.UINT_TO_STRING, BasicTypeID.STRING);
      this.staticMethod(builtin, BuiltinID.UINT_PARSE, "parse", BasicTypeID.UINT, BasicTypeID.STRING);
      this.staticMethod(builtin, BuiltinID.UINT_PARSE_WITH_BASE, "parse", BasicTypeID.UINT, BasicTypeID.STRING, BasicTypeID.INT);
      this.method(builtin, BuiltinID.UINT_COUNT_LOW_ZEROES, "countLowZeroes", BasicTypeID.USIZE);
      this.method(builtin, BuiltinID.UINT_COUNT_HIGH_ZEROES, "countHighZeroes", BasicTypeID.USIZE);
      this.method(builtin, BuiltinID.UINT_COUNT_LOW_ONES, "countLowOnes", BasicTypeID.USIZE);
      this.method(builtin, BuiltinID.UINT_COUNT_HIGH_ONES, "countHighOnes", BasicTypeID.USIZE);
      TypeID optionalUSize = this.registry.getOptional(BasicTypeID.USIZE);
      this.getter(builtin, BuiltinID.UINT_HIGHEST_ONE_BIT, "highestOneBit", optionalUSize);
      this.getter(builtin, BuiltinID.UINT_LOWEST_ONE_BIT, "lowestOneBit", optionalUSize);
      this.getter(builtin, BuiltinID.UINT_HIGHEST_ZERO_BIT, "highestZeroBit", optionalUSize);
      this.getter(builtin, BuiltinID.UINT_LOWEST_ZERO_BIT, "lowestZeroBit", optionalUSize);
      this.getter(builtin, BuiltinID.UINT_BIT_COUNT, "bitCount", BasicTypeID.USIZE);
      this.processType(builtin);
   }

   private void visitLong() {
      ClassDefinition builtin = new ClassDefinition(CodePosition.BUILTIN, Module.BUILTIN, null, "long", 1, null);
      this.invert(builtin, BuiltinID.LONG_NOT, BasicTypeID.LONG);
      this.neg(builtin, BuiltinID.LONG_NEG, BasicTypeID.LONG);
      this.inc(builtin, BuiltinID.LONG_INC, BasicTypeID.LONG);
      this.dec(builtin, BuiltinID.LONG_DEC, BasicTypeID.LONG);
      this.add(builtin, BuiltinID.LONG_ADD_LONG, BasicTypeID.LONG, BasicTypeID.LONG);
      this.add(builtin, BuiltinID.FLOAT_ADD_FLOAT, BasicTypeID.FLOAT, BasicTypeID.FLOAT, BuiltinID.LONG_TO_FLOAT);
      this.add(builtin, BuiltinID.DOUBLE_ADD_DOUBLE, BasicTypeID.DOUBLE, BasicTypeID.DOUBLE, BuiltinID.LONG_TO_DOUBLE);
      this.sub(builtin, BuiltinID.LONG_SUB_LONG, BasicTypeID.LONG, BasicTypeID.LONG);
      this.sub(builtin, BuiltinID.FLOAT_SUB_FLOAT, BasicTypeID.FLOAT, BasicTypeID.FLOAT, BuiltinID.LONG_TO_FLOAT);
      this.sub(builtin, BuiltinID.DOUBLE_SUB_DOUBLE, BasicTypeID.DOUBLE, BasicTypeID.DOUBLE, BuiltinID.LONG_TO_DOUBLE);
      this.mul(builtin, BuiltinID.LONG_MUL_LONG, BasicTypeID.LONG, BasicTypeID.LONG);
      this.mul(builtin, BuiltinID.FLOAT_MUL_FLOAT, BasicTypeID.FLOAT, BasicTypeID.FLOAT, BuiltinID.LONG_TO_FLOAT);
      this.mul(builtin, BuiltinID.DOUBLE_MUL_DOUBLE, BasicTypeID.DOUBLE, BasicTypeID.DOUBLE, BuiltinID.LONG_TO_DOUBLE);
      this.div(builtin, BuiltinID.LONG_DIV_LONG, BasicTypeID.LONG, BasicTypeID.LONG);
      this.div(builtin, BuiltinID.FLOAT_DIV_FLOAT, BasicTypeID.FLOAT, BasicTypeID.FLOAT, BuiltinID.LONG_TO_FLOAT);
      this.div(builtin, BuiltinID.DOUBLE_DIV_DOUBLE, BasicTypeID.DOUBLE, BasicTypeID.DOUBLE, BuiltinID.LONG_TO_DOUBLE);
      this.mod(builtin, BuiltinID.LONG_MOD_LONG, BasicTypeID.LONG, BasicTypeID.LONG);
      this.or(builtin, BuiltinID.LONG_OR_LONG, BasicTypeID.LONG, BasicTypeID.LONG);
      this.and(builtin, BuiltinID.LONG_AND_LONG, BasicTypeID.LONG, BasicTypeID.LONG);
      this.xor(builtin, BuiltinID.LONG_XOR_LONG, BasicTypeID.LONG, BasicTypeID.LONG);
      this.shl(builtin, BuiltinID.LONG_SHL, BasicTypeID.USIZE, BasicTypeID.LONG);
      this.shr(builtin, BuiltinID.LONG_SHR, BasicTypeID.USIZE, BasicTypeID.LONG);
      this.ushr(builtin, BuiltinID.LONG_USHR, BasicTypeID.USIZE, BasicTypeID.LONG);
      this.compare(builtin, BuiltinID.LONG_COMPARE_INT, BasicTypeID.INT);
      this.compare(builtin, BuiltinID.LONG_COMPARE, BasicTypeID.LONG);
      this.compare(builtin, BuiltinID.FLOAT_COMPARE, BasicTypeID.FLOAT, BuiltinID.LONG_TO_FLOAT);
      this.compare(builtin, BuiltinID.DOUBLE_COMPARE, BasicTypeID.DOUBLE, BuiltinID.LONG_TO_DOUBLE);
      this.constant(builtin, BuiltinID.LONG_GET_MIN_VALUE, "MIN_VALUE", new ConstantLongExpression(CodePosition.BUILTIN, Long.MIN_VALUE));
      this.constant(builtin, BuiltinID.LONG_GET_MAX_VALUE, "MAX_VALUE", new ConstantLongExpression(CodePosition.BUILTIN, Long.MAX_VALUE));
      this.castExplicit(builtin, BuiltinID.LONG_TO_BYTE, BasicTypeID.BYTE);
      this.castExplicit(builtin, BuiltinID.LONG_TO_SBYTE, BasicTypeID.SBYTE);
      this.castExplicit(builtin, BuiltinID.LONG_TO_SHORT, BasicTypeID.SHORT);
      this.castExplicit(builtin, BuiltinID.LONG_TO_USHORT, BasicTypeID.USHORT);
      this.castExplicit(builtin, BuiltinID.LONG_TO_INT, BasicTypeID.INT);
      this.castExplicit(builtin, BuiltinID.LONG_TO_UINT, BasicTypeID.UINT);
      this.castImplicit(builtin, BuiltinID.LONG_TO_ULONG, BasicTypeID.ULONG);
      this.castExplicit(builtin, BuiltinID.LONG_TO_USIZE, BasicTypeID.USIZE);
      this.castImplicit(builtin, BuiltinID.LONG_TO_FLOAT, BasicTypeID.FLOAT);
      this.castImplicit(builtin, BuiltinID.LONG_TO_DOUBLE, BasicTypeID.DOUBLE);
      this.castExplicit(builtin, BuiltinID.LONG_TO_CHAR, BasicTypeID.CHAR);
      this.castImplicit(builtin, BuiltinID.LONG_TO_STRING, BasicTypeID.STRING);
      this.staticMethod(builtin, BuiltinID.LONG_PARSE, "parse", BasicTypeID.LONG, BasicTypeID.STRING);
      this.staticMethod(builtin, BuiltinID.LONG_PARSE_WITH_BASE, "parse", BasicTypeID.LONG, BasicTypeID.STRING, BasicTypeID.INT);
      this.method(builtin, BuiltinID.LONG_COUNT_LOW_ZEROES, "countLowZeroes", BasicTypeID.USIZE);
      this.method(builtin, BuiltinID.LONG_COUNT_HIGH_ZEROES, "countHighZeroes", BasicTypeID.USIZE);
      this.method(builtin, BuiltinID.LONG_COUNT_LOW_ONES, "countLowOnes", BasicTypeID.USIZE);
      this.method(builtin, BuiltinID.LONG_COUNT_HIGH_ONES, "countHighOnes", BasicTypeID.USIZE);
      TypeID optionalUSize = this.registry.getOptional(BasicTypeID.USIZE);
      this.getter(builtin, BuiltinID.LONG_HIGHEST_ONE_BIT, "highestOneBit", optionalUSize);
      this.getter(builtin, BuiltinID.LONG_LOWEST_ONE_BIT, "lowestOneBit", optionalUSize);
      this.getter(builtin, BuiltinID.LONG_HIGHEST_ZERO_BIT, "highestZeroBit", optionalUSize);
      this.getter(builtin, BuiltinID.LONG_LOWEST_ZERO_BIT, "lowestZeroBit", optionalUSize);
      this.getter(builtin, BuiltinID.LONG_BIT_COUNT, "bitCount", BasicTypeID.USIZE);
      this.processType(builtin);
   }

   private void visitULong() {
      ClassDefinition builtin = new ClassDefinition(CodePosition.BUILTIN, Module.BUILTIN, null, "ulong", 1, null);
      this.invert(builtin, BuiltinID.ULONG_NOT, BasicTypeID.ULONG);
      this.inc(builtin, BuiltinID.ULONG_INC, BasicTypeID.ULONG);
      this.dec(builtin, BuiltinID.ULONG_DEC, BasicTypeID.ULONG);
      this.add(builtin, BuiltinID.ULONG_ADD_ULONG, BasicTypeID.ULONG, BasicTypeID.ULONG);
      this.add(builtin, BuiltinID.FLOAT_ADD_FLOAT, BasicTypeID.FLOAT, BasicTypeID.FLOAT, BuiltinID.ULONG_TO_FLOAT);
      this.add(builtin, BuiltinID.DOUBLE_ADD_DOUBLE, BasicTypeID.DOUBLE, BasicTypeID.DOUBLE, BuiltinID.ULONG_TO_DOUBLE);
      this.sub(builtin, BuiltinID.ULONG_SUB_ULONG, BasicTypeID.ULONG, BasicTypeID.ULONG);
      this.sub(builtin, BuiltinID.FLOAT_SUB_FLOAT, BasicTypeID.FLOAT, BasicTypeID.FLOAT, BuiltinID.ULONG_TO_FLOAT);
      this.sub(builtin, BuiltinID.DOUBLE_SUB_DOUBLE, BasicTypeID.DOUBLE, BasicTypeID.DOUBLE, BuiltinID.ULONG_TO_DOUBLE);
      this.mul(builtin, BuiltinID.ULONG_MUL_ULONG, BasicTypeID.ULONG, BasicTypeID.ULONG);
      this.mul(builtin, BuiltinID.FLOAT_MUL_FLOAT, BasicTypeID.FLOAT, BasicTypeID.FLOAT, BuiltinID.ULONG_TO_FLOAT);
      this.mul(builtin, BuiltinID.DOUBLE_MUL_DOUBLE, BasicTypeID.DOUBLE, BasicTypeID.DOUBLE, BuiltinID.ULONG_TO_DOUBLE);
      this.div(builtin, BuiltinID.ULONG_DIV_ULONG, BasicTypeID.ULONG, BasicTypeID.ULONG);
      this.div(builtin, BuiltinID.FLOAT_DIV_FLOAT, BasicTypeID.FLOAT, BasicTypeID.FLOAT, BuiltinID.ULONG_TO_FLOAT);
      this.div(builtin, BuiltinID.DOUBLE_DIV_DOUBLE, BasicTypeID.DOUBLE, BasicTypeID.DOUBLE, BuiltinID.ULONG_TO_DOUBLE);
      this.mod(builtin, BuiltinID.ULONG_MOD_ULONG, BasicTypeID.ULONG, BasicTypeID.ULONG);
      this.or(builtin, BuiltinID.ULONG_OR_ULONG, BasicTypeID.ULONG, BasicTypeID.ULONG);
      this.and(builtin, BuiltinID.ULONG_AND_ULONG, BasicTypeID.ULONG, BasicTypeID.ULONG);
      this.xor(builtin, BuiltinID.ULONG_XOR_ULONG, BasicTypeID.ULONG, BasicTypeID.ULONG);
      this.shl(builtin, BuiltinID.ULONG_SHL, BasicTypeID.USIZE, BasicTypeID.ULONG);
      this.shr(builtin, BuiltinID.ULONG_SHR, BasicTypeID.USIZE, BasicTypeID.ULONG);
      this.compare(builtin, BuiltinID.ULONG_COMPARE_UINT, BasicTypeID.UINT);
      this.compare(builtin, BuiltinID.ULONG_COMPARE_USIZE, BasicTypeID.USIZE);
      this.compare(builtin, BuiltinID.ULONG_COMPARE, BasicTypeID.ULONG);
      this.compare(builtin, BuiltinID.FLOAT_COMPARE, BasicTypeID.FLOAT, BuiltinID.ULONG_TO_FLOAT);
      this.compare(builtin, BuiltinID.DOUBLE_COMPARE, BasicTypeID.DOUBLE, BuiltinID.ULONG_TO_DOUBLE);
      this.constant(builtin, BuiltinID.ULONG_GET_MIN_VALUE, "MIN_VALUE", new ConstantULongExpression(CodePosition.BUILTIN, 0L));
      this.constant(builtin, BuiltinID.ULONG_GET_MAX_VALUE, "MAX_VALUE", new ConstantULongExpression(CodePosition.BUILTIN, -1L));
      this.castExplicit(builtin, BuiltinID.ULONG_TO_BYTE, BasicTypeID.BYTE);
      this.castExplicit(builtin, BuiltinID.ULONG_TO_SBYTE, BasicTypeID.SBYTE);
      this.castExplicit(builtin, BuiltinID.ULONG_TO_SHORT, BasicTypeID.SHORT);
      this.castExplicit(builtin, BuiltinID.ULONG_TO_USHORT, BasicTypeID.USHORT);
      this.castExplicit(builtin, BuiltinID.ULONG_TO_INT, BasicTypeID.INT);
      this.castExplicit(builtin, BuiltinID.ULONG_TO_UINT, BasicTypeID.UINT);
      this.castImplicit(builtin, BuiltinID.ULONG_TO_LONG, BasicTypeID.LONG);
      this.castExplicit(builtin, BuiltinID.ULONG_TO_USIZE, BasicTypeID.USIZE);
      this.castImplicit(builtin, BuiltinID.ULONG_TO_FLOAT, BasicTypeID.FLOAT);
      this.castImplicit(builtin, BuiltinID.ULONG_TO_DOUBLE, BasicTypeID.DOUBLE);
      this.castExplicit(builtin, BuiltinID.ULONG_TO_CHAR, BasicTypeID.CHAR);
      this.castImplicit(builtin, BuiltinID.ULONG_TO_STRING, BasicTypeID.STRING);
      this.staticMethod(builtin, BuiltinID.ULONG_PARSE, "parse", BasicTypeID.ULONG, BasicTypeID.STRING);
      this.staticMethod(builtin, BuiltinID.ULONG_PARSE_WITH_BASE, "parse", BasicTypeID.ULONG, BasicTypeID.STRING, BasicTypeID.INT);
      this.method(builtin, BuiltinID.ULONG_COUNT_LOW_ZEROES, "countLowZeroes", BasicTypeID.USIZE);
      this.method(builtin, BuiltinID.ULONG_COUNT_HIGH_ZEROES, "countHighZeroes", BasicTypeID.USIZE);
      this.method(builtin, BuiltinID.ULONG_COUNT_LOW_ONES, "countLowOnes", BasicTypeID.USIZE);
      this.method(builtin, BuiltinID.ULONG_COUNT_HIGH_ONES, "countHighOnes", BasicTypeID.USIZE);
      TypeID optionalUSize = this.registry.getOptional(BasicTypeID.USIZE);
      this.getter(builtin, BuiltinID.ULONG_HIGHEST_ONE_BIT, "highestOneBit", optionalUSize);
      this.getter(builtin, BuiltinID.ULONG_LOWEST_ONE_BIT, "lowestOneBit", optionalUSize);
      this.getter(builtin, BuiltinID.ULONG_HIGHEST_ZERO_BIT, "highestZeroBit", optionalUSize);
      this.getter(builtin, BuiltinID.ULONG_LOWEST_ZERO_BIT, "lowestZeroBit", optionalUSize);
      this.getter(builtin, BuiltinID.ULONG_BIT_COUNT, "bitCount", BasicTypeID.USIZE);
      this.processType(builtin);
   }

   private void visitUSize() {
      ClassDefinition builtin = new ClassDefinition(CodePosition.BUILTIN, Module.BUILTIN, null, "usize", 1, null);
      this.invert(builtin, BuiltinID.USIZE_NOT, BasicTypeID.USIZE);
      this.inc(builtin, BuiltinID.USIZE_INC, BasicTypeID.USIZE);
      this.dec(builtin, BuiltinID.USIZE_DEC, BasicTypeID.USIZE);
      this.add(builtin, BuiltinID.USIZE_ADD_USIZE, BasicTypeID.USIZE, BasicTypeID.USIZE);
      this.add(builtin, BuiltinID.ULONG_ADD_ULONG, BasicTypeID.ULONG, BasicTypeID.ULONG, BuiltinID.USIZE_TO_ULONG);
      this.add(builtin, BuiltinID.FLOAT_ADD_FLOAT, BasicTypeID.FLOAT, BasicTypeID.FLOAT, BuiltinID.USIZE_TO_FLOAT);
      this.add(builtin, BuiltinID.DOUBLE_ADD_DOUBLE, BasicTypeID.DOUBLE, BasicTypeID.DOUBLE, BuiltinID.USIZE_TO_DOUBLE);
      this.sub(builtin, BuiltinID.USIZE_SUB_USIZE, BasicTypeID.USIZE, BasicTypeID.USIZE);
      this.sub(builtin, BuiltinID.ULONG_SUB_ULONG, BasicTypeID.ULONG, BasicTypeID.ULONG, BuiltinID.USIZE_TO_ULONG);
      this.sub(builtin, BuiltinID.FLOAT_SUB_FLOAT, BasicTypeID.FLOAT, BasicTypeID.FLOAT, BuiltinID.USIZE_TO_FLOAT);
      this.sub(builtin, BuiltinID.DOUBLE_SUB_DOUBLE, BasicTypeID.DOUBLE, BasicTypeID.DOUBLE, BuiltinID.USIZE_TO_DOUBLE);
      this.mul(builtin, BuiltinID.USIZE_MUL_USIZE, BasicTypeID.USIZE, BasicTypeID.USIZE);
      this.mul(builtin, BuiltinID.ULONG_MUL_ULONG, BasicTypeID.ULONG, BasicTypeID.ULONG, BuiltinID.USIZE_TO_ULONG);
      this.mul(builtin, BuiltinID.FLOAT_MUL_FLOAT, BasicTypeID.FLOAT, BasicTypeID.FLOAT, BuiltinID.USIZE_TO_FLOAT);
      this.mul(builtin, BuiltinID.DOUBLE_MUL_DOUBLE, BasicTypeID.DOUBLE, BasicTypeID.DOUBLE, BuiltinID.USIZE_TO_DOUBLE);
      this.div(builtin, BuiltinID.USIZE_DIV_USIZE, BasicTypeID.USIZE, BasicTypeID.USIZE);
      this.div(builtin, BuiltinID.ULONG_DIV_ULONG, BasicTypeID.ULONG, BasicTypeID.ULONG, BuiltinID.USIZE_TO_ULONG);
      this.div(builtin, BuiltinID.FLOAT_DIV_FLOAT, BasicTypeID.FLOAT, BasicTypeID.FLOAT, BuiltinID.USIZE_TO_FLOAT);
      this.div(builtin, BuiltinID.DOUBLE_DIV_DOUBLE, BasicTypeID.DOUBLE, BasicTypeID.DOUBLE, BuiltinID.USIZE_TO_DOUBLE);
      this.mod(builtin, BuiltinID.USIZE_MOD_USIZE, BasicTypeID.USIZE, BasicTypeID.USIZE);
      this.or(builtin, BuiltinID.USIZE_OR_USIZE, BasicTypeID.USIZE, BasicTypeID.USIZE);
      this.and(builtin, BuiltinID.USIZE_AND_USIZE, BasicTypeID.USIZE, BasicTypeID.USIZE);
      this.xor(builtin, BuiltinID.USIZE_XOR_USIZE, BasicTypeID.USIZE, BasicTypeID.USIZE);
      this.shl(builtin, BuiltinID.USIZE_SHL, BasicTypeID.USIZE, BasicTypeID.USIZE);
      this.shr(builtin, BuiltinID.USIZE_SHR, BasicTypeID.USIZE, BasicTypeID.USIZE);
      this.compare(builtin, BuiltinID.USIZE_COMPARE_UINT, BasicTypeID.UINT);
      this.compare(builtin, BuiltinID.USIZE_COMPARE, BasicTypeID.USIZE);
      this.compare(builtin, BuiltinID.ULONG_COMPARE, BasicTypeID.ULONG, BuiltinID.USIZE_TO_ULONG);
      this.compare(builtin, BuiltinID.FLOAT_COMPARE, BasicTypeID.FLOAT, BuiltinID.USIZE_TO_FLOAT);
      this.compare(builtin, BuiltinID.DOUBLE_COMPARE, BasicTypeID.DOUBLE, BuiltinID.USIZE_TO_DOUBLE);
      this.constant(builtin, BuiltinID.USIZE_GET_MIN_VALUE, "MIN_VALUE", new ConstantUSizeExpression(CodePosition.BUILTIN, 0L));
      this.constant(builtin, BuiltinID.USIZE_GET_MAX_VALUE, "MAX_VALUE", new ConstantUSizeExpression(CodePosition.BUILTIN, -2L));
      this.constant(builtin, BuiltinID.USIZE_BITS, "BITS", new ConstantUSizeExpression(CodePosition.BUILTIN, 32L));
      this.castExplicit(builtin, BuiltinID.USIZE_TO_BYTE, BasicTypeID.BYTE);
      this.castExplicit(builtin, BuiltinID.USIZE_TO_SBYTE, BasicTypeID.SBYTE);
      this.castExplicit(builtin, BuiltinID.USIZE_TO_SHORT, BasicTypeID.SHORT);
      this.castExplicit(builtin, BuiltinID.USIZE_TO_USHORT, BasicTypeID.USHORT);
      this.castExplicit(builtin, BuiltinID.USIZE_TO_INT, BasicTypeID.INT);
      this.castExplicit(builtin, BuiltinID.USIZE_TO_UINT, BasicTypeID.UINT);
      this.castImplicit(builtin, BuiltinID.USIZE_TO_LONG, BasicTypeID.LONG);
      this.castImplicit(builtin, BuiltinID.USIZE_TO_ULONG, BasicTypeID.ULONG);
      this.castImplicit(builtin, BuiltinID.USIZE_TO_FLOAT, BasicTypeID.FLOAT);
      this.castImplicit(builtin, BuiltinID.USIZE_TO_DOUBLE, BasicTypeID.DOUBLE);
      this.castExplicit(builtin, BuiltinID.USIZE_TO_CHAR, BasicTypeID.CHAR);
      this.castImplicit(builtin, BuiltinID.USIZE_TO_STRING, BasicTypeID.STRING);
      this.staticMethod(builtin, BuiltinID.USIZE_PARSE, "parse", BasicTypeID.USIZE, BasicTypeID.STRING);
      this.staticMethod(builtin, BuiltinID.USIZE_PARSE_WITH_BASE, "parse", BasicTypeID.USIZE, BasicTypeID.STRING, BasicTypeID.INT);
      this.method(builtin, BuiltinID.USIZE_COUNT_LOW_ZEROES, "countLowZeroes", BasicTypeID.USIZE);
      this.method(builtin, BuiltinID.USIZE_COUNT_HIGH_ZEROES, "countHighZeroes", BasicTypeID.USIZE);
      this.method(builtin, BuiltinID.USIZE_COUNT_LOW_ONES, "countLowOnes", BasicTypeID.USIZE);
      this.method(builtin, BuiltinID.USIZE_COUNT_HIGH_ONES, "countHighOnes", BasicTypeID.USIZE);
      TypeID optionalUSize = this.registry.getOptional(BasicTypeID.USIZE);
      this.getter(builtin, BuiltinID.USIZE_HIGHEST_ONE_BIT, "highestOneBit", optionalUSize);
      this.getter(builtin, BuiltinID.USIZE_LOWEST_ONE_BIT, "lowestOneBit", optionalUSize);
      this.getter(builtin, BuiltinID.USIZE_HIGHEST_ZERO_BIT, "highestZeroBit", optionalUSize);
      this.getter(builtin, BuiltinID.USIZE_LOWEST_ZERO_BIT, "lowestZeroBit", optionalUSize);
      this.getter(builtin, BuiltinID.USIZE_BIT_COUNT, "bitCount", BasicTypeID.USIZE);
      this.processType(builtin);
   }

   private void visitFloat() {
      ClassDefinition builtin = new ClassDefinition(CodePosition.BUILTIN, Module.BUILTIN, null, "float", 1, null);
      this.neg(builtin, BuiltinID.FLOAT_NEG, BasicTypeID.FLOAT);
      this.inc(builtin, BuiltinID.FLOAT_INC, BasicTypeID.FLOAT);
      this.dec(builtin, BuiltinID.FLOAT_DEC, BasicTypeID.FLOAT);
      this.addWithCastedOperand(builtin, BuiltinID.FLOAT_ADD_FLOAT, BasicTypeID.BYTE, BasicTypeID.FLOAT, BuiltinID.BYTE_TO_FLOAT);
      this.addWithCastedOperand(builtin, BuiltinID.FLOAT_ADD_FLOAT, BasicTypeID.SBYTE, BasicTypeID.FLOAT, BuiltinID.SBYTE_TO_FLOAT);
      this.addWithCastedOperand(builtin, BuiltinID.FLOAT_ADD_FLOAT, BasicTypeID.SHORT, BasicTypeID.FLOAT, BuiltinID.SHORT_TO_FLOAT);
      this.addWithCastedOperand(builtin, BuiltinID.FLOAT_ADD_FLOAT, BasicTypeID.USHORT, BasicTypeID.FLOAT, BuiltinID.USHORT_TO_FLOAT);
      this.addWithCastedOperand(builtin, BuiltinID.FLOAT_ADD_FLOAT, BasicTypeID.INT, BasicTypeID.FLOAT, BuiltinID.INT_TO_FLOAT);
      this.addWithCastedOperand(builtin, BuiltinID.FLOAT_ADD_FLOAT, BasicTypeID.UINT, BasicTypeID.FLOAT, BuiltinID.UINT_TO_FLOAT);
      this.addWithCastedOperand(builtin, BuiltinID.FLOAT_ADD_FLOAT, BasicTypeID.LONG, BasicTypeID.FLOAT, BuiltinID.LONG_TO_FLOAT);
      this.addWithCastedOperand(builtin, BuiltinID.FLOAT_ADD_FLOAT, BasicTypeID.ULONG, BasicTypeID.FLOAT, BuiltinID.ULONG_TO_FLOAT);
      this.addWithCastedOperand(builtin, BuiltinID.FLOAT_ADD_FLOAT, BasicTypeID.USIZE, BasicTypeID.FLOAT, BuiltinID.USIZE_TO_FLOAT);
      this.add(builtin, BuiltinID.FLOAT_ADD_FLOAT, BasicTypeID.FLOAT, BasicTypeID.FLOAT);
      this.add(builtin, BuiltinID.DOUBLE_ADD_DOUBLE, BasicTypeID.DOUBLE, BasicTypeID.DOUBLE, BuiltinID.FLOAT_TO_DOUBLE);
      this.subWithCastedOperand(builtin, BuiltinID.FLOAT_SUB_FLOAT, BasicTypeID.BYTE, BasicTypeID.FLOAT, BuiltinID.BYTE_TO_FLOAT);
      this.subWithCastedOperand(builtin, BuiltinID.FLOAT_SUB_FLOAT, BasicTypeID.SBYTE, BasicTypeID.FLOAT, BuiltinID.SBYTE_TO_FLOAT);
      this.subWithCastedOperand(builtin, BuiltinID.FLOAT_SUB_FLOAT, BasicTypeID.SHORT, BasicTypeID.FLOAT, BuiltinID.SHORT_TO_FLOAT);
      this.subWithCastedOperand(builtin, BuiltinID.FLOAT_SUB_FLOAT, BasicTypeID.USHORT, BasicTypeID.FLOAT, BuiltinID.USHORT_TO_FLOAT);
      this.subWithCastedOperand(builtin, BuiltinID.FLOAT_SUB_FLOAT, BasicTypeID.INT, BasicTypeID.FLOAT, BuiltinID.INT_TO_FLOAT);
      this.subWithCastedOperand(builtin, BuiltinID.FLOAT_SUB_FLOAT, BasicTypeID.UINT, BasicTypeID.FLOAT, BuiltinID.UINT_TO_FLOAT);
      this.subWithCastedOperand(builtin, BuiltinID.FLOAT_SUB_FLOAT, BasicTypeID.LONG, BasicTypeID.FLOAT, BuiltinID.LONG_TO_FLOAT);
      this.subWithCastedOperand(builtin, BuiltinID.FLOAT_SUB_FLOAT, BasicTypeID.ULONG, BasicTypeID.FLOAT, BuiltinID.ULONG_TO_FLOAT);
      this.subWithCastedOperand(builtin, BuiltinID.FLOAT_SUB_FLOAT, BasicTypeID.USIZE, BasicTypeID.FLOAT, BuiltinID.USIZE_TO_FLOAT);
      this.sub(builtin, BuiltinID.FLOAT_SUB_FLOAT, BasicTypeID.FLOAT, BasicTypeID.FLOAT);
      this.sub(builtin, BuiltinID.DOUBLE_SUB_DOUBLE, BasicTypeID.DOUBLE, BasicTypeID.DOUBLE, BuiltinID.LONG_TO_DOUBLE);
      this.mulWithCastedOperand(builtin, BuiltinID.FLOAT_MUL_FLOAT, BasicTypeID.BYTE, BasicTypeID.FLOAT, BuiltinID.BYTE_TO_FLOAT);
      this.mulWithCastedOperand(builtin, BuiltinID.FLOAT_MUL_FLOAT, BasicTypeID.SBYTE, BasicTypeID.FLOAT, BuiltinID.SBYTE_TO_FLOAT);
      this.mulWithCastedOperand(builtin, BuiltinID.FLOAT_MUL_FLOAT, BasicTypeID.SHORT, BasicTypeID.FLOAT, BuiltinID.SHORT_TO_FLOAT);
      this.mulWithCastedOperand(builtin, BuiltinID.FLOAT_MUL_FLOAT, BasicTypeID.USHORT, BasicTypeID.FLOAT, BuiltinID.USHORT_TO_FLOAT);
      this.mulWithCastedOperand(builtin, BuiltinID.FLOAT_MUL_FLOAT, BasicTypeID.INT, BasicTypeID.FLOAT, BuiltinID.INT_TO_FLOAT);
      this.mulWithCastedOperand(builtin, BuiltinID.FLOAT_MUL_FLOAT, BasicTypeID.UINT, BasicTypeID.FLOAT, BuiltinID.UINT_TO_FLOAT);
      this.mulWithCastedOperand(builtin, BuiltinID.FLOAT_MUL_FLOAT, BasicTypeID.LONG, BasicTypeID.FLOAT, BuiltinID.LONG_TO_FLOAT);
      this.mulWithCastedOperand(builtin, BuiltinID.FLOAT_MUL_FLOAT, BasicTypeID.ULONG, BasicTypeID.FLOAT, BuiltinID.ULONG_TO_FLOAT);
      this.mulWithCastedOperand(builtin, BuiltinID.FLOAT_MUL_FLOAT, BasicTypeID.USIZE, BasicTypeID.FLOAT, BuiltinID.USIZE_TO_FLOAT);
      this.mul(builtin, BuiltinID.FLOAT_MUL_FLOAT, BasicTypeID.FLOAT, BasicTypeID.FLOAT);
      this.mul(builtin, BuiltinID.DOUBLE_MUL_DOUBLE, BasicTypeID.DOUBLE, BasicTypeID.DOUBLE, BuiltinID.LONG_TO_DOUBLE);
      this.divWithCastedOperand(builtin, BuiltinID.FLOAT_DIV_FLOAT, BasicTypeID.BYTE, BasicTypeID.FLOAT, BuiltinID.BYTE_TO_FLOAT);
      this.divWithCastedOperand(builtin, BuiltinID.FLOAT_DIV_FLOAT, BasicTypeID.SBYTE, BasicTypeID.FLOAT, BuiltinID.SBYTE_TO_FLOAT);
      this.divWithCastedOperand(builtin, BuiltinID.FLOAT_DIV_FLOAT, BasicTypeID.SHORT, BasicTypeID.FLOAT, BuiltinID.SHORT_TO_FLOAT);
      this.divWithCastedOperand(builtin, BuiltinID.FLOAT_DIV_FLOAT, BasicTypeID.USHORT, BasicTypeID.FLOAT, BuiltinID.USHORT_TO_FLOAT);
      this.divWithCastedOperand(builtin, BuiltinID.FLOAT_DIV_FLOAT, BasicTypeID.INT, BasicTypeID.FLOAT, BuiltinID.INT_TO_FLOAT);
      this.divWithCastedOperand(builtin, BuiltinID.FLOAT_DIV_FLOAT, BasicTypeID.UINT, BasicTypeID.FLOAT, BuiltinID.UINT_TO_FLOAT);
      this.divWithCastedOperand(builtin, BuiltinID.FLOAT_DIV_FLOAT, BasicTypeID.LONG, BasicTypeID.FLOAT, BuiltinID.LONG_TO_FLOAT);
      this.divWithCastedOperand(builtin, BuiltinID.FLOAT_DIV_FLOAT, BasicTypeID.ULONG, BasicTypeID.FLOAT, BuiltinID.ULONG_TO_FLOAT);
      this.divWithCastedOperand(builtin, BuiltinID.FLOAT_DIV_FLOAT, BasicTypeID.USIZE, BasicTypeID.FLOAT, BuiltinID.USIZE_TO_FLOAT);
      this.div(builtin, BuiltinID.FLOAT_DIV_FLOAT, BasicTypeID.FLOAT, BasicTypeID.FLOAT);
      this.div(builtin, BuiltinID.DOUBLE_DIV_DOUBLE, BasicTypeID.DOUBLE, BasicTypeID.DOUBLE, BuiltinID.LONG_TO_DOUBLE);
      this.compare(builtin, BuiltinID.FLOAT_COMPARE, BasicTypeID.FLOAT);
      this.compare(builtin, BuiltinID.DOUBLE_COMPARE, BasicTypeID.DOUBLE, BuiltinID.LONG_TO_DOUBLE);
      this.constant(builtin, BuiltinID.FLOAT_GET_MIN_VALUE, "MIN_VALUE", new ConstantFloatExpression(CodePosition.BUILTIN, Float.MIN_VALUE));
      this.constant(builtin, BuiltinID.FLOAT_GET_MAX_VALUE, "MAX_VALUE", new ConstantFloatExpression(CodePosition.BUILTIN, Float.MAX_VALUE));
      this.castExplicit(builtin, BuiltinID.FLOAT_TO_BYTE, BasicTypeID.BYTE);
      this.castExplicit(builtin, BuiltinID.FLOAT_TO_SBYTE, BasicTypeID.SBYTE);
      this.castExplicit(builtin, BuiltinID.FLOAT_TO_SHORT, BasicTypeID.SHORT);
      this.castExplicit(builtin, BuiltinID.FLOAT_TO_USHORT, BasicTypeID.USHORT);
      this.castExplicit(builtin, BuiltinID.FLOAT_TO_INT, BasicTypeID.INT);
      this.castExplicit(builtin, BuiltinID.FLOAT_TO_UINT, BasicTypeID.UINT);
      this.castExplicit(builtin, BuiltinID.FLOAT_TO_LONG, BasicTypeID.LONG);
      this.castExplicit(builtin, BuiltinID.FLOAT_TO_ULONG, BasicTypeID.ULONG);
      this.castExplicit(builtin, BuiltinID.FLOAT_TO_USIZE, BasicTypeID.USIZE);
      this.castImplicit(builtin, BuiltinID.FLOAT_TO_DOUBLE, BasicTypeID.DOUBLE);
      this.castImplicit(builtin, BuiltinID.FLOAT_TO_STRING, BasicTypeID.STRING);
      this.staticMethod(builtin, BuiltinID.FLOAT_PARSE, "parse", BasicTypeID.FLOAT, BasicTypeID.STRING);
      this.staticMethod(builtin, BuiltinID.FLOAT_FROM_BITS, "fromBits", BasicTypeID.FLOAT, BasicTypeID.UINT);
      this.getter(builtin, BuiltinID.FLOAT_BITS, "bits", BasicTypeID.UINT);
      this.processType(builtin);
   }

   private void visitDouble() {
      ClassDefinition builtin = new ClassDefinition(CodePosition.BUILTIN, Module.BUILTIN, null, "double", 1, null);
      this.neg(builtin, BuiltinID.DOUBLE_NEG, BasicTypeID.DOUBLE);
      this.inc(builtin, BuiltinID.DOUBLE_INC, BasicTypeID.DOUBLE);
      this.dec(builtin, BuiltinID.DOUBLE_DEC, BasicTypeID.DOUBLE);
      this.add(builtin, BuiltinID.DOUBLE_ADD_DOUBLE, BasicTypeID.DOUBLE, BasicTypeID.DOUBLE);
      this.sub(builtin, BuiltinID.DOUBLE_SUB_DOUBLE, BasicTypeID.DOUBLE, BasicTypeID.DOUBLE);
      this.mul(builtin, BuiltinID.DOUBLE_MUL_DOUBLE, BasicTypeID.DOUBLE, BasicTypeID.DOUBLE);
      this.div(builtin, BuiltinID.DOUBLE_DIV_DOUBLE, BasicTypeID.DOUBLE, BasicTypeID.DOUBLE);
      this.compare(builtin, BuiltinID.DOUBLE_COMPARE, BasicTypeID.DOUBLE);
      this.constant(builtin, BuiltinID.DOUBLE_GET_MIN_VALUE, "MIN_VALUE", new ConstantDoubleExpression(CodePosition.BUILTIN, Double.MIN_VALUE));
      this.constant(builtin, BuiltinID.DOUBLE_GET_MAX_VALUE, "MAX_VALUE", new ConstantDoubleExpression(CodePosition.BUILTIN, Double.MAX_VALUE));
      this.castExplicit(builtin, BuiltinID.DOUBLE_TO_BYTE, BasicTypeID.BYTE);
      this.castExplicit(builtin, BuiltinID.DOUBLE_TO_SBYTE, BasicTypeID.SBYTE);
      this.castExplicit(builtin, BuiltinID.DOUBLE_TO_SHORT, BasicTypeID.SHORT);
      this.castExplicit(builtin, BuiltinID.DOUBLE_TO_USHORT, BasicTypeID.USHORT);
      this.castExplicit(builtin, BuiltinID.DOUBLE_TO_INT, BasicTypeID.INT);
      this.castExplicit(builtin, BuiltinID.DOUBLE_TO_UINT, BasicTypeID.UINT);
      this.castExplicit(builtin, BuiltinID.DOUBLE_TO_LONG, BasicTypeID.LONG);
      this.castExplicit(builtin, BuiltinID.DOUBLE_TO_ULONG, BasicTypeID.ULONG);
      this.castExplicit(builtin, BuiltinID.DOUBLE_TO_USIZE, BasicTypeID.USIZE);
      this.castImplicit(builtin, BuiltinID.DOUBLE_TO_FLOAT, BasicTypeID.FLOAT);
      this.castImplicit(builtin, BuiltinID.DOUBLE_TO_STRING, BasicTypeID.STRING);
      this.staticMethod(builtin, BuiltinID.DOUBLE_PARSE, "parse", BasicTypeID.DOUBLE, BasicTypeID.STRING);
      this.staticMethod(builtin, BuiltinID.DOUBLE_FROM_BITS, "fromBits", BasicTypeID.DOUBLE, BasicTypeID.ULONG);
      this.getter(builtin, BuiltinID.DOUBLE_BITS, "bits", BasicTypeID.ULONG);
      this.processType(builtin);
   }

   private void visitChar() {
      ClassDefinition builtin = new ClassDefinition(CodePosition.BUILTIN, Module.BUILTIN, null, "char", 1, null);
      this.add(builtin, BuiltinID.CHAR_ADD_INT, BasicTypeID.INT, BasicTypeID.CHAR);
      this.sub(builtin, BuiltinID.CHAR_SUB_INT, BasicTypeID.INT, BasicTypeID.CHAR);
      this.sub(builtin, BuiltinID.CHAR_SUB_CHAR, BasicTypeID.CHAR, BasicTypeID.INT);
      this.compare(builtin, BuiltinID.CHAR_COMPARE, BasicTypeID.CHAR);
      this.castExplicit(builtin, BuiltinID.CHAR_TO_BYTE, BasicTypeID.BYTE);
      this.castExplicit(builtin, BuiltinID.CHAR_TO_SBYTE, BasicTypeID.SBYTE);
      this.castExplicit(builtin, BuiltinID.CHAR_TO_SHORT, BasicTypeID.SHORT);
      this.castExplicit(builtin, BuiltinID.CHAR_TO_USHORT, BasicTypeID.USHORT);
      this.castImplicit(builtin, BuiltinID.CHAR_TO_INT, BasicTypeID.INT);
      this.castImplicit(builtin, BuiltinID.CHAR_TO_UINT, BasicTypeID.UINT);
      this.castImplicit(builtin, BuiltinID.CHAR_TO_LONG, BasicTypeID.LONG);
      this.castImplicit(builtin, BuiltinID.CHAR_TO_ULONG, BasicTypeID.ULONG);
      this.castImplicit(builtin, BuiltinID.CHAR_TO_USIZE, BasicTypeID.USIZE);
      this.castImplicit(builtin, BuiltinID.CHAR_TO_STRING, BasicTypeID.STRING);
      this.getter(builtin, BuiltinID.CHAR_GET_MIN_VALUE, "MIN_VALUE", BasicTypeID.CHAR);
      this.getter(builtin, BuiltinID.CHAR_GET_MAX_VALUE, "MAX_VALUE", BasicTypeID.CHAR);
      this.method(builtin, BuiltinID.CHAR_REMOVE_DIACRITICS, "removeDiacritics", BasicTypeID.CHAR);
      this.method(builtin, BuiltinID.CHAR_TO_LOWER_CASE, "toLowerCase", BasicTypeID.CHAR);
      this.method(builtin, BuiltinID.CHAR_TO_UPPER_CASE, "toUpperCase", BasicTypeID.CHAR);
      this.processType(builtin);
   }

   private void castedTargetCall(OperatorMember member, TypeID toType, BuiltinID casterBuiltin) {
      CasterMemberRef caster = this.castImplicitRef(member.definition, casterBuiltin, toType);
      TranslatedOperatorMemberRef method = new TranslatedOperatorMemberRef(
         member,
         this.members.type,
         GenericMapper.EMPTY,
         call -> member.ref(this.members.type, null).call(call.position, caster.cast(call.position, call.target, true), call.arguments, call.scope)
      );
      this.members.getOrCreateGroup(member.operator).addMethod(method, TypeMemberPriority.SPECIFIED);
   }

   private void castedOperandCall(OperatorMember member, TypeID toType, BuiltinID casterBuiltin) {
      CasterMemberRef caster = this.castImplicitRef(member.definition, casterBuiltin, toType);
      TranslatedOperatorMemberRef method = new TranslatedOperatorMemberRef(
         member,
         this.members.type,
         GenericMapper.EMPTY,
         call -> member.ref(this.members.type, null)
            .call(call.position, call.target, new CallArguments(caster.cast(call.position, call.arguments.arguments[0], true)), call.scope)
      );
      this.members.getOrCreateGroup(member.operator).addMethod(method, TypeMemberPriority.SPECIFIED);
   }

   private void register(IDefinitionMember member) {
      member.registerTo(this.members, TypeMemberPriority.SPECIFIED, null);
   }

   private void operator(HighLevelDefinition definition, OperatorType operator, FunctionHeader header, BuiltinID builtin) {
      this.members.addOperator(operator, new OperatorMember(CodePosition.BUILTIN, definition, 1, operator, header, builtin).ref(this.members.type, null));
   }

   private void not(HighLevelDefinition cls, BuiltinID id, TypeID result) {
      this.operator(cls, OperatorType.NOT, new FunctionHeader(result), id);
   }

   private void invert(HighLevelDefinition cls, BuiltinID id, TypeID result) {
      this.operator(cls, OperatorType.INVERT, new FunctionHeader(result), id);
   }

   private void neg(HighLevelDefinition cls, BuiltinID id, TypeID result) {
      this.operator(cls, OperatorType.NEG, new FunctionHeader(result), id);
   }

   private void inc(HighLevelDefinition cls, BuiltinID id, TypeID result) {
      this.operator(cls, OperatorType.INCREMENT, new FunctionHeader(result), id);
   }

   private void dec(HighLevelDefinition cls, BuiltinID id, TypeID result) {
      this.operator(cls, OperatorType.DECREMENT, new FunctionHeader(result), id);
   }

   private OperatorMember addOp(HighLevelDefinition definition, BuiltinID id, TypeID operand, TypeID result) {
      return new OperatorMember(CodePosition.BUILTIN, definition, 1, OperatorType.ADD, new FunctionHeader(result, new FunctionParameter(operand)), id);
   }

   private void add(HighLevelDefinition definition, BuiltinID id, TypeID operand, TypeID result) {
      this.addOp(definition, id, operand, result).registerTo(this.members, TypeMemberPriority.SPECIFIED, null);
   }

   private void add(HighLevelDefinition definition, BuiltinID id, BasicTypeID operand, BasicTypeID result, BuiltinID caster) {
      this.castedTargetCall(this.addOp(definition, id, operand, result), result, caster);
   }

   private void addWithCastedOperand(HighLevelDefinition definition, BuiltinID id, BasicTypeID operand, BasicTypeID result, BuiltinID caster) {
      this.castedOperandCall(this.addOp(definition, id, operand, result), operand, caster);
   }

   private OperatorMember subOp(HighLevelDefinition cls, BuiltinID id, TypeID operand, TypeID result) {
      return new OperatorMember(CodePosition.BUILTIN, cls, 1, OperatorType.SUB, new FunctionHeader(result, new FunctionParameter(operand)), id);
   }

   private void sub(HighLevelDefinition cls, BuiltinID id, BasicTypeID operand, BasicTypeID result) {
      this.subOp(cls, id, operand, result).registerTo(this.members, TypeMemberPriority.SPECIFIED, null);
   }

   private void sub(HighLevelDefinition definition, BuiltinID id, BasicTypeID operand, BasicTypeID result, BuiltinID caster) {
      this.castedTargetCall(this.subOp(definition, id, operand, result), result, caster);
   }

   private void subWithCastedOperand(HighLevelDefinition definition, BuiltinID id, BasicTypeID operand, BasicTypeID result, BuiltinID caster) {
      this.castedOperandCall(this.subOp(definition, id, operand, result), operand, caster);
   }

   private OperatorMember mulOp(HighLevelDefinition cls, BuiltinID id, TypeID operand, TypeID result) {
      return new OperatorMember(CodePosition.BUILTIN, cls, 1, OperatorType.MUL, new FunctionHeader(result, new FunctionParameter(operand)), id);
   }

   private void mul(HighLevelDefinition cls, BuiltinID id, BasicTypeID operand, BasicTypeID result) {
      this.mulOp(cls, id, operand, result).registerTo(this.members, TypeMemberPriority.SPECIFIED, null);
   }

   private void mul(HighLevelDefinition definition, BuiltinID id, BasicTypeID operand, BasicTypeID result, BuiltinID caster) {
      this.castedTargetCall(this.mulOp(definition, id, operand, result), result, caster);
   }

   private void mulWithCastedOperand(HighLevelDefinition definition, BuiltinID id, BasicTypeID operand, BasicTypeID result, BuiltinID caster) {
      this.castedOperandCall(this.mulOp(definition, id, operand, result), operand, caster);
   }

   private OperatorMember divOp(HighLevelDefinition cls, BuiltinID id, TypeID operand, TypeID result) {
      return new OperatorMember(CodePosition.BUILTIN, cls, 1, OperatorType.DIV, new FunctionHeader(result, new FunctionParameter(operand)), id);
   }

   private void div(HighLevelDefinition cls, BuiltinID id, BasicTypeID operand, BasicTypeID result) {
      this.divOp(cls, id, operand, result).registerTo(this.members, TypeMemberPriority.SPECIFIED, null);
   }

   private void div(HighLevelDefinition definition, BuiltinID id, BasicTypeID operand, BasicTypeID result, BuiltinID caster) {
      this.castedTargetCall(this.divOp(definition, id, operand, result), result, caster);
   }

   private void divWithCastedOperand(HighLevelDefinition definition, BuiltinID id, BasicTypeID operand, BasicTypeID result, BuiltinID caster) {
      this.castedOperandCall(this.divOp(definition, id, operand, result), operand, caster);
   }

   private OperatorMember modOp(HighLevelDefinition cls, BuiltinID id, TypeID operand, TypeID result) {
      return new OperatorMember(CodePosition.BUILTIN, cls, 1, OperatorType.MOD, new FunctionHeader(result, new FunctionParameter(operand)), id);
   }

   private void mod(HighLevelDefinition cls, BuiltinID id, BasicTypeID operand, BasicTypeID result) {
      this.modOp(cls, id, operand, result).registerTo(this.members, TypeMemberPriority.SPECIFIED, null);
   }

   private void mod(HighLevelDefinition definition, BuiltinID id, BasicTypeID operand, BasicTypeID result, BuiltinID caster) {
      this.castedTargetCall(this.modOp(definition, id, operand, result), result, caster);
   }

   private OperatorMember shlOp(HighLevelDefinition cls, BuiltinID id, TypeID operand, TypeID result) {
      return new OperatorMember(CodePosition.BUILTIN, cls, 1, OperatorType.SHL, new FunctionHeader(result, new FunctionParameter(operand)), id);
   }

   private void shl(HighLevelDefinition cls, BuiltinID id, BasicTypeID operand, BasicTypeID result) {
      this.shlOp(cls, id, operand, result).registerTo(this.members, TypeMemberPriority.SPECIFIED, null);
   }

   private OperatorMember shrOp(HighLevelDefinition cls, BuiltinID id, TypeID operand, TypeID result) {
      return new OperatorMember(CodePosition.BUILTIN, cls, 1, OperatorType.SHR, new FunctionHeader(result, new FunctionParameter(operand)), id);
   }

   private void shr(HighLevelDefinition cls, BuiltinID id, BasicTypeID operand, BasicTypeID result) {
      this.shrOp(cls, id, operand, result).registerTo(this.members, TypeMemberPriority.SPECIFIED, null);
   }

   private OperatorMember ushrOp(HighLevelDefinition cls, BuiltinID id, TypeID operand, TypeID result) {
      return new OperatorMember(CodePosition.BUILTIN, cls, 1, OperatorType.USHR, new FunctionHeader(result, new FunctionParameter(operand)), id);
   }

   private void ushr(HighLevelDefinition cls, BuiltinID id, BasicTypeID operand, BasicTypeID result) {
      this.ushrOp(cls, id, operand, result).registerTo(this.members, TypeMemberPriority.SPECIFIED, null);
   }

   private OperatorMember orOp(HighLevelDefinition cls, BuiltinID id, TypeID operand, TypeID result) {
      return new OperatorMember(CodePosition.BUILTIN, cls, 1, OperatorType.OR, new FunctionHeader(result, new FunctionParameter(operand)), id);
   }

   private void or(HighLevelDefinition cls, BuiltinID id, BasicTypeID operand, BasicTypeID result) {
      this.orOp(cls, id, operand, result).registerTo(this.members, TypeMemberPriority.SPECIFIED, null);
   }

   private void or(HighLevelDefinition definition, BuiltinID id, BasicTypeID operand, BasicTypeID result, BuiltinID caster) {
      this.orOp(definition, id, operand, result).registerTo(this.members, TypeMemberPriority.SPECIFIED, null);
   }

   private OperatorMember andOp(HighLevelDefinition cls, BuiltinID id, TypeID operand, TypeID result) {
      return new OperatorMember(CodePosition.BUILTIN, cls, 1, OperatorType.AND, new FunctionHeader(result, new FunctionParameter(operand)), id);
   }

   private void and(HighLevelDefinition cls, BuiltinID id, BasicTypeID operand, BasicTypeID result) {
      this.andOp(cls, id, operand, result).registerTo(this.members, TypeMemberPriority.SPECIFIED, null);
   }

   private void and(HighLevelDefinition definition, BuiltinID id, BasicTypeID operand, BasicTypeID result, BuiltinID caster) {
      this.castedTargetCall(this.andOp(definition, id, operand, result), result, caster);
   }

   private OperatorMember xorOp(HighLevelDefinition cls, BuiltinID id, TypeID operand, TypeID result) {
      return new OperatorMember(CodePosition.BUILTIN, cls, 1, OperatorType.XOR, new FunctionHeader(result, new FunctionParameter(operand)), id);
   }

   private void xor(HighLevelDefinition cls, BuiltinID id, BasicTypeID operand, BasicTypeID result) {
      this.xorOp(cls, id, operand, result).registerTo(this.members, TypeMemberPriority.SPECIFIED, null);
   }

   private void xor(HighLevelDefinition definition, BuiltinID id, BasicTypeID operand, BasicTypeID result, BuiltinID caster) {
      this.castedTargetCall(this.xorOp(definition, id, operand, result), result, caster);
   }

   private void indexGet(HighLevelDefinition cls, BuiltinID id, TypeID operand, TypeID result) {
      new OperatorMember(CodePosition.BUILTIN, cls, 1, OperatorType.INDEXGET, new FunctionHeader(result, new FunctionParameter(operand)), id)
         .registerTo(this.members, TypeMemberPriority.SPECIFIED, null);
   }

   private void indexSet(HighLevelDefinition cls, BuiltinID id, TypeID operand, TypeID value) {
      new OperatorMember(CodePosition.BUILTIN, cls, 1, OperatorType.INDEXSET, new FunctionHeader(BasicTypeID.VOID, operand, value), id)
         .registerTo(this.members, TypeMemberPriority.SPECIFIED, null);
   }

   private OperatorMember compareOp(HighLevelDefinition cls, BuiltinID id, TypeID operand) {
      return new OperatorMember(CodePosition.BUILTIN, cls, 1, OperatorType.COMPARE, new FunctionHeader(BasicTypeID.INT, new FunctionParameter(operand)), id);
   }

   private void compare(HighLevelDefinition cls, BuiltinID id, TypeID operand) {
      this.compareOp(cls, id, operand).registerTo(this.members, TypeMemberPriority.SPECIFIED, null);
   }

   private void compare(HighLevelDefinition definition, BuiltinID id, TypeID operand, BuiltinID caster) {
      this.castedTargetCall(this.compareOp(definition, id, operand), operand, caster);
   }

   private void getter(HighLevelDefinition cls, BuiltinID id, String name, TypeID type) {
      new GetterMember(CodePosition.BUILTIN, cls, 1, name, type, id).registerTo(this.members, TypeMemberPriority.SPECIFIED, null);
   }

   private void constant(HighLevelDefinition cls, BuiltinID id, String name, Expression value) {
      ConstMember result = new ConstMember(CodePosition.BUILTIN, cls, 129, name, value.type, id);
      result.value = value;
      result.registerTo(this.members, TypeMemberPriority.SPECIFIED, null);
   }

   private void constructor(HighLevelDefinition definition, BuiltinID id, FunctionHeader header) {
      new ConstructorMember(CodePosition.BUILTIN, definition, 1, header, id).registerTo(this.members, TypeMemberPriority.SPECIFIED, null);
   }

   private void constructor(HighLevelDefinition definition, BuiltinID id, TypeID... arguments) {
      new ConstructorMember(CodePosition.BUILTIN, definition, 1, new FunctionHeader(BasicTypeID.VOID, arguments), id)
         .registerTo(this.members, TypeMemberPriority.SPECIFIED, null);
   }

   private void method(HighLevelDefinition definition, String name, FunctionHeader header, BuiltinID builtin) {
      this.register(new MethodMember(CodePosition.BUILTIN, definition, 2049, name, header, builtin));
   }

   private void method(ClassDefinition cls, BuiltinID id, String name, TypeID result, TypeID... arguments) {
      this.register(new MethodMember(CodePosition.BUILTIN, cls, 2049, name, new FunctionHeader(result, arguments), id));
   }

   private void staticMethod(ClassDefinition cls, BuiltinID id, String name, TypeID result, TypeID... arguments) {
      this.register(new MethodMember(CodePosition.BUILTIN, cls, 2177, name, new FunctionHeader(result, arguments), id));
   }

   private void castExplicit(HighLevelDefinition cls, BuiltinID id, TypeID result) {
      new CasterMember(CodePosition.BUILTIN, cls, 1, result, id).registerTo(this.members, TypeMemberPriority.SPECIFIED, null);
   }

   private void castImplicit(HighLevelDefinition cls, BuiltinID id, TypeID result) {
      new CasterMember(CodePosition.BUILTIN, cls, 513, result, id).registerTo(this.members, TypeMemberPriority.SPECIFIED, null);
   }

   private CasterMemberRef castImplicitRef(HighLevelDefinition definition, BuiltinID id, TypeID result) {
      return new CasterMemberRef(new CasterMember(CodePosition.BUILTIN, definition, 513, result, id), this.members.type, result);
   }

   private void equals(HighLevelDefinition cls, BuiltinID id, TypeID type) {
      new OperatorMember(CodePosition.BUILTIN, cls, 1, OperatorType.EQUALS, new FunctionHeader(BasicTypeID.BOOL, new FunctionParameter(type)), id)
         .registerTo(this.members, TypeMemberPriority.SPECIFIED, null);
   }

   private void same(HighLevelDefinition cls, BuiltinID id, TypeID type) {
      new OperatorMember(CodePosition.BUILTIN, cls, 1, OperatorType.SAME, new FunctionHeader(BasicTypeID.BOOL, new FunctionParameter(type)), id)
         .registerTo(this.members, TypeMemberPriority.SPECIFIED, null);
   }

   private void notequals(HighLevelDefinition cls, BuiltinID id, TypeID type) {
      new OperatorMember(CodePosition.BUILTIN, cls, 1, OperatorType.NOTEQUALS, new FunctionHeader(BasicTypeID.BOOL, new FunctionParameter(type)), id)
         .registerTo(this.members, TypeMemberPriority.SPECIFIED, null);
   }

   private void notsame(HighLevelDefinition cls, BuiltinID id, TypeID type) {
      new OperatorMember(CodePosition.BUILTIN, cls, 1, OperatorType.NOTSAME, new FunctionHeader(BasicTypeID.BOOL, new FunctionParameter(type)), id)
         .registerTo(this.members, TypeMemberPriority.SPECIFIED, null);
   }

   private void iterator(HighLevelDefinition cls, BuiltinID builtin, TypeID... types) {
      new IteratorMember(CodePosition.BUILTIN, cls, 1, types, this.registry, builtin).registerTo(this.members, TypeMemberPriority.SPECIFIED, null);
   }
}
