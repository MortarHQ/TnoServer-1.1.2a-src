package org.openzen.zenscript.codemodel.type;

public interface TypeVisitor<T> {
   T visitBasic(BasicTypeID var1);

   T visitArray(ArrayTypeID var1);

   T visitAssoc(AssocTypeID var1);

   T visitGenericMap(GenericMapTypeID var1);

   T visitIterator(IteratorTypeID var1);

   T visitFunction(FunctionTypeID var1);

   T visitDefinition(DefinitionTypeID var1);

   T visitGeneric(GenericTypeID var1);

   T visitRange(RangeTypeID var1);

   T visitOptional(OptionalTypeID var1);

   default T visitInvalid(InvalidTypeID type) {
      throw new UnsupportedOperationException("Invalid type @ " + type.position + ": " + type.message);
   }
}
