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
package io.github.benas.randombeans;

import static io.github.benas.randombeans.EnhancedRandomBuilder.aNewEnhancedRandom;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.lang.reflect.Array;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.github.benas.randombeans.api.EnhancedRandom;
import io.github.benas.randombeans.api.Randomizer;
import io.github.benas.randombeans.beans.ArrayBean;
import io.github.benas.randombeans.beans.Person;

@ExtendWith(MockitoExtension.class)
public class ArrayPopulatorTest {

    private static final int INT = 10;
    private static final String STRING = "FOO";

    @Mock
    private RandomizationContext context;
    @Mock
    private EnhancedRandomImpl enhancedRandom;
    @Mock
    private RandomizerProvider randomizerProvider;
    @Mock
    private Randomizer integerRandomizer;

    private ArrayPopulator arrayPopulator;

    @BeforeEach
    public void setUp() {
        arrayPopulator = new ArrayPopulator(enhancedRandom, randomizerProvider);
    }

    @Test
    public void getRandomArray() {
        when(enhancedRandom.getRandomCollectionSize()).thenReturn(INT);
        when(enhancedRandom.doPopulateBean(String.class, context)).thenReturn(STRING);

        String[] strings = (String[]) arrayPopulator.getRandomArray(String[].class, context);

        assertThat(strings).containsOnly(STRING);
    }

    @Test
    public void getRandomPrimitiveArray() {
        when(enhancedRandom.nextInt()).thenReturn(INT);
        when(randomizerProvider.getRandomizerByType(Integer.TYPE)).thenReturn(integerRandomizer);
        when(integerRandomizer.getRandomValue()).thenReturn(INT);

        int[] ints = (int[]) arrayPopulator.getRandomPrimitiveArray(Integer.TYPE);

        assertThat(ints).containsOnly(INT);
    }

    /*
     * Integration tests for arrays population
     */

    @Test
    public void testArrayPopulation() {
        EnhancedRandom enhancedRandom = aNewEnhancedRandom();

        final String[] strings = enhancedRandom.nextObject(String[].class);

        assertThat(strings).isNotNull();
    }

    @Test
    public void testPrimitiveArrayPopulation() {
        EnhancedRandom enhancedRandom = aNewEnhancedRandom();

        final int[] ints = enhancedRandom.nextObject(int[].class);

        assertThat(ints).isNotNull();
    }

    @Test
    public void primitiveArraysShouldBeCorrectlyPopulated() {
        EnhancedRandom enhancedRandom = aNewEnhancedRandom();

        final ArrayBean bean = enhancedRandom.nextObject(ArrayBean.class);

        // primitive types
        assertThat(toObjectArray(bean.getByteArray())).hasOnlyElementsOfType(Byte.class);
        assertThat(toObjectArray(bean.getShortArray())).hasOnlyElementsOfType(Short.class);
        assertThat(toObjectArray(bean.getIntArray())).hasOnlyElementsOfType(Integer.class);
        assertThat(toObjectArray(bean.getLongArray())).hasOnlyElementsOfType(Long.class);
        assertThat(toObjectArray(bean.getFloatArray())).hasOnlyElementsOfType(Float.class);
        assertThat(toObjectArray(bean.getDoubleArray())).hasOnlyElementsOfType(Double.class);
        assertThat(toObjectArray(bean.getCharArray())).hasOnlyElementsOfType(Character.class);
        assertThat(toObjectArray(bean.getBooleanArray())).hasOnlyElementsOfType(Boolean.class);
    }

    @Test
    public void wrapperTypeArraysShouldBeCorrectlyPopulated() {
        EnhancedRandom enhancedRandom = aNewEnhancedRandom();

        final ArrayBean bean = enhancedRandom.nextObject(ArrayBean.class);

        // wrapper types
        assertThat(bean.getBytes()).hasOnlyElementsOfType(Byte.class);
        assertThat(bean.getShorts()).hasOnlyElementsOfType(Short.class);
        assertThat(bean.getIntegers()).hasOnlyElementsOfType(Integer.class);
        assertThat(bean.getLongs()).hasOnlyElementsOfType(Long.class);
        assertThat(bean.getFloats()).hasOnlyElementsOfType(Float.class);
        assertThat(bean.getDoubles()).hasOnlyElementsOfType(Double.class);
        assertThat(bean.getCharacters()).hasOnlyElementsOfType(Character.class);
        assertThat(bean.getBooleans()).hasOnlyElementsOfType(Boolean.class);
    }

    @Test
    public void arraysWithCustomTypesShouldBeCorrectlyPopulated() {
        EnhancedRandom enhancedRandom = aNewEnhancedRandom();

        final ArrayBean bean = enhancedRandom.nextObject(ArrayBean.class);

        // custom types
        assertThat(bean.getStrings()).doesNotContain(null, "");

        Person[] persons = bean.getPersons();
        assertContainsOnlyNonEmptyPersons(persons);
    }

    private void assertContainsOnlyNonEmptyPersons(final Person[] persons) {
        for (Person person : persons) {
            assertThat(person).isNotNull();
            assertThat(person.getAddress().getCity()).isNotEmpty();
            assertThat(person.getAddress().getZipCode()).isNotEmpty();
            assertThat(person.getName()).isNotEmpty();
        }
    }

    private Object[] toObjectArray(Object primitiveArray) {
        int length = Array.getLength(primitiveArray);
        Object[] objectArray = new Object[length];
        for (int i = 0; i < length; ++i) {
            objectArray[i] = Array.get(primitiveArray, i);
        }
        return objectArray;
    }
}
