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
package io.hawtjms.provider;

import io.hawtjms.jms.message.JmsMessageFactory;
import io.hawtjms.jms.meta.JmsConsumerId;
import io.hawtjms.jms.meta.JmsSessionId;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.jms.JMSException;

/**
 * Base class used to implement the most common features of a AsyncProvider.
 *
 * Methods that are fully optional such as transaction commit and rollback are implemented
 * here to throw an UnsupportedOperationException.
 */
public abstract class AbstractAsyncProvider implements AsyncProvider {

    protected final URI remoteURI;
    protected final JmsMessageFactory messageFactory;
    protected final AtomicBoolean closed = new AtomicBoolean();

    protected ProviderListener listener;

    public AbstractAsyncProvider(URI remoteURI, JmsMessageFactory messageFactory) {
        this.remoteURI = remoteURI;
        this.messageFactory = messageFactory;
    }

    @Override
    public void start() throws IOException, IllegalStateException {
        checkClosed();

        if (listener == null) {
            throw new IllegalStateException("No ProviderListener registered.");
        }
    }

    @Override
    public void commit(JmsSessionId sessionId, AsyncResult<Void> request) throws IOException, JMSException, UnsupportedOperationException {
        throw new UnsupportedOperationException("Provider does not support Transactions");
    }

    @Override
    public void rollback(JmsSessionId sessionId, AsyncResult<Void> request) throws IOException, JMSException, UnsupportedOperationException {
        throw new UnsupportedOperationException("Provider does not support Transactions");
    }

    @Override
    public void unsubscribe(String subscription, AsyncResult<Void> request) throws IOException, JMSException, UnsupportedOperationException {
        throw new UnsupportedOperationException("Provider does not support unsubscribe operations");
    }

    @Override
    public void pull(JmsConsumerId consumerId, long timeout, AsyncResult<Void> request) throws IOException, UnsupportedOperationException {
        throw new UnsupportedOperationException("Provider does not support message pull");
    }

    @Override
    public JmsMessageFactory getMessageFactory() {
        return messageFactory;
    }

    @Override
    public void setProviderListener(ProviderListener listener) {
        this.listener = listener;
    }

    @Override
    public ProviderListener getProviderListener() {
        return listener;
    }

    @Override
    public URI getRemoteURI() {
        return remoteURI;
    }

    protected void checkClosed() throws IOException {
        if (closed.get()) {
            throw new IOException("The Provider is already closed");
        }
    }
}