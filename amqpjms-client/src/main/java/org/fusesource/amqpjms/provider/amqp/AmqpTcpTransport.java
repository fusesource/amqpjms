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
package org.fusesource.amqpjms.provider.amqp;

import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;

import org.fusesource.amqpjms.jms.util.IOExceptionSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.AsyncResultHandler;
import org.vertx.java.core.Handler;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.VertxFactory;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.net.NetClient;
import org.vertx.java.core.net.NetSocket;

/**
 * Vertex based TCP transport for AMQP raw data packets.
 */
public class AmqpTcpTransport implements AmqpTransport {

    private static final Logger LOG = LoggerFactory.getLogger(AmqpConnection.class);

    private final Vertx vertx = VertxFactory.newVertx();
    private final NetClient client = vertx.createNetClient();
    private final AmqpConnection parent;
    private final URI remoteLocation;

    private NetSocket socket;

    /**
     *
     */
    public AmqpTcpTransport(AmqpConnection parent) {
        this.parent = parent;
        this.remoteLocation = parent.getRemoteURI();
    }

    @Override
    public void connect() throws IOException {

        try {
            client.connect(remoteLocation.getPort(), remoteLocation.getHost(), new AsyncResultHandler<NetSocket>() {
                @Override
                public void handle(AsyncResult<NetSocket> asyncResult) {
                    if (asyncResult.succeeded()) {
                        socket = asyncResult.result();
                        LOG.info("We have connected! Socket is {}", socket);

                        socket.dataHandler(new Handler<Buffer>() {
                            @Override
                            public void handle(Buffer event) {
                                parent.onAmqpData(event);
                            }
                        });

                        socket.exceptionHandler(new Handler<Throwable>() {
                            @Override
                            public void handle(Throwable event) {
                                parent.onTransportError(event);
                            }
                        });
                    } else {
                        // TODO report error;
                        asyncResult.cause();
                    }
                }
            });
        } catch (Throwable reason) {
            LOG.info("Failed to connect to target Broker: {}", reason);
            throw IOExceptionSupport.create(reason);
        }
    }

    @Override
    public void send(ByteBuffer output) throws IOException {
    }

}
