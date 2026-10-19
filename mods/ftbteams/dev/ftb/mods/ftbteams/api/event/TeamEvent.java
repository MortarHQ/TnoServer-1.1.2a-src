package dev.ftb.mods.ftbteams.api.event;

import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import dev.ftb.mods.ftbteams.api.Team;
import java.util.function.Consumer;

public class TeamEvent {
   public static final Event<Consumer<TeamCreatedEvent>> CREATED = EventFactory.createConsumerLoop(new TeamCreatedEvent[0]);
   public static final Event<Consumer<TeamEvent>> LOADED = EventFactory.createConsumerLoop(new TeamEvent[0]);
   public static final Event<Consumer<TeamEvent>> SAVED = EventFactory.createConsumerLoop(new TeamEvent[0]);
   public static final Event<Consumer<TeamEvent>> DELETED = EventFactory.createConsumerLoop(new TeamEvent[0]);
   public static final Event<Consumer<PlayerLoggedInAfterTeamEvent>> PLAYER_LOGGED_IN = EventFactory.createConsumerLoop(new PlayerLoggedInAfterTeamEvent[0]);
   public static final Event<Consumer<PlayerTransferredTeamOwnershipEvent>> OWNERSHIP_TRANSFERRED = EventFactory.createConsumerLoop(
      new PlayerTransferredTeamOwnershipEvent[0]
   );
   public static final Event<Consumer<TeamCollectPropertiesEvent>> COLLECT_PROPERTIES = EventFactory.createConsumerLoop(new TeamCollectPropertiesEvent[0]);
   public static final Event<Consumer<TeamPropertiesChangedEvent>> PROPERTIES_CHANGED = EventFactory.createConsumerLoop(new TeamPropertiesChangedEvent[0]);
   public static final Event<Consumer<PlayerChangedTeamEvent>> PLAYER_CHANGED = EventFactory.createConsumerLoop(new PlayerChangedTeamEvent[0]);
   public static final Event<Consumer<PlayerJoinedPartyTeamEvent>> PLAYER_JOINED_PARTY = EventFactory.createConsumerLoop(new PlayerJoinedPartyTeamEvent[0]);
   public static final Event<Consumer<PlayerLeftPartyTeamEvent>> PLAYER_LEFT_PARTY = EventFactory.createConsumerLoop(new PlayerLeftPartyTeamEvent[0]);
   public static final Event<Consumer<TeamInfoEvent>> INFO = EventFactory.createConsumerLoop(new TeamInfoEvent[0]);
   public static final Event<Consumer<TeamAllyEvent>> ADD_ALLY = EventFactory.createConsumerLoop(new TeamAllyEvent[0]);
   public static final Event<Consumer<TeamAllyEvent>> REMOVE_ALLY = EventFactory.createConsumerLoop(new TeamAllyEvent[0]);
   public static final Event<Consumer<ClientTeamPropertiesChangedEvent>> CLIENT_PROPERTIES_CHANGED = EventFactory.createConsumerLoop(
      ClientTeamPropertiesChangedEvent.class
   );
   private final Team team;

   public TeamEvent(Team team) {
      this.team = team;
   }

   public Team getTeam() {
      return this.team;
   }
}
