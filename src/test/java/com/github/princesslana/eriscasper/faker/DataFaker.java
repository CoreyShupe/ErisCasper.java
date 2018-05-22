package com.github.princesslana.eriscasper.faker;

import com.github.javafaker.Faker;
import com.github.princesslana.eriscasper.data.Snowflake;
import com.github.princesslana.eriscasper.data.event.ImmutableReadyEventData;
import com.github.princesslana.eriscasper.data.event.ReadyEventData;
import com.github.princesslana.eriscasper.data.resource.Channel;
import com.github.princesslana.eriscasper.data.resource.Guild;
import com.github.princesslana.eriscasper.data.resource.ImmutableChannel;
import com.github.princesslana.eriscasper.data.resource.ImmutableGuild;
import com.github.princesslana.eriscasper.data.resource.ImmutableMessage;
import com.github.princesslana.eriscasper.data.resource.ImmutableUnavailableGuild;
import com.github.princesslana.eriscasper.data.resource.ImmutableUser;
import com.github.princesslana.eriscasper.data.resource.Message;
import com.github.princesslana.eriscasper.data.resource.UnavailableGuild;
import com.github.princesslana.eriscasper.data.resource.User;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.OffsetDateTime;

public class DataFaker {
  private DataFaker() {}

  public static String discriminator() {
    return RandomStringUtils.randomNumeric(4);
  }

  public static Message message() {
    return ImmutableMessage.builder()
        .id(DiscordFaker.snowflake())
        .author(user())
        .channelId(DiscordFaker.snowflake())
        .content(Faker.instance().chuckNorris().fact())
        .timestamp(OffsetDateTime.now())
        .isTts(false)
        .isMentionEveryone(false)
        .isPinned(false)
        .type(0L)
        .build();
  }

  public static ReadyEventData ready() {
    return ImmutableReadyEventData.builder()
        .v(6L)
        .user(user())
        .sessionId(DiscordFaker.sessionId().unwrap())
        .build();
  }

  public static User user() {
    return ImmutableUser.builder()
        .id(DiscordFaker.snowflake())
        .username(username())
        .discriminator(discriminator())
        .build();
  }

  public static Guild guild() {
    Snowflake id = DiscordFaker.snowflake();
    return ImmutableGuild.builder()
        .id(id)
        .name(Faker.instance().name().name())
        .ownerId(DiscordFaker.snowflake())
        .region(Faker.instance().address().country())
        .afkTimeout(0L)
        .verificationLevel(0L)
        .defaultMessageNotifications(0L)
        .explicitContentFilter(0L)
        .mfaLevel(0L)
        .addChannels(guildChannel(id))
        .build();
  }

  public static Channel channel() {
    return ImmutableChannel.builder().id(DiscordFaker.snowflake()).type(0L).build();
  }

  public static Channel guildChannel(Snowflake guildId) {
    return ImmutableChannel.builder()
        .id(DiscordFaker.snowflake())
        .type(0L)
        .guildId(guildId)
        .build();
  }

  public static UnavailableGuild unavailableGuildFromGuild(Snowflake predeterminedId) {
    return ImmutableUnavailableGuild.builder().id(predeterminedId).build();
  }

  public static String username() {
    return Faker.instance().name().name();
  }
}
