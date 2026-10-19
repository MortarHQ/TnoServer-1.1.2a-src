package net.unusual.block_factorys_bosses.client.particle.bedrock.vector;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import javax.annotation.Nullable;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;
import software.bernie.geckolib.loading.math.MathParser;
import software.bernie.geckolib.loading.math.MathValue;
import software.bernie.geckolib.loading.math.value.Constant;

public record ParticleVector(MathValue x, MathValue y, MathValue z) implements ParticleDirection {
   @Override
   public void getDirection(Vector3f offsetInput, Vector3f direction) {
      this.getVector(direction);
   }

   public void getVector(Vector3f vector) {
      vector.set(this.x.get(), this.y.get(), this.z.get());
   }

   public Vector3f getNewVector() {
      Vector3f vector = new Vector3f();
      this.getVector(vector);
      return vector;
   }

   public static ParticleVector deserialize(JsonElement vectorJson) {
      if (vectorJson == null) {
         throw new IllegalArgumentException("Received a null vector.");
      }

      if (vectorJson instanceof JsonArray jsonArray) {
         if (jsonArray.size() != 3) {
            throw new IllegalArgumentException("Vector array must have 3 entries.");
         } else {
            return new ParticleVector(MathParser.parseJson(jsonArray.get(0)), MathParser.parseJson(jsonArray.get(1)), MathParser.parseJson(jsonArray.get(2)));
         }
      } else {
         throw new IllegalArgumentException("Vector must be an array.");
      }
   }

   @Nullable
   public static ParticleVector deserializeOrNull(@Nullable JsonElement vectorJson) {
      return vectorJson == null ? null : deserialize(vectorJson);
   }

   public static ParticleVector deserialize(@Nullable JsonElement vectorJson, Vec3 defaultVector) {
      return vectorJson == null ? of(defaultVector) : deserialize(vectorJson);
   }

   public static ParticleVector of(Vec3 vector) {
      return new ParticleVector(new Constant(vector.x()), new Constant(vector.y()), new Constant(vector.z()));
   }
}
