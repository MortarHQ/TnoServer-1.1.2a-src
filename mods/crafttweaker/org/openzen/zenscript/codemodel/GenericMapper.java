package org.openzen.zenscript.codemodel;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.codemodel.generic.TypeParameter;
import org.openzen.zenscript.codemodel.type.GenericTypeID;
import org.openzen.zenscript.codemodel.type.GlobalTypeRegistry;
import org.openzen.zenscript.codemodel.type.TypeID;

public class GenericMapper {
   public static final GenericMapper EMPTY = new GenericMapper(CodePosition.BUILTIN, null, Collections.emptyMap());
   public final CodePosition position;
   public final GlobalTypeRegistry registry;
   private final Map<TypeParameter, TypeID> mapping;

   public GenericMapper(CodePosition position, GlobalTypeRegistry registry, Map<TypeParameter, TypeID> mapping) {
      if (mapping == null) {
         throw new IllegalArgumentException();
      }

      this.position = position;
      this.registry = registry;
      this.mapping = mapping;
   }

   public Map<TypeParameter, TypeID> getMapping() {
      return this.mapping;
   }

   public TypeID map(TypeID original) {
      return this.mapping.isEmpty() ? original : original.instance(this);
   }

   public TypeID[] map(TypeID[] original) {
      if (!this.mapping.isEmpty() && original.length != 0) {
         TypeID[] mapped = new TypeID[original.length];

         for (int i = 0; i < original.length; i++) {
            mapped[i] = original[i].instance(this);
         }

         return mapped;
      } else {
         return original;
      }
   }

   public TypeID mapGeneric(GenericTypeID type) {
      return this.mapping.getOrDefault(type.parameter, type);
   }

   public FunctionHeader map(FunctionHeader original) {
      return this.mapping.isEmpty() ? original : original.withGenericArguments(this);
   }

   public GenericMapper getInner(CodePosition position, GlobalTypeRegistry registry, Map<TypeParameter, TypeID> mapping) {
      Map<TypeParameter, TypeID> resultMap = new HashMap<>(this.mapping);
      mapping.forEach((typeParameter, storedType) -> {
         if (!resultMap.containsKey(typeParameter) || !(storedType instanceof GenericTypeID) || ((GenericTypeID)storedType).parameter != typeParameter) {
            resultMap.put(typeParameter, storedType);
         }
      });
      return new GenericMapper(position, registry, resultMap);
   }

   public GenericMapper getInner(CodePosition position, GlobalTypeRegistry registry, TypeParameter[] parameters) {
      Map<TypeParameter, TypeID> resultMap = new HashMap<>(this.mapping);

      for (TypeParameter parameter : parameters) {
         resultMap.put(parameter, registry.getGeneric(parameter));
      }

      return new GenericMapper(position, registry, resultMap);
   }

   @Override
   public String toString() {
      if (this.mapping.isEmpty()) {
         return "{}";
      }

      StringBuilder result = new StringBuilder();
      result.append('{');
      boolean first = true;

      for (Entry<TypeParameter, TypeID> entry : this.mapping.entrySet()) {
         if (first) {
            first = false;
         } else {
            result.append(", ");
         }

         result.append(entry.getKey().toString()).append(": ").append(entry.getValue());
      }

      result.append('}');
      return result.toString();
   }
}
