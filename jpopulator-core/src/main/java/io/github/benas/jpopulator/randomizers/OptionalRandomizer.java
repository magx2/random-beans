/*
 * The MIT License
 *
 *   Copyright (c) 2016, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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
 *
 */
package io.github.benas.jpopulator.randomizers;

import io.github.benas.jpopulator.api.Randomizer;

import java.util.Random;

/**
 * A {@link Randomizer} which, according to the optional percent, returns the random value from a delegate.
 *
 * @param <T> the type generated by this randomizer
 * @author Eric Taix (eric.taix@gmail.com)
 */
public class OptionalRandomizer<T> implements Randomizer<T> {

    private static final int MAX_PERCENT = 100;
    private final Random randomPercent = new Random(System.currentTimeMillis());
    private Randomizer<T> delegate;
    private int optionalPercent;

    /**
     * A constructor with a delegate randomizer and an optional percent threshold.
     *
     * @param delegate        The delegate to use to retrieve a random value
     * @param optionalPercent The percent of randomized value to return (between 0 and 100)
     */
    public OptionalRandomizer(final Randomizer<T> delegate, final int optionalPercent) {
        this.delegate = delegate;
        if (optionalPercent > MAX_PERCENT) {
            this.optionalPercent = MAX_PERCENT;
        } else if (optionalPercent < 0) {
            this.optionalPercent = 0;
        } else {
            this.optionalPercent = optionalPercent;
        }
    }

    @Override
    public T getRandomValue() {
        if (randomPercent.nextInt(MAX_PERCENT) + 1 <= optionalPercent) {
            return delegate.getRandomValue();
        }
        return null;
    }

    /**
     * Factory method to help creating an optional randomizer.
     *
     * @param delegate        the delegate randomizer to use
     * @param optionalPercent the optional percent threshold
     * @param <T>             the type generated by this randomizer
     * @return optional randomizer
     */
    public static <T> Randomizer<T> option(final Randomizer<T> delegate, final int optionalPercent) {
        return new OptionalRandomizer<>(delegate, optionalPercent);
    }

}
