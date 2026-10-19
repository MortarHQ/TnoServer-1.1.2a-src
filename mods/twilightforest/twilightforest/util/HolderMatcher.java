package twilightforest.util;

import net.minecraft.core.Holder;
import net.minecraft.core.Holder.Kind;
import twilightforest.beanification.Component;

@Component
public class HolderMatcher {
   public <T> boolean match(Holder<T> a, Holder<T> b) {
      return a.kind() != Kind.DIRECT && b.kind() != Kind.DIRECT
         ? b.unwrapKey().<Boolean>map(a::is).orElseGet(() -> a.unwrapKey().map(b::is).orElse(false))
         : a.value() == b.value();
   }
}
