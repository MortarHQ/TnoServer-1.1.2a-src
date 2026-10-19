package org.openzen.zenscript.codemodel.type;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.openzen.zenscript.codemodel.FunctionHeader;
import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.codemodel.definition.ZSPackage;
import org.openzen.zenscript.codemodel.generic.TypeParameter;

public class GlobalTypeRegistry {
   public final ZSPackage stdlib;
   private final Map<Class<?>, Map<?, ? extends TypeID>> identityMaps = new HashMap<>();
   private final Map<ArrayTypeID, ArrayTypeID> arrayTypes = new HashMap<>();
   private final Map<AssocTypeID, AssocTypeID> assocTypes = new HashMap<>();
   private final Map<GenericMapTypeID, GenericMapTypeID> genericMapTypes = new HashMap<>();
   private final Map<IteratorTypeID, IteratorTypeID> iteratorTypes = new HashMap<>();
   private final Map<FunctionTypeID, FunctionTypeID> functionTypes = new HashMap<>();
   private final Map<RangeTypeID, RangeTypeID> rangeTypes = new HashMap<>();
   private final Map<DefinitionTypeID, DefinitionTypeID> definitionTypes = new HashMap<>();
   private final Map<GenericTypeID, GenericTypeID> genericTypes = new HashMap<>();
   private final Map<OptionalTypeID, OptionalTypeID> optionalTypes = new HashMap<>();

   public GlobalTypeRegistry(ZSPackage stdlib) {
      this.stdlib = stdlib;
      this.arrayTypes.put(ArrayTypeID.INT, ArrayTypeID.INT);
      this.arrayTypes.put(ArrayTypeID.CHAR, ArrayTypeID.CHAR);
      this.rangeTypes.put(RangeTypeID.INT, RangeTypeID.INT);
      this.rangeTypes.put(RangeTypeID.USIZE, RangeTypeID.USIZE);
      this.identityMaps.put(ArrayTypeID.class, this.arrayTypes);
      this.identityMaps.put(AssocTypeID.class, this.assocTypes);
      this.identityMaps.put(GenericMapTypeID.class, this.genericMapTypes);
      this.identityMaps.put(IteratorTypeID.class, this.iteratorTypes);
      this.identityMaps.put(FunctionTypeID.class, this.functionTypes);
      this.identityMaps.put(RangeTypeID.class, this.rangeTypes);
      this.identityMaps.put(DefinitionTypeID.class, this.definitionTypes);
      this.identityMaps.put(GenericTypeID.class, this.genericTypes);
      this.identityMaps.put(OptionalTypeID.class, this.optionalTypes);
   }

   public ArrayTypeID getArray(TypeID baseType, int dimension) {
      ArrayTypeID id = new ArrayTypeID(this, baseType, dimension);
      return this.internalize(this.arrayTypes, id);
   }

   public AssocTypeID getAssociative(TypeID keyType, TypeID valueType) {
      AssocTypeID id = new AssocTypeID(this, keyType, valueType);
      return this.internalize(this.assocTypes, id);
   }

   public GenericMapTypeID getGenericMap(TypeID valueType, TypeParameter key) {
      GenericMapTypeID id = new GenericMapTypeID(this, valueType, key);
      return this.internalize(this.genericMapTypes, id);
   }

   public IteratorTypeID getIterator(TypeID[] loopTypes) {
      IteratorTypeID id = new IteratorTypeID(this, loopTypes);
      return this.internalize(this.iteratorTypes, id);
   }

   public FunctionTypeID getFunction(FunctionHeader header) {
      FunctionTypeID id = new FunctionTypeID(this, header);
      return this.internalize(this.functionTypes, id);
   }

   public RangeTypeID getRange(TypeID type) {
      RangeTypeID id = new RangeTypeID(this, type);
      return this.internalize(this.rangeTypes, id);
   }

   public GenericTypeID getGeneric(TypeParameter parameter) {
      GenericTypeID id = new GenericTypeID(parameter);
      return this.internalize(this.genericTypes, id);
   }

   public DefinitionTypeID getForMyDefinition(HighLevelDefinition definition) {
      TypeID[] typeArguments = TypeID.NONE;
      if (definition.getNumberOfGenericParameters() > 0) {
         typeArguments = new TypeID[definition.getNumberOfGenericParameters()];

         for (int i = 0; i < definition.typeParameters.length; i++) {
            typeArguments[i] = this.getGeneric(definition.typeParameters[i]);
         }
      }

      DefinitionTypeID outer = null;
      if (definition.outerDefinition != null) {
         outer = this.getForMyDefinition(definition.outerDefinition);
      }

      return this.getForDefinition(definition, typeArguments, outer);
   }

   public DefinitionTypeID getForDefinition(HighLevelDefinition definition, TypeID... typeArguments) {
      return this.getForDefinition(definition, typeArguments, null);
   }

   public DefinitionTypeID getForDefinition(HighLevelDefinition definition, TypeID[] typeArguments, DefinitionTypeID outer) {
      DefinitionTypeID id = new DefinitionTypeID(this, definition, typeArguments, definition.isStatic() ? null : outer);
      return this.internalize(this.definitionTypes, id);
   }

   public TypeID getOptional(TypeID original) {
      return this.internalize(this.optionalTypes, new OptionalTypeID(this, original));
   }

   private <T> T internalize(Map<T, T> identityMap, T id) {
      if (identityMap.containsKey(id)) {
         return identityMap.get(id);
      }

      identityMap.put(id, id);
      return id;
   }

   public <T extends TypeID> T internalize(Class<T> clazz, T id) {
      Map<T, T> identityMap = (Map<T, T>)this.identityMaps.computeIfAbsent(clazz, aClass -> new HashMap<>());
      if (identityMap.containsKey(id)) {
         return identityMap.get(id);
      }

      identityMap.put(id, id);
      return id;
   }

   public <ID, TYPE extends TypeID> TYPE internalize(Class<TYPE> typeClass, ID id, Function<ID, TYPE> generator) {
      Map<ID, TYPE> identityMap = (Map<ID, TYPE>)this.identityMaps.computeIfAbsent(typeClass, aClass -> new HashMap<>());
      if (identityMap.containsKey(id)) {
         return identityMap.get(id);
      }

      TYPE value = (TYPE)generator.apply(id);
      identityMap.put(id, value);
      return value;
   }

   public Collection<DefinitionTypeID> getDefinitions() {
      return this.definitionTypes.keySet();
   }
}
