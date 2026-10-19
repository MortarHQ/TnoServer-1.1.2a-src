package org.openzen.zenscript.lexer;

public class CharStream {
   private final char[] data;
   private int index;

   public CharStream(String data) {
      this.data = data.toCharArray();
      this.index = 0;
   }

   public char peek() {
      return this.index == this.data.length ? '\u0000' : this.data[this.index];
   }

   public boolean peek(char ch) {
      return this.index == this.data.length ? false : this.data[this.index] == ch;
   }

   public boolean optional(char ch) {
      if (this.index == this.data.length) {
         return false;
      } else if (this.data[this.index] == ch) {
         this.index++;
         return true;
      } else {
         return false;
      }
   }

   public char optional(char from, char to) {
      if (this.index == this.data.length) {
         return '\u0000';
      } else {
         return this.data[this.index] >= from && this.data[this.index] <= to ? this.data[this.index++] : '\u0000';
      }
   }

   public void required(char ch) {
      if (this.index >= this.data.length) {
         throw new IllegalArgumentException("Expected " + ch + " but got EOF");
      }

      if (this.data[this.index] != ch) {
         throw new IllegalArgumentException("Expected " + ch + " but got " + this.data[this.index]);
      }

      this.index++;
   }

   public char required(char from, char to) {
      if (this.data[this.index] >= from && this.data[this.index] <= to) {
         return this.data[this.index++];
      } else {
         throw new IllegalArgumentException("Unexpected character: " + this.data[this.index]);
      }
   }

   public char next() {
      return this.index >= this.data.length ? '\u0000' : this.data[this.index++];
   }

   public boolean hasMore() {
      return this.index < this.data.length;
   }
}
