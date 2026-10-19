package twilightforest.util.entities;

import com.mojang.authlib.GameProfile;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import twilightforest.init.TFDataAttachments;

public class OminousFireDamageSource extends DamageSource {
   public OminousFireDamageSource(DamageSource wrappedSource) {
      super(wrappedSource.typeHolder(), wrappedSource.getEntity(), wrappedSource.getDirectEntity(), wrappedSource.getSourcePosition());
   }

   public Component getLocalizedDeathMessage(LivingEntity living) {
      if (!(living.getKillCredit() instanceof Zombie zombie && zombie.hasData(TFDataAttachments.ZOMBIFIED_PLAYER))) {
         return super.getLocalizedDeathMessage(living);
      } else {
         return living instanceof Player player
               && player.getGameProfile().getName().equals(((GameProfile)zombie.getData(TFDataAttachments.ZOMBIFIED_PLAYER)).getName())
            ? Component.translatable("death.attack.twilightforest.ominousFire.zombified_player.self", new Object[]{living.getDisplayName()})
            : Component.translatable(
               "death.attack.twilightforest.ominousFire.zombified_player",
               new Object[]{living.getDisplayName(), ((GameProfile)zombie.getData(TFDataAttachments.ZOMBIFIED_PLAYER)).getName()}
            );
      }
   }
}
