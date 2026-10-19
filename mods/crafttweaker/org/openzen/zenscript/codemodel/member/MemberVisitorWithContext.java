package org.openzen.zenscript.codemodel.member;

public interface MemberVisitorWithContext<C, R> {
   R visitConst(C var1, ConstMember var2);

   R visitField(C var1, FieldMember var2);

   R visitConstructor(C var1, ConstructorMember var2);

   R visitDestructor(C var1, DestructorMember var2);

   R visitMethod(C var1, MethodMember var2);

   R visitGetter(C var1, GetterMember var2);

   R visitSetter(C var1, SetterMember var2);

   R visitOperator(C var1, OperatorMember var2);

   R visitCaster(C var1, CasterMember var2);

   R visitIterator(C var1, IteratorMember var2);

   R visitCaller(C var1, CallerMember var2);

   R visitImplementation(C var1, ImplementationMember var2);

   R visitInnerDefinition(C var1, InnerDefinitionMember var2);

   R visitStaticInitializer(C var1, StaticInitializerMember var2);
}
