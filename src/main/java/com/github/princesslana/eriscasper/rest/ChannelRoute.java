package com.github.princesslana.eriscasper.rest;

import com.github.princesslana.eriscasper.data.Snowflake;
import com.github.princesslana.eriscasper.data.resource.Channel;
import com.github.princesslana.eriscasper.rest.channel.GetChannelMessagesRequest;
import com.github.princesslana.eriscasper.rest.channel.ModifyChannelRequest;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ChannelRoute {

  private final Snowflake id;

  private ChannelRoute(Snowflake id) {
    this.id = id;
  }

  /**
   * @see <a href="https://discordapp.com/developers/docs/resources/channel#get-channel">
   *     https://discordapp.com/developers/docs/resources/channel#get-channel</a>
   */
  public Route<Void, Channel> getChannel() {
    return Route.get(path("/"), Channel.class);
  }

  /**
   * @see <a href="https://discordapp.com/developers/docs/resources/channel#modify-channel">
   *     https://discordapp.com/developers/docs/resources/channel#modify-channel</a>
   */
  public Route<ModifyChannelRequest, Channel> modifyChannel() {
    return Route.put(path("/"), ModifyChannelRequest.class, Channel.class);
  }

  /**
   * @see <a href="https://discordapp.com/developers/docs/resources/channel#deleteclose-channel">
   *     https://discordapp.com/developers/docs/resources/channel#deleteclose-channel</a>
   */
  public Route<Void, Channel> deleteChannel() {
    return Route.delete(path("/"), Channel.class);
  }

  /**
   * @see <a href="https://discordapp.com/developers/docs/resources/channel#get-channel-messages">
   *     https://discordapp.com/developers/docs/resources/channel#get-channel-messages</a>
   */
  public Route<GetChannelMessagesRequest, ImmutableList<Channel>> getChannelMessages() {
    // We can ignore url encoding since this is only used internally with snowflake ids and longs
    Function<String, Function<String, String>> encode = k -> v -> String.format("%s=%s", k, v);
    Function<String, Function<Snowflake, String>> encodeSnowflake =
        k -> v -> encode.apply(k).apply(v.unwrap());

    return Route.get(
        path("/"),
        rq -> {
          List<String> params = new ArrayList<>();
          rq.getAround().map(encodeSnowflake.apply("around")).ifPresent(params::add);
          rq.getBefore().map(encodeSnowflake.apply("before")).ifPresent(params::add);
          rq.getAfter().map(encodeSnowflake.apply("after")).ifPresent(params::add);
          rq.getLimit().map(l -> l.toString()).map(encode.apply("limit")).ifPresent(params::add);
          return Joiner.on("&").join(params);
        },
        Route.jsonArrayResponse(Channel.class));
  }

  private String path(String path) {
    return "/channels/" + id.unwrap() + path;
  }

  public static ChannelRoute on(Snowflake id) {
    return new ChannelRoute(id);
  }

  public static ChannelRoute on(Channel channel) {
    return on(channel.getId());
  }
}
