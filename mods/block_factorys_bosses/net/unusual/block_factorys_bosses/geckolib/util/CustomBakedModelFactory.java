package net.unusual.block_factorys_bosses.geckolib.util;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.phys.Vec3;
import net.unusual.block_factorys_bosses.geckolib.boss.knight.UnderworldKnightRenderer;
import net.unusual.block_factorys_bosses.geckolib.boss.kraken.KrakenTentacleRenderer;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.cache.object.GeoCube;
import software.bernie.geckolib.cache.object.GeoQuad;
import software.bernie.geckolib.loading.json.raw.Bone;
import software.bernie.geckolib.loading.json.raw.Cube;
import software.bernie.geckolib.loading.json.raw.ModelProperties;
import software.bernie.geckolib.loading.object.BakedModelFactory;
import software.bernie.geckolib.loading.object.BoneStructure;
import software.bernie.geckolib.loading.object.GeometryTree;
import software.bernie.geckolib.loading.object.BakedModelFactory.VertexSet;
import software.bernie.geckolib.util.RenderUtil;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CustomBakedModelFactory implements BakedModelFactory {
   public BakedGeoModel constructGeoModel(GeometryTree geometryTree) {
      List<GeoBone> bones = new ObjectArrayList();

      for (BoneStructure boneStructure : geometryTree.topLevelBones().values()) {
         bones.add(this.constructBone(boneStructure, geometryTree.properties(), null));
      }

      return new BakedGeoModel(bones, geometryTree.properties());
   }

   public GeoBone constructBone(BoneStructure boneStructure, ModelProperties properties, @Nullable GeoBone parent) {
      Bone bone = boneStructure.self();
      GeoBone newBone = new GeoBone(parent, bone.name(), bone.mirror(), bone.inflate(), bone.neverRender(), bone.reset());
      Vec3 rotation = RenderUtil.arrayToVec(bone.rotation());
      Vec3 pivot = RenderUtil.arrayToVec(bone.pivot());
      newBone.updateRotation((float)Math.toRadians(-rotation.x), (float)Math.toRadians(-rotation.y), (float)Math.toRadians(rotation.z));
      newBone.updatePivot((float)(-pivot.x), (float)pivot.y, (float)pivot.z);

      for (Cube cube : bone.cubes()) {
         newBone.getCubes().add(this.constructCube(cube, properties, newBone));
      }

      for (BoneStructure child : boneStructure.children().values()) {
         newBone.getChildBones().add(this.constructBone(child, properties, newBone));
      }

      if ("geometry.helvar".equals(properties.identifier()) && bone.locators() != null) {
         bone.locators().forEach((name, locatorValue) -> UnderworldKnightRenderer.BONER.put(name, new ParticleLocator(bone, locatorValue, newBone)));
      }

      if ("geometry.kraken_tentacle".equals(properties.identifier()) && bone.locators() != null) {
         bone.locators().forEach((name, locatorValue) -> KrakenTentacleRenderer.BONER.put(name, new ParticleLocator(bone, locatorValue, newBone)));
      }

      return newBone;
   }

   public GeoCube constructCube(Cube cube, ModelProperties properties, GeoBone bone) {
      boolean mirror = cube.mirror() == Boolean.TRUE;
      double inflate = cube.inflate() != null ? cube.inflate() / 16.0 : (bone.getInflate() == null ? 0.0 : bone.getInflate() / 16.0);
      Vec3 size = RenderUtil.arrayToVec(cube.size());
      Vec3 origin = RenderUtil.arrayToVec(cube.origin());
      Vec3 rotation = RenderUtil.arrayToVec(cube.rotation());
      Vec3 pivot = RenderUtil.arrayToVec(cube.pivot());
      origin = new Vec3(-(origin.x + size.x) / 16.0, origin.y / 16.0, origin.z / 16.0);
      Vec3 vertexSize = size.multiply(0.0625, 0.0625, 0.0625);
      pivot = pivot.multiply(-1.0, 1.0, 1.0);
      rotation = new Vec3(Math.toRadians(-rotation.x), Math.toRadians(-rotation.y), Math.toRadians(rotation.z));
      GeoQuad[] quads = this.buildQuads(
         cube.uv(), new VertexSet(origin, vertexSize, inflate), cube, (float)properties.textureWidth(), (float)properties.textureHeight(), mirror
      );
      return new GeoCube(quads, pivot, rotation, size, inflate, mirror);
   }
}
