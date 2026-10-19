package org.openzen.zenscript.codemodel.generic;

public interface GenericParameterBoundVisitorWithContext<C, R> {
   R visitSuper(C var1, ParameterSuperBound var2);

   R visitType(C var1, ParameterTypeBound var2);
}
