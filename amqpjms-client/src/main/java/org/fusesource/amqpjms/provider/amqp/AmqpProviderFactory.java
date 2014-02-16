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

import org.fusesource.amqpjms.provider.Provider;
import org.fusesource.amqpjms.provider.ProviderFactory;

/**
 * Factory for creating the AMQP over TCP provider.
 */
public class AmqpProviderFactory implements ProviderFactory {

    /* (non-Javadoc)
     * @see org.fusesource.amqpjms.provider.ProviderFactory#createProvider()
     */
    @Override
    public Provider createProvider() {
        return new AmqpProvider();
    }

    /* (non-Javadoc)
     * @see org.fusesource.amqpjms.provider.ProviderFactory#getName()
     */
    @Override
    public String getName() {
        return "AMQP";
    }
}
