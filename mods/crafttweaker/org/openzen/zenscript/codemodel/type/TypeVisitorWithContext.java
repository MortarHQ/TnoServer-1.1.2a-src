package org.openzen.zenscript.codemodel.type;

public interface TypeVisitorWithContext<C, R, E extends Exception> {
   R visitBasic(C var1, BasicTypeID var2) throws E;

   R visitArray(C var1, ArrayTypeID var2) throws E;

   R visitAssoc(C var1, AssocTypeID var2) throws E;

   R visitGenericMap(C var1, GenericMapTypeID var2) throws E;

   R visitIterator(C var1, IteratorTypeID var2) throws E;

   R visitFunction(C var1, FunctionTypeID var2) throws E;

   R visitDefinition(C var1, DefinitionTypeID var2) throws E;

   R visitGeneric(C var1, GenericTypeID var2) throws E;

   R visitRange(C var1, RangeTypeID var2) throws E;

   R visitOptional(C var1, OptionalTypeID var2) throws E;

   default R visitInvalid(C context, InvalidTypeID type) throws E {
      throw new RuntimeException("Invalid type: " + type.message);
   }
}
