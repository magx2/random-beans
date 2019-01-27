/**
 * The MIT License
 *
 *   Copyright (c) 2019, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in
 *   all copies or substantial portions of the Software.
 *
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *   THE SOFTWARE.
 */
package io.github.benas.randombeans.randomizers.time.internal;

import static io.github.benas.randombeans.randomizers.time.MinuteRandomizer.MAX_MINUTE;
import static io.github.benas.randombeans.randomizers.time.MinuteRandomizer.MIN_MINUTE;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.github.benas.randombeans.randomizers.AbstractRandomizerTest;
import io.github.benas.randombeans.randomizers.time.MinuteRandomizer;

public class MinuteRandomizerTest extends AbstractRandomizerTest<Integer> {

    @BeforeEach
    public void setUp() {
        randomizer = new MinuteRandomizer();
    }

    @Test
    public void generatedValueShouldNotBeNull() {
        assertThat(randomizer.getRandomValue()).isNotNull();
    }

    @Test
    public void generatedValueShouldBeWithinRange() {
        assertThat(randomizer.getRandomValue()).isBetween(MIN_MINUTE, MAX_MINUTE);
    }

    @Test
    public void shouldGenerateTheSameValueForTheSameSeed() {
        // Given
        randomizer = new MinuteRandomizer(SEED);
        Integer expected = 42;

        // When
        Integer actual = randomizer.getRandomValue();

        // Then
        assertThat(actual).isEqualTo(expected);
    }
}
