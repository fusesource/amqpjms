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
package io.hawtjms.provider.stomp.adapters;

import io.hawtjms.jms.JmsDestination;
import io.hawtjms.jms.meta.JmsConsumerInfo;
import io.hawtjms.provider.stomp.StompConnection;
import io.hawtjms.provider.stomp.StompFrame;

import javax.jms.JMSException;

/**
 * The StompServerAdapter defines an interface for an adapter class that can
 * augment the standard STOMP protocol with additional functionality offered by
 * various STOMP server implementations.
 */
public interface StompServerAdapter {

    /**
     * @return the Queue prefix used to define Queue destinations on the Server.
     */
    String getQueuePrefix();

    /**
     * @return the Topic prefix used to define Topic destinations on the Server.
     */
    String getTopicPrefix();

    /**
     * @return the Temporary Queue prefix used to define temporary destinations on the Server.
     */
    String getTempQueuePrefix();

    /**
     * @return the Temporary Topic prefix used to define temporary destinations on the Server.
     */
    String getTempTopicPrefix();

    /**
     * @returns the StompConnection instance that owns this adapter.
     */
    StompConnection getStompConnection();

    /**
     * Converts the given JmsDestination into a Server compatible STOMP destination
     * name string.
     *
     * @param destination
     *        the JmsDestination to convert to the STOMP format appropriate to the Server.
     */
    String toStompDestination(JmsDestination destination);

    /**
     * Converts from a STOMP frame's destination or reply-to destination name string to
     * the appropriate JmsDestination type with correct destination name.  The returned
     * JmsDestination name should be stripped of any Server specific destination name
     * prefix values.
     *
     * @param destination
     *        the STOMP destination name string from a frame.
     *
     * @return a new JmsDesitnation instance with the correct type and name values.
     *
     * @throws JMSException if the name cannot be converted to a JmsDestination instance.
     */
    JmsDestination toJmsDestination(String destination) throws JMSException;

    /**
     * Creates a Credit frame which can be used to release additional messages from a Broker
     * prior to a full message acknowledgment.  Not all STOMP servers support this feature
     * so this method can return null in that case.
     *
     * @param messageFrame
     *
     * @return a new credit frame if the server supports them.
     */
    StompFrame createCreditFrame(StompFrame messageFrame);

    /**
     * Before a STOMP SUBSCRIBE frame is sent to the server, this method is called to allow
     * for server specific properties to be added to the subscription frame.
     *
     * @param frame
     *        the frame containing the SUBSCRIBE command.
     * @param consumerInfo
     *        the consumer information for this subscription.
     *
     * @throws JMSException if an error occurs while configuring the subscription frame.
     */
    void addSubscribeHeaders(StompFrame frame, JmsConsumerInfo consumerInfo) throws JMSException;

    /**
     * Creates a proper UNSUBSCRIBE frame for the given consumer.
     *
     * @param consumer
     *        the consumer that is un-subscribing.
     *
     * @return a new STOMP UNSUBSCRIBE frame.
     *
     * @throws JMSException
     */
    StompFrame createUnsubscribeFrame(JmsConsumerInfo consumer) throws JMSException;

    /**
     * @return the name of the remote server this adapter supports.
     */
    String getServerName();

    /**
     * @return the version of the server connected to, if it reported it.
     */
    String getServerVersion();

}