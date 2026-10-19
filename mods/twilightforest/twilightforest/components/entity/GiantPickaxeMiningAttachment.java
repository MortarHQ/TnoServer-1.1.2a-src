package twilightforest.components.entity;

public class GiantPickaxeMiningAttachment {
   private long mining;
   private boolean breaking;
   private int giantBlockConversion;

   public void setMining(long mining) {
      this.mining = mining;
   }

   public long getMining() {
      return this.mining;
   }

   public void setBreaking(boolean breaking) {
      this.breaking = breaking;
   }

   public boolean getBreaking() {
      return this.breaking;
   }

   public void setGiantBlockConversion(int giantBlockConversion) {
      this.giantBlockConversion = giantBlockConversion;
   }

   public int getGiantBlockConversion() {
      return this.giantBlockConversion;
   }

   public boolean canMakeGiantBlock() {
      return this.giantBlockConversion > 0;
   }
}
