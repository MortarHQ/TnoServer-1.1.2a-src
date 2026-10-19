package com.finderfeed.fdlib.systems.entity.action_chain;

public class AttackDefinition {
   private String name;
   private AttackOptions attack;

   protected AttackDefinition(String name) {
      this.name = name;
   }

   protected AttackDefinition(AttackOptions attack) {
      this.attack = attack;
   }

   public String getExecutorName() {
      return this.name;
   }

   public AttackOptions getOptions() {
      return this.attack;
   }
}
