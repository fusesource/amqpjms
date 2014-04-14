/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.hawtjms.provider.stomp;

import io.hawtjms.jms.meta.JmsProducerId;
import io.hawtjms.jms.meta.JmsProducerInfo;
import io.hawtjms.jms.meta.JmsSessionId;

/**
 * Producer class that wraps the details of message send operations over
 * STOMP.
 */
public class StompProducer {

    private final JmsProducerInfo producerInfo;
    private final StompSession session;

    public StompProducer(StompSession session, JmsProducerInfo producerInfo) {
        this.session = session;
        this.producerInfo = producerInfo;
    }

    public JmsProducerId getProducerId() {
        return this.producerInfo.getProducerId();
    }

    public JmsSessionId getSessionId() {
        return this.producerInfo.getParentId();
    }

    public StompSession getSession() {
        return this.session;
    }
}