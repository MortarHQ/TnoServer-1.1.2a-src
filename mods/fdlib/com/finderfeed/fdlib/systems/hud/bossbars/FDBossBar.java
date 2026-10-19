package com.finderfeed.fdlib.systems.hud.bossbars;

import com.finderfeed.fdlib.FDClientHelpers;
import java.util.UUID;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public abstract class FDBossBar {
   private UUID uuid;
   private int entityId;
   private float percentage;

   public FDBossBar(UUID uuid, int entityId) {
      this.uuid = uuid;
      this.entityId = entityId;
   }

   public abstract void render(GuiGraphics var1, float var2);

   public abstract void tick(float var1);

   public abstract float height();

   public abstract void hanldeBarEvent(int var1, int var2);

   public void setPercentage(float percentage) {
      this.percentage = percentage;
   }

   public float getPercentage() {
      return this.percentage;
   }

   public UUID getUUID() {
      return this.uuid;
   }

   public int getEntityId() {
      return this.entityId;
   }

   public <T extends Entity> T getEntity() {
      Level level = FDClientHelpers.getClientLevel();
      if (level == null) {
         return null;
      }

      Entity entity = level.getEntity(this.entityId);
      return (T)(entity == null ? null : entity);
   }
}
