package team.lodestar.lodestone.systems.model.obj.lod;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.client.GraphicsStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public abstract class LODStrategy<T> implements LODBuilder<T> {
   public List<LevelOfDetail<T>> levelsOfDetail = new ArrayList<>();

   public LODStrategy(Consumer<LODBuilder<T>> lodBuilderSetup) {
      lodBuilderSetup.accept(this);
   }

   public abstract LevelOfDetail<T> getLODLevel(PoseStack var1);

   public LevelOfDetail<T> getDefaultLOD() {
      return this.levelsOfDetail.get(this.levelsOfDetail.size() - 1);
   }

   public static LODStrategy<Float> Distance(Consumer<LODBuilder<Float>> lodBuilder) {
      return new LODStrategy.DistanceLODStrategy(lodBuilder);
   }

   public static LODStrategy<GraphicsStatus> Graphics(Consumer<LODBuilder<GraphicsStatus>> lodBuilder) {
      return new LODStrategy.GraphicsSettingsLODStrategy(lodBuilder);
   }

   private static class DistanceLODStrategy extends LODStrategy<Float> {
      public DistanceLODStrategy(Consumer<LODBuilder<Float>> lodBuilder) {
         super(lodBuilder);
      }

      @Override
      public LevelOfDetail<Float> getLODLevel(PoseStack poseStack) {
         Vector3f cameraPosition = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition().toVector3f();
         float distanceSq = cameraPosition.distanceSquared(this.getPositionFromPose(poseStack));

         for (LevelOfDetail<Float> levelOfDetail : this.levelsOfDetail) {
            if (distanceSq <= levelOfDetail.getArgument()) {
               return levelOfDetail;
            }
         }

         return this.getDefaultLOD();
      }

      public void create(Float argument, ResourceLocation modelLocation) {
         this.levelsOfDetail.add(new LevelOfDetail<>(modelLocation, argument * argument));
      }

      private Vector3f getPositionFromPose(PoseStack poseStack) {
         Matrix4f pose = poseStack.last().pose();
         return new Vector3f(pose.m30(), pose.m31(), pose.m32());
      }
   }

   private static class GraphicsSettingsLODStrategy extends LODStrategy<GraphicsStatus> {
      public GraphicsSettingsLODStrategy(Consumer<LODBuilder<GraphicsStatus>> lodBuilder) {
         super(lodBuilder);
      }

      @Override
      public LevelOfDetail<GraphicsStatus> getLODLevel(PoseStack poseStack) {
         GraphicsStatus graphicsQuality = (GraphicsStatus)Minecraft.getInstance().options.graphicsMode().get();

         for (LevelOfDetail<GraphicsStatus> levelOfDetail : this.levelsOfDetail) {
            if (graphicsQuality == levelOfDetail.getArgument()) {
               return levelOfDetail;
            }
         }

         return this.getDefaultLOD();
      }

      public void create(GraphicsStatus argument, ResourceLocation modelLocation) {
         if (this.checkForDuplicates(argument)) {
            throw new IllegalArgumentException("Duplicate graphics status found: " + argument);
         }

         this.levelsOfDetail.add(new LevelOfDetail<>(modelLocation, argument));
      }

      private boolean checkForDuplicates(GraphicsStatus argument) {
         for (LevelOfDetail<GraphicsStatus> levelOfDetail : this.levelsOfDetail) {
            if (levelOfDetail.getArgument().equals(argument)) {
               return true;
            }
         }

         return false;
      }
   }
}
