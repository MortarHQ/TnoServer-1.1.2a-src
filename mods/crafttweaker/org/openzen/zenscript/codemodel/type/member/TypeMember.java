package org.openzen.zenscript.codemodel.type.member;

import org.openzen.zenscript.codemodel.member.ref.DefinitionMemberRef;

public class TypeMember<T extends DefinitionMemberRef> {
   public final TypeMemberPriority priority;
   public final T member;

   public TypeMember(TypeMemberPriority priority, T member) {
      this.priority = priority;
      this.member = member;
   }

   public TypeMember<T> resolve(TypeMember<T> other) {
      if (this.priority == other.priority) {
         return this;
      } else {
         return this.priority.compareTo(other.priority) < 0 ? other : this;
      }
   }
}
