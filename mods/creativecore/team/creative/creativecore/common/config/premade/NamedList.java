package team.creative.creativecore.common.config.premade;

import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Map.Entry;

public class NamedList<T> extends LinkedHashMap<String, T> {
   @Override
   public Set<Entry<String, T>> entrySet() {
      return super.entrySet();
   }

   @Override
   public Set<String> keySet() {
      return super.keySet();
   }
}
