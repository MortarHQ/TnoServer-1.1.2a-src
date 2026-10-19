package it.hurts.sskirillss.relics.utils.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class WorldPosition {
   private ResourceKey<Level> level;
   private Vec3 pos;
   public static final Codec<WorldPosition> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
            ResourceKey.codec(Registries.DIMENSION).fieldOf("level").forGetter(WorldPosition::getLevel),
            Vec3.CODEC.fieldOf("pos").forGetter(WorldPosition::getPos)
         )
         .apply(instance, WorldPosition::new)
   );

   public WorldPosition(Entity entity) {
      this.level = entity.level().dimension();
      this.pos = entity.position();
   }

   public ResourceKey<Level> getLevel() {
      return this.level;
   }

   public Vec3 getPos() {
      return this.pos;
   }

   public void setLevel(ResourceKey<Level> level) {
      this.level = level;
   }

   public void setPos(Vec3 pos) {
      this.pos = pos;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof WorldPosition other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$level = this.getLevel();
         Object other$level = other.getLevel();
         if (this$level == null ? other$level == null : this$level.equals(other$level)) {
            Object this$pos = this.getPos();
            Object other$pos = other.getPos();
            return this$pos == null ? other$pos == null : this$pos.equals(other$pos);
         } else {
            return false;
         }
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof WorldPosition;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $level = this.getLevel();
      result = result * 59 + ($level == null ? 43 : $level.hashCode());
      Object $pos = this.getPos();
      return result * 59 + ($pos == null ? 43 : $pos.hashCode());
   }

   @Override
   public String toString() {
      return "WorldPosition(level=" + this.getLevel() + ", pos=" + this.getPos() + ")";
   }

   public WorldPosition(ResourceKey<Level> level, Vec3 pos) {
      this.level = level;
      this.pos = pos;
   }
}
