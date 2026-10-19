package net.darkhax.bookshelf.common.impl;

import net.darkhax.bookshelf.common.api.service.Services;

public class BookshelfMod {
   private static BookshelfMod instance;
   private boolean hasInitialized = false;

   public void init() {
      if (this.hasInitialized) {
         throw new IllegalStateException("The Bookshelf has already been initialized.");
      }

      this.runStartupChecks();
      this.hasInitialized = true;
   }

   private void runStartupChecks() {
      if (Services.PLATFORM == null) {
         throw new IllegalStateException("Bookshelf services are not available.");
      }
   }

   public static BookshelfMod getInstance() {
      if (instance == null) {
         instance = new BookshelfMod();
      }

      return instance;
   }
}
