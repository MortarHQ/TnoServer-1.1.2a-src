package org.openzen.zenscript.codemodel;

import java.util.Objects;

public final class AccessScope {
   public final Module module;
   public final HighLevelDefinition definition;

   public AccessScope(Module module, HighLevelDefinition definition) {
      this.module = module;
      this.definition = definition;
   }

   public boolean hasAccessTo(AccessScope other, int access) {
      if (Modifiers.isPublic(access)) {
         return true;
      } else if (this.definition == null) {
         return false;
      } else if (this.definition == other.definition || this.definition.isOuterOf(other.definition) || other.definition.isOuterOf(this.definition)) {
         return true;
      } else if (Modifiers.isPrivate(access)) {
         return false;
      } else if (Modifiers.isInternal(access)) {
         return this.module == other.module;
      } else {
         return Modifiers.isProtected(access) ? this.definition.isSubclassOf(other.definition) : false;
      }
   }

   @Override
   public int hashCode() {
      int hash = 7;
      hash = 79 * hash + Objects.hashCode(this.module);
      return 79 * hash + Objects.hashCode(this.definition);
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj != null && this.getClass() == obj.getClass()) {
         AccessScope other = (AccessScope)obj;
         return this.module == other.module && this.definition == other.definition;
      } else {
         return false;
      }
   }
}
