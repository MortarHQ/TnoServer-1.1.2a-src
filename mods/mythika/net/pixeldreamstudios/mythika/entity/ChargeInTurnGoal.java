package net.pixeldreamstudios.mythika.entity;

public class ChargeInTurnGoal extends TimedMeleeAttackGoal<SatyrEntity> {
   public static final double PACE = 1.4;
   private final SatyrEntity satyr;

   public ChargeInTurnGoal(SatyrEntity satyr) {
      super(satyr, 1.4);
      this.satyr = satyr;
   }

   public boolean canUse() {
      return this.satyr.isChampion() && super.canUse();
   }

   public boolean canContinueToUse() {
      return this.satyr.isChampion() && super.canContinueToUse();
   }
}
