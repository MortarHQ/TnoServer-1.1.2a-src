package org.openzen.zenscript.codemodel.definition;

public interface DefinitionVisitorWithContext<C, R> {
   R visitClass(C var1, ClassDefinition var2);

   R visitInterface(C var1, InterfaceDefinition var2);

   R visitEnum(C var1, EnumDefinition var2);

   R visitStruct(C var1, StructDefinition var2);

   R visitFunction(C var1, FunctionDefinition var2);

   R visitExpansion(C var1, ExpansionDefinition var2);

   R visitAlias(C var1, AliasDefinition var2);

   R visitVariant(C var1, VariantDefinition var2);
}
