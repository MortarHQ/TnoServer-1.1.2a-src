package net.unusual.block_factorys_bosses.client.particle.bedrock.vector;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.phys.Vec2;
import org.joml.Vector2f;
import software.bernie.geckolib.loading.math.MathParser;
import software.bernie.geckolib.loading.math.MathValue;
import software.bernie.geckolib.loading.math.value.Constant;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public record ParticleVector2(MathValue x, MathValue y) {
   public void getVector(Vector2f vector) {
      vector.set(this.x.get(), this.y.get());
   }

   public Vector2f getNewVector() {
      Vector2f vector = new Vector2f();
      this.getVector(vector);
      return vector;
   }

   public static ParticleVector2 deserialize(JsonElement vectorJson) {
      if (vectorJson == null) {
         throw new IllegalArgumentException("Received a null vector.");
      }

      if (vectorJson instanceof JsonArray jsonArray) {
         if (jsonArray.size() != 2) {
            throw new IllegalArgumentException("Vector array must have 2 entries.");
         } else {
            return new ParticleVector2(MathParser.parseJson(jsonArray.get(0)), MathParser.parseJson(jsonArray.get(1)));
         }
      } else {
         throw new IllegalArgumentException("Vector must be an array.");
      }
   }

   public static ParticleVector2 deserialize(@Nullable JsonElement vectorJson, Vec2 defaultVector) {
      return vectorJson == null ? of(defaultVector) : deserialize(vectorJson);
   }

   public static ParticleVector2 of(Vec2 vector) {
      return new ParticleVector2(new Constant(vector.x), new Constant(vector.y));
   }
}
