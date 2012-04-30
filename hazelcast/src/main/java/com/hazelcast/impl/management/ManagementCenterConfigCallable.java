/*
 * Copyright (c) 2008-2012, Hazel Bilisim Ltd. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hazelcast.impl.management;

import com.hazelcast.config.MapConfig;
import com.hazelcast.core.HazelcastInstanceAware;
import com.hazelcast.impl.CMap;
import com.hazelcast.impl.FactoryImpl;
import com.hazelcast.impl.Processable;
import com.hazelcast.nio.DataSerializable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;

public class ManagementCenterConfigCallable extends ClusterServiceCallable implements Callable<Void>, DataSerializable {

    private String newUrl;

    public ManagementCenterConfigCallable() {
    }

    public ManagementCenterConfigCallable(String newUrl) {
        super();
        this.newUrl = newUrl;
    }

    public Void call() throws Exception {
        FactoryImpl factory = (FactoryImpl) hazelcastInstance;
        ManagementCenterService service = factory.node.getManagementCenterService();
        if (service != null)
            service.changeWebServerUrl(newUrl);
        return null;
    }

    public void writeData(DataOutput out) throws IOException {
        out.writeUTF(newUrl);
    }

    public void readData(DataInput in) throws IOException {
        newUrl = in.readUTF();
    }
}
