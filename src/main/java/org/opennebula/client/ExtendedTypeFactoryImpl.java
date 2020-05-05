/*******************************************************************************
 * Copyright 2002-2019, OpenNebula Project, OpenNebula Systems
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.opennebula.client;

import org.apache.ws.commons.util.NamespaceContextImpl;
import org.apache.xmlrpc.common.TypeFactoryImpl;
import org.apache.xmlrpc.common.XmlRpcController;
import org.apache.xmlrpc.common.XmlRpcStreamConfig;
import org.apache.xmlrpc.parser.LongParser;
import org.apache.xmlrpc.parser.TypeParser;

/**
 * This implementation of {@link org.apache.xmlrpc.common.TypeFactory} supports "i8" type
 */
public class ExtendedTypeFactoryImpl extends TypeFactoryImpl {

    private static final String LONG_XML_TAG_NAME = "i8";

    /**
     * Creates a new instance.
     *
     * @param pController The controller, which operates the type factory.
     */
    public ExtendedTypeFactoryImpl(XmlRpcController pController) {
        super(pController);
    }

    @Override
    public TypeParser getParser(XmlRpcStreamConfig pConfig, NamespaceContextImpl pContext, String pURI, String pLocalName) {
        if (LONG_XML_TAG_NAME.equals(pLocalName))
            return new LongParser();

        return super.getParser(pConfig, pContext, pURI, pLocalName);
    }
}
