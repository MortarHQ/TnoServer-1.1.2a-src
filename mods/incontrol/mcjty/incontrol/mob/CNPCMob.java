package mcjty.incontrol.mob;

import java.util.Objects;
import mcjty.incontrol.compat.CustomNPCSupport;
import mcjty.incontrol.setup.ModSetup;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

public class CNPCMob extends DefaultMob {
   private int cloneTab;
   private String cloneName;
   private Entity cached;

   public CNPCMob(int cloneTab, String cloneName) {
      this.cloneName = cloneName;
      this.cloneTab = cloneTab;
   }

   public int getCloneTab() {
      return this.cloneTab;
   }

   public void setCloneTab(int cloneTab) {
      this.cloneTab = cloneTab;
   }

   public String getCloneName() {
      return this.cloneName;
   }

   public void setCloneName(String cloneName) {
      this.cloneName = cloneName;
   }

   @Override
   public EntityType<?> getType() {
      return !ModSetup.customnpcs ? null : CustomNPCSupport.getNPCEntityType();
   }

   public boolean matches(Entity entity) {
      if (entity == null) {
         return false;
      } else {
         return entity.getPersistentData().contains("InControlNatSpawnTab") && entity.getPersistentData().contains("InControlNatSpawnName")
            ? entity.getPersistentData().getInt("InControlNatSpawnTab") == this.cloneTab
               && entity.getPersistentData().getString("InControlNatSpawnName").equals(this.cloneName)
            : false;
      }
   }

   @Override
   public Entity getEntity(ServerLevel level) {
      if (!ModSetup.customnpcs) {
         return null;
      }

      if (this.cached == null || this.cached.isAddedToLevel() || this.cached.isRemoved()) {
         this.cached = CustomNPCSupport.getNpcEntity(level, this.cloneTab, this.cloneName);
      }

      return this.cached;
   }

   @Override
   public boolean equals(Object object) {
      return !(object instanceof CNPCMob cnpcMob) ? false : this.cloneTab == cnpcMob.cloneTab && Objects.equals(this.cloneName, cnpcMob.cloneName);
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.cloneTab, this.cloneName);
   }
}
