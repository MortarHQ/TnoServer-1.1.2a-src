package com.blamejared.crafttweaker.impl.script.recipefs;

import com.blamejared.crafttweaker.impl.script.ScriptRecipe;
import java.io.IOException;

@FunctionalInterface
interface RecipeFsResolver {
   ScriptRecipe resolve(RecipePath var1) throws IOException;

   default String resolveContents(RecipePath path) throws IOException {
      return this.resolve(path).getContent();
   }

   default RecipeFsResolver.Bound bind(RecipePath path) {
      return () -> this.resolve(path);
   }

   @FunctionalInterface
   interface Bound {
      ScriptRecipe resolve() throws IOException;

      default String resolveContents() throws IOException {
         return this.resolve().getContent();
      }
   }
}
