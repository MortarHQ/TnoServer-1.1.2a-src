package org.openzen.zenscript.codemodel.generic;

public interface GenericParameterBoundVisitor<T> {
   T visitSuper(ParameterSuperBound var1);

   T visitType(ParameterTypeBound var1);
}
