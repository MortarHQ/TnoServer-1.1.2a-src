package org.openzen.zencode.java.module;

import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.Map;
import org.openzen.zenscript.codemodel.generic.TypeParameter;

public class TypeVariableContext {
   private final Map<TypeVariable, TypeParameter> typeVariables = new HashMap<>();

   public void put(TypeVariable variable, TypeParameter parameter) {
      this.typeVariables.put(variable, parameter);
   }

   public TypeParameter get(TypeVariable variable) {
      if (!this.typeVariables.containsKey(variable)) {
         throw new IllegalStateException(
            "Could not find type variable " + variable.getName() + " on declaration: " + variable.getGenericDeclaration().toString()
         );
      } else {
         return this.typeVariables.get(variable);
      }
   }

   public void putAllFrom(TypeVariableContext context) {
      this.typeVariables.putAll(context.typeVariables);
   }
}
