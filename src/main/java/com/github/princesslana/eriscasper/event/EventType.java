package com.github.princesslana.eriscasper.event;

import com.github.princesslana.eriscasper.data.GuildCreateData;
import com.github.princesslana.eriscasper.data.ReadyData;
import java.util.function.Function;

/**
 * Possible event types that we can receive from Discord.
 *
 * <p>Each EventType is associated with an {@link EventFactory} so that it is known how to construct
 * an {@link Event} from a {@link com.github.princesslana.eriscasper.gateway.Payload}.
 *
 * <p>It is tempting to try and merge EventType and {@link EventFactory} into a single class, but we
 * can not match the dataClass and factory types using generics within the EventType enum.
 *
 * @see <a
 *     href="https://discordapp.com/developers/docs/topics/gateway#commands-and-events-gateway-events">
 *     https://discordapp.com/developers/docs/topics/gateway#commands-and-events-gateway-events</a>
 */
public enum EventType {
  GUILD_CREATE(GuildCreateData.class, ImmutableGuildCreate::of),
  READY(ReadyData.class, ImmutableReady::of);

  private final EventFactory<?> factory;

  private EventType(EventFactory<?> factory) {
    this.factory = factory;
  }

  private <T> EventType(Class<T> dataClass, Function<T, Event<T>> factory) {
    this(EventFactoryTuple.of(dataClass, factory));
  }

  public EventFactory<?> getFactory() {
    return factory;
  }
}
