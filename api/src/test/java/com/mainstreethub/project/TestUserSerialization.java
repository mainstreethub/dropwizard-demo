package com.mainstreethub.project;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mainstreethub.project.api.User;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.FixtureHelpers;
import java.io.IOException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestUserSerialization {
  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  @Test
  public void testDeserializationFromJSON() throws IOException {
    User user = MAPPER.readValue(FixtureHelpers.fixture("fixtures/user.json"), User.class);
    assertEquals("bbeck", user.getUsername());
    assertEquals("Brandon", user.getFirst());
    assertEquals("Beck", user.getLast());
  }

  @Test
  public void testSerializeToJSON() throws IOException {
    User user = new User("bbeck", "Brandon", "Beck");
    String json = MAPPER.writeValueAsString(user);

    assertJsonEquals(FixtureHelpers.fixture("fixtures/user.json"), json);
  }

  private static void assertJsonEquals(String expected, String actual) throws IOException {
    JsonNode expectedNode = MAPPER.readTree(expected);
    JsonNode actualNode = MAPPER.readTree(actual);
    assertEquals(expectedNode, actualNode);
  }
}
