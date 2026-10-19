package com.blamejared.crafttweaker.api.util.sequence;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SequenceManager {
   private static final Map<SequenceType, List<Sequence<?, ?>>> sequences = new HashMap<>();
   private static final Map<SequenceType, List<Sequence<?, ?>>> sequencesView = Collections.unmodifiableMap(sequences);

   public static void addSequence(SequenceType type, Sequence<?, ?> sequence) {
      sequences.computeIfAbsent(type, sequenceType -> new LinkedList<>()).add(sequence);
   }

   public static void removeSequence(SequenceType type, Sequence<?, ?> sequence) {
      sequences.getOrDefault(type, Collections.emptyList()).remove(sequence);
   }

   public static void tick(SequenceType type) {
      if (sequences.containsKey(type)) {
         Iterator<Sequence<?, ?>> tasks = sequences.get(type).iterator();

         while (tasks.hasNext()) {
            Sequence<?, ?> sequence = tasks.next();
            sequence.tick();
            if (sequence.isComplete() || sequence.isStopped()) {
               tasks.remove();
            }
         }
      }
   }

   public static Map<SequenceType, List<Sequence<?, ?>>> getSequences() {
      return sequencesView;
   }

   public static void clearSequences() {
      sequences.clear();
   }

   public static void clearSequences(SequenceType type) {
      sequences.getOrDefault(type, Collections.emptyList()).clear();
   }
}
