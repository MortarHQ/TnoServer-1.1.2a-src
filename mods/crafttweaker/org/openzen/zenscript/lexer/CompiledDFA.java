package org.openzen.zenscript.lexer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CompiledDFA<T> {
   public Map<Integer, Integer>[] transitions;
   public T[] finals;

   public CompiledDFA(Map<Integer, Integer>[] transitions, T[] finals) {
      this.transitions = transitions;
      this.finals = finals;
   }

   public static <T extends TokenType & Comparable<T>> CompiledDFA<T> createLexerDFA(T[] tokenTypes, Class<T> tokenClass) {
      List<T> tokens = new ArrayList<>();
      List<String> regexps = new ArrayList<>();

      for (T tokenType : tokenTypes) {
         if (tokenType.getRegexp() != null) {
            tokens.add(tokenType);
            regexps.add(tokenType.getRegexp());
         }
      }

      return new NFA<>(regexps, tokens, tokenClass).compile();
   }

   public T eval(String value) {
      int state = 0;

      for (char c : value.toCharArray()) {
         state = this.transitions[state].get(c);
         if (state == Integer.MIN_VALUE) {
            return null;
         }
      }

      return this.finals[state];
   }

   public boolean matches(String value) {
      return this.eval(value) != null;
   }

   @Override
   public String toString() {
      StringBuilder result = new StringBuilder();

      for (int i = 0; i < this.transitions.length; i++) {
         Map<Integer, Integer> map = this.transitions[i];

         for (int v : map.keySet()) {
            result.append("edge(");
            result.append(i);
            result.append(", ");
            result.append(v);
            result.append("): ");
            result.append(map.get(v));
            result.append("\r\n");
         }
      }

      for (int i = 0; i < this.finals.length; i++) {
         if (this.finals[i] != null) {
            result.append("final(");
            result.append(i);
            result.append("): ");
            result.append(this.finals[i]);
            result.append("\r\n");
         }
      }

      return result.toString();
   }
}
