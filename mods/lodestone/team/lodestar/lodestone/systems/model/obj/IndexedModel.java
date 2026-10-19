package team.lodestar.lodestone.systems.model.obj;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.MeshData;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import team.lodestar.lodestone.systems.model.IRenderableModel;
import team.lodestar.lodestone.systems.model.obj.data.IndexedMesh;
import team.lodestar.lodestone.systems.model.obj.data.Vertex;
import team.lodestar.lodestone.systems.model.obj.modifier.ModelModifier;

public abstract class IndexedModel implements IRenderableModel {
   protected List<Vertex> vertices;
   protected List<IndexedMesh> meshes;
   protected List<Integer> bakedIndices;
   public List<ModelModifier> earlyModifiers;
   protected List<ModelModifier> modifiers;
   protected ResourceLocation modelId;
   protected VertexBuffer modelBuffer;
   protected MeshData meshData;

   public IndexedModel(ResourceLocation modelId) {
      this.modelId = modelId;
      this.vertices = new ArrayList<>();
      this.meshes = new ArrayList<>();
      this.bakedIndices = new ArrayList<>();
   }

   public void applyModifiers() {
      if (this.modifiers != null) {
         this.modifiers.forEach(modifier -> modifier.apply(this));
      }
   }

   public List<Vertex> getVertices() {
      return this.vertices;
   }

   public List<IndexedMesh> getMeshes() {
      return this.meshes;
   }

   public void setMeshes(List<IndexedMesh> meshes) {
      this.meshes = meshes;
   }

   public List<Integer> getBakedIndices() {
      return this.bakedIndices;
   }

   public void bakeIndices(Mode mode, boolean triangulate) {
      this.bakedIndices.clear();
      this.meshes.stream().filter(meshx -> meshx.indices.size() == mode.primitiveLength).forEach(meshx -> this.bakedIndices.addAll(meshx.indices));

      for (IndexedMesh mesh : this.meshes) {
         if (mesh.indices.size() != mode.primitiveLength) {
            if (mesh.indices.size() == 4 && triangulate) {
               this.bakedIndices.add(mesh.indices.get(0));
               this.bakedIndices.add(mesh.indices.get(1));
               this.bakedIndices.add(mesh.indices.get(2));
               this.bakedIndices.add(mesh.indices.get(2));
               this.bakedIndices.add(mesh.indices.get(3));
               this.bakedIndices.add(mesh.indices.get(0));
            } else {
               this.bakedIndices.addAll(mesh.indices);
            }
         } else {
            this.bakedIndices.addAll(mesh.indices);
         }
      }
   }

   @Override
   public ResourceLocation getModelLocation() {
      return this.modelId;
   }

   @Override
   public void render(PoseStack poseStack, VertexConsumer vertexConsumer, RenderType renderType) {
      for (IndexedMesh mesh : this.meshes) {
         if (mesh.isCompatibleWith(renderType.mode())) {
            for (Vertex vertex : mesh.getVertices(this)) {
               vertex.supplyVertexData(vertexConsumer, renderType.format(), poseStack);
            }
         }
      }
   }

   @Override
   public MeshData createMesh(PoseStack poseStack, VertexFormat vertexFormat, Mode mode) {
      BufferBuilder bufferBuilder = Tesselator.getInstance().begin(mode, vertexFormat);

      for (IndexedMesh mesh : this.meshes) {
         for (Vertex vertex : mesh.getVertices(this)) {
            vertex.supplyVertexData(bufferBuilder, vertexFormat, poseStack);
         }
      }

      return bufferBuilder.buildOrThrow();
   }

   @Override
   public VertexBuffer getModelBuffer() {
      return this.modelBuffer;
   }

   @Override
   public void setModelBuffer(VertexBuffer modelBuffer) {
      this.modelBuffer = modelBuffer;
   }

   @Override
   public void cleanup() {
      if (this.modelBuffer != null) {
         this.modelBuffer.close();
      }
   }
}
