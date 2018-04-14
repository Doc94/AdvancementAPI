package io.chazza.advancementapi;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class FrameTypeTest {
    @Test
    public void testFrameType_Random() {
        assertNotNull(FrameType.RANDOM());
    }

    @Test
    public void testFrameType_getFromString() throws Exception {
        assertNotNull(FrameType.getFromString("RANDom"));
        assertThat(FrameType.getFromString("task"), is(FrameType.TASK));
        assertThat(FrameType.getFromString("ChallenGE"), is(FrameType.CHALLENGE));
        assertThat(FrameType.getFromString("Goal"), is(FrameType.GOAL));

        assertThat(FrameType.getFromString("none"), is(FrameType.TASK));
        assertThat(FrameType.getFromString(""), is(FrameType.TASK));
    }
}