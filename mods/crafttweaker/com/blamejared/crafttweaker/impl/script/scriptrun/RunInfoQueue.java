package com.blamejared.crafttweaker.impl.script.scriptrun;

import com.blamejared.crafttweaker.api.action.base.IAction;
import com.blamejared.crafttweaker.api.action.base.IUndoableAction;
import java.util.ArrayDeque;
import java.util.Objects;
import java.util.Queue;
import java.util.function.Function;
import org.apache.logging.log4j.Logger;

final class RunInfoQueue {
   private final Queue<RunInfo> queue = new ArrayDeque<>();
   private final Runnable undoActionMessage;
   private final Function<IAction, Logger> loggerObtainingFunction;
   private RunInfo previous;
   private boolean firstRun;

   RunInfoQueue(Runnable undoActionMessage, Function<IAction, Logger> loggerObtainingFunction) {
      this.undoActionMessage = undoActionMessage;
      this.loggerObtainingFunction = loggerObtainingFunction;
      this.firstRun = true;
   }

   boolean isFirstRun() {
      return this.firstRun;
   }

   void offer(RunInfo info) {
      this.queue.offer(Objects.requireNonNull(info, "info"));
      this.previous = info;
      this.firstRun = false;
   }

   RunInfo previous() {
      return this.previous;
   }

   void undoActions() {
      if (!this.queue.isEmpty()) {
         this.undoActionMessage.run();

         while (!this.queue.isEmpty()) {
            RunInfo previous = this.queue.poll();
            previous.appliedActions()
               .stream()
               .filter(IUndoableAction.class::isInstance)
               .map(IUndoableAction.class::cast)
               .map(it -> new RunInfoQueue.UndoQueueElement(it, this.loggerObtainingFunction))
               .filter(it -> it.action().shouldApplyOn(previous.loadSource(), it.logger()))
               .peek(it -> it.logger().info(it.action().describeUndo()))
               .map(RunInfoQueue.UndoQueueElement::action)
               .forEach(IUndoableAction::undo);
         }
      }
   }

   private record UndoQueueElement(IUndoableAction action, Logger logger) {
      UndoQueueElement(IUndoableAction action, Function<IAction, Logger> loggerObtainingFunction) {
         this(action, loggerObtainingFunction.apply(action));
      }
   }
}
