package net.pixeldreamstudios.mythika.entity;

public interface TimedAttacker {
   double IMPACT_GRACE = 0.5;

   boolean canStartAttack();

   void startAttack();

   boolean isAttackLocked();
}
