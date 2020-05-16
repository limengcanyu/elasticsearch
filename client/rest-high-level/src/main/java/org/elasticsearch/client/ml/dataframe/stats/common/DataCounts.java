/*
 * Licensed to Elasticsearch under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.elasticsearch.client.ml.dataframe.stats.common;

import org.elasticsearch.common.Nullable;
import org.elasticsearch.common.ParseField;
import org.elasticsearch.common.inject.internal.ToStringBuilder;
import org.elasticsearch.common.xcontent.ConstructingObjectParser;
import org.elasticsearch.common.xcontent.ToXContentObject;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;
import java.util.Objects;

import static org.elasticsearch.common.xcontent.ConstructingObjectParser.optionalConstructorArg;

public class DataCounts implements ToXContentObject {

    public static final String TYPE_VALUE = "analytics_data_counts";

    public static final ParseField TRAINING_DOCS_COUNT = new ParseField("training_docs_count");
    public static final ParseField TEST_DOCS_COUNT = new ParseField("test_docs_count");
    public static final ParseField SKIPPED_DOCS_COUNT = new ParseField("skipped_docs_count");

    public static final ConstructingObjectParser<DataCounts, Void> PARSER = new ConstructingObjectParser<>(TYPE_VALUE, true,
        a -> {
            Long trainingDocsCount = (Long) a[0];
            Long testDocsCount = (Long) a[1];
            Long skippedDocsCount = (Long) a[2];
            return new DataCounts(
                getOrDefault(trainingDocsCount, 0L),
                getOrDefault(testDocsCount, 0L),
                getOrDefault(skippedDocsCount, 0L)
            );
        });

    static {
        PARSER.declareLong(optionalConstructorArg(), TRAINING_DOCS_COUNT);
        PARSER.declareLong(optionalConstructorArg(), TEST_DOCS_COUNT);
        PARSER.declareLong(optionalConstructorArg(), SKIPPED_DOCS_COUNT);
    }

    private final long trainingDocsCount;
    private final long testDocsCount;
    private final long skippedDocsCount;

    public DataCounts(long trainingDocsCount, long testDocsCount, long skippedDocsCount) {
        this.trainingDocsCount = trainingDocsCount;
        this.testDocsCount = testDocsCount;
        this.skippedDocsCount = skippedDocsCount;
    }

    @Override
    public XContentBuilder toXContent(XContentBuilder builder, Params params) throws IOException {
        builder.startObject();
        builder.field(TRAINING_DOCS_COUNT.getPreferredName(), trainingDocsCount);
        builder.field(TEST_DOCS_COUNT.getPreferredName(), testDocsCount);
        builder.field(SKIPPED_DOCS_COUNT.getPreferredName(), skippedDocsCount);
        builder.endObject();
        return builder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataCounts that = (DataCounts) o;
        return trainingDocsCount == that.trainingDocsCount
            && testDocsCount == that.testDocsCount
            && skippedDocsCount == that.skippedDocsCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(trainingDocsCount, testDocsCount, skippedDocsCount);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(getClass())
            .add(TRAINING_DOCS_COUNT.getPreferredName(), trainingDocsCount)
            .add(TEST_DOCS_COUNT.getPreferredName(), testDocsCount)
            .add(SKIPPED_DOCS_COUNT.getPreferredName(), skippedDocsCount)
            .toString();
    }

    public long getTrainingDocsCount() {
        return trainingDocsCount;
    }

    public long getTestDocsCount() {
        return testDocsCount;
    }

    public long getSkippedDocsCount() {
        return skippedDocsCount;
    }

    private static <T> T getOrDefault(@Nullable T value, T defaultValue) {
        return value != null ? value : defaultValue;
    }
}
