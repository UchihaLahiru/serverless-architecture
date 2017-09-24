/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package user;

import java.util.ArrayList;

/**
 * Created by deshan on 8/24/17.
 */
public interface UserCall {
    /**
     * create a new function
     * @param functionName : the name of the function that is going to be created
     * @param file  : file path in the object storage and name
     * @param language  : the languaage environment
     * @param user  : the user who is creating function
     * @return
     */
    boolean createFunction(String functionName, String file, String language, String user);


    /**
     * delete a existing function
     * @param functionName : the name of the function that is going to be delete
     * @param user  : the user
     */
    boolean deleteFunction(String functionName, String user);

    /**
     * list the all functions of a user
     * @param user
     */
    ArrayList<String> listFunction(String user);

    /**
     * invoke a function
     * @param functionName : name of the invoking function
     * @param blocking  : blocking or non blocking state
     * @param args  : argumaents that should pass to the function
     */
    void invokeFunction(String functionName, boolean blocking, String[] args);


    /**
     * update a existing funtion with new script
     * @param functionName
     * @param file : path of the object storage
     */
    boolean updateFunction(String functionName, String file);

//    refer openwhisk ibm

}
