package com.finderfeed.fdlib.systems.entity.action_chain;

public class AttackInstance {
   public AttackExecutor attack;
   protected String name;
   public int tick;
   public int stage;

   public AttackInstance(String name, AttackExecutor executor) {
      this.attack = executor;
      this.name = name;
   }

   public void nextStage() {
      this.tick = 0;
      this.stage++;
   }
}
