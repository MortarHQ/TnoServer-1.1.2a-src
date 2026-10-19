package org.anti_ad.mc.common.gen;

import org.anti_ad.a.b.a.a.a.C;
import org.anti_ad.a.b.a.a.a.D;
import org.anti_ad.a.b.a.a.a.J;
import org.anti_ad.a.b.a.a.a.N;
import org.anti_ad.a.b.a.a.a.R;
import org.anti_ad.a.b.a.a.a.S;
import org.anti_ad.a.b.a.a.a.T;
import org.anti_ad.a.b.a.a.a.a.Y;
import org.anti_ad.a.b.a.a.a.a.ag;
import org.anti_ad.a.b.a.a.a.a.h;
import org.anti_ad.a.b.a.a.a.b.a;

public class RulesParser extends D {
   protected static final a[] _decisionToDFA;
   protected static final ag _sharedContextCache = new ag();
   public static final int WS = 1;
   public static final int AT = 2;
   public static final int ERR = 3;
   public static final int REVERSE = 4;
   public static final int DOUBLE_COLON = 5;
   public static final int HASHTAG = 6;
   public static final int NamespacedId = 7;
   public static final int OPEN = 8;
   public static final int NBT = 9;
   public static final int WS_mSubRule = 10;
   public static final int RuleName = 11;
   public static final int Parameter = 12;
   public static final int EQUAL = 13;
   public static final int CLOSE = 14;
   public static final int WS_mArgs = 15;
   public static final int COMMA = 16;
   public static final int WS_mArg = 17;
   public static final int Argument = 18;
   public static final int RULE_customRuleEOF = 0;
   public static final int RULE_subRuleEOF = 1;
   public static final int RULE_head = 2;
   public static final int RULE_subRule = 3;
   public static final int RULE_subRuleIdentifier = 4;
   public static final int RULE_arguments = 5;
   public static final int RULE_pair = 6;
   public static final String[] ruleNames = makeRuleNames();
   private static final String[] _LITERAL_NAMES = makeLiteralNames();
   private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
   public static final S VOCABULARY = new T(_LITERAL_NAMES, _SYMBOLIC_NAMES);
   @Deprecated
   public static final String[] tokenNames = new String[_SYMBOLIC_NAMES.length];
   public static final String _serializedATN = "\u0004\u0001\u0012A\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0004\u0001\u0013\b\u0001\u000b\u0001\f\u0001\u0014\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0003\u0003\u0003\u001d\b\u0003\u0001\u0003\u0001\u0003\u0003\u0003!\b\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0003\u0004&\b\u0004\u0001\u0004\u0001\u0004\u0003\u0004*\b\u0004\u0003\u0004,\b\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0005\u00052\b\u0005\n\u0005\f\u00055\t\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0003\u0005;\b\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0000\u0000\u0007\u0000\u0002\u0004\u0006\b\n\f\u0000\u0001\u0002\u0000\u0002\u0002\u0005\u0005A\u0000\u000e\u0001\u0000\u0000\u0000\u0002\u0012\u0001\u0000\u0000\u0000\u0004\u0018\u0001\u0000\u0000\u0000\u0006\u001c\u0001\u0000\u0000\u0000\b+\u0001\u0000\u0000\u0000\n:\u0001\u0000\u0000\u0000\f<\u0001\u0000\u0000\u0000\u000e\u000f\u0003\u0004\u0002\u0000\u000f\u0010\u0003\u0002\u0001\u0000\u0010\u0001\u0001\u0000\u0000\u0000\u0011\u0013\u0003\u0006\u0003\u0000\u0012\u0011\u0001\u0000\u0000\u0000\u0013\u0014\u0001\u0000\u0000\u0000\u0014\u0012\u0001\u0000\u0000\u0000\u0014\u0015\u0001\u0000\u0000\u0000\u0015\u0016\u0001\u0000\u0000\u0000\u0016\u0017\u0005\u0000\u0000\u0001\u0017\u0003\u0001\u0000\u0000\u0000\u0018\u0019\u0005\u0002\u0000\u0000\u0019\u001a\u0005\u000b\u0000\u0000\u001a\u0005\u0001\u0000\u0000\u0000\u001b\u001d\u0005\u0004\u0000\u0000\u001c\u001b\u0001\u0000\u0000\u0000\u001c\u001d\u0001\u0000\u0000\u0000\u001d\u001e\u0001\u0000\u0000\u0000\u001e \u0003\b\u0004\u0000\u001f!\u0003\n\u0005\u0000 \u001f\u0001\u0000\u0000\u0000 !\u0001\u0000\u0000\u0000!\u0007\u0001\u0000\u0000\u0000\"#\u0007\u0000\u0000\u0000#,\u0005\u000b\u0000\u0000$&\u0005\u0006\u0000\u0000%$\u0001\u0000\u0000\u0000%&\u0001\u0000\u0000\u0000&'\u0001\u0000\u0000\u0000')\u0005\u0007\u0000\u0000(*\u0005\t\u0000\u0000)(\u0001\u0000\u0000\u0000)*\u0001\u0000\u0000\u0000*,\u0001\u0000\u0000\u0000+\"\u0001\u0000\u0000\u0000+%\u0001\u0000\u0000\u0000,\t\u0001\u0000\u0000\u0000-.\u0005\b\u0000\u0000.3\u0003\f\u0006\u0000/0\u0005\u0010\u0000\u000002\u0003\f\u0006\u00001/\u0001\u0000\u0000\u000025\u0001\u0000\u0000\u000031\u0001\u0000\u0000\u000034\u0001\u0000\u0000\u000046\u0001\u0000\u0000\u000053\u0001\u0000\u0000\u000067\u0005\u000e\u0000\u00007;\u0001\u0000\u0000\u000089\u0005\b\u0000\u00009;\u0005\u000e\u0000\u0000:-\u0001\u0000\u0000\u0000:8\u0001\u0000\u0000\u0000;\u000b\u0001\u0000\u0000\u0000<=\u0005\f\u0000\u0000=>\u0005\r\u0000\u0000>?\u0005\u0012\u0000\u0000?\r\u0001\u0000\u0000\u0000\b\u0014\u001c %)+3:";
   public static final org.anti_ad.a.b.a.a.a.a.a _ATN;

   private static String[] makeRuleNames() {
      return new String[]{"customRuleEOF", "subRuleEOF", "head", "subRule", "subRuleIdentifier", "arguments", "pair"};
   }

   private static String[] makeLiteralNames() {
      return new String[]{null, null, "'@'", null, "'!'", "'::'", "'#'", null, "'('", null, null, null, null, "'='", "')'", null, "','"};
   }

   private static String[] makeSymbolicNames() {
      return new String[]{
         null,
         "WS",
         "AT",
         "ERR",
         "REVERSE",
         "DOUBLE_COLON",
         "HASHTAG",
         "NamespacedId",
         "OPEN",
         "NBT",
         "WS_mSubRule",
         "RuleName",
         "Parameter",
         "EQUAL",
         "CLOSE",
         "WS_mArgs",
         "COMMA",
         "WS_mArg",
         "Argument"
      };
   }

   @Deprecated
   @Override
   public String[] getTokenNames() {
      return tokenNames;
   }

   @Override
   public S getVocabulary() {
      return VOCABULARY;
   }

   @Override
   public String getGrammarFileName() {
      return "RulesParser.g4";
   }

   @Override
   public String[] getRuleNames() {
      return ruleNames;
   }

   @Override
   public String getSerializedATN() {
      return "\u0004\u0001\u0012A\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0004\u0001\u0013\b\u0001\u000b\u0001\f\u0001\u0014\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0003\u0003\u0003\u001d\b\u0003\u0001\u0003\u0001\u0003\u0003\u0003!\b\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0003\u0004&\b\u0004\u0001\u0004\u0001\u0004\u0003\u0004*\b\u0004\u0003\u0004,\b\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0005\u00052\b\u0005\n\u0005\f\u00055\t\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0003\u0005;\b\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0000\u0000\u0007\u0000\u0002\u0004\u0006\b\n\f\u0000\u0001\u0002\u0000\u0002\u0002\u0005\u0005A\u0000\u000e\u0001\u0000\u0000\u0000\u0002\u0012\u0001\u0000\u0000\u0000\u0004\u0018\u0001\u0000\u0000\u0000\u0006\u001c\u0001\u0000\u0000\u0000\b+\u0001\u0000\u0000\u0000\n:\u0001\u0000\u0000\u0000\f<\u0001\u0000\u0000\u0000\u000e\u000f\u0003\u0004\u0002\u0000\u000f\u0010\u0003\u0002\u0001\u0000\u0010\u0001\u0001\u0000\u0000\u0000\u0011\u0013\u0003\u0006\u0003\u0000\u0012\u0011\u0001\u0000\u0000\u0000\u0013\u0014\u0001\u0000\u0000\u0000\u0014\u0012\u0001\u0000\u0000\u0000\u0014\u0015\u0001\u0000\u0000\u0000\u0015\u0016\u0001\u0000\u0000\u0000\u0016\u0017\u0005\u0000\u0000\u0001\u0017\u0003\u0001\u0000\u0000\u0000\u0018\u0019\u0005\u0002\u0000\u0000\u0019\u001a\u0005\u000b\u0000\u0000\u001a\u0005\u0001\u0000\u0000\u0000\u001b\u001d\u0005\u0004\u0000\u0000\u001c\u001b\u0001\u0000\u0000\u0000\u001c\u001d\u0001\u0000\u0000\u0000\u001d\u001e\u0001\u0000\u0000\u0000\u001e \u0003\b\u0004\u0000\u001f!\u0003\n\u0005\u0000 \u001f\u0001\u0000\u0000\u0000 !\u0001\u0000\u0000\u0000!\u0007\u0001\u0000\u0000\u0000\"#\u0007\u0000\u0000\u0000#,\u0005\u000b\u0000\u0000$&\u0005\u0006\u0000\u0000%$\u0001\u0000\u0000\u0000%&\u0001\u0000\u0000\u0000&'\u0001\u0000\u0000\u0000')\u0005\u0007\u0000\u0000(*\u0005\t\u0000\u0000)(\u0001\u0000\u0000\u0000)*\u0001\u0000\u0000\u0000*,\u0001\u0000\u0000\u0000+\"\u0001\u0000\u0000\u0000+%\u0001\u0000\u0000\u0000,\t\u0001\u0000\u0000\u0000-.\u0005\b\u0000\u0000.3\u0003\f\u0006\u0000/0\u0005\u0010\u0000\u000002\u0003\f\u0006\u00001/\u0001\u0000\u0000\u000025\u0001\u0000\u0000\u000031\u0001\u0000\u0000\u000034\u0001\u0000\u0000\u000046\u0001\u0000\u0000\u000053\u0001\u0000\u0000\u000067\u0005\u000e\u0000\u00007;\u0001\u0000\u0000\u000089\u0005\b\u0000\u00009;\u0005\u000e\u0000\u0000:-\u0001\u0000\u0000\u0000:8\u0001\u0000\u0000\u0000;\u000b\u0001\u0000\u0000\u0000<=\u0005\f\u0000\u0000=>\u0005\r\u0000\u0000>?\u0005\u0012\u0000\u0000?\r\u0001\u0000\u0000\u0000\b\u0014\u001c %)+3:";
   }

   @Override
   public org.anti_ad.a.b.a.a.a.a.a getATN() {
      return _ATN;
   }

   public RulesParser(R var1) {
      super(var1);
      super._interp = new Y(this, _ATN, _decisionToDFA, _sharedContextCache);
   }

   public final RulesParser$CustomRuleEOFContext customRuleEOF() {
      RulesParser$CustomRuleEOFContext var1 = new RulesParser$CustomRuleEOFContext(super._ctx, this.getState());
      this.enterRule(var1, 0, 0);

      try {
         this.enterOuterAlt(var1, 1);
         this.setState(14);
         this.head();
         this.setState(15);
         this.subRuleEOF();
      } catch (J var5) {
         var1.exception = var5;
         super._errHandler.b(this, var5);
         super._errHandler.a(this, var5);
      } finally {
         this.exitRule();
      }

      return var1;
   }

   public final RulesParser$SubRuleEOFContext subRuleEOF() {
      RulesParser$SubRuleEOFContext var1 = new RulesParser$SubRuleEOFContext(super._ctx, this.getState());
      this.enterRule(var1, 2, 1);

      try {
         this.enterOuterAlt(var1, 1);
         this.setState(18);
         super._errHandler.b(this);
         super._input.a(1);

         int var2;
         do {
            this.setState(17);
            this.subRule();
            this.setState(20);
            super._errHandler.b(this);
         } while (((var2 = super._input.a(1)) & -64) == 0 && (1L << var2 & 244L) != 0L);

         this.setState(22);
         this.match(-1);
      } catch (J var5) {
         var1.exception = var5;
         super._errHandler.b(this, var5);
         super._errHandler.a(this, var5);
      } finally {
         this.exitRule();
      }

      return var1;
   }

   public final RulesParser$HeadContext head() {
      RulesParser$HeadContext var1 = new RulesParser$HeadContext(super._ctx, this.getState());
      this.enterRule(var1, 4, 2);

      try {
         this.enterOuterAlt(var1, 1);
         this.setState(24);
         this.match(2);
         this.setState(25);
         this.match(11);
      } catch (J var5) {
         var1.exception = var5;
         super._errHandler.b(this, var5);
         super._errHandler.a(this, var5);
      } finally {
         this.exitRule();
      }

      return var1;
   }

   public final RulesParser$SubRuleContext subRule() {
      RulesParser$SubRuleContext var1 = new RulesParser$SubRuleContext(super._ctx, this.getState());
      this.enterRule(var1, 6, 3);

      try {
         this.enterOuterAlt(var1, 1);
         this.setState(28);
         super._errHandler.b(this);
         if (super._input.a(1) == 4) {
            this.setState(27);
            this.match(4);
         }

         this.setState(30);
         this.subRuleIdentifier();
         this.setState(32);
         super._errHandler.b(this);
         if (super._input.a(1) == 8) {
            this.setState(31);
            this.arguments();
         }
      } catch (J var5) {
         var1.exception = var5;
         super._errHandler.b(this, var5);
         super._errHandler.a(this, var5);
      } finally {
         this.exitRule();
      }

      return var1;
   }

   public final RulesParser$SubRuleIdentifierContext subRuleIdentifier() {
      RulesParser$SubRuleIdentifierContext var1 = new RulesParser$SubRuleIdentifierContext(super._ctx, this.getState());
      this.enterRule(var1, 8, 4);

      try {
         this.setState(43);
         super._errHandler.b(this);
         switch (super._input.a(1)) {
            case 2:
            case 5:
               this.enterOuterAlt(var1, 1);
               this.setState(34);
               int var2;
               if ((var2 = super._input.a(1)) != 2 && var2 != 5) {
                  super._errHandler.a(this);
               } else {
                  if (super._input.a(1) == -1) {
                     super.matchedEOF = true;
                  }

                  super._errHandler.c();
                  this.consume();
               }

               this.setState(35);
               this.match(11);
               break;
            case 3:
            case 4:
            default:
               throw new C(this);
            case 6:
            case 7:
               this.enterOuterAlt(var1, 2);
               this.setState(37);
               super._errHandler.b(this);
               if (super._input.a(1) == 6) {
                  this.setState(36);
                  this.match(6);
               }

               this.setState(39);
               this.match(7);
               this.setState(41);
               super._errHandler.b(this);
               if (super._input.a(1) == 9) {
                  this.setState(40);
                  this.match(9);
               }
         }
      } catch (J var5) {
         var1.exception = var5;
         super._errHandler.b(this, var5);
         super._errHandler.a(this, var5);
      } finally {
         this.exitRule();
      }

      return var1;
   }

   public final RulesParser$ArgumentsContext arguments() {
      RulesParser$ArgumentsContext var1 = new RulesParser$ArgumentsContext(super._ctx, this.getState());
      this.enterRule(var1, 10, 5);

      try {
         this.setState(58);
         super._errHandler.b(this);
         switch (((Y)this.getInterpreter()).a(super._input, 7, super._ctx)) {
            case 1:
               this.enterOuterAlt(var1, 1);
               this.setState(45);
               this.match(8);
               this.setState(46);
               this.pair();
               this.setState(51);
               super._errHandler.b(this);

               for (int var2 = super._input.a(1); var2 == 16; var2 = super._input.a(1)) {
                  this.setState(47);
                  this.match(16);
                  this.setState(48);
                  this.pair();
                  this.setState(53);
                  super._errHandler.b(this);
               }

               this.setState(54);
               this.match(14);
               break;
            case 2:
               this.enterOuterAlt(var1, 2);
               this.setState(56);
               this.match(8);
               this.setState(57);
               this.match(14);
         }
      } catch (J var5) {
         var1.exception = var5;
         super._errHandler.b(this, var5);
         super._errHandler.a(this, var5);
      } finally {
         this.exitRule();
      }

      return var1;
   }

   public final RulesParser$PairContext pair() {
      RulesParser$PairContext var1 = new RulesParser$PairContext(super._ctx, this.getState());
      this.enterRule(var1, 12, 6);

      try {
         this.enterOuterAlt(var1, 1);
         this.setState(60);
         this.match(12);
         this.setState(61);
         this.match(13);
         this.setState(62);
         this.match(18);
      } catch (J var5) {
         var1.exception = var5;
         super._errHandler.b(this, var5);
         super._errHandler.a(this, var5);
      } finally {
         this.exitRule();
      }

      return var1;
   }

   static {
      N.a("4.13.2", "4.13.2");

      for (int var0 = 0; var0 < tokenNames.length; var0++) {
         tokenNames[var0] = VOCABULARY.a(var0);
         if (tokenNames[var0] == null) {
            tokenNames[var0] = VOCABULARY.b(var0);
         }

         if (tokenNames[var0] == null) {
            tokenNames[var0] = "<INVALID>";
         }
      }

      _decisionToDFA = new a[(_ATN = new h()
            .a(
               "\u0004\u0001\u0012A\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0004\u0001\u0013\b\u0001\u000b\u0001\f\u0001\u0014\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0003\u0003\u0003\u001d\b\u0003\u0001\u0003\u0001\u0003\u0003\u0003!\b\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0003\u0004&\b\u0004\u0001\u0004\u0001\u0004\u0003\u0004*\b\u0004\u0003\u0004,\b\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0005\u00052\b\u0005\n\u0005\f\u00055\t\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0003\u0005;\b\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0000\u0000\u0007\u0000\u0002\u0004\u0006\b\n\f\u0000\u0001\u0002\u0000\u0002\u0002\u0005\u0005A\u0000\u000e\u0001\u0000\u0000\u0000\u0002\u0012\u0001\u0000\u0000\u0000\u0004\u0018\u0001\u0000\u0000\u0000\u0006\u001c\u0001\u0000\u0000\u0000\b+\u0001\u0000\u0000\u0000\n:\u0001\u0000\u0000\u0000\f<\u0001\u0000\u0000\u0000\u000e\u000f\u0003\u0004\u0002\u0000\u000f\u0010\u0003\u0002\u0001\u0000\u0010\u0001\u0001\u0000\u0000\u0000\u0011\u0013\u0003\u0006\u0003\u0000\u0012\u0011\u0001\u0000\u0000\u0000\u0013\u0014\u0001\u0000\u0000\u0000\u0014\u0012\u0001\u0000\u0000\u0000\u0014\u0015\u0001\u0000\u0000\u0000\u0015\u0016\u0001\u0000\u0000\u0000\u0016\u0017\u0005\u0000\u0000\u0001\u0017\u0003\u0001\u0000\u0000\u0000\u0018\u0019\u0005\u0002\u0000\u0000\u0019\u001a\u0005\u000b\u0000\u0000\u001a\u0005\u0001\u0000\u0000\u0000\u001b\u001d\u0005\u0004\u0000\u0000\u001c\u001b\u0001\u0000\u0000\u0000\u001c\u001d\u0001\u0000\u0000\u0000\u001d\u001e\u0001\u0000\u0000\u0000\u001e \u0003\b\u0004\u0000\u001f!\u0003\n\u0005\u0000 \u001f\u0001\u0000\u0000\u0000 !\u0001\u0000\u0000\u0000!\u0007\u0001\u0000\u0000\u0000\"#\u0007\u0000\u0000\u0000#,\u0005\u000b\u0000\u0000$&\u0005\u0006\u0000\u0000%$\u0001\u0000\u0000\u0000%&\u0001\u0000\u0000\u0000&'\u0001\u0000\u0000\u0000')\u0005\u0007\u0000\u0000(*\u0005\t\u0000\u0000)(\u0001\u0000\u0000\u0000)*\u0001\u0000\u0000\u0000*,\u0001\u0000\u0000\u0000+\"\u0001\u0000\u0000\u0000+%\u0001\u0000\u0000\u0000,\t\u0001\u0000\u0000\u0000-.\u0005\b\u0000\u0000.3\u0003\f\u0006\u0000/0\u0005\u0010\u0000\u000002\u0003\f\u0006\u00001/\u0001\u0000\u0000\u000025\u0001\u0000\u0000\u000031\u0001\u0000\u0000\u000034\u0001\u0000\u0000\u000046\u0001\u0000\u0000\u000053\u0001\u0000\u0000\u000067\u0005\u000e\u0000\u00007;\u0001\u0000\u0000\u000089\u0005\b\u0000\u00009;\u0005\u000e\u0000\u0000:-\u0001\u0000\u0000\u0000:8\u0001\u0000\u0000\u0000;\u000b\u0001\u0000\u0000\u0000<=\u0005\f\u0000\u0000=>\u0005\r\u0000\u0000>?\u0005\u0012\u0000\u0000?\r\u0001\u0000\u0000\u0000\b\u0014\u001c %)+3:"
                  .toCharArray()
            ))
         .a()];

      for (int var1 = 0; var1 < _ATN.a(); var1++) {
         _decisionToDFA[var1] = new a(_ATN.a(var1), var1);
      }
   }
}
