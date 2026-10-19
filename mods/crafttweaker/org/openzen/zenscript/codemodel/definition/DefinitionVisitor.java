package org.openzen.zenscript.codemodel.definition;

public interface DefinitionVisitor<T> {
   T visitClass(ClassDefinition var1);

   T visitInterface(InterfaceDefinition var1);

   T visitEnum(EnumDefinition var1);

   T visitStruct(StructDefinition var1);

   T visitFunction(FunctionDefinition var1);

   T visitExpansion(ExpansionDefinition var1);

   T visitAlias(AliasDefinition var1);

   T visitVariant(VariantDefinition var1);
}
