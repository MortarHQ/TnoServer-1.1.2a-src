package com.natamus.collective_common_neoforge.implementations.event;

import com.google.common.annotations.VisibleForTesting;
import com.natamus.collective_common_neoforge.data.Constants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class PhaseSorting {
   @VisibleForTesting
   public static boolean ENABLE_CYCLE_WARNING = true;

   static <T> void sortPhases(List<EventPhaseData<T>> sortedPhases) {
      List<EventPhaseData<T>> toposort = new ArrayList<>(sortedPhases.size());

      for (EventPhaseData<T> phase : sortedPhases) {
         forwardVisit(phase, null, toposort);
      }

      clearStatus(toposort);
      Collections.reverse(toposort);
      Map<EventPhaseData<T>, PhaseSorting.PhaseScc<T>> phaseToScc = new IdentityHashMap<>();

      for (EventPhaseData<T> phase : toposort) {
         if (phase.visitStatus == 0) {
            List<EventPhaseData<T>> sccPhases = new ArrayList<>();
            backwardVisit(phase, sccPhases);
            sccPhases.sort(Comparator.comparing(p -> p.id));
            PhaseSorting.PhaseScc<T> scc = new PhaseSorting.PhaseScc<>(sccPhases);

            for (EventPhaseData<T> phaseInScc : sccPhases) {
               phaseToScc.put(phaseInScc, scc);
            }
         }
      }

      clearStatus(toposort);

      for (PhaseSorting.PhaseScc<T> scc : phaseToScc.values()) {
         for (EventPhaseData<T> phase : scc.phases) {
            for (EventPhaseData<T> subsequentPhase : phase.subsequentPhases) {
               PhaseSorting.PhaseScc<T> subsequentScc = phaseToScc.get(subsequentPhase);
               if (subsequentScc != scc) {
                  scc.subsequentSccs.add(subsequentScc);
                  subsequentScc.inDegree++;
               }
            }
         }
      }

      PriorityQueue<PhaseSorting.PhaseScc<T>> pq = new PriorityQueue<>(Comparator.comparing(sccx -> sccx.phases.getFirst().id));
      sortedPhases.clear();

      for (PhaseSorting.PhaseScc<T> scc : phaseToScc.values()) {
         if (scc.inDegree == 0) {
            pq.add(scc);
            scc.inDegree = -1;
         }
      }

      while (!pq.isEmpty()) {
         PhaseSorting.PhaseScc<T> scc = pq.poll();
         sortedPhases.addAll(scc.phases);

         for (PhaseSorting.PhaseScc<T> subsequentScc : scc.subsequentSccs) {
            subsequentScc.inDegree--;
            if (subsequentScc.inDegree == 0) {
               pq.add(subsequentScc);
            }
         }
      }
   }

   private static <T> void forwardVisit(EventPhaseData<T> phase, EventPhaseData<T> parent, List<EventPhaseData<T>> toposort) {
      if (phase.visitStatus == 0) {
         phase.visitStatus = 1;

         for (EventPhaseData<T> data : phase.subsequentPhases) {
            forwardVisit(data, phase, toposort);
         }

         toposort.add(phase);
         phase.visitStatus = 2;
      } else if (phase.visitStatus == 1 && ENABLE_CYCLE_WARNING) {
         Constants.LOG
            .warn(
               String.format("Event phase ordering conflict detected.%nEvent phase %s is ordered both before and after event phase %s.", phase.id, parent.id)
            );
      }
   }

   private static <T> void clearStatus(List<EventPhaseData<T>> phases) {
      for (EventPhaseData<T> phase : phases) {
         phase.visitStatus = 0;
      }
   }

   private static <T> void backwardVisit(EventPhaseData<T> phase, List<EventPhaseData<T>> sccPhases) {
      if (phase.visitStatus == 0) {
         phase.visitStatus = 1;
         sccPhases.add(phase);

         for (EventPhaseData<T> data : phase.previousPhases) {
            backwardVisit(data, sccPhases);
         }
      }
   }

   private static class PhaseScc<T> {
      final List<EventPhaseData<T>> phases;
      final List<PhaseSorting.PhaseScc<T>> subsequentSccs = new ArrayList<>();
      int inDegree = 0;

      private PhaseScc(List<EventPhaseData<T>> phases) {
         this.phases = phases;
      }
   }
}
