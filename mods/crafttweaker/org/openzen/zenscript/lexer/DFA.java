package org.openzen.zenscript.lexer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class DFA<T> {
   public static final int NOFINAL = Integer.MIN_VALUE;
   private final Class<T> tokenClass;
   private DFA.DFAState<T> initial;

   public DFA(Class<T> tokenClass, DFA.DFAState<T> initial) {
      this.tokenClass = tokenClass;
      this.initial = initial;
   }

   public CompiledDFA<T> compile() {
      ArrayList<DFA.DFAState<T>> nodeList = new ArrayList<>();
      HashMap<DFA.DFAState<T>, Integer> nodes = new HashMap<>();
      nodes.put(this.initial, 0);
      nodeList.add(this.initial);
      int counter = 1;
      Queue<DFA.DFAState<T>> todo = new LinkedList<>();
      todo.add(this.initial);

      while (!todo.isEmpty()) {
         DFA.DFAState<T> current = todo.poll();

         for (int k : current.transitions.keySet()) {
            DFA.DFAState<T> next = current.transitions.get(k);
            if (!nodes.containsKey(next)) {
               todo.add(next);
               nodes.put(next, counter++);
               nodeList.add(next);
            }
         }
      }

      Map<Integer, Integer>[] transitions = new HashMap[counter];
      T[] finals2 = (T[])((Object[])Array.newInstance(this.tokenClass, counter));

      for (DFA.DFAState<T> node : nodeList) {
         int index = nodes.get(node);
         finals2[index] = node.finalCode;
         transitions[index] = new HashMap<>();

         for (int k : node.transitions.keySet()) {
            DFA.DFAState<T> next = node.transitions.get(k);
            transitions[index].put(k, nodes.get(next));
         }
      }

      return new CompiledDFA<>(transitions, finals2);
   }

   public DFA<T> optimize() {
      CompiledDFA<T> compiled = this.compile();
      Map<Integer, Integer>[] transitions = compiled.transitions;
      int size = transitions.length;
      Set<Integer> alphabet = new HashSet<>();

      for (int i = 0; i < size; i++) {
         for (int k : transitions[i].keySet()) {
            alphabet.add(k);
         }
      }

      boolean[][] distinguishable = new boolean[size + 1][size + 1];

      for (int i = 0; i < size; i++) {
         for (int j = 0; j < size; j++) {
            distinguishable[i][j] = compiled.finals[i] != compiled.finals[j];
         }
      }

      for (int i = 0; i < size; i++) {
         distinguishable[i][size] = true;
         distinguishable[size][i] = true;
      }

      boolean changed;
      do {
         changed = false;

         for (int x : alphabet) {
            for (int i = 0; i < size; i++) {
               int ti = transitions[i].containsKey(x) ? transitions[i].get(x) : size;

               for (int j = 0; j < size; j++) {
                  if (!distinguishable[i][j]) {
                     int tj = transitions[j].containsKey(x) ? transitions[j].get(x) : size;
                     if (distinguishable[ti][tj]) {
                        distinguishable[i][j] = true;
                        changed = true;
                     }
                  }
               }
            }
         }
      } while (changed);

      Map<Integer, DFA.DFAState<T>> nodeMap = new HashMap<>();

      label95:
      for (int i = 0; i < size; i++) {
         for (int j = 0; j < size; j++) {
            if (!distinguishable[i][j] && nodeMap.containsKey(j)) {
               nodeMap.put(i, nodeMap.get(j));
               if (compiled.finals[i] != null && nodeMap.get(j).getFinal() != null && nodeMap.get(j).getFinal() != compiled.finals[i]) {
                  throw new RuntimeException("Eh?");
               }
               continue label95;
            }
         }

         DFA.DFAState<T> node = new DFA.DFAState<>();
         node.setFinal(compiled.finals[i]);
         nodeMap.put(i, node);
      }

      for (int i = 0; i < compiled.transitions.length; i++) {
         for (int k : transitions[i].keySet()) {
            nodeMap.get(i).addTransition(k, nodeMap.get(transitions[i].get(k)));
         }
      }

      return new DFA<>(this.tokenClass, nodeMap.get(0));
   }

   @Override
   public String toString() {
      StringBuilder result = new StringBuilder();
      CompiledDFA<T> dfs = this.compile();

      for (int i = 0; i < dfs.transitions.length; i++) {
         Map<Integer, Integer> map = dfs.transitions[i];

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

      for (int i = 0; i < dfs.finals.length; i++) {
         if (dfs.finals[i] != null) {
            result.append("final(");
            result.append(i);
            result.append("): ");
            result.append(dfs.finals[i]);
            result.append("\r\n");
         }
      }

      return result.toString();
   }

   public static class DFAState<T> {
      private Map<Integer, DFA.DFAState<T>> transitions;
      private T finalCode = (T)null;

      public DFAState() {
         this.transitions = new HashMap<>();
      }

      public void addTransition(int label, DFA.DFAState<T> next) {
         this.transitions.put(label, next);
      }

      public T getFinal() {
         return this.finalCode;
      }

      public void setFinal(T finalCode) {
         this.finalCode = finalCode;
      }
   }
}
