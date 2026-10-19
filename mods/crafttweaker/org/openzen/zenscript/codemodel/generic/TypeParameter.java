package org.openzen.zenscript.codemodel.generic;

import java.util.ArrayList;
import java.util.List;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.Taggable;
import org.openzen.zenscript.codemodel.type.TypeID;
import org.openzen.zenscript.codemodel.type.member.LocalMemberCache;

public class TypeParameter extends Taggable {
   public static final TypeParameter[] NONE = new TypeParameter[0];
   public final CodePosition position;
   public final String name;
   public final List<TypeParameterBound> bounds = new ArrayList<>();

   public TypeParameter(CodePosition position, String name) {
      this.position = position;
      this.name = name;
   }

   public void addBound(TypeParameterBound bound) {
      this.bounds.add(bound);
   }

   public boolean isObjectType() {
      for (TypeParameterBound bound : this.bounds) {
         if (bound.isObjectType()) {
            return true;
         }
      }

      return false;
   }

   public boolean matches(LocalMemberCache cache, TypeID type) {
      for (TypeParameterBound bound : this.bounds) {
         if (!bound.matches(cache, type)) {
            return false;
         }
      }

      return true;
   }

   public String getCanonical() {
      StringBuilder result = new StringBuilder();
      result.append(this.name);

      for (TypeParameterBound bound : this.bounds) {
         result.append(':');
         result.append(bound.getCanonical());
      }

      return result.toString();
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         TypeParameter that = (TypeParameter)o;
         return !this.name.equals(that.name) ? false : this.bounds.equals(that.bounds);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      int result = this.name.hashCode();
      return 31 * result + this.bounds.hashCode();
   }

   @Override
   public String toString() {
      return this.name + "@" + this.position.toShortString();
   }
}
