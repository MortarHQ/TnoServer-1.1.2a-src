package com.github.b4ndithelps.tennogamenolife.geckolib;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.cache.object.GeoCube;
import software.bernie.geckolib.cache.object.GeoQuad;
import software.bernie.geckolib.cache.object.GeoVertex;
import software.bernie.geckolib.loading.json.raw.Bone;
import software.bernie.geckolib.loading.json.raw.Cube;
import software.bernie.geckolib.loading.json.raw.ModelProperties;
import software.bernie.geckolib.loading.json.raw.PolyMesh;
import software.bernie.geckolib.loading.object.BakedModelFactory;
import software.bernie.geckolib.loading.object.BoneStructure;
import software.bernie.geckolib.loading.object.GeometryTree;
import software.bernie.geckolib.util.RenderUtil;

public final class PolyMeshBakedModelFactory implements BakedModelFactory {
   public static final String POLY_MESH_BONE_SUFFIX = "_poly_mesh";

   public BakedGeoModel constructGeoModel(GeometryTree geometryTree) {
      List<GeoBone> bones = new ObjectArrayList();

      for (BoneStructure boneStructure : geometryTree.topLevelBones().values()) {
         bones.add(this.constructBone(boneStructure, geometryTree.properties(), null));
      }

      return new BakedGeoModel(bones, geometryTree.properties());
   }

   public GeoBone constructBone(BoneStructure boneStructure, ModelProperties properties, @Nullable GeoBone parent) {
      Bone bone = boneStructure.self();
      GeoBone bakedBone = new GeoBone(parent, bone.name(), bone.mirror(), bone.inflate(), bone.neverRender(), bone.reset());
      Vec3 rotation = RenderUtil.arrayToVec(bone.rotation());
      Vec3 pivot = RenderUtil.arrayToVec(bone.pivot());
      bakedBone.updateRotation((float)Math.toRadians(-rotation.x), (float)Math.toRadians(-rotation.y), (float)Math.toRadians(rotation.z));
      bakedBone.updatePivot((float)(-pivot.x), (float)pivot.y, (float)pivot.z);

      for (Cube cube : bone.cubes()) {
         bakedBone.getCubes().add(this.constructCube(cube, properties, bakedBone));
      }

      if (bone.polyMesh() != null) {
         GeoCube polyMesh = constructPolyMesh(bone.polyMesh(), properties);
         if (polyMesh != null) {
            GeoBone polyMeshBone = new GeoBone(bakedBone, bone.name() + "_poly_mesh", Boolean.FALSE, null, Boolean.FALSE, Boolean.FALSE);
            polyMeshBone.getCubes().add(polyMesh);
            bakedBone.getChildBones().add(polyMeshBone);
         }
      }

      for (BoneStructure child : boneStructure.children().values()) {
         bakedBone.getChildBones().add(this.constructBone(child, properties, bakedBone));
      }

      return bakedBone;
   }

   public GeoCube constructCube(Cube cube, ModelProperties properties, GeoBone bone) {
      return BakedModelFactory.DEFAULT_FACTORY.constructCube(cube, properties, bone);
   }

   @Nullable
   private static GeoCube constructPolyMesh(PolyMesh mesh, ModelProperties properties) {
      if (mesh.polysUnion() != null && mesh.polysUnion().union().length != 0) {
         double[] positions = mesh.positions();
         double[] normals = mesh.normals();
         double[] uvs = mesh.uvs();
         double[][][] polygons = mesh.polysUnion().union();
         boolean normalizedUvs = mesh.normalizedUVs() == Boolean.TRUE;
         GeoQuad[] quads = new GeoQuad[polygons.length];

         for (int polygonIndex = 0; polygonIndex < polygons.length; polygonIndex++) {
            double[][] polygon = polygons[polygonIndex];
            if (polygon.length >= 3) {
               GeoVertex[] vertices = new GeoVertex[4];
               Vector3f normal = null;

               for (int vertexIndex = 0; vertexIndex < 3; vertexIndex++) {
                  double[] indices = polygon[vertexIndex];
                  int positionIndex = (int)indices[0] * 3;
                  int normalIndex = (int)indices[1] * 3;
                  int uvIndex = (int)indices[2] * 2;
                  float x = (float)(-positions[positionIndex] / 16.0);
                  float y = (float)(positions[positionIndex + 1] / 16.0);
                  float z = (float)(positions[positionIndex + 2] / 16.0);
                  float u = (float)uvs[uvIndex];
                  float v = (float)uvs[uvIndex + 1];
                  if (!normalizedUvs) {
                     u /= (float)properties.textureWidth();
                     v /= (float)properties.textureHeight();
                  }

                  vertices[vertexIndex] = new GeoVertex(new Vector3f(x, y, z), u, v);
                  if (normal == null) {
                     normal = new Vector3f((float)(-normals[normalIndex]), (float)normals[normalIndex + 1], (float)normals[normalIndex + 2]).normalize();
                  }
               }

               vertices[3] = vertices[2];
               quads[polygonIndex] = new GeoQuad(vertices, normal, nearestDirection(normal));
            }
         }

         return new GeoCube(quads, Vec3.ZERO, Vec3.ZERO, new Vec3(1.0, 1.0, 1.0), 0.0, false);
      } else {
         return null;
      }
   }

   private static Direction nearestDirection(Vector3f normal) {
      float x = Math.abs(normal.x());
      float y = Math.abs(normal.y());
      float z = Math.abs(normal.z());
      if (x >= y && x >= z) {
         return normal.x() >= 0.0F ? Direction.EAST : Direction.WEST;
      } else if (y >= z) {
         return normal.y() >= 0.0F ? Direction.UP : Direction.DOWN;
      } else {
         return normal.z() >= 0.0F ? Direction.SOUTH : Direction.NORTH;
      }
   }
}
