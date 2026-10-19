package com.blamejared.crafttweaker.impl.script.recipefs;

import com.blamejared.crafttweaker.impl.script.ScriptRecipe;
import java.io.IOException;
import java.nio.file.ClosedFileSystemException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.nio.file.DirectoryStream.Filter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

final class RecipeDirectoryStream implements DirectoryStream<Path> {
   private RecipeDirectoryStream.RecipeDirectoryStreamIterator iterator;
   private boolean gaveIterator;
   private boolean closed;

   private RecipeDirectoryStream(RecipeDirectoryStream.RecipeDirectoryStreamIterator iterator) {
      this.iterator = iterator;
      this.gaveIterator = false;
      this.closed = false;
   }

   static DirectoryStream<Path> of(RecipeFileSystem fs, RecipePath dir, Collection<ScriptRecipe> recipes, Filter<? super Path> filter) throws IOException {
      Objects.requireNonNull(fs);
      Objects.requireNonNull(dir);
      Objects.requireNonNull(recipes);
      Objects.requireNonNull(filter);
      String dirName = dir.toAbsolutePath().normalize().toString();
      Collection<ScriptRecipe> satisfactory = new ArrayList<>();

      for (ScriptRecipe recipe : recipes) {
         String name;
         if (dirName.equals(name = "/" + recipe.getFileName())) {
            throw new NotDirectoryException(dirName);
         }

         if (name.startsWith(dirName)) {
            satisfactory.add(recipe);
         }
      }

      if (satisfactory.isEmpty()) {
         throw new NotDirectoryException(dirName);
      }

      RecipeDirectoryStream.RecipeDirectoryStreamIterator iterator = new RecipeDirectoryStream.RecipeDirectoryStreamIterator(
         fs, satisfactory.iterator(), filter
      );
      return new RecipeDirectoryStream(iterator);
   }

   @Override
   public Iterator<Path> iterator() {
      if (this.closed) {
         throw new ClosedFileSystemException();
      }

      if (this.gaveIterator) {
         throw new IllegalStateException();
      }

      this.gaveIterator = true;
      return this.iterator;
   }

   @Override
   public void close() {
      if (!this.closed) {
         this.iterator.close();
         this.iterator = null;
         this.closed = true;
      }
   }

   private static final class RecipeDirectoryStreamIterator implements Iterator<Path> {
      private RecipeFileSystem fs;
      private Iterator<ScriptRecipe> children;
      private Filter<? super Path> filter;
      private RecipePath nextEntry;
      private boolean closed;

      RecipeDirectoryStreamIterator(RecipeFileSystem fs, Iterator<ScriptRecipe> children, Filter<? super Path> filter) {
         this.fs = fs;
         this.children = children;
         this.filter = filter;
         this.nextEntry = null;
         this.closed = false;
      }

      @Override
      public boolean hasNext() {
         if (this.nextEntry != null) {
            return true;
         }

         try {
            this.computeNext();
         } catch (IOException e) {
            throw new DirectoryIteratorException(e);
         }

         return this.nextEntry != null;
      }

      public Path next() {
         if (!this.hasNext()) {
            throw new NoSuchElementException();
         }

         Path next = this.nextEntry;
         this.nextEntry = null;
         return next;
      }

      void close() {
         this.fs = null;
         this.children = null;
         this.filter = null;
         this.nextEntry = null;
         this.closed = true;
      }

      private void computeNext() throws IOException {
         this.nextEntry = null;
         if (!this.closed) {
            while (this.children.hasNext()) {
               ScriptRecipe next = this.children.next();
               RecipePath path = (RecipePath)this.fs.getPath(next.getFileName()).toAbsolutePath().normalize();
               if (this.filter.accept(path)) {
                  this.nextEntry = path;
                  break;
               }
            }
         }
      }
   }
}
