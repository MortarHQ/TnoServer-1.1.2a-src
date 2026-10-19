package org.openzen.zenscript.codemodel.type.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openzen.zenscript.codemodel.definition.ExpansionDefinition;
import org.openzen.zenscript.codemodel.type.GlobalTypeRegistry;
import org.openzen.zenscript.codemodel.type.TypeID;

public class LocalMemberCache {
   private final GlobalTypeRegistry registry;
   private final Map<TypeID, TypeMembers> types = new HashMap<>();
   private final List<ExpansionDefinition> expansions = new ArrayList<>();

   public LocalMemberCache(GlobalTypeRegistry registry, List<ExpansionDefinition> expansions) {
      this.registry = registry;
      this.expansions.addAll(expansions);
   }

   public GlobalTypeRegistry getRegistry() {
      return this.registry;
   }

   public TypeMembers get(TypeID type) {
      type = type.getNormalized();
      if (this.types.containsKey(type)) {
         return this.types.get(type);
      }

      TypeMembers members = new TypeMembers(this, type);
      this.types.put(type, members);
      this.registerMembers(members);
      return members;
   }

   private void registerMembers(TypeMembers members) {
      members.type.accept(null, new TypeMemberBuilder(this.registry, members, this));
   }

   public List<ExpansionDefinition> getExpansions() {
      return this.expansions;
   }
}
