package adventofcode2016;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import org.junit.Test;

public class Day4Test {

	@Test
	public void testSamples() {
		assertThat(Day4.recalculateChecksum("aaaaa-bbb-z-y-x-123[abxyz]")).isEqualTo("aaaaa-bbb-z-y-x-123[abxyz]");
	}

	@Test
	public void testValidity() throws Exception {
		assertTrue(Day4.isValueRoomId("aaaaa-bbb-z-y-x-123[abxyz]"));
		assertTrue(Day4.isValueRoomId("a-b-c-d-e-f-g-h-987[abcde]"));
		assertTrue(Day4.isValueRoomId("not-a-real-room-404[oarel]"));
		assertFalse(Day4.isValueRoomId("totally-real-room-200[decoy]"));
	}

	@Test
	public void canParseSectorIds() throws Exception {
		assertThat(Day4.getSectorId("aaaaa-bbb-z-y-x-123[abxyz]")).isEqualTo(123);
		assertThat(Day4.getSectorId("not-a-real-room-404[oarel]")).isEqualTo(404);
	}

	@Test
	public void sampleDecryptSucceeds() throws Exception {
		assertThat(Day4.decryptRoomId("qzmt-zixmtkozy-ivhz-343")).isEqualTo("very encrypted name");
	}
}
