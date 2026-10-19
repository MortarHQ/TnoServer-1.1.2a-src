package org.openzen.zenscript.lexer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class NFA<T extends Comparable<T>> {
   public static final int EPSILON = -2147483647;
   public static final int UNICODE_PLACEHOLDER = 128;
   private final NFA.NFAState<T> initial;
   private final Class<T> tokenClass;
   private HashMap<NFA<T>.NodeSet, DFA.DFAState<T>> converted;

   public NFA(List<String> regexp, List<T> finals, Class<T> tokenClass) {
      this.tokenClass = tokenClass;
      this.initial = new NFA.NFAState<>();

      for (int i = 0; i < regexp.size(); i++) {
         if (regexp.get(i) != null) {
            NFA.Partial<T> partial = this.processRegExp(new CharStream(regexp.get(i)));
            partial.head.setFinal(finals.get(i));
            this.initial.addTransition(partial.tailLabel, partial.tail);
         }
      }
   }

   public DFA<T> toDFA() {
      this.converted = new HashMap<>();
      HashSet<NFA.NFAState<T>> closure = new HashSet<>();
      this.initial.closure(closure);
      DFA.DFAState<T> init = this.convert(new NFA.NodeSet(closure));
      return new DFA<>(this.tokenClass, init);
   }

   public CompiledDFA<T> compile() {
      return this.toDFA().optimize().compile();
   }

   private DFA.DFAState<T> convert(NFA<T>.NodeSet nodes) {
      if (this.converted.containsKey(nodes)) {
         return this.converted.get(nodes);
      }

      DFA.DFAState<T> node = new DFA.DFAState<>();
      this.converted.put(nodes, node);
      HashSet<Integer> edgeSet = new HashSet<>();

      for (NFA.NFAState<T> n : nodes.nodes) {
         n.alphabet(edgeSet);
      }

      for (int i : edgeSet) {
         HashSet<NFA.NFAState<T>> edge = new HashSet<>();

         for (NFA.NFAState<T> n : nodes.nodes) {
            n.edge(i, edge);
         }

         NFA<T>.NodeSet set = new NFA.NodeSet(edge);
         node.addTransition(i, this.convert(set));
      }

      T finalCode = null;

      for (NFA.NFAState<T> n : nodes.nodes) {
         if (n.state != null) {
            if (finalCode != null) {
               finalCode = n.state.compareTo(finalCode) < 0 ? n.state : finalCode;
            } else {
               finalCode = n.state;
            }
         }
      }

      node.setFinal(finalCode);
      return node;
   }

   private NFA.Partial<T> processRegExp(CharStream stream) {
      NFA.Partial<T> partial = this.processRegExp0(stream);
      if (!stream.optional('|')) {
         return partial;
      }

      ArrayList<NFA.Partial<T>> partials = new ArrayList<>();
      partials.add(partial);
      partials.add(this.processRegExp0(stream));

      while (stream.optional('|')) {
         partials.add(this.processRegExp0(stream));
      }

      NFA.NFAState<T> head = new NFA.NFAState<>();
      NFA.NFAState<T> tail = new NFA.NFAState<>();

      for (NFA.Partial<T> p : partials) {
         tail.addTransition(p.tailLabel, p.tail);
         p.head.addTransition(-2147483647, head);
      }

      return new NFA.Partial<>(-2147483647, tail, head);
   }

   private NFA.Partial<T> processRegExp0(CharStream stream) {
      NFA.Partial<T> partial = this.processRegExp1(stream);

      while (!stream.peek(')') && !stream.peek('|') && stream.hasMore()) {
         NFA.Partial<T> partial2 = this.processRegExp1(stream);
         partial.head.addTransition(partial2.tailLabel, partial2.tail);
         partial = new NFA.Partial<>(partial.tailLabel, partial.tail, partial2.head);
      }

      return partial;
   }

   private NFA.Partial<T> processRegExp1(CharStream stream) {
      NFA.Partial<T> partial = this.processPartial(stream);
      if (stream.optional('*')) {
         NFA.NFAState<T> node = new NFA.NFAState<>();
         partial.head.addTransition(-2147483647, node);
         node.addTransition(partial.tailLabel, partial.tail);
         return new NFA.Partial<>(-2147483647, node, node);
      }

      if (stream.optional('+')) {
         NFA.NFAState<T> node = new NFA.NFAState<>();
         partial.head.addTransition(-2147483647, node);
         node.addTransition(partial.tailLabel, partial.tail);
         return new NFA.Partial<>(partial.tailLabel, partial.tail, node);
      }

      if (stream.optional('?')) {
         NFA.NFAState<T> node = new NFA.NFAState<>();
         node.addTransition(-2147483647, partial.head);
         node.addTransition(partial.tailLabel, partial.tail);
         return new NFA.Partial<>(-2147483647, node, partial.head);
      }

      if (!stream.optional('{')) {
         return partial;
      }

      int amount = this.processInt(stream);
      if (amount < 0) {
         throw new IllegalArgumentException("Repitition count expected");
      }

      if (stream.optional(',')) {
         int amount2 = this.processInt(stream);
         stream.required('}');
         if (amount2 < 0) {
            NFA.Partial<T>[] duplicates = new NFA.Partial[amount];

            for (int i = 0; i < duplicates.length - 1; i++) {
               duplicates[i] = partial.duplicate();
            }

            duplicates[amount - 1] = partial;

            for (int i = 0; i < duplicates.length - 1; i++) {
               duplicates[i].head.addTransition(duplicates[i + 1].tailLabel, duplicates[i + 1].tail);
            }

            duplicates[amount - 1].head.addTransition(duplicates[amount - 1].tailLabel, duplicates[amount - 1].tail);
            return new NFA.Partial<>(duplicates[0].tailLabel, duplicates[0].tail, duplicates[amount - 1].head);
         } else {
            NFA.Partial<T>[] duplicates = new NFA.Partial[amount2];

            for (int i = 0; i < duplicates.length - 1; i++) {
               duplicates[i] = partial.duplicate();
            }

            duplicates[amount2 - 1] = partial;

            for (int i = 0; i < duplicates.length - 1; i++) {
               duplicates[i].head.addTransition(duplicates[i + 1].tailLabel, duplicates[i + 1].tail);
            }

            for (int i = amount; i < amount2; i++) {
               if (i == 0) {
                  NFA.NFAState<T> additional = new NFA.NFAState<>();
                  additional.addTransition(duplicates[0].tailLabel, duplicates[0].tail);
                  additional.addTransition(-2147483647, duplicates[amount2 - 1].head);
                  duplicates[0].tailLabel = -2147483647;
                  duplicates[0].tail = additional;
               } else {
                  duplicates[i - 1].head.addTransition(-2147483647, duplicates[amount2 - 1].head);
               }
            }

            return new NFA.Partial<>(duplicates[0].tailLabel, duplicates[0].tail, duplicates[amount2 - 1].head);
         }
      } else {
         stream.required('}');
         NFA.Partial<T>[] duplicates = new NFA.Partial[amount];

         for (int i = 0; i < duplicates.length - 1; i++) {
            duplicates[i] = partial.duplicate();
         }

         duplicates[amount - 1] = partial;

         for (int i = 0; i < duplicates.length - 1; i++) {
            duplicates[i].head.addTransition(duplicates[i + 1].tailLabel, duplicates[i + 1].tail);
         }

         return new NFA.Partial<>(duplicates[0].tailLabel, duplicates[0].tail, duplicates[amount - 1].head);
      }
   }

   private NFA.Partial<T> processPartial(CharStream stream) {
      if (stream.optional('(')) {
         NFA.Partial<T> result = this.processRegExp(stream);
         stream.required(')');
         return result;
      }

      if (stream.optional('[')) {
         NFA.NFAState<T> head = new NFA.NFAState<>();
         NFA.NFAState<T> tail = new NFA.NFAState<>();
         Iterator<Integer> iter = this.processCharList(stream).iterator();

         while (iter.hasNext()) {
            tail.addTransition(iter.next(), head);
         }

         stream.required(']');
         return new NFA.Partial<>(-2147483647, tail, head);
      } else {
         if (!stream.optional('.')) {
            NFA.NFAState<T> node = new NFA.NFAState<>();
            return new NFA.Partial<>(this.processChar(stream), node, node);
         }

         NFA.NFAState<T> head = new NFA.NFAState<>();
         NFA.NFAState<T> tail = new NFA.NFAState<>();

         for (int i = 0; i <= 256; i++) {
            tail.addTransition(i, head);
         }

         return new NFA.Partial<>(-2147483647, tail, head);
      }
   }

   private Set<Integer> processCharList(CharStream stream) {
      boolean invert = stream.optional('^');
      Set<Integer> base = new HashSet<>();

      do {
         this.processCharPartial(base, stream);
      } while (!stream.peek(']'));

      if (invert) {
         Set<Integer> result = new HashSet<>();

         for (int i = 0; i <= 128; i++) {
            if (!base.contains(i)) {
               result.add(i);
            }
         }

         return result;
      } else {
         return base;
      }
   }

   private void processCharPartial(Set<Integer> out, CharStream stream) {
      if (stream.optional('.')) {
         for (int i = 0; i <= 128; i++) {
            out.add(i);
         }
      } else {
         int from = this.processChar(stream);
         if (stream.optional('-')) {
            int to = this.processChar(stream);

            for (int i = from; i <= to; i++) {
               out.add(i);
            }
         } else {
            out.add(from);
         }
      }
   }

   private int processChar(CharStream stream) {
      if (stream.optional('\\')) {
         int c = stream.next();
         switch (c) {
            case 40:
               return 40;
            case 41:
               return 41;
            case 42:
               return 42;
            case 43:
               return 43;
            case 45:
               return 45;
            case 46:
               return 46;
            case 63:
               return 63;
            case 91:
               return 91;
            case 92:
               return 92;
            case 93:
               return 93;
            case 94:
               return 94;
            case 101:
               return -1;
            case 110:
               return 10;
            case 114:
               return 13;
            case 116:
               return 9;
            case 123:
               return 123;
            case 124:
               return 124;
            case 125:
               return 125;
            case 126:
               return 126;
            default:
               throw new IllegalArgumentException("Invalid character: " + (char)c);
         }
      } else {
         int c = stream.next();
         switch (c) {
            case 40:
            case 41:
            case 42:
            case 46:
            case 63:
            case 91:
            case 93:
            case 123:
            case 125:
               throw new IllegalArgumentException("Invalid character: " + (char)c);
            default:
               return c;
         }
      }
   }

   private int processInt(CharStream stream) {
      int data = stream.optional('0', '9') - '0';
      if (data < 0) {
         return -1;
      }

      for (char ch = stream.optional('0', '9'); ch != 0; ch = stream.optional('0', '9')) {
         data = data * 10 + (ch - '0');
      }

      return data;
   }

   public static class NFAState<T> {
      private static int counter = 1;
      private ArrayList<NFA.NFAState<T>.Transition> transitions = new ArrayList<>();
      private ArrayList<NFA.NFAState<T>> closure;
      private int index = counter++;
      private T state;

      public void addTransition(int label, NFA.NFAState<T> next) {
         this.transitions.add(new NFA.NFAState.Transition(label, next));
      }

      public void setFinal(T finalCode) {
         if (this.state != finalCode) {
            this.state = finalCode;

            for (NFA.NFAState<T>.Transition t : this.transitions) {
               if (t.label == -2147483647) {
                  t.next.setFinal(finalCode);
               }
            }
         }
      }

      private void closure(HashSet<NFA.NFAState<T>> output) {
         for (NFA.NFAState<T> node : this.closure()) {
            output.add(node);
         }
      }

      private ArrayList<NFA.NFAState<T>> closure() {
         if (this.closure == null) {
            this.closure = new ArrayList<>();
            HashSet<NFA.NFAState<T>> tmp = new HashSet<>();
            tmp.add(this);

            for (NFA.NFAState<T>.Transition transition : this.transitions) {
               if (transition.label == -2147483647 && !tmp.contains(transition.next)) {
                  tmp.add(transition.next);
                  transition.next.closure(tmp);
               }
            }

            for (NFA.NFAState<T> node : tmp) {
               this.closure.add(node);
            }
         }

         return this.closure;
      }

      private void edge(int label, HashSet<NFA.NFAState<T>> output) {
         for (NFA.NFAState<T>.Transition transition : this.transitions) {
            if (transition.label == label && !output.contains(transition.next)) {
               output.add(transition.next);
               transition.next.closure(output);
            }
         }
      }

      private void alphabet(HashSet<Integer> output) {
         for (NFA.NFAState<T> node : this.closure()) {
            for (NFA.NFAState<T>.Transition t : node.transitions) {
               if (t.label != -2147483647) {
                  output.add(t.label);
               }
            }
         }
      }

      private class Transition {
         private int label;
         private NFA.NFAState<T> next;

         public Transition(int input, NFA.NFAState<T> next) {
            this.label = input;
            this.next = next;
         }
      }
   }

   private class NodeSet {
      private NFA.NFAState<T>[] nodes;

      public NodeSet(HashSet<NFA.NFAState<T>> nodes) {
         this.nodes = new NFA.NFAState[nodes.size()];
         int i = 0;

         for (NFA.NFAState<T> node : nodes) {
            this.nodes[i++] = node;
         }

         Arrays.sort(this.nodes, (o1, o2) -> o1.index - o2.index);
      }

      @Override
      public int hashCode() {
         return Arrays.hashCode(this.nodes);
      }

      @Override
      public boolean equals(Object other) {
         return other.getClass() != NFA.NodeSet.class ? false : Arrays.equals(this.nodes, ((NFA.NodeSet)other).nodes);
      }
   }

   private static class Partial<T> {
      private int tailLabel;
      private NFA.NFAState<T> tail;
      private NFA.NFAState<T> head;

      public Partial(int tailLabel, NFA.NFAState<T> tail, NFA.NFAState<T> head) {
         this.tailLabel = tailLabel;
         this.tail = tail;
         this.head = head;
      }

      public NFA.Partial<T> duplicate() {
         HashMap<NFA.NFAState<T>, NFA.NFAState<T>> nodes = new HashMap<>();
         Queue<NFA.NFAState<T>> todo = new LinkedList<>();
         todo.add(this.tail);
         nodes.put(this.tail, new NFA.NFAState<>());

         while (!todo.isEmpty()) {
            NFA.NFAState<T> node = todo.poll();

            for (NFA.NFAState<T>.Transition t : node.transitions) {
               if (!nodes.containsKey(t.next)) {
                  nodes.put(t.next, new NFA.NFAState<>());
                  todo.add(t.next);
               }

               nodes.get(node).addTransition(t.label, nodes.get(t.next));
            }
         }

         return new NFA.Partial<>(this.tailLabel, nodes.get(this.tail), nodes.get(this.head));
      }
   }
}
