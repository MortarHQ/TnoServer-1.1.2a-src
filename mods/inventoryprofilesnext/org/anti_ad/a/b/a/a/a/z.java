package org.anti_ad.a.b.a.a.a;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

public abstract class z extends K implements Q {
   public static final int DEFAULT_MODE = 0;
   public static final int MORE = -2;
   public static final int SKIP = -3;
   public static final int DEFAULT_TOKEN_CHANNEL = 0;
   public static final int HIDDEN = 1;
   public static final int MIN_CHAR_VALUE = 0;
   public static final int MAX_CHAR_VALUE = 1114111;
   public g _input;
   protected org.anti_ad.a.b.a.a.a.c.o _tokenFactorySourcePair;
   protected P _factory = r.a;
   public O _token;
   public int _tokenStartCharIndex = -1;
   public int _tokenStartLine;
   public int _tokenStartCharPositionInLine;
   public boolean _hitEOF;
   public int _channel;
   public int _type;
   public final org.anti_ad.a.b.a.a.a.c.j _modeStack = new org.anti_ad.a.b.a.a.a.c.j();
   public int _mode = 0;
   public String _text;

   public z() {
   }

   public z(g var1) {
      this._input = var1;
      this._tokenFactorySourcePair = new org.anti_ad.a.b.a.a.a.c.o(this, var1);
   }

   public void reset() {
      if (this._input != null) {
         this._input.b(0);
      }

      this._token = null;
      this._type = 0;
      this._channel = 0;
      this._tokenStartCharIndex = -1;
      this._tokenStartCharPositionInLine = -1;
      this._tokenStartLine = -1;
      this._text = null;
      this._hitEOF = false;
      this._mode = 0;
      this._modeStack.c();
      ((org.anti_ad.a.b.a.a.a.a.E)this.getInterpreter()).a();
   }

   @Override
   public O nextToken() {
      if (this._input == null) {
         throw new IllegalStateException("nextToken requires a non-null input stream.");
      }

      try {
         label87:
         while (!this._hitEOF) {
            this._token = null;
            this._channel = 0;
            this._tokenStartCharIndex = this._input.b();
            this._tokenStartCharPositionInLine = ((org.anti_ad.a.b.a.a.a.a.E)this.getInterpreter()).d();
            this._tokenStartLine = ((org.anti_ad.a.b.a.a.a.a.E)this.getInterpreter()).c();
            this._text = null;

            do {
               this._type = 0;

               int var1;
               try {
                  var1 = ((org.anti_ad.a.b.a.a.a.a.E)this.getInterpreter()).a(this._input, this._mode);
               } catch (A var4) {
                  this.notifyListeners(var4);
                  this.recover(var4);
                  var1 = -3;
               }

               if (this._input.a(1) == -1) {
                  this._hitEOF = true;
               }

               if (this._type == 0) {
                  this._type = var1;
               }

               if (this._type == -3) {
                  continue label87;
               }
            } while (this._type == -2);

            if (this._token == null) {
               this.emit();
            }

            return this._token;
         }

         this.emitEOF();
         return this._token;
      } finally {
         ;
      }
   }

   public void skip() {
      this._type = -3;
   }

   public void more() {
      this._type = -2;
   }

   public void mode(int var1) {
      this._mode = var1;
   }

   public void pushMode(int var1) {
      this._modeStack.a(this._mode);
      this.mode(var1);
   }

   public int popMode() {
      if (this._modeStack.a()) {
         throw new EmptyStackException();
      }

      this.mode(this._modeStack.d());
      return this._mode;
   }

   @Override
   public void setTokenFactory(P var1) {
      this._factory = var1;
   }

   @Override
   public P getTokenFactory() {
      return this._factory;
   }

   @Override
   public void setInputStream(x var1) {
      this._input = null;
      this._tokenFactorySourcePair = new org.anti_ad.a.b.a.a.a.c.o(this, this._input);
      this.reset();
      this._input = (g)var1;
      this._tokenFactorySourcePair = new org.anti_ad.a.b.a.a.a.c.o(this, this._input);
   }

   @Override
   public String getSourceName() {
      return this._input.d();
   }

   @Override
   public g getInputStream() {
      return this._input;
   }

   public void emit(O var1) {
      this._token = var1;
   }

   public O emit() {
      q var1 = this._factory
         .a(
            this._tokenFactorySourcePair,
            this._type,
            this._text,
            this._channel,
            this._tokenStartCharIndex,
            this.getCharIndex() - 1,
            this._tokenStartLine,
            this._tokenStartCharPositionInLine
         );
      this.emit(var1);
      return var1;
   }

   public O emitEOF() {
      int var1 = this.getCharPositionInLine();
      int var2 = this.getLine();
      q var3 = this._factory.a(this._tokenFactorySourcePair, -1, null, 0, this._input.b(), this._input.b() - 1, var2, var1);
      this.emit(var3);
      return var3;
   }

   @Override
   public int getLine() {
      return ((org.anti_ad.a.b.a.a.a.a.E)this.getInterpreter()).c();
   }

   @Override
   public int getCharPositionInLine() {
      return ((org.anti_ad.a.b.a.a.a.a.E)this.getInterpreter()).d();
   }

   public void setLine(int var1) {
      ((org.anti_ad.a.b.a.a.a.a.E)this.getInterpreter()).a(var1);
   }

   public void setCharPositionInLine(int var1) {
      ((org.anti_ad.a.b.a.a.a.a.E)this.getInterpreter()).b(var1);
   }

   public int getCharIndex() {
      return this._input.b();
   }

   public String getText() {
      return this._text != null ? this._text : ((org.anti_ad.a.b.a.a.a.a.E)this.getInterpreter()).a(this._input);
   }

   public void setText(String var1) {
      this._text = var1;
   }

   public O getToken() {
      return this._token;
   }

   public void setToken(O var1) {
      this._token = var1;
   }

   public void setType(int var1) {
      this._type = var1;
   }

   public int getType() {
      return this._type;
   }

   public void setChannel(int var1) {
      this._channel = var1;
   }

   public int getChannel() {
      return this._channel;
   }

   public String[] getChannelNames() {
      return null;
   }

   public String[] getModeNames() {
      return null;
   }

   @Deprecated
   @Override
   public String[] getTokenNames() {
      return null;
   }

   public List getAllTokens() {
      ArrayList var1 = new ArrayList();

      for (O var2 = this.nextToken(); var2.a() != -1; var2 = this.nextToken()) {
         var1.add(var2);
      }

      return var1;
   }

   public void recover(A var1) {
      if (this._input.a(1) != -1) {
         ((org.anti_ad.a.b.a.a.a.a.E)this.getInterpreter()).b(this._input);
      }
   }

   public void notifyListeners(A var1) {
      String var2 = this._input.a(org.anti_ad.a.b.a.a.a.c.k.a(this._tokenStartCharIndex, this._input.b()));
      var2 = "token recognition error at: '" + this.getErrorDisplay(var2) + "'";
      this.getErrorListenerDispatch().syntaxError(this, null, this._tokenStartLine, this._tokenStartCharPositionInLine, var2, var1);
   }

   public String getErrorDisplay(String var1) {
      StringBuilder var2 = new StringBuilder();
      char[] var6;
      int var3 = (var6 = var1.toCharArray()).length;

      for (int var4 = 0; var4 < var3; var4++) {
         char var5 = var6[var4];
         var2.append(this.getErrorDisplay(var5));
      }

      return var2.toString();
   }

   public String getErrorDisplay(int var1) {
      String var2 = String.valueOf((char)var1);
      switch (var1) {
         case -1:
            var2 = "<EOF>";
            break;
         case 9:
            var2 = "\\t";
            break;
         case 10:
            var2 = "\\n";
            break;
         case 13:
            var2 = "\\r";
      }

      return var2;
   }

   public String getCharErrorDisplay(int var1) {
      String var2 = this.getErrorDisplay(var1);
      return "'" + var2 + "'";
   }

   public void recover(J var1) {
      this._input.a();
   }
}
