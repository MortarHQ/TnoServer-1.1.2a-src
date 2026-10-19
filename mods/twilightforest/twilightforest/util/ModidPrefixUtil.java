package twilightforest.util;

import twilightforest.beanification.Component;

@Component
public class ModidPrefixUtil {
   public String stringPrefix(String suffix) {
      return "twilightforest".concat(":").concat(suffix);
   }
}
