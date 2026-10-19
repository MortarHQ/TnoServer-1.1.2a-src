package twilightforest.client.model.block.forcefield;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.JsonOps;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import net.minecraft.client.renderer.block.model.BlockElement;
import net.minecraft.client.renderer.block.model.BlockElementFace;
import net.minecraft.client.renderer.block.model.BlockElementRotation;
import net.minecraft.client.renderer.block.model.BlockFaceUV;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.neoforged.neoforge.client.model.ExtraFaceData;
import net.neoforged.neoforge.client.model.generators.CustomLoaderBuilder;
import net.neoforged.neoforge.client.model.generators.ModelBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.apache.commons.lang3.mutable.MutableObject;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import twilightforest.TwilightForestMod;

public class ForceFieldModelBuilder<T extends ModelBuilder<T>> extends CustomLoaderBuilder<T> {
   protected final List<ForceFieldModelBuilder<T>.ForceFieldElementBuilder> elements = new ArrayList<>();

   public static <T extends ModelBuilder<T>> ForceFieldModelBuilder<T> begin(T parent, ExistingFileHelper helper) {
      return new ForceFieldModelBuilder<>(parent, helper);
   }

   private ForceFieldModelBuilder<T> self() {
      return this;
   }

   protected ForceFieldModelBuilder(T parent, ExistingFileHelper helper) {
      super(TwilightForestMod.prefix("force_field"), parent, helper, false);
   }

   public ForceFieldModelBuilder<T>.ForceFieldElementBuilder forceFieldElement() {
      ForceFieldModelBuilder<T>.ForceFieldElementBuilder ret = new ForceFieldModelBuilder.ForceFieldElementBuilder();
      this.elements.add(ret);
      return ret;
   }

   public JsonObject toJson(JsonObject json) {
      json = super.toJson(json);
      if (!this.elements.isEmpty()) {
         JsonArray elements = new JsonArray();
         this.elements.forEach(forceFieldElementBuilder -> {
            BlockElement part = forceFieldElementBuilder.build();
            JsonObject partObj = new JsonObject();
            if (forceFieldElementBuilder.condition != null) {
               JsonObject condition = new JsonObject();
               condition.addProperty("if", (Boolean)forceFieldElementBuilder.condition.getSecond());
               condition.addProperty("direction", ((ForceFieldModel.ExtraDirection)forceFieldElementBuilder.condition.getFirst()).getSerializedName());
               JsonArray parents = new JsonArray();

               for (ForceFieldModel.ExtraDirection parent : forceFieldElementBuilder.parents) {
                  parents.add(parent.getSerializedName());
               }

               condition.add("parents", parents);
               partObj.add("condition", condition);
            }

            partObj.add("from", this.serializeVector3f(part.from));
            partObj.add("to", this.serializeVector3f(part.to));
            if (part.rotation != null) {
               JsonObject rotation = new JsonObject();
               rotation.add("origin", this.serializeVector3f(part.rotation.origin()));
               rotation.addProperty("axis", part.rotation.axis().getSerializedName());
               rotation.addProperty("angle", part.rotation.angle());
               if (part.rotation.rescale()) {
                  rotation.addProperty("rescale", part.rotation.rescale());
               }

               partObj.add("rotation", rotation);
            }

            if (!part.shade) {
               partObj.addProperty("shade", part.shade);
            }

            JsonObject faces = new JsonObject();

            for (Direction dir : Direction.values()) {
               BlockElementFace face = (BlockElementFace)part.faces.get(dir);
               if (face != null) {
                  JsonObject faceObj = new JsonObject();
                  faceObj.addProperty("texture", this.serializeLocOrKey(face.texture()));
                  if (!Arrays.equals(face.uv().uvs, part.uvsByFace(dir))) {
                     faceObj.add("uv", new Gson().toJsonTree(face.uv().uvs));
                  }

                  if (face.cullForDirection() != null) {
                     faceObj.addProperty("cullface", face.cullForDirection().getSerializedName());
                  }

                  if (face.uv().rotation != 0) {
                     faceObj.addProperty("rotation", face.uv().rotation);
                  }

                  if (face.tintIndex() != -1) {
                     faceObj.addProperty("tintindex", face.tintIndex());
                  }

                  if (!face.faceData().equals(ExtraFaceData.DEFAULT)) {
                     faceObj.add("neoforge_data", (JsonElement)ExtraFaceData.CODEC.encodeStart(JsonOps.INSTANCE, face.faceData()).result().get());
                  }

                  faces.add(dir.getSerializedName(), faceObj);
               }
            }

            if (!part.faces.isEmpty()) {
               partObj.add("faces", faces);
            }

            elements.add(partObj);
         });
         json.add("elements", elements);
      }

      return json;
   }

   private String serializeLocOrKey(String tex) {
      return tex.charAt(0) == '#' ? tex : ResourceLocation.parse(tex).toString();
   }

   private JsonArray serializeVector3f(Vector3f vec) {
      JsonArray ret = new JsonArray();
      ret.add(this.serializeFloat(vec.x()));
      ret.add(this.serializeFloat(vec.y()));
      ret.add(this.serializeFloat(vec.z()));
      return ret;
   }

   private Number serializeFloat(float f) {
      return (int)f == f ? (int)f : f;
   }

   public enum FaceRotation {
      ZERO(0),
      CLOCKWISE_90(90),
      UPSIDE_DOWN(180),
      COUNTERCLOCKWISE_90(270);

      final int rotation;

      FaceRotation(int rotation) {
         this.rotation = rotation;
      }
   }

   public class ForceFieldElementBuilder {
      private Vector3f from = new Vector3f();
      private Vector3f to = new Vector3f(16.0F, 16.0F, 16.0F);
      private final Map<Direction, ForceFieldModelBuilder<T>.ForceFieldElementBuilder.FaceBuilder> faces = new LinkedHashMap<>();
      private ForceFieldModelBuilder<T>.ForceFieldElementBuilder.RotationBuilder rotation;
      private boolean shade = true;
      private int color = -1;
      private int blockLight = 0;
      private int skyLight = 0;
      private boolean hasAmbientOcclusion = true;
      private Pair<ForceFieldModel.ExtraDirection, Boolean> condition = null;
      private final List<ForceFieldModel.ExtraDirection> parents = new ArrayList<>();

      private void validateCoordinate(float coord, char name) {
         Preconditions.checkArgument(!(coord < -16.0F) && !(coord > 32.0F), "Position " + name + " out of range, must be within [-16, 32]. Found: %d", coord);
      }

      private void validatePosition(Vector3f pos) {
         this.validateCoordinate(pos.x(), 'x');
         this.validateCoordinate(pos.y(), 'y');
         this.validateCoordinate(pos.z(), 'z');
      }

      public ForceFieldModelBuilder<T>.ForceFieldElementBuilder from(float x, float y, float z) {
         this.from = new Vector3f(x, y, z);
         this.validatePosition(this.from);
         return this;
      }

      public ForceFieldModelBuilder<T>.ForceFieldElementBuilder to(float x, float y, float z) {
         this.to = new Vector3f(x, y, z);
         this.validatePosition(this.to);
         return this;
      }

      public ForceFieldModelBuilder<T>.ForceFieldElementBuilder.FaceBuilder face(Direction dir) {
         Preconditions.checkNotNull(dir, "Direction must not be null");
         return this.faces.computeIfAbsent(dir, x$0 -> new ForceFieldModelBuilder.ForceFieldElementBuilder.FaceBuilder(x$0));
      }

      public ForceFieldModelBuilder<T>.ForceFieldElementBuilder.RotationBuilder rotation() {
         if (this.rotation == null) {
            this.rotation = new ForceFieldModelBuilder.ForceFieldElementBuilder.RotationBuilder();
         }

         return this.rotation;
      }

      public ForceFieldModelBuilder<T>.ForceFieldElementBuilder shade(boolean shade) {
         this.shade = shade;
         return this;
      }

      public ForceFieldModelBuilder<T>.ForceFieldElementBuilder allFaces(
         BiConsumer<Direction, ForceFieldModelBuilder<T>.ForceFieldElementBuilder.FaceBuilder> action
      ) {
         Arrays.stream(Direction.values()).forEach(d -> action.accept(d, this.face(d)));
         return this;
      }

      public ForceFieldModelBuilder<T>.ForceFieldElementBuilder faces(
         BiConsumer<Direction, ForceFieldModelBuilder<T>.ForceFieldElementBuilder.FaceBuilder> action
      ) {
         this.faces.forEach(action);
         return this;
      }

      public ForceFieldModelBuilder<T>.ForceFieldElementBuilder textureAll(String texture) {
         return this.allFaces(this.addTexture(texture));
      }

      public ForceFieldModelBuilder<T>.ForceFieldElementBuilder texture(String texture) {
         return this.faces(this.addTexture(texture));
      }

      public ForceFieldModelBuilder<T>.ForceFieldElementBuilder cube(String texture) {
         return this.allFaces(this.addTexture(texture).andThen((dir, f) -> f.cullface(dir)));
      }

      public ForceFieldModelBuilder<T>.ForceFieldElementBuilder emissivity(int blockLight, int skyLight) {
         this.blockLight = blockLight;
         this.skyLight = skyLight;
         return this;
      }

      public ForceFieldModelBuilder<T>.ForceFieldElementBuilder color(int color) {
         this.color = color;
         return this;
      }

      public ForceFieldModelBuilder<T>.ForceFieldElementBuilder ao(boolean ao) {
         this.hasAmbientOcclusion = ao;
         return this;
      }

      public ForceFieldModelBuilder<T>.ForceFieldElementBuilder ifState(ForceFieldModel.ExtraDirection condition, boolean b) {
         this.condition = Pair.of(condition, b);
         return this;
      }

      public ForceFieldModelBuilder<T>.ForceFieldElementBuilder ifSame() {
         ForceFieldModelBuilder<T>.ForceFieldElementBuilder newBuilder = this.end().forceFieldElement();
         newBuilder.condition = Pair.of((ForceFieldModel.ExtraDirection)this.condition.getFirst(), (Boolean)this.condition.getSecond());
         return newBuilder;
      }

      public ForceFieldModelBuilder<T>.ForceFieldElementBuilder ifElse() {
         ForceFieldModelBuilder<T>.ForceFieldElementBuilder newBuilder = this.end().forceFieldElement();
         newBuilder.condition = Pair.of((ForceFieldModel.ExtraDirection)this.condition.getFirst(), !(Boolean)this.condition.getSecond());
         return newBuilder;
      }

      public ForceFieldModelBuilder<T>.ForceFieldElementBuilder parents(ForceFieldModel.ExtraDirection... parents) {
         Collections.addAll(this.parents, parents);
         return this;
      }

      private BiConsumer<Direction, ForceFieldModelBuilder<T>.ForceFieldElementBuilder.FaceBuilder> addTexture(String texture) {
         return ($, f) -> f.texture(texture);
      }

      BlockElement build() {
         Map<Direction, BlockElementFace> faces = this.faces
            .entrySet()
            .stream()
            .collect(Collectors.toMap(Entry::getKey, e -> ((ForceFieldModelBuilder.ForceFieldElementBuilder.FaceBuilder)e.getValue()).build(), (k1, k2) -> {
               throw new IllegalArgumentException();
            }, LinkedHashMap::new));
         return new BlockElement(
            this.from,
            this.to,
            faces,
            this.rotation == null ? null : this.rotation.build(),
            this.shade,
            new ExtraFaceData(this.color, this.blockLight, this.skyLight, this.hasAmbientOcclusion)
         );
      }

      public ForceFieldModelBuilder<T> end() {
         return ForceFieldModelBuilder.this.self();
      }

      public class FaceBuilder {
         private Direction cullface;
         private int tintindex = -1;
         private String texture = MissingTextureAtlasSprite.getLocation().toString();
         private float[] uvs;
         private ForceFieldModelBuilder.FaceRotation rotation = ForceFieldModelBuilder.FaceRotation.ZERO;
         private int color = -1;
         private int blockLight = 0;
         private int skyLight = 0;
         private boolean hasAmbientOcclusion = true;

         FaceBuilder(Direction dir) {
         }

         public ForceFieldModelBuilder<T>.ForceFieldElementBuilder.FaceBuilder cullface(@Nullable Direction dir) {
            this.cullface = dir;
            return this;
         }

         public ForceFieldModelBuilder<T>.ForceFieldElementBuilder.FaceBuilder tintindex(int index) {
            this.tintindex = index;
            return this;
         }

         public ForceFieldModelBuilder<T>.ForceFieldElementBuilder.FaceBuilder texture(String texture) {
            Preconditions.checkNotNull(texture, "Texture must not be null");
            this.texture = texture;
            return this;
         }

         public ForceFieldModelBuilder<T>.ForceFieldElementBuilder.FaceBuilder uvs(float u1, float v1, float u2, float v2) {
            this.uvs = new float[]{u1, v1, u2, v2};
            return this;
         }

         public ForceFieldModelBuilder<T>.ForceFieldElementBuilder.FaceBuilder rotation(ForceFieldModelBuilder.FaceRotation rot) {
            Preconditions.checkNotNull(rot, "Rotation must not be null");
            this.rotation = rot;
            return this;
         }

         public ForceFieldModelBuilder<T>.ForceFieldElementBuilder.FaceBuilder emissivity(int blockLight, int skyLight) {
            this.blockLight = blockLight;
            this.skyLight = skyLight;
            return this;
         }

         public ForceFieldModelBuilder<T>.ForceFieldElementBuilder.FaceBuilder color(int color) {
            this.color = color;
            return this;
         }

         public ForceFieldModelBuilder<T>.ForceFieldElementBuilder.FaceBuilder ao(boolean ao) {
            this.hasAmbientOcclusion = ao;
            return this;
         }

         BlockElementFace build() {
            if (this.texture == null) {
               throw new IllegalStateException("A model face must have a texture");
            } else {
               return new BlockElementFace(
                  this.cullface,
                  this.tintindex,
                  this.texture,
                  new BlockFaceUV(this.uvs, this.rotation.rotation),
                  new ExtraFaceData(this.color, this.blockLight, this.skyLight, this.hasAmbientOcclusion),
                  new MutableObject()
               );
            }
         }

         public ForceFieldModelBuilder<T>.ForceFieldElementBuilder end() {
            return ForceFieldElementBuilder.this;
         }
      }

      public class RotationBuilder {
         private Vector3f origin;
         private Axis axis;
         private float angle;
         private boolean rescale;

         public ForceFieldModelBuilder<T>.ForceFieldElementBuilder.RotationBuilder origin(float x, float y, float z) {
            this.origin = new Vector3f(x, y, z);
            return this;
         }

         public ForceFieldModelBuilder<T>.ForceFieldElementBuilder.RotationBuilder axis(Axis axis) {
            Preconditions.checkNotNull(axis, "Axis must not be null");
            this.axis = axis;
            return this;
         }

         public ForceFieldModelBuilder<T>.ForceFieldElementBuilder.RotationBuilder angle(float angle) {
            Preconditions.checkArgument(
               angle == 0.0F || Mth.abs(angle) == 22.5F || Mth.abs(angle) == 45.0F, "Invalid rotation %f found, only -45/-22.5/0/22.5/45 allowed", angle
            );
            this.angle = angle;
            return this;
         }

         public ForceFieldModelBuilder<T>.ForceFieldElementBuilder.RotationBuilder rescale(boolean rescale) {
            this.rescale = rescale;
            return this;
         }

         BlockElementRotation build() {
            return new BlockElementRotation(this.origin, this.axis, this.angle, this.rescale);
         }

         public ForceFieldModelBuilder<T>.ForceFieldElementBuilder end() {
            return ForceFieldElementBuilder.this;
         }
      }
   }
}
