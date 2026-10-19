package toni.lib.animation;

import net.minecraft.network.FriendlyByteBuf;
import toni.lib.utils.ColorUtils;

public class AnimationKeyframe {
   public float rotX;
   public float rotY;
   public float rotZ;
   public float posX;
   public float posY;
   public float posZ;
   public float size = 1.0F;
   public float alpha = 1.0F;
   public float color = ColorUtils.color(255, 255, 255, 255);

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeFloat(this.rotX);
      buffer.writeFloat(this.rotY);
      buffer.writeFloat(this.rotZ);
      buffer.writeFloat(this.posX);
      buffer.writeFloat(this.posY);
      buffer.writeFloat(this.posZ);
      buffer.writeFloat(this.size);
      buffer.writeFloat(this.alpha);
      buffer.writeFloat(this.color);
   }

   public static AnimationKeyframe decode(FriendlyByteBuf buffer) {
      AnimationKeyframe keyframe = new AnimationKeyframe();
      keyframe.rotX = buffer.readFloat();
      keyframe.rotY = buffer.readFloat();
      keyframe.rotZ = buffer.readFloat();
      keyframe.posX = buffer.readFloat();
      keyframe.posY = buffer.readFloat();
      keyframe.posZ = buffer.readFloat();
      keyframe.size = buffer.readFloat();
      keyframe.alpha = buffer.readFloat();
      keyframe.color = buffer.readFloat();
      return keyframe;
   }

   public void setValue(Binding key, float value) {
      switch (key) {
         case xRot:
            this.rotX = value;
            break;
         case yRot:
            this.rotY = value;
            break;
         case zRot:
            this.rotZ = value;
            break;
         case xPos:
            this.posX = value;
            break;
         case yPos:
            this.posY = value;
            break;
         case zPos:
            this.posZ = value;
            break;
         case Size:
            this.size = value;
            break;
         case Alpha:
            this.alpha = value;
            break;
         case Color:
            this.color = value;
      }
   }

   public float getValue(Binding key) {
      return switch (key) {
         case xRot -> this.rotX;
         case yRot -> this.rotY;
         case zRot -> this.rotZ;
         case xPos -> this.posX;
         case yPos -> this.posY;
         case zPos -> this.posZ;
         case Size -> this.size;
         case Alpha -> this.alpha;
         case Color -> this.color;
      };
   }
}
