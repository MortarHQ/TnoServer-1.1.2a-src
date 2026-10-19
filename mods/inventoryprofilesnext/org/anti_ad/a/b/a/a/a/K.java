package org.anti_ad.a.b.a.a.a;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import org.anti_ad.a.b.a.a.a.a.X;

public abstract class K {
   public static final int EOF = -1;
   private static final Map tokenTypeMapCache = new WeakHashMap();
   private static final Map ruleIndexMapCache = new WeakHashMap();
   private List _listeners = new L(this);
   protected org.anti_ad.a.b.a.a.a.a.j _interp;
   private int _stateNumber = -1;

   @Deprecated
   public abstract String[] getTokenNames();

   public abstract String[] getRuleNames();

   public S getVocabulary() {
      return T.a(this.getTokenNames());
   }

   public Map getTokenTypeMap() {
      S var1 = this.getVocabulary();
      synchronized (tokenTypeMapCache) {
         Map var3;
         if ((var3 = (Map)tokenTypeMapCache.get(var1)) == null) {
            var3 = new HashMap();

            for (int var4 = 0; var4 <= this.getATN().f; var4++) {
               String var5;
               if ((var5 = var1.a(var4)) != null) {
                  var3.put(var5, var4);
               }

               if ((var5 = var1.b(var4)) != null) {
                  var3.put(var5, var4);
               }
            }

            var3.put("EOF", -1);
            var3 = Collections.unmodifiableMap(var3);
            tokenTypeMapCache.put(var1, var3);
         }

         return var3;
      }
   }

   public Map getRuleIndexMap() {
      String[] var1;
      if ((var1 = this.getRuleNames()) == null) {
         throw new UnsupportedOperationException("The current recognizer does not provide a list of rule names.");
      }

      synchronized (ruleIndexMapCache) {
         Map var3;
         if ((var3 = (Map)ruleIndexMapCache.get(var1)) == null) {
            String[] var7 = var1;
            HashMap var4 = new HashMap();

            for (int var5 = 0; var5 < var7.length; var5++) {
               var4.put(var7[var5], var5);
            }

            var3 = Collections.unmodifiableMap(var4);
            ruleIndexMapCache.put(var1, var3);
         }

         return var3;
      }
   }

   public int getTokenType(String var1) {
      Integer var2;
      return (var2 = (Integer)this.getTokenTypeMap().get(var1)) != null ? var2 : 0;
   }

   public String getSerializedATN() {
      throw new UnsupportedOperationException("there is no serialized ATN");
   }

   public abstract String getGrammarFileName();

   public abstract org.anti_ad.a.b.a.a.a.a.a getATN();

   public org.anti_ad.a.b.a.a.a.a.j getInterpreter() {
      return this._interp;
   }

   public X getParseInfo() {
      return null;
   }

   public void setInterpreter(org.anti_ad.a.b.a.a.a.a.j var1) {
      this._interp = var1;
   }

   public String getErrorHeader(J var1) {
      int var2 = var1.d().c();
      int var3 = var1.d().d();
      return "line " + var2 + ":" + var3;
   }

   @Deprecated
   public String getTokenErrorDisplay(O var1) {
      if (var1 == null) {
         return "<no token>";
      }

      String var2;
      if ((var2 = var1.b()) == null) {
         if (var1.a() == -1) {
            var2 = "<EOF>";
         } else {
            var2 = "<" + var1.a() + ">";
         }
      }

      var2 = var2.replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t");
      return "'" + var2 + "'";
   }

   public void addErrorListener(a var1) {
      if (var1 == null) {
         throw new NullPointerException("listener cannot be null.");
      }

      this._listeners.add(var1);
   }

   public void removeErrorListener(a var1) {
      this._listeners.remove(var1);
   }

   public void removeErrorListeners() {
      this._listeners.clear();
   }

   public List getErrorListeners() {
      return this._listeners;
   }

   public a getErrorListenerDispatch() {
      return new I(this.getErrorListeners());
   }

   public boolean sempred(M var1, int var2, int var3) {
      return true;
   }

   public boolean precpred(M var1, int var2) {
      return true;
   }

   public void action(M var1, int var2, int var3) {
   }

   public final int getState() {
      return this._stateNumber;
   }

   public final void setState(int var1) {
      this._stateNumber = var1;
   }

   public abstract x getInputStream();

   public abstract void setInputStream(x var1);

   public abstract P getTokenFactory();

   public abstract void setTokenFactory(P var1);
}
