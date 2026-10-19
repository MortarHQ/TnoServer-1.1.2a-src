package org.openzen.zenscript.codemodel.member;

public interface MemberVisitor<T> {
   T visitConst(ConstMember var1);

   T visitField(FieldMember var1);

   T visitConstructor(ConstructorMember var1);

   T visitDestructor(DestructorMember var1);

   T visitMethod(MethodMember var1);

   T visitGetter(GetterMember var1);

   T visitSetter(SetterMember var1);

   T visitOperator(OperatorMember var1);

   T visitCaster(CasterMember var1);

   T visitCustomIterator(IteratorMember var1);

   T visitCaller(CallerMember var1);

   T visitImplementation(ImplementationMember var1);

   T visitInnerDefinition(InnerDefinitionMember var1);

   T visitStaticInitializer(StaticInitializerMember var1);
}
