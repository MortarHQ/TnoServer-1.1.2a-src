package org.crypticdev.ancient_artifacts.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.crypticdev.ancient_artifacts.AncientArtifacts;
import org.crypticdev.ancient_artifacts.artifact.ArtifactEffect;
import org.crypticdev.ancient_artifacts.artifact.ArtifactItem;

public record RefinementComponent(int grade, int refinement, List<Integer> upgrades) {
   public static final RefinementComponent DEFAULT = new RefinementComponent(3, 0, new ArrayList<>());
   public static final Map<Integer, Integer> GRADES = Map.of(1, 3, 2, 5, 3, 10);
   public static final Codec<RefinementComponent> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
            Codec.INT.fieldOf("grade").forGetter(RefinementComponent::grade),
            Codec.INT.fieldOf("refinement").forGetter(RefinementComponent::refinement),
            Codec.list(Codec.INT).fieldOf("upgrades").forGetter(RefinementComponent::upgrades)
         )
         .apply(instance, RefinementComponent::new)
   );
   public static final StreamCodec<ByteBuf, RefinementComponent> STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.INT,
      RefinementComponent::grade,
      ByteBufCodecs.INT,
      RefinementComponent::refinement,
      ByteBufCodecs.INT.apply(ByteBufCodecs.list()),
      RefinementComponent::upgrades,
      RefinementComponent::new
   );

   public RefinementComponent(int grade, List<Integer> upgrades) {
      this(grade, 0, upgrades);
   }

   public RefinementComponent addRefinement(ItemStack stack, RefinementComponent addition) {
      Item item = stack.getItem();
      ArtifactItem artifactItem = (ArtifactItem)item;
      List<Integer> effectsForUpgrade = new ArrayList<>();

      for (int i = 0; i < artifactItem.getArtifact().artifactEffects().size(); i++) {
         ArtifactEffect artifactEffect = artifactItem.getArtifact().artifactEffects().get(i);
         if (artifactEffect.maxRefinement().isPresent()) {
            if (this.upgrades.get(i) + addition.upgrades.get(i) <= artifactEffect.maxRefinement().getAsInt()
               && artifactEffect.maxRefinement().getAsInt() > this.upgrades.get(i) + addition.upgrades.get(i)) {
               effectsForUpgrade.add(i);
            }
         } else {
            effectsForUpgrade.add(i);
         }
      }

      List<Integer> newUpgrades = new ArrayList<>(this.upgrades);
      int i = 0;

      for (int size = Math.min(newUpgrades.size(), addition.upgrades.size()); i < size; i++) {
         newUpgrades.set(i, newUpgrades.get(i) + addition.upgrades.get(i));
      }

      if (!effectsForUpgrade.isEmpty() && !newUpgrades.isEmpty()) {
         if (AncientArtifacts.CONFIG.artifacts.random_refinement_upgrades) {
            i = effectsForUpgrade.get(new Random().nextInt(effectsForUpgrade.size()));
         } else {
            i = effectsForUpgrade.getLast();
         }

         newUpgrades.set(i, newUpgrades.get(i) + 1);
      }

      return new RefinementComponent(this.grade, this.refinement + addition.refinement() + 1, newUpgrades);
   }
}
