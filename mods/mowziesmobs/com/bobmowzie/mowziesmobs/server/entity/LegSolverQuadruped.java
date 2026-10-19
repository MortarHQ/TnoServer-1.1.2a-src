package com.bobmowzie.mowziesmobs.server.entity;

public final class LegSolverQuadruped extends LegSolver {
   public final LegSolver.Leg backLeft = this.legs[0];
   public final LegSolver.Leg backRight = this.legs[1];
   public final LegSolver.Leg frontLeft = this.legs[2];
   public final LegSolver.Leg frontRight = this.legs[3];

   public LegSolverQuadruped(float frontZOffset, float frontXOffset, float backZOffset, float backXOffset) {
      super(
         new LegSolver.Leg(backZOffset, backXOffset),
         new LegSolver.Leg(backZOffset, -backXOffset),
         new LegSolver.Leg(frontZOffset, frontXOffset),
         new LegSolver.Leg(frontZOffset, -frontXOffset)
      );
   }
}
