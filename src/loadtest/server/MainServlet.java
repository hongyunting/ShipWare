/*******************************************************************************
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
 *
 ******************************************************************************/

package loadtest.server;

import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.*;
import java.util.zip.*;

/**
 * This is the main servlet for testing our load testing program. This servlet
 * is just a simple demo so we have something to test against. This server will
 * show the form MainForm.html on get requests and display the parameters on put
 * and post requests.
 */
public class MainServlet extends HttpServlet
{
    /**
     * This is the default content type for an HTML file.
     */
    static final String CONTENT_TYPE = "text/html";

    /**
     * This method handles GET requests from the browser or the load client.
     * This method will load the return the text of MainForm.html
     * 
     * @param request
     *        the http request
     * @param response
     *        the http response
     * @exception ServletException
     * @exception IOException
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException,
            IOException
    {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();

        out.print(getMainForm());
        out.flush();
        out.close();
    }

    /**
     * This method handles POST requests from the browser or the load client.
     * This method will just print out the parameters which were passed followed
     * by the text "Everything worked fine". That is the text the load client
     * will look for.
     * 
     * @param request
     *        the http request
     * @param response
     *        the http response
     * @exception ServletException
     * @exception IOException
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException,
            IOException
    {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();

        out.println("<html><body>");

        Enumeration params = request.getParameterNames();
        do {
            String name = (String) params.nextElement();
            out.print(name);
            out.print(": ");
            String vals[] = request.getParameterValues(name);
            for (int i = 0; i < vals.length; i++) {
                out.print(vals[i]);
                out.print(", ");
            }
            out.println("<br />");
        }
        while (params.hasMoreElements());

        out.println("<br /><br />Everything worked fine");

        out.println("</body></html>");

        out.flush();
        out.close();
    }

    /**
     * This method handles PUT requests from the browser or the load client. In
     * this servlet the doPut method just calls doPost.
     * 
     * @param request
     *        the http request
     * @param response
     *        the http response
     * @exception ServletException
     * @exception IOException
     */
    public void doPut(HttpServletRequest request, HttpServletResponse response)
        throws ServletException,
            IOException
    {
        doPost(request, response);
    }

    /**
     * A static variable for holding onto the main form's text.
     */
    private static String g_mainForm = null;

    /**
     * We want to load the text of the main form, but we only want to load it
     * once.
     * 
     * @return the HTML text of the form for the get method
     */
    private String getMainForm()
    {
        if (g_mainForm == null) {
            StringBuffer out = new StringBuffer();
            try {
                ClassLoader cl = this.getClass().getClassLoader();
                InputStream input = cl.getResourceAsStream("ShipWare/Containers.html");

                /*
                 * It is just a good practice to use an InputStreamReader in
                 * case there are any international characters. This will read
                 * them with the system's default encoding.
                 */
                InputStreamReader in = new InputStreamReader(input);
                try {
                    int read;
                    char[] buf = new char[512];

                    while ((read = in.read(buf)) > 0) {
                        out.append(buf, 0, read);
                    }
                } finally {
                    /*
                     * It is important to make sure that we close the
                     * inputstream once we are done with it.
                     */
                    if (in != null) {
                        in.close();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

            g_mainForm = out.toString();
        }

        return g_mainForm;
    }

}
