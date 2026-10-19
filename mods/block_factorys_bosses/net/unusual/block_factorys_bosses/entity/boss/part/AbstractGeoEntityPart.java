package net.unusual.block_factorys_bosses.entity.boss.part;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.unusual.block_factorys_bosses.geckolib.ServerAnimationPlayer;
import org.joml.Vector3f;
import software.bernie.geckolib.animatable.GeoEntity;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class AbstractGeoEntityPart<ParentT extends Entity & AbstractEntityPartParent<ParentT, PartT>, PartT extends AbstractEntityPart<ParentT, PartT>>
   extends AbstractEntityPart<ParentT, PartT> {
   private final String anchorBoneName;
   private final String bodyBoneName;

   public AbstractGeoEntityPart(ParentT parent, int partIndex, String anchorBoneName, String bodyBoneName, float width, float height) {
      super(parent, partIndex, width, height);
      this.anchorBoneName = anchorBoneName;
      this.bodyBoneName = bodyBoneName;
   }

   public String getAnchorBoneName() {
      return this.anchorBoneName;
   }

   public String getBodyBoneName() {
      return this.bodyBoneName;
   }

   public static <ParentT extends Entity & GeoEntity & AbstractEntityPartParent<ParentT, PartT>, PartT extends AbstractGeoEntityPart<ParentT, PartT>> void positionPartsFromBones(
      PartT[] parts, ServerAnimationPlayer<ParentT> serverAnimationPlayer, @Nullable Vec3 rootOffset
   ) {
      ServerAnimationPlayer.ServerPoseStack poseStack = new ServerAnimationPlayer.ServerPoseStack();
      serverAnimationPlayer.applyWorldSpaceTransformations(poseStack);
      if (rootOffset != null) {
         poseStack.translate(rootOffset.x(), rootOffset.y(), rootOffset.z());
      }

      for (PartT part : parts) {
         part.xo = part.getX();
         part.yo = part.getY();
         part.zo = part.getZ();
         part.xOld = part.getX();
         part.yOld = part.getY();
         part.zOld = part.getZ();
         Vector3f bonePosition = serverAnimationPlayer.getBonePosition(poseStack, serverAnimationPlayer.getBone(part.getAnchorBoneName()));
         part.setPos(bonePosition.x, bonePosition.y - part.getBbHeight() * 0.5F, bonePosition.z);
      }
   }
}
