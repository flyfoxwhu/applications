/*
 * Copyright (c) 2006 Hyperic, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.applications.service.sigar.examples;

import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.win32.Service;
import org.hyperic.sigar.win32.Win32Exception;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Win32Service extends SigarCommandBase {

    private static final List COMMANDS = 
        Arrays.asList(new String[] {
            "state",
            "start",
            "stop",
            "pause",
            "resume",
            "restart",
        });
    
    public Win32Service() {
        super();
    }

    public Win32Service(Shell shell) {
        super(shell);
    }

    @Override
    public String getSyntaxArgs() {
        return "[name] [action]";
    }

    @Override
    public String getUsageShort() {
        return "Windows service commands";
    }

    @Override
    protected boolean validateArgs(String[] args) {
        return (args.length == 1) || (args.length == 2);
    }

    @Override
    public Collection getCompletions() {
        try {
            return Service.getServiceNames();
        } catch (Win32Exception e) {
            return null;
        }
    }

    @Override
    public void output(String[] args) throws SigarException {
        Service service = null;
        String name = args[0];
        String cmd = null;

        if (args.length == 2) {
            cmd = args[1];
        }
        
        try {
            service = new Service(name);
        
            if ((cmd == null) || "state".equals(cmd)) {
                service.list(this.out);
            }
            else if ("start".equals(cmd)) {
                service.start();
            }
            else if ("stop".equals(cmd)) {
                service.stop();
            }
            else if ("pause".equals(cmd)) {
                service.pause();
            }
            else if ("resume".equals(cmd)) {
                service.resume();
            }
            else if ("delete".equals(cmd)) {
                service.delete();
            }
            else if ("restart".equals(cmd)) {
                service.stop(0);
                service.start();
            }
            else {
                println("Unsupported service command: " + args[1]);
                println("Valid commands: " + COMMANDS);
            }
        } finally {
            if (service != null) {
                service.close();
            }
        }
    }
}
