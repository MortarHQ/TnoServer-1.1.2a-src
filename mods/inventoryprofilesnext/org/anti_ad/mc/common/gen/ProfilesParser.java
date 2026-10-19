package org.anti_ad.mc.common.gen;

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

public class ProfilesParser extends D {
   protected static final a[] _decisionToDFA;
   protected static final ag _sharedContextCache = new ag();
   public static final int WS = 1;
   public static final int PROFILE = 2;
   public static final int ACTIVATE = 3;
   public static final int HOT1 = 4;
   public static final int HOT2 = 5;
   public static final int HOT3 = 6;
   public static final int HOT4 = 7;
   public static final int HOT5 = 8;
   public static final int HOT6 = 9;
   public static final int HOT7 = 10;
   public static final int HOT8 = 11;
   public static final int HOT9 = 12;
   public static final int CHESTPLATE = 13;
   public static final int LEGS = 14;
   public static final int FEET = 15;
   public static final int HEAD = 16;
   public static final int OFFHAND = 17;
   public static final int COMMA = 18;
   public static final int LPAREN = 19;
   public static final int RPAREN = 20;
   public static final int LBRACK = 21;
   public static final int RBRACK = 22;
   public static final int LBRACE = 23;
   public static final int RBRACE = 24;
   public static final int DQUOTE = 25;
   public static final int LID = 26;
   public static final int COLON = 27;
   public static final int ARROW = 28;
   public static final int Level = 29;
   public static final int Id = 30;
   public static final int NamespacedId = 31;
   public static final int STRING = 32;
   public static final int RULE_script = 0;
   public static final int RULE_profile = 1;
   public static final int RULE_slotsDef = 2;
   public static final int RULE_slotDef = 3;
   public static final int RULE_itemDef = 4;
   public static final int RULE_itemName = 5;
   public static final int RULE_customName = 6;
   public static final int RULE_components = 7;
   public static final int RULE_component = 8;
   public static final int RULE_name = 9;
   public static final int RULE_slotname = 10;
   public static final int RULE_activeSlotName = 11;
   public static final String[] ruleNames = makeRuleNames();
   private static final String[] _LITERAL_NAMES = makeLiteralNames();
   private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
   public static final S VOCABULARY = new T(_LITERAL_NAMES, _SYMBOLIC_NAMES);
   @Deprecated
   public static final String[] tokenNames = new String[_SYMBOLIC_NAMES.length];
   public static final String _serializedATN = "\u0004\u0001 Z\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0001\u0000\u0005\u0000\u001a\b\u0000\n\u0000\f\u0000\u001d\t\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0003\u0001%\b\u0001\u0001\u0001\u0001\u0001\u0001\u0002\u0005\u0002*\b\u0002\n\u0002\f\u0002-\t\u0002\u0001\u0003\u0001\u0003\u0005\u00031\b\u0003\n\u0003\f\u00034\t\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0003\u00049\b\u0004\u0001\u0005\u0001\u0005\u0003\u0005=\b\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0005\u0007G\b\u0007\n\u0007\f\u0007J\t\u0007\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0003\bP\b\b\u0001\t\u0001\t\u0001\t\u0001\t\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0001\u000b\u00022H\u0000\f\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0000\u0002\u0001\u0000\u0004\u0011\u0001\u0000\u0004\fU\u0000\u001b\u0001\u0000\u0000\u0000\u0002 \u0001\u0000\u0000\u0000\u0004+\u0001\u0000\u0000\u0000\u0006.\u0001\u0000\u0000\u0000\b5\u0001\u0000\u0000\u0000\n:\u0001\u0000\u0000\u0000\f>\u0001\u0000\u0000\u0000\u000eB\u0001\u0000\u0000\u0000\u0010M\u0001\u0000\u0000\u0000\u0012Q\u0001\u0000\u0000\u0000\u0014U\u0001\u0000\u0000\u0000\u0016W\u0001\u0000\u0000\u0000\u0018\u001a\u0003\u0002\u0001\u0000\u0019\u0018\u0001\u0000\u0000\u0000\u001a\u001d\u0001\u0000\u0000\u0000\u001b\u0019\u0001\u0000\u0000\u0000\u001b\u001c\u0001\u0000\u0000\u0000\u001c\u001e\u0001\u0000\u0000\u0000\u001d\u001b\u0001\u0000\u0000\u0000\u001e\u001f\u0005\u0000\u0000\u0001\u001f\u0001\u0001\u0000\u0000\u0000 !\u0005\u0002\u0000\u0000!$\u0005\u001e\u0000\u0000\"#\u0005\u0003\u0000\u0000#%\u0003\u0016\u000b\u0000$\"\u0001\u0000\u0000\u0000$%\u0001\u0000\u0000\u0000%&\u0001\u0000\u0000\u0000&'\u0003\u0004\u0002\u0000'\u0003\u0001\u0000\u0000\u0000(*\u0003\u0006\u0003\u0000)(\u0001\u0000\u0000\u0000*-\u0001\u0000\u0000\u0000+)\u0001\u0000\u0000\u0000+,\u0001\u0000\u0000\u0000,\u0005\u0001\u0000\u0000\u0000-+\u0001\u0000\u0000\u0000.2\u0003\u0014\n\u0000/1\u0003\b\u0004\u00000/\u0001\u0000\u0000\u000014\u0001\u0000\u0000\u000023\u0001\u0000\u0000\u000020\u0001\u0000\u0000\u00003\u0007\u0001\u0000\u0000\u000042\u0001\u0000\u0000\u000058\u0003\n\u0005\u000067\u0005\u001c\u0000\u000079\u0003\u000e\u0007\u000086\u0001\u0000\u0000\u000089\u0001\u0000\u0000\u00009\t\u0001\u0000\u0000\u0000:<\u0005\u001f\u0000\u0000;=\u0003\f\u0006\u0000<;\u0001\u0000\u0000\u0000<=\u0001\u0000\u0000\u0000=\u000b\u0001\u0000\u0000\u0000>?\u0005\u0013\u0000\u0000?@\u0005 \u0000\u0000@A\u0005\u0014\u0000\u0000A\r\u0001\u0000\u0000\u0000BC\u0005\u0015\u0000\u0000CH\u0003\u0010\b\u0000DE\u0005\u0012\u0000\u0000EG\u0003\u0010\b\u0000FD\u0001\u0000\u0000\u0000GJ\u0001\u0000\u0000\u0000HI\u0001\u0000\u0000\u0000HF\u0001\u0000\u0000\u0000IK\u0001\u0000\u0000\u0000JH\u0001\u0000\u0000\u0000KL\u0005\u0016\u0000\u0000L\u000f\u0001\u0000\u0000\u0000MO\u0005\u001f\u0000\u0000NP\u0003\f\u0006\u0000ON\u0001\u0000\u0000\u0000OP\u0001\u0000\u0000\u0000P\u0011\u0001\u0000\u0000\u0000QR\u0005\u001a\u0000\u0000RS\u0005\u001b\u0000\u0000ST\u0005\u001f\u0000\u0000T\u0013\u0001\u0000\u0000\u0000UV\u0007\u0000\u0000\u0000V\u0015\u0001\u0000\u0000\u0000WX\u0007\u0001\u0000\u0000X\u0017\u0001\u0000\u0000\u0000\b\u001b$+28<HO";
   public static final org.anti_ad.a.b.a.a.a.a.a _ATN;

   private static String[] makeRuleNames() {
      return new String[]{
         "script", "profile", "slotsDef", "slotDef", "itemDef", "itemName", "customName", "components", "component", "name", "slotname", "activeSlotName"
      };
   }

   private static String[] makeLiteralNames() {
      return new String[]{
         null,
         null,
         "'profile'",
         "'activate'",
         "'HOT1'",
         "'HOT2'",
         "'HOT3'",
         "'HOT4'",
         "'HOT5'",
         "'HOT6'",
         "'HOT7'",
         "'HOT8'",
         "'HOT9'",
         "'CHEST'",
         "'LEGS'",
         "'FEET'",
         "'HEAD'",
         "'OFFHAND'",
         "','",
         "'('",
         "')'",
         "'['",
         "']'",
         "'{'",
         "'}'",
         "'\"'",
         "'id'",
         "':'",
         "'->'"
      };
   }

   private static String[] makeSymbolicNames() {
      return new String[]{
         null,
         "WS",
         "PROFILE",
         "ACTIVATE",
         "HOT1",
         "HOT2",
         "HOT3",
         "HOT4",
         "HOT5",
         "HOT6",
         "HOT7",
         "HOT8",
         "HOT9",
         "CHESTPLATE",
         "LEGS",
         "FEET",
         "HEAD",
         "OFFHAND",
         "COMMA",
         "LPAREN",
         "RPAREN",
         "LBRACK",
         "RBRACK",
         "LBRACE",
         "RBRACE",
         "DQUOTE",
         "LID",
         "COLON",
         "ARROW",
         "Level",
         "Id",
         "NamespacedId",
         "STRING"
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
      return "ProfilesParser.g4";
   }

   @Override
   public String[] getRuleNames() {
      return ruleNames;
   }

   @Override
   public String getSerializedATN() {
      return "\u0004\u0001 Z\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0001\u0000\u0005\u0000\u001a\b\u0000\n\u0000\f\u0000\u001d\t\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0003\u0001%\b\u0001\u0001\u0001\u0001\u0001\u0001\u0002\u0005\u0002*\b\u0002\n\u0002\f\u0002-\t\u0002\u0001\u0003\u0001\u0003\u0005\u00031\b\u0003\n\u0003\f\u00034\t\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0003\u00049\b\u0004\u0001\u0005\u0001\u0005\u0003\u0005=\b\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0005\u0007G\b\u0007\n\u0007\f\u0007J\t\u0007\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0003\bP\b\b\u0001\t\u0001\t\u0001\t\u0001\t\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0001\u000b\u00022H\u0000\f\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0000\u0002\u0001\u0000\u0004\u0011\u0001\u0000\u0004\fU\u0000\u001b\u0001\u0000\u0000\u0000\u0002 \u0001\u0000\u0000\u0000\u0004+\u0001\u0000\u0000\u0000\u0006.\u0001\u0000\u0000\u0000\b5\u0001\u0000\u0000\u0000\n:\u0001\u0000\u0000\u0000\f>\u0001\u0000\u0000\u0000\u000eB\u0001\u0000\u0000\u0000\u0010M\u0001\u0000\u0000\u0000\u0012Q\u0001\u0000\u0000\u0000\u0014U\u0001\u0000\u0000\u0000\u0016W\u0001\u0000\u0000\u0000\u0018\u001a\u0003\u0002\u0001\u0000\u0019\u0018\u0001\u0000\u0000\u0000\u001a\u001d\u0001\u0000\u0000\u0000\u001b\u0019\u0001\u0000\u0000\u0000\u001b\u001c\u0001\u0000\u0000\u0000\u001c\u001e\u0001\u0000\u0000\u0000\u001d\u001b\u0001\u0000\u0000\u0000\u001e\u001f\u0005\u0000\u0000\u0001\u001f\u0001\u0001\u0000\u0000\u0000 !\u0005\u0002\u0000\u0000!$\u0005\u001e\u0000\u0000\"#\u0005\u0003\u0000\u0000#%\u0003\u0016\u000b\u0000$\"\u0001\u0000\u0000\u0000$%\u0001\u0000\u0000\u0000%&\u0001\u0000\u0000\u0000&'\u0003\u0004\u0002\u0000'\u0003\u0001\u0000\u0000\u0000(*\u0003\u0006\u0003\u0000)(\u0001\u0000\u0000\u0000*-\u0001\u0000\u0000\u0000+)\u0001\u0000\u0000\u0000+,\u0001\u0000\u0000\u0000,\u0005\u0001\u0000\u0000\u0000-+\u0001\u0000\u0000\u0000.2\u0003\u0014\n\u0000/1\u0003\b\u0004\u00000/\u0001\u0000\u0000\u000014\u0001\u0000\u0000\u000023\u0001\u0000\u0000\u000020\u0001\u0000\u0000\u00003\u0007\u0001\u0000\u0000\u000042\u0001\u0000\u0000\u000058\u0003\n\u0005\u000067\u0005\u001c\u0000\u000079\u0003\u000e\u0007\u000086\u0001\u0000\u0000\u000089\u0001\u0000\u0000\u00009\t\u0001\u0000\u0000\u0000:<\u0005\u001f\u0000\u0000;=\u0003\f\u0006\u0000<;\u0001\u0000\u0000\u0000<=\u0001\u0000\u0000\u0000=\u000b\u0001\u0000\u0000\u0000>?\u0005\u0013\u0000\u0000?@\u0005 \u0000\u0000@A\u0005\u0014\u0000\u0000A\r\u0001\u0000\u0000\u0000BC\u0005\u0015\u0000\u0000CH\u0003\u0010\b\u0000DE\u0005\u0012\u0000\u0000EG\u0003\u0010\b\u0000FD\u0001\u0000\u0000\u0000GJ\u0001\u0000\u0000\u0000HI\u0001\u0000\u0000\u0000HF\u0001\u0000\u0000\u0000IK\u0001\u0000\u0000\u0000JH\u0001\u0000\u0000\u0000KL\u0005\u0016\u0000\u0000L\u000f\u0001\u0000\u0000\u0000MO\u0005\u001f\u0000\u0000NP\u0003\f\u0006\u0000ON\u0001\u0000\u0000\u0000OP\u0001\u0000\u0000\u0000P\u0011\u0001\u0000\u0000\u0000QR\u0005\u001a\u0000\u0000RS\u0005\u001b\u0000\u0000ST\u0005\u001f\u0000\u0000T\u0013\u0001\u0000\u0000\u0000UV\u0007\u0000\u0000\u0000V\u0015\u0001\u0000\u0000\u0000WX\u0007\u0001\u0000\u0000X\u0017\u0001\u0000\u0000\u0000\b\u001b$+28<HO";
   }

   @Override
   public org.anti_ad.a.b.a.a.a.a.a getATN() {
      return _ATN;
   }

   public ProfilesParser(R var1) {
      super(var1);
      super._interp = new Y(this, _ATN, _decisionToDFA, _sharedContextCache);
   }

   public final ProfilesParser$ScriptContext script() {
      ProfilesParser$ScriptContext var1 = new ProfilesParser$ScriptContext(super._ctx, this.getState());
      this.enterRule(var1, 0, 0);

      try {
         this.enterOuterAlt(var1, 1);
         this.setState(27);
         super._errHandler.b(this);

         for (int var2 = super._input.a(1); var2 == 2; var2 = super._input.a(1)) {
            this.setState(24);
            this.profile();
            this.setState(29);
            super._errHandler.b(this);
         }

         this.setState(30);
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

   public final ProfilesParser$ProfileContext profile() {
      ProfilesParser$ProfileContext var1 = new ProfilesParser$ProfileContext(super._ctx, this.getState());
      this.enterRule(var1, 2, 1);

      try {
         this.enterOuterAlt(var1, 1);
         this.setState(32);
         this.match(2);
         this.setState(33);
         this.match(30);
         this.setState(36);
         super._errHandler.b(this);
         if (super._input.a(1) == 3) {
            this.setState(34);
            this.match(3);
            this.setState(35);
            this.activeSlotName();
         }

         this.setState(38);
         this.slotsDef();
      } catch (J var5) {
         var1.exception = var5;
         super._errHandler.b(this, var5);
         super._errHandler.a(this, var5);
      } finally {
         this.exitRule();
      }

      return var1;
   }

   public final ProfilesParser$SlotsDefContext slotsDef() {
      ProfilesParser$SlotsDefContext var1 = new ProfilesParser$SlotsDefContext(super._ctx, this.getState());
      this.enterRule(var1, 4, 2);

      try {
         this.enterOuterAlt(var1, 1);
         this.setState(43);
         super._errHandler.b(this);

         for (int var2 = super._input.a(1); (var2 & -64) == 0 && (1L << var2 & 262128L) != 0L; var2 = super._input.a(1)) {
            this.setState(40);
            this.slotDef();
            this.setState(45);
            super._errHandler.b(this);
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

   public final ProfilesParser$SlotDefContext slotDef() {
      ProfilesParser$SlotDefContext var1 = new ProfilesParser$SlotDefContext(super._ctx, this.getState());
      this.enterRule(var1, 6, 3);

      try {
         this.enterOuterAlt(var1, 1);
         this.setState(46);
         this.slotname();
         this.setState(50);
         super._errHandler.b(this);

         for (int var2 = ((Y)this.getInterpreter()).a(super._input, 3, super._ctx);
            var2 != 1 && var2 != 0;
            var2 = ((Y)this.getInterpreter()).a(super._input, 3, super._ctx)
         ) {
            if (var2 == 2) {
               this.setState(47);
               this.itemDef();
            }

            this.setState(52);
            super._errHandler.b(this);
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

   public final ProfilesParser$ItemDefContext itemDef() {
      ProfilesParser$ItemDefContext var1 = new ProfilesParser$ItemDefContext(super._ctx, this.getState());
      this.enterRule(var1, 8, 4);

      try {
         this.enterOuterAlt(var1, 1);
         this.setState(53);
         this.itemName();
         this.setState(56);
         super._errHandler.b(this);
         if (super._input.a(1) == 28) {
            this.setState(54);
            this.match(28);
            this.setState(55);
            this.components();
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

   public final ProfilesParser$ItemNameContext itemName() {
      ProfilesParser$ItemNameContext var1 = new ProfilesParser$ItemNameContext(super._ctx, this.getState());
      this.enterRule(var1, 10, 5);

      try {
         this.enterOuterAlt(var1, 1);
         this.setState(58);
         this.match(31);
         this.setState(60);
         super._errHandler.b(this);
         if (super._input.a(1) == 19) {
            this.setState(59);
            this.customName();
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

   public final ProfilesParser$CustomNameContext customName() {
      ProfilesParser$CustomNameContext var1 = new ProfilesParser$CustomNameContext(super._ctx, this.getState());
      this.enterRule(var1, 12, 6);

      try {
         this.enterOuterAlt(var1, 1);
         this.setState(62);
         this.match(19);
         this.setState(63);
         this.match(32);
         this.setState(64);
         this.match(20);
      } catch (J var5) {
         var1.exception = var5;
         super._errHandler.b(this, var5);
         super._errHandler.a(this, var5);
      } finally {
         this.exitRule();
      }

      return var1;
   }

   public final ProfilesParser$ComponentsContext components() {
      ProfilesParser$ComponentsContext var1 = new ProfilesParser$ComponentsContext(super._ctx, this.getState());
      this.enterRule(var1, 14, 7);

      try {
         this.enterOuterAlt(var1, 1);
         this.setState(66);
         this.match(21);
         this.setState(67);
         this.component();
         this.setState(72);
         super._errHandler.b(this);

         for (int var2 = ((Y)this.getInterpreter()).a(super._input, 6, super._ctx);
            var2 != 1 && var2 != 0;
            var2 = ((Y)this.getInterpreter()).a(super._input, 6, super._ctx)
         ) {
            if (var2 == 2) {
               this.setState(68);
               this.match(18);
               this.setState(69);
               this.component();
            }

            this.setState(74);
            super._errHandler.b(this);
         }

         this.setState(75);
         this.match(22);
      } catch (J var5) {
         var1.exception = var5;
         super._errHandler.b(this, var5);
         super._errHandler.a(this, var5);
      } finally {
         this.exitRule();
      }

      return var1;
   }

   public final ProfilesParser$ComponentContext component() {
      ProfilesParser$ComponentContext var1 = new ProfilesParser$ComponentContext(super._ctx, this.getState());
      this.enterRule(var1, 16, 8);

      try {
         this.enterOuterAlt(var1, 1);
         this.setState(77);
         this.match(31);
         this.setState(79);
         super._errHandler.b(this);
         if (super._input.a(1) == 19) {
            this.setState(78);
            this.customName();
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

   public final ProfilesParser$NameContext name() {
      ProfilesParser$NameContext var1 = new ProfilesParser$NameContext(super._ctx, this.getState());
      this.enterRule(var1, 18, 9);

      try {
         this.enterOuterAlt(var1, 1);
         this.setState(81);
         this.match(26);
         this.setState(82);
         this.match(27);
         this.setState(83);
         this.match(31);
      } catch (J var5) {
         var1.exception = var5;
         super._errHandler.b(this, var5);
         super._errHandler.a(this, var5);
      } finally {
         this.exitRule();
      }

      return var1;
   }

   public final ProfilesParser$SlotnameContext slotname() {
      ProfilesParser$SlotnameContext var1 = new ProfilesParser$SlotnameContext(super._ctx, this.getState());
      this.enterRule(var1, 20, 10);

      try {
         this.enterOuterAlt(var1, 1);
         this.setState(85);
         int var2;
         if (((var2 = super._input.a(1)) & -64) == 0 && (1L << var2 & 262128L) != 0L) {
            if (super._input.a(1) == -1) {
               super.matchedEOF = true;
            }

            super._errHandler.c();
            this.consume();
         } else {
            super._errHandler.a(this);
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

   public final ProfilesParser$ActiveSlotNameContext activeSlotName() {
      ProfilesParser$ActiveSlotNameContext var1 = new ProfilesParser$ActiveSlotNameContext(super._ctx, this.getState());
      this.enterRule(var1, 22, 11);

      try {
         this.enterOuterAlt(var1, 1);
         this.setState(87);
         int var2;
         if (((var2 = super._input.a(1)) & -64) == 0 && (1L << var2 & 8176L) != 0L) {
            if (super._input.a(1) == -1) {
               super.matchedEOF = true;
            }

            super._errHandler.c();
            this.consume();
         } else {
            super._errHandler.a(this);
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
               "\u0004\u0001 Z\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0001\u0000\u0005\u0000\u001a\b\u0000\n\u0000\f\u0000\u001d\t\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0003\u0001%\b\u0001\u0001\u0001\u0001\u0001\u0001\u0002\u0005\u0002*\b\u0002\n\u0002\f\u0002-\t\u0002\u0001\u0003\u0001\u0003\u0005\u00031\b\u0003\n\u0003\f\u00034\t\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0003\u00049\b\u0004\u0001\u0005\u0001\u0005\u0003\u0005=\b\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0005\u0007G\b\u0007\n\u0007\f\u0007J\t\u0007\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0003\bP\b\b\u0001\t\u0001\t\u0001\t\u0001\t\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0001\u000b\u00022H\u0000\f\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0000\u0002\u0001\u0000\u0004\u0011\u0001\u0000\u0004\fU\u0000\u001b\u0001\u0000\u0000\u0000\u0002 \u0001\u0000\u0000\u0000\u0004+\u0001\u0000\u0000\u0000\u0006.\u0001\u0000\u0000\u0000\b5\u0001\u0000\u0000\u0000\n:\u0001\u0000\u0000\u0000\f>\u0001\u0000\u0000\u0000\u000eB\u0001\u0000\u0000\u0000\u0010M\u0001\u0000\u0000\u0000\u0012Q\u0001\u0000\u0000\u0000\u0014U\u0001\u0000\u0000\u0000\u0016W\u0001\u0000\u0000\u0000\u0018\u001a\u0003\u0002\u0001\u0000\u0019\u0018\u0001\u0000\u0000\u0000\u001a\u001d\u0001\u0000\u0000\u0000\u001b\u0019\u0001\u0000\u0000\u0000\u001b\u001c\u0001\u0000\u0000\u0000\u001c\u001e\u0001\u0000\u0000\u0000\u001d\u001b\u0001\u0000\u0000\u0000\u001e\u001f\u0005\u0000\u0000\u0001\u001f\u0001\u0001\u0000\u0000\u0000 !\u0005\u0002\u0000\u0000!$\u0005\u001e\u0000\u0000\"#\u0005\u0003\u0000\u0000#%\u0003\u0016\u000b\u0000$\"\u0001\u0000\u0000\u0000$%\u0001\u0000\u0000\u0000%&\u0001\u0000\u0000\u0000&'\u0003\u0004\u0002\u0000'\u0003\u0001\u0000\u0000\u0000(*\u0003\u0006\u0003\u0000)(\u0001\u0000\u0000\u0000*-\u0001\u0000\u0000\u0000+)\u0001\u0000\u0000\u0000+,\u0001\u0000\u0000\u0000,\u0005\u0001\u0000\u0000\u0000-+\u0001\u0000\u0000\u0000.2\u0003\u0014\n\u0000/1\u0003\b\u0004\u00000/\u0001\u0000\u0000\u000014\u0001\u0000\u0000\u000023\u0001\u0000\u0000\u000020\u0001\u0000\u0000\u00003\u0007\u0001\u0000\u0000\u000042\u0001\u0000\u0000\u000058\u0003\n\u0005\u000067\u0005\u001c\u0000\u000079\u0003\u000e\u0007\u000086\u0001\u0000\u0000\u000089\u0001\u0000\u0000\u00009\t\u0001\u0000\u0000\u0000:<\u0005\u001f\u0000\u0000;=\u0003\f\u0006\u0000<;\u0001\u0000\u0000\u0000<=\u0001\u0000\u0000\u0000=\u000b\u0001\u0000\u0000\u0000>?\u0005\u0013\u0000\u0000?@\u0005 \u0000\u0000@A\u0005\u0014\u0000\u0000A\r\u0001\u0000\u0000\u0000BC\u0005\u0015\u0000\u0000CH\u0003\u0010\b\u0000DE\u0005\u0012\u0000\u0000EG\u0003\u0010\b\u0000FD\u0001\u0000\u0000\u0000GJ\u0001\u0000\u0000\u0000HI\u0001\u0000\u0000\u0000HF\u0001\u0000\u0000\u0000IK\u0001\u0000\u0000\u0000JH\u0001\u0000\u0000\u0000KL\u0005\u0016\u0000\u0000L\u000f\u0001\u0000\u0000\u0000MO\u0005\u001f\u0000\u0000NP\u0003\f\u0006\u0000ON\u0001\u0000\u0000\u0000OP\u0001\u0000\u0000\u0000P\u0011\u0001\u0000\u0000\u0000QR\u0005\u001a\u0000\u0000RS\u0005\u001b\u0000\u0000ST\u0005\u001f\u0000\u0000T\u0013\u0001\u0000\u0000\u0000UV\u0007\u0000\u0000\u0000V\u0015\u0001\u0000\u0000\u0000WX\u0007\u0001\u0000\u0000X\u0017\u0001\u0000\u0000\u0000\b\u001b$+28<HO"
                  .toCharArray()
            ))
         .a()];

      for (int var1 = 0; var1 < _ATN.a(); var1++) {
         _decisionToDFA[var1] = new a(_ATN.a(var1), var1);
      }
   }
}
