/*
* Licensed to the Apache Software Foundation (ASF) under one or more
* contributor license agreements.  See the NOTICE file distributed with
* this work for additional information regarding copyright ownership.
* The ASF licenses this file to You under the Apache License, Version 2.0
* (the "License"); you may not use this file except in compliance with
* the License.  You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ResourceBundle;

/**
 * The simplest possible servlet.
 *
 * @author James Duncan Davidson
 */

public class ProcessorCommonServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
        throws IOException, ServletException
    {
        ResourceBundle rb =
            ResourceBundle.getBundle("LocalStrings",request.getLocale());
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        out.println("ProcessorResearchServlet");

    }
    private static final Log log = LogFactory.getLog(ProcessorCommonServlet.class);

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException
    {

        response.setStatus(200);
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Transfer-Encoding", "chunked");
        response.flushBuffer();

        ServletOutputStream outputStream = response.getOutputStream();

        for (int i = 0; i < 5; i++) {
            outputStream.write(("Chunk " + i + "\n").getBytes());
            outputStream.flush();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                log.error("Sleep interrupted", e);
            }
        }

    }

    private void doEchoService(HttpServletRequest request,
                               HttpServletResponse response) throws IOException, ServletException {
        log.info("Echo service called");
        OutputStream outputStream = response.getOutputStream();
        InputStream inputStream = request.getInputStream();
        while (true) {
            byte[] buffer = new byte[10];
            int bytesRead = inputStream.read(buffer);
            if (bytesRead == -1) {
                break;
            }
            outputStream.write(buffer, 0, bytesRead);
            outputStream.flush();
        }
    }
}



