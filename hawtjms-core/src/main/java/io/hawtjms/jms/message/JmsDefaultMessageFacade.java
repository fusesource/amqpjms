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
package io.hawtjms.jms.message;

import io.hawtjms.jms.JmsDestination;
import io.hawtjms.jms.meta.JmsMessageId;
import io.hawtjms.jms.meta.JmsTransactionId;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;

/**
 * A default implementation of the JmsMessageFaceade that provides a generic
 * message instance which can be used instead of implemented in Provider specific
 * version that maps to a Provider message object.
 */
public class JmsDefaultMessageFacade implements JmsMessageFacade {

    protected Map<String, Object> properties;

    protected byte priority = javax.jms.Message.DEFAULT_PRIORITY;
    protected String groupId;
    protected int groupSequence;
    protected JmsMessageId messageId;
    protected long expiration;
    protected long timestamp;
    protected String correlationId;
    protected boolean persistent;
    protected int redeliveryCount;
    protected String type;
    protected JmsDestination destination;
    protected JmsDestination replyTo;
    protected String userId;
    protected JmsTransactionId transactionId;

    @Override
    public JmsDefaultMessageFacade copy() {
        JmsDefaultMessageFacade copy = new JmsDefaultMessageFacade();
        copy.priority = this.priority;
        copy.groupSequence = this.groupSequence;
        copy.groupId = this.groupId;
        copy.expiration = this.expiration;
        copy.timestamp = this.timestamp;
        copy.correlationId = this.correlationId;
        copy.persistent = this.persistent;
        copy.redeliveryCount = this.redeliveryCount;
        copy.type = this.type;
        copy.destination = this.destination;
        copy.replyTo = this.replyTo;
        copy.userId = this.userId;
        copy.transactionId = this.transactionId;

        if (this.messageId != null) {
            copy.messageId = this.messageId.copy();
        }

        if (this.properties != null) {
            copy.properties = new HashMap<String, Object>(this.properties);
        } else {
            copy.properties = null;
        }

        return copy;
    }

    @Override
    public Map<String, Object> getProperties() throws IOException {
        lazyCreateProperties();
        return properties;
    }

    @Override
    public void storeContent() throws JMSException {
        // TODO
    }

    @Override
    public void clearBody() {
        // TODO
    }

    @Override
    public void clearProperties() {
        properties = null;
    }

    @Override
    public JmsTransactionId getTransactionId() {
        return this.transactionId;
    }

    @Override
    public void setTransactionId(JmsTransactionId transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public JmsMessageId getMessageId() {
        return this.messageId;
    }

    @Override
    public void setMessageId(JmsMessageId messageId) {
        this.messageId = messageId;
    }

    @Override
    public long getTimestamp() {
        return this.timestamp;
    }

    @Override
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String getCorrelationId() {
        return correlationId;
    }

    @Override
    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    @Override
    public boolean isPersistent() {
        return this.persistent;
    }

    @Override
    public void setPersistent(boolean value) {
        this.persistent = value;
    }

    @Override
    public int getRedeliveryCounter() {
        return this.redeliveryCount;
    }

    @Override
    public void setRedeliveryCounter(int redeliveryCount) {
        this.redeliveryCount = redeliveryCount;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public byte getPriority() {
        return priority;
    }

    @Override
    public void setPriority(byte priority) {
        this.priority = priority;
    }

    @Override
    public long getExpiration() {
        return expiration;
    }

    @Override
    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }

    @Override
    public JmsDestination getDestination() throws JMSException {
        return this.destination;
    }

    @Override
    public void setDestination(JmsDestination destination) {
        this.destination = destination;
    }

    @Override
    public JmsDestination getReplyTo() throws JMSException {
        return this.replyTo;
    }

    @Override
    public void setReplyTo(JmsDestination replyTo) {
        this.replyTo = replyTo;
    }

    @Override
    public String getUserId() {
        return this.userId;
    }

    @Override
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String getGroupId() {
        return this.groupId;
    }

    @Override
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Override
    public int getGroupSequence() {
        return this.groupSequence;
    }

    @Override
    public void setGroupSequence(int groupSequence) {
        this.groupSequence = groupSequence;
    }

    private void lazyCreateProperties() throws IOException {
        if (properties == null) {
            properties = new HashMap<String, Object>();
        }
    }
}
