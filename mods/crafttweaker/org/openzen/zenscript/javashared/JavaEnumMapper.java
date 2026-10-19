package org.openzen.zenscript.javashared;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.openzen.zenscript.codemodel.definition.EnumDefinition;
import org.openzen.zenscript.codemodel.member.EnumConstantMember;

public class JavaEnumMapper {
   private final Map<EnumDefinition, Map<EnumConstantMember, String>> definitionMap = new HashMap<>();

   public void merge(JavaEnumMapper mapper) {
      mapper.definitionMap
         .forEach(
            (key, val) -> this.definitionMap
               .merge(
                  key,
                  (Map<EnumConstantMember, String>)val,
                  (a, b) -> Stream.concat(a.entrySet().stream(), b.entrySet().stream()).distinct().collect(Collectors.toMap(Entry::getKey, Entry::getValue))
               )
         );
   }

   public Optional<String> getMapping(EnumConstantMember member) {
      return !(member.definition instanceof EnumDefinition)
         ? Optional.empty()
         : Optional.ofNullable(this.definitionMap.getOrDefault(member.definition, Collections.emptyMap()).get(member));
   }

   public Optional<Map<EnumConstantMember, String>> getMappings(EnumDefinition definition) {
      return Optional.ofNullable(this.definitionMap.get(definition));
   }

   public void registerMapping(EnumDefinition definition, EnumConstantMember member, String name) {
      this.definitionMap.computeIfAbsent(definition, definition1 -> new HashMap<>()).put(member, name);
   }
}
