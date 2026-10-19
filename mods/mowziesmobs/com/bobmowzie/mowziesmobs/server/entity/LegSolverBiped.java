package com.bobmowzie.mowziesmobs.server.entity;

public final class LegSolverBiped extends LegSolver {
   public final LegSolver.Leg left = this.legs[0];
   public final LegSolver.Leg right = this.legs[1];

   public LegSolverBiped(float forward, float side) {
      super(new LegSolver.Leg(forward, side), new LegSolver.Leg(forward, -side));
   }
}
