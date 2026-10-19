package org.embeddedt.modernfix.registry;

import it.unimi.dsi.fastutil.objects.Reference2ReferenceOpenHashMap;
import net.minecraft.core.RegistrationInfo;
import net.minecraft.resources.ResourceKey;

public class LifecycleMap<T> extends Reference2ReferenceOpenHashMap<ResourceKey<T>, RegistrationInfo> {
   public LifecycleMap() {
      this.defaultReturnValue(RegistrationInfo.BUILT_IN);
   }

   public RegistrationInfo put(ResourceKey<T> t, RegistrationInfo lifecycle) {
      if (lifecycle != this.defRetValue) {
         return (RegistrationInfo)super.put(t, lifecycle);
      } else {
         return super.containsKey(t) ? (RegistrationInfo)super.get(t) : null;
      }
   }
}
