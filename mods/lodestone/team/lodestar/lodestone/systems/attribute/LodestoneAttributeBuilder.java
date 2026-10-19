package team.lodestar.lodestone.systems.attribute;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attribute.Sentiment;

public class LodestoneAttributeBuilder {
   public final ResourceLocation id;
   public final double defaultValue;
   public final double minValue;
   public final double maxValue;
   public ResourceLocation baseId;
   public boolean forcePercentage;
   public boolean syncable;
   public Sentiment sentiment = Sentiment.POSITIVE;

   public LodestoneAttributeBuilder(ResourceLocation id, double defaultValue) {
      this(id, defaultValue, 0.0, 0.0);
   }

   public LodestoneAttributeBuilder(ResourceLocation id, double defaultValue, double minValue, double maxValue) {
      this.id = id;
      this.defaultValue = defaultValue;
      this.minValue = minValue;
      this.maxValue = maxValue;
   }

   public LodestoneAttributeBuilder setAsBaseAttribute() {
      return this.setAsBaseAttribute(ResourceLocation.withDefaultNamespace("base_" + this.id.getPath()));
   }

   public LodestoneAttributeBuilder setAsBaseAttribute(ResourceLocation baseId) {
      this.baseId = baseId;
      return this;
   }

   public LodestoneAttributeBuilder forcePercentageDisplay() {
      this.forcePercentage = true;
      return this;
   }

   public LodestoneAttributeBuilder setSyncable(boolean syncable) {
      this.syncable = syncable;
      return this;
   }

   public LodestoneAttributeBuilder setSentiment(Sentiment sentiment) {
      this.sentiment = sentiment;
      return this;
   }

   public Attribute build() {
      return this.minValue < this.maxValue
         ? new LodestoneRangedAttribute(this.id, this.baseId, this.defaultValue, this.minValue, this.maxValue, this.forcePercentage)
            .setSyncable(this.syncable)
            .setSentiment(this.sentiment)
         : new LodestoneAttribute(this.id, this.baseId, this.defaultValue, this.forcePercentage).setSyncable(this.syncable).setSentiment(this.sentiment);
   }
}
