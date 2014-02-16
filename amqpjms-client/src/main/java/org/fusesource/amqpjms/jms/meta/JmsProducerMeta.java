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
package org.fusesource.amqpjms.jms.meta;

import org.fusesource.amqpjms.jms.JmsDestination;
import org.fusesource.amqpjms.jms.util.ToStringSupport;

public class JmsProducerMeta implements Comparable<JmsProducerMeta> {

    protected final JmsProducerId producerId;
    protected JmsDestination destination;

    public JmsProducerMeta(JmsProducerId producerId) {
        this.producerId = producerId;
    }

    public JmsProducerMeta(JmsSessionMeta sessionInfo, long producerId) {
        this.producerId = new JmsProducerId(sessionInfo.getSessionId(), producerId);
    }

    public JmsProducerMeta copy() {
        JmsProducerMeta info = new JmsProducerMeta(producerId);
        copy(info);
        return info;
    }

    public void copy(JmsProducerMeta info) {
        info.destination = destination;
    }

    public JmsProducerId getProducerId() {
        return producerId;
    }

    public JmsDestination getDestination() {
        return destination;
    }

    public void setDestination(JmsDestination destination) {
        this.destination = destination;
    }

    @Override
    public String toString() {
        return ToStringSupport.toString(this);
    }

    @Override
    public int hashCode() {
        return (producerId == null) ? 0 : producerId.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        JmsProducerMeta other = (JmsProducerMeta) obj;

        if (producerId == null && other.producerId != null) {
            return false;
        } else if (!producerId.equals(other.producerId)) {
            return false;
        }

        return true;
    }

    @Override
    public int compareTo(JmsProducerMeta other) {
        return this.producerId.compareTo(other.producerId);
    }
}