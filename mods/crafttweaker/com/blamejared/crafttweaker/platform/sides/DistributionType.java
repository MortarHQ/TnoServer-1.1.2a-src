package com.blamejared.crafttweaker.platform.sides;

import java.util.Locale;
import java.util.Set;

public enum DistributionType {
   CLIENT(Set.of("client")),
   SERVER(Set.of("server", "dedicated_server"));

   private static final DistributionType[] VALUES = values();
   private static final DistributionType[] CACHE = new DistributionType[VALUES.length];
   private final Set<String> names;

   DistributionType(Set<String> names) {
      this.names = names;
   }

   public static DistributionType from(Enum<?> other) {
      int ordinal = other.ordinal();
      DistributionType cached = CACHE[ordinal];
      if (cached != null) {
         return cached;
      }

      String name = other.name();
      DistributionType target = null;

      for (DistributionType type : VALUES) {
         if (type.matches(name)) {
            target = type;
            CACHE[ordinal] = type;
            break;
         }
      }

      if (target == null) {
         throw new IllegalArgumentException("Cannot create DistributionType from provided enum " + other + " of class " + other.getClass());
      } else {
         return target;
      }
   }

   public boolean isServer() {
      return this == SERVER;
   }

   public boolean isClient() {
      return this == CLIENT;
   }

   public Set<String> getNames() {
      return this.names;
   }

   public boolean matches(String name) {
      return this.getNames().contains(name.toLowerCase(Locale.ENGLISH));
   }

   @Override
   public String toString() {
      return super.toString().toLowerCase(Locale.ENGLISH);
   }
}
