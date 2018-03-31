package com.github.princesslana.eriscasper.event;

import com.github.princesslana.eriscasper.data.event.Event;
import com.github.princesslana.eriscasper.data.GuildCreateData;
import com.github.princesslana.eriscasper.data.Message;
import com.github.princesslana.eriscasper.data.ResumedData;
import com.github.princesslana.eriscasper.data.TypingStartData;
import com.github.princesslana.eriscasper.data.immutable.Wrapped;
import com.github.princesslana.eriscasper.data.immutable.Wrapper;
import org.immutables.value.Value;

@Wrapped
public class Events {

  private Events() {}

  @Value.Immutable
  public static interface GuildCreateWrapper extends Event, Wrapper<GuildCreateData> {}

  @Value.Immutable
  public static interface MessageCreateWrapper extends Event, Wrapper<Message> {}

  @Value.Immutable
  public static interface ResumedWrapper extends Event, Wrapper<ResumedData> {}

  @Value.Immutable
  public static interface TypingStartWrapper extends Event, Wrapper<TypingStartData> {}
}
