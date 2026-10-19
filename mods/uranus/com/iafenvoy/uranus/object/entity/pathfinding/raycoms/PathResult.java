package com.iafenvoy.uranus.object.entity.pathfinding.raycoms;

import com.iafenvoy.uranus.Uranus;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import net.minecraft.world.level.pathfinder.Path;

public class PathResult<T extends Callable<Path>> {
   private static boolean threadException = false;
   protected PathFindingStatus status = PathFindingStatus.IN_PROGRESS_COMPUTING;
   private volatile boolean pathReachesDestination = false;
   private Path path = null;
   private Future<Path> pathCalculation = null;
   private T job = (T)null;
   private boolean pathingDoneAndProcessed = false;

   public PathFindingStatus getStatus() {
      return this.status;
   }

   public void setStatus(PathFindingStatus s) {
      this.status = s;
   }

   public boolean isInProgress() {
      return this.isComputing() || this.status == PathFindingStatus.IN_PROGRESS_FOLLOWING;
   }

   public boolean isComputing() {
      return this.status == PathFindingStatus.IN_PROGRESS_COMPUTING;
   }

   public boolean failedToReachDestination() {
      return this.isFinished() && !this.pathReachesDestination;
   }

   public boolean isPathReachingDestination() {
      return this.isFinished() && this.path != null && this.pathReachesDestination;
   }

   public void setPathReachesDestination(boolean value) {
      this.pathReachesDestination = value;
   }

   public boolean isCancelled() {
      return this.status == PathFindingStatus.CANCELLED;
   }

   public int getPathLength() {
      return this.path.getNodeCount();
   }

   public boolean hasPath() {
      return this.path != null;
   }

   public Path getPath() {
      return this.path;
   }

   public T getJob() {
      return this.job;
   }

   public void setJob(T job) {
      this.job = job;
   }

   public void startJob(ExecutorService executorService) {
      if (this.job != null) {
         try {
            if (!threadException) {
               this.pathCalculation = executorService.submit(this.job);
            }
         } catch (NullPointerException e) {
            Uranus.LOGGER.error("Mod tried to move an entity from non server thread", e);
         } catch (RuntimeException e) {
            threadException = true;
            Uranus.LOGGER.error("Failed to start job", e);
         } catch (Exception e) {
            Uranus.LOGGER.error("Failed to start job", e);
         }
      }
   }

   public void processCalculationResults() {
      if (!this.pathingDoneAndProcessed) {
         try {
            this.path = this.pathCalculation.get();
            this.pathCalculation = null;
            this.setStatus(PathFindingStatus.CALCULATION_COMPLETE);
         } catch (InterruptedException | ExecutionException e) {
            Uranus.LOGGER.error("Failed to get result", e);
         }
      }
   }

   public boolean isCalculatingPath() {
      return this.pathCalculation != null && !this.pathCalculation.isDone();
   }

   public boolean isFinished() {
      if (!this.pathingDoneAndProcessed && this.pathCalculation != null && this.pathCalculation.isDone()) {
         this.processCalculationResults();
         this.pathingDoneAndProcessed = true;
      }

      return this.pathingDoneAndProcessed;
   }

   public void cancel() {
      if (this.pathCalculation != null) {
         this.pathCalculation.cancel(false);
         this.pathCalculation = null;
      }

      this.pathingDoneAndProcessed = true;
   }
}
