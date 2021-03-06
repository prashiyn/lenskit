/*
 * LensKit, an open source recommender systems toolkit.
 * Copyright 2010-2014 LensKit Contributors.  See CONTRIBUTORS.md.
 * Work on LensKit has been funded by the National Science Foundation under
 * grants IIS 05-34939, 08-08692, 08-12148, and 10-17697.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package org.grouplens.lenskit.basic;

import it.unimi.dsi.fastutil.longs.LongLists;
import org.grouplens.lenskit.RatingPredictor;
import org.grouplens.lenskit.vectors.MutableSparseVector;
import org.grouplens.lenskit.vectors.SparseVector;

import javax.annotation.Nonnull;
import java.util.Collection;

/**
 * Base class to make rating predictors easier to implement. Delegates all predict methods to
 * {@link #predict(long, MutableSparseVector)}.
 *
 * @author <a href="http://www.grouplens.org">GroupLens Research</a>
 */
public abstract class AbstractRatingPredictor implements RatingPredictor {
    /**
     * {@inheritDoc}
     * <p>Delegates to {@link #predict(long, MutableSparseVector)}.
     */
    @Nonnull
    @Override
    public SparseVector predict(long user, @Nonnull Collection<Long> items) {
        MutableSparseVector scores = MutableSparseVector.create(items);
        predict(user, scores);
        // FIXME Create a more efficient way of "releasing" mutable sparse vectors
        return scores.freeze();
    }

    /**
     * {@inheritDoc}
     * <p>Delegates to {@link #predict(long, java.util.Collection)}.
     */
    @Override
    public double predict(long user, long item) {
        SparseVector v = predict(user, LongLists.singleton(item));
        return v.get(item, Double.NaN);
    }
}
