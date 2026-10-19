package org.anti_ad.a.b.a.a.a;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.anti_ad.a.b.a.a.a.a.X;
import org.anti_ad.a.b.a.a.a.a.Y;
import org.anti_ad.a.b.a.a.a.a.ak;
import org.anti_ad.a.b.a.a.a.a.ao;

public abstract class D extends K {
   private org.anti_ad.a.b.a.a.a.a.a bypassAltsAtnCache;
   protected b _errHandler = new u();
   protected R _input;
   protected final org.anti_ad.a.b.a.a.a.c.j _precedenceStack = new org.anti_ad.a.b.a.a.a.c.j();
   protected H _ctx;
   protected boolean _buildParseTrees;
   private E _tracer;
   protected List _parseListeners;
   protected int _syntaxErrors;
   protected boolean matchedEOF;

   public D(R var1) {
      this._precedenceStack.a(0);
      this._buildParseTrees = true;
      this.setInputStream(var1);
   }

   public void reset() {
      if (this.getInputStream() != null) {
         this.getInputStream().b(0);
      }

      this._errHandler.a();
      this._ctx = null;
      this._syntaxErrors = 0;
      this.matchedEOF = false;
      this.setTrace(false);
      this._precedenceStack.c();
      this._precedenceStack.a(0);
      org.anti_ad.a.b.a.a.a.a.j var1;
      if ((var1 = this.getInterpreter()) != null) {
         var1.a();
      }
   }

   public O match(int var1) {
      O var2;
      if ((var2 = this.getCurrentToken()).a() == var1) {
         if (var1 == -1) {
            this.matchedEOF = true;
         }

         this._errHandler.c();
         this.consume();
      } else {
         var2 = this._errHandler.a(this);
         if (this._buildParseTrees && var2.h() == -1) {
            this._ctx.addErrorNode(this.createErrorNode(this._ctx, var2));
         }
      }

      return var2;
   }

   public O matchWildcard() {
      O var1;
      if ((var1 = this.getCurrentToken()).a() > 0) {
         this._errHandler.c();
         this.consume();
      } else {
         var1 = this._errHandler.a(this);
         if (this._buildParseTrees && var1.h() == -1) {
            this._ctx.addErrorNode(this.createErrorNode(this._ctx, var1));
         }
      }

      return var1;
   }

   public void setBuildParseTree(boolean var1) {
      this._buildParseTrees = var1;
   }

   public boolean getBuildParseTree() {
      return this._buildParseTrees;
   }

   public void setTrimParseTree(boolean var1) {
      if (var1) {
         if (!this.getTrimParseTree()) {
            this.addParseListener(F.a);
         }
      } else {
         this.removeParseListener(F.a);
      }
   }

   public boolean getTrimParseTree() {
      return this.getParseListeners().contains(F.a);
   }

   public List getParseListeners() {
      List var1 = this._parseListeners;
      return this._parseListeners == null ? Collections.emptyList() : var1;
   }

   public void addParseListener(org.anti_ad.a.b.a.a.a.d.e var1) {
      if (var1 == null) {
         throw new NullPointerException("listener");
      }

      if (this._parseListeners == null) {
         this._parseListeners = new ArrayList();
      }

      this._parseListeners.add(var1);
   }

   public void removeParseListener(org.anti_ad.a.b.a.a.a.d.e var1) {
      if (this._parseListeners != null && this._parseListeners.remove(var1) && this._parseListeners.isEmpty()) {
         this._parseListeners = null;
      }
   }

   public void removeParseListeners() {
      this._parseListeners = null;
   }

   protected void triggerEnterRuleEvent() {
      Iterator var1 = this._parseListeners.iterator();

      while (var1.hasNext()) {
         org.anti_ad.a.b.a.a.a.d.e var2;
         (var2 = (org.anti_ad.a.b.a.a.a.d.e)var1.next()).enterEveryRule(this._ctx);
         this._ctx.enterRule(var2);
      }
   }

   protected void triggerExitRuleEvent() {
      for (int var1 = this._parseListeners.size() - 1; var1 >= 0; var1--) {
         org.anti_ad.a.b.a.a.a.d.e var2 = (org.anti_ad.a.b.a.a.a.d.e)this._parseListeners.get(var1);
         this._ctx.exitRule(var2);
         var2.exitEveryRule(this._ctx);
      }
   }

   public int getNumberOfSyntaxErrors() {
      return this._syntaxErrors;
   }

   @Override
   public P getTokenFactory() {
      return this._input.e().getTokenFactory();
   }

   @Override
   public void setTokenFactory(P var1) {
      this._input.e().setTokenFactory(var1);
   }

   public org.anti_ad.a.b.a.a.a.a.a getATNWithBypassAlts() {
      String var1;
      if ((var1 = this.getSerializedATN()) == null) {
         throw new UnsupportedOperationException("The current parser does not support an ATN with bypass alternatives.");
      }

      synchronized (this) {
         if (this.bypassAltsAtnCache != null) {
            return this.bypassAltsAtnCache;
         }

         org.anti_ad.a.b.a.a.a.a.g var3;
         (var3 = new org.anti_ad.a.b.a.a.a.a.g()).d();
         this.bypassAltsAtnCache = new org.anti_ad.a.b.a.a.a.a.h(var3).a(var1.toCharArray());
         return this.bypassAltsAtnCache;
      }
   }

   public org.anti_ad.a.b.a.a.a.d.a.b compileParseTreePattern(String var1, int var2) {
      Q var3;
      if (this.getTokenStream() != null && (var3 = this.getTokenStream().e()) instanceof z) {
         var3 = var3;
         return this.compileParseTreePattern(var1, var2, var3);
      } else {
         throw new UnsupportedOperationException("Parser can't discover a lexer to use");
      }
   }

   public org.anti_ad.a.b.a.a.a.d.a.b compileParseTreePattern(String var1, int var2, z var3) {
      return new org.anti_ad.a.b.a.a.a.d.a.c(var3, this).a(var1, var2);
   }

   public b getErrorHandler() {
      return this._errHandler;
   }

   public void setErrorHandler(b var1) {
      this._errHandler = var1;
   }

   public R getInputStream() {
      return this.getTokenStream();
   }

   @Override
   public final void setInputStream(x var1) {
      this.setTokenStream((R)var1);
   }

   public R getTokenStream() {
      return this._input;
   }

   public void setTokenStream(R var1) {
      this._input = null;
      this.reset();
      this._input = var1;
   }

   public O getCurrentToken() {
      return this._input.f(1);
   }

   public final void notifyErrorListeners(String var1) {
      this.notifyErrorListeners(this.getCurrentToken(), var1, null);
   }

   public void notifyErrorListeners(O var1, String var2, J var3) {
      this._syntaxErrors++;
      int var4 = var1.c();
      int var5 = var1.d();
      this.getErrorListenerDispatch().syntaxError(this, var1, var4, var5, var2, var3);
   }

   public O consume() {
      O var1;
      if ((var1 = this.getCurrentToken()).a() != -1) {
         this.getInputStream().a();
      }

      boolean var2 = this._parseListeners != null && !this._parseListeners.isEmpty();
      if (this._buildParseTrees || var2) {
         if (this._errHandler.b()) {
            org.anti_ad.a.b.a.a.a.d.b var4 = this._ctx.addErrorNode(this.createErrorNode(this._ctx, var1));
            if (this._parseListeners != null) {
               Iterator var3 = this._parseListeners.iterator();

               while (var3.hasNext()) {
                  ((org.anti_ad.a.b.a.a.a.d.e)var3.next()).visitErrorNode(var4);
               }
            }
         } else {
            org.anti_ad.a.b.a.a.a.d.i var5 = this._ctx.addChild(this.createTerminalNode(this._ctx, var1));
            if (this._parseListeners != null) {
               Iterator var6 = this._parseListeners.iterator();

               while (var6.hasNext()) {
                  ((org.anti_ad.a.b.a.a.a.d.e)var6.next()).visitTerminal(var5);
               }
            }
         }
      }

      return var1;
   }

   public org.anti_ad.a.b.a.a.a.d.i createTerminalNode(H var1, O var2) {
      return new org.anti_ad.a.b.a.a.a.d.j(var2);
   }

   public org.anti_ad.a.b.a.a.a.d.b createErrorNode(H var1, O var2) {
      return new org.anti_ad.a.b.a.a.a.d.c(var2);
   }

   protected void addContextToParseTree() {
      H var1;
      if ((var1 = (H)this._ctx.parent) != null) {
         var1.addChild(this._ctx);
      }
   }

   public void enterRule(H var1, int var2, int var3) {
      this.setState(var2);
      this._ctx = var1;
      this._ctx.start = this._input.f(1);
      if (this._buildParseTrees) {
         this.addContextToParseTree();
      }

      if (this._parseListeners != null) {
         this.triggerEnterRuleEvent();
      }
   }

   public void exitRule() {
      if (this.matchedEOF) {
         this._ctx.stop = this._input.f(1);
      } else {
         this._ctx.stop = this._input.f(-1);
      }

      if (this._parseListeners != null) {
         this.triggerExitRuleEvent();
      }

      this.setState(this._ctx.invokingState);
      this._ctx = (H)this._ctx.parent;
   }

   public void enterOuterAlt(H var1, int var2) {
      var1.setAltNumber(var2);
      H var3;
      if (this._buildParseTrees && this._ctx != var1 && (var3 = (H)this._ctx.parent) != null) {
         var3.removeLastChild();
         var3.addChild(var1);
      }

      this._ctx = var1;
   }

   public final int getPrecedence() {
      return this._precedenceStack.a() ? -1 : this._precedenceStack.e();
   }

   @Deprecated
   public void enterRecursionRule(H var1, int var2) {
      this.enterRecursionRule(var1, this.getATN().c[var2].b, var2, 0);
   }

   public void enterRecursionRule(H var1, int var2, int var3, int var4) {
      this.setState(var2);
      this._precedenceStack.a(var4);
      this._ctx = var1;
      this._ctx.start = this._input.f(1);
      if (this._parseListeners != null) {
         this.triggerEnterRuleEvent();
      }
   }

   public void pushNewRecursionContext(H var1, int var2, int var3) {
      H var4 = this._ctx;
      this._ctx.parent = var1;
      var4.invokingState = var2;
      var4.stop = this._input.f(-1);
      this._ctx = var1;
      this._ctx.start = var4.start;
      if (this._buildParseTrees) {
         this._ctx.addChild(var4);
      }

      if (this._parseListeners != null) {
         this.triggerEnterRuleEvent();
      }
   }

   public void unrollRecursionContexts(H var1) {
      this._precedenceStack.d();
      this._ctx.stop = this._input.f(-1);
      H var2 = this._ctx;
      if (this._parseListeners != null) {
         while (this._ctx != var1) {
            this.triggerExitRuleEvent();
            this._ctx = (H)this._ctx.parent;
         }
      } else {
         this._ctx = var1;
      }

      var2.parent = var1;
      if (this._buildParseTrees && var1 != null) {
         var1.addChild(var2);
      }
   }

   public H getInvokingContext(int var1) {
      for (H var2 = this._ctx; var2 != null; var2 = (H)var2.parent) {
         if (var2.getRuleIndex() == var1) {
            return var2;
         }
      }

      return null;
   }

   public H getContext() {
      return this._ctx;
   }

   public void setContext(H var1) {
      this._ctx = var1;
   }

   @Override
   public boolean precpred(M var1, int var2) {
      return var2 >= this._precedenceStack.e();
   }

   public boolean inContext(String var1) {
      return false;
   }

   public boolean isExpectedToken(int var1) {
      org.anti_ad.a.b.a.a.a.a.a var2 = ((Y)this.getInterpreter()).b;
      H var3 = this._ctx;
      org.anti_ad.a.b.a.a.a.a.k var4 = (org.anti_ad.a.b.a.a.a.a.k)var2.a.get(this.getState());
      org.anti_ad.a.b.a.a.a.c.l var5;
      if ((var5 = var2.a(var4)).c(var1)) {
         return true;
      }

      if (!var5.c(-2)) {
         return false;
      }

      while (var3 != null && var3.invokingState >= 0 && var5.c(-2)) {
         ao var6 = (ao)((org.anti_ad.a.b.a.a.a.a.k)var2.a.get(var3.invokingState)).a(0);
         if ((var5 = var2.a(var6.b)).c(var1)) {
            return true;
         }

         var3 = (H)var3.parent;
      }

      return var5.c(-2) && var1 == -1;
   }

   public boolean isMatchedEOF() {
      return this.matchedEOF;
   }

   public org.anti_ad.a.b.a.a.a.c.l getExpectedTokens() {
      return this.getATN().a(this.getState(), this.getContext());
   }

   public org.anti_ad.a.b.a.a.a.c.l getExpectedTokensWithinCurrentRule() {
      org.anti_ad.a.b.a.a.a.a.a var1;
      org.anti_ad.a.b.a.a.a.a.k var2 = (org.anti_ad.a.b.a.a.a.a.k)(var1 = ((Y)this.getInterpreter()).b).a.get(this.getState());
      return var1.a(var2);
   }

   public int getRuleIndex(String var1) {
      Integer var2;
      return (var2 = (Integer)this.getRuleIndexMap().get(var1)) != null ? var2 : -1;
   }

   public H getRuleContext() {
      return this._ctx;
   }

   public List getRuleInvocationStack() {
      return this.getRuleInvocationStack(this._ctx);
   }

   public List getRuleInvocationStack(M var1) {
      String[] var2 = this.getRuleNames();
      ArrayList var3 = new ArrayList();

      while (var1 != null) {
         int var4;
         if ((var4 = var1.getRuleIndex()) < 0) {
            var3.add("n/a");
         } else {
            var3.add(var2[var4]);
         }

         var1 = var1.parent;
      }

      return var3;
   }

   public List getDFAStrings() {
      synchronized (((Y)super._interp).e) {
         ArrayList var2 = new ArrayList();

         for (int var3 = 0; var3 < ((Y)super._interp).e.length; var3++) {
            org.anti_ad.a.b.a.a.a.b.a var4 = ((Y)super._interp).e[var3];
            var2.add(var4.a(this.getVocabulary()));
         }

         return var2;
      }
   }

   public void dumpDFA() {
      this.dumpDFA(System.out);
   }

   public void dumpDFA(PrintStream var1) {
      synchronized (((Y)super._interp).e) {
         boolean var3 = false;

         for (int var4 = 0; var4 < ((Y)super._interp).e.length; var4++) {
            org.anti_ad.a.b.a.a.a.b.a var5;
            if (!(var5 = ((Y)super._interp).e[var4]).a.isEmpty()) {
               if (var3) {
                  var1.println();
               }

               var1.println("Decision " + var5.c + ":");
               var1.print(var5.a(this.getVocabulary()));
               var3 = true;
            }
         }
      }
   }

   public String getSourceName() {
      return this._input.d();
   }

   @Override
   public X getParseInfo() {
      Y var1;
      return (var1 = (Y)this.getInterpreter()) instanceof ak ? new X((ak)var1) : null;
   }

   public void setProfile(boolean var1) {
      Y var2;
      int var3 = (var2 = (Y)this.getInterpreter()).c();
      if (var1) {
         if (!(var2 instanceof ak)) {
            this.setInterpreter(new ak(this));
         }
      } else if (var2 instanceof ak) {
         Y var4 = new Y(this, this.getATN(), var2.e, var2.b());
         this.setInterpreter(var4);
      }

      ((Y)this.getInterpreter()).a(var3);
   }

   public void setTrace(boolean var1) {
      if (!var1) {
         this.removeParseListener(this._tracer);
         this._tracer = null;
      } else {
         if (this._tracer != null) {
            this.removeParseListener(this._tracer);
         } else {
            this._tracer = new E(this);
         }

         this.addParseListener(this._tracer);
      }
   }

   public boolean isTrace() {
      return this._tracer != null;
   }
}
