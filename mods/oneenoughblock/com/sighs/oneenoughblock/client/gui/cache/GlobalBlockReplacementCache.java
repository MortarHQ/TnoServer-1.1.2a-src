package com.sighs.oneenoughblock.client.gui.cache;

import com.mafuyu404.oneenoughitem.client.gui.cache.AbstractGlobalReplacementCache;

public class GlobalBlockReplacementCache extends AbstractGlobalReplacementCache {
   private static final GlobalBlockReplacementCache INSTANCE = new GlobalBlockReplacementCache();

   private GlobalBlockReplacementCache() {
      super("oeb", "oeb");
   }

   protected String domainId() {
      return "oeb";
   }

   public static AbstractGlobalReplacementCache get() {
      return INSTANCE;
   }
}
